package com.example.androidexamples;


import com.google.gson.annotations.SerializedName;

public class Contact {

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {

        return email;
    }
}
