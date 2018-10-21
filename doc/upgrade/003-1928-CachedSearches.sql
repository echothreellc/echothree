ALTER TABLE cachedsearches DROP FOREIGN KEY csrch_srchk_searchkindid_fk;
ALTER TABLE cachedsearches DROP INDEX index1_idx;
ALTER TABLE cachedsearches DROP COLUMN csrch_srchk_searchkindid;

ALTER TABLE cachedsearches ADD COLUMN csrch_idx_indexid BIGINT;

UPDATE cachedsearches SET csrch_idx_indexid = (SELECT idx_indexid FROM indexes, indexdetails WHERE idx_activedetailid = idxdt_indexdetailid AND idxdt_indexname = 'ITEM_EN');

ALTER TABLE cachedsearches MODIFY COLUMN csrch_idx_indexid BIGINT NOT NULL;

ALTER TABLE cachedsearches ADD UNIQUE KEY index1_idx (csrch_idx_indexid, csrch_querysha1hash, csrch_parsedquerysha1hash, csrch_srchdefop_searchdefaultoperatorid, csrch_srchsrtord_searchsortorderid, csrch_srchsrtdir_searchsortdirectionid, csrch_thrutime);

ALTER TABLE cachedsearches ADD CONSTRAINT csrch_idx_indexid_fk FOREIGN KEY (csrch_idx_indexid) REFERENCES indexes(idx_indexid) ON DELETE CASCADE;
