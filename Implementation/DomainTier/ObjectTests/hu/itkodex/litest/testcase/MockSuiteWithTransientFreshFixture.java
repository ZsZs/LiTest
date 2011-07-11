package hu.itkodex.litest.testcase;

import hu.itkodex.litest.fixture.TestTransientFreshFixture;
import hu.itkodex.litest.fixture.TransientFreshFixture;

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
