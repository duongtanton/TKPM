package com.tkpm.studentsmanagement.constant;

import java.util.Arrays;

public enum Role {
    ADMIN, USER;

    public static Role lookup(String roleStr) {
        for(Role role : Role.values()) {
            if(role.name().equalsIgnoreCase(roleStr)) {
                return role;
            }
        }
        throw new RuntimeException(String.format("Invalid role. It should be  %s",
                Arrays.asList(Role.values())));
    }
}
