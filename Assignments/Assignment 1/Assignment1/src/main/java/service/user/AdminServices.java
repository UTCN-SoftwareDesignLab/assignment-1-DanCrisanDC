package service.user;

import model.User;
import model.builder.UserBuilder;
import repository.user.UserRepositoryMySQL;

public class AdminServices implements AdminService {
    private UserRepositoryMySQL userRepositoryMySQL;

    public AdminServices(UserRepositoryMySQL userRepositoryMySQL) {
        this.userRepositoryMySQL = userRepositoryMySQL;
    }

    @Override
    public boolean update(String username, String password, int id) {
        User u = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setId(id)
                .build();

        return userRepositoryMySQL.update(u);
    }
}
