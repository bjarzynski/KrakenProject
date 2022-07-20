package api;

public class Order {
  private double price;
  private double volume;
  private double timestamp;

  public Order(double price, double volume, double timestamp) {
    this.price = price;
    this.volume = volume;
    this.timestamp = timestamp;
  }

  public double getPrice() {
    return price;
  }

  public double getVolume() {
    return volume;
  }

  public double getTimestamp() {
    return timestamp;
  }
}
