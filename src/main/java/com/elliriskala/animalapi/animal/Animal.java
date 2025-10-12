package com.elliriskala.animalapi.animal;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Animal(
                Long id,

                @NotEmpty @Size(min = 2, max = 100, message = "Animal name must be between 2 and 100 characters") String animal_name,

                @NotNull Long species_id,

                @NotNull @PastOrPresent(message = "Birthdate cannot be in the future") LocalDate birthdate,

                @NotEmpty String location_name,

                @NotNull @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90") @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90") BigDecimal latitude,

                @NotNull @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180") @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180") BigDecimal longitude) {
}
