package api.messages;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class BookMessage {
  private final Integer channelId;
  private final List<Order> as;
  private final List<Order> bs;
  private final String channelName;
  private final String pair;

  public BookMessage(JsonElement jsonElement) {
    JsonArray bookJsonArray = jsonElement.getAsJsonArray();
    channelId = bookJsonArray.get(0).getAsInt();
    channelName = bookJsonArray.get(2).getAsString();
    pair = bookJsonArray.get(3).getAsString();
    JsonObject ordersJsonArray = bookJsonArray.get(1).getAsJsonObject();
    as = new ArrayList<>();
    addOrdersToArray(ordersJsonArray,"as");
    bs = new ArrayList<>();
    addOrdersToArray(ordersJsonArray,"bs");
  }

  private void addOrdersToArray(JsonObject ordersJsonArray, String type) {
    JsonArray array = ordersJsonArray.get(type).getAsJsonArray();
    for(JsonElement element : array) {
      JsonArray orderElement = element.getAsJsonArray();
      Order order = new Order(orderElement.get(0).getAsDouble(), orderElement.get(1).getAsDouble(),
              orderElement.get(2).getAsDouble());
      if(type.equals("as")) {
        as.add(order);
      } else {
        bs.add(order);
      }
    }
  }

  public List<Order> getAs() {
    return as;
  }

  public List<Order> getBs() {
    return bs;
  }
}
