package repository;

import java.sql.Date;

public interface ReportRepository {

    boolean addR(int idE, Date date, String activity);
}
