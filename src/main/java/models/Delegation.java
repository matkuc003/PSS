package models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
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
    private TransportType transportType = TransportType.AUTO;
    private Double ticketPrice;
    private AutoCapacity autoCapacity;
    private Integer km;
    private Double accomodationPrice;
    private Double otherTicketsPrice;
    private Double otherOutlayDesc;
    private Double otherOutlayPrice;
}
