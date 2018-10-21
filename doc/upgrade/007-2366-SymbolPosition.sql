SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `symbolpositions` (
  `sympos_symbolpositionid` bigint(20) NOT NULL,
  `sympos_activedetailid` bigint(20) DEFAULT NULL,
  `sympos_lastdetailid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sympos_symbolpositionid`),
  UNIQUE KEY `activedetailid_idx` (`sympos_activedetailid`),
  UNIQUE KEY `lastdetailid_idx` (`sympos_lastdetailid`),
  CONSTRAINT `sympos_activedetailid_fk` FOREIGN KEY (`sympos_activedetailid`) REFERENCES `symbolpositiondetails` (`symposdt_symbolpositiondetailid`) ON DELETE CASCADE,
  CONSTRAINT `sympos_lastdetailid_fk` FOREIGN KEY (`sympos_lastdetailid`) REFERENCES `symbolpositiondetails` (`symposdt_symbolpositiondetailid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

INSERT INTO `symbolpositions` VALUES (1,1,1);
INSERT INTO `symbolpositions` VALUES (2,2,2);

CREATE TABLE `symbolpositiondetails` (
  `symposdt_symbolpositiondetailid` bigint(20) NOT NULL,
  `symposdt_sympos_symbolpositionid` bigint(20) NOT NULL,
  `symposdt_symbolpositionname` varchar(40) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `symposdt_isdefault` int(1) NOT NULL,
  `symposdt_sortorder` int(11) NOT NULL,
  `symposdt_fromtime` bigint(20) NOT NULL,
  `symposdt_thrutime` bigint(20) NOT NULL,
  PRIMARY KEY (`symposdt_symbolpositiondetailid`),
  UNIQUE KEY `index2_idx` (`symposdt_symbolpositionname`,`symposdt_thrutime`),
  UNIQUE KEY `index1_idx` (`symposdt_sympos_symbolpositionid`,`symposdt_thrutime`),
  KEY `index3_idx` (`symposdt_isdefault`,`symposdt_thrutime`),
  CONSTRAINT `symposdt_sympos_symbolpositionid_fk` FOREIGN KEY (`symposdt_sympos_symbolpositionid`) REFERENCES `symbolpositions` (`sympos_symbolpositionid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

INSERT INTO `symbolpositiondetails` VALUES (1,1,'BEFORE_VALUE',1,1,1515908119578,9223372036854775807);
INSERT INTO `symbolpositiondetails` VALUES (2,2,'AFTER_VALUE',0,2,1515908119649,9223372036854775807);

CREATE TABLE `symbolpositiondescriptions` (
  `symposd_symbolpositiondescriptionid` bigint(20) NOT NULL,
  `symposd_sympos_symbolpositionid` bigint(20) NOT NULL,
  `symposd_lang_languageid` bigint(20) NOT NULL,
  `symposd_description` varchar(80) COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `symposd_fromtime` bigint(20) NOT NULL,
  `symposd_thrutime` bigint(20) NOT NULL,
  PRIMARY KEY (`symposd_symbolpositiondescriptionid`),
  UNIQUE KEY `index1_idx` (`symposd_sympos_symbolpositionid`,`symposd_lang_languageid`,`symposd_thrutime`),
  KEY `index2_idx` (`symposd_sympos_symbolpositionid`,`symposd_thrutime`),
  KEY `index3_idx` (`symposd_lang_languageid`,`symposd_thrutime`),
  CONSTRAINT `symposd_lang_languageid_fk` FOREIGN KEY (`symposd_lang_languageid`) REFERENCES `languages` (`lang_languageid`) ON DELETE CASCADE,
  CONSTRAINT `symposd_sympos_symbolpositionid_fk` FOREIGN KEY (`symposd_sympos_symbolpositionid`) REFERENCES `symbolpositions` (`sympos_symbolpositionid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

INSERT INTO `symbolpositiondescriptions` VALUES (1,1,1,'Before Value',1515908119622,9223372036854775807);
INSERT INTO `symbolpositiondescriptions` VALUES (2,2,1,'After Value',1515908119660,9223372036854775807);

INSERT INTO entityids VALUES('ECHOTHREE', 'SymbolPosition', 100);
INSERT INTO entityids VALUES('ECHOTHREE', 'SymbolPositionDetail', 100);
INSERT INTO entityids VALUES('ECHOTHREE', 'SymbolPositionDescription', 100);

ALTER TABLE currencies ADD COLUMN cur_sympos_symbolpositionid BIGINT;
UPDATE currencies SET cur_sympos_symbolpositionid = 1;
ALTER TABLE currencies MODIFY COLUMN cur_sympos_symbolpositionid BIGINT NOT NULL;
ALTER TABLE currencies ADD KEY symbolpositionid_idx (cur_sympos_symbolpositionid);
ALTER TABLE currencies ADD CONSTRAINT cur_sympos_symbolpositionid_fk FOREIGN KEY (cur_sympos_symbolpositionid) REFERENCES symbolpositions(sympos_symbolpositionid) ON DELETE CASCADE;

ALTER TABLE currencies MODIFY cur_symbol VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

ALTER TABLE unitofmeasuretypedetails ADD COLUMN uomtdt_sympos_symbolpositionid BIGINT;
UPDATE unitofmeasuretypedetails SET uomtdt_sympos_symbolpositionid = 2;
ALTER TABLE unitofmeasuretypedetails MODIFY COLUMN uomtdt_sympos_symbolpositionid BIGINT NOT NULL;
ALTER TABLE unitofmeasuretypedetails ADD KEY index4_idx (uomtdt_sympos_symbolpositionid, uomtdt_thrutime);
ALTER TABLE unitofmeasuretypedetails ADD CONSTRAINT uomtdt_sympos_symbolpositionid_fk FOREIGN KEY (uomtdt_sympos_symbolpositionid) REFERENCES symbolpositions(sympos_symbolpositionid) ON DELETE CASCADE;

ALTER TABLE unitofmeasuretypedetails ADD COLUMN uomtdt_suppresssymbolseparator INT(1) NOT NULL;

ALTER TABLE unitofmeasuretypedescriptions ADD COLUMN uomtd_symbol VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;
UPDATE unitofmeasuretypedescriptions SET uomtd_symbol = 'PLACEHOLDER';
ALTER TABLE unitofmeasuretypedescriptions MODIFY COLUMN uomtd_symbol VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL;
