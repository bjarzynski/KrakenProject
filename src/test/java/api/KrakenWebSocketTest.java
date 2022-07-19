package api;

import com.google.gson.Gson;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URISyntaxException;

public class KrakenWebSocketTest {
  @Test
  public void ExampleTest() throws URISyntaxException {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(new URI("wss://ws.kraken.com"));
    krakenClient.connect();
    krakenClient.awaitForMessageLatch(10);
    SubscriptionMessage subscriptionMessage = new SubscriptionMessage("subscribe", new String[]{"XBT/USD"}, new Subscription("ticker"));
    Gson gson = new Gson();
    String message = gson.toJson(subscriptionMessage);
    krakenClient.newMessageLatch();
    krakenClient.send(message);
    krakenClient.awaitForMessageLatch(10);
  }
}
