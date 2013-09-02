package com.processpuzzle.litest.fixture;

import com.processpuzzle.commons.event.ComponentEvent;

public interface FixtureLifecycleEvent extends ComponentEvent {
   public TestFixture<?> getFixture();
}
