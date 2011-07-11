package hu.itkodex.litest.testcase;

import hu.itkodex.litest.fixture.TestPersistentSharedFixture;

public class MockSuiteWitPersistentSharedFixture extends GenericTestSuite<TestSUT, TestPersistentSharedFixture> {

   protected MockSuiteWitPersistentSharedFixture( String containerConfigurationPath ) {
      super( containerConfigurationPath );
   }

   public void beforeAllTests_InstantiatesAndSetUpFixture() { }
   
   public void testCaseMethod_One() { }
   public void testCaseMethod_Two() { }
}
