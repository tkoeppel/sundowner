-- Create Tablespace
mkdir '/var/lib/postgresql/sundowner_@DBTAGLOWERCASE/'

CREATE TABLESPACE sundowner_@DBTAGLOWERCASE_tablespace OWNER sundowner_@DBTAGLOWERCASE LOCATION '/var/lib/postgresql/sundowner_@DBTAGLOWERCASE';
COMMENT ON TABLESPACE sundowner_@DBTAGLOWERCASE_tablespace IS 'Sundowner @DBTAGLOWERCASE DB Tablespace';                                                        
GRANT CREATE ON TABLESPACE sundowner_@DBTAGLOWERCASE_tablespace TO public;
