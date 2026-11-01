package com.arcaneemporium.backend.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }


    @GetMapping("/home")
    public String showHomePage(){
        return "Home";
    }

    @GetMapping("/list")
    public List<ProductDTO> getProductList(){
        return service.getProducts();
    }
}
