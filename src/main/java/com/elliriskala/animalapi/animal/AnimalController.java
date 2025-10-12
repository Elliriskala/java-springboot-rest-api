package com.elliriskala.animalapi.animal;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/animals")
@Tag(name = "Animals", description = "Animal management API")
public class AnimalController {

    private final AnimalRepository animalRepository;

    public AnimalController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    // get all animals
    @GetMapping("")
    List<Animal> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String sort) {
        return animalRepository.findAllPaginated(page, size, sort);
    }

    // get by id
    @GetMapping("/{id}")
    Animal findById(@PathVariable Long id) {

        Optional<Animal> animal = animalRepository.findById(id);
        if (animal.isEmpty()) {
            throw new AnimalNotFoundException();
        }
        return animal.get();
    }

    // get animals by species id
    @GetMapping("/species/{species_id}")
    List<Animal> findBySpeciesId(@PathVariable Long species_id) {
        return animalRepository.findBySpeciesId(species_id);
    }

    // sort animals by name
    @GetMapping("/sorted")
    List<Animal> findAllSorted() {
        List<Animal> animalList = animalRepository.findAll();
        animalList.sort((a1, a2) -> a1.animal_name().compareToIgnoreCase(a2.animal_name()));
        return animalList;
    }

    // get animals by name
    @GetMapping("/name/{name}")
    List<Animal> findByName(@PathVariable String name) {
        return animalRepository.findByName(name);
    }

    // get animals by location name
    @GetMapping("/location/{location_name}")
    List<Animal> findByLocationName(@PathVariable String location_name) {
        return animalRepository.findByLocationName(location_name);
    }

    // filter animals by species id and location name
    @GetMapping("/filter")
    List<Animal> findBySpeciesIdAndLocationName(@RequestParam Long species_id, @RequestParam String location_name) {
        return animalRepository.findBySpeciesIdAndLocationName(species_id, location_name);
    }

    // post
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Animal animal) {
        animalRepository.create(animal);
    }

    // put
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Animal animal, @PathVariable Long id) {
        animalRepository.update(animal, id);
    }

    // delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        animalRepository.delete(id);
    }
}
