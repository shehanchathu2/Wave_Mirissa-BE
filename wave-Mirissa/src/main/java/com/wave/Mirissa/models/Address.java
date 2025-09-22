package com.wave.Mirissa.models;

import jakarta.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contactName;
    private String street;
    private String city;
    private String state;
    private String area;
    private String zipCode;
    private String phone;
    private String country = "Sri Lanka";

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Address() {}

    public Address(Long id, String contactName, String street, String city, String state,
                   String area, String zipCode, String phone, String country, User user) {
        this.id = id;
        this.contactName = contactName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.area = area;
        this.zipCode = zipCode;
        this.phone = phone;
        this.country = country;
        this.user = user;
    }

    // Getters
    public Long getId() { return id; }
    public String getContactName() { return contactName; }
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getArea() { return area; }
    public String getZipCode() { return zipCode; }
    public String getPhone() { return phone; }
    public String getCountry() { return country; }
    public User getUser() { return user; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public void setStreet(String street) { this.street = street; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setArea(String area) { this.area = area; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setCountry(String country) { this.country = country; }
    public void setUser(User user) { this.user = user; }
}