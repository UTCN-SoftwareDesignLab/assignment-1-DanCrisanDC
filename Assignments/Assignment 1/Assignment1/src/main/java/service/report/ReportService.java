package service.report;

import model.Report;

import java.sql.Date;
import java.util.List;

public interface ReportService {

    boolean addReport(int idEmployee, Date date, String activity);

    List<Report> findAll();
}
