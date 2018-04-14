package model.builder;

import model.Report;

import java.sql.Date;

public class ReportBuilder {

    private Report report;

    public ReportBuilder() {
        report = new Report();
    }

    public ReportBuilder setId(int id) {
        report.setId(id);
        return this;
    }

    public ReportBuilder setIdEmployee(int idEmployee) {
        report.setIdEmployee(idEmployee);
        return this;
    }

    public ReportBuilder setActivity(String activity) {
        report.setActivity(activity);
        return this;
    }

    public ReportBuilder setDate(Date date) {
        report.setDate(date);
        return this;
    }

    public Report build() {
        return report;
    }
}
