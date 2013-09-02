package com.processpuzzle.litest.fixture;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingletonRegistry {
   public static SingletonRegistry REGISTRY = new SingletonRegistry();

   private static HashMap<String, Object> map = new HashMap<String, Object>();
   private static Logger logger = LoggerFactory.getLogger( SingletonRegistry.class );

   protected SingletonRegistry() {
   // Exists to defeat instantiation
   }

   public static Object getInstance( String className ) {
      return getInstance( className, className );
   }
   
   public static Object getInstance( String singletonName, String classname ) {
      Object singleton = map.get( singletonName );

      synchronized( map ) {
         if( singleton != null ) {
            return singleton;
         }
         try {
            singleton = Class.forName( classname ).newInstance();
            logger.info( "created singleton: " + singleton );
         } catch( ClassNotFoundException cnf ) {
            logger.error( "Couldn't find class " + classname );
         } catch( InstantiationException ie ) {
            logger.error( "Couldn't instantiate an object of type " + classname );
         } catch( IllegalAccessException ia ) {
            logger.error( "Couldn't access class " + classname );
         }
         map.put( singletonName, singleton );
      }
      return singleton;
   }
   
   public static void removeInstance( String singletonName ) {
      map.remove( singletonName );
   }

   public static boolean checkIfExists( String singletonName ) {
      return map.get( singletonName ) != null ? true : false;
   }
}
