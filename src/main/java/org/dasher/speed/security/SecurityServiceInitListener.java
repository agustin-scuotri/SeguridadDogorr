package org.dasher.speed.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;
import org.dasher.speed.base.domain.User;
import org.dasher.speed.base.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Cada vez que Vaadin inicializa una nueva UI,
 * comprobamos si Spring Security tiene un usuario autenticado
 * y lo guardamos en la VaadinSession.
 */
@Component
public class SecurityServiceInitListener implements VaadinServiceInitListener {

    private final UserRepository userRepo;

    @Autowired
    public SecurityServiceInitListener(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiInit -> {
            UI ui = uiInit.getUI();
            // Antes de cada navegación...
            ui.addBeforeEnterListener((BeforeEnterEvent before) -> {
                if (SecurityUtils.isUserLoggedIn()) {
                    String username = SecurityUtils.getUsername();
                    userRepo.findByUsername(username).ifPresent(user ->
                        VaadinSession.getCurrent().setAttribute(User.class, user)
                    );
                }
            });
        });
    }
}
