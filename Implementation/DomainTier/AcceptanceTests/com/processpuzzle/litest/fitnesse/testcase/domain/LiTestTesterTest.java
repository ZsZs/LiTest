package com.processpuzzle.litest.fitnesse.testcase.domain;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class LiTestTesterTest {
   private static final String TEST_FIXTURE = "com/processpuzzle/litest/fixture/FitTestTransientFreshFixture.java.template";
   private static final String TEST_SUITE = "com/processpuzzle/litest/testsuite/FitTestSuiteWithTransientFreshFixture.java.template";
   private static LiTestTester liTestTester;

   @BeforeClass public static void beforeAllTes() throws IOException{
      liTestTester = new LiTestTester();
      liTestTester.defineTestFixture( loadSourceCode( TEST_FIXTURE ));
      liTestTester.defineTestSuite( loadSourceCode( TEST_SUITE ));      
   }
   
   @Before public void beforeEachTests(){
   }
   
   @Test public void instantiateTestSuite_compilesObject() throws IOException, InstantiationException, IllegalAccessException{
      liTestTester.instantiateTestSuite( "freshTestSuite" );
      liTestTester.assertThatIs( "freshTestSuite.getFixture()", "nullValue()" );
      liTestTester.assertThatIs( "freshTestSuite.getFixture()", "instanceOf( GenericTestFixture.class )" );
   }
   
   //Private test helpers
   private static String loadSourceCode( String sourcePath ) throws IOException{
      String sourceCode = null;
      Resource resource = new ClassPathResource( sourcePath );
      sourceCode = new String( Files.readAllBytes( resource.getFile().toPath() ));
      return sourceCode;
   }
}
