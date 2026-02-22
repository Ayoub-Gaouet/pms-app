package com.ayoub.pmsapp.controllers.supplier;

import com.ayoub.pmsapp.dto.SupplierDTO;
import com.ayoub.pmsapp.entities.supplier.Supplier;
import com.ayoub.pmsapp.service.supplier.SupplierService;
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
    public List<Supplier> getAllSuppliers(){
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public Supplier findSupplierById(@PathVariable long id){
        return supplierService.findSupplierById(id);
    }

    @PostMapping("")
    public Supplier saveSupplier(@RequestBody SupplierDTO supplierDTO){
        return supplierService.saveSupplier(supplierDTO);
    }

    @PutMapping("/{id}")
    public Supplier updateSupplier(@PathVariable long id, @RequestBody SupplierDTO supplierDTO){
        return supplierService.updateSupplier(id, supplierDTO);
    }

}
