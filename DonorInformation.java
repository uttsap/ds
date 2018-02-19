package com.afeastoffriends.doctorsaathi;

/**
 * Created by Acer-PC on 2/10/2017.
 */


public class DonorInformation {

    int id;
    String name,email,phone , bloodGroup, lastDate;

    public DonorInformation(int id, String name, String email, String phone, String bloodGroup, String lastDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.lastDate = lastDate;
    }

    public DonorInformation(int id, String name, String email, String phone, String bloodGroup) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
    }

    public DonorInformation(String name, String email, String phone, String bloodGroup) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
    }

    public DonorInformation(String name, String email, String phone, String bloodGroup, String lastDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.lastDate = lastDate;
    }
}
