package org.dasher.speed.base.ui.view;

import org.dasher.speed.base.domain.*;
import org.dasher.speed.base.service.*;
import org.dasher.speed.security.AppRoles;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import jakarta.annotation.security.RolesAllowed;
@RolesAllowed("STUDENT")
@PageTitle("Mi Perfil - Alumno")
@Route(value = "student/profile", layout = MainLayout.class)
public class StudentProfileView extends VerticalLayout {

    private final StudentService studentService;
    private final PersonService  personService;
    private final AddressService addressService;

    public StudentProfileView(StudentService studentService,
                              PersonService personService,
                              AddressService addressService) {
        this.studentService = studentService;
        this.personService  = personService;
        this.addressService = addressService;

        User u = VaadinSession.getCurrent().getAttribute(User.class);
        if (u == null || u.getRole() != AppRoles.STUDENT) {
            UI.getCurrent().navigate("login");
            return;
        }

        setSizeFull();

        H2 header = new H2();
        header.add(new Icon(VaadinIcon.USER),
                   new Span(" Mi Perfil de Alumno"));
        add(header);

        studentService.findByUserId(u.getId()).ifPresentOrElse(student -> {
            if (student.getAddress() == null) student.setAddress(new Address());
            Binder<Student> binder = new Binder<>(Student.class);

            TextField name    = new TextField("Nombre");
            TextField email   = new TextField("Email");
            TextField phone   = new TextField("Teléfono");
            TextField street  = new TextField("Calle");
            TextField city    = new TextField("Ciudad");
            TextField state   = new TextField("Provincia");
            TextField country = new TextField("País");
            name.setPrefixComponent(new Icon(VaadinIcon.USER));
            email.setPrefixComponent(new Icon(VaadinIcon.ENVELOPE));
            phone.setPrefixComponent(new Icon(VaadinIcon.PHONE));
            street.setPrefixComponent(new Icon(VaadinIcon.MAP_MARKER));
            city.setPrefixComponent(new Icon(VaadinIcon.BUILDING));
            state.setPrefixComponent(new Icon(VaadinIcon.FLAG));
            country.setPrefixComponent(new Icon(VaadinIcon.GLOBE));

            binder.forField(name).asRequired("Requerido")
                  .bind(Student::getName, Student::setName);
            binder.forField(email).asRequired("Requerido")
                  .bind(Student::getEmail, Student::setEmail);
            binder.forField(phone).bind(Student::getPhone, Student::setPhone);

            binder.forField(street).bind(
                    s -> s.getAddress().getStreet(),
                    (s,v) -> s.getAddress().setStreet(v));
            binder.forField(city).bind(
                    s -> s.getAddress().getCity(),
                    (s,v) -> s.getAddress().setCity(v));
            binder.forField(state).bind(
                    s -> s.getAddress().getState(),
                    (s,v) -> s.getAddress().setState(v));
            binder.forField(country).bind(
                    s -> s.getAddress().getCountry(),
                    (s,v) -> s.getAddress().setCountry(v));

            FormLayout form = new FormLayout(
                name, email, phone,
                street, city, state, country
            );
            binder.readBean(student);

            Button save = new Button("Guardar cambios", evt -> {
                if (binder.writeBeanIfValid(student)) {
                    addressService.save(student.getAddress());
                    personService.save(student);
                    studentService.save(student);
                    Notification.show("Perfil actualizado",
                                      1500, Notification.Position.BOTTOM_START);
                }
            });
            save.setIcon(new Icon(VaadinIcon.CHECK));
            add(form, save);
        }, () -> {
            Notification.show("Perfil no encontrado", 3000,
                              Notification.Position.MIDDLE);
            UI.getCurrent().navigate("");
        });
    }
}
