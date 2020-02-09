package net.nh.domain;

public class Customer {

    private Long id;
    private Integer version;
    private String forename;
    private String surname;

    public Customer(String forename, String surname) {
        this.forename = forename;
        this.surname = surname;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    private void setVersion(Integer version) {
        this.version = version;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
