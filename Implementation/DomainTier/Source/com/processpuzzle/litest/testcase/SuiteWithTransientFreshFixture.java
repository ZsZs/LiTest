package com.processpuzzle.litest.testcase;

import com.processpuzzle.litest.fixture.TransientFreshFixture;

public abstract class SuiteWithTransientFreshFixture<S, F extends TransientFreshFixture<S>> extends GenericTestSuite<S, F> {
   public SuiteWithTransientFreshFixture() {
      super( null );
   }
}
