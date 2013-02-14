package se.joakimkemeny.demo.factory.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import se.joakimkemeny.demo.factory.domain.Scale;
import se.joakimkemeny.demo.factory.manager.ScaleManager;
import se.joakimkemeny.demo.factory.websocket.WebSocketController;
import se.joakimkemeny.demo.factory.websocket.WebSocketMapping;

import java.util.List;

@WebSocketController("scale")
@Controller
@RequestMapping(produces = "application/json")
public class ScaleController {

    @Autowired
    private ScaleManager scaleManager;

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

    @RequestMapping(value = "/api/scale/toggle", method = RequestMethod.GET)
    @ResponseBody
    public void hideScales() {
        scaleManager.toggleScales();
    }
}
