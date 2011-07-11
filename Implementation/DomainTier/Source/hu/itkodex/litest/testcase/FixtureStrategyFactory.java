package hu.itkodex.litest.testcase;

import hu.itkodex.litest.fixture.CompositeFixture;
import hu.itkodex.litest.fixture.PersistentFreshFixture;
import hu.itkodex.litest.fixture.PersistentSharedFixture;
import hu.itkodex.litest.fixture.TestFixture;
import hu.itkodex.litest.fixture.TransientFreshFixture;

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

   @SuppressWarnings("unchecked")
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
