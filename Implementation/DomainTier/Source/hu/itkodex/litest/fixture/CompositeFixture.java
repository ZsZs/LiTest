package hu.itkodex.litest.fixture;

import hu.itkodex.commons.event.ComponentEventSubscriber;

import java.util.List;

public interface CompositeFixture<S> extends TestFixture<S>, ComponentEventSubscriber<FixtureLifecycleEvent> {
   public <F extends TestFixture<?>> F getFixture( Class<F> fixtureClass );
   public List<TestFixture<?>> getComponents();
   public List<Class<? extends TestFixture<?>>> getComponentTypes();
   public void tearDown();
}
