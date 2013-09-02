package com.processpuzzle.litest.testcase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;

import java.util.List;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.processpuzzle.litest.fixture.CompositeFixture;
import com.processpuzzle.litest.fixture.GenericCompositeFixture;
import com.processpuzzle.litest.fixture.PersistentSharedFixture;
import com.processpuzzle.litest.fixture.TestCompositeFixture;
import com.processpuzzle.litest.fixture.TestFixture;
import com.processpuzzle.litest.fixture.TestPersistentSharedFixture;
import com.processpuzzle.litest.fixture.TestTransientFreshFixture;
import com.processpuzzle.litest.fixture.TransientFreshFixture;
import com.processpuzzle.litest.testcase.FixtureStrategyFactory;
import com.processpuzzle.litest.testcase.GenericTestSuite;
import com.processpuzzle.litest.testcase.MixedFixtureStrategy;
import com.processpuzzle.litest.testcase.NoSuchFixtureDefinitionException;
import com.processpuzzle.litest.testcase.ObjectTestSuite;
import com.processpuzzle.litest.testcase.PersistentSharedFixtureStrategy;
import com.processpuzzle.litest.testcase.TransientFreshFixtureStrategy;

public class MixedFixtureStrategyTest {
   private ObjectTestSuite<?, TestCompositeFixture> testSuite;
   private MixedFixtureStrategy<TestCompositeFixture> fixtureStrategy;
   private PersistentSharedFixtureStrategy<PersistentSharedFixture<?>> spyPartOne;
   private TransientFreshFixtureStrategy<TransientFreshFixture<?>> spyPartTwo;
   
   @BeforeClass
   public static void beforeAllTests() {
      FixtureStrategyFactory.createInstance();
   }
   
   @Before
   public void beforeEachTests() throws NoSuchFixtureDefinitionException {
      setUpCompositeFixture();
      setUpTestSuite();
      setUpMixedFixtureStrategy();
   }

   @Test
   public void constructor_InstantiatesCompositeFixture() {
      MixedFixtureStrategy<TestCompositeFixture> aMixedFixtureStrategy = new MixedFixtureStrategy<TestCompositeFixture>( TestCompositeFixture.class, testSuite );

      assertThat( aMixedFixtureStrategy.getFixture(), notNullValue() );
      assertThat( aMixedFixtureStrategy.getFixture(), instanceOf( CompositeFixture.class ));
   }
   
   @Test
   public void constructor_InstantiatesComponentStrategiesAccordingToCompositeFixture() {
      MixedFixtureStrategy<TestCompositeFixture> testFixtureStrategyForTestComposite = new MixedFixtureStrategy<TestCompositeFixture>( TestCompositeFixture.class, testSuite );
      
      assertThat( testFixtureStrategyForTestComposite.getStrategies(), hasKey( TestPersistentSharedFixture.class ));
      assertThat( testFixtureStrategyForTestComposite.getStrategy( TestPersistentSharedFixture.class ), instanceOf( PersistentSharedFixtureStrategy.class ) );
      
      assertThat( testFixtureStrategyForTestComposite.getStrategies(), hasKey( TestTransientFreshFixture.class ));
      assertThat( testFixtureStrategyForTestComposite.getStrategy( TestTransientFreshFixture.class ), instanceOf( TransientFreshFixtureStrategy.class ));
   }
   
   @Test
   public void constructor_RegistersCompositeFixturesAsSubscriber() {
      MixedFixtureStrategy<TestCompositeFixture> testFixtureStrategyForTestComposite = new MixedFixtureStrategy<TestCompositeFixture>( TestCompositeFixture.class, testSuite );
      
      assertThat( testFixtureStrategyForTestComposite.getStrategy( TestPersistentSharedFixture.class ).getSubscribers(), hasItem( testFixtureStrategyForTestComposite.getFixture() ));
      assertThat( testFixtureStrategyForTestComposite.getStrategy( TestTransientFreshFixture.class ).getSubscribers(), hasItem( testFixtureStrategyForTestComposite.getFixture() ));
   }
   
   @Test
   public void beforeAllTests_DelegatesResposibilityToParts() {
      fixtureStrategy.beforeAllTests();
      
      verify( spyPartOne ).beforeAllTests();
      verify( spyPartTwo ).beforeAllTests();
   }
   
   @Test
   public void beforeEachTest_DelegatesResposibilityToParts() {
      fixtureStrategy.beforeAllTests();
      fixtureStrategy.beforeEachTest();
      
      verify( spyPartOne ).beforeEachTest();
      verify( spyPartTwo ).beforeEachTest();
   }

   @Test
   public void afterEachTest_DelegatesResposibilityToParts() {
      fixtureStrategy.beforeAllTests();
      fixtureStrategy.beforeEachTest();
      fixtureStrategy.afterEachTest();
      
      verify( spyPartOne ).afterEachTest();
      verify( spyPartTwo ).afterEachTest();
   }
   
   @Test
   public void afterAllTests_DelegatesResposibilityToParts() {
      fixtureStrategy.beforeAllTests();
      fixtureStrategy.beforeEachTest();
      fixtureStrategy.afterEachTest();
      fixtureStrategy.afterAllTests();
      
      verify( spyPartOne ).afterAllTests();
      verify( spyPartTwo ).afterAllTests();
   }
   
   @AfterClass
   public static void afterAllTests() {
      FixtureStrategyFactory.destroyInstance();
   }

   //Protected, private helper methods
   @SuppressWarnings("unchecked")
   private void setUpCompositeFixture() {
      TransientFreshFixture mockTransientFreshFixture;
      PersistentSharedFixture mockPersistentSharedFixture;
      CompositeFixture mockCompositeFixture;
      
      mockTransientFreshFixture = mock( TransientFreshFixture.class );
      mockPersistentSharedFixture = mock( PersistentSharedFixture.class );
      mockCompositeFixture = mock( GenericCompositeFixture.class );
      List<Class<? extends TestFixture>> componentTypes = ImmutableList.of( TransientFreshFixture.class, PersistentSharedFixture.class );
      when( mockCompositeFixture.getComponentTypes() ).thenReturn( componentTypes );
      when( mockCompositeFixture.getFixture( TransientFreshFixture.class )).thenReturn( mockTransientFreshFixture );
      when( mockCompositeFixture.getFixture( PersistentSharedFixture.class )).thenReturn( mockPersistentSharedFixture );
   }

   @SuppressWarnings("unchecked")
   private void setUpMixedFixtureStrategy() {
      PersistentSharedFixtureStrategy<PersistentSharedFixture<?>> componentStrategyOne;
      TransientFreshFixtureStrategy<TransientFreshFixture<?>> componentStrategyTwo;
      
      fixtureStrategy = new MixedFixtureStrategy<TestCompositeFixture>( TestCompositeFixture.class, testSuite );
      componentStrategyOne = (PersistentSharedFixtureStrategy<PersistentSharedFixture<?>>) fixtureStrategy.getStrategy( TestPersistentSharedFixture.class );
      spyPartOne = spy( componentStrategyOne );
      fixtureStrategy.strategies.put( TestPersistentSharedFixture.class, spyPartOne );
      
      componentStrategyTwo = (TransientFreshFixtureStrategy<TransientFreshFixture<?>>) fixtureStrategy.getStrategy( TestTransientFreshFixture.class );
      spyPartTwo = spy( componentStrategyTwo );
      fixtureStrategy.strategies.put( TestTransientFreshFixture.class, spyPartTwo );
   }

   @SuppressWarnings("unchecked")
   private void setUpTestSuite() throws NoSuchFixtureDefinitionException {
      testSuite = mock( GenericTestSuite.class );
      when( testSuite.acquireFixture( TestCompositeFixture.class ) ).thenReturn( new TestCompositeFixture() );
      when( testSuite.acquireFixture( TestTransientFreshFixture.class ) ).thenReturn( new TestTransientFreshFixture() );
      when( testSuite.acquireFixture( TestPersistentSharedFixture.class ) ).thenReturn( new TestPersistentSharedFixture() );
   }
}
