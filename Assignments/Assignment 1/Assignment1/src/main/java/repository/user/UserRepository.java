package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException;

    Notification<User> findById(int id) throws AuthenticationException;

    boolean save(User user);

    boolean update(User user);

    boolean remove(int id);

    void removeAll();
}
