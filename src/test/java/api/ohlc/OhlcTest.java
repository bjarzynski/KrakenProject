package api.ohlc;

import api.base.TestBase;
import api.messages.OhlcMessage;
import api.messages.Subscription;
import api.messages.SubscriptionMessage;
import api.websocket.WebSocketKrakenClient;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.JsonUtils;

public class OhlcTest extends TestBase {
  private final String currencyPair = "XBT/USD";
  @DataProvider(name = "interval-test-data")
  public Object[][] dataProviderApi() {
    return new Object[][]{
            {1}, {15}, {60}
    };
  }

  @Test(dataProvider = "interval-test-data")
  public void checkOhlcTimeInterval(int interval) {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    Subscription subscription = new Subscription("ohlc");
    subscription.setInterval(interval);
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("subscribe", new String[]{currencyPair}, subscription);
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(messageTimeout);
    OhlcMessage ohlcMessage = new OhlcMessage(krakenClient.getLastMessage());
    Assert.assertEquals(ohlcMessage.getChannelName(), "ohlc-" + interval, "Ohlc interval is incorrect");
    krakenClient.close();
  }

  @Test
  public void checkOhlcOpenPrice() {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    Subscription subscription = new Subscription("ohlc");
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("subscribe", new String[]{currencyPair}, subscription);
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(messageTimeout);
    OhlcMessage ohlcMessage = new OhlcMessage(krakenClient.getLastMessage());
    Assert.assertTrue(ohlcMessage.getHigh() >= ohlcMessage.getOpen(), "Open price is higher than High price");
    Assert.assertTrue(ohlcMessage.getLow() <= ohlcMessage.getOpen(), "Open price is lower then Low price");
    krakenClient.close();
  }

  @Test
  public void checkOhlcTrades() {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    Subscription subscription = new Subscription("ohlc");
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("subscribe", new String[]{currencyPair}, subscription);
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(messageTimeout);
    OhlcMessage ohlcMessage = new OhlcMessage(krakenClient.getLastMessage());
    Assert.assertNotEquals(ohlcMessage.getCount(), 0, "There isn't any transaction for candle");
    Assert.assertTrue(ohlcMessage.getVolume() > 0, "There isn't any volume for candle");
    krakenClient.close();
  }
}
