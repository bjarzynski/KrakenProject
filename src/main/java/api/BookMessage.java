package api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class BookMessage {
  private Integer channelId;
  private List<Order> as;
  private List<Order> bs;
  private String channelName;
  private String pair;

  public BookMessage(JsonElement jsonElement) {
    as = new ArrayList<>();
    bs = new ArrayList<>();
    JsonArray jsonArray = jsonElement.getAsJsonArray();
    channelId = jsonArray.get(0).getAsInt();
    channelName = jsonArray.get(2).getAsString();
    pair = jsonArray.get(3).getAsString();
    JsonObject ordersArray = jsonArray.get(1).getAsJsonObject();
    JsonArray asArray = ordersArray.get("as").getAsJsonArray();
    for(JsonElement element : asArray) {
      JsonArray orderElement = element.getAsJsonArray();
      Order order = new Order(orderElement.get(0).getAsDouble(), orderElement.get(1).getAsDouble(), orderElement.get(2).getAsDouble());
      as.add(order);
    }
    JsonArray bsArray = ordersArray.get("bs").getAsJsonArray();
    for(JsonElement element : bsArray) {
      JsonArray orderElement = element.getAsJsonArray();
      Order order = new Order(orderElement.get(0).getAsDouble(), orderElement.get(1).getAsDouble(), orderElement.get(2).getAsDouble());
      bs.add(order);
    }
  }


  public Integer getChannelId() {
    return channelId;
  }

  public List<Order> getAs() {
    return as;
  }

  public List<Order> getBs() {
    return bs;
  }

  public String getChannelName() {
    return channelName;
  }

  public String getPair() {
    return pair;
  }
}
