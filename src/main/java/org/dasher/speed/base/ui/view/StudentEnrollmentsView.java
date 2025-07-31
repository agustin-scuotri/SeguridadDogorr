package org.dasher.speed.base.ui.view;

import org.dasher.speed.base.domain.Seat;
import org.dasher.speed.base.domain.User;
import org.dasher.speed.base.service.SeatService;
import org.dasher.speed.security.AppRoles;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import jakarta.annotation.security.RolesAllowed;

import java.util.List;
@RolesAllowed("STUDENT")
@PageTitle("Mis Inscripciones")
@Route(value = "student/enrollments", layout = MainLayout.class)
public class StudentEnrollmentsView extends VerticalLayout {

    private final SeatService seatService;
    private final Grid<Seat>  grid = new Grid<>(Seat.class, false);

    public StudentEnrollmentsView(SeatService seatService) {
        this.seatService = seatService;
        setSizeFull();

        H2 header = new H2();
        header.add(new Icon(VaadinIcon.CLIPBOARD_TEXT),
                   new Span(" Mis Inscripciones"));
        add(header);

        grid.addColumn(Seat::getId).setHeader("ID").setWidth("70px");
        grid.addColumn(s -> s.getCourse().getName())
            .setHeader("Curso").setAutoWidth(true);
        grid.addColumn(Seat::getYear).setHeader("Año");
        grid.addColumn(Seat::getMark).setHeader("Nota");
        grid.setSizeFull();
        add(grid);

        User current = VaadinSession.getCurrent().getAttribute(User.class);
        if (current == null || current.getRole() != AppRoles.STUDENT) {
            getUI().ifPresent(ui -> ui.navigate("login"));
        } else {
            List<Seat> inscripciones =
                seatService.findByStudentUserId(current.getId());
            grid.setItems(inscripciones);
        }
    }
}
