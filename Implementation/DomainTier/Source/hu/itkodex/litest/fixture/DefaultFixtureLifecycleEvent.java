package hu.itkodex.litest.fixture;

import hu.itkodex.commons.event.DefaultComponentEvent;

public class DefaultFixtureLifecycleEvent extends DefaultComponentEvent implements FixtureLifecycleEvent {
   private final TestFixture<?> fixture;

   public DefaultFixtureLifecycleEvent( String name, TestFixture<?> fixture ) {
      super( name );
      this.fixture = fixture;
   }
   
   @Override
   public TestFixture<?> getFixture() {
      return fixture;
   }
}
