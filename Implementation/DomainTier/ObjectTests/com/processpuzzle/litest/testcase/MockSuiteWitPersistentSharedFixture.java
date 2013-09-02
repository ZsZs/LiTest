package com.processpuzzle.litest.testcase;

import com.processpuzzle.litest.fixture.TestPersistentSharedFixture;
import com.processpuzzle.litest.testcase.GenericTestSuite;


public class MockSuiteWitPersistentSharedFixture extends GenericTestSuite<TestSUT, TestPersistentSharedFixture> {

   protected MockSuiteWitPersistentSharedFixture( String containerConfigurationPath ) {
      super( containerConfigurationPath );
   }

   public void beforeAllTests_InstantiatesAndSetUpFixture() { }
   
   public void testCaseMethod_One() { }
   public void testCaseMethod_Two() { }
}
