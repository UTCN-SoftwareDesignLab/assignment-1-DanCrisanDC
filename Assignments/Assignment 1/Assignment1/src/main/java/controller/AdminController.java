package controller;

import database.JDBConnectionWrapper;
import model.Report;
import model.User;
import model.builder.UserBuilder;
import repository.ReportRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AdminServices;
import service.user.AuthenticationServiceMySQL;
import view.AdminView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AdminController {
    private AdminView adminView;
    private UserRepositoryMySQL userRepository;
    private AuthenticationServiceMySQL authenticationService;
    private AdminServices adminServices;
    private ReportRepositoryMySQL reportRepositoryMySQL;

    public AdminController(AdminView adminView, UserRepositoryMySQL userRepository, AdminServices adminServices, AuthenticationServiceMySQL authenticationServiceMySQL, ReportRepositoryMySQL reportRepositoryMySQL) {

        this.adminView = adminView;
        this.adminView.setVisible(false);
        adminView.setCreateButtonListener(new CreateButtonListener());
        adminView.setViewButtonListener(new ViewButtonListener());
        adminView.setUpdateButtonListener(new UpdateButtonListener());
        adminView.setDeleteButtonListener(new DeleteButtonListener());
        adminView.setReportButtonListener(new ReportButtonListener());

        this.adminServices = adminServices;
        this.userRepository = userRepository;
        this.authenticationService = authenticationServiceMySQL;
        this.reportRepositoryMySQL = reportRepositoryMySQL;
    }

    public void setVisible(boolean b) {
        adminView.setVisible(b);

    }

    private class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUserField();
            String password = adminView.getPassField();

            authenticationService.register(username, password);

            updateTable();
        }
    }

    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateTable();
        }
    }

    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            User user = getClicked();
            String userText = adminView.getUserField();
            String passText = adminView.getPassField();

            adminServices.update(userText, authenticationService.encodePassword(passText), user.getId());

            updateTable();
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            userRepository.remove(getClicked().getId());
            updateTable();
        }
    }

    private class ReportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            updateTable2();
        }
    }

    public User getClicked() {

        Integer userId = (Integer) adminView.getTableUsers().getValueAt(adminView.getTableUsers().getSelectedRow(),0);
        String username = (String) adminView.getTableUsers().getValueAt(adminView.getTableUsers().getSelectedRow(),1);
        String userpass = (String) adminView.getTableUsers().getValueAt(adminView.getTableUsers().getSelectedRow(),2);

        User u = new UserBuilder()
                .setUsername(username)
                .setPassword(userpass)
                .setId(userId)
                .build();
        return u;
    }

    public void updateTable() {
        Connection connection = new JDBConnectionWrapper("asg1").getConnection();
        UserRepository ur;
        ur = new UserRepositoryMySQL(connection);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("username");
        model.addColumn("password");

        for (User u : ur.findAll()) {
            Object[] o = { u.getId(), u.getUsername(), u.getPassword()};
            model.addRow(o);
        }

        adminView.getTableUsers().setModel(model);
    }

    public void updateTable2() {
        Connection connection = new JDBConnectionWrapper("asg1").getConnection();

        DefaultTableModel model2  = new DefaultTableModel();
        model2.addColumn("id");
        model2.addColumn("employee ID");
        model2.addColumn("date");
        model2.addColumn("activity");

        ReportRepositoryMySQL r;
        r = new ReportRepositoryMySQL(connection);
        for (
                Report report : r.findAll()) {
            try {
                if(report.getDate().after(new SimpleDateFormat("yy-MM-dd").parse(adminView.getFromField())) && report.getDate().before(new SimpleDateFormat("yy-MM-dd").parse(adminView.getUntilField()))) {
                    Object[] o = {report.getId(), report.getIdE(), report.getDate(), report.getActivity()};
                    model2.addRow(o);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        adminView.getTableReport().setModel(model2);
    }
}
