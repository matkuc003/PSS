package com.example.PSO.gui;

import com.example.PSO.models.Role;
import com.example.PSO.models.User;
import com.example.PSO.service.RoleService;
import com.example.PSO.service.UserService;
import com.vaadin.annotations.Title;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@Title("Register user")
@SpringUI(path = "/register")
public class RegisterView extends UI {
    private UserService userService;
    private User registerUser;
    private RoleService roleService;

    @Autowired
    public RegisterView(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
        this.registerUser = new User();
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);

        VerticalLayout root = new VerticalLayout();
        root.setMargin(new MarginInfo(false, true));

        Button signIn = new Button("Sign in");
        signIn.setStyleName("link");
        signIn.addClickListener(clickEvent -> {
            getUI().getPage().setLocation("/login");
        });

        FormLayout registerLayout = new FormLayout();
        registerLayout.setWidth(null);

        TextField emailTextField = new TextField("Email: ");
        PasswordField passwordField1 = new PasswordField("Password: ");
        PasswordField passwordField2 = new PasswordField("Repeat password: ");
        TextField firstNameTextField = new TextField("First name: ");
        TextField lastNameTextField = new TextField("Last name: ");
        TextField companyName = new TextField("Company name: ");
        TextField companyNip = new TextField("Company NIP: ");
        TextField companyAddress = new TextField("Company address: ");

        binder.forField(emailTextField).bind("email");
        binder.forField(passwordField1).bind("password");
        binder.forField(passwordField2).bind("password");
        binder.forField(firstNameTextField).bind("name");
        binder.forField(lastNameTextField).bind("lastName");
        binder.forField(companyName).bind("companyName");
        binder.forField(companyNip).bind("companyNip");
        binder.forField(companyAddress).bind("companyAddress");
        binder.setBean(registerUser);

        Button registerBtn = new Button("Register");
        registerBtn.addClickListener(clickEvent -> {
            if (!passwordField1.getValue().equals(passwordField2.getValue()))
                Notification.show("Passwords must be identical", Notification.Type.ERROR_MESSAGE);
            else {
                Role roleUser = roleService.getRoleByName("ROLE_USER");
                registerUser.addRole(roleUser);
                ResponseEntity<User> userResponseEntity = userService.registerUser(registerUser);
                if (userResponseEntity.getStatusCode().is4xxClientError())
                    Notification.show(userResponseEntity.getStatusCode().toString(), Notification.Type.ERROR_MESSAGE);
                else {
                    Notification.show("Hello " + userResponseEntity.getBody().getName() + ". You are registered.", Notification.Type.HUMANIZED_MESSAGE);
                    registerUser = new User();
                    binder.setBean(registerUser);
                }
            }
        });

        registerLayout.addComponents(emailTextField, passwordField1, passwordField2, firstNameTextField, lastNameTextField, companyName, companyAddress, companyNip, registerBtn, signIn);
        root.addComponents(registerLayout);
        root.setComponentAlignment(registerLayout, Alignment.MIDDLE_CENTER);
        root.setSizeFull();
        setContent(root);
    }
}
