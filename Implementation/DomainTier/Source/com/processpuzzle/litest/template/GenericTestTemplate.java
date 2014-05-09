package com.processpuzzle.litest.template;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.processpuzzle.commons.generics.GenericTypeParameterInvestigator;
import com.processpuzzle.litest.fixture.TestFixture;
import com.processpuzzle.litest.testcase.GenericTestSuite;
import com.processpuzzle.litest.testcase.MixedFixtureStrategy;
import com.processpuzzle.litest.testcase.NoSuchFixtureDefinitionException;

public abstract class GenericTestTemplate<S, F extends GenericTemplatedFixture<S>, E extends GenericTestEnvironment<S>> extends GenericTestSuite<S, F> {
   protected E testEnvironment;
   protected Class<E> testEnvironmentClass;
   protected F templatedFixture;
   protected Class<F> templatedFixtureClass;

   @SuppressWarnings("unchecked")
   protected GenericTestTemplate( String fixtureContainerConfigurationPath, Class<? extends TestFixture<?>> testEnvironmentClass ) {
      super( fixtureContainerConfigurationPath, testEnvironmentClass );
      templatedFixtureClass = (Class<F>) GenericTypeParameterInvestigator.getTypeParameter( getClass(), 1 );
      //this.testEnvironmentClass = (Class<E>) GenericTypeParameterInvestigator.getTypeParameter( getClass(), 2 );
   }

   @SuppressWarnings( "unchecked" ) @Override
   public <B extends TestFixture<?>> B acquireFixture( Class<B> requiredType ) throws NoSuchFixtureDefinitionException {
      if( requiredType.equals( fixtureClass )) return (B) instantiateTestEnvironment( fixtureClass );
      else if( requiredType.equals( templatedFixtureClass )) return (B) instantiateTestFixture();
      else return super.acquireFixture( requiredType );
   }

   @SuppressWarnings("unchecked") @Override
   public void beforeEachTest() {
      super.beforeEachTest();
      templatedFixture = (F) ((MixedFixtureStrategy<?>)fixtureStrategy).getStrategy( templatedFixtureClass ).getFixture();
   }

   @SuppressWarnings("unchecked")
   protected E instantiateTestEnvironment( Class<?> testEnvironmentClass ) {
      Constructor<E> environmentConstructor;
      Class<?>[] argumentClasses = { Class.class }; 
      Class<F> templatedFixtureClass = (Class<F>) GenericTypeParameterInvestigator.getTypeParameter( getClass(), 1 );
      Object[] arguments = { templatedFixtureClass };
      
      try{
         environmentConstructor = (Constructor<E>) testEnvironmentClass.getConstructor( argumentClasses );
         testEnvironment = environmentConstructor.newInstance( arguments );
      }catch( SecurityException e ){ e.printStackTrace();
      }catch( NoSuchMethodException e ){ e.printStackTrace();
      }catch( IllegalArgumentException e ){ e.printStackTrace();
      }catch( InstantiationException e ){ e.printStackTrace();
      }catch( IllegalAccessException e ){ e.printStackTrace();
      }catch( InvocationTargetException e ){ e.printStackTrace(); }
      
      return testEnvironment;
   }
   
   protected F instantiateTestFixture() {
      F fixture = null;
      Constructor<F> fixtureConstructor;
      Class<?>[] argumentClasses = { fixtureClass };
      Object[] arguments = { testEnvironment };

      try{
         fixtureConstructor = templatedFixtureClass.getConstructor( argumentClasses );
         fixture = fixtureConstructor.newInstance( arguments );
      }catch( IllegalArgumentException e ){
         e.printStackTrace();
      }catch( SecurityException e ){
         e.printStackTrace();
      }catch( NoSuchMethodException e ){
         e.printStackTrace();
      }catch( InstantiationException e ){
         e.printStackTrace();
      }catch( IllegalAccessException e ){
         e.printStackTrace();
      }catch( InvocationTargetException e ){
         e.printStackTrace();
      }
      
      return fixture;
   }
}
