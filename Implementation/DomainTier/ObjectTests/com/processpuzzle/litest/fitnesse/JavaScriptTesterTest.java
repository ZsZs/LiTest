package com.processpuzzle.litest.fitnesse;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.processpuzzle.commons.file.FileHelper;
import com.processpuzzle.litest.fitnesse.JavaScriptTester;

public class JavaScriptTesterTest {
   protected static final String WIDGET_CONTAINER_ID = "widgetContainer";
   protected static final String GLOBAL_VARIABLE = "globalVariable";
   protected static final String JAVASCRIPT_FRAGMENT = "<script type='text/javascript'>" + GLOBAL_VARIABLE + "=2</script>";
   protected static final String HTML_FRAGMENT = "<div id=\"widgetContainer\">\r\n  <div id=\"parent\">\r\n  </div>\r\n</div>\r\n";
   protected static final String HTML_TEST_PAGE = "hu/itkodex/litest/fitnesse/TestbedPage.html";
   protected JavaScriptTester javaScriptTester;

   @Before
   public void beforeEachTests() {
      String testPageUrl = "file:///" + FileHelper.educeRealPathFromClassPath( HTML_TEST_PAGE );
      javaScriptTester = new JavaScriptTester( testPageUrl );
      
      assumeThat( javaScriptTester.isInitialized() , is( false ));
   }
   
   @Test
   public void constructorInstantiatesWebClient() {
      assertThat( javaScriptTester.getWebClient(), notNullValue() );
      assertThat( javaScriptTester.isInitialized, is( false ));
   }

   @Test
   public void addToBody_AddsGivenHtmlFragmentToTestPageBody() throws SAXException, IOException {
      javaScriptTester.addToBody( HTML_FRAGMENT );
      
      HtmlElement injectedElement = javaScriptTester.getTestPage().getElementById( WIDGET_CONTAINER_ID );
      assertThat( javaScriptTester.isInitialized, is( true ));
      assertThat( injectedElement, notNullValue() );
      assertThat( injectedElement.asXml(), equalTo( HTML_FRAGMENT ));
   }
   
   @Ignore @Test
   public void assertThatEqualsTo_ComparesCalculatedAndExpectedExpressions() throws SAXException, IOException{
      javaScriptTester.addToBody( JAVASCRIPT_FRAGMENT );
      
      assertThat( javaScriptTester.assertThatEqualsTo( GLOBAL_VARIABLE, new Integer( 2 ).toString() ), is( true ));
   }
}
