package com.processpuzzle.litest.fixture;

import com.processpuzzle.litest.fixture.GenericCompositeFixture;
import com.processpuzzle.litest.testcase.AnotherTestSUT;


public class TestCompositeFixture extends GenericCompositeFixture<AnotherTestSUT> {

   @Override
   protected void defineComponentTypes() {
      componentTypes.add( TestPersistentSharedFixture.class );
      componentTypes.add( TestTransientFreshFixture.class );
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
