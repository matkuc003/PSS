package com.example.PSO.gui;

import com.example.PSO.models.User;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.security.core.context.SecurityContextHolder;


@Title("User Panel")
@SpringUI(path = "/panel")
public class MainView extends UI {

    private User loggedUser;

    private VerticalLayout root;
    private VerticalLayout mainView;

    @Override
    protected void init(VaadinRequest request) {
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
                    // TODO: add delegation view
                    // mainView.addComponents(new DelegationView());
                }
        );

        Button profileButton = new Button("Profile");
        profileButton.setWidth(200, Unit.PIXELS);
        profileButton.addClickListener(clickEvent -> {
                    mainView.removeAllComponents();
                    // TODO: add profile view:
                    // mainView.addComponents(new ProfileView());
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

        root.addComponents(topBar, mainView);
        root.setComponentAlignment(topBar, Alignment.TOP_CENTER);
        root.setSizeFull();
        setContent(root);
    }
}
