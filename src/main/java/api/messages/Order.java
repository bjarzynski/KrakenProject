package api.messages;

public class Order {
  private final double price;
  private final double volume;
  private final double timestamp;

  public Order(double price, double volume, double timestamp) {
    this.price = price;
    this.volume = volume;
    this.timestamp = timestamp;
  }

  public double getPrice() {
    return price;
  }
}
