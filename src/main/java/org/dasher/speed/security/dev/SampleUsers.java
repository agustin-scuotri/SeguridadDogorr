package org.dasher.speed.security.dev;

import org.dasher.speed.security.AppRoles;
import org.dasher.speed.security.domain.UserId;   // ← import nuevo
import java.util.List;
import java.util.UUID;

public final class SampleUsers {

    private SampleUsers() {}

    static final String SAMPLE_PASSWORD = "123";

    /* ---------- ADMIN ---------- */
    public static final UUID   ADMIN_ID       = UUID.randomUUID();
    public static final String ADMIN_USERNAME = "admin";

    static final DevUser ADMIN = DevUser.builder()
            .preferredUsername(ADMIN_USERNAME)
            .fullName("Alice Administrator")
            .userId(UserId.of(ADMIN_ID.toString()))   // ← aquí
            .password(SAMPLE_PASSWORD)
            .email("alice@example.com")
            .roles(AppRoles.ADMIN.name())   //  ← aquí
            .build();

    /* ---------- STUDENT ---------- */
    public static final UUID   STUDENT_ID       = UUID.randomUUID();
    public static final String STUDENT_USERNAME = "student";

    public static final String USER_USERNAME = STUDENT_USERNAME;
    public static final UUID   USER_ID       = STUDENT_ID;
    
    static final DevUser STUDENT = DevUser.builder()
            .preferredUsername(STUDENT_USERNAME)
            .fullName("Steve Student")
            .userId(UserId.of(STUDENT_ID.toString())) // ← y aquí
            .password(SAMPLE_PASSWORD)
            .email("steve@example.com")
            .roles(AppRoles.STUDENT.name()) //  ← y aquí
            .build();

    /** Lista unificada para DevUserDetailsService */
    static final List<DevUser> ALL_USERS = List.of(ADMIN, STUDENT);
}
