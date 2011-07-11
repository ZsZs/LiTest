package hu.itkodex.litest.testcase;

import hu.itkodex.litest.fixture.TestFixture;

public class SpyFixtureStrategy<F extends TestFixture<?>> extends FixtureStrategy<F> {

   SpyFixtureStrategy( Class<F> fixtureClass, ObjectTestSuite<?, F> testSuite ) {
      super( fixtureClass, testSuite );
   }
   
   public void invokeInstantiateFixture() {
      super.instantiateFixture();
   }

   @Override
   void afterAllTests() {
      // We do not need this for the tests.
   }

   @Override
   void afterEachTest() {
      // We do not need this for the tests.
   }

   @Override
   void beforeAllTests() {
      // We do not need this for the tests.
   }

   @Override
   void beforeEachTest() {
      // We do not need this for the tests.
   }
}
