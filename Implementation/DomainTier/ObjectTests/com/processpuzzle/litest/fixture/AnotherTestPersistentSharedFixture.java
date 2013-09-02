package com.processpuzzle.litest.fixture;

import com.processpuzzle.litest.fixture.GenericTestFixture;
import com.processpuzzle.litest.fixture.PersistentSharedFixture;
import com.processpuzzle.litest.testcase.TestSUT;


public class AnotherTestPersistentSharedFixture extends GenericTestFixture<TestSUT> implements PersistentSharedFixture<TestSUT> {

   @Override public void setUp() {
      super.setUp();
   }
   
   @Override protected TestSUT instantiateSUT() {
      return new TestSUT();
   }

   @Override
   protected void configureAfterSutInstantiation() {
   }

   @Override
   protected void configureBeforeSutInstantiation() {
   }

   @Override
   protected void releaseResources() {
   }
}
