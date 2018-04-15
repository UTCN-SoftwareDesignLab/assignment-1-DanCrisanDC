package repository.client;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryMySQL implements ClientRepository{
    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                clients.add(getClientFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public Notification<Client> findById(int id) {

        Notification<Client> clientNotification = new Notification<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                clientNotification.setResult(getClientFromResultSet(rs));
            } else {
                clientNotification.addError("Client with " + id + " was not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            clientNotification.addError("Database did not respond accordingly");
        }
        return clientNotification;
    }

    @Override
    public boolean create(Client client) {

        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)");
            insertStatement.setString(1, client.getName());
            insertStatement.setLong(2, client.getIdCardNo());
            insertStatement.setString(3, client.getAddress());
            insertStatement.setString(4, client.getCNP());
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
            String sql = "DELETE from client where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean remove(int id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client where id = " + id;
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Client client) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("UPDATE client SET name = ?, idCardNo = ?, address = ?, CNP = ? WHERE id = " + client.getId());
            insertUserStatement.setString(1, client.getName());
            insertUserStatement.setLong(2, client.getIdCardNo());
            insertUserStatement.setString(3, client.getAddress());
            insertUserStatement.setString(4, client.getCNP());
            insertUserStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setId(rs.getInt("id"))
                .setName(rs.getString("name"))
                .setIdCardNo(rs.getLong("idCardNo"))
                .setAddress(rs.getString("address"))
                .setCNP(rs.getString("CNP"))
                .build();
    }

}
