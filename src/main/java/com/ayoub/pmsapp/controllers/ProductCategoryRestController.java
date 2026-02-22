package com.ayoub.pmsapp.controllers;

import com.ayoub.pmsapp.entities.ProductCategory;
import com.ayoub.pmsapp.service.ProductCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cat")
@CrossOrigin("*")
public class ProductCategoryRestController {
    final ProductCategoryService productCategoryService;

    public ProductCategoryRestController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @RequestMapping(method= RequestMethod.GET)
    public List<ProductCategory> getAllCategories()
    {
        return productCategoryService.getAllCategories();
    }
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public ProductCategory findCategoryById(@PathVariable("id") Long id) {
        return productCategoryService.findCategoryById(id);
    }

}