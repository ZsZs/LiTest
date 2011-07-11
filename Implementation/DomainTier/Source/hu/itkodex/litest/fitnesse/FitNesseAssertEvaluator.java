package hu.itkodex.litest.fitnesse;

import hu.itkodex.commons.compiler.StringCompiler;
import hu.itkodex.commons.compiler.StringCompilerException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FitNesseAssertEvaluator<T> {
   private static final String IMPORT_PLACEHOLDER = "<<import>>";
   private static final String TYPE_PLACEHOLDER = "<<type>>";
   private static final String VARIABLE_PLACEHOLDER = "<<variable>>";
   private static final String MATCHER_PLACEHOLDER = "<<macher>>";
   private static final String OBJECT_PLACEHOLDER = "<<subject>>";
   private static final String SOURCE_CODE_FILE = "FitNesseAssertionWrapper.java.template";
   private static final Logger logger = LoggerFactory.getLogger(  FitNesseAssertEvaluator.class );
   private static final List<String> COMPILER_OPTIONS = Arrays.asList( new String[] { "-target", "1.5" } );
   private String sourceCodeTemplate;
   private StringCompiler<FitNesseAssertion<T>> compiler;
   private FitNesseAssertion<T> assertion;
   private Class<T> subjectClass;
   
   public FitNesseAssertEvaluator( Class<?> subjectClass ) {
      this( subjectClass, null );
   }
   
   @SuppressWarnings("unchecked")
   public FitNesseAssertEvaluator( Class<?> subjectClass, StringCompiler<?> compiler ) {
      this.compiler = (StringCompiler<FitNesseAssertion<T>>) compiler;
      instantiateRuntimeCompiler();
      loadSourceFromFile();
      this.subjectClass = (Class<T>) subjectClass;
   }

   public boolean evaluateAssertion( Map<String, T> variables, String objectExpression, String matcherExpression ) {
      String variableName = determineVariableName( objectExpression );
      
      String sourceToCompile = replacePlaceholdersInSourceTemplate( objectExpression, matcherExpression, variableName );

      Class<? extends FitNesseAssertion<T>> assertionClass = compileFitNesseAssertionClass( sourceToCompile, matcherExpression );
      
      reloadFitNesseAssertionClass( assertionClass );
      
      assertion = instantiateAssertion( assertionClass );
      return assertion.evaluateAssert( variables, variableName );
   }

   private Class<? extends FitNesseAssertion<T>> compileFitNesseAssertionClass( String sourceToCompile, String matcherExpression ) {
      logger.trace( "Intending to compile:\n" + sourceToCompile );
      Class<? extends FitNesseAssertion<T>> assertionClass = null;
      
      try{
         assertionClass = compiler.compile( sourceToCompile, null, new Class<?>[] { FitNesseAssertion.class } );
      }catch( ClassCastException e ){
         String message = "Type mismatch in compilation of 'FitNesseAssertion'";
         logger.error( message );
         throw new FitNesseAssertEvaluatorException( message, e );
      }catch( StringCompilerException e ){
         String message = "There was a problem in compiling 'FitNesseAssertion'.";
         logger.error( message, e );
         throw new FitNesseAssertEvaluatorException( message, e );
      }
      
      return assertionClass;
   }

   private String determineVariableName( String objectExpression ) {
      String variableName = StringUtils.substringBefore( objectExpression, "." );
      return variableName;
   }

   private String insertImportIntoSource( String sourceCode, Class<T> subjectClass ) {
      String replacedCode = StringUtils.replace( sourceCode, IMPORT_PLACEHOLDER, "import " + subjectClass.getName() + ";" );
      return replacedCode;
   }

   private FitNesseAssertion<T> instantiateAssertion( Class<? extends FitNesseAssertion<T>> assertionClass ) {
      FitNesseAssertion<T> assertion = null;
      try{
         assertion = assertionClass.newInstance();
      }catch( InstantiationException e ){
         String message = "There was a problem when instantiting '" + assertionClass.getName() + "'";
         logger.error( message, e );
         throw new FitNesseAssertEvaluatorException( message, e );
      }catch( IllegalAccessException e ){
         String message = "Can't access when instantiting '" + assertionClass.getName() + "'";
         logger.error( message, e );
         throw new FitNesseAssertEvaluatorException( message, e );
      }
      return assertion;
   }

   private void instantiateRuntimeCompiler() {
      compiler = new StringCompiler<FitNesseAssertion<T>>( getClass().getClassLoader(), COMPILER_OPTIONS, compiler );
   }

   private void loadSourceFromFile() {
      InputStream inputStream = getClass().getResourceAsStream( SOURCE_CODE_FILE );
      try{
         sourceCodeTemplate = IOUtils.toString( inputStream );
      }catch( IOException e ){
         String message = "Couldn't read in file:" + SOURCE_CODE_FILE;
         logger.error( message );
         throw new FitNesseAssertEvaluatorException( message, e );
      }finally {
         IOUtils.closeQuietly( inputStream );
      }
   }

   private void reloadFitNesseAssertionClass( Class<? extends FitNesseAssertion<T>> newClass ) {
      logger.trace( "Class before reload:" + FitNesseAssertion.class );
      try{
         compiler.loadClass( newClass.getName() );
      }catch( ClassNotFoundException e ){
         String message = "Class: '" + newClass.getName() + "' not found when relading.";
         logger.error( message );
         throw new FitNesseAssertEvaluatorException( message, e );
      }
      logger.trace( "Class after reload:" + FitNesseAssertion.class );
   }

   private String replaceGenericType( String sourceCode, String genericTypeName ) {
      String replacedCode = StringUtils.replace( sourceCode, TYPE_PLACEHOLDER, genericTypeName );
      return replacedCode;
   }

   private String replaceMatcherExpression( String sourceCode, String matcherExpression ) {
      String replacedCode = StringUtils.replace( sourceCode, MATCHER_PLACEHOLDER, matcherExpression );
      return replacedCode;
   }

   private String replaceObjectExpression( String sourceCode, String objectExpression ) {
      String replacedCode = StringUtils.replace(  sourceCode, OBJECT_PLACEHOLDER, objectExpression );
      return replacedCode;
   }

   private String replacePlaceholdersInSourceTemplate( String objectExpression, String matcherExpression, String variableName ) {
      String sourceToCompile = replaceGenericType( sourceCodeTemplate, subjectClass.getSimpleName() );
      sourceToCompile = replaceVariable( sourceToCompile, variableName );
      sourceToCompile = replaceObjectExpression( sourceToCompile, objectExpression );
      sourceToCompile = replaceMatcherExpression( sourceToCompile, matcherExpression );
      sourceToCompile = insertImportIntoSource( sourceToCompile, subjectClass );
      return sourceToCompile;
   }

   private String replaceVariable( String sourceCode, String variableName ) {
      String replacedCode = StringUtils.replace(  sourceCode, VARIABLE_PLACEHOLDER, variableName );
      return replacedCode;
   }
}
