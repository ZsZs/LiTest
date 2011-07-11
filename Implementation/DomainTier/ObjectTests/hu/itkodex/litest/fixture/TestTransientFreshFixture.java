package hu.itkodex.litest.fixture;

import hu.itkodex.litest.testcase.AnotherTestSUT;

public class TestTransientFreshFixture extends GenericTestFixture<AnotherTestSUT> implements TransientFreshFixture<AnotherTestSUT>{
   private boolean setUpWasCalled = false;
   private boolean tearDownWasCalled = false;
   
   public void setUp() {
      super.setUp();
      setUpWasCalled = true;
   }
   
   public boolean setUpWasCalled() { return setUpWasCalled; }
   public boolean tearDownWasCalled() { return tearDownWasCalled; }
   
   @Override protected AnotherTestSUT instantiateSUT() {
      return new AnotherTestSUT();
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
