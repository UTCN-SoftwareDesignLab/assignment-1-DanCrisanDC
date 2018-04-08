package service.client;

import model.Client;
import model.builder.ClientBuilder;
import repository.EntityNotFoundException;
import repository.client.ClientRepositoryMySQL;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private ClientRepositoryMySQL clientRepositoryMySQL;

    public ClientServiceImpl(ClientRepositoryMySQL clientRepositoryMySQL) {
        this.clientRepositoryMySQL = clientRepositoryMySQL;
    }

    @Override
    public boolean update(int id, String name, long idCard, String address, String CNP) {
        Client c = new ClientBuilder()
                .setId(id)
                .setName(name)
                .setIdCardNo(idCard)
                .setAddress(address)
                .setCNP(CNP)
                .build();

        return clientRepositoryMySQL.update(c);
    }

    @Override
    public boolean create(String name, long idCard, String address, String CNP) {
        Client c = new ClientBuilder()
                .setName(name)
                .setIdCardNo(idCard)
                .setAddress(address)
                .setCNP(CNP)
                .build();

        return clientRepositoryMySQL.create(c);
    }

    @Override
    public boolean remove(int id) {
        return clientRepositoryMySQL.remove(id);
    }

    @Override
    public Client findById(int id) {
        try {
            return clientRepositoryMySQL.findById(id);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Client> findAll() {
        return clientRepositoryMySQL.findAll();
    }

    @Override
    public void removeAll() {
        clientRepositoryMySQL.removeAll();
    }
}
