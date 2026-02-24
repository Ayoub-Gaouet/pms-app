package com.ayoub.pmsapp.controllers;

import com.ayoub.pmsapp.dto.OrderFabricationRequestDTO;
import com.ayoub.pmsapp.dto.OrderFabricationResponseDTO;
import com.ayoub.pmsapp.entities.OrderStatus;
import com.ayoub.pmsapp.service.OrderFabricationService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ordres-fabrication")
@CrossOrigin("*")
public class OrderFabricationRestController {

    private final OrderFabricationService orderFabricationService;

    public OrderFabricationRestController(OrderFabricationService orderFabricationService) {
        this.orderFabricationService = orderFabricationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderFabricationResponseDTO createOrdreFabrication(@Valid @RequestBody OrderFabricationRequestDTO dto) {
        return orderFabricationService.createOrdreFabrication(dto);
    }

    @GetMapping("/{id}")
    public OrderFabricationResponseDTO getOrdreFabricationById(@PathVariable Long id) {
        return orderFabricationService.getOrdreFabricationById(id);
    }

    @GetMapping
    public List<OrderFabricationResponseDTO> getOrdresFabrication(
            @RequestParam(required = false) OrderStatus statut,
            @RequestParam(required = false) Long machineId,
            @RequestParam(required = false) Long produitId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (statut != null) return orderFabricationService.getOrdresByStatut(statut);
        if (machineId != null) return orderFabricationService.getOrdresByMachineId(machineId);
        if (produitId != null) return orderFabricationService.getOrdresByProduitId(produitId);
        if (date != null) return orderFabricationService.getOrdresByDate(date);

        return orderFabricationService.getAllOrdresFabrication();
    }

    @PutMapping("/{id}")
    public OrderFabricationResponseDTO updateOrdreFabrication(@PathVariable Long id,
                                                              @Valid @RequestBody OrderFabricationRequestDTO dto) {
        return orderFabricationService.updateOrdreFabrication(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrdreFabrication(@PathVariable Long id) {
        orderFabricationService.deleteOrdreFabrication(id);
    }
}
