package org.dasher.speed.security;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.UIInitEvent;
import com.vaadin.flow.server.UIInitListener;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.component.UI;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.dasher.speed.base.domain.User;
import org.dasher.speed.base.service.UserService;

@Component
public class SecurityServiceInitListener implements VaadinServiceInitListener {

    private final UserService userService;

    public SecurityServiceInitListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        // acá corrige: sobre el source (VaadinService) se agrega el UIInitListener
        event.getSource().addUIInitListener(new UIInitListener() {
            @Override
            public void uiInit(UIInitEvent uiInitEvent) {
                // antes de cada navegación
                uiInitEvent.getUI().addBeforeEnterListener(before -> {
                    VaadinSession session = VaadinSession.getCurrent();
                    if (session == null) return;
                    if (session.getAttribute(User.class) != null) {
                        return; // ya está cargado
                    }

                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    if (auth != null && auth.isAuthenticated()
                            && auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User springUser) {

                        String username = springUser.getUsername();
                        userService.findByUsername(username).ifPresent(domainUser -> {
                            session.setAttribute(User.class, domainUser);
                        });
                    }
                });
            }
        });
    }
}
