package se.joakimkemeny.demo.factory.domain;

public class Scale {

    private String name;
    private int current;
    private int target;
    private boolean emptying;
    private boolean active;

    public Scale() {
    }

    public Scale(String name, int current, int target, boolean emptying, boolean active) {
        this.name = name;
        this.current = current;
        this.target = target;
        this.emptying = emptying;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public boolean isEmptying() {
        return emptying;
    }

    public void setEmptying(boolean emptying) {
        this.emptying = emptying;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
