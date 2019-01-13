package dev.ibrahhout.shinystoreadmin.Models;

/**
 * This file is created By: ( Ibrahim A. Elhout ) on 07/27/2018 at 10:25 PM
 * Project : LaundryAppAdmin
 * Contacts:
 * Email: Ibrahimhout.dev@gmail.com
 * Phone: 00972598825662
 **/
public class UserModel {
    private String Address;
    private String email;
    private String name;

    public UserModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
