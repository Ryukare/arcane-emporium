package com.arcaneemporium.backend.product;

import com.arcaneemporium.backend.category.Category;
import com.arcaneemporium.backend.category.CategoryDTO;

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
