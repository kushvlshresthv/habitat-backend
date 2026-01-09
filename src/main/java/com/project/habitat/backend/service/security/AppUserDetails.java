package com.project.habitat.backend.service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AppUserDetails implements UserDetails {
    private final Integer userId;
    private final String username;
    private final String password;
    private final String timezone;

    public AppUserDetails(Integer userId, String username, String password, String timezone) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.timezone = timezone;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getTimeZone() {
        return timezone;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
