package com.example.PSO.gui;

import com.example.PSO.models.AutoCapacity;
import com.example.PSO.models.Delegation;
import com.example.PSO.models.TransportType;
import com.example.PSO.models.User;
import com.example.PSO.service.DelegationService;
import com.example.PSO.service.UserService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DelegationView extends HorizontalLayout {
    private User loggedUser;
    private UserService userService;
    private DelegationService delegationService;

    public DelegationView(User loggedUser, UserService userService, DelegationService delegationService) {
        this.loggedUser = loggedUser;
        this.userService = userService;
        this.delegationService = delegationService;
        getDelegationView();
    }

    public void getDelegationView(){
        setSpacing(true);
        setSizeFull();
        FormLayout settingFormLayout = new FormLayout();
        HorizontalLayout horizontaLayout = new HorizontalLayout();
        settingFormLayout.setMargin(new MarginInfo(false, true));

        ComboBox<Delegation> delegationsList = new ComboBox<>();
        delegationsList.setTextInputAllowed(false);
        delegationsList.setEmptySelectionAllowed(false);
        AtomicReference<List<Delegation>> delegationListToCB = new AtomicReference<>(delegationService.getAllDelByUserOrderByDateStartDesc(loggedUser.getUid()));
        delegationsList.setItems(delegationListToCB.get());
        delegationsList.setValue(delegationListToCB.get().get(0));
        AtomicReference<Delegation> chosenDelegation = new AtomicReference<>(delegationsList.getValue());

        TextField descriptionTextField = new TextField("Description", chosenDelegation.get().getDescription()+"");
        DateField dateTimeStartDateField = new DateField("dateTimeStart", chosenDelegation.get().getDateTimeStart());
        DateField dateTimeStopDateField = new DateField("dateTimeStop", chosenDelegation.get().getDateTimeStop());
        TextField travelDietAmountTextField = new TextField("travelDietAmount", chosenDelegation.get().getTravelDietAmount().toString());
        TextField breakfastNumberTextField = new TextField("breakfastNumber", chosenDelegation.get().getBreakfastNumber().toString());
        TextField dinnerNumberTextField = new TextField("dinnerNumber", chosenDelegation.get().getDinnerNumber().toString());
        TextField supperNumberTextField = new TextField("supperNumber", chosenDelegation.get().getSupperNumber().toString());
        ComboBox transportTypeEnum = new ComboBox("transportType");

        transportTypeEnum.setItems(TransportType.values());
        transportTypeEnum.setValue(TransportType.valueOf(chosenDelegation.get().getTransportType().toString()));

        TextField ticketPriceTextField = new TextField("ticketPrice", chosenDelegation.get().getTicketPrice().toString());

        ComboBox autoCapacityEnum = new ComboBox("autoCapacity");
        autoCapacityEnum.setItems(AutoCapacity.values());
        autoCapacityEnum.setValue(chosenDelegation.get().getAutoCapacity());
        TextField kmTextField = new TextField("km", chosenDelegation.get().getKm().toString());
        TextField accommodationPriceTextField = new TextField("accommodationPrice", chosenDelegation.get().getAccommodationPrice().toString());
        TextField otherTicketsPriceTextField = new TextField("otherTicketsPrice", chosenDelegation.get().getOtherTicketsPrice().toString());
        TextField otherOutlayDescTextField = new TextField("otherOutlayDesc", chosenDelegation.get().getOtherOutlayDesc()+"");
        TextField otherOutlayPriceTextField = new TextField("otherOutlayPrice", chosenDelegation.get().getOtherTicketsPrice().toString());

        Button saveDelegation = new Button("Save");
        Button deleteDelegation = new Button("Delete");
        saveDelegation.addClickListener(clickEvent ->
        {
            chosenDelegation.set(delegationsList.getValue());

            chosenDelegation.get().setId(chosenDelegation.get().getId());
            chosenDelegation.get().setDescription(descriptionTextField.getValue());
            chosenDelegation.get().setDateTimeStart(dateTimeStartDateField.getValue());
            chosenDelegation.get().setDateTimeStop(dateTimeStopDateField.getValue());
            chosenDelegation.get().setTravelDietAmount(Integer.parseInt(travelDietAmountTextField.getValue()));
            chosenDelegation.get().setBreakfastNumber(Integer.parseInt(breakfastNumberTextField.getValue()));
            chosenDelegation.get().setDinnerNumber(Integer.parseInt(dinnerNumberTextField.getValue()));
            chosenDelegation.get().setSupperNumber(Integer.parseInt(supperNumberTextField.getValue()));
            chosenDelegation.get().setTransportType(TransportType.valueOf(transportTypeEnum.getValue().toString()));
            chosenDelegation.get().setTicketPrice(Double.parseDouble(ticketPriceTextField.getValue()));
            chosenDelegation.get().setAutoCapacity(AutoCapacity.valueOf(autoCapacityEnum.getValue().toString()));
            chosenDelegation.get().setKm(Integer.parseInt(kmTextField.getValue()));
            chosenDelegation.get().setAccommodationPrice(Double.parseDouble(accommodationPriceTextField.getValue()));
            chosenDelegation.get().setOtherTicketsPrice(Double.parseDouble(otherTicketsPriceTextField.getValue()));
            chosenDelegation.get().setOtherOutlayDesc(otherOutlayDescTextField.getValue());
            chosenDelegation.get().setOtherOutlayPrice(Double.parseDouble(otherOutlayPriceTextField.getValue()));


            delegationService.changeDelegation(chosenDelegation.get().getId(), chosenDelegation.get());
            delegationsList.getDataProvider().refreshAll();
            Notification.show("All changes has  been saved.", Notification.Type.HUMANIZED_MESSAGE);
        });
        delegationsList.addValueChangeListener(delegationValueChangeEvent ->
        {
            System.out.println("IMHERE");
            chosenDelegation.set(delegationValueChangeEvent.getValue());

            descriptionTextField.setValue(chosenDelegation.get().getDescription()+"");
            dateTimeStartDateField.setValue(chosenDelegation.get().getDateTimeStart());
            dateTimeStopDateField.setValue(chosenDelegation.get().getDateTimeStop());
            travelDietAmountTextField.setValue(chosenDelegation.get().getTravelDietAmount().toString());
            breakfastNumberTextField.setValue(chosenDelegation.get().getBreakfastNumber().toString());
            dinnerNumberTextField.setValue(chosenDelegation.get().getDinnerNumber().toString());
            supperNumberTextField.setValue(chosenDelegation.get().getSupperNumber().toString());
            transportTypeEnum.setValue(chosenDelegation.get().getTransportType().toString());
            ticketPriceTextField.setValue(chosenDelegation.get().getTicketPrice().toString());
            autoCapacityEnum.setValue(chosenDelegation.get().getAutoCapacity().toString());
            kmTextField.setValue(chosenDelegation.get().getKm().toString());
            accommodationPriceTextField.setValue(chosenDelegation.get().getAccommodationPrice().toString());
            otherTicketsPriceTextField.setValue(chosenDelegation.get().getOtherTicketsPrice().toString());
            otherOutlayDescTextField.setValue(chosenDelegation.get().getOtherOutlayDesc()+"");
            otherOutlayPriceTextField.setValue(chosenDelegation.get().getOtherOutlayPrice().toString());
        });

            deleteDelegation.addClickListener(clickEvent ->
            {
               delegationService.removeDelegation(loggedUser.getUid(),chosenDelegation.get().getId());
               delegationListToCB.get().remove(chosenDelegation);
                delegationsList.setItems(delegationListToCB.get());
                delegationsList.getDataProvider().refreshAll();
                Notification.show("Delegetion has been deleted", Notification.Type.HUMANIZED_MESSAGE);
            });
        horizontaLayout.addComponents(delegationsList,deleteDelegation);
        settingFormLayout.addComponents(horizontaLayout,descriptionTextField,dateTimeStartDateField,dateTimeStopDateField,
                travelDietAmountTextField,breakfastNumberTextField,dinnerNumberTextField,supperNumberTextField,transportTypeEnum,autoCapacityEnum,kmTextField,accommodationPriceTextField,otherTicketsPriceTextField,
                otherOutlayDescTextField,otherOutlayPriceTextField,saveDelegation);
        addComponent(settingFormLayout);
    }
}
