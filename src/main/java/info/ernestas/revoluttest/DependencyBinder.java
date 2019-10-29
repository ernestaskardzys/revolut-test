package info.ernestas.revoluttest;

import info.ernestas.revoluttest.service.GreetingService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class DependencyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(GreetingService.class).to(GreetingService.class);
    }
}
