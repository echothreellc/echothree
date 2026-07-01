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

import com.echothree.control.user.shipment.common.form.GetShipmentTypeShippingMethodsForm;
import com.echothree.control.user.shipment.common.result.ShipmentResultFactory;
import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.control.shipping.server.logic.ShippingMethodLogic;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
import com.echothree.model.data.shipment.server.entity.ShipmentTypeShippingMethod;
import com.echothree.model.data.shipment.server.factory.ShipmentTypeShippingMethodFactory;
import com.echothree.model.data.shipping.server.entity.ShippingMethod;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetShipmentTypeShippingMethodsCommand
        extends BasePaginatedMultipleEntitiesCommand<ShipmentTypeShippingMethod, GetShipmentTypeShippingMethodsForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ShipmentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ShippingMethodName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    ShipmentControl shipmentControl;

    @Inject
    ShippingControl shippingControl;

    @Inject
    ShippingMethodLogic shippingMethodLogic;

    /** Creates a new instance of GetShipmentTypeShippingMethodsCommand */
    public GetShipmentTypeShippingMethodsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    ShipmentType shipmentType;
    ShippingMethod shippingMethod;

    @Override
    protected void handleForm() {
        var shipmentTypeName = form.getShipmentTypeName();
        var shippingMethodName = form.getShippingMethodName();
        var parameterCount = (shipmentTypeName != null ? 1 : 0) + (shippingMethodName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(shipmentTypeName != null) {
                shipmentType = shipmentControl.getShipmentTypeByName(shipmentTypeName);

                if(shipmentType == null) {
                    addExecutionError(ExecutionErrors.UnknownShipmentTypeName.name(), shipmentTypeName);
                }
            } else {
                shippingMethod = shippingMethodLogic.getShippingMethodByName(this, shippingMethodName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(shipmentType != null) {
                total = shipmentControl.countShipmentTypeShippingMethodsByShipmentType(shipmentType);
            } else {
                total = shipmentControl.countShipmentTypeShippingMethodsByShippingMethod(shippingMethod);
            }
        }

        return total;
    }

    @Override
    protected Collection<ShipmentTypeShippingMethod> getEntities() {
        Collection<ShipmentTypeShippingMethod> entities = null;

        if(!hasExecutionErrors()) {
            if(shipmentType != null) {
                entities = shipmentControl.getShipmentTypeShippingMethodsByShipmentType(shipmentType);
            } else {
                entities = shipmentControl.getShipmentTypeShippingMethodsByShippingMethod(shippingMethod);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<ShipmentTypeShippingMethod> entities) {
        var result = ShipmentResultFactory.getGetShipmentTypeShippingMethodsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(shipmentType != null) {
                result.setShipmentType(shipmentControl.getShipmentTypeTransfer(userVisit, shipmentType));
            } else {
                result.setShippingMethod(shippingControl.getShippingMethodTransfer(userVisit, shippingMethod));
            }

            if(session.hasLimit(ShipmentTypeShippingMethodFactory.class)) {
                result.setShipmentTypeShippingMethodCount(getTotalEntities());
            }

            result.setShipmentTypeShippingMethods(shipmentControl.getShipmentTypeShippingMethodTransfers(userVisit, entities));
        }

        return result;
    }

}
