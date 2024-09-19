START TRANSACTION;

-- Create the 'users' table
CREATE TABLE IF NOT EXISTS users (
  id BIGINT PRIMARY KEY,
  register_date TIMESTAMP WITH TIME ZONE NOT NULL,
  last_active_date TIMESTAMP WITH TIME ZONE NOT NULL,
  last_used_username TEXT NOT NULL
);


-- Create the 'spots' table
CREATE TABLE IF NOT EXISTS spots (
  id BIGINT PRIMARY KEY,
  type VARCHAR(255) NOT NULL CHECK (type IN ('sunset')) DEFAULT 'sunset',
  location geography(Point, 4326) NOT NULL,
  name TEXT NOT NULL,
  description TEXT,
  added_by BIGINT REFERENCES users(id) NOT NULL,
  added_date TIMESTAMP WITH TIME ZONE NOT NULL,
  transport VARCHAR(255) NOT NULL CHECK (type IN ('by_foot', 'car', 'bike', 'public_transport'))
);
-- spatial index on the 'location' column in 'spots'
CREATE INDEX scenic_spots_location_idx ON spots USING GIST (location);


-- Create the 'spot_reviews' table
CREATE TABLE IF NOT EXISTS spot_reviews (
  id BIGINT PRIMARY KEY,
  spot BIGINT REFERENCES spots(id) NOT NULL,
  review_user BIGINT REFERENCES users(id) NOT NULL,
  rating INTEGER CHECK (rating BETWEEN 0 AND 5) NOT NULL,
  comment TEXT
);


-- Create the 'photos' table
CREATE TABLE IF NOT EXISTS photos (
  id BIGINT PRIMARY KEY,
  spot_id BIGINT REFERENCES spots(id),
  review_id BIGINT REFERENCES spot_reviews(id),
  uploaded_by BIGINT REFERENCES users(id) NOT NULL,
  uploaded_date TIMESTAMP WITH TIME ZONE NOT NULL,
  url TEXT NOT NULL
);

END;
