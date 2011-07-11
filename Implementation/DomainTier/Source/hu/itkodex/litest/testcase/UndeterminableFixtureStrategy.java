package hu.itkodex.litest.testcase;

import hu.itkodex.litest.fixture.TestFixture;

import com.ibm.icu.text.MessageFormat;

public class UndeterminableFixtureStrategy extends RuntimeException {
   private static final long serialVersionUID = -7021876086841443213L;
   private static final String message = "Fixture strategy can't be determined from fixture class: '{0}'";
   private Class<? extends TestFixture<?>> fixtureClass;
   
   UndeterminableFixtureStrategy( Class<? extends TestFixture<?>> fixtureClass ) {
      super( MessageFormat.format( message, new Object[] { fixtureClass } ) );
      this.fixtureClass = fixtureClass;
   }
   
   public Class<? extends TestFixture<?>> getFixtureClass() {
      return fixtureClass;
   }
}
