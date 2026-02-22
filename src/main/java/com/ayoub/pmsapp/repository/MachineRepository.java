package com.ayoub.pmsapp.repository;

import com.ayoub.pmsapp.entities.Machine;
import com.ayoub.pmsapp.entities.MachineState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MachineRepository extends JpaRepository<Machine, Long> {
    List<Machine> findByEtat(MachineState etat);
}