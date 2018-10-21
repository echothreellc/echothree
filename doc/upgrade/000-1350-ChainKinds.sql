ALTER TABLE chainkinddescriptions DROP FOREIGN KEY chnkd_lang_languageid_fk;
ALTER TABLE chainkinddescriptions DROP FOREIGN KEY chnkd_chnk_chainkindid_fk;
ALTER TABLE chainkinddescriptions DROP INDEX index1_idx;
ALTER TABLE chainkinddescriptions DROP INDEX languageid_idx;
ALTER TABLE chainkinddescriptions ADD COLUMN chnkd_fromtime bigint(20) NOT NULL;
ALTER TABLE chainkinddescriptions ADD COLUMN chnkd_thrutime bigint(20) NOT NULL;
UPDATE chainkinddescriptions SET chnkd_thrutime = 9223372036854775807;
ALTER TABLE chainkinddescriptions ADD UNIQUE KEY index1_idx (chnkd_chnk_chainkindid,chnkd_lang_languageid,chnkd_thrutime);
ALTER TABLE chainkinddescriptions ADD KEY index3_idx (chnkd_lang_languageid,chnkd_thrutime);
ALTER TABLE chainkinddescriptions ADD KEY index2_idx (chnkd_chnk_chainkindid,chnkd_thrutime);
ALTER TABLE chainkinddescriptions ADD CONSTRAINT chnkd_lang_languageid_fk FOREIGN KEY (chnkd_lang_languageid) REFERENCES languages (lang_languageid) ON DELETE CASCADE;
ALTER TABLE chainkinddescriptions ADD CONSTRAINT chnkd_chnk_chainkindid_fk FOREIGN KEY (chnkd_chnk_chainkindid) REFERENCES chainkinds (chnk_chainkindid) ON DELETE CASCADE;
ALTER TABLE chainkinddescriptions MODIFY chnkd_description VARCHAR(80) NOT NULL;

CREATE TABLE chainkinddetails (
  chnkdt_chainkinddetailid bigint(20) NOT NULL,
  chnkdt_chnk_chainkindid bigint(20) NOT NULL,
  chnkdt_chainkindname varchar(40) NOT NULL,
  chnkdt_isdefault int(1) NOT NULL,
  chnkdt_sortorder int(11) NOT NULL,
  chnkdt_fromtime bigint(20) NOT NULL,
  chnkdt_thrutime bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO chainkinddetails SELECT chnk_chainkindid, chnk_chainkindid, chnk_chainkindname, 0, 1, 0, 9223372036854775807 FROM chainkinds;
UPDATE chainkinddetails SET chnkdt_isdefault = 1 WHERE chnkdt_chainkindname = 'SALES_ORDER';
ALTER TABLE chainkinddetails ADD PRIMARY KEY (chnkdt_chainkinddetailid);

ALTER TABLE chainkinds DROP INDEX chainkindname_idx;
ALTER TABLE chainkinds DROP COLUMN chnk_chainkindname;
ALTER TABLE chainkinds DROP COLUMN chnk_sortorder;
ALTER TABLE chainkinds ADD COLUMN chnk_activedetailid bigint(20) DEFAULT NULL;
ALTER TABLE chainkinds ADD COLUMN chnk_lastdetailid bigint(20) DEFAULT NULL;
UPDATE chainkinds SET chnk_activedetailid = chnk_chainkindid, chnk_lastdetailid = chnk_chainkindid;

ALTER TABLE chainkinds ADD UNIQUE KEY activedetailid_idx (chnk_activedetailid);
ALTER TABLE chainkinds ADD UNIQUE KEY lastdetailid_idx (chnk_lastdetailid);
ALTER TABLE chainkinds ADD CONSTRAINT chnk_activedetailid_fk FOREIGN KEY (chnk_activedetailid) REFERENCES chainkinddetails (chnkdt_chainkinddetailid) ON DELETE CASCADE;
ALTER TABLE chainkinds ADD CONSTRAINT chnk_lastdetailid_fk FOREIGN KEY (chnk_lastdetailid) REFERENCES chainkinddetails (chnkdt_chainkinddetailid) ON DELETE CASCADE;

ALTER TABLE chainkinddetails ADD UNIQUE KEY index1_idx (chnkdt_chnk_chainkindid, chnkdt_thrutime);
ALTER TABLE chainkinddetails ADD UNIQUE KEY index2_idx (chnkdt_chainkindname, chnkdt_thrutime);
ALTER TABLE chainkinddetails ADD KEY index3_idx (chnkdt_isdefault, chnkdt_thrutime);
ALTER TABLE chainkinddetails ADD CONSTRAINT chnkdt_chnk_chainkindid_fk FOREIGN KEY (chnkdt_chnk_chainkindid) REFERENCES chainkinds (chnk_chainkindid) ON DELETE CASCADE;

INSERT INTO entityids SELECT 'ChainKindDetail', eid_lastentityid from entityids WHERE eid_entityname = 'ChainKind';

