BEGIN;
UPDATE itemaliastypedetails SET iatdt_itemaliastypename = 'UPC_A' WHERE iatdt_itemaliastypename = 'UPC';
UPDATE itemaliastypedetails SET iatdt_itemaliastypename = 'ISBN_10' WHERE iatdt_itemaliastypename = 'ISBN10';
UPDATE itemaliastypedetails SET iatdt_itemaliastypename = 'ISBN_13' WHERE iatdt_itemaliastypename = 'ISBN13';
COMMIT;
