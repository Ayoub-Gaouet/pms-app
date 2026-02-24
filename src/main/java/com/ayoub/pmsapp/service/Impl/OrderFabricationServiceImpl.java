package com.ayoub.pmsapp.service.Impl;

import com.ayoub.pmsapp.dto.OrderFabricationRequestDTO;
import com.ayoub.pmsapp.dto.OrderFabricationResponseDTO;
import com.ayoub.pmsapp.entities.Machine;
import com.ayoub.pmsapp.entities.OrderStatus;
import com.ayoub.pmsapp.entities.OrderFabrication;
import com.ayoub.pmsapp.entities.Product;
import com.ayoub.pmsapp.exception.ResourceNotFoundException;
import com.ayoub.pmsapp.repository.MachineRepository;
import com.ayoub.pmsapp.repository.OrderFabricationRepository;
import com.ayoub.pmsapp.repository.ProductRepository;
import com.ayoub.pmsapp.service.OrderFabricationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderFabricationServiceImpl implements OrderFabricationService {

    private final OrderFabricationRepository orderFabricationRepository;
    private final ProductRepository productRepository;
    private final MachineRepository machineRepository;
    private final ModelMapper modelMapper;

    public OrderFabricationServiceImpl(OrderFabricationRepository orderFabricationRepository,
                                       ProductRepository productRepository,
                                       MachineRepository machineRepository,
                                       ModelMapper modelMapper) {
        this.orderFabricationRepository = orderFabricationRepository;
        this.productRepository = productRepository;
        this.machineRepository = machineRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public OrderFabricationResponseDTO createOrdreFabrication(OrderFabricationRequestDTO orderFabricationRequestDTO) {
        OrderFabrication orderFabrication = convertDtoToEntity(orderFabricationRequestDTO);
        OrderFabrication saved = orderFabricationRepository.save(orderFabrication);
        return convertEntityToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderFabricationResponseDTO getOrdreFabricationById(Long id) {
        return convertEntityToDto(findOrdreFabricationById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderFabricationResponseDTO> getAllOrdresFabrication() {
        return orderFabricationRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderFabricationResponseDTO> getOrdresByStatut(OrderStatus orderStatus) {
        return orderFabricationRepository.findByStatut(orderStatus).stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderFabricationResponseDTO> getOrdresByMachineId(Long machineId) {
        return orderFabricationRepository.findByMachineId(machineId).stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderFabricationResponseDTO> getOrdresByProduitId(Long produitId) {
        return orderFabricationRepository.findByProduitId(produitId).stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderFabricationResponseDTO> getOrdresByDate(LocalDate date) {
        return orderFabricationRepository.findByDate(date).stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderFabricationResponseDTO updateOrdreFabrication(Long id, OrderFabricationRequestDTO dto) {
        OrderFabrication existing = findOrdreFabricationById(id);
        Product produit = findProduitOrThrow(dto.getProduitId());
        Machine machine = findMachineOrThrow(dto.getMachineId());
        existing.setProduit(produit);
        existing.setQuantite(dto.getQuantite());
        existing.setDate(dto.getDate());
        existing.setMachine(machine);
        existing.setStatut(dto.getStatut());
        OrderFabrication saved = orderFabricationRepository.save(existing);
        return convertEntityToDto(saved);
    }

    @Override
    @Transactional
    public void deleteOrdreFabrication(Long id) {
        OrderFabrication ordre = findOrdreFabricationById(id);
        orderFabricationRepository.delete(ordre);
    }

    @Override
    public OrderFabricationResponseDTO convertEntityToDto(OrderFabrication ordre) {
        return modelMapper.map(ordre, OrderFabricationResponseDTO.class);
    }

    @Override
    public OrderFabrication convertDtoToEntity(OrderFabricationRequestDTO dto) {
        return modelMapper.map(dto, OrderFabrication.class);
    }


    @Override
    public OrderFabrication findOrdreFabricationById(Long id) {
        return orderFabricationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrdreFabrication not found with id=" + id));
    }

    private Product findProduitOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id=" + id));
    }

    private Machine findMachineOrThrow(Long id) {
        return machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found with id=" + id));
    }
}
