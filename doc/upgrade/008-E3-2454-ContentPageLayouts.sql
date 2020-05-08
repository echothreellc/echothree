ALTER TABLE contentpagelayoutdescriptions DROP FOREIGN KEY cntpld_lang_languageid_fk;
ALTER TABLE contentpagelayoutdescriptions DROP FOREIGN KEY cntpld_cntpl_contentpagelayoutid_fk;
ALTER TABLE contentpagelayoutdescriptions DROP INDEX index1_idx;
ALTER TABLE contentpagelayoutdescriptions DROP INDEX languageid_idx;
ALTER TABLE contentpagelayoutdescriptions ADD COLUMN cntpld_fromtime bigint(20) NOT NULL;
ALTER TABLE contentpagelayoutdescriptions ADD COLUMN cntpld_thrutime bigint(20) NOT NULL;
UPDATE contentpagelayoutdescriptions SET cntpld_thrutime = 9223372036854775807;
ALTER TABLE contentpagelayoutdescriptions ADD UNIQUE KEY index1_idx (cntpld_cntpl_contentpagelayoutid,cntpld_lang_languageid,cntpld_thrutime);
ALTER TABLE contentpagelayoutdescriptions ADD KEY index3_idx (cntpld_lang_languageid,cntpld_thrutime);
ALTER TABLE contentpagelayoutdescriptions ADD KEY index2_idx (cntpld_cntpl_contentpagelayoutid,cntpld_thrutime);
ALTER TABLE contentpagelayoutdescriptions ADD CONSTRAINT cntpld_lang_languageid_fk FOREIGN KEY (cntpld_lang_languageid) REFERENCES languages (lang_languageid) ON DELETE CASCADE;
ALTER TABLE contentpagelayoutdescriptions ADD CONSTRAINT cntpld_cntpl_contentpagelayoutid_fk FOREIGN KEY (cntpld_cntpl_contentpagelayoutid) REFERENCES contentpagelayouts (cntpl_contentpagelayoutid) ON DELETE CASCADE;
ALTER TABLE contentpagelayoutdescriptions MODIFY cntpld_description VARCHAR(80) NOT NULL;

CREATE TABLE contentpagelayoutdetails (
  cntpldt_contentpagelayoutdetailid bigint(20) NOT NULL,
  cntpldt_cntpl_contentpagelayoutid bigint(20) NOT NULL,
  cntpldt_contentpagelayoutname varchar(40) NOT NULL,
  cntpldt_isdefault int(1) NOT NULL,
  cntpldt_sortorder int(11) NOT NULL,
  cntpldt_fromtime bigint(20) NOT NULL,
  cntpldt_thrutime bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

INSERT INTO contentpagelayoutdetails SELECT cntpl_contentpagelayoutid, cntpl_contentpagelayoutid, cntpl_contentpagelayoutname, cntpl_isdefault, cntpl_sortorder, 0, 9223372036854775807 FROM contentpagelayouts;
ALTER TABLE contentpagelayoutdetails ADD PRIMARY KEY (cntpldt_contentpagelayoutdetailid);

ALTER TABLE contentpagelayouts DROP INDEX contentpagelayoutname_idx;
ALTER TABLE contentpagelayouts DROP INDEX isdefault_idx;
ALTER TABLE contentpagelayouts DROP COLUMN cntpl_contentpagelayoutname;
ALTER TABLE contentpagelayouts DROP COLUMN cntpl_isdefault;
ALTER TABLE contentpagelayouts DROP COLUMN cntpl_sortorder;
ALTER TABLE contentpagelayouts ADD COLUMN cntpl_activedetailid bigint(20) DEFAULT NULL;
ALTER TABLE contentpagelayouts ADD COLUMN cntpl_lastdetailid bigint(20) DEFAULT NULL;
UPDATE contentpagelayouts SET cntpl_activedetailid = cntpl_contentpagelayoutid, cntpl_lastdetailid = cntpl_contentpagelayoutid;

ALTER TABLE contentpagelayouts ADD UNIQUE KEY activedetailid_idx (cntpl_activedetailid);
ALTER TABLE contentpagelayouts ADD UNIQUE KEY lastdetailid_idx (cntpl_lastdetailid);
ALTER TABLE contentpagelayouts ADD CONSTRAINT cntpl_activedetailid_fk FOREIGN KEY (cntpl_activedetailid) REFERENCES contentpagelayoutdetails (cntpldt_contentpagelayoutdetailid) ON DELETE CASCADE;
ALTER TABLE contentpagelayouts ADD CONSTRAINT cntpl_lastdetailid_fk FOREIGN KEY (cntpl_lastdetailid) REFERENCES contentpagelayoutdetails (cntpldt_contentpagelayoutdetailid) ON DELETE CASCADE;

ALTER TABLE contentpagelayoutdetails ADD UNIQUE KEY index1_idx (cntpldt_cntpl_contentpagelayoutid, cntpldt_thrutime);
ALTER TABLE contentpagelayoutdetails ADD UNIQUE KEY index2_idx (cntpldt_contentpagelayoutname, cntpldt_thrutime);
ALTER TABLE contentpagelayoutdetails ADD KEY index3_idx (cntpldt_isdefault, cntpldt_thrutime);
ALTER TABLE contentpagelayoutdetails ADD CONSTRAINT cntpldt_cntpl_contentpagelayoutid_fk FOREIGN KEY (cntpldt_cntpl_contentpagelayoutid) REFERENCES contentpagelayouts (cntpl_contentpagelayoutid) ON DELETE CASCADE;

INSERT INTO entityids (eid_componentvendorname, eid_entitytypename, eid_lastentityid) SELECT 'ECHOTHREE', 'ContentPageLayoutDetail', eid_lastentityid FROM entityids WHERE eid_entitytypename = 'ContentPageLayout';
