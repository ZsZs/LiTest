package hu.itkodex.litest.fixture;

import hu.itkodex.litest.testcase.AnotherTestSUT;
import hu.itkodex.litest.testcase.TestSUT;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class FixtureFactoryTest {
   private static FixtureFactory fixtureFactory;
   
   @BeforeClass
   public static void beforeAllTests() {
      fixtureFactory = FixtureFactory.createInstance();
   }
   
   @Test public void createInstance_ReturnsTheSoleInstance() {
      assertThat( FixtureFactory.createInstance(), sameInstance( fixtureFactory ));
   }
   
   @Test public void destroyInstance_DestroysTheSoleInstance() {
      FixtureFactory.desctroyInstance();
      
      assertThat( FixtureFactory.createInstance(), not( sameInstance( fixtureFactory )));
   }
   
   @Test public void createTransientFreshFixture_ShouldCreateNewInstance() {
      TransientFreshFixture<AnotherTestSUT> previousFixture = fixtureFactory.createTransientFreshFixture( TestTransientFreshFixture.class );
      
      TransientFreshFixture<AnotherTestSUT> fixture = fixtureFactory.createTransientFreshFixture( TestTransientFreshFixture.class );
      
      assertThat( fixture, instanceOf( TransientFreshFixture.class ));
      assertThat( fixture, not( sameInstance( previousFixture )));
      assertThat( fixture.isConfigured(), is( false ));
   }
   
   @Test public void createPersistentFreshFixture_ShouldCreateNewInstace() {
      PersistentFreshFixture<TestSUT> previousFixture = fixtureFactory.createPersistentFreshFixture( TestPersistentFreshFixture.class );
      
      PersistentFreshFixture<TestSUT> fixture = fixtureFactory.createPersistentFreshFixture( TestPersistentFreshFixture.class );
      
      assertThat( fixture, instanceOf( PersistentFreshFixture.class ));
      assertThat( fixture, not( sameInstance( previousFixture )));
      assertThat( fixture.isConfigured(), is( false ));
   }
   
   @Test public void createPersistentSharedFixture_ShouldReturnSoleInstance() {
      PersistentSharedFixture<TestSUT> previousFixture = fixtureFactory.createPersistentSharedFixture( AnotherTestPersistentSharedFixture.class );
      PersistentSharedFixture<TestSUT> fixture = fixtureFactory.createPersistentSharedFixture( AnotherTestPersistentSharedFixture.class );
      
      assertThat( fixture, instanceOf( PersistentSharedFixture.class ));
      assertThat( fixture.isConfigured(), is( false ));
      assertThat( fixture, sameInstance( previousFixture ));
   }
   
   @Test public void createImmutableSharedFixture_IsSingleton() {
      ImmutableSharedFixture<TestSUT> previousFixture = fixtureFactory.createImmutableSharedFixture( TestImmutableSharedFixture.class );
      
      ImmutableSharedFixture<TestSUT> fixture = fixtureFactory.createImmutableSharedFixture( TestImmutableSharedFixture.class );
      
      assertThat( fixture, instanceOf( ImmutableSharedFixture.class ));
      assertThat( fixture, sameInstance( previousFixture ));
      assertThat( fixture.isConfigured(), is( false ));
   }
}
