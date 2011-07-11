package hu.itkodex.litest.testcase;

public class NoFixtureContainerException extends RuntimeException {
   private static final long serialVersionUID = -6582348926395336857L;
   private static final String message = "There is no fixture container defined.";

   public NoFixtureContainerException() {
      super( message );
   }
}
