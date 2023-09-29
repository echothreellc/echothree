CREATE TABLE recoveryanswerdetails (
  ransdt_recoveryanswerdetailid bigint(20) NOT NULL,
  ransdt_rans_recoveryanswerid bigint(20) NOT NULL,
  ransdt_par_partyid bigint(20) NOT NULL,
  ransdt_rqus_recoveryquestionid bigint(20) NOT NULL,
  ransdt_answer varchar(40) NOT NULL,
  ransdt_fromtime bigint(20) NOT NULL,
  ransdt_thrutime bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO recoveryanswerdetails SELECT rans_recoveryanswerid, rans_recoveryanswerid, rans_par_partyid, rans_rqus_recoveryquestionid, rans_answer, 0, 9223372036854775807 FROM recoveryanswers;
ALTER TABLE recoveryanswerdetails ADD PRIMARY KEY (ransdt_recoveryanswerdetailid);

ALTER TABLE recoveryanswers DROP FOREIGN KEY rans_par_partyid_fk;
ALTER TABLE recoveryanswers DROP FOREIGN KEY rans_rqus_recoveryquestionid_fk;
ALTER TABLE recoveryanswers DROP INDEX index1_idx;
ALTER TABLE recoveryanswers DROP INDEX recoveryquestionid_idx;
ALTER TABLE recoveryanswers DROP COLUMN rans_par_partyid;
ALTER TABLE recoveryanswers DROP COLUMN rans_rqus_recoveryquestionid;
ALTER TABLE recoveryanswers DROP COLUMN rans_answer;
ALTER TABLE recoveryanswers DROP COLUMN rans_fromtime;
ALTER TABLE recoveryanswers DROP COLUMN rans_thrutime;
ALTER TABLE recoveryanswers ADD COLUMN rans_activedetailid bigint(20) DEFAULT NULL;
ALTER TABLE recoveryanswers ADD COLUMN rans_lastdetailid bigint(20) DEFAULT NULL;
UPDATE recoveryanswers SET rans_activedetailid = rans_recoveryanswerid, rans_lastdetailid = rans_recoveryanswerid;

ALTER TABLE recoveryanswers ADD UNIQUE KEY activedetailid_idx (rans_activedetailid);
ALTER TABLE recoveryanswers ADD UNIQUE KEY lastdetailid_idx (rans_lastdetailid);
ALTER TABLE recoveryanswers ADD CONSTRAINT rans_activedetailid_fk FOREIGN KEY (rans_activedetailid) REFERENCES recoveryanswerdetails (ransdt_recoveryanswerdetailid) ON DELETE CASCADE;
ALTER TABLE recoveryanswers ADD CONSTRAINT rans_lastdetailid_fk FOREIGN KEY (rans_lastdetailid) REFERENCES recoveryanswerdetails (ransdt_recoveryanswerdetailid) ON DELETE CASCADE;

ALTER TABLE recoveryanswerdetails ADD UNIQUE KEY index1_idx (ransdt_rans_recoveryanswerid, ransdt_thrutime);
ALTER TABLE recoveryanswerdetails ADD UNIQUE KEY index2_idx (ransdt_par_partyid, ransdt_thrutime);
ALTER TABLE recoveryanswerdetails ADD KEY index3_idx (ransdt_rqus_recoveryquestionid, ransdt_thrutime);
ALTER TABLE recoveryanswerdetails ADD CONSTRAINT ransdt_rans_recoveryanswerid_fk FOREIGN KEY (ransdt_rans_recoveryanswerid) REFERENCES recoveryanswers (rans_recoveryanswerid) ON DELETE CASCADE;
ALTER TABLE recoveryanswerdetails ADD CONSTRAINT ransdt_par_partyid_fk FOREIGN KEY (ransdt_par_partyid) REFERENCES parties (par_partyid) ON DELETE CASCADE;
ALTER TABLE recoveryanswerdetails ADD CONSTRAINT ransdt_rqus_recoveryquestionid_fk FOREIGN KEY (ransdt_rqus_recoveryquestionid) REFERENCES recoveryquestions (rqus_recoveryquestionid) ON DELETE CASCADE;

INSERT INTO entityids SELECT 'RecoveryAnswerDetail', eid_lastentityid from entityids WHERE eid_entityname = 'RecoveryAnswer';

