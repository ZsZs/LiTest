package hu.itkodex.litest.testcase;

import java.text.MessageFormat;

public class NoSuchFixtureDefinitionException extends Exception {
   private static final long serialVersionUID = 3165759803821519855L;
   private static final String message = "Fixture ''{0}'' is not defined in ''{1}'' configuration.";
   private String fixtureName;
   private String containerConfigurationPath;
   
   public NoSuchFixtureDefinitionException( String fixtureName, String containerConfigurationPath, Throwable cause ) {
      super( MessageFormat.format( message, new Object[] {fixtureName, containerConfigurationPath} ), cause );
      this.fixtureName = fixtureName;
      this.containerConfigurationPath = containerConfigurationPath;
   }

   public String getFixtureName() {
      return fixtureName;
   }

   public String getContainerConfigurationPath() {
      return containerConfigurationPath;
   }
}
