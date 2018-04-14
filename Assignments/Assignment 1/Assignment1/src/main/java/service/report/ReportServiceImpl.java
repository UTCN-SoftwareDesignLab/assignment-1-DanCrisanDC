package service.report;

import model.Report;
import repository.Report.ReportRepository;

import java.sql.Date;
import java.util.List;

public class ReportServiceImpl implements ReportService{

    private ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public boolean addReport(int idEmployee, Date date, String activity) {
        return reportRepository.addReport(idEmployee, date, activity);
    }

    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }
}
