CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS postgis_topology;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- SET ROLE sundowner_@DBTAGLOWERCASE;
ALTER SCHEMA topology OWNER TO sundowner_@DBTAGLOWERCASE;

ALTER TABLE spatial_ref_sys OWNER TO sundowner_@DBTAGLOWERCASE;
ALTER TABLE layer OWNER TO sundowner_@DBTAGLOWERCASE;
ALTER TABLE topology OWNER TO sundowner_@DBTAGLOWERCASE;

GRANT USAGE ON SCHEMA topology TO sundowner_@DBTAGLOWERCASE;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA topology TO sundowner_@DBTAGLOWERCASE;
GRANT SELECT ON spatial_ref_sys TO sundowner_@DBTAGLOWERCASE;