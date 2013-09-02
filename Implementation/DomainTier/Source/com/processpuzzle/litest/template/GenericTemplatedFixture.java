package com.processpuzzle.litest.template;

import com.processpuzzle.litest.fixture.GenericTestFixture;
import com.processpuzzle.litest.fixture.TransientFreshFixture;

public abstract class GenericTemplatedFixture<S> extends GenericTestFixture<S> implements TransientFreshFixture<S> {
   protected GenericTestEnvironment<S> testEnvironment;

   protected GenericTemplatedFixture( GenericTestEnvironment<S> testEnvironment ) {
      this.testEnvironment = testEnvironment;
   }
}
