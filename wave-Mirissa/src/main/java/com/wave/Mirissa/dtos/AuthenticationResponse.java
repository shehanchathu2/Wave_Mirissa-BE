package com.wave.Mirissa.dtos;

public class AuthenticationResponse {
    private String jwt;
    private String email;
    private String role;
    private String username;
    private Long id; // 👈 Add this

    // Constructor
    public AuthenticationResponse(String jwt, String email, String role, String username, Long id) {
        this.jwt = jwt;
        this.email = email;
        this.role = role;
        this.username = username;
        this.id = id;
    }

    // Getters and Setters
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() { // 👈 Add getter
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
