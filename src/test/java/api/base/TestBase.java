package api.base;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.net.URI;
import java.net.URISyntaxException;

@Listeners(TestListener.class)
public class TestBase {
  protected URI appUri;
  protected long messageTimeout;

  @BeforeMethod
  public void beforeTestSetup() throws URISyntaxException {
    appUri = new URI("wss://ws.kraken.com");
    messageTimeout = 60;
  }

}
