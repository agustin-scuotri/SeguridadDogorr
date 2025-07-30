package org.dasher.speed.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

public final class SecurityUtils {

    private SecurityUtils() {
        // utilería estática
    }

    /**
     * Dice si hay un usuario autenticado en Spring Security
     */
    public static boolean isUserLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null
            && auth.isAuthenticated()
            && !(auth instanceof AnonymousAuthenticationToken);
    }

    /**
     * Devuelve el nombre del usuario que Spring Security puso en el Authentication.
     * 
     * @return el username, o null si no hay ninguno
     */
    public static String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null) ? auth.getName() : null;
    }
}
