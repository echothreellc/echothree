ALTER TABLE selectorkinddescriptions DROP FOREIGN KEY slkd_lang_languageid_fk;
ALTER TABLE selectorkinddescriptions DROP FOREIGN KEY slkd_slk_selectorkindid_fk;
ALTER TABLE selectorkinddescriptions DROP INDEX index1_idx;
ALTER TABLE selectorkinddescriptions DROP INDEX languageid_idx;
ALTER TABLE selectorkinddescriptions ADD COLUMN slkd_fromtime bigint(20) NOT NULL;
ALTER TABLE selectorkinddescriptions ADD COLUMN slkd_thrutime bigint(20) NOT NULL;
UPDATE selectorkinddescriptions SET slkd_thrutime = 9223372036854775807;
ALTER TABLE selectorkinddescriptions ADD UNIQUE KEY index1_idx (slkd_slk_selectorkindid,slkd_lang_languageid,slkd_thrutime);
ALTER TABLE selectorkinddescriptions ADD KEY index3_idx (slkd_lang_languageid,slkd_thrutime);
ALTER TABLE selectorkinddescriptions ADD KEY index2_idx (slkd_slk_selectorkindid,slkd_thrutime);
ALTER TABLE selectorkinddescriptions ADD CONSTRAINT slkd_lang_languageid_fk FOREIGN KEY (slkd_lang_languageid) REFERENCES languages (lang_languageid) ON DELETE CASCADE;
ALTER TABLE selectorkinddescriptions ADD CONSTRAINT slkd_slk_selectorkindid_fk FOREIGN KEY (slkd_slk_selectorkindid) REFERENCES selectorkinds (slk_selectorkindid) ON DELETE CASCADE;
ALTER TABLE selectorkinddescriptions MODIFY slkd_description VARCHAR(80) NOT NULL;

CREATE TABLE selectorkinddetails (
  slkdt_selectorkinddetailid bigint(20) NOT NULL,
  slkdt_slk_selectorkindid bigint(20) NOT NULL,
  slkdt_selectorkindname varchar(40) NOT NULL,
  slkdt_isdefault int(1) NOT NULL,
  slkdt_sortorder int(11) NOT NULL,
  slkdt_fromtime bigint(20) NOT NULL,
  slkdt_thrutime bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO selectorkinddetails SELECT slk_selectorkindid, slk_selectorkindid, slk_selectorkindname, 0, 1, 0, 9223372036854775807 FROM selectorkinds;
UPDATE selectorkinddetails SET slkdt_isdefault = 1 WHERE slkdt_selectorkindname = 'PRICE';
ALTER TABLE selectorkinddetails ADD PRIMARY KEY (slkdt_selectorkinddetailid);

ALTER TABLE selectorkinds DROP INDEX selectorkindname_idx;
ALTER TABLE selectorkinds DROP COLUMN slk_selectorkindname;
ALTER TABLE selectorkinds DROP COLUMN slk_sortorder;
ALTER TABLE selectorkinds ADD COLUMN slk_activedetailid bigint(20) DEFAULT NULL;
ALTER TABLE selectorkinds ADD COLUMN slk_lastdetailid bigint(20) DEFAULT NULL;
UPDATE selectorkinds SET slk_activedetailid = slk_selectorkindid, slk_lastdetailid = slk_selectorkindid;

ALTER TABLE selectorkinds ADD UNIQUE KEY activedetailid_idx (slk_activedetailid);
ALTER TABLE selectorkinds ADD UNIQUE KEY lastdetailid_idx (slk_lastdetailid);
ALTER TABLE selectorkinds ADD CONSTRAINT slk_activedetailid_fk FOREIGN KEY (slk_activedetailid) REFERENCES selectorkinddetails (slkdt_selectorkinddetailid) ON DELETE CASCADE;
ALTER TABLE selectorkinds ADD CONSTRAINT slk_lastdetailid_fk FOREIGN KEY (slk_lastdetailid) REFERENCES selectorkinddetails (slkdt_selectorkinddetailid) ON DELETE CASCADE;

ALTER TABLE selectorkinddetails ADD UNIQUE KEY index1_idx (slkdt_slk_selectorkindid, slkdt_thrutime);
ALTER TABLE selectorkinddetails ADD UNIQUE KEY index2_idx (slkdt_selectorkindname, slkdt_thrutime);
ALTER TABLE selectorkinddetails ADD KEY index3_idx (slkdt_isdefault, slkdt_thrutime);
ALTER TABLE selectorkinddetails ADD CONSTRAINT slkdt_slk_selectorkindid_fk FOREIGN KEY (slkdt_slk_selectorkindid) REFERENCES selectorkinds (slk_selectorkindid) ON DELETE CASCADE;

INSERT INTO entityids (eid_componentvendorname, eid_entitytypename, eid_lastentityid) SELECT 'ECHOTHREE', 'SelectorKindDetail', eid_lastentityid FROM entityids WHERE eid_entitytypename = 'SelectorKind';
