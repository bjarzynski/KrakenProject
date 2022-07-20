package api.subscription;

import api.Subscription;
import api.SubscriptionMessage;
import api.SubscriptionStatusMessage;
import api.WebSocketKrakenClient;
import com.google.gson.Gson;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.JsonUtils;

import java.net.URI;
import java.net.URISyntaxException;

public class SubscriptionTest {
  @DataProvider(name = "test-data")
  public Object[][] dataProviderApi() {
    return new Object[][]{
            {"ticker"}, {"book"}, {"trade"}
    };
  }

  @Test(dataProvider = "test-data")
  public void checkSubscriptionStatus(String channel) throws URISyntaxException {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(new URI("wss://ws.kraken.com"));
    krakenClient.connect();
    krakenClient.awaitForMessage(10);
    SubscriptionMessage subscriptionMessage = new SubscriptionMessage("subscribe", new String[]{"XBT/USD"}, new Subscription(channel));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(10);
    Gson gson = new Gson();
    SubscriptionStatusMessage subscriptionStatusMes = gson.fromJson(krakenClient.getLastMessage(), SubscriptionStatusMessage.class);
    Assert.assertEquals(subscriptionStatusMes.getStatus(), "subscribed");
    subscriptionMessage = new SubscriptionMessage("unsubscribe", new String[]{"XBT/USD"}, new Subscription(channel));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(10);
    if(krakenClient.getLastMessage().isJsonArray()) {
      krakenClient.prepareMessageAwaiting();
      krakenClient.awaitForMessage(10);
    }
    subscriptionStatusMes = gson.fromJson(krakenClient.getLastMessage(), SubscriptionStatusMessage.class);
    Assert.assertEquals(subscriptionStatusMes.getStatus(), "unsubscribed");
    krakenClient.close();
  }

  @Test
  public void checkSubscriptionError() throws URISyntaxException {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(new URI("wss://ws.kraken.com"));
    krakenClient.connect();
    krakenClient.awaitForMessage(10);
    SubscriptionMessage subscriptionMessage = new SubscriptionMessage("unsubscribe", new String[]{"XBT/USD"}, new Subscription("ticker"));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(10);
    Gson gson = new Gson();
    SubscriptionStatusMessage subscriptionStatusMes = gson.fromJson(krakenClient.getLastMessage(), SubscriptionStatusMessage.class);
    Assert.assertEquals(subscriptionStatusMes.getErrorMessage(), "Subscription Not Found");
    krakenClient.close();
  }
}
