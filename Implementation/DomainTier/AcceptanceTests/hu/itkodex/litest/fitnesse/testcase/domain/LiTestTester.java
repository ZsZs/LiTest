package hu.itkodex.litest.fitnesse.testcase.domain;

import hu.itkodex.commons.compiler.StringCompiler;
import hu.itkodex.commons.compiler.StringCompilerException;
import hu.itkodex.litest.fitnesse.FitNesseAssertEvaluator;
import hu.itkodex.litest.fixture.TestFixture;
import hu.itkodex.litest.testcase.ObjectTestSuite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fitlibrary.DoFixture;

public class LiTestTester extends DoFixture {
   private static final Logger logger = LoggerFactory.getLogger( LiTestTester.class );
   private static final List<String> COMPILER_OPTIONS = Arrays.asList( new String[] { "-target", "1.6" } );
   @SuppressWarnings("unused")
   private Class<? extends TestFixture<?>> fixtureClass;
   private ObjectTestSuite<?,?> testSuite;
   private Class<? extends ObjectTestSuite<?, ?>> testSuiteClass;
   private final Map<String, ObjectTestSuite<?,?>> testSuites = new HashMap<String, ObjectTestSuite<?,?>>();
   private StringCompiler<TestFixture<?>> fixtureCompiler;
   private StringCompiler<ObjectTestSuite<?,?>> suiteCompiler;
   
   public LiTestTester() {
   }
   
   public boolean assertThatIs( String objectExpression, String matcherExpression ) {
      FitNesseAssertEvaluator<ObjectTestSuite<?,?>> assertEvaluator = new FitNesseAssertEvaluator<ObjectTestSuite<?,?>>( ObjectTestSuite.class );
      return assertEvaluator.evaluateAssertion( testSuites, objectExpression, matcherExpression );
   }
   
   public void defineTestFixture( String sourceCode ) {
      fixtureCompiler = new StringCompiler<TestFixture<?>>( getClass().getClassLoader(), COMPILER_OPTIONS, fixtureCompiler );  
      
      try{
         fixtureClass = fixtureCompiler.compile( sourceCode, null, new Class<?>[] { TestFixture.class } );
      }catch( ClassCastException e ){
         logger.error( "Compiler recognized ClassCastException when compiling test fixture:\n\n" + sourceCode, e );
      }catch( StringCompilerException e ){
         logger.error( "Compiler recognized Exception when compiling test fixture:\n\n" + sourceCode, e );
      }
      logger.trace( "\n\nThe following source was successfully compiled:\n" + sourceCode + "\n\n" );
   }
   
   public void defineTestSuite( String sourceCode ) {
      suiteCompiler = new StringCompiler<ObjectTestSuite<?,?>>( getClass().getClassLoader(), COMPILER_OPTIONS, fixtureCompiler );      
      try{
         testSuiteClass = suiteCompiler.compile( sourceCode, null, new Class<?>[] { ObjectTestSuite.class } );
      }catch( ClassCastException e ){
         logger.error( "Compiler recognized ClassCastException when compiling test suite:\n\n" + sourceCode, e );
      }catch( StringCompilerException e ){
         logger.error( "Compiler recognized an Exception when compiling test suite:\n\n" + sourceCode, e );
      }
      logger.trace( "\n\nThe following source was successfully compiled:\n" + sourceCode + "\n\n" );
   }
   
   public void instantiateTestSuite( String variableName ) throws InstantiationException, IllegalAccessException {
      testSuite = testSuiteClass.newInstance();
      ObjectTestSuite<?, ?> testSuiteObject = testSuite;
      testSuites.put( variableName, testSuiteObject );
   }
   
   public void runAfterEachTest() {
      testSuite.afterEachTest();
   }
   
   public void runBeforeAllTests() {
      testSuite.beforeAllTests();
   }
   
   public void runBeforeEachTest() {
      testSuite.beforeEachTest();
   }
   
   public boolean runTestCase( String testCaseName ) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
      Method testCaseMethod = testSuiteClass.getDeclaredMethod( testCaseName, new Class[] {} );
      testCaseMethod.invoke( testSuite, new Object[] {} );
      return true;
   }
}
