package com.processpuzzle.litest.testcase;

import com.processpuzzle.litest.fixture.CompositeFixture;
import com.processpuzzle.litest.fixture.PersistentFreshFixture;
import com.processpuzzle.litest.fixture.PersistentSharedFixture;
import com.processpuzzle.litest.fixture.TestFixture;
import com.processpuzzle.litest.fixture.TransientFreshFixture;

public class FixtureStrategyFactory {
   private static FixtureStrategyFactory soleInstance = null;

   public static FixtureStrategyFactory createInstance() {
      if( soleInstance == null ) {
         soleInstance = new FixtureStrategyFactory();
      }
      return soleInstance;
   }
   
   public static void destroyInstance() {
      soleInstance = null;
   }

   @SuppressWarnings({ "unchecked", "rawtypes" })
   public <F extends TestFixture<?>> FixtureStrategy<F> createFixtureStrategy( Class<? extends TestFixture<?>> fixtureClass, ObjectTestSuite<?, F> testSuite ) {
      FixtureStrategy<F> strategy = null;

      if( TransientFreshFixture.class.isAssignableFrom( fixtureClass )) {
         strategy = new TransientFreshFixtureStrategy( fixtureClass, testSuite );
      }else if( PersistentFreshFixture.class.isAssignableFrom( fixtureClass )) {
         strategy = new PersistentFreshFixtureStrategy( fixtureClass, testSuite );
      }else if( PersistentSharedFixture.class.isAssignableFrom( fixtureClass )) {
         strategy = new PersistentSharedFixtureStrategy( fixtureClass, testSuite );
      }else if( CompositeFixture.class.isAssignableFrom( fixtureClass )) {
         strategy = new MixedFixtureStrategy( fixtureClass, testSuite );
      }else throw new UndeterminableFixtureStrategy( fixtureClass );
      
      return strategy;
   }

   private FixtureStrategyFactory() {
   }
}
