package com.example.PSO.gui;

import com.example.PSO.models.AutoCapacity;
import com.example.PSO.models.Delegation;
import com.example.PSO.models.TransportType;
import com.example.PSO.models.User;
import com.example.PSO.service.DelegationService;
import com.example.PSO.service.UserService;
import com.vaadin.data.Binder;
import com.vaadin.data.Binder.Binding;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.NumberRenderer;
import org.w3c.dom.Text;

import java.time.LocalDate;
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

        AtomicReference<List<Delegation>> delegationListToCB = new AtomicReference<>(delegationService.getAllDelByUserOrderByDateStartDesc(loggedUser.getUid()));

        //Converters
        StringToIntegerConverter stringToIntConverter = new StringToIntegerConverter("Enter a number!");
        StringToDoubleConverter stringToDoubleConverter = new StringToDoubleConverter("Enter a double number!");

        //FIELDS IN GRID
        TextField travelDietAmountTextField = new TextField("travelDietAmount");
        TextField breakfastNumberTextField = new TextField("breakfastNumber");
        TextField dinnerNumberTextField = new TextField("dinnerNumber");
        TextField supperNumberTextField = new TextField("supperNumber");
        ComboBox transportTypeEnum = new ComboBox("transportType");
        transportTypeEnum.setItems(TransportType.values());
        //transportTypeEnum.setValue(TransportType.valueOf(chosenDelegation.get().getTransportType().toString()));
        TextField ticketPriceTextField = new TextField("ticketPrice");
        ComboBox autoCapacityEnum = new ComboBox("autoCapacity");
        autoCapacityEnum.setItems(AutoCapacity.values());
       // autoCapacityEnum.setValue(chosenDelegation.get().getAutoCapacity());
        TextField kmTextField = new TextField("km");
        TextField accommodationPriceTextField = new TextField("accommodationPrice");
        TextField otherTicketsPriceTextField = new TextField("otherTicketsPrice");
        TextField otherOutlayDescTextField = new TextField("otherOutlayDesc");
        TextField otherOutlayPriceTextField = new TextField("otherOutlayPrice");

        //GRID
        Grid<Delegation> delegationsGrid = new Grid<>();
        delegationsGrid.getEditor().setEnabled(true);
        delegationsGrid.setItems(delegationListToCB.get());

        //BINDERS
        Binding bindTravelDietAmount = delegationsGrid.getEditor().getBinder().forField(travelDietAmountTextField).withConverter(stringToIntConverter).bind(Delegation::getTravelDietAmount,Delegation::setTravelDietAmount);
        Binding bindBreakFastNumber = delegationsGrid.getEditor().getBinder().forField(breakfastNumberTextField).withConverter(stringToIntConverter).bind(Delegation::getBreakfastNumber,Delegation::setBreakfastNumber);
        Binding bindDinnerNumber = delegationsGrid.getEditor().getBinder().forField(dinnerNumberTextField).withConverter(stringToIntConverter).bind(Delegation::getDinnerNumber,Delegation::setDinnerNumber);
        Binding bindSupperNumber = delegationsGrid.getEditor().getBinder().forField(supperNumberTextField).withConverter(stringToIntConverter).bind(Delegation::getSupperNumber,Delegation::setSupperNumber);
        Binding bindTicketPrice = delegationsGrid.getEditor().getBinder().forField(ticketPriceTextField).withConverter(stringToDoubleConverter).bind(Delegation::getTicketPrice,Delegation::setTicketPrice);
        Binding bindKM = delegationsGrid.getEditor().getBinder().forField(kmTextField).withConverter(stringToIntConverter).bind(Delegation::getKm,Delegation::setKm);
        Binding bindAccommodationPrice = delegationsGrid.getEditor().getBinder().forField(accommodationPriceTextField).withConverter(stringToDoubleConverter).bind(Delegation::getAccommodationPrice,Delegation::setAccommodationPrice);
        Binding bindOtherTicketsPrice = delegationsGrid.getEditor().getBinder().forField(otherTicketsPriceTextField).withConverter(stringToDoubleConverter).bind(Delegation::getOtherTicketsPrice,Delegation::setOtherTicketsPrice);
        Binding bindOtherOutlayPrice = delegationsGrid.getEditor().getBinder().forField(otherOutlayPriceTextField).withConverter(stringToDoubleConverter).bind(Delegation::getOtherOutlayPrice,Delegation::setOtherOutlayPrice);

        //ADDING COLUMN
        delegationsGrid.addColumn(Delegation::getDescription).setCaption("Description").setEditorComponent(new TextField(),Delegation::setDescription);
        delegationsGrid.addColumn(Delegation::getDateTimeStart).setCaption("DateTimeStart").setEditorComponent(new DateField(),Delegation::setDateTimeStart);
        delegationsGrid.addColumn(Delegation::getDateTimeStop).setCaption("DateTimeStop").setEditorComponent(new DateField(),Delegation::setDateTimeStop);
        delegationsGrid.addColumn(Delegation::getTravelDietAmount).setCaption("TravelDietAmount").setEditorBinding(bindTravelDietAmount);
        delegationsGrid.addColumn(Delegation::getBreakfastNumber).setCaption("BreakfastNumber").setEditorBinding(bindBreakFastNumber);
        delegationsGrid.addColumn(Delegation::getDinnerNumber).setCaption("DinnerNumber").setEditorBinding(bindDinnerNumber);
        delegationsGrid.addColumn(Delegation::getSupperNumber).setCaption("SupperNumber").setEditorBinding(bindSupperNumber);
        delegationsGrid.addColumn(Delegation::getTransportType).setCaption("TransportType");
        delegationsGrid.addColumn(Delegation::getTicketPrice).setCaption("TicketPrice").setEditorBinding(bindTicketPrice);
        delegationsGrid.addColumn(Delegation::getAutoCapacity).setCaption("AutoCapacity");
        delegationsGrid.addColumn(Delegation::getKm).setCaption("KM").setEditorBinding(bindKM);
        delegationsGrid.addColumn(Delegation::getAccommodationPrice).setCaption("AccommodationPrice").setEditorBinding(bindAccommodationPrice);
        delegationsGrid.addColumn(Delegation::getOtherTicketsPrice).setCaption("OtherTicketsPrice").setEditorBinding(bindOtherTicketsPrice);
        delegationsGrid.addColumn(Delegation::getOtherOutlayDesc).setCaption("OtherOutlayDesc").setEditorComponent(new TextField(),Delegation::setOtherOutlayDesc);
        delegationsGrid.addColumn(Delegation::getOtherOutlayPrice).setCaption("OtherOutlayPrice").setEditorBinding(bindOtherOutlayPrice);
        delegationsGrid.setWidth("100%");
        delegationsGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        //SAVE LISTENER
        delegationsGrid.getEditor().addSaveListener(editorSaveEvent -> {

            delegationService.changeDelegation(editorSaveEvent.getBean().getId(), editorSaveEvent.getBean());
            delegationsGrid.setItems(delegationService.getAllDelByUserOrderByDateStartDesc(loggedUser.getUid()));
            delegationsGrid.getDataProvider().refreshAll();
            Notification.show("Delegation has been saved", Notification.Type.HUMANIZED_MESSAGE);
        });



        //DELETE BUTTON
        Button deleteDelegation = new Button("Delete");

            deleteDelegation.addClickListener(clickEvent ->
            {
              //TODO validation
                delegationsGrid.getSelectedItems().stream().forEach(d->{
                    delegationService.removeDelegation(loggedUser.getUid(),d.getId());
                    delegationListToCB.set(delegationService.getAllDelByUserOrderByDateStartDesc(loggedUser.getUid()));
                    delegationsGrid.setItems(delegationListToCB.get());
                    delegationsGrid.getDataProvider().refreshAll();
                });
                Notification.show("Delegation has been deleted", Notification.Type.HUMANIZED_MESSAGE);
            });
            //ADD BUTTON
        Button addDelegation = new Button("Add New");
        addDelegation.addClickListener(clickEvent ->
        {
            Delegation newDelegation = new Delegation(LocalDate.now(),LocalDate.now().plusDays(1),loggedUser);
            newDelegation.setDescription("New Delegation - EDIT IT");
            delegationService.addDelegation(loggedUser.getUid(),newDelegation);
            delegationsGrid.setItems(delegationService.getAllDelByUserOrderByDateStartDesc(loggedUser.getUid()));
            delegationsGrid.getDataProvider().refreshAll();
            Notification.show("Delegation has been added - EDIT IT!", Notification.Type.HUMANIZED_MESSAGE);
        });
        horizontaLayout.addComponents(addDelegation,deleteDelegation);
        settingFormLayout.addComponents(delegationsGrid,horizontaLayout);
        addComponent(settingFormLayout);
    }
}
