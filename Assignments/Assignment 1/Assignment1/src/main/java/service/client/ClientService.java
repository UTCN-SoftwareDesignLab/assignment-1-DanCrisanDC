package service.client;

import model.Client;

import java.util.List;

public interface ClientService {
    boolean update(int id, String name, long idCard, String address, String CNP);

    boolean create(String name, long idCard, String address, String CNP);

    boolean remove(int id);

    Client findById(int id);

    List<Client> findAll();

    void removeAll();
}
