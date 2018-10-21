DELETE parties
FROM parties, partydetails, partytypes
WHERE par_activedetailid = pardt_partydetailid
AND ptyp_partytypename = 'VENDOR' AND ptyp_partytypeid = pardt_ptyp_partytypeid;
