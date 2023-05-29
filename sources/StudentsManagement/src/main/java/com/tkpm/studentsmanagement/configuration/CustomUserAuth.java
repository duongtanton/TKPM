package com.tkpm.studentsmanagement.configuration;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserAuth extends User {
    private Long id;

    public CustomUserAuth(Long id, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.setId(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
