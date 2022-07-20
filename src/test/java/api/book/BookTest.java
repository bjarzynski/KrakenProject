package api.book;

import api.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.JsonUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookTest {

  @DataProvider(name = "test-data")
  public Object[][] dataProviderApi() {
    return new Object[][]{
            {10}, {25}, {100}
    };
  }

  @Test
  public void checkBookOrder() throws URISyntaxException {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(new URI("wss://ws.kraken.com"));
    krakenClient.connect();
    krakenClient.awaitForMessage(10);
    SubscriptionMessage subscriptionMessage = new SubscriptionMessage("subscribe", new String[]{"XBT/USD"}, new Subscription("book"));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(10);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(10);
    BookMessage bookMessage = new BookMessage(krakenClient.getLastMessageContaining("\"as\":"));
    List<Double> asOrderValues = bookMessage.getAs().stream().map(Order::getPrice).collect(Collectors.toList());
    List<Double> sorted = new ArrayList<>(asOrderValues);
    Collections.sort(sorted);
    Assert.assertEquals(asOrderValues, sorted);
    List<Double> bsOrderValues = bookMessage.getBs().stream().map(Order::getPrice).collect(Collectors.toList());
    sorted = new ArrayList<>(bsOrderValues);
    Collections.sort(sorted, Collections.reverseOrder());
    Assert.assertEquals(bsOrderValues, sorted);
  }

  @Test(dataProvider = "test-data")
  public void checkBookDepth(int depth) throws URISyntaxException {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(new URI("wss://ws.kraken.com"));
    krakenClient.connect();
    krakenClient.awaitForMessage(10);
    SubscriptionMessage subscriptionMessage = new SubscriptionMessage("subscribe", new String[]{"XBT/USD"}, new Subscription("book", depth));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(10);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(10);
    BookMessage bookMessage = new BookMessage(krakenClient.getLastMessageContaining("\"as\":"));
    Assert.assertEquals(bookMessage.getAs().size(), depth);
    Assert.assertEquals(bookMessage.getBs().size(), depth);
  }
}
