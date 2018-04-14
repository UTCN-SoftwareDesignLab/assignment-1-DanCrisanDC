package controller;

import model.Report;
import model.User;
import model.builder.UserBuilder;
import service.report.ReportService;
import service.user.AdminService;
import service.user.AuthenticationService;
import view.AdminView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AdminController {
    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final AdminService adminService;
    private final ReportService reportService;

    public AdminController(AdminView adminView, AdminService adminService, AuthenticationService authenticationService, ReportService reportService) {

        this.adminView = adminView;
        this.adminView.setVisible(false);
        adminView.setCreateButtonListener(new CreateButtonListener());
        adminView.setViewButtonListener(new ViewButtonListener());
        adminView.setUpdateButtonListener(new UpdateButtonListener());
        adminView.setDeleteButtonListener(new DeleteButtonListener());
        adminView.setReportButtonListener(new ReportButtonListener());

        this.adminService = adminService;
        this.authenticationService = authenticationService;
        this.reportService = reportService;
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

            updateUserTable();
        }
    }

    private class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateUserTable();
        }
    }

    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            User user = getUserFromTable();
            String userText = adminView.getUserField();
            String passText = adminView.getPassField();

            adminService.update(userText, passText, user.getId());

            updateUserTable();
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            adminService.remove(getUserFromTable().getId());
            updateUserTable();
        }
    }

    private class ReportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            updateReportTable();
        }
    }

    public User getUserFromTable() {

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

    public void updateUserTable() {

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("username");
        model.addColumn("password");

        for (User u : adminService.findAll()) {
            Object[] o = { u.getId(), u.getUsername(), u.getPassword()};
            model.addRow(o);
        }

        adminView.getTableUsers().setModel(model);
    }

    public void updateReportTable() {

        DefaultTableModel model2  = new DefaultTableModel();
        model2.addColumn("id");
        model2.addColumn("employee ID");
        model2.addColumn("date");
        model2.addColumn("activity");

        for (
                Report report : reportService.findAll()) {
            try {
                if(report.getDate().after(new SimpleDateFormat("yy-MM-dd").parse(adminView.getFromField())) && report.getDate().before(new SimpleDateFormat("yy-MM-dd").parse(adminView.getUntilField()))) {
                    Object[] o = {report.getId(), report.getIdEmployee(), report.getDate(), report.getActivity()};
                    model2.addRow(o);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        adminView.getTableReport().setModel(model2);
    }
}
