package api;

public class Subscription {
  private Integer depth;
  private Integer interval;
  private String name;
  private Boolean ratecounter;
  private Boolean snapshot;
  private String token;

  public Subscription(String name) {
    this.name = name;
  }

  public Integer getDepth() {
    return depth;
  }

  public void setDepth(Integer depth) {
    this.depth = depth;
  }

  public Integer getInterval() {
    return interval;
  }

  public void setInterval(Integer interval) {
    this.interval = interval;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getRatecounter() {
    return ratecounter;
  }

  public void setRatecounter(Boolean ratecounter) {
    this.ratecounter = ratecounter;
  }

  public Boolean getSnapshot() {
    return snapshot;
  }

  public void setSnapshot(Boolean snapshot) {
    this.snapshot = snapshot;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
