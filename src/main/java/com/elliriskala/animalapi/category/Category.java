package com.elliriskala.animalapi.category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record Category(
        Long id,
        @NotEmpty @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters") String category_name) {

}
