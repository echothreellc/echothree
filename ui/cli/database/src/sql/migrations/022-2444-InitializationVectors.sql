BEGIN;

DELETE FROM entityencryptionkeys;
DELETE FROM partypaymentmethods;
DELETE FROM partypaymentmethodcreditcards;
DELETE FROM partypaymentmethodcreditcardsecuritycodes;
DELETE FROM idempotentresponses;
DELETE FROM communicationsources;
DELETE FROM communicationemailsources;

COMMIT;
