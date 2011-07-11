package hu.itkodex.litest.fixture;

import com.ibm.icu.text.MessageFormat;

public class UnconfiguredSharedFixtureException extends RuntimeException {
   private static final long serialVersionUID = 4511662483441905333L;
   private static final String message = "Could not configure fixture: ''{0}'' configure properly.";
   
   public UnconfiguredSharedFixtureException() {
      super();
   }
   
   public UnconfiguredSharedFixtureException( String fixtureName, Throwable cause ) {
      super( MessageFormat.format( message, new Object[] {fixtureName} ), cause );
   }
}
