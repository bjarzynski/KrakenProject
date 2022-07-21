package api.messages;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class TradeMessage {
  private final Integer channelId;
  private final List<Trade> tradeList;
  private final String channelName;
  private final String pair;

  public TradeMessage(JsonElement jsonElement) {
    JsonArray tradeJsonArray = jsonElement.getAsJsonArray();
    this.channelId = tradeJsonArray.get(0).getAsInt();
    JsonArray tradeDetailsJsonArray = tradeJsonArray.get(1).getAsJsonArray();
    tradeList = new ArrayList<>();
    for(JsonElement json : tradeDetailsJsonArray) {
      JsonArray tradeInnerJsonArray = json.getAsJsonArray();
      Double price = tradeInnerJsonArray.get(0).getAsDouble();
      Double volume = tradeInnerJsonArray.get(1).getAsDouble();
      Double time = tradeInnerJsonArray.get(2).getAsDouble();
      String side = tradeInnerJsonArray.get(3).getAsString();
      String orderType = tradeInnerJsonArray.get(4).getAsString();
      String misc = tradeInnerJsonArray.get(5).getAsString();
      tradeList.add(new Trade(price, volume, time, side, orderType, misc));
    }
    this.channelName = tradeJsonArray.get(2).getAsString();
    this.pair = tradeJsonArray.get(3).getAsString();
  }

  public String getChannelName() {
    return channelName;
  }

  public List<Trade> getTradeList() {
    return tradeList;
  }
}
