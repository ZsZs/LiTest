package hu.itkodex.litest.fixture;

import hu.itkodex.litest.testcase.AnotherTestSUT;

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
