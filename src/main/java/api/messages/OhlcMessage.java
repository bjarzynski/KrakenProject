package api.messages;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class OhlcMessage {
  private final Integer channelId;
  private final Double time;
  private final Double etime;
  private final Double open;
  private final Double high;
  private final Double low;
  private final Double close;
  private final Double vwap;
  private final Double volume;
  private final Integer count;
  private final String channelName;
  private final String pair;

  public OhlcMessage(JsonElement jsonElement) {
    JsonArray ohlcJsonArray = jsonElement.getAsJsonArray();
    this.channelId = ohlcJsonArray.get(0).getAsInt();
    JsonArray candleArray = ohlcJsonArray.get(1).getAsJsonArray();
    this.time = candleArray.get(0).getAsDouble();
    this.etime = candleArray.get(1).getAsDouble();
    this.open = candleArray.get(2).getAsDouble();
    this.high = candleArray.get(3).getAsDouble();
    this.low = candleArray.get(4).getAsDouble();
    this.close = candleArray.get(5).getAsDouble();
    this.vwap = candleArray.get(6).getAsDouble();
    this.volume = candleArray.get(7).getAsDouble();
    this.count = candleArray.get(8).getAsInt();
    this.channelName = ohlcJsonArray.get(2).getAsString();
    this.pair = ohlcJsonArray.get(3).getAsString();
  }

  public Double getOpen() {
    return open;
  }

  public Double getHigh() {
    return high;
  }

  public Double getLow() {
    return low;
  }

  public String getChannelName() {
    return channelName;
  }

  public Double getVolume() {
    return volume;
  }

  public Integer getCount() {
    return count;
  }
}
