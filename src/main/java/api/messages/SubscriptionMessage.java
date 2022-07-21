package api.messages;

public class SubscriptionMessage {
  private final String event;
  private Integer reqId;
  private final String[] pair;
  private final Subscription subscription;

  public SubscriptionMessage(String event, String[] pair, Subscription subscription) {
    this.event = event;
    this.pair = pair;
    this.subscription = subscription;
  }
}
