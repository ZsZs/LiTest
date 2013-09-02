package com.processpuzzle.litest.fixture;

import com.processpuzzle.litest.fixture.GenericTestFixture;
import com.processpuzzle.litest.fixture.ImmutableSharedFixture;
import com.processpuzzle.litest.testcase.TestSUT;


public class TestImmutableSharedFixture extends GenericTestFixture<TestSUT> implements ImmutableSharedFixture<TestSUT> {

   @Override protected TestSUT instantiateSUT() {
      // TODO Auto-generated method stub
      return null;
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
