package com.processpuzzle.litest.fitnesse;


import java.io.IOException;
import java.net.MalformedURLException;

import org.dom4j.DocumentException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.javascript.host.HTMLElement;
import com.processpuzzle.commons.xml.XmlDocumentComparator;

public class BrowserWidgetTester extends JavaScriptTester {
   private String widgetContainerId;
   private String widgetVariableName;
   
   //Constructors
   public BrowserWidgetTester() {
      super();
   }
   
   public BrowserWidgetTester( String testPageUrl ){
      super( testPageUrl );
   }
   
   public Boolean expectedContainer( String expectedContainerContent ) throws IOException, DocumentException {
      lazyInit();
      String currentContainerContent = testPage.getElementById( widgetContainerId ).asXml();
      return XmlDocumentComparator.compareGeneratedDocumentToExpectedDocument( currentContainerContent, expectedContainerContent );
   }
   
   public Boolean expectedElement( String expectedElement ) throws IOException, DocumentException {
      String elementAsText = ((HTMLElement)invocationResult.getJavaScriptResult()).jsxGet_outerHTML();
      return XmlDocumentComparator.compareGeneratedDocumentToExpectedDocument( elementAsText, expectedElement );
   }
   
   public void invokeWithParameters( String methodName, String methodParameters ) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
      lazyInit();
      String parameters = interpretParameters( methodParameters );
      String javaScriptExpression = widgetVariableName + "." + methodName + "( " + parameters + " );";
      
      logger.info( "Executing JavaScript: " + javaScriptExpression );
      invocationResult = testPage.executeJavaScript( javaScriptExpression );
   }
   
   public void setUpContainer( String htmlBody ){
   }
   
   //Properties
   public void setWidgetContainerId( String widgetContainerId ) { this.widgetContainerId = widgetContainerId; }
   public void setWidgetVariable( String widgetVariableName ) { this.widgetVariableName = widgetVariableName; }

}
