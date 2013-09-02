package com.processpuzzle.litest.fixture;

import java.util.HashMap;
import java.util.Map;

public class GenericTestFixtureConfiguration implements TestFixtureConfiguration {
   private Map<String, Object> configurationProperties = new HashMap<String, Object>();

   public <P> void addProperty( String propertyName, P property ) {
      configurationProperties.put( propertyName, property );
   }
   
   @SuppressWarnings("unchecked")
   public <P> P getProperty( String propertyName, P propertyType ) {
      return (P) configurationProperties.get( propertyName );
   }
}
