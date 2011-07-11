package hu.itkodex.litest.fixture;

import hu.itkodex.commons.event.ComponentEvent;

public interface FixtureLifecycleEvent extends ComponentEvent {
   public TestFixture<?> getFixture();
}
