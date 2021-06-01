package com.flightapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String first_name;
    
    private String last_name;
    
    private String address;
    
    private String email;
    private String phone_number;

    public Long getId() {
            return this.id;
    }

    public void setId(Long id) {
            this.id = id;
    }


    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }


    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return this.phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    
    public Guest(){ }

    public Guest(String first_name, String last_name, String address, String email, String phone_number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.email = email;
        this.phone_number = phone_number;
        
    }

}
