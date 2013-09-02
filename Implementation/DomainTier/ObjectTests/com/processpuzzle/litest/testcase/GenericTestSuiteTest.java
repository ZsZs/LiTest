package com.processpuzzle.litest.testcase;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.processpuzzle.litest.fixture.TestPersistentSharedFixture;
import com.processpuzzle.litest.fixture.TestTransientFreshFixture;
import com.processpuzzle.litest.testcase.GenericTestSuite;
import com.processpuzzle.litest.testcase.NoSuchFixtureDefinitionException;

public class GenericTestSuiteTest {
   private static final String containerConfigurationPath = "classpath:hu/itkodex/litest/testcase/fixtures.xml";
   private static final String FIXTURE_BEAN_NAME = "testTransientFreshFixture";
   private static final String NONEXISTING_FIXTURE_BEAN_NAME = "blabla";
   private MockSuiteWitPersistentSharedFixture testSuite;
   
   @BeforeClass
   public static void beforeAllTest() {
   }
   
   @Before
   public void beforeEachTests() {
      testSuite = new MockSuiteWitPersistentSharedFixture( containerConfigurationPath );
      testSuite.fixtureStrategy = spy( testSuite.getFixtureStrategy() ); 
   }

   @Test
   public void instantiation_InstantiatesFixtureStrategy() {
      assertThat( testSuite.getFixtureStrategy(), notNullValue() );
      assertThat( testSuite.getFixture(), nullValue() );
      
      assertThat( GenericTestSuite.fixtureContainer, notNullValue() );
   }
   
   @Test public void acquireFixture_retrievesFromFixtureContainer() throws NoSuchFixtureDefinitionException {
      assertThat( testSuite.acquireFixture( FIXTURE_BEAN_NAME, TestTransientFreshFixture.class ), notNullValue() );
   }

   @Test( expected = NoSuchFixtureDefinitionException.class )
   public void acquireFixture_throwsException() throws NoSuchFixtureDefinitionException {
      testSuite.acquireFixture( NONEXISTING_FIXTURE_BEAN_NAME, TestPersistentSharedFixture.class );
   }
   
   @Test
   public void beforeEachTests_Calls_BeforeAllTests() {
      testSuite.beforeEachTest();
      
      assertThat( testSuite.getFixture(), notNullValue() );
      assertThat( testSuite.getFixture().isConfigured(), is( true ));
      verify( testSuite.getFixtureStrategy() ).beforeAllTests();
   }
   
   @Test
   public void afterEachTests_Calls_AfterEchTest() {
      testSuite.beforeEachTest();
      testSuite.afterEachTest();
      
      verify( testSuite.getFixtureStrategy() ).afterEachTest();
   }
   
   @Test
   public void subsequent_beforeEachTests_CallsJust_BeforeEachTest() {
      testSuite.beforeEachTest();
      testSuite.afterEachTest();
      
      testSuite.beforeEachTest();
      testSuite.afterEachTest();
      
      verify( testSuite.getFixtureStrategy(), times(1) ).beforeAllTests();
      verify( testSuite.getFixtureStrategy(), times(2) ).beforeEachTest();
      verify( testSuite.getFixtureStrategy(), times(2) ).afterEachTest();
      verify( testSuite.getFixtureStrategy(), never() ).afterAllTests();
   }
   
   @Test
   public void afterAllTests_Calls_AfterAllTests() {
      testSuite.beforeAllTests();
      testSuite.afterAllTests();
      
      verify( testSuite.getFixtureStrategy() ).afterAllTests();
   }
   
   @After
   public void afterEachTests() {
      GenericTestSuite.firstBeforeEachTestCall = false;
   }
}
