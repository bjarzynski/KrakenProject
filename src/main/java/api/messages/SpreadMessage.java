package api.messages;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class SpreadMessage {
  private final Integer channelId;
  private final Double bid;
  private final Double ask;
  private final Double timestamp;
  private final Double bidVolume;
  private final Double askVolume;
  private final String channelName;
  private final String pair;

  public SpreadMessage(JsonElement jsonElement) {
    JsonArray spreadJsonArray = jsonElement.getAsJsonArray();
    this.channelId = spreadJsonArray.get(0).getAsInt();
    JsonArray spreadDetailsJsonArray = spreadJsonArray.get(1).getAsJsonArray();
    this.bid = spreadDetailsJsonArray.get(0).getAsDouble();
    this.ask = spreadDetailsJsonArray.get(1).getAsDouble();
    this.timestamp = spreadDetailsJsonArray.get(2).getAsDouble();
    this.bidVolume = spreadDetailsJsonArray.get(3).getAsDouble();
    this.askVolume = spreadDetailsJsonArray.get(4).getAsDouble();
    this.channelName = spreadJsonArray.get(2).getAsString();
    this.pair = spreadJsonArray.get(3).getAsString();
  }

  public Double getBid() {
    return bid;
  }

  public Double getAsk() {
    return ask;
  }

  public String getPair() {
    return pair;
  }
}
