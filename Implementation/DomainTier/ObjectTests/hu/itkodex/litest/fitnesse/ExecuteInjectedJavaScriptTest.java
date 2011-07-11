package hu.itkodex.litest.fitnesse;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import hu.itkodex.commons.file.FileHelper;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HTMLParser;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ExecuteInjectedJavaScriptTest {
   @Ignore @Test
   public void htmlUnitCheck() throws FailingHttpStatusCodeException, MalformedURLException, IOException, SAXException {
      WebClient webClient = new WebClient();
      webClient.setJavaScriptEnabled( true );
      HtmlPage testPage = webClient.getPage( "file:///" + FileHelper.educeRealPathFromClassPath( "hu/itkodex/litest/fitnesse/TestbedPage.html" ));
      HTMLParser.parseFragment( testPage.getBody(), "<script type='text/javascript'>aVariable = 2;</script>" );
      ScriptResult invocationResult = testPage.executeJavaScript( "aVariable == 2;" );
      
      assertThat( new Boolean( invocationResult.getJavaScriptResult().toString()), is( true ));
   }
}
