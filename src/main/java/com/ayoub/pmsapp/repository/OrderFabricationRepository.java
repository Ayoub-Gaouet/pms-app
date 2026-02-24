package com.ayoub.pmsapp.repository;

import com.ayoub.pmsapp.entities.OrderFabrication;
import com.ayoub.pmsapp.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderFabricationRepository extends JpaRepository<OrderFabrication, Long> {
    List<OrderFabrication> findByStatut(OrderStatus statut);
    List<OrderFabrication> findByMachineId(Long machineId);
    List<OrderFabrication> findByProduitId(Long produitId);
    List<OrderFabrication> findByDate(LocalDate date);
}
