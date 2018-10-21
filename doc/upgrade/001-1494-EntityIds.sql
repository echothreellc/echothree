ALTER TABLE entityids DROP PRIMARY KEY;
ALTER TABLE entityids CHANGE eid_entityname eid_entitytypename VARCHAR(64) NOT NULL;
ALTER TABLE entityids ADD eid_componentvendorname VARCHAR(40) NULL;
UPDATE entityids SET eid_componentvendorname='ECHOTHREE';
ALTER TABLE entityids MODIFY eid_componentvendorname VARCHAR(40) NOT NULL;
ALTER TABLE entityids ADD PRIMARY KEY (eid_componentvendorname, eid_entitytypename);
