package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator implements  Validator {

    private final Account account;
    private final List<String> errors;

    public AccountValidator(Account account) {
        this.account = account;
        errors = new ArrayList<>();
    }

    private void validateType(String type) {
        if(!type.equals("Spendings") && !type.equals("Savings"))
            errors.add("Account must be of type 'Spendings' or 'Savings'");
    }

    private void validateAmount(int amount) {
        if(amount < 0)
            errors.add("Your account needs to have a positive balance at all times");
    }

    public boolean validate() {

        validateType(account.getType());
        validateAmount(account.getAmount());
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }
}
