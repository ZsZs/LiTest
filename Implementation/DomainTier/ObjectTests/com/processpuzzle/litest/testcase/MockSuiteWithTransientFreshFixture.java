package com.processpuzzle.litest.testcase;

import com.processpuzzle.litest.fixture.TestTransientFreshFixture;
import com.processpuzzle.litest.fixture.TransientFreshFixture;
import com.processpuzzle.litest.testcase.GenericTestSuite;


public class MockSuiteWithTransientFreshFixture extends GenericTestSuite<AnotherTestSUT, TestTransientFreshFixture> implements TransientFreshFixture<AnotherTestSUT> {

   protected MockSuiteWithTransientFreshFixture( String fixtureContainerConfigurationPath ) {
      super( fixtureContainerConfigurationPath );
      // TODO Auto-generated constructor stub
   }

   @Override
   public void tearDown() {
      // TODO Auto-generated method stub
      
   }

   @Override
   public <D> D defineDOC( Class<D> dependsOnComponent ) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public <E> void defineExpectedValueFor( String propertyName, E expectedValue ) {
      // TODO Auto-generated method stub
      
   }

   @Override
   public Object getExpectedValueFor( String propertyName ) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public boolean isConfigured() {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void setUp() {
      // TODO Auto-generated method stub
      
   }

}
