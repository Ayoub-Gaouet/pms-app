package com.ayoub.pmsapp.controllers;

import com.ayoub.pmsapp.dto.SupplierRequestDTO;
import com.ayoub.pmsapp.dto.SupplierResponseDTO;
import com.ayoub.pmsapp.service.SupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/suppliers")
@CrossOrigin(origins = "*")
@RestController
public class SupplierRestController {
    private final SupplierService supplierService;

    public SupplierRestController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("")
    public List<SupplierResponseDTO> getAllSuppliers(){
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public SupplierResponseDTO findSupplierById(@PathVariable Long id){
        return supplierService.findSupplierById(id);
    }

    @PostMapping("")
    public SupplierResponseDTO saveSupplier(@RequestBody SupplierRequestDTO supplierDTO){
        return supplierService.saveSupplier(supplierDTO);
    }

    @PutMapping("/{id}")
    public SupplierResponseDTO updateSupplier(@PathVariable Long id, @RequestBody SupplierRequestDTO supplierDTO){
        return supplierService.updateSupplier(id, supplierDTO);
    }

}
