package se.joakimkemeny.demo.factory.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.joakimkemeny.demo.factory.domain.Scale;
import se.joakimkemeny.demo.factory.websocket.WebSocketManager;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScaleManager {

    @Autowired
    private WebSocketManager webSocketManager;

    private List<Scale> scales = new ArrayList<Scale>();

    @PostConstruct
    protected void setupScales() {
        scales.add(new Scale("S1", 0, 0, false, false, true));
        scales.add(new Scale("S2", 0, 0, false, false, true));
    }

    public List<Scale> listScales() {
        return scales;
    }

    public Scale getScale(String name) {
        for (Scale scale : scales) {
            if (scale.getName().equals(name)) {
                return scale;
            }
        }
        return null;
    }

    public Scale updateScale(String name, int current, int target, boolean emptying, boolean active) {
        Scale scale = getScale(name);
        if (scale != null) {
            scale.setCurrent(current);
            scale.setTarget(target);
            scale.setEmptying(emptying);
            scale.setActive(active);
            webSocketManager.broadcast("scale", "updated", scale);
        }
        return scale;
    }

    public void activateScale(String name) {
        Scale scale = getScale(name);
        if (scale != null && !scale.isActive()) {
            scale.setActive(true);
            webSocketManager.broadcast("scale", "updated", scale);
        }
    }

    public void inactivateScale(String name) {
        Scale scale = getScale(name);
        if (scale != null && scale.isActive()) {
            scale.setActive(false);
            webSocketManager.broadcast("scale", "updated", scale);
        }
    }

    public void toggleScales() {
        for (Scale scale : scales) {
            scale.setHidden(!scale.isHidden());
            webSocketManager.broadcast("scale", "updated", scale);
        }
    }
}
