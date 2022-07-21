package api.trade;

import api.base.TestBase;
import api.messages.Subscription;
import api.messages.SubscriptionMessage;
import api.messages.Trade;
import api.messages.TradeMessage;
import api.websocket.WebSocketKrakenClient;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TradeTest extends TestBase {
  private final String currencyPair = "XBT/USD";

  @Test
  public void checkTradeTimeSorting() {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("subscribe", new String[]{currencyPair}, new Subscription("trade"));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(messageTimeout);
    TradeMessage tradeMessage = new TradeMessage(krakenClient.getLastMessage());
    List<Double> tradeTimes = tradeMessage.getTradeList().stream().map(Trade::getTime).collect(Collectors.toList());
    List<Double> sorted = new ArrayList<>(tradeTimes);
    Collections.sort(sorted);
    Assert.assertEquals(tradeTimes, sorted, "Trades aren't correctly sorted");
  }

  @Test
  public void checkTradeChannelName() {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("subscribe", new String[]{currencyPair}, new Subscription("trade"));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(messageTimeout);
    TradeMessage tradeMessage = new TradeMessage(krakenClient.getLastMessage());
    Assert.assertEquals(tradeMessage.getChannelName(), "trade", "Incorrect channel name");
  }

  @Test
  public void checkTriggeringOrderParameters() {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("subscribe", new String[]{currencyPair}, new Subscription("trade"));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(messageTimeout);
    TradeMessage tradeMessage = new TradeMessage(krakenClient.getLastMessage());
    tradeMessage.getTradeList()
            .forEach(trade -> Assert.assertTrue(trade.getOrderType().equals("m") || trade.getOrderType().equals("l"),
                    "Incorrect order type format"));
    tradeMessage.getTradeList()
            .forEach(trade -> Assert.assertTrue(trade.getSide().equals("b") || trade.getSide().equals("s"),
                    "Incorrect trade side format"));
  }
}
