package hu.itkodex.litest.testcase;

import hu.itkodex.commons.event.ComponentEventPublisher;
import hu.itkodex.commons.event.ComponentEventSubscriber;
import hu.itkodex.litest.fixture.DefaultFixtureInstantiationEvent;
import hu.itkodex.litest.fixture.DefaultFixtureTerminationEvent;
import hu.itkodex.litest.fixture.FixtureFactory;
import hu.itkodex.litest.fixture.FixtureInstantiationEvent;
import hu.itkodex.litest.fixture.FixtureLifecycleEvent;
import hu.itkodex.litest.fixture.FixtureTerminationEvent;
import hu.itkodex.litest.fixture.TestFixture;

import java.util.HashSet;
import java.util.Set;

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
