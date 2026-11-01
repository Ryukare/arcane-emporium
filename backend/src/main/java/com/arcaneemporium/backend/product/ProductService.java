package com.arcaneemporium.backend.product;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository = repository;
    }
}
