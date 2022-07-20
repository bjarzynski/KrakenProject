package api.spread;

import api.*;
import com.google.gson.Gson;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.JsonUtils;

import java.net.URI;
import java.net.URISyntaxException;

public class SpreadTest {
  @DataProvider(name = "test-data")
  public Object[][] dataProviderApi() {
    return new Object[][]{
            {"XBT/USD"}, {"ETH/USD"}, {"XDG/USD"}
    };
  }

  @Test
  public void checkBidAsk() throws URISyntaxException {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(new URI("wss://ws.kraken.com"));
    krakenClient.connect();
    krakenClient.awaitForMessage(10);
    SubscriptionMessage subscriptionMessage = new SubscriptionMessage("subscribe", new String[]{"XBT/USD"}, new Subscription("spread"));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(10);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(10);
    SpreadMessage spreadMessage = new SpreadMessage(krakenClient.getLastMessage());
    Assert.assertTrue(spreadMessage.getAsk() > spreadMessage.getBid());
  }

  @Test(dataProvider = "test-data")
  public void checkPair(String pair) throws URISyntaxException {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(new URI("wss://ws.kraken.com"));
    krakenClient.connect();
    krakenClient.awaitForMessage(10);
    SubscriptionMessage subscriptionMessage = new SubscriptionMessage("subscribe", new String[]{pair}, new Subscription("spread"));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(10);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(10);
    SpreadMessage spreadMessage = new SpreadMessage(krakenClient.getLastMessage());
    Assert.assertEquals(spreadMessage.getPair(), pair);
  }
}
