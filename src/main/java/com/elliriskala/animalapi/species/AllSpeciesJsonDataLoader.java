package com.elliriskala.animalapi.species;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AllSpeciesJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AllSpeciesJsonDataLoader.class);

    private final SpeciesRepository allSpeciesRepository;
    private final ObjectMapper objectMapper;

    public AllSpeciesJsonDataLoader(SpeciesRepository allSpeciesRepository, ObjectMapper objectMapper) {
        this.allSpeciesRepository = allSpeciesRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (allSpeciesRepository.count() == 0) {
            try (InputStream inputStream = getClass().getResourceAsStream("/data/species.json")) {
                if (inputStream == null) {
                    log.error("Could not find species.json file in resources/data/");
                    return;
                }
                AllSpecies allSpecies = objectMapper.readValue(inputStream, AllSpecies.class);
                log.info("Reading {} species from JSON file", allSpecies.species().size());
                allSpeciesRepository.saveAll(allSpecies.species());
            } catch (IOException e) {
                log.error("Failed to load species data", e);
            }
        } else {
            log.info("Species data already exists, skipping JSON data load");
        }
    }
}