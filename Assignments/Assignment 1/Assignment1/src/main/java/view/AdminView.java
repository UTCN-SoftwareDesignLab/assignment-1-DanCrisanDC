package view;

import database.JDBConnectionWrapper;
import model.Report;
import model.User;
import repository.ReportRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AdminView extends JFrame {

    private JButton btnCreate;
    private JButton btnView;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnReport;

    private JTextField userTxt;
    private JTextField passTxt;

    private JTextField fromTxt;
    private JTextField untilTxt;

    private JTable tableUsers;
    private JTable tableReport;

    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private JScrollPane scrollPane2;
    private DefaultTableModel model2;

    public AdminView() throws HeadlessException {
        setSize(700, 700);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(null);
        add(btnCreate);
        add(btnView);
        add(btnUpdate);
        add(btnDelete);
        add(btnReport);
        add(userTxt);
        add(passTxt);
        add(fromTxt);
        add(untilTxt);
        add(scrollPane);
        add(scrollPane2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    private void initializeFields() {

        btnCreate = new JButton("Create");
        btnCreate.setBounds(20, 20, 100, 100);
        btnView = new JButton("View");
        btnView.setBounds(20, 140, 100, 100);
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(20, 260, 100, 100);
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(20, 380, 100, 100);
        btnReport = new JButton("Generate Report");
        btnReport.setBounds(20, 520, 150, 50);

        userTxt = new JTextField("username");
        userTxt.setBounds(200, 520, 200, 40);
        passTxt = new JTextField("password");
        passTxt.setBounds(200, 570, 200, 40);

        fromTxt = new JTextField("2018-01-01");
        fromTxt.setBounds(450, 520, 200, 40);
        untilTxt= new JTextField("2020-12-31");
        untilTxt.setBounds(450, 570, 200, 40);

        // table for users
        scrollPane = new JScrollPane();
        scrollPane.setBounds(200, 20, 300, 200);

        model  = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("username");
        model.addColumn("password");


        Connection connection = new JDBConnectionWrapper("asg1").getConnection();
        UserRepository ur;
        ur = new UserRepositoryMySQL(connection);
        for (User u : ur.findAll()) {
            Object[] o = { u.getId(), u.getUsername(), u.getPassword()};
            model.addRow(o);
        }

        tableUsers = new JTable();
        tableUsers.setModel(model);
        scrollPane.setViewportView(tableUsers);

        //table for report
        scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(200, 240, 300, 200);

        model2  = new DefaultTableModel();
        model2.addColumn("id");
        model2.addColumn("employee ID");
        model2.addColumn("date");
        model2.addColumn("activity");

        ReportRepositoryMySQL r;
        r = new ReportRepositoryMySQL(connection);
        for (Report report : r.findAll()) {
            try {
                if(report.getDate().after(new SimpleDateFormat("yy-MM-dd").parse(fromTxt.getText())) && report.getDate().before(new SimpleDateFormat("yy-MM-dd").parse(untilTxt.getText()))) {
                    Object[] o = {report.getId(), report.getIdE(), report.getDate(), report.getActivity()};
                    model2.addRow(o);
                }
            } catch (ParseException e) {
               e.printStackTrace();
            }
        }

        tableReport = new JTable();
        tableReport.setModel(model2);
        scrollPane2.setViewportView(tableReport);

    }

    public void setCreateButtonListener(ActionListener createButtonListener) {
        btnCreate.addActionListener(createButtonListener);
    }

    public void setViewButtonListener(ActionListener viewButtonListener) {
        btnView.addActionListener(viewButtonListener);
    }

    public void setUpdateButtonListener(ActionListener updateButtonListener) {
        btnUpdate.addActionListener(updateButtonListener);
    }

    public void setDeleteButtonListener(ActionListener deleteButtonListener) {
        btnDelete.addActionListener(deleteButtonListener);
    }

    public void setReportButtonListener(ActionListener reportButtonListener) {
        btnReport.addActionListener(reportButtonListener);
    }

    public String getUserField() {
        String uf = this.userTxt.getText();
        return uf;
    }

    public String getPassField() {
        String pf = this.passTxt.getText();
        return pf;
    }

    public String getFromField() {
        String from = this.fromTxt.getText();
        return from;
    }

    public String getUntilField() {
        String until = this.untilTxt.getText();
        return until;
    }

    public DefaultTableModel getModel() {
        DefaultTableModel m = this.model;
        return m;
    }

    public JTable getTableUsers() {
        JTable tableUsers = this.tableUsers;
        return tableUsers;
    }

    public JTable getTableReport() {
        JTable tableReport = this.tableReport;
        return tableReport;
    }
}
