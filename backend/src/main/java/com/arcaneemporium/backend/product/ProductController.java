package com.arcaneemporium.backend.product;

import com.arcaneemporium.backend.category.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable int id){
        return service.getProduct(id);
    }

    @GetMapping("/list")
    public List<ProductDTO> getProductList(){
        return service.getProducts();
    }

    @PostMapping("/add_product")
    public CreateProductDTO createProduct(@RequestBody @Valid CreateProductDTO createProductDTO){
        return service.createProduct(createProductDTO);
    }
}
