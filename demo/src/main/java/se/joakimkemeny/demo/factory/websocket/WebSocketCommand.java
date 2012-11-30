package se.joakimkemeny.demo.factory.websocket;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class WebSocketCommand {

    public WebSocketCommand() {
    }

    public WebSocketCommand(String protocol, String command, Object data) {
        this.protocol = protocol;
        this.command = command;
        this.data = data;
    }


    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static WebSocketCommand fromString(String data) throws IOException {
        if (data == null) {
            return null;
        } else {
            return MAPPER.readValue(data, WebSocketCommand.class);
        }
    }

    public static String toString(WebSocketCommand webSocketCommand) throws IOException {
        if (webSocketCommand == null) {
            return null;
        } else {
            return MAPPER.writeValueAsString(webSocketCommand);
        }
    }


    private String protocol;
    private String command;
    private Object data;
    private Map<String, Object> variables;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
