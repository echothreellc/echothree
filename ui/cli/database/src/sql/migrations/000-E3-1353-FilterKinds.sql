ALTER TABLE filterkinddescriptions DROP FOREIGN KEY fltkd_lang_languageid_fk;
ALTER TABLE filterkinddescriptions DROP FOREIGN KEY fltkd_fltk_filterkindid_fk;
ALTER TABLE filterkinddescriptions DROP INDEX index1_idx;
ALTER TABLE filterkinddescriptions DROP INDEX languageid_idx;
ALTER TABLE filterkinddescriptions ADD COLUMN fltkd_fromtime bigint(20) NOT NULL;
ALTER TABLE filterkinddescriptions ADD COLUMN fltkd_thrutime bigint(20) NOT NULL;
UPDATE filterkinddescriptions SET fltkd_thrutime = 9223372036854775807;
ALTER TABLE filterkinddescriptions ADD UNIQUE KEY index1_idx (fltkd_fltk_filterkindid,fltkd_lang_languageid,fltkd_thrutime);
ALTER TABLE filterkinddescriptions ADD KEY index3_idx (fltkd_lang_languageid,fltkd_thrutime);
ALTER TABLE filterkinddescriptions ADD KEY index2_idx (fltkd_fltk_filterkindid,fltkd_thrutime);
ALTER TABLE filterkinddescriptions ADD CONSTRAINT fltkd_lang_languageid_fk FOREIGN KEY (fltkd_lang_languageid) REFERENCES languages (lang_languageid) ON DELETE CASCADE;
ALTER TABLE filterkinddescriptions ADD CONSTRAINT fltkd_fltk_filterkindid_fk FOREIGN KEY (fltkd_fltk_filterkindid) REFERENCES filterkinds (fltk_filterkindid) ON DELETE CASCADE;
ALTER TABLE filterkinddescriptions MODIFY fltkd_description VARCHAR(80) NOT NULL;

CREATE TABLE filterkinddetails (
  fltkdt_filterkinddetailid bigint(20) NOT NULL,
  fltkdt_fltk_filterkindid bigint(20) NOT NULL,
  fltkdt_filterkindname varchar(40) NOT NULL,
  fltkdt_isdefault int(1) NOT NULL,
  fltkdt_sortorder int(11) NOT NULL,
  fltkdt_fromtime bigint(20) NOT NULL,
  fltkdt_thrutime bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO filterkinddetails SELECT fltk_filterkindid, fltk_filterkindid, fltk_filterkindname, 0, 1, 0, 9223372036854775807 FROM filterkinds;
UPDATE filterkinddetails SET fltkdt_isdefault = 1 WHERE fltkdt_filterkindname = 'PRICE';
ALTER TABLE filterkinddetails ADD PRIMARY KEY (fltkdt_filterkinddetailid);

ALTER TABLE filterkinds DROP INDEX filterkindname_idx;
ALTER TABLE filterkinds DROP COLUMN fltk_filterkindname;
ALTER TABLE filterkinds DROP COLUMN fltk_sortorder;
ALTER TABLE filterkinds ADD COLUMN fltk_activedetailid bigint(20) DEFAULT NULL;
ALTER TABLE filterkinds ADD COLUMN fltk_lastdetailid bigint(20) DEFAULT NULL;
UPDATE filterkinds SET fltk_activedetailid = fltk_filterkindid, fltk_lastdetailid = fltk_filterkindid;

ALTER TABLE filterkinds ADD UNIQUE KEY activedetailid_idx (fltk_activedetailid);
ALTER TABLE filterkinds ADD UNIQUE KEY lastdetailid_idx (fltk_lastdetailid);
ALTER TABLE filterkinds ADD CONSTRAINT fltk_activedetailid_fk FOREIGN KEY (fltk_activedetailid) REFERENCES filterkinddetails (fltkdt_filterkinddetailid) ON DELETE CASCADE;
ALTER TABLE filterkinds ADD CONSTRAINT fltk_lastdetailid_fk FOREIGN KEY (fltk_lastdetailid) REFERENCES filterkinddetails (fltkdt_filterkinddetailid) ON DELETE CASCADE;

ALTER TABLE filterkinddetails ADD UNIQUE KEY index1_idx (fltkdt_fltk_filterkindid, fltkdt_thrutime);
ALTER TABLE filterkinddetails ADD UNIQUE KEY index2_idx (fltkdt_filterkindname, fltkdt_thrutime);
ALTER TABLE filterkinddetails ADD KEY index3_idx (fltkdt_isdefault, fltkdt_thrutime);
ALTER TABLE filterkinddetails ADD CONSTRAINT fltkdt_fltk_filterkindid_fk FOREIGN KEY (fltkdt_fltk_filterkindid) REFERENCES filterkinds (fltk_filterkindid) ON DELETE CASCADE;

INSERT INTO entityids SELECT 'FilterKindDetail', eid_lastentityid from entityids WHERE eid_entityname = 'FilterKind';

