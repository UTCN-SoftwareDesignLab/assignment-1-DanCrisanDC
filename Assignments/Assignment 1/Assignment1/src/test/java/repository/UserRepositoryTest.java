package repository;

import database.DBConnectionFactory;
import model.User;
import model.builder.UserBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserRepositoryTest {
    private static UserRepository userRepository;

    @BeforeClass
    public static void setupClass() {
        userRepository = new UserRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection());
    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        List<User> users = userRepository.findAll();
        assertEquals(users.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        User usr = new UserBuilder()
                .setUsername("Username")
                .setPassword("parola123#")
                .build();
        userRepository.save(usr);

        List<User> users = userRepository.findAll();
        assertEquals(users.size(), 1);
    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    public void save() throws Exception {
        assertTrue(userRepository.save(
                new UserBuilder()
                        .setUsername("Username")
                        .setPassword("parola123#")
                        .build()));
    }

    @Test
    public void removeAll() throws Exception {

    }
}
