-- Install postgis as sundowner_@DBTAGLOWERCASE user
CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS postgis_topology;

-- Create Tables
START TRANSACTION;

-- Create the 'spots' table
CREATE TABLE IF NOT EXISTS spots (
  id BIGINT PRIMARY KEY,
  type VARCHAR(255) NOT NULL CHECK (type IN ('SUNSET', 'SUNRISE')) DEFAULT 'SUNSET',
  location geometry(Point, 4326) NOT NULL,
  name TEXT NOT NULL,
  description TEXT,
  added_by TEXT NOT NULL,
  added_date TIMESTAMP WITH TIME ZONE NOT NULL,
  transport VARCHAR(255)[] NOT NULL CHECK (transport <@ ARRAY['BY_FOOT', 'CAR', 'BIKE', 'PUBLIC_TRANSPORT']::VARCHAR(255)[]),
  status VARCHAR(255) NOT NULL CHECK (status IN ('DRAFT', 'PENDING', 'CONFIRMED', 'REJECTED', 'ARCHIVED'))
);
-- set owner
ALTER TABLE spots OWNER TO sundowner_@DBTAGLOWERCASE;
-- spatial index on the 'location' column in 'spots'
CREATE INDEX scenic_spots_location_idx ON spots USING GIST (location);

-- Create the 'spot_reviews' table
CREATE TABLE IF NOT EXISTS spot_reviews (
  id BIGINT PRIMARY KEY,
  spot_id BIGINT REFERENCES spots(id) NOT NULL,
  review_user TEXT NOT NULL,
  rating INTEGER CHECK (rating BETWEEN 0 AND 5) NOT NULL,
  comment TEXT
);
-- set owner
ALTER TABLE spot_reviews OWNER TO sundowner_@DBTAGLOWERCASE;

-- Create the 'photos' table
CREATE TABLE IF NOT EXISTS photos (
  id BIGINT PRIMARY KEY,
  spot_id BIGINT REFERENCES spots(id),
  review_id BIGINT REFERENCES spot_reviews(id),
  uploaded_by TEXT NOT NULL,
  uploaded_date TIMESTAMP WITH TIME ZONE NOT NULL,
  url TEXT NOT NULL
);
-- set owner
ALTER TABLE photos OWNER TO sundowner_@DBTAGLOWERCASE;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO sundowner_@DBTAGLOWERCASE;

END;

