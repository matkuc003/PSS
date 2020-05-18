package com.example.PSO.gui;

import com.example.PSO.models.Role;
import com.example.PSO.models.User;
import com.example.PSO.service.DelegationService;
import com.example.PSO.service.PdfService;
import com.example.PSO.service.RoleService;
import com.example.PSO.service.UserService;
import com.vaadin.annotations.Title;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.TimeZone;


@Title("User Panel")
@SpringUI(path = "/panel")
public class MainView extends UI {

    private UserService userService;
    private DelegationService delegationService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private PdfService pdfService;

    @Autowired
    public MainView(UserService userService,RoleService roleService, DelegationService delegationService, PasswordEncoder passwordEncoder, PdfService pdfService) {
        this.userService = userService;
        this.delegationService = delegationService;
        this.roleService=roleService;
        this.passwordEncoder = passwordEncoder;
        this.pdfService = pdfService;
    }

    private User loggedUser;

    private VerticalLayout root;
    private VerticalLayout mainView;

    @Override
    protected void init(VaadinRequest request) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        root = new VerticalLayout();
        mainView = new VerticalLayout();
        mainView.setSizeFull();

        VerticalLayout topBar = new VerticalLayout();
        topBar.setWidth(60, Unit.PERCENTAGE);
        topBar.setMargin(new MarginInfo(false, true));

        Label userInfo = new Label("Hey " + loggedUser.getName() + " " + loggedUser.getLastName());
        topBar.addComponent(userInfo);
        topBar.setComponentAlignment(userInfo, Alignment.TOP_CENTER);

        HorizontalLayout menu = new HorizontalLayout();

        Button delegationsButton = new Button("Delegations");
        delegationsButton.setWidth(150, Unit.PIXELS);
        delegationsButton.addClickListener(clickEvent -> {
                    mainView.removeAllComponents();
                    mainView.addComponents(new DelegationView(loggedUser,delegationService, pdfService));
                }
        );

        Button profileButton = new Button("Profile");
        profileButton.setWidth(150, Unit.PIXELS);
        profileButton.addClickListener(clickEvent -> {
                    mainView.removeAllComponents();
                    mainView.addComponents(new ProfileView(userService, loggedUser, passwordEncoder));
                }
        );
        Button adminButton = new Button("Admin Panel");
        adminButton.setWidth(150, Unit.PIXELS);
        adminButton.addClickListener(clickEvent -> {
            if(loggedUser.getRoles().stream().anyMatch(r->r.getRoleName().equals("ROLE_ADMIN"))) {
                mainView.removeAllComponents();
                mainView.addComponents(new AdminView(userService,delegationService, roleService, loggedUser));
            }
            else{
                new Notification("You don't have permission",
                        "",
                        Notification.Type.WARNING_MESSAGE, true)
                        .show(Page.getCurrent());
            }
                }
        );

        Button signOutButton = new Button("Sign out");
        signOutButton.setWidth(150, Unit.PIXELS);
        signOutButton.addClickListener(clickEvent -> getUI().getPage().setLocation("/logout"));

        Optional<Role> role_user = loggedUser.getRoles().stream().filter(r -> r.getRoleName().equals("ROLE_USER")).findAny();
        Optional<Role> role_admin = loggedUser.getRoles().stream().filter(r -> r.getRoleName().equals("ROLE_ADMIN")).findAny();
        if(role_user.isPresent() && role_admin.isPresent()) {
            menu.addComponents(delegationsButton, profileButton,adminButton, signOutButton);
            menu.setComponentAlignment(delegationsButton, Alignment.MIDDLE_LEFT);
            menu.setComponentAlignment(profileButton, Alignment.MIDDLE_CENTER);
            menu.setComponentAlignment(adminButton, Alignment.TOP_RIGHT);
            menu.setComponentAlignment(signOutButton, Alignment.MIDDLE_RIGHT);
        } else if (role_user.isPresent()) {
            menu.addComponents(delegationsButton, profileButton, signOutButton);
            menu.setComponentAlignment(delegationsButton, Alignment.MIDDLE_LEFT);
            menu.setComponentAlignment(profileButton, Alignment.MIDDLE_CENTER);
            menu.setComponentAlignment(signOutButton, Alignment.MIDDLE_RIGHT);
        } else if (role_admin.isPresent()) {
            menu.addComponents(adminButton, signOutButton);
            menu.setComponentAlignment(adminButton, Alignment.MIDDLE_LEFT);
            menu.setComponentAlignment(signOutButton, Alignment.MIDDLE_RIGHT);
        } else {
            System.out.println("You are not authorized to use the service");
        }
        menu.setSizeFull();

        topBar.addComponent(menu);
        topBar.setHeightUndefined();

        root.addComponents(topBar, mainView);
        root.setComponentAlignment(topBar, Alignment.TOP_CENTER);
        root.setComponentAlignment(mainView, Alignment.MIDDLE_CENTER);
        root.setWidth(100, Unit.PERCENTAGE);
        setContent(root);
    }
}
