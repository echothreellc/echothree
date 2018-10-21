ALTER TABLE mimetypedescriptions DROP FOREIGN KEY mtypd_lang_languageid_fk;
ALTER TABLE mimetypedescriptions DROP FOREIGN KEY mtypd_mtyp_mimetypeid_fk;
ALTER TABLE mimetypedescriptions DROP INDEX index1_idx;
ALTER TABLE mimetypedescriptions DROP INDEX languageid_idx;
ALTER TABLE mimetypedescriptions ADD COLUMN mtypd_fromtime bigint(20) NOT NULL;
ALTER TABLE mimetypedescriptions ADD COLUMN mtypd_thrutime bigint(20) NOT NULL;
UPDATE mimetypedescriptions SET mtypd_thrutime = 9223372036854775807;
ALTER TABLE mimetypedescriptions ADD UNIQUE KEY index1_idx (mtypd_mtyp_mimetypeid,mtypd_lang_languageid,mtypd_thrutime);
ALTER TABLE mimetypedescriptions ADD KEY index3_idx (mtypd_lang_languageid,mtypd_thrutime);
ALTER TABLE mimetypedescriptions ADD KEY index2_idx (mtypd_mtyp_mimetypeid,mtypd_thrutime);
ALTER TABLE mimetypedescriptions ADD CONSTRAINT mtypd_lang_languageid_fk FOREIGN KEY (mtypd_lang_languageid) REFERENCES languages (lang_languageid) ON DELETE CASCADE;
ALTER TABLE mimetypedescriptions ADD CONSTRAINT mtypd_mtyp_mimetypeid_fk FOREIGN KEY (mtypd_mtyp_mimetypeid) REFERENCES mimetypes (mtyp_mimetypeid) ON DELETE CASCADE;

create table mimetypedetails (
  mtypdt_mimetypedetailid bigint(20) NOT NULL,
  mtypdt_mtyp_mimetypeid bigint(20) NOT NULL,
  mtypdt_mimetypename varchar(40) NOT NULL,
  mtypdt_enat_entityattributetypeid bigint(20) NOT NULL,
  mtypdt_isdefault int(1) NOT NULL,
  mtypdt_sortorder int(11) NOT NULL,
  mtypdt_fromtime bigint(20) NOT NULL,
  mtypdt_thrutime bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO mimetypedetails SELECT mtyp_mimetypeid, mtyp_mimetypeid, mtyp_mimetypename, mtyp_enat_entityattributetypeid, 0, 1, 0, 9223372036854775807 FROM mimetypes;
UPDATE mimetypedetails SET mtypdt_isdefault = 1 WHERE mtypdt_mimetypename='text/html';
ALTER TABLE mimetypedetails ADD primary key (mtypdt_mimetypedetailid);

ALTER TABLE mimetypes DROP INDEX mimetypename_idx;
ALTER TABLE mimetypes DROP FOREIGN KEY mtyp_enat_entityattributetypeid_fk;
ALTER TABLE mimetypes DROP INDEX entityattributetypeid_idx;
ALTER TABLE mimetypes DROP COLUMN mtyp_mimetypename;
ALTER TABLE mimetypes DROP COLUMN mtyp_enat_entityattributetypeid;
ALTER TABLE mimetypes ADD COLUMN mtyp_activedetailid bigint(20) DEFAULT NULL;
ALTER TABLE mimetypes ADD COLUMN mtyp_lastdetailid bigint(20) DEFAULT NULL;
UPDATE mimetypes SET mtyp_activedetailid = mtyp_mimetypeid, mtyp_lastdetailid = mtyp_mimetypeid;

ALTER TABLE mimetypes ADD UNIQUE KEY activedetailid_idx (mtyp_activedetailid);
ALTER TABLE mimetypes ADD UNIQUE KEY lastdetailid_idx (mtyp_lastdetailid);
ALTER TABLE mimetypes ADD CONSTRAINT mtyp_activedetailid_fk FOREIGN KEY (mtyp_activedetailid) REFERENCES mimetypedetails (mtypdt_mimetypedetailid) ON DELETE CASCADE;
ALTER TABLE mimetypes ADD CONSTRAINT mtyp_lastdetailid_fk FOREIGN KEY (mtyp_lastdetailid) REFERENCES mimetypedetails (mtypdt_mimetypedetailid) ON DELETE CASCADE;

ALTER TABLE mimetypedetails ADD UNIQUE KEY index1_idx (mtypdt_mtyp_mimetypeid,mtypdt_thrutime);
ALTER TABLE mimetypedetails ADD UNIQUE KEY index2_idx (mtypdt_mimetypename,mtypdt_thrutime);
ALTER TABLE mimetypedetails ADD KEY index3_idx (mtypdt_enat_entityattributetypeid,mtypdt_thrutime);
ALTER TABLE mimetypedetails ADD KEY index4_idx (mtypdt_isdefault,mtypdt_thrutime);
ALTER TABLE mimetypedetails ADD CONSTRAINT mtypdt_mtyp_mimetypeid_fk FOREIGN KEY (mtypdt_mtyp_mimetypeid) REFERENCES mimetypes (mtyp_mimetypeid) ON DELETE CASCADE;
ALTER TABLE mimetypedetails ADD CONSTRAINT mtypdt_enat_entityattributetypeid_fk FOREIGN KEY (mtypdt_enat_entityattributetypeid) REFERENCES entityattributetypes (enat_entityattributetypeid) ON DELETE CASCADE;

INSERT INTO entityids SELECT 'MimeTypeDetail', eid_lastentityid from entityids WHERE eid_entityname = 'MimeType';

