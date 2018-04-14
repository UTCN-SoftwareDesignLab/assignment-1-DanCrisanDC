package service.account;

import model.Account;
import model.builder.AccountBuilder;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;

import java.sql.Date;
import java.util.List;

public class AccountServiceImpl implements AccountService{

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean remove(int id) {
       return accountRepository.remove(id);
    }


    @Override
    public boolean update(int id, long cardNo, String type, int amount, Date date, int idClient) {
        Account a = new AccountBuilder()
                .setId(id)
                .setIdNo(cardNo)
                .setType(type)
                .setAmount(amount)
                .setDateOfCreation(date)
                .setIdClient(idClient)
                .build();

        return accountRepository.update(a);
    }

    @Override
    public boolean create(long cardNo, String type, int amount, Date date, int idClient) {
        Account account = new AccountBuilder()
                .setIdNo(cardNo)
                .setType(type)
                .setAmount(amount)
                .setDateOfCreation(new Date(System.currentTimeMillis()))
                .setIdClient(idClient)
                .build();

        return accountRepository.create(account);
    }

    @Override
    public Account findById(int id) {
        try {
            return accountRepository.findById(id);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public void removeAll() {
        accountRepository.removeAll();
    }

}
