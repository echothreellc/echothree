UPDATE workflowentrancedetails
    JOIN workflowentrances ON wkflen_activedetailid = wkflendt_workflowentrancedetailid
    JOIN workflowdetails ON wkflendt_wkfl_workflowid = wkfldt_wkfl_workflowid
    JOIN workflows ON wkfl_activedetailid = wkfldt_workflowdetailid
SET wkflendt_workflowentrancename = 'NEW_SHOPPER'
WHERE wkflendt_workflowentrancename = 'NEW_ACTIVE'
  AND wkfldt_workflowname = 'CUSTOMER_STATUS';

UPDATE workflowstepdetails
    JOIN workflowsteps ON wkfls_activedetailid = wkflsdt_workflowstepdetailid
    JOIN workflowdetails ON wkflsdt_wkfl_workflowid = wkfldt_wkfl_workflowid
    JOIN workflows ON wkfl_activedetailid = wkfldt_workflowdetailid
SET wkflsdt_workflowstepname = 'VISITOR'
WHERE wkflsdt_workflowstepname = 'INACTIVE'
  AND wkfldt_workflowname = 'CUSTOMER_STATUS';

UPDATE workflowstepdetails
    JOIN workflowsteps ON wkfls_activedetailid = wkflsdt_workflowstepdetailid
    JOIN workflowdetails ON wkflsdt_wkfl_workflowid = wkfldt_wkfl_workflowid
    JOIN workflows ON wkfl_activedetailid = wkfldt_workflowdetailid
SET wkflsdt_workflowstepname = 'SHOPPER'
WHERE wkflsdt_workflowstepname = 'ACTIVE'
  AND wkfldt_workflowname = 'CUSTOMER_STATUS';

UPDATE workflowdestinationdetails
    JOIN workflowdestinations ON wkfldn_activedetailid = wkfldndt_workflowdestinationdetailid
    JOIN workflowstepdetails ON wkfldndt_wkfls_workflowstepid = wkflsdt_wkfls_workflowstepid
    JOIN workflowsteps ON wkfls_activedetailid = wkflsdt_workflowstepdetailid
    JOIN workflowdetails ON wkflsdt_wkfl_workflowid = wkfldt_wkfl_workflowid
    JOIN workflows ON wkfl_activedetailid = wkfldt_wkfl_workflowid
SET wkfldndt_workflowdestinationname = 'VISITOR_TO_SHOPPER'
WHERE wkfldndt_workflowdestinationname = 'INACTIVE_TO_ACTIVE'
  AND wkflsdt_workflowstepname = 'VISITOR'
  AND wkfldt_workflowname = 'CUSTOMER_STATUS';
