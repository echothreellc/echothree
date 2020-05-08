ALTER TABLE chainentityroletypedescriptions DROP FOREIGN KEY chnertypd_lang_languageid_fk;
ALTER TABLE chainentityroletypedescriptions DROP FOREIGN KEY chnertypd_chnertyp_chainentityroletypeid_fk;
ALTER TABLE chainentityroletypedescriptions DROP INDEX index1_idx;
ALTER TABLE chainentityroletypedescriptions DROP INDEX languageid_idx;
ALTER TABLE chainentityroletypedescriptions ADD COLUMN chnertypd_fromtime bigint(20) NOT NULL;
ALTER TABLE chainentityroletypedescriptions ADD COLUMN chnertypd_thrutime bigint(20) NOT NULL;
UPDATE chainentityroletypedescriptions SET chnertypd_thrutime = 9223372036854775807;
ALTER TABLE chainentityroletypedescriptions ADD UNIQUE KEY index1_idx (chnertypd_chnertyp_chainentityroletypeid,chnertypd_lang_languageid,chnertypd_thrutime);
ALTER TABLE chainentityroletypedescriptions ADD KEY index3_idx (chnertypd_lang_languageid,chnertypd_thrutime);
ALTER TABLE chainentityroletypedescriptions ADD KEY index2_idx (chnertypd_chnertyp_chainentityroletypeid,chnertypd_thrutime);
ALTER TABLE chainentityroletypedescriptions ADD CONSTRAINT chnertypd_lang_languageid_fk FOREIGN KEY (chnertypd_lang_languageid) REFERENCES languages (lang_languageid) ON DELETE CASCADE;
ALTER TABLE chainentityroletypedescriptions ADD CONSTRAINT chnertypd_chnertyp_chainentityroletypeid_fk FOREIGN KEY (chnertypd_chnertyp_chainentityroletypeid) REFERENCES chainentityroletypes (chnertyp_chainentityroletypeid) ON DELETE CASCADE;

CREATE TABLE chainentityroletypedetails (
  chnertypdt_chainentityroletypedetailid bigint(20) NOT NULL,
  chnertypdt_chnertyp_chainentityroletypeid bigint(20) NOT NULL,
  chnertypdt_chntyp_chaintypeid bigint(20) NOT NULL,
  chnertypdt_chainentityroletypename varchar(40) NOT NULL,
  chnertypdt_ent_entitytypeid bigint(20) NOT NULL,
  chnertypdt_sortorder int(11) NOT NULL,
  chnertypdt_fromtime bigint(20) NOT NULL,
  chnertypdt_thrutime bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO chainentityroletypedetails SELECT chnertyp_chainentityroletypeid, chnertyp_chainentityroletypeid, chnertyp_chntyp_chaintypeid, chnertyp_chainentityroletypename, chnertyp_ent_entitytypeid, 1, 0, 9223372036854775807 FROM chainentityroletypes;
ALTER TABLE chainentityroletypedetails ADD PRIMARY KEY (chnertypdt_chainentityroletypedetailid);

ALTER TABLE chainentityroletypes DROP FOREIGN KEY chnertyp_chntyp_chaintypeid_fk;
ALTER TABLE chainentityroletypes DROP FOREIGN KEY chnertyp_ent_entitytypeid_fk;
ALTER TABLE chainentityroletypes DROP INDEX entitytypeid_idx;
ALTER TABLE chainentityroletypes DROP INDEX index1_idx;
ALTER TABLE chainentityroletypes DROP COLUMN chnertyp_chntyp_chaintypeid;
ALTER TABLE chainentityroletypes DROP COLUMN chnertyp_chainentityroletypename;
ALTER TABLE chainentityroletypes DROP COLUMN chnertyp_ent_entitytypeid;
ALTER TABLE chainentityroletypes DROP COLUMN chnertyp_sortorder;
ALTER TABLE chainentityroletypes ADD COLUMN chnertyp_activedetailid bigint(20) DEFAULT NULL;
ALTER TABLE chainentityroletypes ADD COLUMN chnertyp_lastdetailid bigint(20) DEFAULT NULL;
UPDATE chainentityroletypes SET chnertyp_activedetailid = chnertyp_chainentityroletypeid, chnertyp_lastdetailid = chnertyp_chainentityroletypeid;

ALTER TABLE chainentityroletypes ADD UNIQUE KEY activedetailid_idx (chnertyp_activedetailid);
ALTER TABLE chainentityroletypes ADD UNIQUE KEY lastdetailid_idx (chnertyp_lastdetailid);
ALTER TABLE chainentityroletypes ADD CONSTRAINT chnertyp_activedetailid_fk FOREIGN KEY (chnertyp_activedetailid) REFERENCES chainentityroletypedetails (chnertypdt_chainentityroletypedetailid) ON DELETE CASCADE;
ALTER TABLE chainentityroletypes ADD CONSTRAINT chnertyp_lastdetailid_fk FOREIGN KEY (chnertyp_lastdetailid) REFERENCES chainentityroletypedetails (chnertypdt_chainentityroletypedetailid) ON DELETE CASCADE;

ALTER TABLE chainentityroletypedetails ADD UNIQUE KEY index1_idx (chnertypdt_chnertyp_chainentityroletypeid, chnertypdt_thrutime);
ALTER TABLE chainentityroletypedetails ADD UNIQUE KEY index2_idx (chnertypdt_chntyp_chaintypeid, chnertypdt_chainentityroletypename, chnertypdt_thrutime);
ALTER TABLE chainentityroletypedetails ADD KEY index3_idx (chnertypdt_ent_entitytypeid, chnertypdt_thrutime);
ALTER TABLE chainentityroletypedetails ADD CONSTRAINT chnertypdt_chnertyp_chainentityroletypeid_fk FOREIGN KEY (chnertypdt_chnertyp_chainentityroletypeid) REFERENCES chainentityroletypes (chnertyp_chainentityroletypeid) ON DELETE CASCADE;
ALTER TABLE chainentityroletypedetails ADD CONSTRAINT chnertypdt_chntyp_chaintypeid_fk FOREIGN KEY (chnertypdt_chntyp_chaintypeid) REFERENCES chaintypes (chntyp_chaintypeid) ON DELETE CASCADE;
ALTER TABLE chainentityroletypedetails ADD CONSTRAINT chnertypdt_ent_entitytypeid_fk FOREIGN KEY (chnertypdt_ent_entitytypeid) REFERENCES entitytypes(ent_entitytypeid) ON DELETE CASCADE;

INSERT INTO entityids SELECT 'ChainEntityRoleTypeDetail', eid_lastentityid from entityids WHERE eid_entityname = 'ChainEntityRoleType';

