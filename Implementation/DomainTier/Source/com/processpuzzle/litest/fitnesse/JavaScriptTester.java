package com.processpuzzle.litest.fitnesse;

import java.io.IOException;
import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HTMLParser;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class JavaScriptTester {
   public static final String TESTBED_PAGE = "ProcessPuzzle/JavaScript/TestbedForFit.html";
   protected static final Logger logger = LoggerFactory.getLogger( JavaScriptTester.class );
   protected String host;
   protected ScriptResult invocationResult;
   protected boolean isInitialized = false;
   protected HtmlPage testPage;
   protected String testPageUrl;
   protected final WebClient webClient;

   //Constructors
   public JavaScriptTester() {
      this( null );
   }
   
   public JavaScriptTester( String testPageUrl ) {
      webClient = new WebClient();
      webClient.setJavaScriptEnabled( true );
      if( testPageUrl != null )
         this.testPageUrl = testPageUrl;
      else
         this.testPageUrl = host + "/files/" + TESTBED_PAGE; 
   }
   
   //Public accessors and mutators
   public void addToBody( String htmlText ) throws SAXException, IOException {
      lazyInit();
      HTMLParser.parseFragment( testPage.getBody(), htmlText );
   }

   public boolean assertThatEqualsTo( String calculatedExpression, String expectedExpression ){
      String javaScriptExpression = "eval( " + calculatedExpression + " ) == eval( " + expectedExpression + " );";
      invocationResult = testPage.executeJavaScript( javaScriptExpression );
      return new Boolean( invocationResult.getJavaScriptResult().toString() );
   }
   
   public void instantiateWithArgumentsAs( String className, String arguments, String instanceVariable ) {
      String javaScriptExpression = "new " + className + "( " + arguments + " );";
      
      logger.info( "Instantiate JavaScript object: " + className );
      invocationResult = testPage.executeJavaScript( javaScriptExpression );
   }
   
   public void invokeSetUp() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
      lazyInit();
      logger.info( "Invoking setUp();" );
      testPage.executeJavaScript( "setUp();" );
   }
   
   public void invokeTearDown() {
      logger.info( "Invoking tearDown();" );
      testPage.executeJavaScript( "tearDown();" );
   }
   
   //Properties
   public HtmlPage getTestPage() { return this.testPage; }
   public WebClient getWebClient() { return this.webClient; }
   public boolean isInitialized() { return this.isInitialized; }
   public void setHost( String host ) { this.host = host; }
   public void setTestPage( String testPageUrl ){ this.testPageUrl = testPageUrl; }
   
   //Protected, private helper methods
   protected String interpretParameters( String methodParameters ) {
      String interpretedArgumentList = "";
      
      for( String parameter : methodParameters.split( "," ) ){
         interpretedArgumentList += interpretedArgumentList != "" ? ", " : ""; 
         interpretedArgumentList += parameter;
      }
      
      return interpretedArgumentList;
   }

   protected void lazyInit() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
      if( testPage == null ) {
         retrievePage();
      }
      
      isInitialized = true;
   }
   
   protected void retrievePage() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
      testPage = webClient.getPage( testPageUrl );
   }
}
