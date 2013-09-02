package com.processpuzzle.litest.fixture;

import com.processpuzzle.commons.event.DefaultComponentEvent;

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
