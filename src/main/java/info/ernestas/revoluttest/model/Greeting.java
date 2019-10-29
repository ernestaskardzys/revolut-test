package info.ernestas.revoluttest.model;

import java.io.Serializable;

public class Greeting implements Serializable {

    private final String name;

    public Greeting() {
        this.name = null;
    }

    public Greeting(String name) {
        this.name = getGreeting(name);
    }

    private String getGreeting(String name) {
        return "Hello " + name;
    }

    public String getName() {
        return name;
    }
}
