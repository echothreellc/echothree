BEGIN;

DELETE FROM partypaymentmethods;
DELETE FROM partypaymentmethodcreditcards;
DELETE FROM partypaymentmethodcreditcardsecuritycodes;
DELETE FROM idempotentresponses;
DELETE FROM communicationemailsources;

COMMIT;
