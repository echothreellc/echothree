ALTER TABLE entityinstances DROP INDEX uuid_idx;
ALTER TABLE entityinstances CHANGE eni_uuid eni_uuid_old VARCHAR(36);
ALTER TABLE entityinstances ADD COLUMN eni_uuid BINARY(16);
ALTER TABLE entityinstances ADD UNIQUE KEY uuid_idx(eni_uuid);
UPDATE entityinstances SET eni_uuid = UUID_TO_BIN(eni_uuid_old);
ALTER TABLE entityinstances DROP COLUMN eni_uuid_old;
