package com.example.PSO.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Double accommodationPrice;
    private Double otherTicketsPrice;
    private String otherOutlayDesc;
    private Double otherOutlayPrice;

    @ManyToOne
    @JsonIgnore
    private User user;

    public Delegation(LocalDate dateTimeStart, LocalDate dateTimeStop, User user) {
        this.dateTimeStart = dateTimeStart;
        this.dateTimeStop = dateTimeStop;
        this.user = user;
    }
}
