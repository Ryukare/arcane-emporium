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

    @PutMapping("/{id}")
    public CreateProductDTO updateProduct(@PathVariable int id,
                                          @RequestBody @Valid CreateProductDTO createProductDTO){
        return service.updateProduct(id, createProductDTO);
    }

    @DeleteMapping("/{id}")
    public ProductDTO deleteProduct(@PathVariable int id){
        return service.deleteProduct(id);
    }
}
