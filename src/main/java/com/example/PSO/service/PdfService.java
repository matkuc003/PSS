package com.example.PSO.service;

import com.example.PSO.models.Delegation;
import com.example.PSO.models.TransportType;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.server.StreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class PdfService {

    public StreamResource getPdfStream(Delegation d) {
        StreamResource.StreamSource source = (StreamResource.StreamSource) () -> {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                PdfWriter.getInstance(document, baos);
                document.open();

                Font font = FontFactory.getFont(FontFactory.COURIER, 24, BaseColor.BLACK);
                Paragraph chunk = new Paragraph("Delegation nr " + d.getId(), font);
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);
                document.add(Chunk.NEWLINE);
                document.add(new Paragraph("User: " + d.getUser().getName() + " " + d.getUser().getLastName()));
                document.add(new Paragraph("From: " + d.getDateTimeStart().toString()));
                document.add(new Paragraph("To: " + d.getDateTimeStop().toString()));
                document.add(new Paragraph("Description: " + d.getDescription()));
                document.add(new Paragraph("Travel diet amount: " + d.getTravelDietAmount()));
                document.add(new Paragraph("Number of meals: "));

                List meals = new List(List.UNORDERED);
                meals.add(new ListItem("Breakfast: " + d.getBreakfastNumber()));
                meals.add(new ListItem("Dinner: " + d.getDinnerNumber()));
                meals.add(new ListItem("Supper: " + d.getSupperNumber()));

                document.add(meals);
                document.add(new Paragraph("Transport type: " + d.getTransportType()));
                if(d.getTransportType().equals(TransportType.AUTO)) {
                    document.add(new Paragraph("Auto capacity: " + d.getAutoCapacity()));
                    document.add(new Paragraph("Km: " + d.getKm()));
                } else {
                    document.add(new Paragraph("Ticket price: " + d.getTicketPrice()));
                }
                document.add(new Paragraph("Accommodation price: " + d.getAccommodationPrice()));
                document.add(new Paragraph("Other tickets price: " + d.getOtherTicketsPrice()));
                document.add(new Paragraph("Other outlay description: " + d.getOtherOutlayDesc()));
                document.add(new Paragraph("Other outlay price: " + d.getOtherOutlayPrice()));
                document.add(new Paragraph("Confirmation status: " + d.getConfirmation()));

                document.close();

            } catch (DocumentException ex) {
                ex.printStackTrace();
            }

            ByteArrayOutputStream stream = baos;
            InputStream input = new ByteArrayInputStream(stream.toByteArray());
            return input;

        };
        StreamResource resource = new StreamResource ( source, "delegation.pdf" );
        return resource;
    }
}
