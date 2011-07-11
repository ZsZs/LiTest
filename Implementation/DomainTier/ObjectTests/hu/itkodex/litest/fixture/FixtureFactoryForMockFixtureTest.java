package hu.itkodex.litest.fixture;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class FixtureFactoryForMockFixtureTest {
   private static FixtureFactory fixtureFactory;
   
   @BeforeClass
   public static void beforeAllTests() {
      fixtureFactory = FixtureFactory.createInstance( true );
   }
   
   @Test
   public void createFixture_InstantiatesMock() {
      assertThat( fixtureFactory.createFixture( TestTransientFreshFixture.class ), notNullValue() );
   }
   
   @AfterClass
   public static void afterAllTests() {
      FixtureFactory.desctroyInstance();
   }
}
