package se.joakimkemeny.demo.factory.domain;

public class Silo {

    private String name;
    private int level;
    private int capacity;
    private int flow;
    private boolean active;
    private String scale;

    public Silo() {
    }

    public Silo(String name, int level, int capacity, int flow, boolean active, String scale) {
        this.name = name;
        this.level = level;
        this.capacity = capacity;
        this.flow = flow;
        this.active = active;
        this.scale = scale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }
}
