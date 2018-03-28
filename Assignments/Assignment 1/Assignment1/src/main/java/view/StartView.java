package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class StartView extends JFrame{

    private JButton btnEmployee;
    private JButton btnAdmin;
    private JButton btnRegister;

    public StartView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(btnEmployee);
        add(btnAdmin);
        add(btnRegister);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        btnEmployee = new JButton("Employee Login");
        btnAdmin = new JButton("Admin Login");
        btnRegister = new JButton("Register");
    }

    public void setEmployeeButtonListener(ActionListener loginButtonListener) {
        btnEmployee.addActionListener(loginButtonListener);
    }

    public void setAdminButtonListener(ActionListener loginButtonListener) {
        btnAdmin.addActionListener(loginButtonListener);
    }

    public void setRegisterButtonListener(ActionListener registerButtonListener) {
        btnRegister.addActionListener(registerButtonListener);
    }
}
