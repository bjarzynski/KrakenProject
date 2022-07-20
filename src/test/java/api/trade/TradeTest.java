package api.trade;

import api.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JsonUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TradeTest {
  @Test
  public void checkTradeTimeOrder() throws URISyntaxException {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(new URI("wss://ws.kraken.com"));
    krakenClient.connect();
    krakenClient.awaitForMessage(10);
    SubscriptionMessage subscriptionMessage = new SubscriptionMessage("subscribe", new String[]{"ETH/USD"}, new Subscription("trade"));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(10);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(10);
    TradeMessage tradeMessage = new TradeMessage(krakenClient.getLastMessage());
    List<Double> tradeTimes = tradeMessage.getTradeList().stream().map(Trade::getTime).collect(Collectors.toList());
    List<Double> sorted = new ArrayList<>(tradeTimes);
    Collections.sort(sorted);
    Assert.assertEquals(tradeTimes, sorted);
  }

  @Test
  public void checkTradeChannelName() throws URISyntaxException {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(new URI("wss://ws.kraken.com"));
    krakenClient.connect();
    krakenClient.awaitForMessage(10);
    SubscriptionMessage subscriptionMessage = new SubscriptionMessage("subscribe", new String[]{"ETH/USD"}, new Subscription("trade"));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(10);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(10);
    TradeMessage tradeMessage = new TradeMessage(krakenClient.getLastMessage());;
    Assert.assertEquals(tradeMessage.getChannelName(), "trade");
  }
}
