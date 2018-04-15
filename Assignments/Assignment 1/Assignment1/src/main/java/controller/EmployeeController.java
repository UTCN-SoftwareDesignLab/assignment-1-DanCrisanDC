package controller;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validation.Notification;
import service.account.AccountService;
import service.client.ClientService;
import service.report.ReportService;
import view.EmployeeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class EmployeeController {
    private final EmployeeView employeeView;
    private final AccountService accountService;
    private final ClientService clientService;
    private final ReportService reportService;
    private int idEmployee;

    public EmployeeController(EmployeeView employeeView, AccountService accountService, ClientService clientService, ReportService reportService) {

        this.employeeView = employeeView;
        this.employeeView.setVisible(false);

        employeeView.setCreateAccountButtonListener(new CreateAccountButtonListener());
        employeeView.setUpdateAccountButtonListener(new UpdateAccountButtonListener());
        employeeView.setDeleteAccountButtonListener(new DeleteAccountButtonListener());

        employeeView.setCreateClientButtonListener(new CreateClientButtonListener());
        employeeView.setUpdateClientButtonListener(new UpdateClientButtonListener());
        employeeView.setDeleteClientButtonListener(new DeleteClientButtonListener());

        employeeView.setTransferButtonListener(new TransferButtonListener());
        employeeView.setBillButtonListener(new BillButtonListener());

        employeeView.setGetAccountButtonListener(new getAccountButtonListener());
        employeeView.setGetClientButtonListener(new getClientButtonListener());

        this.accountService = accountService;
        this.clientService = clientService;
        this.reportService = reportService;
    }

    public void setVisible(boolean b) {
        employeeView.setVisible(b);

    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    private class CreateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String cardNo = employeeView.getIdNoTxt();
            String type = employeeView.getTypeTxt();
            String amount = employeeView.getAmountTxt();
            String date = employeeView.getDateTxt();
            String clientId = employeeView.getIdClientTxt();

            Notification<Boolean> accountNotification = accountService.create(Long.parseLong(cardNo), type, Integer.parseInt(amount), null, Integer.parseInt(clientId));

            if (accountNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), accountNotification.getFormattedErrors());
            }
            else {
                if (!accountNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account creation failed");
                }
                else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account created");
                    updateAccountTable();
                    reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Created account.");
                }
            }
        }
    }

    private class UpdateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Account account = getAccountFromTable();
            String cardNo = employeeView.getIdNoTxt();
            String type = employeeView.getTypeTxt();
            String amount = employeeView.getAmountTxt();
            String date = employeeView.getDateTxt();
            String clientId = employeeView.getIdClientTxt();
            accountService.update( account.getId(), Long.parseLong(cardNo), type, Integer.parseInt(amount), null, Integer.parseInt(clientId));

            updateAccountTable();

            reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Updated account.");
        }
    }

    private class DeleteAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            accountService.remove(getAccountFromTable().getId());
            updateAccountTable();
            reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Deleted account.");
        }
    }

    private class CreateClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = employeeView.getNameTxt();
            String cardId = employeeView.getIdCardTxt();
            String address = employeeView.getAddressTxt();
            String CNP = employeeView.getCNPTxt();

            Notification<Boolean> clientNotification = clientService.create(name, Long.parseLong(cardId), address, CNP);

            if (clientNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), clientNotification.getFormattedErrors());
            }
            else {
                if (!clientNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client creation failed");
                }
                else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client created");
                    updateClientTable();
                    reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Created client");
                }
            }
        }
    }

    private class UpdateClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Client client = getClientFromTable();

            String name = employeeView.getNameTxt();
            String cardId = employeeView.getIdCardTxt();
            String address = employeeView.getAddressTxt();
            String CNP = employeeView.getCNPTxt();

            clientService.update(client.getId(), name, Long.parseLong(cardId), address, CNP);

            updateClientTable();

            reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Updated client.");
        }
    }

    private class DeleteClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            for (Account a : accountService.findAll()) {
                if(a.getIdClient() == getClientFromTable().getId())
                    accountService.remove(a.getId());
            }

            clientService.remove(getClientFromTable().getId());
            updateAccountTable();
            updateClientTable();

            reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Deleted client with all its accounts.");
        }
    }

    private class TransferButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Account a1, a2;
            a1 = accountService.findById(Integer.parseInt(employeeView.getFirstAcc()));
            a2 = accountService.findById(Integer.parseInt(employeeView.getSecondAcc()));

            accountService.update(a1.getId(),a1.getIdNo(),a1.getType(),a1.getAmount() - Integer.parseInt(employeeView.getSumTxt()), a1.getDateOfCreation(), a1.getIdClient());
            accountService.update(a2.getId(),a2.getIdNo(),a2.getType(),a2.getAmount() + Integer.parseInt(employeeView.getSumTxt()), a2.getDateOfCreation(), a2.getIdClient());

            updateAccountTable();

            reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Transfered money from " + a1.getId() + "th account to" + a2.getId());
        }
    }

    private class BillButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Account account = getAccountFromTable();
            accountService.update(account.getId(), account.getIdNo(), account.getType(), account.getAmount() - Integer.parseInt(employeeView.getProcessTxt()), account.getDateOfCreation(), account.getIdClient());

            updateAccountTable();

            reportService.addReport(getIdEmployee(), new Date(System.currentTimeMillis()), "Bills payed by client " + account.getIdClient());
        }
    }

    private class getAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Account account = getAccountFromTable();

            employeeView.setAmountTxt(Integer.toString(account.getAmount()));
            employeeView.setTypeTxt(account.getType());
            employeeView.setIdNoTxt(Long.toString(account.getIdNo()));
            employeeView.setIdClientTxt(Integer.toString(account.getIdClient()));
        }
    }

    private class getClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Client client = getClientFromTable();

            employeeView.setNameTxt(client.getName());
            employeeView.setIdCardTxt(Long.toString(client.getIdCardNo()));
            employeeView.setAddressTxt(client.getAddress());
            employeeView.setCNPTxt(client.getCNP());
        }
    }

    public Client getClientFromTable() {

        Integer id = (Integer) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),0);
        String name = (String)  employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),1);
        long idCard = (long) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),2);
        String address = (String)  employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),3);
        String CNP = (String) employeeView.getTableClients().getValueAt(employeeView.getTableClients().getSelectedRow(),4);

        Client c = new ClientBuilder()
                .setId(id)
                .setName(name)
                .setIdCardNo(idCard)
                .setAddress(address)
                .setCNP(CNP)
                .build();

        return c;
    }

    public Account getAccountFromTable() {

        Integer accountId = (Integer) employeeView.getTableAccounts().getValueAt(employeeView.getTableAccounts().getSelectedRow(),0);
        long cardNo = (long) employeeView.getTableAccounts().getValueAt(employeeView.getTableAccounts().getSelectedRow(),1);
        String type = (String)  employeeView.getTableAccounts().getValueAt(employeeView.getTableAccounts().getSelectedRow(),2);
        Integer amount = (Integer)  employeeView.getTableAccounts().getValueAt(employeeView.getTableAccounts().getSelectedRow(),3);
        Date date = (Date) employeeView.getTableAccounts().getValueAt(employeeView.getTableAccounts().getSelectedRow(),4);

        Integer idClient = (Integer)  employeeView.getTableAccounts().getValueAt(employeeView.getTableAccounts().getSelectedRow(),5);

        Account a = new AccountBuilder()
                .setId(accountId)
                .setIdNo(cardNo)
                .setType(type)
                .setAmount(amount)
                .setDateOfCreation(date)
                .setIdClient(idClient)
                .build();
        return a;
    }

    public void updateAccountTable(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("card No.");
        model.addColumn("type");
        model.addColumn("amount");
        model.addColumn("date of creation");
        model.addColumn("id client");

        for (Account a : accountService.findAll()) {
            Object[] o = {a.getId(), a.getIdNo(), a.getType(), a.getAmount(), a.getDateOfCreation(), a.getIdClient()};
            model.addRow(o);
        }

        employeeView.getTableAccounts().setModel(model);
    }

    public void updateClientTable(){
        DefaultTableModel model2 = new DefaultTableModel();
        model2.addColumn("id");
        model2.addColumn("name");
        model2.addColumn("ID card");
        model2.addColumn("address");
        model2.addColumn("CNP");

        for (Client c : clientService.findAll()) {
            Object[] o = {c.getId(), c.getName(), c.getIdCardNo(), c.getAddress(), c.getCNP()};
            model2.addRow(o);
        }

        employeeView.getTableClients().setModel(model2);
    }
}
