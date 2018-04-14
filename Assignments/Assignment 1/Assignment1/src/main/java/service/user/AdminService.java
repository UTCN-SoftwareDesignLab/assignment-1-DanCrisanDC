package service.user;

import model.User;

import java.util.List;

public interface AdminService {

    boolean update(String username, String password, int id);

    List<User> findAll();

    boolean remove(int id);
}
