package com.ayoub.pmsapp.dto;

import com.ayoub.pmsapp.entities.OrderStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderFabricationRequestDTO {

    @NotNull(message = "produitId is required")
    private Long produitId;

    @NotNull(message = "quantite is required")
    @Min(value = 1, message = "quantite must be at least 1")
    private Integer quantite;

    @NotNull(message = "date is required")
    private LocalDate date;

    @NotNull(message = "machineId is required")
    private Long machineId;

    @NotNull(message = "statut is required")
    private OrderStatus statut;
}
