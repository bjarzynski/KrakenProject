package api.messages;

public class SubscriptionStatusMessage {
  private String channelId;
  private String channelName;
  private String event;
  private String pair;
  private String status;
  private Subscription subscription;
  private String errorMessage;

  public String getErrorMessage() {
    return errorMessage;
  }

  public String getStatus() {
    return status;
  }
}
