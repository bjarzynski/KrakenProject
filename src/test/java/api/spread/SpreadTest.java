package api.spread;

import api.base.TestBase;
import api.messages.SpreadMessage;
import api.messages.Subscription;
import api.messages.SubscriptionMessage;
import api.websocket.WebSocketKrakenClient;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.JsonUtils;

public class SpreadTest extends TestBase {
  @DataProvider(name = "pair-test-data")
  public Object[][] dataProviderApi() {
    return new Object[][]{
            {"XBT/USD"}, {"ETH/USD"}, {"XDG/USD"}
    };
  }

  @Test
  public void checkBidAskComparison() {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("subscribe", new String[]{"XBT/USD"}, new Subscription("spread"));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(messageTimeout);
    SpreadMessage spreadMessage = new SpreadMessage(krakenClient.getLastMessage());
    Assert.assertTrue(spreadMessage.getAsk() >= spreadMessage.getBid(), "Ask is lower than Bid");
    krakenClient.close();
  }

  @Test(dataProvider = "pair-test-data")
  public void checkPairResponse(String pair) {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("subscribe", new String[]{pair}, new Subscription("spread"));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(messageTimeout);
    SpreadMessage spreadMessage = new SpreadMessage(krakenClient.getLastMessage());
    Assert.assertEquals(spreadMessage.getPair(), pair, "Incorrect currency pair in response");
    krakenClient.close();
  }
}
