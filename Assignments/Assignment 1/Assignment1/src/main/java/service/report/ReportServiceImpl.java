package service.report;

import repository.ReportRepositoryMySQL;

import java.sql.Date;

public class ReportServiceImpl implements ReportService{

    private ReportRepositoryMySQL reportRepositoryMySQL;

    public ReportServiceImpl(ReportRepositoryMySQL reportRepositoryMySQL) {
        this.reportRepositoryMySQL = reportRepositoryMySQL;
    }

    @Override
    public boolean addR(int idE, Date date, String activity) {
        return reportRepositoryMySQL.addR(idE, date, activity);
    }
}
