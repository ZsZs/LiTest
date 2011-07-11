package hu.itkodex.litest.fixture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GenericCompositeFixture<S> extends GenericTestFixture<S> implements CompositeFixture<S> {
   private Map<Class<? extends TestFixture<?>>, TestFixture<?>> components = new HashMap<Class<? extends TestFixture<?>>, TestFixture<?>>();
   protected List<Class<? extends TestFixture<?>>> componentTypes = new ArrayList<Class<? extends TestFixture<?>>>();
   private boolean isInitialized = false;
   
   public GenericCompositeFixture() {
      super();
   }
   
   @SuppressWarnings( "unchecked" )
   @Override
   public void notifyOnEvent( FixtureLifecycleEvent event ) {
      lazyInitialization();
      
      if( event instanceof FixtureInstantiationEvent ) {
         if( !components.containsKey( event.getFixture().getClass() )) {
            components.put( (Class<? extends TestFixture<?>>) event.getFixture().getClass(), event.getFixture() );
         }
      }else if( event instanceof FixtureTerminationEvent ){
         components.remove( event.getFixture().getClass() );
      }
   }

   //Properties
   @Override
   public List<TestFixture<?>> getComponents() {
      lazyInitialization();
      
      List<TestFixture<?>> componentFixtures = new ArrayList<TestFixture<?>>();
      for( Map.Entry<Class<? extends TestFixture<?>>, TestFixture<?>> componentsEntry : components.entrySet() ) {
         componentFixtures.add( componentsEntry.getValue() );
      }
      return componentFixtures;
   }
   
   @Override
   public List<Class<? extends TestFixture<?>>> getComponentTypes() {
      lazyInitialization();
      
      return componentTypes;
   }

   @SuppressWarnings("unchecked")
   @Override
   public <F extends TestFixture<?>> F getFixture( Class<F> fixtureClass ) {
      lazyInitialization();
      
      return (F) components.get( fixtureClass );
   }

   //Protected, private helper methods
   protected abstract void defineComponentTypes();

   @SuppressWarnings("unchecked")
   @Override protected S instantiateSUT() {
      lazyInitialization();
      
      for( Map.Entry<Class<? extends TestFixture<?>>, TestFixture<?>> componentsEntry : components.entrySet() ) {
         if( componentsEntry.getValue().getSUTClass().equals( sutClass ) ) {
            sut = (S) componentsEntry.getValue().getSUT();
            if( sut != null ) return sut;
         }
      }
      return null;
   }
   
   protected void lazyInitialization() {
      if( !isInitialized ) {
         defineComponentTypes();
      }
   }
}
