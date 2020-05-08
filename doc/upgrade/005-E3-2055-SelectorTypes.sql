ALTER TABLE selectortypedescriptions DROP FOREIGN KEY sltd_lang_languageid_fk;
ALTER TABLE selectortypedescriptions DROP FOREIGN KEY sltd_slt_selectortypeid_fk;
ALTER TABLE selectortypedescriptions DROP INDEX index1_idx;
ALTER TABLE selectortypedescriptions DROP INDEX languageid_idx;
ALTER TABLE selectortypedescriptions ADD COLUMN sltd_fromtime bigint(20) NOT NULL;
ALTER TABLE selectortypedescriptions ADD COLUMN sltd_thrutime bigint(20) NOT NULL;
UPDATE selectortypedescriptions SET sltd_thrutime = 9223372036854775807;
ALTER TABLE selectortypedescriptions ADD UNIQUE KEY index1_idx (sltd_slt_selectortypeid,sltd_lang_languageid,sltd_thrutime);
ALTER TABLE selectortypedescriptions ADD KEY index3_idx (sltd_lang_languageid,sltd_thrutime);
ALTER TABLE selectortypedescriptions ADD KEY index2_idx (sltd_slt_selectortypeid,sltd_thrutime);
ALTER TABLE selectortypedescriptions ADD CONSTRAINT sltd_lang_languageid_fk FOREIGN KEY (sltd_lang_languageid) REFERENCES languages (lang_languageid) ON DELETE CASCADE;
ALTER TABLE selectortypedescriptions ADD CONSTRAINT sltd_slt_selectortypeid_fk FOREIGN KEY (sltd_slt_selectortypeid) REFERENCES selectortypes (slt_selectortypeid) ON DELETE CASCADE;
ALTER TABLE selectortypedescriptions MODIFY sltd_description VARCHAR(80) NOT NULL;

CREATE TABLE selectortypedetails (
  sltdt_selectortypedetailid bigint(20) NOT NULL,
  sltdt_slt_selectortypeid bigint(20) NOT NULL,
  sltdt_slk_selectorkindid bigint(20) NOT NULL,
  sltdt_selectortypename varchar(40) NOT NULL,
  sltdt_isdefault int(1) NOT NULL,
  sltdt_sortorder int(11) NOT NULL,
  sltdt_fromtime bigint(20) NOT NULL,
  sltdt_thrutime bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO selectortypedetails SELECT slt_selectortypeid, slt_selectortypeid, slt_slk_selectorkindid, slt_selectortypename, 0, 1, 0, 9223372036854775807 FROM selectortypes;
ALTER TABLE selectortypedetails ADD PRIMARY KEY (sltdt_selectortypedetailid);

ALTER TABLE selectortypes DROP FOREIGN KEY slt_slk_selectorkindid_fk;
ALTER TABLE selectortypes DROP INDEX index1_idx;
ALTER TABLE selectortypes DROP COLUMN slt_slk_selectorkindid;
ALTER TABLE selectortypes DROP COLUMN slt_selectortypename;
ALTER TABLE selectortypes DROP COLUMN slt_sortorder;
ALTER TABLE selectortypes ADD COLUMN slt_activedetailid bigint(20) DEFAULT NULL;
ALTER TABLE selectortypes ADD COLUMN slt_lastdetailid bigint(20) DEFAULT NULL;
UPDATE selectortypes SET slt_activedetailid = slt_selectortypeid, slt_lastdetailid = slt_selectortypeid;

ALTER TABLE selectortypes ADD UNIQUE KEY activedetailid_idx (slt_activedetailid);
ALTER TABLE selectortypes ADD UNIQUE KEY lastdetailid_idx (slt_lastdetailid);
ALTER TABLE selectortypes ADD CONSTRAINT slt_activedetailid_fk FOREIGN KEY (slt_activedetailid) REFERENCES selectortypedetails (sltdt_selectortypedetailid) ON DELETE CASCADE;
ALTER TABLE selectortypes ADD CONSTRAINT slt_lastdetailid_fk FOREIGN KEY (slt_lastdetailid) REFERENCES selectortypedetails (sltdt_selectortypedetailid) ON DELETE CASCADE;

ALTER TABLE selectortypedetails ADD UNIQUE KEY index1_idx (sltdt_slt_selectortypeid, sltdt_thrutime);
ALTER TABLE selectortypedetails ADD UNIQUE KEY index2_idx (sltdt_slk_selectorkindid, sltdt_selectortypename, sltdt_thrutime);
ALTER TABLE selectortypedetails ADD KEY index3_idx (sltdt_slk_selectorkindid, sltdt_isdefault, sltdt_thrutime);
ALTER TABLE selectortypedetails ADD CONSTRAINT sltdt_slt_selectortypeid_fk FOREIGN KEY (sltdt_slt_selectortypeid) REFERENCES selectortypes (slt_selectortypeid) ON DELETE CASCADE;
ALTER TABLE selectortypedetails ADD CONSTRAINT sltdt_slk_selectorkindid_fk FOREIGN KEY (sltdt_slk_selectorkindid) REFERENCES selectorkinds (slk_selectorkindid) ON DELETE CASCADE;

INSERT INTO entityids (eid_componentvendorname, eid_entitytypename, eid_lastentityid) SELECT 'ECHOTHREE', 'SelectorTypeDetail', eid_lastentityid from entityids WHERE eid_entitytypename = 'SelectorType';
