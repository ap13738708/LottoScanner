package com.example.scannerproject.login;

import com.google.gson.annotations.SerializedName;

public class AuthDao {
    @SerializedName("access_token")
    private String token;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_at")
    private String tokenExpires;

    public AuthDao(String token, String tokenType, String tokenExpires ) {
        this.token = token;
        this.tokenType = tokenType;
        this.tokenExpires = tokenExpires;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }
}
