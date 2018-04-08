package model;

public class Client {

    private int id;
    private String name;
    private long idCardNo;
    private String address;
    private String CNP;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIdCardNo(long idCardNo) {
        this.idCardNo = idCardNo;
    }

    public long getIdCardNo() {
        return idCardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }
}
