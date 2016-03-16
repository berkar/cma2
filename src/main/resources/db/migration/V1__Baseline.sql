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
-- Table Anmalning
--------------------------------------------------------------------------------------------------

CREATE TABLE "${schemaName}"."anmalning" (
		"did"    INTEGER                NOT NULL,
		"name"   CHARACTER VARYING(256) NOT NULL,
		"gender" CHARACTER VARYING(6),
		"class"  CHARACTER VARYING(6)
)
WITH (OIDS = FALSE
);

-- Add keys

ALTER TABLE "${schemaName}"."anmalning" ADD CONSTRAINT "ixpk_anmalning" PRIMARY KEY ("did");

ALTER TABLE "${schemaName}"."anmalning" ADD CONSTRAINT "r_gender" FOREIGN KEY ("gender") REFERENCES "${schemaName}"."gender" ("key");

ALTER TABLE "${schemaName}"."anmalning" ADD CONSTRAINT "r_class" FOREIGN KEY ("class") REFERENCES "${schemaName}"."class" ("key");

GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE "${schemaName}"."anmalning" TO "${roleName}";

--------------------------------------------------------------------------------------------------
-- Table Registration list
--------------------------------------------------------------------------------------------------

CREATE TABLE "${schemaName}"."start" (
		"did"            INTEGER                NOT NULL,
		"name"           CHARACTER VARYING(256) NOT NULL,
		"gender"         CHARACTER VARYING(6)   NOT NULL,
		"class"          CHARACTER VARYING(6)   NOT NULL,
		"start_number"   INTEGER                NOT NULL,
		"start_time"     TIMESTAMP,
		"did_not_start"  BOOLEAN,
		"did_not_finish" BOOLEAN
)
WITH (OIDS = FALSE
);

-- Add keys

ALTER TABLE "${schemaName}"."start" ADD CONSTRAINT "ixpk_start" PRIMARY KEY ("did");

ALTER TABLE "${schemaName}"."start" ADD CONSTRAINT "ixak_start" UNIQUE ("start_number");

ALTER TABLE "${schemaName}"."start" ADD CONSTRAINT "r_gender" FOREIGN KEY ("gender") REFERENCES "${schemaName}"."gender" ("key");

ALTER TABLE "${schemaName}"."start" ADD CONSTRAINT "r_class" FOREIGN KEY ("class") REFERENCES "${schemaName}"."class" ("key");

GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE "${schemaName}"."start" TO "${roleName}";

--------------------------------------------------------------------------------------------------
-- Table Resultat (the order in which the startnumber finishes)
--------------------------------------------------------------------------------------------------

CREATE TABLE "${schemaName}"."resultat" (
		"did"          INTEGER   NOT NULL,
		"start_number" INTEGER   NOT NULL,
		"finish_time"  TIMESTAMP NOT NULL
)
WITH (OIDS = FALSE
);

-- Add keys

ALTER TABLE "${schemaName}"."resultat" ADD CONSTRAINT "ixpk_order_list" PRIMARY KEY ("did");

ALTER TABLE "${schemaName}"."resultat" ADD CONSTRAINT "ixak_order_list" UNIQUE ("start_number");

ALTER TABLE "${schemaName}"."resultat" ADD CONSTRAINT "r_order" FOREIGN KEY ("start_number") REFERENCES "${schemaName}"."start" ("start_number");

--------------------------------------------------------------------------------------------------
-- View Result list
--------------------------------------------------------------------------------------------------

CREATE VIEW resultat_gv AS
		SELECT
				start.*,
				resultat.did AS finish_order,
				resultat.finish_time
		FROM "${schemaName}".start, "${schemaName}".resultat
		WHERE start.start_number = resultat.start_number AND resultat.finish_time IS NOT NULL
		ORDER BY resultat.finish_time DESC;
