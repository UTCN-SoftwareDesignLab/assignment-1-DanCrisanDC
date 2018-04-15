package service.client;

import model.Client;
import model.validation.Notification;

import java.util.List;

public interface ClientService {
    boolean update(int id, String name, long idCard, String address, String CNP);

    Notification<Boolean> create(String name, long idCard, String address, String CNP);

    boolean remove(int id);

    Notification<Client> findById(int id);

    List<Client> findAll();

    void removeAll();
}
