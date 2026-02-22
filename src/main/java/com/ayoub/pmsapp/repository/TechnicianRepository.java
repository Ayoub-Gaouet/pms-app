package com.ayoub.pmsapp.repository;

import com.ayoub.pmsapp.entities.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {

    List<Technician> findByMachineAssigneeId(Long machineId);

    List<Technician> findBySkillId(Long skillId);
}
