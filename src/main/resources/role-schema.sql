-- Delete ms_role table if exist
DROP TABLE IF EXISTS ms_role;

-- Create ms_role table
CREATE TABLE ms_role(
    id VARCHAR(6) PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    creator_id INT4 NOT NULL,
    updated_date TIMESTAMP NULL,
    updater_id INT4 NULL,
    deleted_date TIMESTAMP NULL,
    deleter_id INT4 NULL,
    rec_status VARCHAR(1) NULL DEFAULT 'N'::VARCHAR
)
WITH(
    OIDS=FALSE
);

-- Query All data in ms_role table
SELECT * FROM ms_role;