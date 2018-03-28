package repository.account;

import model.Account;
import repository.EntityNotFoundException;

public interface AccountRepository {

    Account findById(Long id) throws EntityNotFoundException;

    boolean save(Account account);

    void removeAll();
}
