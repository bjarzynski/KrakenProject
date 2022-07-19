package api;

public class SubscriptionMessage {
  private String event;
  private Integer reqId;
  private String[] pair;
  private Subscription subscription;

  public SubscriptionMessage(String event, String[] pair, Subscription subscription) {
    this.event = event;
    this.pair = pair;
    this.subscription = subscription;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public int getReqId() {
    return reqId;
  }

  public void setReqId(int reqId) {
    this.reqId = reqId;
  }

  public String[] getPair() {
    return pair;
  }

  public void setPair(String[] pair) {
    this.pair = pair;
  }

  public Subscription getSubscription() {
    return subscription;
  }

  public void setSubscription(Subscription subscription) {
    this.subscription = subscription;
  }
}
