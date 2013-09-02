package com.processpuzzle.litest.fixture;

public abstract class GenericSharedFixture<S> implements SharedFixture.SharedFixtureInterface<S> {
   private boolean isConfigured = false;

   public void setUp() {
      isConfigured = true;
   }

   public void tearDown() {
      isConfigured = false;
   }
   
   public boolean isConfigured() { return isConfigured; }
}
