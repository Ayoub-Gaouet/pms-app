package com.ayoub.pmsapp.dto;

import com.ayoub.pmsapp.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderFabricationResponseDTO {

    private Long id;
    private Long produitId;
    private String produitName;
    private Integer quantite;
    private LocalDate date;
    private Long machineId;
    private String machineNom;
    private OrderStatus statut;
}
