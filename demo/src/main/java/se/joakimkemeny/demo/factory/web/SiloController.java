package se.joakimkemeny.demo.factory.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import se.joakimkemeny.demo.factory.domain.Silo;
import se.joakimkemeny.demo.factory.manager.SiloManager;
import se.joakimkemeny.demo.factory.websocket.WebSocketDelegate;
import se.joakimkemeny.demo.factory.websocket.WebSocketManager;
import se.joakimkemeny.demo.factory.websocket.WebSocketMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping(produces = "application/json")
public class SiloController implements WebSocketDelegate {

    @Autowired
    private SiloManager siloManager;
    @Autowired
    private WebSocketManager webSocketManager;

    @PostConstruct
    private void registerWebSocketDelegate() {
        webSocketManager.registerDelegateForProtocol(this, "silo");
    }

    @WebSocketMapping(command = "list")
    @RequestMapping(value = "/api/silo", method = RequestMethod.GET)
    @ResponseBody
    public List<Silo> listSilos() {
        return siloManager.listSilos();
    }
}
