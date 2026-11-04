package com.arcaneemporium.backend.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDTO(@NotBlank @Size(min = 3, max = 50) String name) {
}
