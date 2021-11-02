package com.barlipdev.dwyf.model.auth;

import com.barlipdev.dwyf.model.user.User;

public class LoginResponse {

    private String authToken;
    private User user;

    public LoginResponse(String authToken, User user) {
        this.authToken = authToken;
        this.user = user;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
