package repository.account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountRepository {

    List<Account> findAll();

    Account findById(int id) throws EntityNotFoundException;

    Account findByClientId(int idC) throws EntityNotFoundException;

    boolean create(Account account);

    void removeAll();

    boolean remove(int id);

    boolean update(Account account);
}
