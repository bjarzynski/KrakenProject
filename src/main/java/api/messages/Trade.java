package api.messages;

public class Trade {
  private final Double price;
  private final Double volume;
  private final Double time;
  private final String side;
  private final String orderType;
  private final String misc;

  public Trade(Double price, Double volume, Double time, String side, String orderType, String misc) {
    this.price = price;
    this.volume = volume;
    this.time = time;
    this.side = side;
    this.orderType = orderType;
    this.misc = misc;
  }

  public Double getTime() {
    return time;
  }

  public String getSide() {
    return side;
  }

  public String getOrderType() {
    return orderType;
  }
}
