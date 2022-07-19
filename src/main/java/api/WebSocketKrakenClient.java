package api;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WebSocketKrakenClient extends WebSocketClient {
  private final Logger logger = LoggerFactory.getLogger(WebSocketKrakenClient.class);
  private CountDownLatch messageLatch;

  public WebSocketKrakenClient(URI serverUri) {
    super(serverUri);
    messageLatch = new CountDownLatch(1);
  }

  @Override
  public void onOpen(ServerHandshake serverHandshake) {
    logger.info("Connected to Kraken");
    messageLatch.countDown();
  }

  @Override
  public void onMessage(String s) {
    logger.info("Got message " + s);
    messageLatch.countDown();
  }

  @Override
  public void onClose(int i, String s, boolean b) {
    logger.info("Disconnected from Kraken");
    messageLatch.countDown();
  }

  @Override
  public void onError(Exception e) {
    logger.info("Got error");
    e.printStackTrace();
  }

  public void awaitForMessageLatch(long timeoutSeconds) {
    try {
      messageLatch.await(timeoutSeconds, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

   public void newMessageLatch() {
     messageLatch = new CountDownLatch(1);
   }
}
