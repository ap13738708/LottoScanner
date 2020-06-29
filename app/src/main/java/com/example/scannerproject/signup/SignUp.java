package com.example.scannerproject.signup;

import com.google.gson.annotations.SerializedName;

public class SignUp {

    private String name;
    private String surname;
    private String line;
    private String tel;
    private String password;
    @SerializedName("password_confirmation")
    private String passwordConfirmation;

    public SignUp(String name, String surname, String line, String tel, String password, String passwordConfirmation) {
        this.name = name;
        this.surname = surname;
        this.line = line;
        this.tel = tel;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }


}
