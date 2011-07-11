package hu.itkodex.litest.fixture;

public class SharedFixture<S> {
   private static SharedFixtureInterface<?> soleInstance;
   
   @SuppressWarnings("unchecked")
   public static <F extends SharedFixture> F getInstance( Class<F> fixtureClass ) {
      if( soleInstance == null ) throw new UnconfiguredSharedFixtureException();
      else return (F) soleInstance;
   }
   
   @SuppressWarnings("unchecked")
   public static <F extends SharedFixtureInterface> F createInstance( Class<F> fixtureClass ) {
      try {
         soleInstance = (SharedFixtureInterface) fixtureClass.newInstance();
      } catch (InstantiationException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IllegalAccessException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return (F) soleInstance;
   }
   
   public static interface SharedFixtureInterface<S> extends TestFixture<S> {
      public void tearDown();
   }
}
