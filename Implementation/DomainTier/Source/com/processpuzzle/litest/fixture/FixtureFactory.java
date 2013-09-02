package com.processpuzzle.litest.fixture;

import static org.mockito.Mockito.mock;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixtureFactory {
   private static final Logger logger = LoggerFactory.getLogger( FixtureFactory.class );
   private static FixtureFactory soleInstance = null;
   private static final Map<Class<PersistentSharedFixture<?>>, PersistentSharedFixture<?>> persistentSharedFixtures = 
      new HashMap<Class<PersistentSharedFixture<?>>, PersistentSharedFixture<?>>();
   private boolean instantiateMocks = false;
   
   public static FixtureFactory createInstance() {
      return FixtureFactory.createInstance( false );
   }
   
   public static FixtureFactory createInstance( boolean instantiateMocks ) {
      if( soleInstance == null ) {
         soleInstance = new FixtureFactory( instantiateMocks );
      }
      
      return soleInstance;
   }
   
   public static void desctroyInstance() {
      soleInstance = null;
   }
   
   public <F extends CompositeFixture<?>> F createCompositeFixture( Class<F> fixtureClass ) {
      return instantiateFixture( fixtureClass );
   }

   public <F extends TestFixture<?>> F createFixture( Class<F> fixtureClass ) {
      return instantiateFixture( fixtureClass );
   }
   
   public <F extends ImmutableSharedFixture<?>> F createImmutableSharedFixture( Class<F> fixtureClass ) {
      return createPersistentSharedFixture( fixtureClass );
//      if( immutableFixtures.get( fixtureClass ) != null ) {
//         return (F) immutableFixtures.get( fixtureClass );
//      }else {
//         F fixture = instantiateFixture( fixtureClass );
//         immutableFixtures.put( (Class<ImmutableSharedFixture<?>>) fixtureClass, fixture );
//         return fixture;
//      }
   }

   public <F extends PersistentFreshFixture<?>> F createPersistentFreshFixture( Class<F> fixtureClass ) {
      return instantiateFixture( fixtureClass );
   }

   @SuppressWarnings("unchecked")
   public <F extends PersistentSharedFixture<?>> F createPersistentSharedFixture( Class<F> fixtureClass ) {
      if( persistentSharedFixtures.get( fixtureClass ) != null ) {
         return (F) persistentSharedFixtures.get( fixtureClass );
      }else {
         F fixture = instantiateFixture( fixtureClass );
         persistentSharedFixtures.put( (Class<PersistentSharedFixture<?>>) fixtureClass, fixture );
         return fixture;
      }
   }

   public <F extends SharedFixture<?>> F createSharedFixture( Class<F> fixtureClass ) {
      return instantiateFixture( fixtureClass );
   }

   public <F extends TransientFreshFixture<S>, S> F createTransientFreshFixture( Class<F> fixtureClass ) {
      return instantiateFixture( fixtureClass );
   }

   public void instantiateMocks( boolean newValue ) {
      instantiateMocks = newValue;
   }
   
   private FixtureFactory( boolean instantiateMocks ) {
      this.instantiateMocks = instantiateMocks;
   }

   private <S, F> F instantiateFixture( Class<F> fixtureClass ) {
      logger.debug( "Instantiating fixture:'" + fixtureClass.getName() );
      
      F fixture = null;

      if( instantiateMocks ) return mock( fixtureClass );
         
      Class<?>[] parameterTypes = new Class[] {};
      Constructor<F> fixtureConstructor;
      try{
         fixtureConstructor = fixtureClass.getConstructor( parameterTypes );
         Object[] constructorArguments = {};
         fixture = fixtureConstructor.newInstance( constructorArguments );
      }catch( SecurityException e ){
         e.printStackTrace();
      }catch( NoSuchMethodException e ){
         e.printStackTrace();
      }catch( IllegalArgumentException e ){
         e.printStackTrace();
      }catch( InstantiationException e ){
         e.printStackTrace();
      }catch( IllegalAccessException e ){
         e.printStackTrace();
      }catch( InvocationTargetException e ){
         e.printStackTrace();
      }

      return fixture;
   }
}
