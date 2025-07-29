package org.dasher.speed.security;

import org.dasher.speed.base.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class DbUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public DbUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // Usa el nombre completo para tu entidad
        var appUser = repo.findByUsername(username)
                          .orElseThrow(() ->
                               new UsernameNotFoundException("No existe: " + username));

        // Builder de Spring Security (nombre completo para evitar confusión)
        return org.springframework.security.core.userdetails.User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(appUser.getRole().name())   // ADMIN / STUDENT / PROFESSOR
                .build();
    }
}
