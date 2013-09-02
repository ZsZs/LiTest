package com.processpuzzle.litest.testcase;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.processpuzzle.litest.fixture.CompositeFixture;
import com.processpuzzle.litest.fixture.FixtureInstantiationEvent;
import com.processpuzzle.litest.fixture.FixtureLifecycleEvent;
import com.processpuzzle.litest.fixture.FixtureTerminationEvent;
import com.processpuzzle.litest.fixture.TestPersistentSharedFixture;
import com.processpuzzle.litest.testcase.NoSuchFixtureDefinitionException;
import com.processpuzzle.litest.testcase.ObjectTestSuite;
import com.processpuzzle.litest.testcase.PersistentSharedFixtureStrategy;

public class PersistentSharedFixtureStrategyTest {
   private ObjectTestSuite<?, TestPersistentSharedFixture> testSuite;
   private PersistentSharedFixtureStrategy<TestPersistentSharedFixture> fixtureStrategy;
   @Mock protected CompositeFixture<?> fixtureLifecycEventSubscriber;

   @Before
   public void beforeEachTest() throws NoSuchFixtureDefinitionException {
      testSuite = mock( MockSuiteWitPersistentSharedFixture.class );
      when( testSuite.acquireFixture( TestPersistentSharedFixture.class ) ).thenReturn( new TestPersistentSharedFixture() );
      
      instantiateNewStrategyObject();
      fixtureStrategy.beforeAllTests();
   }

   private void instantiateNewStrategyObject() {
      fixtureStrategy = new PersistentSharedFixtureStrategy<TestPersistentSharedFixture>( TestPersistentSharedFixture.class, testSuite );
      
      fixtureLifecycEventSubscriber = mock( CompositeFixture.class );
      fixtureStrategy.subscribeToEvent( fixtureLifecycEventSubscriber, FixtureLifecycleEvent.class );
   }
   
   @Test
   public void beforeAllTests_InstantiatesAndSetsUpFixture() {
      
      assertThat( fixtureStrategy.getFixture(), notNullValue() );
      assertThat( fixtureStrategy.getFixture().isConfigured(), is( true ));
      
      assertThat( fixtureStrategy.getLastEvent(), instanceOf( FixtureInstantiationEvent.class ));
      verify( fixtureLifecycEventSubscriber ).notifyOnEvent( fixtureStrategy.getLastEvent() );
   }
   
   @Test
   public void beforeEachTest_DoesNothing() {
      fixtureStrategy.beforeEachTest();

      assertThat( fixtureStrategy.getFixture(), notNullValue() );
      assertThat( fixtureStrategy.getFixture().isConfigured(), is( true ));
      
      //Now check the second instantiation of the strategy object, when beforeAllTest() is not called, just beforeEachTest().
      fixtureStrategy.afterEachTest();
      fixtureStrategy = null;
      instantiateNewStrategyObject();
      
      fixtureStrategy.beforeEachTest();
      
      assertThat( fixtureStrategy.getFixture(), notNullValue() );
      assertThat( fixtureStrategy.getFixture().isConfigured(), is( true ));
   }

   @Test
   public void afterEachTest_DoesNothing() {
      fixtureStrategy.beforeEachTest();
      fixtureStrategy.afterEachTest();
      
      assertThat( fixtureStrategy.getFixture(), notNullValue() );
      assertThat( fixtureStrategy.getFixture().isConfigured(), is( true ));
   }

   @Test
   public void afterAllTests_TearsDownFixture() {
      fixtureStrategy.beforeEachTest();
      fixtureStrategy.afterEachTest();
      fixtureStrategy.afterAllTests();
      
      assertThat( fixtureStrategy.getFixture(), nullValue() );

      assertThat( fixtureStrategy.getLastEvent(), instanceOf( FixtureTerminationEvent.class ));
      verify( fixtureLifecycEventSubscriber ).notifyOnEvent( fixtureStrategy.getLastEvent() );
   }
   
   @After public void afterEachTest() {
      fixtureStrategy = null;
   }
}
