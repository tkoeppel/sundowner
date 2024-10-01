-- Connect to created Database
\c sundowner_%s_db

-- Install postgis beforehand
SET ROLE sundowner_%s;
CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS postgis_topology;

ALTER SCHEMA topology OWNER TO sundowner_%s;

ALTER TABLE spatial_ref_sys OWNER TO sundowner_%s;
ALTER TABLE layer OWNER TO sundowner_%s;
ALTER TABLE topology OWNER TO sundowner_%s;

-- Create Tables
START TRANSACTION;

-- Create the 'spots' table
CREATE TABLE IF NOT EXISTS spots (
  id BIGINT PRIMARY KEY,
  type VARCHAR(255) NOT NULL CHECK (type IN ('sunset')) DEFAULT 'sunset',
  location geometry(Point, 4326) NOT NULL,
  name TEXT NOT NULL,
  description TEXT,
  average_rating DOUBLE PRECISION NOT NULL,
  added_by TEXT NOT NULL,
  added_date TIMESTAMP WITH TIME ZONE NOT NULL,
  transport VARCHAR(255)[] NOT NULL CHECK (transport <@ ARRAY['by_foot', 'car', 'bike', 'public_transport']::VARCHAR[])
);
-- set owner
ALTER TABLE spots OWNER TO sundowner_%s;
-- spatial index on the 'location' column in 'spots'
CREATE INDEX scenic_spots_location_idx ON spots USING GIST (location);


-- Create the 'spot_reviews' table
CREATE TABLE IF NOT EXISTS spot_reviews (
  id BIGINT PRIMARY KEY,
  spot BIGINT REFERENCES spots(id) NOT NULL,
  review_user TEXT NOT NULL,
  rating INTEGER CHECK (rating BETWEEN 0 AND 5) NOT NULL,
  comment TEXT
);
-- set owner
ALTER TABLE spot_reviews OWNER TO sundowner_%s;

-- Create the 'photos' table
CREATE TABLE IF NOT EXISTS photos (
  id BIGINT PRIMARY KEY,
  spot_id BIGINT REFERENCES spots(id),
  review_id BIGINT REFERENCES spot_reviews(id),
  uploaded_date TIMESTAMP WITH TIME ZONE NOT NULL,
  url TEXT NOT NULL
);
-- set owner
ALTER TABLE photos OWNER TO sundowner_%s;

END;

