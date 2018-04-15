package repository.report;

import model.Report;
import model.builder.ReportBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportRepositoryMySQL implements ReportRepository{

    private final Connection connection;

    public ReportRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Report> findAll() {
        List<Report> reports = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from report";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                reports.add(getReportFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reports;
    }

    @Override
    public boolean addReport(int idEmployee, Date date, String activity) {

        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO report values (null, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setInt(1, idEmployee);
            insertUserStatement.setDate(2, date);
            insertUserStatement.setString(3, activity);
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Report getReportFromResultSet(ResultSet rs) throws SQLException {
        return new ReportBuilder()
                .setId(rs.getInt("id"))
                .setIdEmployee(rs.getInt("idEmployee"))
                .setActivity(rs.getString("activity"))
                .setDate(new Date(System.currentTimeMillis()))
                .build();
    }
}
