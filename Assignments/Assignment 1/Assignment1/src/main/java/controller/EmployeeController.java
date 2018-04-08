package controller;

import database.JDBConnectionWrapper;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import service.account.AccountServiceImpl;
import service.client.ClientServiceImpl;
import service.report.ReportServiceImpl;
import view.EmployeeView;
import view.LoginView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;

public class EmployeeController {
    private final EmployeeView employeeView;
    private AccountServiceImpl accountService;
    private ClientServiceImpl clientService;
    private ReportServiceImpl reportService;
    private LoginView loginView;
    private int idE;

    public EmployeeController(EmployeeView employeeView, AccountServiceImpl accountService, ClientServiceImpl clientService, ReportServiceImpl reportService, LoginView loginView) {

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

        this.accountService = accountService;
        this.clientService = clientService;
        this.reportService = reportService;
        this.loginView = loginView;
    }

    public void setVisible(boolean b) {
        employeeView.setVisible(b);

    }

    public int getIdE() {
        return idE;
    }

    public void setIdE(int idE) {
        this.idE = idE;
    }

    private class CreateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String cardNo = employeeView.getIdNoTxt();
            String type = employeeView.getTypeTxt();
            String amount = employeeView.getAmountTxt();
            String date = employeeView.getDateTxt();
            String clientId = employeeView.getIdClientTxt();

            accountService.create(Long.parseLong(cardNo), type, Integer.parseInt(amount), null, Integer.parseInt(clientId));

            updateModel();

            reportService.addR(getIdE(), new Date(System.currentTimeMillis()), "Created account.");
        }
    }

    private class UpdateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Account account = getClicked();
            String cardNo = employeeView.getIdNoTxt();
            String type = employeeView.getTypeTxt();
            String amount = employeeView.getAmountTxt();
            String date = employeeView.getDateTxt();
            String clientId = employeeView.getIdClientTxt();
            accountService.update( account.getId(), Long.parseLong(cardNo), type, Integer.parseInt(amount), null, Integer.parseInt(clientId));

            updateModel();

            reportService.addR(getIdE(), new Date(System.currentTimeMillis()), "Updated account.");
        }
    }

    private class DeleteAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            accountService.remove(getClicked().getId());
            updateModel();
            reportService.addR(getIdE(), new Date(System.currentTimeMillis()), "Deleted account.");
        }
    }

    private class CreateClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = employeeView.getNameTxt();
            String cardId = employeeView.getIdCardTxt();
            String address = employeeView.getAddressTxt();
            String CNP = employeeView.getCNPTxt();

            clientService.create(name, Long.parseLong(cardId), address, CNP);

            updateModel2();

            reportService.addR(getIdE(), new Date(System.currentTimeMillis()), "Created client.");
        }
    }

    private class UpdateClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Client client = getClicked2();

            String name = employeeView.getNameTxt();
            String cardId = employeeView.getIdCardTxt();
            String address = employeeView.getAddressTxt();
            String CNP = employeeView.getCNPTxt();

            clientService.update(client.getId(), name, Long.parseLong(cardId), address, CNP);

            updateModel2();

            reportService.addR(getIdE(), new Date(System.currentTimeMillis()), "Updated client.");
        }
    }

    private class DeleteClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            Connection connection = new JDBConnectionWrapper("asg1").getConnection();

            AccountRepositoryMySQL ac;
            ac = new AccountRepositoryMySQL(connection);
            for (Account a : ac.findAll()) {
                if(a.getIdClient() == getClicked2().getId())
                    accountService.remove(a.getId());
            }

            clientService.remove(getClicked2().getId());
            updateModel();
            updateModel2();

            reportService.addR(getIdE(), new Date(System.currentTimeMillis()), "Deleted client with all its accounts.");
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

            updateModel();

            reportService.addR(getIdE(), new Date(System.currentTimeMillis()), "Transfered money from " + a1.getId() + "th account to" + a2.getId());
        }
    }

    private class BillButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Account account = getClicked();
            accountService.update(account.getId(), account.getIdNo(), account.getType(), account.getAmount() - Integer.parseInt(employeeView.getProcessTxt()), account.getDateOfCreation(), account.getIdClient());

            updateModel();

            reportService.addR(getIdE(), new Date(System.currentTimeMillis()), "Bills payed by client " + account.getIdClient());
        }
    }

    public Client getClicked2() {

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

    public Account getClicked() {

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

    public void updateModel(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("card No.");
        model.addColumn("type");
        model.addColumn("amount");
        model.addColumn("date of creation");
        model.addColumn("id client");

        Connection connection = new JDBConnectionWrapper("asg1").getConnection();

        AccountRepositoryMySQL ac;
        ac = new AccountRepositoryMySQL(connection);
        for (Account a : ac.findAll()) {
            Object[] o = {a.getId(), a.getIdNo(), a.getType(), a.getAmount(), a.getDateOfCreation(), a.getIdClient()};
            model.addRow(o);
        }

        employeeView.getTableAccounts().setModel(model);
    }

    public void updateModel2(){
        DefaultTableModel model2 = new DefaultTableModel();
        model2.addColumn("id");
        model2.addColumn("name");
        model2.addColumn("ID card");
        model2.addColumn("address");
        model2.addColumn("CNP");

        Connection connection = new JDBConnectionWrapper("asg1").getConnection();

        ClientRepositoryMySQL cl;
        cl = new ClientRepositoryMySQL(connection);
        for (Client c : cl.findAll()) {
            Object[] o = {c.getId(), c.getName(), c.getIdCardNo(), c.getAddress(), c.getCNP()};
            model2.addRow(o);
        }

        employeeView.getTableClients().setModel(model2);
    }
}
