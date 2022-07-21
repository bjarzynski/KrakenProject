package api.websocket;

import com.google.gson.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class WebSocketKrakenClient extends WebSocketClient {
  private final Logger logger = LoggerFactory.getLogger(WebSocketKrakenClient.class);
  private CountDownLatch messageLatch;
  private final List<JsonElement> messages;

  public WebSocketKrakenClient(URI serverUri) {
    super(serverUri);
    messageLatch = new CountDownLatch(1);
    messages = new ArrayList<>();
  }

  public List<JsonElement> getMessages() {
    return messages;
  }

  public JsonElement getLastMessage() {
    return messages.get(messages.size() - 1);
  }

  public JsonElement getLastMessageContaining(String stringToContain) {
    List<JsonElement> messagesCopy = new ArrayList<>(messages);
    List<JsonElement> filteredMessages =  messagesCopy
            .stream()
            .filter(m -> m.toString().contains(stringToContain))
            .collect(Collectors.toList());
    if(!filteredMessages.isEmpty()) {
      return filteredMessages.get(filteredMessages.size()-1);
    } else {
      return new JsonObject();
    }
  }

  @Override
  public void onOpen(ServerHandshake serverHandshake) {
  }

  @Override
  public void onMessage(String s) {
    messages.add(JsonParser.parseString(s));
    if(!s.contains("heartbeat")) {
      messageLatch.countDown();
    }
  }

  @Override
  public void onClose(int i, String s, boolean b) {
    messageLatch.countDown();
  }

  @Override
  public void onError(Exception e) {
    logger.info("Error from Kraken API: " + e);
  }

  public void awaitForMessage(long timeoutSeconds) {
    try {
      messageLatch.await(timeoutSeconds, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      logger.info("Exception during wait for message");
    }
  }

   public void prepareMessageAwaiting() {
     messageLatch = new CountDownLatch(1);
   }
}
