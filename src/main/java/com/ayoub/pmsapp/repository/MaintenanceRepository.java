package com.ayoub.pmsapp.repository;

import com.ayoub.pmsapp.entities.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findByMachineId(Long machineId);
    List<Maintenance> findByTechnicianId(Long technicianId);
    List<Maintenance> findByDate(LocalDate date);
}

