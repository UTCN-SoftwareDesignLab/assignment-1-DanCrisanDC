package view;

import database.JDBConnectionWrapper;
import model.Account;
import model.Client;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class EmployeeView extends JFrame{

    private JButton btnCreateAccount;
    private JButton btnUpdateAccount;
    private JButton btnDeleteAccount;

    private JButton btnCreateClient;
    private JButton btnUpdateClient;
    private JButton btnDeleteClient;

    private JButton btnTransfer;
    private JButton btnBill;

    private JButton btnGetClient;
    private JButton btnGetAccount;

    private JTextField nameTxt;
    private JTextField idCardTxt;
    private JTextField addressTxt;
    private JTextField CNPTxt;

    private JTextField idNoTxt;
    private JTextField typeTxt;
    private JTextField amountTxt;
    private JTextField dateTxt;
    private JTextField idClientTxt;

    private JTextField firstAcc;
    private JTextField secondAcc;
    private JTextField sumTxt;
    private JTextField processTxt;

    private JTable tableAccounts;
    private JTable tableClients;

    private JScrollPane scrollPane;
    private JScrollPane scrollPane2;
    private DefaultTableModel model;
    private DefaultTableModel model2;

    public EmployeeView() throws HeadlessException {
        setSize(900, 900);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(null);
        add(btnCreateAccount);
        add(btnUpdateAccount);
        add(btnDeleteAccount);
        add(btnCreateClient);
        add(btnUpdateClient);
        add(btnDeleteClient);
        add(btnTransfer);
        add(btnBill);
        add(btnGetClient);
        add(btnGetAccount);
        add(nameTxt);
        add(idCardTxt);
        add(addressTxt);
        add(CNPTxt);
        add(idNoTxt);
        add(typeTxt);
        add(amountTxt);
        add(dateTxt);
        add(idClientTxt);
        add(sumTxt);
        add(processTxt);
        add(firstAcc);
        add(secondAcc);
        add(scrollPane);
        add(scrollPane2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {

        btnCreateAccount = new JButton("Create Account");
        btnCreateAccount.setBounds(20, 380, 150, 50);
        btnUpdateAccount = new JButton("Update Account");
        btnUpdateAccount.setBounds(20, 500, 150, 50);
        btnDeleteAccount = new JButton("Delete Account");
        btnDeleteAccount.setBounds(20, 620, 150, 50);

        btnCreateClient = new JButton("Create Client");
        btnCreateClient.setBounds(20, 20, 150, 50);
        btnUpdateClient = new JButton("Update Client");
        btnUpdateClient.setBounds(20, 140, 150, 50);
        btnDeleteClient = new JButton("Delete Client");
        btnDeleteClient.setBounds(20, 260, 150, 50);

        btnTransfer = new JButton("Transfer Money");
        btnTransfer.setBounds(20, 740, 150, 50);

        btnBill = new JButton("Process bill");
        btnBill.setBounds(450, 730, 120, 50);

        btnGetClient = new JButton("Get client");
        btnGetClient.setBounds(600, 300, 150, 50);

        btnGetAccount = new JButton("Get account");
        btnGetAccount.setBounds(600, 730, 150, 50);

        nameTxt = new JTextField("name");
        nameTxt.setBounds(600, 20, 200, 40);
        idCardTxt = new JTextField("card ID");
        idCardTxt.setBounds(600, 90, 200, 40);
        addressTxt = new JTextField("address");
        addressTxt.setBounds(600, 160, 200, 40);
        CNPTxt = new JTextField("CNP");
        CNPTxt.setBounds(600, 230, 200, 40);

        idNoTxt = new JTextField("card no.");
        idNoTxt.setBounds(600, 380, 200, 40);
        typeTxt = new JTextField("account type");
        typeTxt.setBounds(600, 450, 200, 40);
        amountTxt = new JTextField("amount");
        amountTxt.setBounds(600, 520, 200, 40);
        dateTxt = new JTextField("date");
        dateTxt.setBounds(600, 590, 200, 40);
        idClientTxt = new JTextField("client ID");
        idClientTxt.setBounds(600, 660, 200, 40);

        firstAcc = new JTextField("1st account");
        firstAcc.setBounds(200, 700, 50, 50);
        secondAcc = new JTextField("2nd account");
        secondAcc.setBounds(200, 760, 50, 50);
        sumTxt = new JTextField("amount");
        sumTxt.setBounds(270, 730, 100, 50);

        processTxt = new JTextField("to pay");
        processTxt.setBounds(450, 790, 100, 50);

        // table for accounts
        scrollPane = new JScrollPane();
        scrollPane.setBounds(200, 380, 300, 300);

        model  = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("Card No.");
        model.addColumn("Type");
        model.addColumn("Amount");
        model.addColumn("Date of creation");
        model.addColumn("ID client");

        Connection connection = new JDBConnectionWrapper("asg1").getConnection();

        AccountRepositoryMySQL ac;
        ac = new AccountRepositoryMySQL(connection);
        for (Account a : ac.findAll()) {
            Object[] o = {a.getId(), a.getIdNo(), a.getType(), a.getAmount(), a.getDateOfCreation(), a.getIdClient()};
            model.addRow(o);
        }

        tableAccounts = new JTable();
        tableAccounts.setModel(model);
        scrollPane.setViewportView(tableAccounts);

        // table for clients
        scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(200, 20, 300, 300);

        model2  = new DefaultTableModel();
        model2.addColumn("id");
        model2.addColumn("name");
        model2.addColumn("identity card no.");
        model2.addColumn("address");
        model2.addColumn("CNP");

        ClientRepositoryMySQL cl;
        cl = new ClientRepositoryMySQL(connection);
        for (Client c : cl.findAll()) {
            Object[] o = {c.getId(), c.getName(), c.getIdCardNo(), c.getAddress(), c.getCNP()};
            model2.addRow(o);
        }

        tableClients = new JTable();
        tableClients.setModel(model2);
        scrollPane2.setViewportView(tableClients);
    }

    public void setNameTxt(String name) {
        nameTxt.setText(name);
    }

    public void setIdCardTxt(String idCard) {
        idCardTxt.setText(idCard);
    }

    public void setAddressTxt(String address) {
        addressTxt.setText(address);
    }

    public void setCNPTxt(String CNP) {
        CNPTxt.setText(CNP);
    }

    public void setIdNoTxt(String idNo) {
        idNoTxt.setText(idNo);
    }

    public void setTypeTxt(String type) {
        typeTxt.setText(type);
    }

    public void setAmountTxt(String amount) {
        amountTxt.setText(amount);
    }

    public void setDateTxt(String date) {
        dateTxt.setText(date);
    }

    public void setIdClientTxt(String idClient) {
        idClientTxt.setText(idClient);
    }

    public String getNameTxt() {
        return nameTxt.getText();
    }

    public String getIdCardTxt() {
        return idCardTxt.getText();
    }

    public String getAddressTxt() {
        return addressTxt.getText();
    }

    public String getCNPTxt() {
        return CNPTxt.getText();
    }

    public String getIdNoTxt() {
        return idNoTxt.getText();
    }

    public String getTypeTxt() {
        return typeTxt.getText();
    }

    public String getAmountTxt() {
        return amountTxt.getText();
    }

    public String getDateTxt() {
        return dateTxt.getText();
    }

    public String getIdClientTxt() {
        return idClientTxt.getText();
    }

    public String getFirstAcc() {
        return firstAcc.getText();
    }

    public String getSecondAcc() {
        return secondAcc.getText();
    }

    public String getSumTxt() {
        return sumTxt.getText();
    }

    public String getProcessTxt() {
        return processTxt.getText();
    }

    public void setCreateAccountButtonListener(ActionListener createAccountButtonListener) {
        btnCreateAccount.addActionListener(createAccountButtonListener);
    }

    public void setUpdateAccountButtonListener(ActionListener updateAccountButtonListener) {
        btnUpdateAccount.addActionListener(updateAccountButtonListener);
    }

    public void setDeleteAccountButtonListener(ActionListener deleteAccountButtonListener) {
        btnDeleteAccount.addActionListener(deleteAccountButtonListener);
    }

    public void setCreateClientButtonListener(ActionListener createClientButtonListener) {
        btnCreateClient.addActionListener(createClientButtonListener);
    }

    public void setUpdateClientButtonListener(ActionListener updateClientButtonListener) {
        btnUpdateClient.addActionListener(updateClientButtonListener);
    }

    public void setDeleteClientButtonListener(ActionListener deleteClientButtonListener) {
        btnDeleteClient.addActionListener(deleteClientButtonListener);
    }

    public void setTransferButtonListener(ActionListener transferButtonListener) {
        btnTransfer.addActionListener(transferButtonListener);
    }

    public void setBillButtonListener(ActionListener billButtonListener) {
        btnBill.addActionListener(billButtonListener);
    }

    public void setGetClientButtonListener(ActionListener getClientButtonListener) {
        btnGetClient.addActionListener(getClientButtonListener);
    }

    public void setGetAccountButtonListener(ActionListener getAccountButtonListener) {
        btnGetAccount.addActionListener(getAccountButtonListener);
    }

    public DefaultTableModel getModel() {
        DefaultTableModel m = this.model;
        return m;
    }

    public JTable getTableAccounts() {
        JTable tableAccounts = this.tableAccounts;
        return tableAccounts;
    }

    public JTable getTableClients() {
        JTable tableClients = this.tableClients;
        return tableClients;
    }
}
