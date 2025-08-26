BEGIN;
UPDATE itemaliastypedetails SET iatdt_itemaliastypename = 'EAN_13' WHERE iatdt_itemaliastypename = 'EAN';
COMMIT;
