package hu.itkodex.litest.testcase;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import hu.itkodex.commons.spring.BeanName;
import hu.itkodex.litest.fixture.TestPersistentFreshFixture;
import hu.itkodex.litest.fixture.TestPersistentSharedFixture;

import org.junit.Test;

public class FixtureStrategyTest {
   private static final String containerConfigurationPath = "classpath:hu/itkodex/litest/testcase/fixtures.xml";
   private static final String FIXTURE_NAME = "testPersistentSharedFixture";
   
   
   @Test public void subscribeToEvent_ReqistersSubscriberAndEventClass() {
   }
   
   @Test public void instantiateFixture_FirstGetsFixtureFromGenericTestSuite() throws NoSuchFixtureDefinitionException {
      ObjectTestSuite<?, TestPersistentSharedFixture> testSuite = new MockSuiteWitPersistentSharedFixture( containerConfigurationPath );
      
      FixtureStrategy<TestPersistentSharedFixture> fixtureStrategy = new SpyFixtureStrategy<TestPersistentSharedFixture>( TestPersistentSharedFixture.class, testSuite );
      ((SpyFixtureStrategy<TestPersistentSharedFixture>)fixtureStrategy).invokeInstantiateFixture();
      
      assertThat( fixtureStrategy.getFixture(), sameInstance( testSuite.acquireFixture( FIXTURE_NAME, TestPersistentSharedFixture.class ) ));
   }

   @Test public void instantiateFixture_GetsFixtureFromFactoryIfNotFoundInContainer() {
      ObjectTestSuite<?, TestPersistentFreshFixture> testSuite = new MockSuiteWithPersistentFreshFixture( containerConfigurationPath );
      
      Class<TestPersistentFreshFixture> fixtureClass = TestPersistentFreshFixture.class;
      try{
         testSuite.acquireFixture( BeanName.determineBeanNameFromClass( fixtureClass ), fixtureClass );
      }catch( NoSuchFixtureDefinitionException e ){
         assertThat("Test Container dosn't contains this fixture.", true, is(true) );
      }
      
      FixtureStrategy<TestPersistentFreshFixture> fixtureStrategy = new SpyFixtureStrategy<TestPersistentFreshFixture>( fixtureClass, testSuite );
      ((SpyFixtureStrategy<TestPersistentFreshFixture>)fixtureStrategy).invokeInstantiateFixture();
      
      assertThat( fixtureStrategy.getFixture(), notNullValue() );
      assertThat( fixtureStrategy.getFixture(), instanceOf( TestPersistentFreshFixture.class ));
   }
}
