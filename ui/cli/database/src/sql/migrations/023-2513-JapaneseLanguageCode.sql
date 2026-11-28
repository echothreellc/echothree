BEGIN;
UPDATE languages
SET lang_languageisoname = 'ja'
WHERE lang_languageisoname = 'jp';
COMMIT;
