package info.ernestas.revoluttest;

import info.ernestas.revoluttest.repository.AccountRepository;
import info.ernestas.revoluttest.service.AccountService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class DependencyBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(AccountService.class).to(AccountService.class);
        bind(AccountRepository.class).to(AccountRepository.class);
    }

}
