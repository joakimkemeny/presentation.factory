package se.joakimkemeny.demo.factory.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.joakimkemeny.demo.factory.domain.Silo;
import se.joakimkemeny.demo.factory.websocket.WebSocketManager;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SiloManager {

    @Autowired
    private WebSocketManager webSocketManager;

    private List<Silo> silos = new ArrayList<Silo>();

    @PostConstruct
    protected void setupSilos() {
        silos.add(new Silo("A1", 50, 50, 10, false, "S1"));
        silos.add(new Silo("A2", 100, 100, 15, false, "S1"));
        silos.add(new Silo("A3", 150, 150, 20, false, "S1"));
        silos.add(new Silo("B1", 50, 50, 20, false, "S2"));
        silos.add(new Silo("B2", 100, 100, 30, false, "S2"));
        silos.add(new Silo("B3", 200, 200, 10, false, "S2"));
        silos.add(new Silo("B4", 100, 100, 20, false, "S2"));
    }

    public List<Silo> listSilos() {
        return silos;
    }

    public Silo getSilo(String name) {
        for (Silo silo : silos) {
            if (silo.getName().equals(name)) {
                return silo;
            }
        }
        return null;
    }

    public Silo updateSilo(String name, int level, boolean active) {
        Silo silo = getSilo(name);
        if (silo != null) {
            silo.setLevel(level);
            silo.setActive(active);
            webSocketManager.broadcast("silo", "updated", silo);
        }
        return silo;
    }
}
