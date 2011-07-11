package hu.itkodex.litest.testcase;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import hu.itkodex.litest.fixture.FixtureInstantiationEvent;
import hu.itkodex.litest.fixture.FixtureLifecycleEvent;
import hu.itkodex.litest.fixture.FixtureTerminationEvent;
import hu.itkodex.litest.fixture.TestTransientFreshFixture;

import org.junit.Before;
import org.junit.Test;

public class TransientFreshFixtureStrategyTest extends FreshFixtureStrategyTest<TransientFreshFixtureStrategy<TestTransientFreshFixture>> {
   private ObjectTestSuite<?, TestTransientFreshFixture> testSuite;
   
   @Override @Before
   public void beforeEachTest() throws Exception {
      super.beforeEachTest();
      
      testSuite = mock( MockSuiteWithTransientFreshFixture.class );
      when( testSuite.acquireFixture( TestTransientFreshFixture.class )).thenReturn( new TestTransientFreshFixture() );
      
      strategy = (TransientFreshFixtureStrategy<TestTransientFreshFixture>) factory.createFixtureStrategy( TestTransientFreshFixture.class, testSuite );
      strategy.subscribeToEvent( fixtureLifecycEventSubscriber, FixtureLifecycleEvent.class );
   }

   @Test
   public void beforeAllTests_Does_Nothing() {
      strategy.beforeAllTests();
      
      assertThat( strategy.getFixture(), nullValue() );
   }
   
   @Test
   public void beforeEachTest_InstantiatesAndSetsUpFixture() {
      strategy.beforeEachTest();
      
      assertThat( strategy.getFixture(), notNullValue() );
      assertThat( strategy.getFixture().isConfigured(), is( true ));
      
      assertThat( strategy.getLastEvent(), instanceOf( FixtureInstantiationEvent.class ));
      verify( fixtureLifecycEventSubscriber ).notifyOnEvent( strategy.getLastEvent() );
   }
   
   @Test
   public void afterEachTest_TearsDownFixture() {
      strategy.beforeEachTest();
      TestTransientFreshFixture spyFixture = spy( strategy.fixture );  
      strategy.fixture = spyFixture;
      strategy.afterEachTest();
    
      assertThat( strategy.getFixture(), nullValue() );
      verify( spyFixture ).tearDown();

      assertThat( strategy.getLastEvent(), instanceOf( FixtureTerminationEvent.class ));
      verify( fixtureLifecycEventSubscriber ).notifyOnEvent( strategy.getLastEvent() );
   }
   
   @Test
   public void afterAllTests_Does_Nothing() {
      strategy.beforeAllTests();
      strategy.afterAllTests();
      
      assertThat( strategy.getFixture(), nullValue() );
   }
}
