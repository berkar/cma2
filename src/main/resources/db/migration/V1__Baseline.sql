-- Create sequences section -------------------------------------------------

CREATE SEQUENCE "${schemaName}"."sq_cma"
INCREMENT BY 1
START WITH 100001
NO MAXVALUE
NO MINVALUE
CACHE 1;

GRANT SELECT, UPDATE ON SEQUENCE "${schemaName}"."sq_cma" TO "${roleName}";

--------------------------------------------------------------------------------------------------
-- Table Gender
--------------------------------------------------------------------------------------------------

CREATE TABLE "${schemaName}"."gender" (
		"key"  CHARACTER VARYING(6)   NOT NULL,
		"namn" CHARACTER VARYING(100) NOT NULL
)
WITH (OIDS = FALSE
);

-- Add keys

ALTER TABLE "${schemaName}"."gender" ADD CONSTRAINT "ixpk_gender" PRIMARY KEY ("key");

-- Add data

INSERT INTO "${schemaName}"."gender" (
		key, namn
) VALUES ('herrar', 'Herrar');

INSERT INTO "${schemaName}"."gender" (
		key, namn
) VALUES ('damer', 'Damer');

GRANT SELECT ON TABLE "${schemaName}"."gender" TO "${roleName}";

--------------------------------------------------------------------------------------------------
-- Table Class
--------------------------------------------------------------------------------------------------

CREATE TABLE "${schemaName}"."class" (
		"key"          CHARACTER VARYING(6)   NOT NULL,
		"namn"         CHARACTER VARYING(100) NOT NULL,
		"start_number" INTEGER,
		"start_time"   CHARACTER VARYING(5)
)
WITH (OIDS = FALSE
);

-- Add keys

ALTER TABLE "${schemaName}"."class" ADD CONSTRAINT "ixpk_class" PRIMARY KEY ("key");

-- Add data
INSERT INTO "${schemaName}"."class" (
		key, namn, start_number, start_time
) VALUES ('elit', 'Elit', 100, '10:00');

INSERT INTO "${schemaName}"."class" (
		key, namn, start_number, start_time
) VALUES ('motion', 'Motion', 200, '12:00');

GRANT SELECT ON TABLE "${schemaName}"."class" TO "${roleName}";

--------------------------------------------------------------------------------------------------
-- Table Registration list
--------------------------------------------------------------------------------------------------

CREATE TABLE "${schemaName}"."registration_list" (
		"did"            INTEGER               NOT NULL,
		"first_name"     CHARACTER VARYING(40) NOT NULL,
		"last_name"      CHARACTER VARYING(40) NOT NULL,
		"gender"         CHARACTER VARYING(6)  NOT NULL,
		"class"          CHARACTER VARYING(6)  NOT NULL,
		"start_number"   INTEGER,
		"start_time"     TIMESTAMP,
		"did_not_start"  BOOLEAN,
		"did_not_finish" BOOLEAN
)
WITH (OIDS = FALSE
);

-- Add keys

ALTER TABLE "${schemaName}"."registration_list" ADD CONSTRAINT "ixpk_registration_list" PRIMARY KEY ("did");

ALTER TABLE "${schemaName}"."registration_list" ADD CONSTRAINT "ixak_registration_list" UNIQUE ("start_number");

ALTER TABLE "${schemaName}"."registration_list" ADD CONSTRAINT "r_gender" FOREIGN KEY ("gender") REFERENCES "${schemaName}"."gender" ("key");

ALTER TABLE "${schemaName}"."registration_list" ADD CONSTRAINT "r_class" FOREIGN KEY ("class") REFERENCES "${schemaName}"."class" ("key");

GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE "${schemaName}"."registration_list" TO "${roleName}";

--------------------------------------------------------------------------------------------------
-- Table Order list (the order in which the startnumber finishes)
--------------------------------------------------------------------------------------------------

CREATE TABLE "${schemaName}"."order_list" (
		"did"          INTEGER NOT NULL,
		"start_number" INTEGER NOT NULL
)
WITH (OIDS = FALSE
);

-- Add keys

ALTER TABLE "${schemaName}"."order_list" ADD CONSTRAINT "ixpk_order_list" PRIMARY KEY ("did");

ALTER TABLE "${schemaName}"."order_list" ADD CONSTRAINT "ixak_order_list" UNIQUE ("start_number");

ALTER TABLE "${schemaName}"."order_list" ADD CONSTRAINT "r_order" FOREIGN KEY ("start_number") REFERENCES "${schemaName}"."registration_list" ("start_number");

--------------------------------------------------------------------------------------------------
-- Table Time list (the order in which the finish times are listed)
--------------------------------------------------------------------------------------------------

CREATE TABLE "${schemaName}"."time_list" (
		"did"         INTEGER   NOT NULL,
		"finish_time" TIMESTAMP NOT NULL
)
WITH (OIDS = FALSE
);

-- Add keys

ALTER TABLE "${schemaName}"."time_list" ADD CONSTRAINT "ixpk_time_list" PRIMARY KEY ("did");

ALTER TABLE "${schemaName}"."time_list" ADD CONSTRAINT "ixak_time_list" UNIQUE ("finish_time");

--------------------------------------------------------------------------------------------------
-- View Result list
--------------------------------------------------------------------------------------------------

CREATE VIEW start_list_gv AS
		SELECT *
		FROM "${schemaName}".registration_list
		WHERE start_number IS NOT NULL
		ORDER BY start_number;

--------------------------------------------------------------------------------------------------
-- View Result list
--------------------------------------------------------------------------------------------------

CREATE VIEW resultat_gv AS
		SELECT
				reg.*,
				tim.did AS finish_order,
				tim.finish_time
		FROM "${schemaName}".registration_list AS reg, "${schemaName}".order_list AS ord, "${schemaName}".time_list AS tim
		WHERE reg.start_number = ord.start_number AND ord.did = tim.did AND
					tim.finish_time IS NOT NULL
		ORDER BY finish_time DESC;
