package com.arcaneemporium.backend.utils;

import com.arcaneemporium.backend.category.Category;
import com.arcaneemporium.backend.category.CategoryDTO;
import com.arcaneemporium.backend.product.CreateProductDTO;
import com.arcaneemporium.backend.product.Product;
import com.arcaneemporium.backend.product.ProductDTO;

public class ProductMapper {

    public static ProductDTO mapToProductDTO(Product product){
        return new ProductDTO(product.getName(), product.getDescription(), product.getPrice(),
                new CategoryDTO(product.getCategory().getName()));
    }

    public static Product mapToProduct(CreateProductDTO productDTO, Category category){
        return new Product(productDTO.name(), productDTO.description(), productDTO.stock(),
                productDTO.price(), category);
    }
}
