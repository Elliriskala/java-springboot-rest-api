package com.elliriskala.animalapi.animal;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AnimalJsonDataLoader implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(AnimalJsonDataLoader.class);

    private final AnimalRepository animalRepository;
    private final ObjectMapper objectMapper;

    public AnimalJsonDataLoader(AnimalRepository animalRepository, ObjectMapper objectMapper) {
        this.animalRepository = animalRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
       if(animalRepository.count() == 0) {
        try (InputStream inputStream = getClass().getResourceAsStream("/data/animals.json")) {
            if (inputStream == null) {
                log.error("Could not find animals.json file in resources/data/");
                return;
            }
            Animals allAnimals = objectMapper.readValue(inputStream, Animals.class);
            log.info("Reading {} animals from JSON file", allAnimals.animals().size());
            animalRepository.saveAll(allAnimals.animals());
        } catch (IOException e) {
            log.error("Failed to load animal data", e);
        }
         } else {
            log.info("Animal data already exists, skipping JSON data load");
         }
    }
}
