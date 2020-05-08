ALTER TABLE chaintypedescriptions DROP FOREIGN KEY chntypd_lang_languageid_fk;
ALTER TABLE chaintypedescriptions DROP FOREIGN KEY chntypd_chntyp_chaintypeid_fk;
ALTER TABLE chaintypedescriptions DROP INDEX index1_idx;
ALTER TABLE chaintypedescriptions DROP INDEX languageid_idx;
ALTER TABLE chaintypedescriptions ADD COLUMN chntypd_fromtime bigint(20) NOT NULL;
ALTER TABLE chaintypedescriptions ADD COLUMN chntypd_thrutime bigint(20) NOT NULL;
UPDATE chaintypedescriptions SET chntypd_thrutime = 9223372036854775807;
ALTER TABLE chaintypedescriptions ADD UNIQUE KEY index1_idx (chntypd_chntyp_chaintypeid,chntypd_lang_languageid,chntypd_thrutime);
ALTER TABLE chaintypedescriptions ADD KEY index3_idx (chntypd_lang_languageid,chntypd_thrutime);
ALTER TABLE chaintypedescriptions ADD KEY index2_idx (chntypd_chntyp_chaintypeid,chntypd_thrutime);
ALTER TABLE chaintypedescriptions ADD CONSTRAINT chntypd_lang_languageid_fk FOREIGN KEY (chntypd_lang_languageid) REFERENCES languages (lang_languageid) ON DELETE CASCADE;
ALTER TABLE chaintypedescriptions ADD CONSTRAINT chntypd_chntyp_chaintypeid_fk FOREIGN KEY (chntypd_chntyp_chaintypeid) REFERENCES chaintypes (chntyp_chaintypeid) ON DELETE CASCADE;
ALTER TABLE chaintypedescriptions MODIFY chntypd_description VARCHAR(80) NOT NULL;

CREATE TABLE chaintypedetails (
  chntypdt_chaintypedetailid bigint(20) NOT NULL,
  chntypdt_chntyp_chaintypeid bigint(20) NOT NULL,
  chntypdt_chnk_chainkindid bigint(20) NOT NULL,
  chntypdt_chaintypename varchar(40) NOT NULL,
  chntypdt_isdefault int(1) NOT NULL,
  chntypdt_sortorder int(11) NOT NULL,
  chntypdt_fromtime bigint(20) NOT NULL,
  chntypdt_thrutime bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO chaintypedetails SELECT chntyp_chaintypeid, chntyp_chaintypeid, chntyp_chnk_chainkindid, chntyp_chaintypename, 0, 1, 0, 9223372036854775807 FROM chaintypes;
ALTER TABLE chaintypedetails ADD PRIMARY KEY (chntypdt_chaintypedetailid);

ALTER TABLE chaintypes DROP FOREIGN KEY chntyp_chnk_chainkindid_fk;
ALTER TABLE chaintypes DROP INDEX index1_idx;
ALTER TABLE chaintypes DROP COLUMN chntyp_chnk_chainkindid;
ALTER TABLE chaintypes DROP COLUMN chntyp_chaintypename;
ALTER TABLE chaintypes DROP COLUMN chntyp_sortorder;
ALTER TABLE chaintypes ADD COLUMN chntyp_activedetailid bigint(20) DEFAULT NULL;
ALTER TABLE chaintypes ADD COLUMN chntyp_lastdetailid bigint(20) DEFAULT NULL;
UPDATE chaintypes SET chntyp_activedetailid = chntyp_chaintypeid, chntyp_lastdetailid = chntyp_chaintypeid;

ALTER TABLE chaintypes ADD UNIQUE KEY activedetailid_idx (chntyp_activedetailid);
ALTER TABLE chaintypes ADD UNIQUE KEY lastdetailid_idx (chntyp_lastdetailid);
ALTER TABLE chaintypes ADD CONSTRAINT chntyp_activedetailid_fk FOREIGN KEY (chntyp_activedetailid) REFERENCES chaintypedetails (chntypdt_chaintypedetailid) ON DELETE CASCADE;
ALTER TABLE chaintypes ADD CONSTRAINT chntyp_lastdetailid_fk FOREIGN KEY (chntyp_lastdetailid) REFERENCES chaintypedetails (chntypdt_chaintypedetailid) ON DELETE CASCADE;

ALTER TABLE chaintypedetails ADD UNIQUE KEY index1_idx (chntypdt_chntyp_chaintypeid, chntypdt_thrutime);
ALTER TABLE chaintypedetails ADD UNIQUE KEY index2_idx (chntypdt_chnk_chainkindid, chntypdt_chaintypename, chntypdt_thrutime);
ALTER TABLE chaintypedetails ADD KEY index3_idx (chntypdt_chnk_chainkindid, chntypdt_isdefault, chntypdt_thrutime);
ALTER TABLE chaintypedetails ADD CONSTRAINT chntypdt_chntyp_chaintypeid_fk FOREIGN KEY (chntypdt_chntyp_chaintypeid) REFERENCES chaintypes (chntyp_chaintypeid) ON DELETE CASCADE;
ALTER TABLE chaintypedetails ADD CONSTRAINT chntypdt_chnk_chainkindid_fk FOREIGN KEY (chntypdt_chnk_chainkindid) REFERENCES chainkinds (chnk_chainkindid) ON DELETE CASCADE;

INSERT INTO entityids SELECT 'ChainTypeDetail', eid_lastentityid from entityids WHERE eid_entityname = 'ChainType';

