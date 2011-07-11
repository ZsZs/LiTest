package hu.itkodex.litest.testcase;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import hu.itkodex.litest.fixture.CompositeFixture;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

public abstract class FreshFixtureStrategyTest<R extends FixtureStrategy<?>> {
   protected static FixtureStrategyFactory factory;
   protected R strategy;
   @Mock protected CompositeFixture<?> fixtureLifecycEventSubscriber;

   @BeforeClass
   public static void beforeAllTests() {
      factory = FixtureStrategyFactory.createInstance();
   }
   
   @Before
   public void beforeEachTest() throws Exception {
      fixtureLifecycEventSubscriber = mock( CompositeFixture.class );
   }

   @Test
   public void instantiation_Does_Nothing() {
      assertThat( strategy.getFixture(), nullValue() );
   }

}
