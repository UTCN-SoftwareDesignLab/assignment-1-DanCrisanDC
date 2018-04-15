package repository.client;

import model.Client;
import model.validation.Notification;

import java.util.List;

public interface ClientRepository {
    List<Client> findAll();

    Notification<Client> findById(int id);

    boolean create(Client client);

    void removeAll();

    boolean remove(int id);

    boolean update(Client client);
}
