package hu.itkodex.litest.fixture;

public interface PersistentSharedFixture<S> extends TestFixture<S> {
   public void tearDown();
}
