package org.dasher.speed.security;

public enum AppRoles {
    ADMIN,
    PROFESSOR,
    STUDENT;

    public String asRole() {
        return "ROLE_" + name();
    }
}
