package service;

import database.DBConnectionFactory;
import model.Account;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImpl;

import java.sql.Connection;
import java.sql.Date;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class AccountServiceTest {

    private static AccountService accountService;
    private static ClientService clientService;
    private static AccountRepository accountRepository;
    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setup() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository = new AccountRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection);
        accountService = new AccountServiceImpl(accountRepository);
        clientService = new ClientServiceImpl(clientRepository);
    }

    @Before
    public void cleanUp() {
        accountRepository.removeAll();
    }

    @Before
    public void createClient() {
        clientRepository.removeAll();
        clientService.create("Ana", 112233, "Teius", "196");
    }

    @Test
    public void create() {
        assertTrue(accountService.create(123123, "Saving", 500, new Date(System.currentTimeMillis()), 1));
    }

    @Test
    public void findAll() throws Exception {
        accountService.create(123123, "Saving", 500, new Date(System.currentTimeMillis()), 1);
        assertEquals(1, accountService.findAll().size());
    }

    @Test
    public void updateAccount() throws EntityNotFoundException {
        accountService.create(123123, "Saving", 500, new Date(System.currentTimeMillis()), 1);
        boolean rez = accountService.update(accountRepository.findByClientId(1).getId(), 123123, "Spending", 500, new Date(System.currentTimeMillis()), 1);
        Account acc = accountRepository.findByClientId(1);
        assertTrue(rez);
        assertEquals("Spending", acc.getType());
    }
}
