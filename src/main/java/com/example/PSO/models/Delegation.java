package com.example.PSO.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private Double ticketPrice=0.0;
    @Enumerated(EnumType.STRING)
    private AutoCapacity autoCapacity = AutoCapacity.LESS900;
    private Integer km =0;
    private Double accommodationPrice=0.0;
    private Double otherTicketsPrice=0.0;
    private String otherOutlayDesc;
    private Double otherOutlayPrice=0.0;
    private Confirmation confirmation = Confirmation.NONE;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private User user;

    public Delegation(LocalDate dateTimeStart, LocalDate dateTimeStop, User user) {
        this.dateTimeStart = dateTimeStart;
        this.dateTimeStop = dateTimeStop;
        this.user = user;
    }
}
