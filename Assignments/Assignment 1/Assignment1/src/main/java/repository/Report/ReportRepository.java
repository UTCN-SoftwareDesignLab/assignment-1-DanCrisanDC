package repository.report;

import model.Report;

import java.sql.Date;
import java.util.List;

public interface ReportRepository {

    List<Report> findAll();

    boolean addReport(int idEmployee, Date date, String activity);
}
