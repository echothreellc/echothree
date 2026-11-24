// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.control.user.shipment.server.command;

import com.echothree.control.user.shipment.common.edit.ShipmentEditFactory;
import com.echothree.control.user.shipment.common.edit.ShipmentTypeShippingMethodEdit;
import com.echothree.control.user.shipment.common.result.EditShipmentTypeShippingMethodResult;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.control.user.shipment.common.spec.ShipmentTypeShippingMethodSpec;
import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.model.control.shipment.server.logic.ShipmentTypeLogic;
import com.echothree.model.control.shipping.server.logic.ShippingMethodLogic;
import com.echothree.model.data.shipment.server.entity.ShipmentTypeShippingMethod;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditShipmentTypeShippingMethodCommand
        extends BaseAbstractEditCommand<ShipmentTypeShippingMethodSpec, ShipmentTypeShippingMethodEdit, EditShipmentTypeShippingMethodResult, ShipmentTypeShippingMethod, ShippingMethod> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }

    @Inject
    ShipmentControl shipmentControl;

    @Inject
    ShipmentTypeLogic shipmentTypeLogic;

    @Inject
    ShippingMethodLogic shippingMethodLogic;

    /** Creates a new instance of EditShipmentTypeShippingMethodCommand */
    public EditShipmentTypeShippingMethodCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditShipmentTypeShippingMethodResult getResult() {
        return ShipmentResultFactory.getEditShipmentTypeShippingMethodResult();
    }

    @Override
    public ShipmentTypeShippingMethodEdit getEdit() {
        return ShipmentEditFactory.getShipmentTypeShippingMethodEdit();
    }

    @Override
    public ShipmentTypeShippingMethod getEntity(EditShipmentTypeShippingMethodResult result) {
        ShipmentTypeShippingMethod shipmentTypeShippingMethod = null;
        var shipmentTypeName = spec.getShipmentTypeName();
        var shipmentType = shipmentTypeLogic.getShipmentTypeByName(this, shipmentTypeName);
        var shippingMethodName = spec.getShippingMethodName();
        var shippingMethod = shippingMethodLogic.getShippingMethodByName(this, shippingMethodName);

        if(!hasExecutionErrors()) {
            shipmentTypeShippingMethod = shipmentControl.getShipmentTypeShippingMethod(shipmentType, shippingMethod,
                    editModeToEntityPermission(editMode));

            if(shipmentTypeShippingMethod == null) {
                addExecutionError(ExecutionErrors.UnknownShipmentTypeShippingMethod.name(), shipmentTypeName, shippingMethodName);
            }
        }

        return shipmentTypeShippingMethod;
    }

    @Override
    public ShippingMethod getLockEntity(ShipmentTypeShippingMethod shipmentTypeShippingMethod) {
        return shipmentTypeShippingMethod.getShippingMethod();
    }

    @Override
    public void fillInResult(EditShipmentTypeShippingMethodResult result, ShipmentTypeShippingMethod shipmentTypeShippingMethod) {
        result.setShipmentTypeShippingMethod(shipmentControl.getShipmentTypeShippingMethodTransfer(getUserVisit(), shipmentTypeShippingMethod));
    }

    @Override
    public void doLock(ShipmentTypeShippingMethodEdit edit, ShipmentTypeShippingMethod shipmentTypeShippingMethod) {
        edit.setIsDefault(shipmentTypeShippingMethod.getIsDefault().toString());
        edit.setSortOrder(shipmentTypeShippingMethod.getSortOrder().toString());
    }

    @Override
    public void doUpdate(ShipmentTypeShippingMethod shipmentTypeShippingMethod) {
        var shipmentTypeShippingMethodValue = shipmentTypeShippingMethod.getShipmentTypeShippingMethodValue().clone();

        shipmentTypeShippingMethodValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        shipmentTypeShippingMethodValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        shipmentControl.updateShipmentTypeShippingMethodFromValue(shipmentTypeShippingMethodValue, getPartyPK());
    }

}
