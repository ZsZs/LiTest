package hu.itkodex.litest.fixture;

import hu.itkodex.litest.testcase.TestSUT;

public class TestPersistentFreshFixture extends GenericTestFixture<TestSUT> implements PersistentFreshFixture<TestSUT> {

   @Override public void setUp() {
      super.setUp();
   }
   
   @Override protected TestSUT instantiateSUT() {
      return new TestSUT();
   }

   @Override
   protected void configureAfterSutInstantiation() {
      // TODO Auto-generated method stub
      
   }

   @Override
   protected void configureBeforeSutInstantiation() {
      // TODO Auto-generated method stub
      
   }

   @Override
   protected void releaseResources() {
      // TODO Auto-generated method stub
      
   }

}
