package com.arcaneemporium.backend.product;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository = repository;
    }

    public List<ProductDTO> getProducts(){
        return repository.findAll().stream().map(ProductMapper::mapProduct)
                .toList();
    }
}
