package com.processpuzzle.litest.testcase;


import java.util.HashSet;
import java.util.Set;

import com.processpuzzle.commons.event.ComponentEventPublisher;
import com.processpuzzle.commons.event.ComponentEventSubscriber;
import com.processpuzzle.litest.fixture.DefaultFixtureInstantiationEvent;
import com.processpuzzle.litest.fixture.DefaultFixtureTerminationEvent;
import com.processpuzzle.litest.fixture.FixtureFactory;
import com.processpuzzle.litest.fixture.FixtureInstantiationEvent;
import com.processpuzzle.litest.fixture.FixtureLifecycleEvent;
import com.processpuzzle.litest.fixture.FixtureTerminationEvent;
import com.processpuzzle.litest.fixture.TestFixture;

public abstract class FixtureStrategy<F extends TestFixture<?>> implements ComponentEventPublisher<FixtureLifecycleEvent> {
   protected F fixture;
   protected Class<F> fixtureClass;
   protected ObjectTestSuite<?, F> testSuite;
   protected Set<ComponentEventSubscriber<FixtureLifecycleEvent>> fixtureLifecycleSubscribers = new HashSet<ComponentEventSubscriber<FixtureLifecycleEvent>>();
   private FixtureLifecycleEvent lastEvent;
   
   FixtureStrategy( Class<F> fixtureClass, ObjectTestSuite<?, F> testSuite ) {
      this.fixtureClass = fixtureClass;
      this.testSuite = testSuite;
   }
   
   abstract void afterAllTests();
   abstract void afterEachTest();
   abstract void beforeAllTests();
   abstract void beforeEachTest();
   
   @Override
   public void subscribeToEvent( ComponentEventSubscriber<FixtureLifecycleEvent> subscriber, Class<FixtureLifecycleEvent> eventClass ) {
      fixtureLifecycleSubscribers.add( subscriber );
   }
   
   //Properties
   public FixtureLifecycleEvent getLastEvent() { return lastEvent; }

   public F getFixture() { return fixture; }
   
   @Override
   public Set<ComponentEventSubscriber<FixtureLifecycleEvent>> getSubscribers() {
      return fixtureLifecycleSubscribers;
   }

   //Protected, private helper methods
   protected F instantiateFixture() {
      try{
         fixture = testSuite.acquireFixture( fixtureClass );
      }catch( NoSuchFixtureDefinitionException e ){
         fixture = FixtureFactory.createInstance().createFixture( fixtureClass );
      }catch( NoFixtureContainerException e ){
         fixture = FixtureFactory.createInstance().createFixture( fixtureClass );
      }
      
      notifySubscribers( fixture, FixtureInstantiationEvent.class );
      return fixture;
   }
   
   protected void terminateFixture() {
      notifySubscribers( fixture, FixtureTerminationEvent.class );
      fixture = null;
   }
   
   private void notifySubscribers( F fixture, Class<? extends FixtureLifecycleEvent> eventType ) {
      if( eventType.equals( FixtureInstantiationEvent.class ))
         lastEvent = new DefaultFixtureInstantiationEvent( fixture );
      else
         lastEvent = new DefaultFixtureTerminationEvent( fixture );
      
      for( ComponentEventSubscriber<FixtureLifecycleEvent> subscriber : fixtureLifecycleSubscribers ) {
         subscriber.notifyOnEvent( lastEvent );
      }
   }
}
