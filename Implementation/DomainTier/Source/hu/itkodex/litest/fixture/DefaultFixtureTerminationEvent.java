package hu.itkodex.litest.fixture;

public class DefaultFixtureTerminationEvent extends DefaultFixtureLifecycleEvent implements FixtureTerminationEvent {
   private static final String EVENT_NAME = "FixtureTermination";

   public DefaultFixtureTerminationEvent( TestFixture<?> fixture ) {
      super( EVENT_NAME, fixture );
   }
}
