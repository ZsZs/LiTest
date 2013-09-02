package com.processpuzzle.litest.testcase;

import com.processpuzzle.litest.fixture.TestPersistentFreshFixture;
import com.processpuzzle.litest.testcase.GenericTestSuite;


public class MockSuiteWithPersistentFreshFixture extends GenericTestSuite<TestSUT, TestPersistentFreshFixture> {

   protected MockSuiteWithPersistentFreshFixture( String containerConfigurationPath ) {
      super( containerConfigurationPath );
   }
}
