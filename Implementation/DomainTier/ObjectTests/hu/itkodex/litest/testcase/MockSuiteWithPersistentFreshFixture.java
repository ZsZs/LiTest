package hu.itkodex.litest.testcase;

import hu.itkodex.litest.fixture.TestPersistentFreshFixture;

public class MockSuiteWithPersistentFreshFixture extends GenericTestSuite<TestSUT, TestPersistentFreshFixture> {

   protected MockSuiteWithPersistentFreshFixture( String containerConfigurationPath ) {
      super( containerConfigurationPath );
   }
}
