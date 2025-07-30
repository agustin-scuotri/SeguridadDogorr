package org.dasher.speed.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.dasher.speed.base.ui.view.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CommonSecurityConfig extends VaadinWebSecurity {

    private final DbUserDetailsService userDetailsService;
    private final PasswordEncoder      passwordEncoder;
    private final VaadinAuthSuccessHandler successHandler;

    public CommonSecurityConfig(DbUserDetailsService uds,
                                PasswordEncoder encoder,
                                VaadinAuthSuccessHandler successHandler) {
        this.userDetailsService = uds;
        this.passwordEncoder    = encoder;
        this.successHandler     = successHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                // login y assets
                .requestMatchers(
                    "/login", "/VAADIN/**", "/frontend/**", "/webjars/**", "/error", "/h2-console/**"
                ).permitAll()
                // rutas por rol
                .requestMatchers("/admin/**").hasRole(AppRoles.ADMIN.name())
                .requestMatchers("/professor/**").hasRole(AppRoles.PROFESSOR.name())
                .requestMatchers("/student/**").hasRole(AppRoles.STUDENT.name())
        );

        // Configura tu LoginView (VaadinOverlay / Router)
        setLoginView(http, LoginView.class);

        // Asocio mi success handler
        http.formLogin(form -> form
                .successHandler(successHandler)
        );

        // Finalmente, force el resto a autenticarse
        super.configure(http);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
