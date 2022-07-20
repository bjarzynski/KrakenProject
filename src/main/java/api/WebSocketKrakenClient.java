package api;

import com.google.gson.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WebSocketKrakenClient extends WebSocketClient {
  private final Logger logger = LoggerFactory.getLogger(WebSocketKrakenClient.class);
  private CountDownLatch messageLatch;
  private List<JsonElement> messages;

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
    Optional<JsonElement> last =  messages
            .stream()
            .filter(m -> m.toString().contains(stringToContain))
            .reduce((first, second) -> second);
    return last.orElse(new JsonObject());
  }

  @Override
  public void onOpen(ServerHandshake serverHandshake) {
    logger.info("Connected to Kraken WebSockets API");
  }

  @Override
  public void onMessage(String s) {
    logger.info("Message from Kraken API: " + s);
    messages.add(JsonParser.parseString(s));
    if(!s.contains("heartbeat")) {
      messageLatch.countDown();
    }
  }

  @Override
  public void onClose(int i, String s, boolean b) {
    logger.info("Disconnected from Kraken Websockets API");
    messageLatch.countDown();
  }

  @Override
  public void onError(Exception e) {
    logger.info("Error from Kraken API: ");
    e.printStackTrace();
  }

  public void awaitForMessage(long timeoutSeconds) {
    try {
      messageLatch.await(timeoutSeconds, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

   public void prepareMessageAwaiting() {
     messageLatch = new CountDownLatch(1);
   }
}
