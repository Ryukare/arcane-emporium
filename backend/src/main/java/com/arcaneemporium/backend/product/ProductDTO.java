package com.arcaneemporium.backend.product;

import com.arcaneemporium.backend.category.CategoryDTO;

import java.math.BigDecimal;

public record ProductDTO(String name, String description, BigDecimal price, CategoryDTO category) {
}
