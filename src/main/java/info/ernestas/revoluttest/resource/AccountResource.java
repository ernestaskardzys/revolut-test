package info.ernestas.revoluttest.resource;

import info.ernestas.revoluttest.model.Account;
import info.ernestas.revoluttest.model.dto.AccountResponseDto;
import info.ernestas.revoluttest.model.dto.AccountOpenDto;
import info.ernestas.revoluttest.model.dto.TransferDto;
import info.ernestas.revoluttest.model.dto.TransferResponseDto;
import info.ernestas.revoluttest.service.AccountService;
import org.glassfish.jersey.server.ManagedAsync;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

@Path("/account")
public class AccountResource {

    public static final int FIVE = 5;
    private final AccountService accountService;

    @Inject
    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Path("/{accountNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void getAccount(@PathParam("accountNumber") String accountNumber, @Suspended final AsyncResponse asyncResponse) {
        asyncResponse.setTimeout(FIVE, TimeUnit.SECONDS);

        final Account account = accountService.get(accountNumber);
        asyncResponse.resume(AccountResponseDto.from(account));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void openAccount(AccountOpenDto accountOpenDto, @Suspended final AsyncResponse asyncResponse) {
        asyncResponse.setTimeout(FIVE, TimeUnit.SECONDS);

        final AccountResponseDto account = AccountResponseDto.from(accountService.open(accountOpenDto.getName()));
        asyncResponse.resume(Response.ok().entity(account).build());
    }

    @PUT
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void transfer(TransferDto transferDto, @Suspended final AsyncResponse asyncResponse) {
        asyncResponse.setTimeout(FIVE, TimeUnit.SECONDS);

        accountService.transfer(transferDto.getAccountFrom(), transferDto.getAccountTo(), transferDto.getAmount());
        final TransferResponseDto response = TransferResponseDto.from(transferDto);
        asyncResponse.resume(Response.ok().entity(response).build());
    }

}
