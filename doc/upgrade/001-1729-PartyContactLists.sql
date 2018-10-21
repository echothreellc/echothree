CREATE TABLE partycontactlistdetails (
  parclstdt_partycontactlistdetailid bigint(20) NOT NULL,
  parclstdt_parclst_partycontactlistid bigint(20) NOT NULL,
  parclstdt_par_partyid bigint(20) NOT NULL,
  parclstdt_clst_contactlistid bigint(20) NOT NULL,
  parclstdt_preferredcontactlistcontactmechanismpurposeid bigint(20) DEFAULT NULL,
  parclstdt_fromtime bigint(20) NOT NULL,
  parclstdt_thrutime bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO partycontactlistdetails SELECT parclst_partycontactlistid, parclst_partycontactlistid, parclst_par_partyid, parclst_clst_contactlistid, NULL, parclst_fromtime, parclst_thrutime FROM partycontactlists;
ALTER TABLE partycontactlistdetails ADD PRIMARY KEY (parclstdt_partycontactlistdetailid);

ALTER TABLE partycontactlists ADD COLUMN parclst_activedetailid bigint(20) DEFAULT NULL;
ALTER TABLE partycontactlists ADD COLUMN parclst_lastdetailid bigint(20) DEFAULT NULL;
UPDATE partycontactlists SET parclst_activedetailid = IF(parclst_thrutime = 9223372036854775807, parclst_partycontactlistid, null), parclst_lastdetailid = parclst_partycontactlistid;

ALTER TABLE partycontactlists ADD UNIQUE KEY activedetailid_idx (parclst_activedetailid);
ALTER TABLE partycontactlists ADD UNIQUE KEY lastdetailid_idx (parclst_lastdetailid);
ALTER TABLE partycontactlists ADD CONSTRAINT parclst_activedetailid_fk FOREIGN KEY (parclst_activedetailid) REFERENCES partycontactlistdetails(parclstdt_partycontactlistdetailid) ON DELETE CASCADE;
ALTER TABLE partycontactlists ADD CONSTRAINT parclst_lastdetailid_fk FOREIGN KEY (parclst_lastdetailid) REFERENCES partycontactlistdetails(parclstdt_partycontactlistdetailid) ON DELETE CASCADE;

ALTER TABLE partycontactlists DROP FOREIGN KEY parclst_par_partyid_fk;
ALTER TABLE partycontactlists DROP FOREIGN KEY parclst_clst_contactlistid_fk;
ALTER TABLE partycontactlists DROP INDEX index1_idx;
ALTER TABLE partycontactlists DROP INDEX index2_idx;
ALTER TABLE partycontactlists DROP INDEX index3_idx;
ALTER TABLE partycontactlists DROP COLUMN parclst_par_partyid;
ALTER TABLE partycontactlists DROP COLUMN parclst_clst_contactlistid;
ALTER TABLE partycontactlists DROP COLUMN parclst_fromtime;
ALTER TABLE partycontactlists DROP COLUMN parclst_thrutime;

ALTER TABLE partycontactlistdetails ADD  UNIQUE KEY index1_idx (parclstdt_parclst_partycontactlistid, parclstdt_thrutime);
ALTER TABLE partycontactlistdetails ADD UNIQUE KEY index2_idx (parclstdt_par_partyid, parclstdt_clst_contactlistid, parclstdt_thrutime);
ALTER TABLE partycontactlistdetails ADD KEY index3_idx (parclstdt_par_partyid, parclstdt_thrutime);
ALTER TABLE partycontactlistdetails ADD KEY index4_idx (parclstdt_clst_contactlistid, parclstdt_thrutime);
ALTER TABLE partycontactlistdetails ADD KEY index5_idx (parclstdt_preferredcontactlistcontactmechanismpurposeid, parclstdt_thrutime);
ALTER TABLE partycontactlistdetails ADD CONSTRAINT parclstdt_parclst_partycontactlistid_fk FOREIGN KEY (parclstdt_parclst_partycontactlistid) REFERENCES partycontactlists (parclst_partycontactlistid) ON DELETE CASCADE;
ALTER TABLE partycontactlistdetails ADD CONSTRAINT parclstdt_par_partyid_fk FOREIGN KEY (parclstdt_par_partyid) REFERENCES parties(par_partyid) ON DELETE CASCADE;
ALTER TABLE partycontactlistdetails ADD CONSTRAINT parclstdt_clst_contactlistid_fk FOREIGN KEY (parclstdt_clst_contactlistid) REFERENCES contactlists(clst_contactlistid) ON DELETE CASCADE;
ALTER TABLE partycontactlistdetails ADD CONSTRAINT parclstdt_preferredcontactlistcontactmechanismpurposeid_fk FOREIGN KEY (parclstdt_preferredcontactlistcontactmechanismpurposeid) REFERENCES contactlistcontactmechanismpurposes (clstcmpr_contactlistcontactmechanismpurposeid) ON DELETE CASCADE;

INSERT INTO entityids SELECT 'PartyContactListDetail', eid_lastentityid, eid_componentvendorname from entityids WHERE eid_entitytypename = 'PartyContactList';

