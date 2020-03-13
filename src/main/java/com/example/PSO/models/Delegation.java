package com.example.PSO.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "delegations")
public class Delegation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @NotNull
    private LocalDate dateTimeStart;
    @NotNull
    private LocalDate dateTimeStop;
    private Integer travelDietAmount = 30;
    private Integer breakfastNumber = 0;
    private Integer dinnerNumber = 0;
    private Integer supperNumber = 0;
    @Enumerated(EnumType.STRING)
    private TransportType transportType = TransportType.AUTO;
    private Double ticketPrice;
    @Enumerated(EnumType.STRING)
    private AutoCapacity autoCapacity;
    private Integer km;
    private Double accomodationPrice;
    private Double otherTicketsPrice;
    private Double otherOutlayDesc;
    private Double otherOutlayPrice;

    @ManyToOne
    private User user;
}
