package org.dasher.speed.security;   // ⬅ Pon el paquete que corresponda

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {}        // util-class, no instanciable

    /** Devuelve <code>true</code> si el usuario YA está autenticado. */
    public static boolean isUserLoggedIn() {
        Authentication auth = SecurityContextHolder
                                  .getContext()
                                  .getAuthentication();

        return auth != null
                && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getPrincipal());
    }
}
