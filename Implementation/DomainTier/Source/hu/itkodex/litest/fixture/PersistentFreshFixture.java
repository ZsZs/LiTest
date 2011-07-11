package hu.itkodex.litest.fixture;

public interface PersistentFreshFixture<S> extends TestFixture<S> {
   public void tearDown();
}
