package api.spread;

import api.*;
import com.google.gson.Gson;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JsonUtils;

import java.net.URI;
import java.net.URISyntaxException;

public class SpreadTest {
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
}
