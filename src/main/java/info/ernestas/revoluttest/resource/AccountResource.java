package info.ernestas.revoluttest.resource;

import info.ernestas.revoluttest.model.Account;
import info.ernestas.revoluttest.model.dto.AccountDto;
import info.ernestas.revoluttest.model.dto.AccountOpenInfoDto;
import info.ernestas.revoluttest.model.dto.TransferInfoDto;
import info.ernestas.revoluttest.service.AccountService;
import org.glassfish.jersey.server.ManagedAsync;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

@Path("/account")
public class AccountResource {

    private final AccountService accountService;

    @Inject
    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Path("/{accountNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void getAccount(@PathParam("accountNumber") String accountNumber, @Suspended AsyncResponse asyncResponse) {
        Account account = accountService.get(accountNumber);
        asyncResponse.resume(AccountDto.from(account));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AccountDto openAccount(AccountOpenInfoDto accountOpenInfoDto) {
        return AccountDto.from(accountService.open(accountOpenInfoDto.getName()));
    }

    @PUT
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void transfer(TransferInfoDto transferInfoDto) {
        accountService.transfer(transferInfoDto.getAccountFrom(), transferInfoDto.getAccountTo(), transferInfoDto.getAmount());
    }

}
