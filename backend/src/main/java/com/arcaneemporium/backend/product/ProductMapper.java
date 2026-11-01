package com.arcaneemporium.backend.product;

import com.arcaneemporium.backend.category.CategoryDTO;

public class ProductMapper {

    public static ProductDTO mapProduct(Product product){
        return new ProductDTO(product.getName(), product.getDescription(), product.getPrice(),
                new CategoryDTO(product.getCategory().getName()));
    }
}
