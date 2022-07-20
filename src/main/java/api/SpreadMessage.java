package api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class SpreadMessage {
  private Integer channelId;
  private Double bid;
  private Double ask;
  private Double timestamp;
  private Double bidVolume;
  private Double askVolume;
  private String channelName;
  private String pair;

  public SpreadMessage(JsonElement jsonElement) {
    JsonArray jsonArray = jsonElement.getAsJsonArray();
    channelId = jsonArray.get(0).getAsInt();
    JsonArray values = jsonArray.get(1).getAsJsonArray();
    bid = values.get(0).getAsDouble();
    ask = values.get(1).getAsDouble();
    timestamp = values.get(2).getAsDouble();
    bidVolume = values.get(3).getAsDouble();
    askVolume = values.get(4).getAsDouble();
    channelName = jsonArray.get(2).getAsString();
    pair = jsonArray.get(3).getAsString();
  }

  public Integer getChannelId() {
    return channelId;
  }

  public Double getBid() {
    return bid;
  }

  public Double getAsk() {
    return ask;
  }

  public Double getTimestamp() {
    return timestamp;
  }

  public Double getBidVolume() {
    return bidVolume;
  }

  public Double getAskVolume() {
    return askVolume;
  }

  public String getChannelName() {
    return channelName;
  }

  public String getPair() {
    return pair;
  }
}
