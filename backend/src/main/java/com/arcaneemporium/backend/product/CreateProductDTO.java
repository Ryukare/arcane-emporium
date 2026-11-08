package com.arcaneemporium.backend.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductDTO(@NotBlank @Size(min = 3, max = 100) String name,
                               @NotBlank @Size(min = 3, max = 1000) String description,
                               @Min(0) int stock,
                               @Min(0) BigDecimal price,
                               int categoryId) {
}
