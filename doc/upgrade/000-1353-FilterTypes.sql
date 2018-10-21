ALTER TABLE filtertypedescriptions DROP FOREIGN KEY flttypd_lang_languageid_fk;
ALTER TABLE filtertypedescriptions DROP FOREIGN KEY flttypd_flttyp_filtertypeid_fk;
ALTER TABLE filtertypedescriptions DROP INDEX index1_idx;
ALTER TABLE filtertypedescriptions DROP INDEX languageid_idx;
ALTER TABLE filtertypedescriptions ADD COLUMN flttypd_fromtime bigint(20) NOT NULL;
ALTER TABLE filtertypedescriptions ADD COLUMN flttypd_thrutime bigint(20) NOT NULL;
UPDATE filtertypedescriptions SET flttypd_thrutime = 9223372036854775807;
ALTER TABLE filtertypedescriptions ADD UNIQUE KEY index1_idx (flttypd_flttyp_filtertypeid,flttypd_lang_languageid,flttypd_thrutime);
ALTER TABLE filtertypedescriptions ADD KEY index3_idx (flttypd_lang_languageid,flttypd_thrutime);
ALTER TABLE filtertypedescriptions ADD KEY index2_idx (flttypd_flttyp_filtertypeid,flttypd_thrutime);
ALTER TABLE filtertypedescriptions ADD CONSTRAINT flttypd_lang_languageid_fk FOREIGN KEY (flttypd_lang_languageid) REFERENCES languages (lang_languageid) ON DELETE CASCADE;
ALTER TABLE filtertypedescriptions ADD CONSTRAINT flttypd_flttyp_filtertypeid_fk FOREIGN KEY (flttypd_flttyp_filtertypeid) REFERENCES filtertypes (flttyp_filtertypeid) ON DELETE CASCADE;
ALTER TABLE filtertypedescriptions MODIFY flttypd_description VARCHAR(80) NOT NULL;

CREATE TABLE filtertypedetails (
  flttypdt_filtertypedetailid bigint(20) NOT NULL,
  flttypdt_flttyp_filtertypeid bigint(20) NOT NULL,
  flttypdt_fltk_filterkindid bigint(20) NOT NULL,
  flttypdt_filtertypename varchar(40) NOT NULL,
  flttypdt_isdefault int(1) NOT NULL,
  flttypdt_sortorder int(11) NOT NULL,
  flttypdt_fromtime bigint(20) NOT NULL,
  flttypdt_thrutime bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO filtertypedetails SELECT flttyp_filtertypeid, flttyp_filtertypeid, flttyp_fltk_filterkindid, flttyp_filtertypename, 0, 1, 0, 9223372036854775807 FROM filtertypes;
ALTER TABLE filtertypedetails ADD PRIMARY KEY (flttypdt_filtertypedetailid);

ALTER TABLE filtertypes DROP FOREIGN KEY flttyp_fltk_filterkindid_fk;
ALTER TABLE filtertypes DROP INDEX index2_idx;
ALTER TABLE filtertypes DROP COLUMN flttyp_fltk_filterkindid;
ALTER TABLE filtertypes DROP COLUMN flttyp_filtertypename;
ALTER TABLE filtertypes DROP COLUMN flttyp_sortorder;
ALTER TABLE filtertypes ADD COLUMN flttyp_activedetailid bigint(20) DEFAULT NULL;
ALTER TABLE filtertypes ADD COLUMN flttyp_lastdetailid bigint(20) DEFAULT NULL;
UPDATE filtertypes SET flttyp_activedetailid = flttyp_filtertypeid, flttyp_lastdetailid = flttyp_filtertypeid;

ALTER TABLE filtertypes ADD UNIQUE KEY activedetailid_idx (flttyp_activedetailid);
ALTER TABLE filtertypes ADD UNIQUE KEY lastdetailid_idx (flttyp_lastdetailid);
ALTER TABLE filtertypes ADD CONSTRAINT flttyp_activedetailid_fk FOREIGN KEY (flttyp_activedetailid) REFERENCES filtertypedetails (flttypdt_filtertypedetailid) ON DELETE CASCADE;
ALTER TABLE filtertypes ADD CONSTRAINT flttyp_lastdetailid_fk FOREIGN KEY (flttyp_lastdetailid) REFERENCES filtertypedetails (flttypdt_filtertypedetailid) ON DELETE CASCADE;

ALTER TABLE filtertypedetails ADD UNIQUE KEY index1_idx (flttypdt_flttyp_filtertypeid, flttypdt_thrutime);
ALTER TABLE filtertypedetails ADD UNIQUE KEY index2_idx (flttypdt_fltk_filterkindid, flttypdt_filtertypename, flttypdt_thrutime);
ALTER TABLE filtertypedetails ADD KEY index3_idx (flttypdt_fltk_filterkindid, flttypdt_isdefault, flttypdt_thrutime);
ALTER TABLE filtertypedetails ADD CONSTRAINT flttypdt_flttyp_filtertypeid_fk FOREIGN KEY (flttypdt_flttyp_filtertypeid) REFERENCES filtertypes (flttyp_filtertypeid) ON DELETE CASCADE;
ALTER TABLE filtertypedetails ADD CONSTRAINT flttypdt_fltk_filterkindid_fk FOREIGN KEY (flttypdt_fltk_filterkindid) REFERENCES filterkinds (fltk_filterkindid) ON DELETE CASCADE;

INSERT INTO entityids SELECT 'FilterTypeDetail', eid_lastentityid from entityids WHERE eid_entityname = 'FilterType';

