package org.dasher.speed.security;

import org.dasher.speed.base.domain.User;
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
        User appUser = repo.findByUsername(username)
            .orElseThrow(() ->
                new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Construyo el UserDetails de Spring Security
        return org.springframework.security.core.userdetails.User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(appUser.getRole().name())  // ADMIN, PROFESSOR o STUDENT
                .build();
    }
}
