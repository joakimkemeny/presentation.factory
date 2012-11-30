package se.joakimkemeny.demo.factory.websocket;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WebSocketHandlerServlet extends WebSocketServlet {

    private static WebSocketManager webSocketManager;

    @Autowired
    public void setApplicationContext(ApplicationContext ctx) {
        webSocketManager = ctx.getBean(WebSocketManager.class);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext().getNamedDispatcher("default").forward(request, response);
    }

    public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
        if (webSocketManager == null) {
            return null;
        }
        return new WebSocketClient(webSocketManager);
    }
}
