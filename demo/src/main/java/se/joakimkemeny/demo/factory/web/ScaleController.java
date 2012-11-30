package se.joakimkemeny.demo.factory.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import se.joakimkemeny.demo.factory.domain.Scale;
import se.joakimkemeny.demo.factory.manager.ScaleManager;
import se.joakimkemeny.demo.factory.websocket.WebSocketDelegate;
import se.joakimkemeny.demo.factory.websocket.WebSocketManager;
import se.joakimkemeny.demo.factory.websocket.WebSocketMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping(produces = "application/json")
public class ScaleController implements WebSocketDelegate {

    @Autowired
    private ScaleManager scaleManager;
    @Autowired
    private WebSocketManager webSocketManager;

    @PostConstruct
    private void registerWebSocketDelegate() {
        webSocketManager.registerDelegateForProtocol(this, "scale");
    }

    @WebSocketMapping(command = "list")
    @RequestMapping(value = "/api/scale", method = RequestMethod.GET)
    @ResponseBody
    public List<Scale> listScales() {
        return scaleManager.listScales();
    }

    @WebSocketMapping(command = "activate", variables = "name")
    @RequestMapping(value = "/api/scale/{name}/activate", method = RequestMethod.GET)
    @ResponseBody
    public void activateScale(@PathVariable String name) {
        scaleManager.activateScale(name);
    }

    @WebSocketMapping(command = "inactivate", variables = "name")
    @RequestMapping(value = "/api/scale/{name}/inactivate", method = RequestMethod.GET)
    @ResponseBody
    public void inactivateScale(@PathVariable String name) {
        scaleManager.inactivateScale(name);
    }
}
