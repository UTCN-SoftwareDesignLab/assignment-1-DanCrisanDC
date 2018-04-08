package repository.account;

import model.Account;
import model.builder.AccountBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository {

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Account findById(int id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getAccountFromResultSet(rs);
            } else {
                throw new EntityNotFoundException(id, Account.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Account.class.getSimpleName());
        }
    }

    @Override
    public Account findByClientId(int idC) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where idClient=" + idC;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getAccountFromResultSet(rs);
            } else {
                throw new EntityNotFoundException(idC, Account.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(idC, Account.class.getSimpleName());
        }
    }

    @Override
    public boolean create(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?, ?, ?)");
            insertStatement.setLong(1, account.getIdNo());
            insertStatement.setString(2, account.getType());
            insertStatement.setInt(3, account.getAmount());
            insertStatement.setDate(4, new java.sql.Date(account.getDateOfCreation().getTime()));
            insertStatement.setInt(5, account.getIdClient());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean remove(int id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id = " + id;
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Account account) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE account SET idNo = ?, type = ?, amount = ?, dateOfCreation = ?, idClient = ? WHERE id = " + account.getId());
            insertUserStatement.setLong(1, account.getIdNo());
            insertUserStatement.setString(2, account.getType());
            insertUserStatement.setInt(3, account.getAmount());
            insertUserStatement.setDate(4, account.getDateOfCreation());
            insertUserStatement.setInt(5, account.getIdClient());
            insertUserStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getInt("id"))
                .setIdNo(rs.getLong("idNo"))
                .setType(rs.getString("type"))
                .setAmount(rs.getInt("amount"))
                .setDateOfCreation(new Date(System.currentTimeMillis()))
                .setIdClient(rs.getInt("idClient"))
                .build();
    }
}
