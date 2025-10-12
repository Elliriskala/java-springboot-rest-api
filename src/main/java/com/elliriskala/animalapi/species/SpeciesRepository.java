package com.elliriskala.animalapi.species;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class SpeciesRepository {
    private final JdbcClient jdbcClient;

    public SpeciesRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // get all species from the DB
    public List<Species> findAll() {
        return jdbcClient.sql("SELECT * FROM species").query(Species.class).list();
    }

    // find a species by it's id
    public Optional<Species> findById(Long id) {
        return jdbcClient.sql("SELECT * FROM species WHERE id = :id")
                .param("id", id)
                .query(Species.class)
                .optional();

    }

    // find species by category id
    public List<Species> findByCategoryId(Long categoryId) {
        return jdbcClient.sql("SELECT * FROM species WHERE category_id = :categoryId")
                .param("categoryId", categoryId)
                .query(Species.class)
                .list();
    }

    // find species by location name
    public List<Species> findByLocationName(String location_name) {
        return jdbcClient.sql("SELECT * FROM species WHERE location_name = :location_name")
                .param("location_name", location_name)
                .query(Species.class)
                .list();
    }

    // create a new species
    public void create(Species species) {
        var updated = jdbcClient.sql(
                "INSERT INTO species(species_name, category_id, location_name, latitude, longitude, image) values(?, ?, ?, ?, ?, ?)")
                .params(List.of(species.species_name(), species.category_id(),
                        species.location_name(), species.latitude(), species.longitude(),
                        species.image() != null ? species.image() : ""))
                .update();

        Assert.state(updated == 1, "Failed to insert species " + species.species_name());
    }

    // update a species
    public void update(Species species, Long id) {
        var updated = jdbcClient.sql(
                "UPDATE species SET species_name = ?, category_id = ?, location_name = ?, latitude = ?, longitude = ?, image = ? WHERE id = ?")
                .params(List.of(species.species_name(), species.category_id(), species.location_name(), species.latitude(),
                        species.longitude(), species.image(), id))
                .update();

        Assert.state(updated == 1, "Failed to update species with id " + id);
    }

    // delete a species
    public void delete(Long id) {
        var updated = jdbcClient.sql("DELETE FROM species WHERE id = ?")
                .param(1, id)
                .update();

        Assert.state(updated == 1, "Failed to delete species with id " + id);
    }

    public void saveAll(List<Species> species) {
        species.stream().forEach(this::create);
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM species").query().listOfRows().size();
    }

}
