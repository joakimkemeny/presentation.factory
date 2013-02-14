package se.joakimkemeny.demo.factory.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import se.joakimkemeny.demo.factory.domain.Silo;
import se.joakimkemeny.demo.factory.manager.SiloManager;
import se.joakimkemeny.demo.factory.websocket.WebSocketController;
import se.joakimkemeny.demo.factory.websocket.WebSocketMapping;

import java.util.List;

@WebSocketController("silo")
@Controller
@RequestMapping(produces = "application/json")
public class SiloController {

    @Autowired
    private SiloManager siloManager;

    @WebSocketMapping(command = "list")
    @RequestMapping(value = "/api/silo", method = RequestMethod.GET)
    @ResponseBody
    public List<Silo> listSilos() {
        return siloManager.listSilos();
    }
}
