package hu.itkodex.litest.testcase;

import hu.itkodex.litest.fixture.TransientFreshFixture;

public class TransientFreshFixtureStrategy<F extends TransientFreshFixture<?>> extends FixtureStrategy<F> {

   TransientFreshFixtureStrategy( Class<F> fixtureClass, ObjectTestSuite<?, F> testSuite ) {
      super( fixtureClass, testSuite );
   }

   @Override
   void afterAllTests() {
      // TODO Auto-generated method stub
   }

   @Override
   void afterEachTest() {
      fixture.tearDown();
      super.terminateFixture();
   }

   @Override
   void beforeAllTests() {
      // TODO Auto-generated method stub
   }

   @Override
   void beforeEachTest() {
      instantiateFixture();
      fixture.setUp();
   }
}
