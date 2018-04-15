package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator implements Validator{

    private final Client client;
    private final List<String> errors;

    public ClientValidator(Client client) {
        this.client = client;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateCNP(client.getCNP());
        validateCardNr(client.getIdCardNo());
        return errors.isEmpty();
    }

    private void validateCNP(String CNP) {
        if(!CNP.matches("[0-9]+"))
            errors.add("CNP should contain only digits");
        if(CNP.length()!=10)
            errors.add("CNP should be 10 digits long");
    }

    private void validateCardNr(Long cardNr) {
        if(cardNr < 100000 || cardNr > 999999)
            errors.add("ID card number should be 6 digits long");
    }

    public List<String> getErrors() {
        return errors;
    }
}
