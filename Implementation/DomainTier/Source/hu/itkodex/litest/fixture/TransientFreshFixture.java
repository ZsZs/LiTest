package hu.itkodex.litest.fixture;

public interface TransientFreshFixture<S> extends TestFixture<S> {
   public void tearDown();
}
