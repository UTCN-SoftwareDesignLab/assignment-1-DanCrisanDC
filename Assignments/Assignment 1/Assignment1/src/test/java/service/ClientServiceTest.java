package service;

import database.DBConnectionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.client.ClientRepositoryMySQL;
import service.client.ClientServiceImpl;

import java.sql.Connection;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ClientServiceTest {

    private static ClientServiceImpl clientService;
    private static ClientRepositoryMySQL clientRepository;

    @BeforeClass
    public static void setup() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepositoryMySQL(connection);
        clientService = new ClientServiceImpl(clientRepository);
    }

    @Before
    public void cleanUp() {
        clientRepository.removeAll();
    }

    @Test
    public void create() {
        assertTrue(clientService.create("test",112233,"Romania","199"));
    }

    @Test
    public void findAll() throws Exception {
        clientService.create("test",112233,"Romania","199");
        assertEquals(1, clientService.findAll().size());
    }
}
