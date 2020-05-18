package com.example.PSO.gui;

import com.example.PSO.models.*;
import com.example.PSO.service.DelegationService;
import com.example.PSO.service.PdfService;
import com.vaadin.data.Binder.Binding;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DelegationView extends HorizontalLayout {
    private User loggedUser;
    private DelegationService delegationService;
    private PdfService pdfService;

    private Grid<Delegation> delegationsGrid;

    public DelegationView(User loggedUser,DelegationService delegationService, PdfService pdfService) {
        this.loggedUser = loggedUser;
        this.delegationService = delegationService;
        this.pdfService = pdfService;
        getDelegationView();
    }

    public void getDelegationView() {
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

        TextField ticketPriceTextField = new TextField("ticketPrice");

        ComboBox autoCapacityEnum = new ComboBox("autoCapacity");
        autoCapacityEnum.setItems(AutoCapacity.values());

        TextField kmTextField = new TextField("km");
        TextField accommodationPriceTextField = new TextField("accommodationPrice");
        TextField otherTicketsPriceTextField = new TextField("otherTicketsPrice");
        TextField otherOutlayDescTextField = new TextField("otherOutlayDesc");
        TextField otherOutlayPriceTextField = new TextField("otherOutlayPrice");
        DateField startDateField = new DateField("Start Date");
        DateField stopDateField = new DateField("Stop Date");

        //GRID
        delegationsGrid = new Grid<>();
        delegationsGrid.getEditor().setEnabled(true);
        delegationsGrid.setItems(delegationListToCB.get());

        //BINDERS
        Binding bindStartDateField = delegationsGrid.getEditor().getBinder().forField(startDateField).bind(Delegation::getDateTimeStart, Delegation::setDateTimeStart);
        Binding bindStopDateField = delegationsGrid.getEditor().getBinder().forField(stopDateField).bind(Delegation::getDateTimeStop, Delegation::setDateTimeStop);
        Binding bindTravelDietAmount = delegationsGrid.getEditor().getBinder().forField(travelDietAmountTextField).withConverter(stringToIntConverter).bind(Delegation::getTravelDietAmount, Delegation::setTravelDietAmount);
        Binding bindBreakFastNumber = delegationsGrid.getEditor().getBinder().forField(breakfastNumberTextField).withConverter(stringToIntConverter).bind(Delegation::getBreakfastNumber, Delegation::setBreakfastNumber);
        Binding bindDinnerNumber = delegationsGrid.getEditor().getBinder().forField(dinnerNumberTextField).withConverter(stringToIntConverter).bind(Delegation::getDinnerNumber, Delegation::setDinnerNumber);
        Binding bindSupperNumber = delegationsGrid.getEditor().getBinder().forField(supperNumberTextField).withConverter(stringToIntConverter).bind(Delegation::getSupperNumber, Delegation::setSupperNumber);
        Binding bindTicketPrice = delegationsGrid.getEditor().getBinder().forField(ticketPriceTextField).withConverter(stringToDoubleConverter).bind(Delegation::getTicketPrice, Delegation::setTicketPrice);
        Binding bindKM = delegationsGrid.getEditor().getBinder().forField(kmTextField).withConverter(stringToIntConverter).bind(Delegation::getKm, Delegation::setKm);
        Binding bindAccommodationPrice = delegationsGrid.getEditor().getBinder().forField(accommodationPriceTextField).withConverter(stringToDoubleConverter).bind(Delegation::getAccommodationPrice, Delegation::setAccommodationPrice);
        Binding bindOtherTicketsPrice = delegationsGrid.getEditor().getBinder().forField(otherTicketsPriceTextField).withConverter(stringToDoubleConverter).bind(Delegation::getOtherTicketsPrice, Delegation::setOtherTicketsPrice);
        Binding bindOtherOutlayPrice = delegationsGrid.getEditor().getBinder().forField(otherOutlayPriceTextField).withConverter(stringToDoubleConverter).bind(Delegation::getOtherOutlayPrice, Delegation::setOtherOutlayPrice);

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
        delegationsGrid.addColumn(Delegation::getConfirmation).setCaption("Confirmation");
        delegationsGrid.addComponentColumn(this::buildSendConfirmBtn);
        delegationsGrid.addComponentColumn(this::buildPdfBtn).setCaption("PDF");
        delegationsGrid.addComponentColumn(this::buildPrintBtn).setCaption("Print");
        delegationsGrid.setWidth("100%");
        delegationsGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        delegationsGrid.addItemClickListener(itemClick -> {
            if (itemClick.getItem().getConfirmation().equals(Confirmation.NOT_CONFIRM) || itemClick.getItem().getConfirmation().equals(Confirmation.CONFIRM)) {
                delegationsGrid.getEditor().setEnabled(false);
            } else {
                delegationsGrid.getEditor().setEnabled(true);
            }
        });

        //SAVE LISTENER
        delegationsGrid.getEditor().addSaveListener(editorSaveEvent -> {
            if (editorSaveEvent.getBean().getDateTimeStop().isBefore(editorSaveEvent.getBean().getDateTimeStart())) {
                Notification.show("STOP DATE cannot be before START DATE", Notification.Type.ERROR_MESSAGE);
            } else {
                delegationService.changeDelegation(editorSaveEvent.getBean().getId(), editorSaveEvent.getBean());
                delegationsGrid.setItems(delegationService.getAllDelByUserOrderByDateStartDesc(loggedUser.getUid()));
                delegationsGrid.getDataProvider().refreshAll();
                Notification.show("Delegation has been saved", Notification.Type.HUMANIZED_MESSAGE);
            }

        });


        //DELETE BUTTON
        Button deleteDelegation = new Button("Delete");

        deleteDelegation.addClickListener(clickEvent ->
                delegationsGrid.getSelectedItems().stream()
                        .filter(f ->
                                {
                                    if (f.getDateTimeStart().isAfter(LocalDate.now())) {
                                        return true;
                                    } else {
                                        Notification.show("You can't delete this delegation!", Notification.Type.ERROR_MESSAGE);
                                        return false;
                                    }
                                }
                        )

                        .forEach(d -> {
                            delegationService.removeDelegation(loggedUser.getUid(), d.getId());
                            delegationListToCB.set(delegationService.getAllDelByUserOrderByDateStartDesc(loggedUser.getUid()));
                            delegationsGrid.setItems(delegationListToCB.get());
                            delegationsGrid.getDataProvider().refreshAll();
                            Notification.show("Delegation has been deleted", Notification.Type.HUMANIZED_MESSAGE);
                        }));
        //ADD BUTTON
        Button addDelegation = new Button("Add New");
        addDelegation.addClickListener(clickEvent ->
        {
            Delegation newDelegation = new Delegation(LocalDate.now().plusDays(5), LocalDate.now().plusDays(6), loggedUser);
            newDelegation.setDescription("New_Delegation");
            delegationService.addDelegation(loggedUser.getUid(), newDelegation);
            delegationsGrid.setItems(delegationService.getAllDelByUserOrderByDateStartDesc(loggedUser.getUid()));
            delegationsGrid.getDataProvider().refreshAll();
            Notification.show("Delegation has been added - EDIT IT!", Notification.Type.HUMANIZED_MESSAGE);
        });
        horizontaLayout.addComponents(addDelegation, deleteDelegation);
        settingFormLayout.addComponents(delegationsGrid, horizontaLayout);
        addComponent(settingFormLayout);
    }

    private AbstractComponent buildSendConfirmBtn(Delegation d) {
        if(d.getConfirmation().equals(Confirmation.NOT_CONFIRM) ) {
            return new Label();
        } else {
            Button button = new Button((d.getConfirmation().equals(Confirmation.NONE)) ? "Send confirm" : "Revoke confirm");
            button.addClickListener(e ->  {
                if (d.getConfirmation().equals(Confirmation.NONE))
                    d.setConfirmation(Confirmation.WAITING_FOR_CONFIRM);
                else
                    d.setConfirmation(Confirmation.NONE);
                delegationService.changeDelegation(d.getId(), d);
                delegationsGrid.setItems(delegationService.getAllDelByUserOrderByDateStartDesc(loggedUser.getUid()));
                delegationsGrid.getDataProvider().refreshAll();
            });
            return button;
        }
    }

    private Button buildPdfBtn(Delegation d) {
        Button button = new Button(VaadinIcons.FILE_TEXT_O);
        button.addStyleName(ValoTheme.BUTTON_BORDERLESS);

        StreamResource myResource = pdfService.getPdfStream(d);
        FileDownloader fileDownloader = new FileDownloader(myResource);
        fileDownloader.extend(button);
        return button;
    }

    private Button buildPrintBtn(Delegation d) {
        Button button = new Button(VaadinIcons.PRINT);
        button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        button.addClickListener(e -> {
            StreamResource resource = pdfService.getPdfStream(d);
            resource.setMIMEType("application/pdf");
            resource.getStream().setParameter(
                    "Content-Disposition",
                    "attachment; filename=delegation.pdf");

            BrowserWindowOpener opener = new BrowserWindowOpener(resource);
            opener.extend(button);
        });

        return button;
    }

}
