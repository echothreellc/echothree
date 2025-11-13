// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.model.control.shipment.server.transfer;

import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.shipment.common.transfer.ShipmentTypeTransfer;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ShipmentTypeTransferCache
        extends BaseShipmentTransferCache<ShipmentType, ShipmentTypeTransfer> {

    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    ShipmentControl shipmentControl = Session.getModelController(ShipmentControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of ShipmentTypeTransferCache */
    public ShipmentTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }

    @Override
    public ShipmentTypeTransfer getTransfer(UserVisit userVisit, ShipmentType shipmentType) {
        var shipmentTypeTransfer = get(shipmentType);
        
        if(shipmentTypeTransfer == null) {
            var shipmentTypeDetail = shipmentType.getLastDetail();
            var shipmentTypeName = shipmentTypeDetail.getShipmentTypeName();
            var parentShipmentType = shipmentTypeDetail.getParentShipmentType();
            var parentShipmentTypeTransfer = parentShipmentType == null? null: getTransfer(parentShipmentType);
            var shipmentSequenceType = shipmentTypeDetail.getShipmentSequenceType();
            var shipmentSequenceTypeTransfer = shipmentSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, shipmentSequenceType);
            var shipmentWorkflow = shipmentTypeDetail.getShipmentWorkflow();
            var shipmentWorkflowTransfer = shipmentWorkflow == null? null: workflowControl.getWorkflowTransfer(userVisit, shipmentWorkflow);
            var shipmentWorkflowEntrance = shipmentTypeDetail.getShipmentWorkflowEntrance();
            var shipmentWorkflowEntranceTransfer = shipmentWorkflowEntrance == null? null: workflowControl.getWorkflowEntranceTransfer(userVisit, shipmentWorkflowEntrance);
            var isDefault = shipmentTypeDetail.getIsDefault();
            var sortOrder = shipmentTypeDetail.getSortOrder();
            var description = shipmentControl.getBestShipmentTypeDescription(shipmentType, getLanguage(userVisit));
            
            shipmentTypeTransfer = new ShipmentTypeTransfer(shipmentTypeName, parentShipmentTypeTransfer, shipmentSequenceTypeTransfer, shipmentWorkflowTransfer,
                    shipmentWorkflowEntranceTransfer, isDefault, sortOrder, description);
            put(userVisit, shipmentType, shipmentTypeTransfer);
        }
        
        return shipmentTypeTransfer;
    }
    
}
