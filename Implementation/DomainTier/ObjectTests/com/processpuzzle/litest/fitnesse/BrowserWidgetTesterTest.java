package com.processpuzzle.litest.fitnesse;


import java.io.IOException;

import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import com.processpuzzle.commons.file.FileHelper;
import com.processpuzzle.litest.fitnesse.BrowserWidgetTester;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class BrowserWidgetTesterTest extends JavaScriptTesterTest{
   private static final String WIDGET_ID = "widgetId";
   private BrowserWidgetTester browserWidgetTester;
   
   @Before
   public void beforeEachTests() {
      super.beforeEachTests();
      
      String testPageUrl = "file:///" + FileHelper.educeRealPathFromClassPath( HTML_TEST_PAGE );
      browserWidgetTester = new BrowserWidgetTester( testPageUrl );
      browserWidgetTester.setWidgetContainerId( WIDGET_ID );
   }
   
   @Test
   public void expectedContainer_WhenRealHtlmFragmentIsEqualWithTheExpected_ReturnsTrue() throws IOException, DocumentException {
      assertThat( browserWidgetTester.expectedContainer( "<div id=\"widgetId\">\r\n</div>\r\n" ), is( true ));
   }

}
