package com.example.PSO.gui;

import com.example.PSO.models.User;
import com.example.PSO.service.DelegationService;
import com.example.PSO.service.PdfService;
import com.example.PSO.service.UserService;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.TimeZone;


@Title("User Panel")
@SpringUI(path = "/panel")
public class MainView extends UI {

    private UserService userService;
    private DelegationService delegationService;
    private PasswordEncoder passwordEncoder;
    private PdfService pdfService;

    @Autowired
    public MainView(UserService userService, DelegationService delegationService, PasswordEncoder passwordEncoder, PdfService pdfService) {
        this.userService = userService;
        this.delegationService = delegationService;
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
        delegationsButton.setWidth(200, Unit.PIXELS);
        delegationsButton.addClickListener(clickEvent -> {
                    mainView.removeAllComponents();
                    mainView.addComponents(new DelegationView(loggedUser,delegationService, pdfService));
                }
        );

        Button profileButton = new Button("Profile");
        profileButton.setWidth(200, Unit.PIXELS);
        profileButton.addClickListener(clickEvent -> {
                    mainView.removeAllComponents();
                    mainView.addComponents(new ProfileView(userService, loggedUser, passwordEncoder));
                }
        );

        Button signOutButton = new Button("Sign out");
        signOutButton.setWidth(200, Unit.PIXELS);
        signOutButton.addClickListener(clickEvent -> getUI().getPage().setLocation("/logout"));

        menu.addComponents(delegationsButton, profileButton, signOutButton);
        menu.setSizeFull();
        menu.setComponentAlignment(delegationsButton, Alignment.MIDDLE_LEFT);
        menu.setComponentAlignment(profileButton, Alignment.MIDDLE_CENTER);
        menu.setComponentAlignment(signOutButton, Alignment.MIDDLE_RIGHT);

        topBar.addComponent(menu);
        topBar.setHeightUndefined();

        root.addComponents(topBar, mainView);
        root.setComponentAlignment(topBar, Alignment.TOP_CENTER);
        root.setComponentAlignment(mainView, Alignment.MIDDLE_CENTER);
        root.setWidth(100, Unit.PERCENTAGE);
        setContent(root);
    }
}
