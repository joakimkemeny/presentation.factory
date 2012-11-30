package se.joakimkemeny.demo.factory.websocket;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import se.joakimkemeny.demo.factory.util.AnnotationUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class WebSocketManager {

    public WebSocketManager() {
    }


    private Set<WebSocketClient> clients = new CopyOnWriteArraySet<WebSocketClient>();

    void registerClient(WebSocketClient client) {
        clients.add(client);
    }

    void deregisterClient(WebSocketClient client) {
        clients.remove(client);
    }


    private ConcurrentMap<String, WebSocketDelegate> delegates =
            new ConcurrentHashMap<String, WebSocketDelegate>();

    public void registerDelegateForProtocol(WebSocketDelegate delegate, String subProtocol) {
        delegates.putIfAbsent(subProtocol, delegate);
    }


    private final static ObjectMapper MAPPER = new ObjectMapper();

    public void onCommand(String message, WebSocketClient client) {

        WebSocketCommand command = null;
        try {
            command = WebSocketCommand.fromString(message);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        WebSocketDelegate delegate = delegates.get(command.getProtocol());

        for (Method method : delegate.getClass().getMethods()) {
            WebSocketMapping mapping = AnnotationUtils.findAnnotation(method, WebSocketMapping.class);
            if (mapping != null) {
                if (command.getCommand().equals(mapping.command())) {

                    List<Object> params = new ArrayList<Object>();
                    String[] variables = mapping.variables();
                    int variableIndex = 0;

                    Annotation[][] parameterAnnotations = AnnotationUtils.getParameterAnnotations(method);
                    Class<?>[] parameterTypes = method.getParameterTypes();

                    for (int i = 0; i < parameterAnnotations.length; i++) {
                        for (Annotation annotation : parameterAnnotations[i]) {
                            if (annotation instanceof PathVariable) {
                                String variableName = variables[variableIndex];
                                variableIndex++;
                                Object value = command.getVariables().get(variableName);
                                params.add(MAPPER.convertValue(value, parameterTypes[i]));
                            } else if (annotation instanceof RequestBody) {
                                params.add(MAPPER.convertValue(command.getData(), parameterTypes[i]));
                            }
                        }
                    }

                    if (params.size() == parameterTypes.length) {
                        try {
                            Object result;
                            if (params.size() == 0) {
                                result = method.invoke(delegate);
                            } else {
                                result = method.invoke(delegate, params.toArray());
                            }

                            if (!method.getReturnType().equals(Void.TYPE)) {
                                sendCommand(command.getProtocol(), command.getCommand(), result, client);
                            }

                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void broadcast(String protocol, String command, Object data) {
        sendCommand(protocol, command, data, null);
    }

    public void sendCommand(String protocol, String command, Object data, WebSocketClient client) {
        try {
            WebSocketCommand webSocketCommand = new WebSocketCommand(protocol, command, data);
            String message = WebSocketCommand.toString(webSocketCommand);

            if (client != null) {
                client.sendMessage(message);
            } else {
                for (WebSocketClient c : clients) {
                    c.sendMessage(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
