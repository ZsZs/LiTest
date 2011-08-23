package hu.itkodex.litest.fitnesse;

import fitlibrary.DoFixture;

public class WebServiceTester extends DoFixture {
   public boolean invokeOfWithArguments( String methodName, String serviceName, String arguments ) {
      return true;
   }
   
   public boolean expectResponse( String expectedResponseUri ){
      return true;
   }
}
