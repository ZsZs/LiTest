package hu.itkodex.litest.testcase;

import hu.itkodex.litest.fixture.PersistentFreshFixture;

public class PersistentFreshFixtureStrategy<F extends PersistentFreshFixture<?>> extends FixtureStrategy<F> {

   PersistentFreshFixtureStrategy( Class<F> fixtureClass, ObjectTestSuite<?, F> testSuite ) {
      super( fixtureClass, testSuite );
   }

   @Override
   void afterAllTests() {
      // TODO Auto-generated method stub
      
   }

   @Override
   void afterEachTest() {
      fixture.tearDown();
      fixture = null;
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
