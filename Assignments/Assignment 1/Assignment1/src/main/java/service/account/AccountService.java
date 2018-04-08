package service.account;

import model.Account;

import java.sql.Date;
import java.util.List;

public interface AccountService {
    boolean remove(int id);

    boolean update(int id, long cardNo, String type, int amount, Date date, int idClient);

    boolean create(long cardNo, String type, int amount, Date date, int idClient);

    Account findById(int id);

    List<Account> findAll();

    void removeAll();
}
