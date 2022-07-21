package api.messages;

public class Subscription {
  private Integer depth;
  private Integer interval;
  private final String name;
  private Boolean ratecounter;
  private Boolean snapshot;
  private String token;

  public Subscription(String name) {
    this.name = name;
  }

  public Subscription(String name, Integer depth) {
    this.name = name;
    this.depth = depth;
  }

  public void setInterval(Integer interval) {
    this.interval = interval;
  }
}
