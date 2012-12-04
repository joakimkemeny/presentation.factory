public class WebSocketClient implements WebSocket.OnTextMessage {

    private WebSocketManager webSocketManager;
    private Connection connection;

    public WebSocketClient(WebSocketManager webSocketManager) {
        this.webSocketManager = webSocketManager;
    }

    public void onOpen(Connection connection) {
        this.connection = connection;
        webSocketManager.registerClient(this);
    }
