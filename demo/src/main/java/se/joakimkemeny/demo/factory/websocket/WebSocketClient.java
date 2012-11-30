package se.joakimkemeny.demo.factory.websocket;

import org.eclipse.jetty.websocket.WebSocket;

import java.io.IOException;

public class WebSocketClient implements WebSocket.OnTextMessage {

    private WebSocketManager webSocketManager;
    private Connection connection;

    public WebSocketClient(WebSocketManager webSocketManager) {
        this.webSocketManager = webSocketManager;
    }

    @Override
    public void onOpen(Connection connection) {
        this.connection = connection;
        this.connection.setMaxIdleTime(Integer.MAX_VALUE);
        webSocketManager.registerClient(this);
    }

    @Override
    public void onClose(int closeCode, String message) {
        webSocketManager.deregisterClient(this);
    }

    @Override
    public void onMessage(String message) {
        webSocketManager.onCommand(message, this);
    }

    public void sendMessage(String message) throws IOException {
        if (connection != null) {
            connection.sendMessage(message);
        }
    }
}
