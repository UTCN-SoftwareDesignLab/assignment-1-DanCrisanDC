package repository.client;

import model.Client;
import repository.EntityNotFoundException;

import java.util.List;

public interface ClientRepository {
    List<Client> findAll();

    Client findById(int id) throws EntityNotFoundException;

    boolean create(Client client);

    void removeAll();

    boolean remove(int id);

    boolean update(Client client);
}
