package com.elliriskala.animalapi.species;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/species")
public class SpeciesController {
    private final SpeciesRepository speciesRepository;

    public SpeciesController(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    // get all species
    @GetMapping("")
    List<Species> findAll() {
        return speciesRepository.findAll();
    }

    // get by id
    @GetMapping("/{id}")
    Species findById(@PathVariable Long id) {

        Optional<Species> species = speciesRepository.findById(id);
        if (species.isEmpty()) {
            throw new SpeciesNotFoundException();
        }
        return species.get();
    }

    // get by category
    @GetMapping("/category/{categoryId}")
    List<Species> findByCategory(@PathVariable Long categoryId) {
        return speciesRepository.findByCategoryId(categoryId);
    }

    // sort species by name
    @GetMapping("/sorted")
    List<Species> findAllSorted() {
        List<Species> speciesList = speciesRepository.findAll();
        speciesList.sort((s1, s2) -> s1.species_name().compareToIgnoreCase(s2.species_name()));
        return speciesList;
    }

    // get by location name
    @GetMapping("/location/{location_name}")
    List<Species> findByLocationName(@PathVariable String location_name) {
        return speciesRepository.findByLocationName(location_name);
    }

    // post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Species species) {
        speciesRepository.create(species);
    }

    // put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Species species, @PathVariable Long id) {
        speciesRepository.update(species, id);
    }

    // delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        speciesRepository.delete(id);
    }
}
