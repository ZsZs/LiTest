package hu.itkodex.litest.fixture;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import hu.itkodex.litest.testcase.AnotherTestSUT;
import hu.itkodex.litest.testcase.FixtureStrategyFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CompositeFixtureTest {
   private static FixtureFactory fixtureFactory;
   private CompositeFixture<AnotherTestSUT> compositeFixture;
   private TestPersistentSharedFixture firstFixture;
   private TestTransientFreshFixture secondFixture;
   
   @BeforeClass
   public static void beforeAllTests() {
      fixtureFactory = FixtureFactory.createInstance();
   }
   
   @Before
   public void beforeEachTests() {
      compositeFixture = fixtureFactory.createCompositeFixture( TestCompositeFixture.class );
      
      instantiateComponentFixturesAsStrategyWould();
   }
   
   @Test
   public void instantiation_DeterminesComponentsType() {
      assertThat( compositeFixture.getComponentTypes(), hasItem( TestPersistentSharedFixture.class ));
      assertThat( compositeFixture.getComponentTypes(), hasItem( TestTransientFreshFixture.class ));
   }
   
   @Test
   public void notifyOnEvent_HandlesComponentsCollection() {
      assertThat( compositeFixture.getComponents(), hasItem( firstFixture ));
      assertThat( compositeFixture.getComponents(), hasItem( secondFixture ));
      
      FixtureTerminationEvent firstTerminationEvent = new DefaultFixtureTerminationEvent( firstFixture );
      compositeFixture.notifyOnEvent( firstTerminationEvent );
      
      FixtureTerminationEvent secondTerminationEvent = new DefaultFixtureTerminationEvent( secondFixture );
      compositeFixture.notifyOnEvent( secondTerminationEvent );
      
      assertThat( compositeFixture.getComponents(), not( hasItem( firstFixture )));
      assertThat( compositeFixture.getComponents(), not( hasItem( secondFixture )));
   }

   @Test
   public void getSUT_SelectsFromComponentsByItsOwnSutType() {
      compositeFixture.getFixture( TestPersistentSharedFixture.class ).setUp();
      compositeFixture.getFixture( TestTransientFreshFixture.class ).setUp();
      compositeFixture.setUp();
      
      assertThat( compositeFixture.getSUT(), notNullValue() );
      assertThat( compositeFixture.getSUT(), instanceOf( AnotherTestSUT.class ));
   }
   
   @After
   public void afterEachTest() {
      firstFixture = null;
      secondFixture = null;
      compositeFixture = null;
   }
   
   @AfterClass
   public static void afterAllTests() {
      FixtureFactory.desctroyInstance();
      FixtureStrategyFactory.destroyInstance();
   }

   private void instantiateComponentFixturesAsStrategyWould() {
      firstFixture = fixtureFactory.createPersistentSharedFixture( TestPersistentSharedFixture.class );
      FixtureInstantiationEvent firstInstantiationEvent = new DefaultFixtureInstantiationEvent( firstFixture );
      compositeFixture.notifyOnEvent( firstInstantiationEvent );
   
      secondFixture = fixtureFactory.createTransientFreshFixture( TestTransientFreshFixture.class );
      FixtureInstantiationEvent secondInstantiationEvent = new DefaultFixtureInstantiationEvent( secondFixture );
      compositeFixture.notifyOnEvent( secondInstantiationEvent );
   }
}
