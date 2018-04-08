package model.builder;


import model.Client;

public class ClientBuilder {
    private Client client;

    public ClientBuilder() {
        client = new Client();
    }

    public ClientBuilder setId(int id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setAddress(String address) {
        client.setAddress(address);
        return this;
    }

    public ClientBuilder setCNP(String CNP) {
        client.setCNP(CNP);
        return this;
    }

    public ClientBuilder setIdCardNo(long idCardNo) {
        client.setIdCardNo(idCardNo);
        return this;
    }

    public Client build() {
        return client;
    }
}
