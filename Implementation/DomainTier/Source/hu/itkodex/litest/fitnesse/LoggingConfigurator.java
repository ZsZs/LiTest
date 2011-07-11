package hu.itkodex.litest.fitnesse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class LoggingConfigurator extends GenericApplicationTest {
   private Map<String, Appender> appenders = new HashMap<String, Appender>();
   private Map<String, Layout> layouts = new HashMap<String, Layout>();
   
   public LoggingConfigurator() {
      initializeRootLogger();
   }

   public void addAppenderToLogger( String appenderName, String loggerName ) {
      Appender appender = appenders.get( appenderName );

      Logger logger = Logger.getLogger( loggerName );
      logger.addAppender( appender );
   }

   public void addAppenderToRootLogger( String appenderName ) {
      Logger rootLogger = Logger.getRootLogger();
      rootLogger.addAppender( appenders.get( appenderName ) );
   }

   public boolean checkLoggerWithMessageOfLevel( String loggerName, String message, String level ) {
      Logger logger = Logger.getLogger( loggerName );
      logger.log( Level.toLevel( level ), message );
      return true;
   }

   public void createAppenderOfTypeWithLayout( String appenderName, String appenderType, String layoutName ) throws IOException {
      Layout layout = layouts.get( layoutName );
      if( layout == null )
         throw new NullPointerException( "Layout: '" + layoutName + "' not found." );

      Appender appender = null;
      if( appenderType.equals( ConsoleAppender.class.getSimpleName() ) ){
         appender = new ConsoleAppender( layout, ConsoleAppender.SYSTEM_OUT );
      }else if( appenderType.equals( RollingFileAppender.class.getSimpleName() ) )
         appender = new RollingFileAppender( layout, "AcceptanceTests.log" );

      appender.setName( appenderName );
      if( appender != null )
         appenders.put( appenderName, appender );
   }

   public void createLogger( String loggerName ) {
      Logger.getLogger( loggerName );
   }

   public void createPatternLayoutWithFormat( String layoutName, String pattern ) {
      Layout layout = new PatternLayout( pattern );
      layouts.put( layoutName, layout );
   }

   public void setLoggingLevelForLogger( String loggingLevel, String loggerName ) {
      Logger logger = Logger.getLogger( loggerName );
      logger.setLevel( Level.toLevel( loggingLevel ) );
   }

   public void setRootLoggerLevel( String loggingLevel ) {
      Logger rootLogger = Logger.getRootLogger();
      rootLogger.setLevel( Level.toLevel( loggingLevel ) );
   }

   public void specifyLayoutForAppender( String layoutName, String appenderName ) {
      Appender appender = appenders.get( appenderName );
      if( appender == null )
         throw new NullPointerException( "Appender: '" + appenderName + "' not found." );

      Layout layout = layouts.get( layoutName );
      if( layout == null )
         throw new NullPointerException( "Layout: '" + layoutName + "' not found." );

      appender.setLayout( layout );
      layout.activateOptions();
   }

   // Private helper methods
   private void initializeRootLogger() {
      Logger rootLogger = Logger.getRootLogger();
      if( !rootLogger.getAllAppenders().hasMoreElements() ){
         rootLogger.setLevel( Level.INFO );
         rootLogger.addAppender( new ConsoleAppender( new PatternLayout( PatternLayout.TTCC_CONVERSION_PATTERN ) ) );
         rootLogger.debug( "Root logger initialized!" );
      }
   }
}
