package com.elliriskala.animalapi.animal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AnimalNotFoundException extends RuntimeException {
    public AnimalNotFoundException() {
        super("Animal not found");
    }
}   
