package com.processpuzzle.litest.fixture;

public class DefaultFixtureInstantiationEvent extends DefaultFixtureLifecycleEvent implements FixtureInstantiationEvent {
   private static final String EVENT_NAME = "FixtureInstantiation";
   
   public DefaultFixtureInstantiationEvent( TestFixture<?> fixture ) {
      super( EVENT_NAME, fixture );
   }
}
