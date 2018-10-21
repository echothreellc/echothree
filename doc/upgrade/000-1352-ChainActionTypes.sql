ALTER TABLE chainactiontypedescriptions DROP FOREIGN KEY chnacttypd_lang_languageid_fk;
ALTER TABLE chainactiontypedescriptions DROP FOREIGN KEY chnacttypd_chnacttyp_chainactiontypeid_fk;
ALTER TABLE chainactiontypedescriptions DROP INDEX index1_idx;
ALTER TABLE chainactiontypedescriptions DROP INDEX languageid_idx;
ALTER TABLE chainactiontypedescriptions ADD COLUMN chnacttypd_fromtime bigint(20) NOT NULL;
ALTER TABLE chainactiontypedescriptions ADD COLUMN chnacttypd_thrutime bigint(20) NOT NULL;
UPDATE chainactiontypedescriptions SET chnacttypd_thrutime = 9223372036854775807;
ALTER TABLE chainactiontypedescriptions ADD UNIQUE KEY index1_idx (chnacttypd_chnacttyp_chainactiontypeid,chnacttypd_lang_languageid,chnacttypd_thrutime);
ALTER TABLE chainactiontypedescriptions ADD KEY index3_idx (chnacttypd_lang_languageid,chnacttypd_thrutime);
ALTER TABLE chainactiontypedescriptions ADD KEY index2_idx (chnacttypd_chnacttyp_chainactiontypeid,chnacttypd_thrutime);
ALTER TABLE chainactiontypedescriptions ADD CONSTRAINT chnacttypd_lang_languageid_fk FOREIGN KEY (chnacttypd_lang_languageid) REFERENCES languages (lang_languageid) ON DELETE CASCADE;
ALTER TABLE chainactiontypedescriptions ADD CONSTRAINT chnacttypd_chnacttyp_chainactiontypeid_fk FOREIGN KEY (chnacttypd_chnacttyp_chainactiontypeid) REFERENCES chainactiontypes (chnacttyp_chainactiontypeid) ON DELETE CASCADE;
ALTER TABLE chainactiontypedescriptions MODIFY chnacttypd_description VARCHAR(80) NOT NULL;

CREATE TABLE chainactiontypedetails (
  chnacttypdt_chainactiontypedetailid bigint(20) NOT NULL,
  chnacttypdt_chnacttyp_chainactiontypeid bigint(20) NOT NULL,
  chnacttypdt_chainactiontypename varchar(40) NOT NULL,
  chnacttypdt_allowmultiple int(1) NOT NULL,
  chnacttypdt_isdefault int(1) NOT NULL,
  chnacttypdt_sortorder int(11) NOT NULL,
  chnacttypdt_fromtime bigint(20) NOT NULL,
  chnacttypdt_thrutime bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO chainactiontypedetails SELECT chnacttyp_chainactiontypeid, chnacttyp_chainactiontypeid, chnacttyp_chainactiontypename, chnacttyp_allowmultiple, 0, 1, 0, 9223372036854775807 FROM chainactiontypes;
UPDATE chainactiontypedetails SET chnacttypdt_isdefault = 1 WHERE chnacttypdt_chainactiontypename = 'CHAIN_ACTION_SET';
ALTER TABLE chainactiontypedetails ADD PRIMARY KEY (chnacttypdt_chainactiontypedetailid);

ALTER TABLE chainactiontypes DROP INDEX chainactiontypename_idx;
ALTER TABLE chainactiontypes DROP COLUMN chnacttyp_chainactiontypename;
ALTER TABLE chainactiontypes DROP COLUMN chnacttyp_allowmultiple;
ALTER TABLE chainactiontypes DROP COLUMN chnacttyp_sortorder;
ALTER TABLE chainactiontypes ADD COLUMN chnacttyp_activedetailid bigint(20) DEFAULT NULL;
ALTER TABLE chainactiontypes ADD COLUMN chnacttyp_lastdetailid bigint(20) DEFAULT NULL;
UPDATE chainactiontypes SET chnacttyp_activedetailid = chnacttyp_chainactiontypeid, chnacttyp_lastdetailid = chnacttyp_chainactiontypeid;

ALTER TABLE chainactiontypes ADD UNIQUE KEY activedetailid_idx (chnacttyp_activedetailid);
ALTER TABLE chainactiontypes ADD UNIQUE KEY lastdetailid_idx (chnacttyp_lastdetailid);
ALTER TABLE chainactiontypes ADD CONSTRAINT chnacttyp_activedetailid_fk FOREIGN KEY (chnacttyp_activedetailid) REFERENCES chainactiontypedetails (chnacttypdt_chainactiontypedetailid) ON DELETE CASCADE;
ALTER TABLE chainactiontypes ADD CONSTRAINT chnacttyp_lastdetailid_fk FOREIGN KEY (chnacttyp_lastdetailid) REFERENCES chainactiontypedetails (chnacttypdt_chainactiontypedetailid) ON DELETE CASCADE;

ALTER TABLE chainactiontypedetails ADD UNIQUE KEY index1_idx (chnacttypdt_chnacttyp_chainactiontypeid, chnacttypdt_thrutime);
ALTER TABLE chainactiontypedetails ADD UNIQUE KEY index2_idx (chnacttypdt_chainactiontypename, chnacttypdt_thrutime);
ALTER TABLE chainactiontypedetails ADD KEY index3_idx (chnacttypdt_isdefault, chnacttypdt_thrutime);
ALTER TABLE chainactiontypedetails ADD CONSTRAINT chnacttypdt_chnacttyp_chainactiontypeid_fk FOREIGN KEY (chnacttypdt_chnacttyp_chainactiontypeid) REFERENCES chainactiontypes (chnacttyp_chainactiontypeid) ON DELETE CASCADE;

INSERT INTO entityids SELECT 'ChainActionTypeDetail', eid_lastentityid from entityids WHERE eid_entityname = 'ChainActionType';

