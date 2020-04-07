package com.example.PSO.gui;

import com.example.PSO.models.User;
import com.example.PSO.service.UserService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ProfileView extends HorizontalLayout {

    private UserService userService;
    private User loggedUser;
    private PasswordEncoder passwordEncoder;

    public ProfileView(UserService userService, User loggedUser, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.loggedUser = loggedUser;
        this.passwordEncoder = passwordEncoder;
        getProfileView();
    }

    private void getProfileView() {
        setSpacing(true);
        setSizeFull();

        FormLayout settingFormLayout = new FormLayout();
        settingFormLayout.setMargin(new MarginInfo(false, true));

        Label titleSetting = new Label("Change user data");
        TextField nameTextField = new TextField("Name", loggedUser.getName());
        TextField lastNameTextField = new TextField("Last Name", loggedUser.getLastName());
        TextField emailTextField = new TextField("Email", loggedUser.getEmail());
        TextField companyNameTextField = new TextField("Company name", loggedUser.getCompanyName());
        TextField companyAddressTextField = new TextField("Company address", loggedUser.getCompanyAddress());
        TextField companyNipTextField = new TextField("Company NIP", loggedUser.getCompanyNip());

        Button saveSettingsBtn = new Button("Save");
        saveSettingsBtn.addClickListener(clickEvent -> {
            loggedUser.setName(nameTextField.getValue());
            loggedUser.setLastName(lastNameTextField.getValue());
            loggedUser.setEmail(emailTextField.getValue());
            loggedUser.setCompanyName(companyNameTextField.getValue());
            loggedUser.setCompanyAddress(companyAddressTextField.getValue());
            loggedUser.setCompanyNip(companyNipTextField.getValue());

            loggedUser = userService.updateUser(loggedUser);
            Notification.show("All changes has  been saved.", Notification.Type.HUMANIZED_MESSAGE);
        });

        settingFormLayout.addComponents(titleSetting, nameTextField, lastNameTextField, emailTextField, companyNameTextField, companyAddressTextField, companyNipTextField, saveSettingsBtn);
        settingFormLayout.setComponentAlignment(titleSetting, Alignment.TOP_CENTER);

        FormLayout changePasswordLayout = new FormLayout();
        changePasswordLayout.setMargin(new MarginInfo(false, true));

        Label titleChangePassword = new Label("Change password");
        PasswordField currentPasswordField = new PasswordField("Current password");
        PasswordField newPasswordField = new PasswordField("New password");
        PasswordField newPasswordField2 = new PasswordField("Repeat new password");

        Button savePasswordBtn = new Button("Save");
        savePasswordBtn.addClickListener(clickEvent -> {
            if (passwordEncoder.matches(currentPasswordField.getValue(), loggedUser.getPassword())) {
                if (newPasswordField.getValue().equals(newPasswordField2.getValue())) {
                    ResponseEntity<User> responseEntity = userService.changePassword(loggedUser.getUid(), newPasswordField.getValue());
                    if (responseEntity.getStatusCode().is2xxSuccessful()) {
                        loggedUser = responseEntity.getBody();
                        currentPasswordField.clear();
                        newPasswordField.clear();
                        newPasswordField2.clear();
                        Notification.show("Password has changed", Notification.Type.HUMANIZED_MESSAGE);
                    } else
                        Notification.show(responseEntity.getStatusCode().toString(), Notification.Type.ERROR_MESSAGE);
                } else
                    Notification.show("New passwords aren't identical", Notification.Type.ERROR_MESSAGE);
            } else
                Notification.show("Current password is invalid", Notification.Type.ERROR_MESSAGE);
        });

        changePasswordLayout.addComponents(titleChangePassword, currentPasswordField, newPasswordField, newPasswordField2, savePasswordBtn);
        addComponents(settingFormLayout, changePasswordLayout);
    }
}
