package hu.itkodex.litest.template;

import hu.itkodex.litest.fixture.GenericTestFixture;
import hu.itkodex.litest.fixture.TransientFreshFixture;

public abstract class GenericTemplatedFixture<S> extends GenericTestFixture<S> implements TransientFreshFixture<S> {
   protected GenericTestEnvironment<S> testEnvironment;

   protected GenericTemplatedFixture( GenericTestEnvironment<S> testEnvironment ) {
      this.testEnvironment = testEnvironment;
   }
}
