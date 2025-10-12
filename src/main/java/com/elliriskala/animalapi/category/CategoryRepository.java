package com.elliriskala.animalapi.category;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class CategoryRepository {
    private final JdbcClient jdbcClient;

    public CategoryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // get all categories from the DB
    public List<Category> findAll() {
        return jdbcClient.sql("SELECT * FROM categories").query(Category.class).list();
    }

    // find a category by it's id
    public Optional<Category> findById(Long id) {
        return jdbcClient.sql("SELECT * FROM categories WHERE id = :id")
                .param("id", id)
                .query(Category.class)
                .optional();

    }

    // create a new category
    public void create(Category category) {
        var updated = jdbcClient.sql(
                "INSERT INTO categories(category_name) values(?)")
                .params(List.of(category.category_name()))
                .update();

        Assert.state(updated == 1, "Failed to insert category " + category.category_name());
    }

    // update a category
    public void update(Category category, Long id) {
        var updated = jdbcClient.sql(
                "UPDATE categories SET category_name = ? WHERE id = ?")
                .params(List.of(category.category_name(), id))
                .update();

        Assert.state(updated == 1, "Failed to update category with id " + id);
    }

    // delete a category
    public void delete(Long id) {
        var updated = jdbcClient.sql("DELETE FROM categories WHERE id = ?")
                .param(1, id)
                .update();

        Assert.state(updated == 1, "Failed to delete category with id " + id);
    }

     public void saveAll(List<Category> categories) {
        categories.stream().forEach(this::create);
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM categories").query().listOfRows().size();
    }
}