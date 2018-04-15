package service.user;

import model.User;
import model.builder.UserBuilder;
import repository.user.UserRepository;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    private UserRepository userRepository;
    private AuthenticationService authenticationService;

    public AdminServiceImpl(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean update(String username, String password, int id) {
        User u = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setId(id)
                .build();

        u.setPassword(authenticationService.encodePassword(password));

        return userRepository.update(u);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean remove(int id) {
        return userRepository.remove(id);
    }


}
