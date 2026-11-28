BEGIN;
UPDATE securityrolegroupdetails
SET srgdt_securityrolegroupname = CASE srgdt_securityrolegroupname
    WHEN 'UnitOfMeasureVolume' THEN 'UnitOfMeasureTypeVolume'
    WHEN 'UnitOfMeasureWeight' THEN 'UnitOfMeasureTypeWeight'
END
WHERE srgdt_securityrolegroupname IN ('UnitOfMeasureVolume', 'UnitOfMeasureWeight')
  AND srgdt_securityrolegroupdetailid IN (SELECT srg_activedetailid FROM securityrolegroups);
COMMIT;
