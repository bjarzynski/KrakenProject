package api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class OhlcMessage {
  private Integer channelId;
  private Double time;
  private Double etime;
  private Double open;
  private Double high;
  private Double low;
  private Double close;
  private Double vwap;
  private Double volume;
  private Integer count;
  private String channelName;
  private String pair;

  public OhlcMessage(JsonElement jsonElement) {
    JsonArray jsonArray = jsonElement.getAsJsonArray();
    this.channelId = jsonArray.get(0).getAsInt();
    JsonArray candleArray = jsonArray.get(1).getAsJsonArray();
    this.time = candleArray.get(0).getAsDouble();
    this.etime = candleArray.get(1).getAsDouble();
    this.open = candleArray.get(2).getAsDouble();
    this.high = candleArray.get(3).getAsDouble();
    this.low = candleArray.get(4).getAsDouble();
    this.close = candleArray.get(5).getAsDouble();
    this.vwap = candleArray.get(6).getAsDouble();
    this.volume = candleArray.get(7).getAsDouble();
    this.count = candleArray.get(8).getAsInt();
    this.channelName = jsonArray.get(2).getAsString();
    this.pair = jsonArray.get(3).getAsString();
  }

  public Double getTime() {
    return time;
  }

  public Double getEtime() {
    return etime;
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

  public Double getClose() {
    return close;
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
