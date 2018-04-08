package service;

import database.DBConnectionFactory;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.sql.Connection;

public class AuthenticationServiceTest {

    public static final String TEST_USERNAME = "dancrisandc";
    public static final String TEST_PASSWORD = "parola123*";
    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;

    @BeforeClass
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection);

        authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }

    @Test
    public void register() throws Exception {
        Assert.assertTrue(
                authenticationService.register(TEST_USERNAME, TEST_PASSWORD).getResult()
        );
    }

    @Test
    public void login() throws Exception {
        authenticationService.register(TEST_USERNAME, TEST_PASSWORD).getResult();
        User user = authenticationService.login(TEST_USERNAME, TEST_PASSWORD).getResult();
        Assert.assertNotNull(user);
    }

    @Test
    public void logout() throws Exception {

    }
}
