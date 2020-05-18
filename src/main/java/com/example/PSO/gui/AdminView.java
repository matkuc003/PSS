package com.example.PSO.gui;

import com.example.PSO.models.*;
import com.example.PSO.service.DelegationService;
import com.example.PSO.service.RoleService;
import com.example.PSO.service.UserService;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.shared.ui.AlignmentInfo;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AdminView extends HorizontalLayout {

    private UserService userService;
    private DelegationService delegationService;
    private RoleService roleService;
    private User loggedUser;
    private Grid<Delegation> delegationsGrid = new Grid<>();
    private Grid<User> usersGrid = new Grid<>();
    public AdminView(UserService userService, DelegationService delegationService, RoleService roleService,User loggedUser) {
        this.userService = userService;
        this.delegationService=delegationService;
        this.roleService=roleService;
        this.loggedUser = loggedUser;
        getAdminView();
    }

    private void getAdminView() {
        AtomicReference<List<Delegation>> allDelegationList= new AtomicReference<>(delegationService.getAllDelegation());

        AtomicReference<List<User>> allUsersList= new AtomicReference<>(userService.getAllUser());
        setSpacing(true);
        setSizeFull();
     //   FormLayout gridLayout = new FormLayout();
      //  gridLayout.setMargin(new MarginInfo(false, true));

        GridLayout mainFormLayout = new GridLayout(3,3);
        mainFormLayout.setMargin(new MarginInfo(false, true));
        mainFormLayout.setWidth("100%");

        ComboBox chooseAction = new ComboBox();
        chooseAction.setItems("Users","Delegations");

        delegationsGrid.getEditor().setEnabled(true);
        mainFormLayout.addComponent(chooseAction,1,0);
        mainFormLayout.setComponentAlignment(chooseAction, Alignment.TOP_CENTER);
        chooseAction.setSelectedItem("Users");
        chooseAction.addValueChangeListener(valueChangeEvent -> {


        if(chooseAction.getValue().equals("Delegations")){
            //Converters
            StringToIntegerConverter stringToIntConverter = new StringToIntegerConverter("Enter a number!");
            StringToDoubleConverter stringToDoubleConverter = new StringToDoubleConverter("Enter a double number!");
            StringToLongConverter stringToLongConverter = new StringToLongConverter("Enter a long number");

            //FIELDS IN GRID
            TextField travelDietAmountTextField = new TextField("travelDietAmount");
            TextField breakfastNumberTextField = new TextField("breakfastNumber");
            TextField dinnerNumberTextField = new TextField("dinnerNumber");
            TextField supperNumberTextField = new TextField("supperNumber");

            ComboBox transportTypeEnum = new ComboBox("transportType");
            transportTypeEnum.setItems(TransportType.values());

            TextField ticketPriceTextField = new TextField("ticketPrice");

            ComboBox autoCapacityEnum = new ComboBox("autoCapacity");
            autoCapacityEnum.setItems(AutoCapacity.values());

            TextField kmTextField = new TextField("km");
            TextField accommodationPriceTextField = new TextField("accommodationPrice");
            TextField otherTicketsPriceTextField = new TextField("otherTicketsPrice");
            TextField otherOutlayDescTextField = new TextField("otherOutlayDesc");
            TextField otherOutlayPriceTextField = new TextField("otherOutlayPrice");
            TextField userIDTextField = new TextField("USER_ID");
            DateField startDateField = new DateField("Start Date");
            DateField stopDateField = new DateField("Stop Date");
            //BINDERS
            Binder.Binding bindStartDateField = delegationsGrid.getEditor().getBinder().forField(startDateField).bind(Delegation::getDateTimeStart, Delegation::setDateTimeStart);
            Binder.Binding bindStopDateField = delegationsGrid.getEditor().getBinder().forField(stopDateField).bind(Delegation::getDateTimeStop, Delegation::setDateTimeStop);
            Binder.Binding bindTravelDietAmount = delegationsGrid.getEditor().getBinder().forField(travelDietAmountTextField).withConverter(stringToIntConverter).bind(Delegation::getTravelDietAmount, Delegation::setTravelDietAmount);
            Binder.Binding bindBreakFastNumber = delegationsGrid.getEditor().getBinder().forField(breakfastNumberTextField).withConverter(stringToIntConverter).bind(Delegation::getBreakfastNumber, Delegation::setBreakfastNumber);
            Binder.Binding bindDinnerNumber = delegationsGrid.getEditor().getBinder().forField(dinnerNumberTextField).withConverter(stringToIntConverter).bind(Delegation::getDinnerNumber, Delegation::setDinnerNumber);
            Binder.Binding bindSupperNumber = delegationsGrid.getEditor().getBinder().forField(supperNumberTextField).withConverter(stringToIntConverter).bind(Delegation::getSupperNumber, Delegation::setSupperNumber);
            Binder.Binding bindTicketPrice = delegationsGrid.getEditor().getBinder().forField(ticketPriceTextField).withConverter(stringToDoubleConverter).bind(Delegation::getTicketPrice, Delegation::setTicketPrice);
            Binder.Binding bindKM = delegationsGrid.getEditor().getBinder().forField(kmTextField).withConverter(stringToIntConverter).bind(Delegation::getKm, Delegation::setKm);
            Binder.Binding bindAccommodationPrice = delegationsGrid.getEditor().getBinder().forField(accommodationPriceTextField).withConverter(stringToDoubleConverter).bind(Delegation::getAccommodationPrice, Delegation::setAccommodationPrice);
            Binder.Binding bindOtherTicketsPrice = delegationsGrid.getEditor().getBinder().forField(otherTicketsPriceTextField).withConverter(stringToDoubleConverter).bind(Delegation::getOtherTicketsPrice, Delegation::setOtherTicketsPrice);
            Binder.Binding bindOtherOutlayPrice = delegationsGrid.getEditor().getBinder().forField(otherOutlayPriceTextField).withConverter(stringToDoubleConverter).bind(Delegation::getOtherOutlayPrice, Delegation::setOtherOutlayPrice);
            Binder.Binding bindUSERID = delegationsGrid.getEditor().getBinder().forField(userIDTextField).withConverter(stringToLongConverter).bind(Delegation->Delegation.getUser().getUid(),(Delegation, aLong) -> delegationService.addDelegation(aLong,Delegation));

            //ADDING COLUMN
            delegationsGrid.addColumn(Delegation::getDescription).setCaption("Description").setEditorComponent(new TextField(), Delegation::setDescription);
            delegationsGrid.addColumn(Delegation::getDateTimeStart).setCaption("DateTimeStart").setEditorBinding(bindStartDateField);
            delegationsGrid.addColumn(Delegation::getDateTimeStop).setCaption("DateTimeStop").setEditorBinding(bindStopDateField);
            delegationsGrid.addColumn(Delegation::getTravelDietAmount).setCaption("TravelDietAmount").setEditorBinding(bindTravelDietAmount);
            delegationsGrid.addColumn(Delegation::getBreakfastNumber).setCaption("BreakfastNumber").setEditorBinding(bindBreakFastNumber);
            delegationsGrid.addColumn(Delegation::getDinnerNumber).setCaption("DinnerNumber").setEditorBinding(bindDinnerNumber);
            delegationsGrid.addColumn(Delegation::getSupperNumber).setCaption("SupperNumber").setEditorBinding(bindSupperNumber);
            delegationsGrid.addColumn(Delegation::getTransportType).setCaption("TransportType").setEditorComponent(transportTypeEnum, Delegation::setTransportType);
            delegationsGrid.addColumn(Delegation::getTicketPrice).setCaption("TicketPrice").setEditorBinding(bindTicketPrice);
            delegationsGrid.addColumn(Delegation::getAutoCapacity).setCaption("AutoCapacity").setEditorComponent(autoCapacityEnum, Delegation::setAutoCapacity);
            delegationsGrid.addColumn(Delegation::getKm).setCaption("KM").setEditorBinding(bindKM);
            delegationsGrid.addColumn(Delegation::getAccommodationPrice).setCaption("AccommodationPrice").setEditorBinding(bindAccommodationPrice);
            delegationsGrid.addColumn(Delegation::getOtherTicketsPrice).setCaption("OtherTicketsPrice").setEditorBinding(bindOtherTicketsPrice);
            delegationsGrid.addColumn(Delegation::getOtherOutlayDesc).setCaption("OtherOutlayDesc").setEditorComponent(otherOutlayDescTextField, Delegation::setOtherOutlayDesc);
            delegationsGrid.addColumn(Delegation::getOtherOutlayPrice).setCaption("OtherOutlayPrice").setEditorBinding(bindOtherOutlayPrice);
            delegationsGrid.addColumn(Delegation->Delegation.getUser().getUid()).setCaption("USER_ID").setEditorBinding(bindUSERID);
            delegationsGrid.addColumn(Delegation::getConfirmation).setCaption("Confirmation");
            delegationsGrid.addComponentColumn(this::buildConfirmButton);
             delegationsGrid.addComponentColumn(this::buildDeniedButton);
            delegationsGrid.setWidth("250%");
            delegationsGrid.setSelectionMode(Grid.SelectionMode.MULTI);

            //SAVE LISTENER
            delegationsGrid.getEditor().addSaveListener(editorSaveEvent -> {
                if (editorSaveEvent.getBean().getDateTimeStop().isBefore(editorSaveEvent.getBean().getDateTimeStart())) {
                    Notification.show("STOP DATE cannot be before START DATE", Notification.Type.ERROR_MESSAGE);
                } else {
                    delegationService.changeDelegation(editorSaveEvent.getBean().getId(), editorSaveEvent.getBean());
                    delegationsGrid.setItems(delegationService.getAllDelegation());
                    delegationsGrid.getDataProvider().refreshAll();
                    Notification.show("Delegation has been saved", Notification.Type.HUMANIZED_MESSAGE);
                }

            });
            //SettingItems
            delegationsGrid.setItems(allDelegationList.get());
            mainFormLayout.removeComponent(usersGrid);
            mainFormLayout.addComponent(delegationsGrid,1,1);
            mainFormLayout.setComponentAlignment(delegationsGrid,  new Alignment(AlignmentInfo.Bits.ALIGNMENT_VERTICAL_CENTER |
                    AlignmentInfo.Bits.ALIGNMENT_HORIZONTAL_CENTER));
        }
        else{
            //Adding Columns
            usersGrid.addColumn(User::getUid).setCaption("ID");
            usersGrid.addColumn(User::getCompanyName).setCaption("Company Name");
            usersGrid.addColumn(User::getCompanyAddress).setCaption("Company Address");
            usersGrid.addColumn(User::getCompanyNip).setCaption("Company NIP");
            usersGrid.addColumn(User::getName).setCaption("Name");
            usersGrid.addColumn(User::getLastName).setCaption("Last Name");
            usersGrid.addColumn(User::getEmail).setCaption("Email");
            usersGrid.addColumn(User::getPassword).setCaption("Password");
            usersGrid.addColumn(User::getRegistrationDate).setCaption("Registration Date");
            usersGrid.addColumn(User::getRoles).setCaption("ROLES");
            usersGrid.addComponentColumn(this::buildCheckBox).setCaption("Admin");

            //SettingItems
            usersGrid.setWidth("250%");
            usersGrid.setSelectionMode(Grid.SelectionMode.MULTI);
            usersGrid.setItems(allUsersList.get());

            mainFormLayout.removeComponent(delegationsGrid);
            mainFormLayout.addComponent(usersGrid,1,1);
            mainFormLayout.setComponentAlignment(usersGrid,  new Alignment(AlignmentInfo.Bits.ALIGNMENT_VERTICAL_CENTER |
                    AlignmentInfo.Bits.ALIGNMENT_HORIZONTAL_CENTER));
        }
        });

        Button deleteUserButton = new Button("Delete");
        deleteUserButton.addClickListener(clickEvent ->
        {
            if(chooseAction.getValue().equals("Users")) {
                usersGrid.getSelectedItems().forEach(SI ->
                {
                SI.getDelegations().clear();
                    userService.updateUser(SI);
                    userService.deleteUserById(SI.getUid());
                });

                allUsersList.set(userService.getAllUser());
                usersGrid.setItems(allUsersList.get());
                usersGrid.getDataProvider().refreshAll();
                allDelegationList.set(delegationService.getAllDelegation());
                delegationsGrid.setItems(allDelegationList.get());
                delegationsGrid.getDataProvider().refreshAll();
                Notification.show("Users has been deleted", Notification.Type.HUMANIZED_MESSAGE);
            }
            else if(chooseAction.getValue().equals("Delegations")){
                delegationsGrid.getSelectedItems().forEach(SI->delegationService.removeDelegation(SI.getUser().getUid(),SI.getId()));
                allDelegationList.set(delegationService.getAllDelegation());
                delegationsGrid.setItems(allDelegationList.get());
                delegationsGrid.getDataProvider().refreshAll();
                Notification.show("Delegations has been deleted", Notification.Type.HUMANIZED_MESSAGE);
            }
        });

        mainFormLayout.addComponent(deleteUserButton,1,2);


        addComponents(mainFormLayout);
    }
    public CheckBox buildCheckBox(User u){
        CheckBox checkBox = new CheckBox();
        User user = u;
        if(u.getRoles().stream().anyMatch(r->r.getRoleName().equals("ROLE_ADMIN"))) {
            checkBox.setValue(true);
        }
        else {
            checkBox.setValue(false);
        }
        checkBox.addValueChangeListener(valueChangeEvent ->
        {
            Boolean valueCB=valueChangeEvent.getValue();
            Role role = roleService.getRoleByName("ROLE_ADMIN");
            if(valueCB==false) {
                System.out.println("uncheck");
                user.removeRole(role);
                roleService.updateRole(role);
                userService.updateUser(user);
                usersGrid.setItems(userService.getAllUser());
                usersGrid.getDataProvider().refreshAll();
            }
            else if(valueCB==true) {
                user.addRole(role);
                userService.updateUser(user);
                usersGrid.setItems(userService.getAllUser());
                usersGrid.getDataProvider().refreshAll();
            }

        });
        return checkBox;
    }
    private AbstractComponent buildConfirmButton(Delegation d) {
        if(d.getConfirmation().equals(Confirmation.NONE) ) {
            return new Label();
        } else {
            Button button = new Button();
             if (d.getConfirmation().equals(Confirmation.CONFIRM)) {
                button.setCaption("Revoke Confirm");
            }
             else{
                 button.setCaption( "Confirm");
             };
            button.addClickListener(e ->  {
                if (d.getConfirmation().equals(Confirmation.WAITING_FOR_CONFIRM))
                    d.setConfirmation(Confirmation.CONFIRM);
                else if(d.getConfirmation().equals(Confirmation.CONFIRM))
                    d.setConfirmation(Confirmation. WAITING_FOR_CONFIRM);
                else if(d.getConfirmation().equals(Confirmation.NOT_CONFIRM))
                    d.setConfirmation(Confirmation. CONFIRM);
                delegationService.changeDelegation(d.getId(), d);
                delegationsGrid.setItems(delegationService.getAllDelegation());
                delegationsGrid.getDataProvider().refreshAll();
            });
            return button;
        }
    }
    private AbstractComponent buildDeniedButton(Delegation d) {
        if(d.getConfirmation().equals(Confirmation.NONE) ) {
            return new Label();
        } else {
            Button button = new Button();
            if (d.getConfirmation().equals(Confirmation.NOT_CONFIRM)) {
                button.setCaption("Revoke deny");
            }
            else{
                button.setCaption( "Deny");
            };
            button.addClickListener(e ->  {
                if (d.getConfirmation().equals(Confirmation.WAITING_FOR_CONFIRM))
                    d.setConfirmation(Confirmation.NOT_CONFIRM);
                else if(d.getConfirmation().equals(Confirmation.NOT_CONFIRM))
                    d.setConfirmation(Confirmation. WAITING_FOR_CONFIRM);
                else if(d.getConfirmation().equals(Confirmation.CONFIRM))
                    d.setConfirmation(Confirmation. NOT_CONFIRM);
                delegationService.changeDelegation(d.getId(), d);
                delegationsGrid.setItems(delegationService.getAllDelegation());
                delegationsGrid.getDataProvider().refreshAll();
            });
            return button;
        }
    }

}
