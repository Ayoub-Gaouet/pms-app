package com.ayoub.pmsapp.service;

import com.ayoub.pmsapp.dto.OrderFabricationRequestDTO;
import com.ayoub.pmsapp.dto.OrderFabricationResponseDTO;
import com.ayoub.pmsapp.entities.OrderStatus;
import com.ayoub.pmsapp.entities.OrderFabrication;

import java.time.LocalDate;
import java.util.List;

public interface OrderFabricationService {

    OrderFabricationResponseDTO createOrdreFabrication(OrderFabricationRequestDTO dto);

    OrderFabricationResponseDTO getOrdreFabricationById(Long id);

    OrderFabrication findOrdreFabricationById(Long id);

    List<OrderFabricationResponseDTO> getAllOrdresFabrication();

    List<OrderFabricationResponseDTO> getOrdresByStatut(OrderStatus statut);

    List<OrderFabricationResponseDTO> getOrdresByMachineId(Long machineId);

    List<OrderFabricationResponseDTO> getOrdresByProduitId(Long produitId);

    List<OrderFabricationResponseDTO> getOrdresByDate(LocalDate date);

    OrderFabricationResponseDTO updateOrdreFabrication(Long id, OrderFabricationRequestDTO dto);

    void deleteOrdreFabrication(Long id);

    OrderFabricationResponseDTO convertEntityToDto(OrderFabrication ordre);

    OrderFabrication convertDtoToEntity(OrderFabricationRequestDTO dto);
}
