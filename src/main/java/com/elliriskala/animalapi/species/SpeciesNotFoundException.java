package com.elliriskala.animalapi.species;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SpeciesNotFoundException extends RuntimeException {
    public SpeciesNotFoundException() {
        super("Species not found");
    }
}   
