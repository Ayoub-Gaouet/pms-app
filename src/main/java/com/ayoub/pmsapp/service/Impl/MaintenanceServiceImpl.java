package com.ayoub.pmsapp.service.Impl;

import com.ayoub.pmsapp.dto.MaintenanceRequestDTO;
import com.ayoub.pmsapp.dto.MaintenanceResponseDTO;
import com.ayoub.pmsapp.entities.Machine;
import com.ayoub.pmsapp.entities.Maintenance;
import com.ayoub.pmsapp.entities.Technician;
import com.ayoub.pmsapp.exception.ResourceNotFoundException;
import com.ayoub.pmsapp.repository.MachineRepository;
import com.ayoub.pmsapp.repository.MaintenanceRepository;
import com.ayoub.pmsapp.repository.TechnicianRepository;
import com.ayoub.pmsapp.service.MaintenanceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final MachineRepository machineRepository;
    private final TechnicianRepository technicianRepository;
    private final ModelMapper modelMapper;

    public MaintenanceServiceImpl(MaintenanceRepository maintenanceRepository,
                                  MachineRepository machineRepository,
                                  TechnicianRepository technicianRepository, ModelMapper modelMapper) {
        this.maintenanceRepository = maintenanceRepository;
        this.machineRepository = machineRepository;
        this.technicianRepository = technicianRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public MaintenanceResponseDTO createMaintenance(MaintenanceRequestDTO dto) {
        Maintenance maintenance = convertDtoToEntity(dto);
        Maintenance saved = maintenanceRepository.save(maintenance);
        return convertEntityToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceResponseDTO getMaintenanceById(Long id) {
        return convertEntityToDto(findMaintenanceById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceResponseDTO> getAllMaintenances() {
        return maintenanceRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceResponseDTO> getMaintenancesByMachineId(Long machineId) {
        return maintenanceRepository.findByMachineId(machineId).stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceResponseDTO> getMaintenancesByTechnicianId(Long technicianId) {
        return maintenanceRepository.findByTechnicianId(technicianId).stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceResponseDTO> getMaintenancesByDate(LocalDate date) {
        return maintenanceRepository.findByDate(date).stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    @Transactional
    public MaintenanceResponseDTO updateMaintenance(Long id, MaintenanceRequestDTO dto) {
        Maintenance existing = findMaintenanceById(id);
        Machine machine = findMachineOrThrow(dto.getMachineId());
        Technician technician = findTechnicianOrThrow(dto.getTechnicianId());
        existing.setMachine(machine);
        existing.setTechnician(technician);
        existing.setDate(dto.getDate());
        existing.setType(dto.getType());

        Maintenance saved = maintenanceRepository.save(existing);
        return convertEntityToDto(saved);
    }

    @Override
    @Transactional
    public void deleteMaintenance(Long id) {
        Maintenance maintenance = findMaintenanceById(id);
        maintenanceRepository.delete(maintenance);
    }

    @Override
    public MaintenanceResponseDTO convertEntityToDto(Maintenance maintenance) {
        return modelMapper.map(maintenance, MaintenanceResponseDTO.class);
    }

    @Override
    public Maintenance convertDtoToEntity(MaintenanceRequestDTO dto) {
        Maintenance maintenance = modelMapper.map(dto, Maintenance.class);
        maintenance.setMachine(findMachineOrThrow(dto.getMachineId()));
        maintenance.setTechnician(findTechnicianOrThrow(dto.getTechnicianId()));
        return maintenance;
    }


    @Override
    public Maintenance findMaintenanceById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance not found with id=" + id));
    }

    private Machine findMachineOrThrow(Long id) {
        return machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found with id=" + id));
    }

    private Technician findTechnicianOrThrow(Long id) {
        return technicianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Technician not found with id=" + id));
    }
}

