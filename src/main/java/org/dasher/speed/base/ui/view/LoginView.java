package org.dasher.speed.base.ui.view;

import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.component.login.LoginOverlay;

import org.dasher.speed.security.SecurityUtils;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Login")
public class LoginView extends Div implements BeforeEnterObserver {

    private final LoginOverlay login = new LoginOverlay();

    public LoginView() {
        /*  ***** ESTA LÍNEA ES CLAVE *****  */
        login.setAction("login");          // <- endpoint de Spring Security
        login.setOpened(true);
        login.setForgotPasswordButtonVisible(false);   // opcional
        add(login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (SecurityUtils.isUserLoggedIn()) {
            event.forwardTo("");           // ya autenticado → home
        }
        if (event.getLocation().getQueryParameters()
                 .getParameters().containsKey("error")) {
            login.setError(true);          // credenciales incorrectas
        }
    }
}

