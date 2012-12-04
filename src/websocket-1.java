public class WebSocketHandlerServlet extends WebSocketServlet {

    private WebSocketManager webSocketManager;
    …

    public WebSocket doWebSocketConnect(
            HttpServletRequest request, String protocol) {

        return new WebSocketClient(webSocketManager);
    }
}
