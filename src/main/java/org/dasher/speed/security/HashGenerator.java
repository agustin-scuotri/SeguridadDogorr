package org.dasher.speed.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "alumno";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Hash generado: " + encodedPassword);
    }
}
