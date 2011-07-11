package hu.itkodex.litest.testcase;

import hu.itkodex.litest.fixture.PersistentSharedFixture;

public class PersistentSharedFixtureStrategy<F extends PersistentSharedFixture<?>> extends FixtureStrategy<F> {

   PersistentSharedFixtureStrategy( Class<F> fixtureClass, ObjectTestSuite<?, F> testSuite ) {
      super( fixtureClass, testSuite );
   }

   @Override
   void afterAllTests() {
      fixture.tearDown();
      super.terminateFixture();
   }

   @Override
   void afterEachTest() {
   }

   @Override
   void beforeAllTests() {
      fixture = instantiateFixture();
      fixture.setUp();
   }

   @Override
   void beforeEachTest() {
      fixture = instantiateFixture();
   }
}
