package hu.itkodex.litest.testcase;

import hu.itkodex.litest.fixture.TransientFreshFixture;

public abstract class SuiteWithTransientFreshFixture<S, F extends TransientFreshFixture<S>> extends GenericTestSuite<S, F> {
   public SuiteWithTransientFreshFixture() {
      super( null );
   }
}
