package com.jwt.model;

import java.util.Collection;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class User {

    private String username;
    private String password;
    private String email;
    private String rol;
    private boolean enabled;

    public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

    public User() {
    }

    public User(String username, String password, String email, String rol, boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.rol = rol;
        this.enabled = enabled;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
