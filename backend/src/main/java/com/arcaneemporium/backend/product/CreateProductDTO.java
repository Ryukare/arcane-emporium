package com.arcaneemporium.backend.product;

import java.math.BigDecimal;

public record CreateProductDTO(String name, String description, int stock, BigDecimal price, int categoryId) {
}
