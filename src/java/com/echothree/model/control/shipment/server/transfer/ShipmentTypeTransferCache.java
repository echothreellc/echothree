// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.shipment.common.transfer.ShipmentTypeTransfer;
import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
import com.echothree.model.data.shipment.server.entity.ShipmentTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.server.persistence.Session;

public class ShipmentTypeTransferCache
        extends BaseShipmentTransferCache<ShipmentType, ShipmentTypeTransfer> {

    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
    ShipmentControl shipmentControl = (ShipmentControl)Session.getModelController(ShipmentControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of ShipmentTypeTransferCache */
    public ShipmentTypeTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }

    @Override
    public ShipmentTypeTransfer getTransfer(ShipmentType shipmentType) {
        ShipmentTypeTransfer shipmentTypeTransfer = get(shipmentType);
        
        if(shipmentTypeTransfer == null) {
            ShipmentTypeDetail shipmentTypeDetail = shipmentType.getLastDetail();
            String shipmentTypeName = shipmentTypeDetail.getShipmentTypeName();
            ShipmentType parentShipmentType = shipmentTypeDetail.getParentShipmentType();
            ShipmentTypeTransfer parentShipmentTypeTransfer = parentShipmentType == null? null: getTransfer(parentShipmentType);
            SequenceType shipmentSequenceType = shipmentTypeDetail.getShipmentSequenceType();
            SequenceTypeTransfer shipmentSequenceTypeTransfer = shipmentSequenceType == null? null: sequenceControl.getSequenceTypeTransfer(userVisit, shipmentSequenceType);
            Workflow shipmentWorkflow = shipmentTypeDetail.getShipmentWorkflow();
            WorkflowTransfer shipmentWorkflowTransfer = shipmentWorkflow == null? null: workflowControl.getWorkflowTransfer(userVisit, shipmentWorkflow);
            WorkflowEntrance shipmentWorkflowEntrance = shipmentTypeDetail.getShipmentWorkflowEntrance();
            WorkflowEntranceTransfer shipmentWorkflowEntranceTransfer = shipmentWorkflowEntrance == null? null: workflowControl.getWorkflowEntranceTransfer(userVisit, shipmentWorkflowEntrance);
            Boolean isDefault = shipmentTypeDetail.getIsDefault();
            Integer sortOrder = shipmentTypeDetail.getSortOrder();
            String description = shipmentControl.getBestShipmentTypeDescription(shipmentType, getLanguage());
            
            shipmentTypeTransfer = new ShipmentTypeTransfer(shipmentTypeName, parentShipmentTypeTransfer, shipmentSequenceTypeTransfer, shipmentWorkflowTransfer,
                    shipmentWorkflowEntranceTransfer, isDefault, sortOrder, description);
            put(shipmentType, shipmentTypeTransfer);
        }
        
        return shipmentTypeTransfer;
    }
    
}
