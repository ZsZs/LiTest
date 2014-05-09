package com.processpuzzle.litest.testcase;

import java.text.MessageFormat;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.processpuzzle.commons.generics.GenericTypeParameterInvestigator;
import com.processpuzzle.commons.spring.BeanName;
import com.processpuzzle.litest.fixture.TestFixture;

public abstract class GenericTestSuite<S, F extends TestFixture<S>> implements ObjectTestSuite<S, F>{
   private static GenericTestSuite<?,?> lastInstance;
   private static final Logger logger = LoggerFactory.getLogger( GenericTestSuite.class );
   private Class<S> sutClass;
   private int testRuns = 0;
   protected static ApplicationContext fixtureContainer;
   protected static String containerConfigurationPath;
   protected static boolean firstBeforeEachTestCall = false;
   protected static FixtureStrategy<?> staticStrategy;
   protected Class<F> fixtureClass;
   protected FixtureStrategy<F> fixtureStrategy;
   protected F fixture;
   protected S sut;

   //Constructors and destructors
   protected GenericTestSuite( String fixtureContainerConfigurationPath ) {
      this( fixtureContainerConfigurationPath, null );
   }
   
   @SuppressWarnings("unchecked") protected GenericTestSuite( String fixtureContainerConfigurationPath, Class<? extends TestFixture<?>> fixtureClass ) {
      sutClass = (Class<S>) GenericTypeParameterInvestigator.getTypeParameter( this.getClass(), 0 );
      
      if( fixtureClass == null ) this.fixtureClass = (Class<F>) GenericTypeParameterInvestigator.getTypeParameter( this.getClass(), 1 );
      else this.fixtureClass = (Class<F>) fixtureClass;
      
      GenericTestSuite.containerConfigurationPath = fixtureContainerConfigurationPath;
      configureFixtureContainer();
      FixtureStrategyFactory factory = FixtureStrategyFactory.createInstance();
      fixtureStrategy = (FixtureStrategy<F>) factory.createFixtureStrategy( this.fixtureClass, this );
      
      lastInstance = this;
      logger.debug( MessageFormat.format( "Test suite was instantiated for SUT: ''{0}'' with fixture ''{1}''", new Object[] { sutClass, fixtureClass } ) );
   }
   
   //Public mutators
   public <B extends TestFixture<?>> B acquireFixture( Class<B> requiredType ) throws NoSuchFixtureDefinitionException {
      return acquireFixture( BeanName.determineBeanNameFromClass( requiredType ), requiredType );
   }
   
   public <B extends TestFixture<?>> B acquireFixture( String beanName, Class<B> requiredType ) throws NoSuchFixtureDefinitionException {
      if( fixtureContainer != null ) {
         try{
            return (B) fixtureContainer.getBean( beanName, requiredType );
         }catch( NoSuchBeanDefinitionException e ){
            throw new NoSuchFixtureDefinitionException( beanName, containerConfigurationPath, e );
         }
      }else
         throw new NoFixtureContainerException();
   }

   public void afterAllTests() { 
      logger.trace( MessageFormat.format( "{0}.afterAllTests() started to run.", new Object[] {this.getClass()} ));
      fixtureStrategy.afterAllTests();
   }
   
   @AfterClass public static void afterClass() {
      lastInstance.afterAllTests();
   }

   @After public void afterEachTest() {
      logger.trace( MessageFormat.format( "{0}.afterEachTest() started to run.", new Object[] {this.getClass()} ));
      fixtureStrategy.afterEachTest();
      fixture = fixtureStrategy.getFixture();
      if( fixture != null )
         sut = fixture.getSUT();
      else
         sut = null;
   }

   public void beforeAllTests() {
      logger.trace( MessageFormat.format( "{0}.beforeAllTests() started to run.", new Object[] {this.getClass()} ));
      fixtureStrategy.beforeAllTests();
      fixture = fixtureStrategy.getFixture();
      if( fixture != null )
         sut = fixture.getSUT();
   }
   
   @Before public void beforeEachTest() {
      if( !firstBeforeEachTestCall ) {
         beforeAllTests();
         firstBeforeEachTestCall = true;
      }
      logger.trace( MessageFormat.format( "{0}.beforeEachTest() started to run.", new Object[] {this.getClass()} ));
      fixtureStrategy.beforeEachTest();
      fixture = fixtureStrategy.getFixture();
      sut = fixture.getSUT();
   }
   
   public void executeTestCases() {
      testRuns += 1;
   }
   
   //Properties
   public F getFixture() { return fixture; }
   public Class<F> getFixtureClass() { return fixtureClass; }
   public FixtureStrategy<F> getFixtureStrategy() { return fixtureStrategy; }
   public S getSUT() { return sut; }
   public Class<S> getSUTClass() { return sutClass; }
   public int getTestRuns() { return testRuns; }

   private void configureFixtureContainer() {
      if( containerConfigurationPath != null && fixtureContainer == null )
         fixtureContainer = new ClassPathXmlApplicationContext( containerConfigurationPath );
      
      if( containerConfigurationPath == null )
         fixtureContainer = null;
   }
   
}
