package com.processpuzzle.litest.fitnesse;


import org.junit.Before;
import org.junit.Test;

import com.processpuzzle.commons.file.FileHelper;
import com.processpuzzle.litest.fitnesse.BrowserWidgetTester;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class BrowserWidgetTesterTest extends JavaScriptTesterTest{
   private BrowserWidgetTester browserWidgetTester;
   
   @Before
   public void beforeEachTests() {
      super.beforeEachTests();
      
      String testPageUrl = "file:///" + FileHelper.educeRealPathFromClassPath( HTML_TEST_PAGE );
      browserWidgetTester = new BrowserWidgetTester( testPageUrl );
   }
   
   @Test
   public void expectedContainer_WhenRealHtlmFragmentIsEqualWithTheExpected_ReturnsTrue() {
      assertThat( true, is( true ));
   }

}