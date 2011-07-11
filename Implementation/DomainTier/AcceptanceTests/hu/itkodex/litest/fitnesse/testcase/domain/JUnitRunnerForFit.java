package hu.itkodex.litest.fitnesse.testcase.domain;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import fitnesse.junit.JUnitHelper;

public class JUnitRunnerForFit {
   public static final String FITNESSE_ROOT_FOLDER_ARGUMENT = "fitnesse.root";
   public static final String TEST_PAGE_ARGUMENT = "fitnesse.page";
   public static final String TEST_SUITE_ARGUMENT = "fitnesse.suite";
   public static final String OUTPUT_FOLDER_ARGUMENT = "fitnesse.output";
   private static final String FITNESSE_ROOT_FOLDER = "C:/FitNesse";
   private static final String OUTPUT_FOLDER = "C:/Temp/Trinidad";
   private static final String TEST_PAGE = null;
   private static final String TEST_SUITE = null;
   private String fitnesseRoot;
   private String testPage;
   private String testSuite;
   private String outputFolder;
   JUnitHelper helper;

   @Before public void initHelper() throws Exception {
      determineParameters();
      File file = new File( outputFolder );
      helper = new JUnitHelper( fitnesseRoot, file.getAbsolutePath() );
   }
   
   @Test public void runTestSuite() throws Exception {
      if( testSuite != null ) helper.assertSuitePasses( testSuite );
      else System.out.println( "You should set system property '" + TEST_SUITE_ARGUMENT + "' to specify test suite to run." );
   }

   @Test public void runSingleTest() throws Exception {
      if( testPage != null ) helper.assertTestPasses( testPage );
      else System.out.println( "You should set system property '" + TEST_PAGE_ARGUMENT + "' to specify test page to run.");
   }
   
   private void determineParameters() {
      fitnesseRoot = System.getProperty( FITNESSE_ROOT_FOLDER_ARGUMENT ) != null ? System.getProperty( FITNESSE_ROOT_FOLDER_ARGUMENT ) : FITNESSE_ROOT_FOLDER; 
      testPage = System.getProperty( TEST_PAGE_ARGUMENT ) != null ? System.getProperty( TEST_PAGE_ARGUMENT ) : TEST_PAGE;
      testSuite = System.getProperty( TEST_SUITE_ARGUMENT ) != null ? System.getProperty( TEST_SUITE_ARGUMENT ) : TEST_SUITE;
      outputFolder = System.getProperty( OUTPUT_FOLDER_ARGUMENT ) != null ? System.getProperty( OUTPUT_FOLDER_ARGUMENT ) : OUTPUT_FOLDER;
   }
}
