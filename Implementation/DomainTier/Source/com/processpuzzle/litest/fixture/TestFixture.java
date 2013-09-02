package com.processpuzzle.litest.fixture;

public interface TestFixture<S> {
   public void setUp();
   public <D> D defineDOC( Class<D> dependsOnComponent );
   public <E> void defineExpectedValueFor( String propertyName, E expectedValue );
   public S getSUT();
   public Class<S> getSUTClass();
   public Object getExpectedValueFor( String propertyName );
   public boolean isConfigured();
}
