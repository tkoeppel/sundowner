CREATE TABLESPACE sundowner_dev_data OWNER sundowner_dev LOCATION '/home/tim/Public/sundowner_dev_data';
COMMENT ON TABLESPACE sundowner_dev_data IS 'Sundowner DEV DB Tablespace';
GRANT CREATE ON TABLESPACE sundowner_dev_data TO public;