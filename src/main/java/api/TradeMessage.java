package api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class TradeMessage {
  private Integer channelId;
  private List<Trade> tradeList;
  private String channelName;
  private String pair;

  public TradeMessage(JsonElement jsonElement) {
    JsonArray jsonArray = jsonElement.getAsJsonArray();
    this.channelId = jsonArray.get(0).getAsInt();
    JsonArray tradeArray = jsonArray.get(1).getAsJsonArray();
    tradeList = new ArrayList<>();
    for(JsonElement json : tradeArray) {
      JsonArray jsonArr = json.getAsJsonArray();
      Double price = jsonArr.get(0).getAsDouble();
      Double volume = jsonArr.get(1).getAsDouble();
      Double time = jsonArr.get(2).getAsDouble();
      String side = jsonArr.get(3).getAsString();
      String orderType = jsonArr.get(4).getAsString();
      String misc = jsonArr.get(5).getAsString();
      tradeList.add(new Trade(price, volume, time, side, orderType, misc));
    }
    this.channelName = jsonArray.get(2).getAsString();
    this.pair = jsonArray.get(3).getAsString();
  }

  public Integer getChannelId() {
    return channelId;
  }

  public String getChannelName() {
    return channelName;
  }

  public String getPair() {
    return pair;
  }

  public List<Trade> getTradeList() {
    return tradeList;
  }
}
