package info.ernestas.revoluttest;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class App extends ResourceConfig {

    public App() {
        packages("info.ernestas.revoluttest");
        register(new DependencyBinder());
        register(new JacksonFeature());
    }

}
