package com.processpuzzle.litest.fixture;

public interface TestFixtureConfiguration {
   public <P> void addProperty( String propertyName, P property );
   public <P> P getProperty( String propertyName, P propertyType );
}
