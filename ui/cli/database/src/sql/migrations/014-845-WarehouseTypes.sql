CREATE TABLE warehousetypes (
    whsetyp_warehousetypeid BIGINT NOT NULL,
    whsetyp_activedetailid BIGINT,
    whsetyp_lastdetailid BIGINT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE warehousetypedetails (
    whsetypdt_warehousetypedetailid BIGINT NOT NULL,
    whsetypdt_whsetyp_warehousetypeid BIGINT NOT NULL,
    whsetypdt_warehousetypename VARCHAR(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    whsetypdt_priority INT NOT NULL,
    whsetypdt_isdefault BIT(1) NOT NULL,
    whsetypdt_sortorder INT NOT NULL,
    whsetypdt_fromtime BIGINT NOT NULL,
    whsetypdt_thrutime BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE warehousetypedescriptions (
    whsetypd_warehousetypedescriptionid BIGINT NOT NULL,
    whsetypd_whsetyp_warehousetypeid BIGINT NOT NULL,
    whsetypd_lang_languageid BIGINT NOT NULL,
    whsetypd_description VARCHAR(132) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    whsetypd_fromtime BIGINT NOT NULL, whsetypd_thrutime BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO entityids VALUES('ECHO_THREE', 'WarehouseType', 100);
INSERT INTO entityids VALUES('ECHO_THREE', 'WarehouseTypeDetail', 100);
INSERT INTO entityids VALUES('ECHO_THREE', 'WarehouseTypeDescription', 100);

ALTER TABLE warehousetypes ADD PRIMARY KEY (whsetyp_warehousetypeid);
ALTER TABLE warehousetypes ADD UNIQUE KEY lastdetailid_idx (whsetyp_lastdetailid);
ALTER TABLE warehousetypes ADD UNIQUE KEY activedetailid_idx (whsetyp_activedetailid);

INSERT INTO warehousetypes VALUES (1, 1, 1);

ALTER TABLE warehousetypedetails ADD UNIQUE KEY index1_idx (whsetypdt_whsetyp_warehousetypeid, whsetypdt_thrutime);
ALTER TABLE warehousetypedetails ADD UNIQUE KEY index2_idx (whsetypdt_warehousetypename, whsetypdt_thrutime);
ALTER TABLE warehousetypedetails ADD KEY index3_idx (whsetypdt_isdefault, whsetypdt_thrutime);
ALTER TABLE warehousetypedetails ADD PRIMARY KEY (whsetypdt_warehousetypedetailid);

INSERT INTO warehousetypedetails VALUES (1, 1, 'DEFAULT', 1, 1, 1, 1704240005000, 9223372036854775807);

ALTER TABLE warehousetypedescriptions ADD PRIMARY KEY (whsetypd_warehousetypedescriptionid);
ALTER TABLE warehousetypedescriptions ADD UNIQUE KEY index1_idx (whsetypd_whsetyp_warehousetypeid, whsetypd_lang_languageid, whsetypd_thrutime);
ALTER TABLE warehousetypedescriptions ADD KEY index2_idx (whsetypd_whsetyp_warehousetypeid, whsetypd_thrutime);
ALTER TABLE warehousetypedescriptions ADD KEY index3_idx (whsetypd_lang_languageid, whsetypd_thrutime);
ALTER TABLE warehousetypedescriptions ADD CONSTRAINT whsetypd_lang_languageid_fk FOREIGN KEY (whsetypd_lang_languageid) REFERENCES languages(lang_languageid) ON DELETE CASCADE;

ALTER TABLE warehouses ADD COLUMN whse_whsetyp_warehousetypeid BIGINT NOT NULL;

UPDATE warehouses SET whse_whsetyp_warehousetypeid = 1;

DROP INDEX index3_idx ON warehouses;
ALTER TABLE warehouses ADD KEY index3_idx (whse_whsetyp_warehousetypeid, whse_thrutime);
ALTER TABLE warehouses ADD KEY index4_idx (whse_isdefault, whse_thrutime);
ALTER TABLE warehouses ADD CONSTRAINT whse_whsetyp_warehousetypeid_fk FOREIGN KEY (whse_whsetyp_warehousetypeid) REFERENCES warehousetypes(whsetyp_warehousetypeid) ON DELETE CASCADE;
