-- Create sequences section -------------------------------------------------

CREATE SEQUENCE "${schemaName}"."sq_cma"
INCREMENT BY 1
START WITH 100001
NO MAXVALUE
NO MINVALUE
CACHE 1;

GRANT SELECT, UPDATE ON SEQUENCE "${schemaName}"."sq_cma" TO "${roleName}";

--------------------------------------------------------------------------------------------------
-- Table Egenskaper
--------------------------------------------------------------------------------------------------

CREATE TABLE "${schemaName}"."egenskaper" (
		"did"                        INTEGER NOT NULL,
		"starttid"                   CHARACTER VARYING(8),
		"antal_per_grupp"            INTEGER,
		"forsta_startnummer"         INTEGER,
		"tid_mellan_grupper_minuter" INTEGER,
		"antal_varv"                 INTEGER
)
WITH (OIDS = FALSE
);

-- Add keys

ALTER TABLE "${schemaName}"."egenskaper"
		ADD CONSTRAINT "ixpk_egenskaper" PRIMARY KEY ("did");

-- Add data

INSERT INTO "${schemaName}"."egenskaper" (
		did, starttid, antal_per_grupp, forsta_startnummer, tid_mellan_grupper_minuter, antal_varv
) VALUES (1, '13:00:00', 20, 100, 10, 3);

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

ALTER TABLE "${schemaName}"."gender"
		ADD CONSTRAINT "ixpk_gender" PRIMARY KEY ("key");

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
		"start_time"   INTEGER
)
WITH (OIDS = FALSE
);

-- Add keys

ALTER TABLE "${schemaName}"."class"
		ADD CONSTRAINT "ixpk_class" PRIMARY KEY ("key");

-- Add data
INSERT INTO "${schemaName}"."class" (
		key, namn, start_number, start_time
) VALUES ('elit', 'Elit', 100, 0);

INSERT INTO "${schemaName}"."class" (
		key, namn, start_number, start_time
) VALUES ('motion', 'Motion', 200, 300);

GRANT SELECT ON TABLE "${schemaName}"."class" TO "${roleName}";

--------------------------------------------------------------------------------------------------
-- Table Anmalning
--------------------------------------------------------------------------------------------------

CREATE TABLE "${schemaName}"."foranmald" (
		"did"  INTEGER                NOT NULL,
		"name" CHARACTER VARYING(256) NOT NULL
)
WITH (OIDS = FALSE
);

-- Add keys

ALTER TABLE "${schemaName}"."foranmald"
		ADD CONSTRAINT "ixpk_foranmald" PRIMARY KEY ("did");

GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE "${schemaName}"."foranmald" TO "${roleName}";

--------------------------------------------------------------------------------------------------
-- Table Registration list
--------------------------------------------------------------------------------------------------

CREATE TABLE "${schemaName}"."start" (
		"did"          INTEGER                NOT NULL,
		"name"         CHARACTER VARYING(256) NOT NULL,
		"gender"       CHARACTER VARYING(6)   NOT NULL,
		"class"        CHARACTER VARYING(6)   NOT NULL,
		"start_number" INTEGER                NOT NULL,
		"start_time"   INTEGER                NOT NULL
)
WITH (OIDS = FALSE
);

-- Add keys

ALTER TABLE "${schemaName}"."start"
		ADD CONSTRAINT "ixpk_start" PRIMARY KEY ("did");

ALTER TABLE "${schemaName}"."start"
		ADD CONSTRAINT "ixak_start" UNIQUE ("start_number");

ALTER TABLE "${schemaName}"."start"
		ADD CONSTRAINT "r_gender" FOREIGN KEY ("gender") REFERENCES "${schemaName}"."gender" ("key");

ALTER TABLE "${schemaName}"."start"
		ADD CONSTRAINT "r_class" FOREIGN KEY ("class") REFERENCES "${schemaName}"."class" ("key");

GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE "${schemaName}"."start" TO "${roleName}";

--------------------------------------------------------------------------------------------------
-- Table Resultat
--------------------------------------------------------------------------------------------------

CREATE TABLE "${schemaName}"."resultat" (
		"did"              INTEGER NOT NULL,
		"start_number"     INTEGER NOT NULL,
		"placering_total"  INTEGER,
		"placering_class"  INTEGER,
		"placering_gender" INTEGER,
		"finish_order"     INTEGER NOT NULL,
		"finish_time"      INTEGER  NOT NULL,
		"total_time"       INTEGER,
		"medalj"           CHARACTER VARYING(6),
		"did_not_start"    BOOLEAN,
		"did_not_finish"   BOOLEAN
)
WITH (OIDS = FALSE
);

-- Add keys

ALTER TABLE "${schemaName}"."resultat"
		ADD CONSTRAINT "ixpk_order_list" PRIMARY KEY ("did");

ALTER TABLE "${schemaName}"."resultat"
		ADD CONSTRAINT "ixak_order_list" UNIQUE ("start_number");

ALTER TABLE "${schemaName}"."resultat"
		ADD CONSTRAINT "r_order" FOREIGN KEY ("start_number") REFERENCES "${schemaName}"."start" ("start_number");

--------------------------------------------------------------------------------------------------
-- View Result list
--------------------------------------------------------------------------------------------------

CREATE VIEW resultatlista_gv AS
		SELECT
				start.name,
				start.class,
				start.gender,
				resultat.*
		FROM "${schemaName}".start
				FULL JOIN "${schemaName}".resultat
						ON "${schemaName}".start.start_number = "${schemaName}".resultat.start_number
		WHERE resultat.did_not_start OR resultat.did_not_finish OR resultat.finish_time IS NOT NULL
		ORDER BY resultat.placering_total DESC;
