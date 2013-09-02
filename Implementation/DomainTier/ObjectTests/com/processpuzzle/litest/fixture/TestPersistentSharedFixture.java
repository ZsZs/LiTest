package com.processpuzzle.litest.fixture;

import com.processpuzzle.litest.fixture.GenericTestFixture;
import com.processpuzzle.litest.fixture.PersistentSharedFixture;
import com.processpuzzle.litest.testcase.TestSUT;


public class TestPersistentSharedFixture extends GenericTestFixture<TestSUT> implements PersistentSharedFixture<TestSUT> {

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
