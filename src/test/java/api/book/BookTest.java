package api.book;

import api.base.TestBase;
import api.messages.BookMessage;
import api.messages.Order;
import api.messages.Subscription;
import api.messages.SubscriptionMessage;
import api.websocket.WebSocketKrakenClient;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookTest extends TestBase {
  private final String currencyPair = "XBT/USD";

  @DataProvider(name = "depth-test-data")
  public Object[][] dataProviderApi() {
    return new Object[][]{
            {10}, {25}, {100}
    };
  }

  @Test
  public void checkBookSorting() {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("subscribe", new String[]{currencyPair}, new Subscription("book"));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(messageTimeout);
    BookMessage bookMessage = new BookMessage(krakenClient.getLastMessageContaining("as"));
    List<Double> asOrderValues = bookMessage.getAs().stream().map(Order::getPrice).collect(Collectors.toList());
    List<Double> sorted = new ArrayList<>(asOrderValues);
    Collections.sort(sorted);
    Assert.assertEquals(asOrderValues, sorted, "Order As values aren't correctly sorted");
    List<Double> bsOrderValues = bookMessage.getBs().stream().map(Order::getPrice).collect(Collectors.toList());
    sorted = new ArrayList<>(bsOrderValues);
    sorted.sort(Collections.reverseOrder());
    Assert.assertEquals(bsOrderValues, sorted, "Order Bs values aren't correctly sorted");
    krakenClient.close();
  }

  @Test(dataProvider = "depth-test-data")
  public void checkBookDepth(int depth) {
    WebSocketKrakenClient krakenClient = new WebSocketKrakenClient(appUri);
    krakenClient.connect();
    krakenClient.awaitForMessage(messageTimeout);
    SubscriptionMessage subscriptionMessage =
            new SubscriptionMessage("subscribe", new String[]{currencyPair}, new Subscription("book", depth));
    krakenClient.prepareMessageAwaiting();
    krakenClient.send(JsonUtils.getJsonString(subscriptionMessage));
    krakenClient.awaitForMessage(messageTimeout);
    krakenClient.prepareMessageAwaiting();
    krakenClient.awaitForMessage(messageTimeout);
    BookMessage bookMessage = new BookMessage(krakenClient.getLastMessageContaining("as"));
    Assert.assertEquals(bookMessage.getAs().size(), depth, "Depth of book for As isn't correct");
    Assert.assertEquals(bookMessage.getBs().size(), depth, "Depth of book for Bs isn't correct");
    krakenClient.close();
  }
}
