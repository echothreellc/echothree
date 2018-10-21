ALTER TABLE subscriptionkinddescriptions DROP FOREIGN KEY subscrkd_lang_languageid_fk;
ALTER TABLE subscriptionkinddescriptions DROP FOREIGN KEY subscrkd_subscrk_subscriptionkindid_fk;
ALTER TABLE subscriptionkinddescriptions DROP INDEX index1_idx;
ALTER TABLE subscriptionkinddescriptions DROP INDEX languageid_idx;
ALTER TABLE subscriptionkinddescriptions ADD COLUMN subscrkd_fromtime bigint(20) NOT NULL;
ALTER TABLE subscriptionkinddescriptions ADD COLUMN subscrkd_thrutime bigint(20) NOT NULL;
UPDATE subscriptionkinddescriptions SET subscrkd_thrutime = 9223372036854775807;
ALTER TABLE subscriptionkinddescriptions ADD UNIQUE KEY index1_idx (subscrkd_subscrk_subscriptionkindid,subscrkd_lang_languageid,subscrkd_thrutime);
ALTER TABLE subscriptionkinddescriptions ADD KEY index3_idx (subscrkd_lang_languageid,subscrkd_thrutime);
ALTER TABLE subscriptionkinddescriptions ADD KEY index2_idx (subscrkd_subscrk_subscriptionkindid,subscrkd_thrutime);
ALTER TABLE subscriptionkinddescriptions ADD CONSTRAINT subscrkd_lang_languageid_fk FOREIGN KEY (subscrkd_lang_languageid) REFERENCES languages (lang_languageid) ON DELETE CASCADE;
ALTER TABLE subscriptionkinddescriptions ADD CONSTRAINT subscrkd_subscrk_subscriptionkindid_fk FOREIGN KEY (subscrkd_subscrk_subscriptionkindid) REFERENCES subscriptionkinds (subscrk_subscriptionkindid) ON DELETE CASCADE;
ALTER TABLE subscriptionkinddescriptions MODIFY subscrkd_description VARCHAR(80) NOT NULL;

CREATE TABLE subscriptionkinddetails (
  subscrkdt_subscriptionkinddetailid bigint(20) NOT NULL,
  subscrkdt_subscrk_subscriptionkindid bigint(20) NOT NULL,
  subscrkdt_subscriptionkindname varchar(40) NOT NULL,
  subscrkdt_isdefault int(1) NOT NULL,
  subscrkdt_sortorder int(11) NOT NULL,
  subscrkdt_fromtime bigint(20) NOT NULL,
  subscrkdt_thrutime bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO subscriptionkinddetails SELECT subscrk_subscriptionkindid, subscrk_subscriptionkindid, subscrk_subscriptionkindname, 0, 1, 0, 9223372036854775807 FROM subscriptionkinds;
UPDATE subscriptionkinddetails SET subscrkdt_isdefault = 1 WHERE subscrkdt_subscriptionkindname = 'CLUB';
ALTER TABLE subscriptionkinddetails ADD PRIMARY KEY (subscrkdt_subscriptionkinddetailid);

ALTER TABLE subscriptionkinds DROP INDEX subscriptionkindname_idx;
ALTER TABLE subscriptionkinds DROP COLUMN subscrk_subscriptionkindname;
ALTER TABLE subscriptionkinds DROP COLUMN subscrk_sortorder;
ALTER TABLE subscriptionkinds ADD COLUMN subscrk_activedetailid bigint(20) DEFAULT NULL;
ALTER TABLE subscriptionkinds ADD COLUMN subscrk_lastdetailid bigint(20) DEFAULT NULL;
UPDATE subscriptionkinds SET subscrk_activedetailid = subscrk_subscriptionkindid, subscrk_lastdetailid = subscrk_subscriptionkindid;

ALTER TABLE subscriptionkinds ADD UNIQUE KEY activedetailid_idx (subscrk_activedetailid);
ALTER TABLE subscriptionkinds ADD UNIQUE KEY lastdetailid_idx (subscrk_lastdetailid);
ALTER TABLE subscriptionkinds ADD CONSTRAINT subscrk_activedetailid_fk FOREIGN KEY (subscrk_activedetailid) REFERENCES subscriptionkinddetails (subscrkdt_subscriptionkinddetailid) ON DELETE CASCADE;
ALTER TABLE subscriptionkinds ADD CONSTRAINT subscrk_lastdetailid_fk FOREIGN KEY (subscrk_lastdetailid) REFERENCES subscriptionkinddetails (subscrkdt_subscriptionkinddetailid) ON DELETE CASCADE;

ALTER TABLE subscriptionkinddetails ADD UNIQUE KEY index1_idx (subscrkdt_subscrk_subscriptionkindid, subscrkdt_thrutime);
ALTER TABLE subscriptionkinddetails ADD UNIQUE KEY index2_idx (subscrkdt_subscriptionkindname, subscrkdt_thrutime);
ALTER TABLE subscriptionkinddetails ADD KEY index3_idx (subscrkdt_isdefault, subscrkdt_thrutime);
ALTER TABLE subscriptionkinddetails ADD CONSTRAINT subscrkdt_subscrk_subscriptionkindid_fk FOREIGN KEY (subscrkdt_subscrk_subscriptionkindid) REFERENCES subscriptionkinds (subscrk_subscriptionkindid) ON DELETE CASCADE;

INSERT INTO entityids SELECT 'SubscriptionKindDetail', eid_lastentityid from entityids WHERE eid_entityname = 'SubscriptionKind';

