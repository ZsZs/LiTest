package com.processpuzzle.litest.testcase;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import com.processpuzzle.litest.fixture.CompositeFixture;
import com.processpuzzle.litest.testcase.FixtureStrategy;
import com.processpuzzle.litest.testcase.FixtureStrategyFactory;

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
