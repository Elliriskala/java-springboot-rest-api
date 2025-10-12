-- Insert some sample data for testing (MariaDB compatible)
INSERT IGNORE INTO categories (category_name) VALUES 
('Mammals'), 
('Birds'), 
('Reptiles'), 
('Fish'), 
('Amphibians');

INSERT IGNORE INTO species (species_name, category_id, location_name, latitude, longitude, image) VALUES 
('Cat', 1, 'Middle East', 35.2131, 38.9968, 'https://example.com/cat.jpg'),
('Bald Eagle', 2, 'North America', 64.0685, -152.2782, 'https://example.com/eagle.jpg'),
('Sea Turtle', 3, 'Pacific Ocean', 21.3099, -157.8581, 'https://example.com/turtle.jpg'),
('African Lion', 1, 'African Savanna', -1.2921, 36.8219, 'https://example.com/lion.jpg'),
('Emperor Penguin', 2, 'Antarctica', -77.8462, 166.6890, 'https://example.com/penguin.jpg'),
('Golden Retriever', 1, 'Scotland', 56.4907, -4.2026, 'https://example.com/golden.jpg');
