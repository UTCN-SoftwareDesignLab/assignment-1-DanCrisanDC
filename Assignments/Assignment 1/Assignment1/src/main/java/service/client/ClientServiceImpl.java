package service.client;

import model.Client;
import model.builder.ClientBuilder;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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

        return clientRepository.update(c);
    }

    @Override
    public boolean create(String name, long idCard, String address, String CNP) {
        Client c = new ClientBuilder()
                .setName(name)
                .setIdCardNo(idCard)
                .setAddress(address)
                .setCNP(CNP)
                .build();

        return clientRepository.create(c);
    }

    @Override
    public boolean remove(int id) {
        return clientRepository.remove(id);
    }

    @Override
    public Client findById(int id) {
        try {
            return clientRepository.findById(id);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public void removeAll() {
        clientRepository.removeAll();
    }
}
