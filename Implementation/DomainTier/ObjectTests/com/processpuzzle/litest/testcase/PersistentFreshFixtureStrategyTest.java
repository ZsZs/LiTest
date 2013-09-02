package com.processpuzzle.litest.testcase;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import com.processpuzzle.litest.fixture.TestPersistentFreshFixture;
import com.processpuzzle.litest.testcase.NoSuchFixtureDefinitionException;
import com.processpuzzle.litest.testcase.ObjectTestSuite;
import com.processpuzzle.litest.testcase.PersistentFreshFixtureStrategy;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class PersistentFreshFixtureStrategyTest extends FreshFixtureStrategyTest<PersistentFreshFixtureStrategy<TestPersistentFreshFixture>>{
   protected ObjectTestSuite<?, TestPersistentFreshFixture> testSuite;
   
   @Before
   public void beforeEachTest() throws NoSuchFixtureDefinitionException {
      testSuite = mock( MockSuiteWithPersistentFreshFixture.class );
      when( testSuite.acquireFixture( TestPersistentFreshFixture.class ) ).thenReturn( new TestPersistentFreshFixture() );
      
      strategy = (PersistentFreshFixtureStrategy<TestPersistentFreshFixture>) factory.createFixtureStrategy( TestPersistentFreshFixture.class, testSuite );
   }

   @Test
   public void afterEachTest_TearsDownFixture() {
      strategy.beforeAllTests();
      strategy.beforeEachTest();
      TestPersistentFreshFixture spyFixture = spy( strategy.fixture );  
      strategy.fixture = spyFixture;
      strategy.afterEachTest();
      
      verify( spyFixture ).tearDown();
      assertThat( strategy.getFixture(), nullValue() );
   }
}
