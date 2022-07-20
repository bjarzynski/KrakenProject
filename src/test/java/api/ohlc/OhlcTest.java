package api.ohlc;

import api.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.JsonUtils;

import java.net.URI;
import java.net.URISyntaxException;

public class OhlcTest {
  @DataProvider(name = "test-data")
  public Object[][] dataProviderApi() {
    return new Object[][]{
            {1}, {15}, {60}
    };
  }

  @Test(dataProvider = "test-data")
  public void checkOhlcTimeInterval(int interval) throws URISyntaxException {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(new URI("wss://ws.kraken.com"));
    krakenClient.connect();
    krakenClient.awaitForMessage(10);
    Subscription subscription = new Subscription("ohlc");
    subscription.setInterval(interval);
    SubscriptionMessage subscriptionMessage = new SubscriptionMessage("subscribe", new String[]{"XBT/USD"}, subscription);
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(10);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(10);
    OhlcMessage ohlcMessage = new OhlcMessage(krakenClient.getLastMessage());
    Assert.assertEquals(ohlcMessage.getChannelName(), "ohlc-" + interval);
  }

  @Test
  public void checkOhlcOpenPrice() throws URISyntaxException {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(new URI("wss://ws.kraken.com"));
    krakenClient.connect();
    krakenClient.awaitForMessage(10);
    Subscription subscription = new Subscription("ohlc");
    SubscriptionMessage subscriptionMessage = new SubscriptionMessage("subscribe", new String[]{"XBT/USD"}, subscription);
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(10);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(10);
    OhlcMessage ohlcMessage = new OhlcMessage(krakenClient.getLastMessage());
    Assert.assertTrue(ohlcMessage.getHigh() >= ohlcMessage.getOpen());
    Assert.assertTrue(ohlcMessage.getLow() <= ohlcMessage.getOpen());
  }

  @Test
  public void checkOhlcTrades() throws URISyntaxException {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(new URI("wss://ws.kraken.com"));
    krakenClient.connect();
    krakenClient.awaitForMessage(10);
    Subscription subscription = new Subscription("ohlc");
    SubscriptionMessage subscriptionMessage = new SubscriptionMessage("subscribe", new String[]{"XBT/USD"}, subscription);
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(10);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(10);
    OhlcMessage ohlcMessage = new OhlcMessage(krakenClient.getLastMessage());
    Assert.assertNotEquals(ohlcMessage.getCount(), 0);
    Assert.assertTrue(ohlcMessage.getVolume() > 0);
  }
}
