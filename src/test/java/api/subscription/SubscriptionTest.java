package api.subscription;

import api.base.TestBase;
import api.messages.Subscription;
import api.messages.SubscriptionMessage;
import api.messages.SubscriptionStatusMessage;
import api.websocket.WebSocketKrakenClient;
import com.google.gson.Gson;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.JsonUtils;

public class SubscriptionTest extends TestBase {
  private final String currencyPair = "ETH/USD";
  @DataProvider(name = "channel-test-data")
  public Object[][] dataProviderApi() {
    return new Object[][]{
            {"ticker"}, {"trade"}
    };
  }

  @Test(dataProvider = "channel-test-data")
  public void checkSubscriptionStatus(String channel){
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("subscribe", new String[]{currencyPair}, new Subscription(channel));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    Gson gson = new Gson();
    SubscriptionStatusMessage subscriptionStatusMessage =
            gson.fromJson(krakenClient.getLastMessage(), SubscriptionStatusMessage.class);
    Assert.assertEquals(subscriptionStatusMessage.getStatus(), "subscribed", "Incorrect subscription status");
    subscriptionMessage =
            new SubscriptionMessage("unsubscribe", new String[]{currencyPair}, new Subscription(channel));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(messageTimeout);
    subscriptionStatusMessage =
            gson.fromJson(krakenClient.getLastMessageContaining("subscriptionStatus"), SubscriptionStatusMessage.class);
    Assert.assertEquals(subscriptionStatusMessage.getStatus(), "unsubscribed", "Incorrect subscription status");
    krakenClient.close();
  }

  @Test
  public void checkSubscriptionNotFoundError() {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("unsubscribe", new String[]{currencyPair}, new Subscription("ticker"));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    Gson gson = new Gson();
    SubscriptionStatusMessage subscriptionStatusMes = gson.fromJson(krakenClient.getLastMessage(), SubscriptionStatusMessage.class);
    Assert.assertEquals(subscriptionStatusMes.getErrorMessage(), "Subscription Not Found", "Incorrect error message");
    krakenClient.close();
  }

  @Test
  public void checkSubscriptionNotExistingPairError() {
    String currencyPair = "ABC/USD";
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("unsubscribe", new String[]{currencyPair}, new Subscription("ticker"));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    Gson gson = new Gson();
    SubscriptionStatusMessage subscriptionStatusMes = gson.fromJson(krakenClient.getLastMessage(), SubscriptionStatusMessage.class);
    Assert.assertEquals(subscriptionStatusMes.getErrorMessage(), "Currency pair not supported " + currencyPair,
            "Incorrect error message");
    krakenClient.close();
  }

  @Test
  public void checkSubscriptionInvalidNameError() {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("unsubscribe", new String[]{currencyPair}, new Subscription("tick"));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    Gson gson = new Gson();
    SubscriptionStatusMessage subscriptionStatusMes = gson.fromJson(krakenClient.getLastMessage(), SubscriptionStatusMessage.class);
    Assert.assertEquals(subscriptionStatusMes.getErrorMessage(), "Subscription name invalid",
            "Incorrect error message");
    krakenClient.close();
  }

  @Test
  public void checkSubscriptionInvalidDepthError() {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("unsubscribe", new String[]{currencyPair}, new Subscription("book", 5));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    Gson gson = new Gson();
    SubscriptionStatusMessage subscriptionStatusMes = gson.fromJson(krakenClient.getLastMessage(), SubscriptionStatusMessage.class);
    Assert.assertEquals(subscriptionStatusMes.getErrorMessage(), "Subscription depth not supported",
            "Incorrect error message");
    krakenClient.close();
  }

  @Test
  public void checkSubscriptionDepthForInvalidChannelError() {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("unsubscribe", new String[]{currencyPair}, new Subscription("ticker", 100));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    Gson gson = new Gson();
    SubscriptionStatusMessage subscriptionStatusMes = gson.fromJson(krakenClient.getLastMessage(), SubscriptionStatusMessage.class);
    Assert.assertEquals(subscriptionStatusMes.getErrorMessage(), "Subscription ticker doesn't require depth",
            "Incorrect error message");
    krakenClient.close();
  }

}
