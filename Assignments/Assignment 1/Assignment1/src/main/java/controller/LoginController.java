package controller;

import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;
import repository.user.UserRepositoryMySQL;
import service.user.AuthenticationService;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private EmployeeController employeeController;
    private AdminController adminController;
    private UserRepositoryMySQL userRepositoryMySQL;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, AdminController adminController, EmployeeController employeeController, UserRepositoryMySQL userRepositoryMySQL) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());

        this.adminController = adminController;
        adminController.setVisible(false);
        this.employeeController = employeeController;
        this.userRepositoryMySQL = userRepositoryMySQL;
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            Notification<User> loginNotification = null;
            try {
                loginNotification = authenticationService.login(username, password);
                employeeController.setIdE(userRepositoryMySQL.findByUsernameAndPassword(username, authenticationService.encodePassword(password)).getResult().getId());
            } catch (AuthenticationException e1) {
                e1.printStackTrace();
            }

            if (loginNotification != null) {
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
                } else {

                    User user = loginNotification.getResult();
                    if (loginView.getRadioButtonStatus() && isAdmin() == true) {
                        adminController.setVisible(true);
                    } else {
                        employeeController.setVisible(true);
                    }
                }
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                    adminController.updateTable();
                }
            }
        }
    }

    private boolean isAdmin() {
        if(loginView.getUsername().equals("dancrisan") && loginView.getPassword().equals("parola123#"))
            return true;
        else
            return false;
    }
}