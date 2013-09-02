package com.processpuzzle.litest.fixture;

import static org.mockito.Mockito.mock;


import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.processpuzzle.commons.generics.GenericTypeParameterInvestigator;

public abstract class GenericTestFixture<S> implements TestFixture<S> {
   protected static final Logger logger = LoggerFactory.getLogger( GenericTestFixture.class );
   protected S sut;
   protected Class<S> sutClass;
   protected Map<Class<?>, Object> DOCs = new HashMap<Class<?>, Object>();
   protected Map<String, Object> expectedValues = new HashMap<String, Object>();
   protected TestFixture<?> nextFixture;
   protected boolean isConfigured = false;
   
   @SuppressWarnings("unchecked")
   protected GenericTestFixture() {
      this.sutClass = (Class<S>) GenericTypeParameterInvestigator.getTypeParameter( this.getClass(), 0 );
      logger.debug( MessageFormat.format( "Test fixture was created for SUT: ''{0}''.", new Object[] {sutClass} ) );
   }
   
   public void setUp() {
      logger.trace( MessageFormat.format( "Setting up fixture: ''{0}'' started.", new Object[] {this.getClass()} ) );
      configureBeforeSutInstantiation();
      sut = instantiateSUT();
      configureAfterSutInstantiation();
      isConfigured = true;
   }
   
   public void tearDown() {
      logger.trace( MessageFormat.format( "Tearing down fixture: ''{0}'' started.", new Object[] {this.getClass()} ) );
      releaseResources();
      sut = null;
      isConfigured = false;
   }
   
   public <D> D defineDOC( Class<D> dependsOnComponentClass ) {
      D mockedComponent = mock( dependsOnComponentClass );
      DOCs.put( dependsOnComponentClass, mockedComponent );
      return mockedComponent;
   }
   
   public <E> void defineExpectedValueFor( String propertyName, E expectedValue ) {
      expectedValues.put( propertyName, expectedValue );
   }

   //Properties
   public Object getExpectedValueFor( String propertyName ) { return expectedValues.get( propertyName ); }
   public S getSUT() { return sut; }
   public Class<S> getSUTClass() { return sutClass; }
   public boolean isConfigured() { return isConfigured; }
   
   //Protected, private helper methods
   protected abstract void configureBeforeSutInstantiation();
   protected abstract void configureAfterSutInstantiation();
   protected abstract S instantiateSUT();
   protected abstract void releaseResources();
}
