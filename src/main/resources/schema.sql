-- Animal API Database SQL Schema

-- Categories table
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE,
    CONSTRAINT chk_category_name_length CHECK (CHAR_LENGTH(category_name) >= 2)
);

-- Species table
CREATE TABLE IF NOT EXISTS species (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    species_name VARCHAR(100) NOT NULL UNIQUE,
    category_id BIGINT NOT NULL,
    location_name VARCHAR(255) NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    image VARCHAR(2048),
    
    CONSTRAINT fk_species_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
    CONSTRAINT chk_species_name_length CHECK (CHAR_LENGTH(species_name) >= 2),
    CONSTRAINT chk_latitude_range CHECK (latitude >= -90 AND latitude <= 90),
    CONSTRAINT chk_longitude_range CHECK (longitude >= -180 AND longitude <= 180)
);

-- Animals table
CREATE TABLE IF NOT EXISTS animals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    animal_name VARCHAR(100) NOT NULL UNIQUE,
    species_id BIGINT NOT NULL,
    birthdate DATE NOT NULL,
    location_name VARCHAR(255) NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    
    CONSTRAINT fk_animals_species FOREIGN KEY (species_id) REFERENCES species(id) ON DELETE CASCADE,
    CONSTRAINT chk_animal_name_length CHECK (CHAR_LENGTH(animal_name) >= 2),
    CONSTRAINT chk_animal_latitude_range CHECK (latitude >= -90 AND latitude <= 90),
    CONSTRAINT chk_animal_longitude_range CHECK (longitude >= -180 AND longitude <= 180)
);
