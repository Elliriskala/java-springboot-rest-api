package com.elliriskala.animalapi.animal;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class AnimalRepository {

    private final JdbcClient jdbcClient;

    public AnimalRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // get all animals from the DB
    public List<Animal> findAll() {
        return jdbcClient.sql("SELECT * FROM animals").query(Animal.class).list();
    }

    // get all animals with pagination and optional sorting
    public List<Animal> findAllPaginated(int page, int size, String sort) {
        String baseQuery = "SELECT * FROM animals";
        String orderByClause = "";

        if (sort != null && !sort.isEmpty()) {
            String[] sortParams = sort.split(",");
            if (sortParams.length == 2) {
                String sortField = sortParams[0];
                String sortOrder = sortParams[1].equalsIgnoreCase("desc") ? "DESC" : "ASC";
                orderByClause = " ORDER BY " + sortField + " " + sortOrder;
            }
        }

        int offset = page * size;
        String paginatedQuery = baseQuery + orderByClause + " LIMIT :size OFFSET :offset";

        return jdbcClient.sql(paginatedQuery)
                .param("size", size)
                .param("offset", offset)
                .query(Animal.class)
                .list();
    }

    // get an animal by it's id
    public Optional<Animal> findById(Long id) {
        return jdbcClient.sql("SELECT * FROM animals WHERE id = :id")
                .param("id", id)
                .query(Animal.class)
                .optional();

    }

    // get animals by name
    public List<Animal> findByName(String name) {
        return jdbcClient.sql("SELECT * FROM animals WHERE animal_name = :name")
                .param("name", name)
                .query(Animal.class)
                .list();
    }

    // get animals based on their location name
    public List<Animal> findByLocationName(String location_name) {
        return jdbcClient.sql("SELECT * FROM animals WHERE location_name = :location_name")
                .param("location_name", location_name)
                .query(Animal.class)
                .list();
    }

    //

    // get animals by species id
    public List<Animal> findBySpeciesId(Long species_id) {
        return jdbcClient.sql("SELECT * FROM animals WHERE species_id = :species_id")
                .param("species_id", species_id)
                .query(Animal.class)
                .list();
    }

    // filter animals by species id and location name
    public List<Animal> findBySpeciesIdAndLocationName(Long species_id, String location_name) {
        return jdbcClient.sql("SELECT * FROM animals WHERE species_id = :species_id AND location_name = :location_name")
                .param("species_id", species_id)
                .param("location_name", location_name)
                .query(Animal.class)
                .list();
    }

    // create a new animal
    public void create(Animal animal) {
        var updated = jdbcClient.sql(
                "INSERT INTO animals(animal_name, species_id, birthdate, location_name, latitude, longitude) values(?, ?, ?, ?, ?, ?)")
                .params(List.of(animal.animal_name(), animal.species_id(), animal.birthdate(),
                        animal.location_name(), animal.latitude(), animal.longitude()))
                .update();

        Assert.state(updated == 1, "Failed to insert animal " + animal.animal_name());
    }

    // update an animal
    public void update(Animal animal, Long id) {
        var updated = jdbcClient.sql(
                "UPDATE animals SET animal_name = ?, species_id = ?, birthdate = ?, location_name = ?, latitude = ?, longitude = ? WHERE id = ?")
                .params(List.of(animal.animal_name(), animal.species_id(), animal.birthdate(),
                        animal.location_name(), animal.latitude(), animal.longitude(), id))
                .update();

        Assert.state(updated == 1, "Failed to update animal with id " + id);
    }

    // delete an animal
    public void delete(Long id) {
        var updated = jdbcClient.sql("DELETE FROM animals WHERE id = ?")
                .param(1, id)
                .update();

        Assert.state(updated == 1, "Failed to delete animal with id " + id);
    }

    public void saveAll(List<Animal> animals) {
        animals.stream().forEach(this::create);
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM animals").query().listOfRows().size();
    }

}
