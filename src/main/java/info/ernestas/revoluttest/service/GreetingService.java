package info.ernestas.revoluttest.service;

import info.ernestas.revoluttest.model.Greeting;
import org.jvnet.hk2.annotations.Service;

@Service
public class GreetingService {

    public Greeting getGreeting(String text) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Greeting(text);
    }

}
