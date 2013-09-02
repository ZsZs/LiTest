package com.processpuzzle.litest.fixture;


import java.util.List;

import com.processpuzzle.commons.event.ComponentEventSubscriber;

public interface CompositeFixture<S> extends TestFixture<S>, ComponentEventSubscriber<FixtureLifecycleEvent> {
   public <F extends TestFixture<?>> F getFixture( Class<F> fixtureClass );
   public List<TestFixture<?>> getComponents();
   public List<Class<? extends TestFixture<?>>> getComponentTypes();
   public void tearDown();
}
