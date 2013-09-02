package com.processpuzzle.litest.testcase;


import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.processpuzzle.litest.fixture.CompositeFixture;
import com.processpuzzle.litest.fixture.FixtureLifecycleEvent;
import com.processpuzzle.litest.fixture.TestFixture;

public class MixedFixtureStrategy<F extends CompositeFixture<?>> extends FixtureStrategy<F> {
   protected Map<Class<? extends TestFixture<?>>, FixtureStrategy<?>> strategies = Maps.newLinkedHashMap();
   
   MixedFixtureStrategy( Class<F> fixtureClass, ObjectTestSuite<?, F> testSuite ) {
      super( fixtureClass, testSuite );
      fixture = instantiateFixture();
      instantiateStrategies( testSuite );
   }

   @Override
   void afterAllTests() {
      for( Map.Entry<Class<? extends TestFixture<?>>, FixtureStrategy<?>> entry : strategies.entrySet() ) {
         FixtureStrategy<?> strategy = entry.getValue();
         strategy.afterAllTests();
      }
      fixture.tearDown();
   }

   @Override
   void afterEachTest() {
      for( Map.Entry<Class<? extends TestFixture<?>>, FixtureStrategy<?>> entry : strategies.entrySet() ) {
         FixtureStrategy<?> strategy = entry.getValue();
         strategy.afterEachTest();
      }
   }

   @Override
   void beforeAllTests() {
      for( Map.Entry<Class<? extends TestFixture<?>>, FixtureStrategy<?>> entry : strategies.entrySet() ) {
         FixtureStrategy<?> strategy = entry.getValue();
         strategy.beforeAllTests();
      }
      //fixture.setUp();
   }

   @Override
   void beforeEachTest() {
      for( Map.Entry<Class<? extends TestFixture<?>>, FixtureStrategy<?>> entry : strategies.entrySet() ) {
         FixtureStrategy<?> strategy = entry.getValue();
         strategy.beforeEachTest();
      }
      fixture.setUp();
   }

   public FixtureStrategy<?> getStrategy( Class<? extends TestFixture<?>> fixtureClass ) {
      return strategies.get( fixtureClass );
   }

   public Map<Class<? extends TestFixture<?>>, FixtureStrategy<?>> getStrategies() {
      return ImmutableMap.copyOf( strategies );
   }

   private void instantiateStrategies( ObjectTestSuite<?, F> testSuite ) {
      FixtureStrategyFactory factory = FixtureStrategyFactory.createInstance();
      
      for( Class<? extends TestFixture<?>> fixtureClass : fixture.getComponentTypes() ) {
         FixtureStrategy<?> strategy = factory.createFixtureStrategy( fixtureClass, testSuite );
         strategy.subscribeToEvent( fixture, FixtureLifecycleEvent.class );
         strategies.put( fixtureClass, strategy );
      }
   }
}
