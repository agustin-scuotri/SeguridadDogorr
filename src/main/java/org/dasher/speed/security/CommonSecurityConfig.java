package org.dasher.speed.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.dasher.speed.base.ui.view.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CommonSecurityConfig extends VaadinWebSecurity {

    private final DbUserDetailsService uds;
    private final PasswordEncoder      encoder;

    public CommonSecurityConfig(DbUserDetailsService uds, PasswordEncoder encoder) {
        this.uds     = uds;
        this.encoder = encoder;
    }

    @Override                 // <<<<<<  ESTE método manda
    protected void configure(HttpSecurity http) throws Exception {

        /* 1️⃣  Rutas públicas / protegidas */
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/login").permitAll()
                .requestMatchers("/admin/**").hasRole(AppRoles.ADMIN.name())
                .requestMatchers("/professor/**").hasRole(AppRoles.PROFESSOR.name())
                .requestMatchers("/student/**").hasRole(AppRoles.STUDENT.name())
        );
        /* 2️⃣  Login Vaadin */
        setLoginView(http, LoginView.class);   // muestra /login

        /* 3️⃣  ¡Siempre al final! */
        super.configure(http);                 // añade .anyRequest().authenticated()
    }

    /* Proveedor con usuarios de BD */
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        var p = new DaoAuthenticationProvider();
        p.setUserDetailsService(uds);
        p.setPasswordEncoder(encoder);
        return p;
    }


}
