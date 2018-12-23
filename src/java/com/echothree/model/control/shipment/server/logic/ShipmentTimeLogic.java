// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.shipment.server.logic;

import com.echothree.model.control.shipment.server.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.Shipment;
import com.echothree.model.data.shipment.server.entity.ShipmentDetail;
import com.echothree.model.data.shipment.server.entity.ShipmentTime;
import com.echothree.model.data.shipment.server.entity.ShipmentTimeType;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
import com.echothree.model.data.shipment.server.value.ShipmentTimeValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class ShipmentTimeLogic {

    private ShipmentTimeLogic() {
        super();
    }

    private static class ShipmentTimeLogicHolder {
        static ShipmentTimeLogic instance = new ShipmentTimeLogic();
    }

    public static ShipmentTimeLogic getInstance() {
        return ShipmentTimeLogicHolder.instance;
    }

    private String getShipmentTypeName(ShipmentType shipmentType) {
        return shipmentType.getLastDetail().getShipmentTypeName();
    }

    public void createOrUpdateShipmentTimeIfNotNull(final ExecutionErrorAccumulator ema, final Shipment shipment, final String shipmentTimeTypeName, final Long time,
            final BasePK partyPK) {
        if(time != null) {
            createOrUpdateShipmentTime(ema, shipment, shipmentTimeTypeName, time, partyPK);
        }
    }

    public void createOrUpdateShipmentTime(final ExecutionErrorAccumulator ema, final Shipment shipment, final String shipmentTimeTypeName, final Long time,
            final BasePK partyPK) {
        ShipmentControl shipmentControl = (ShipmentControl)Session.getModelController(ShipmentControl.class);
        ShipmentDetail shipmentDetail = shipment.getLastDetail();
        ShipmentType shipmentType = shipmentDetail.getShipmentType();
        ShipmentTimeType shipmentTimeType = shipmentControl.getShipmentTimeTypeByName(shipmentType, shipmentTimeTypeName);

        if(shipmentTimeType == null) {
            if(ema != null) {
                ema.addExecutionError(ExecutionErrors.UnknownShipmentTimeTypeName.name(), getShipmentTypeName(shipmentType), shipmentTimeTypeName);
            }
        } else {
            ShipmentTimeValue shipmentTimeValue = shipmentControl.getShipmentTimeValueForUpdate(shipment, shipmentTimeType);

            if(shipmentTimeValue == null) {
                shipmentControl.createShipmentTime(shipment, shipmentTimeType, time, partyPK);
            } else {
                shipmentTimeValue.setTime(time);
                shipmentControl.updateShipmentTimeFromValue(shipmentTimeValue, partyPK);
            }
        }
    }

    public Long getShipmentTime(final ExecutionErrorAccumulator ema, final Shipment shipment, final String shipmentTimeTypeName) {
        ShipmentControl shipmentControl = (ShipmentControl)Session.getModelController(ShipmentControl.class);
        ShipmentDetail shipmentDetail = shipment.getLastDetail();
        ShipmentType shipmentType = shipmentDetail.getShipmentType();
        ShipmentTimeType shipmentTimeType = shipmentControl.getShipmentTimeTypeByName(shipmentType, shipmentTimeTypeName);
        Long result = null;

        if(shipmentTimeType == null) {
            if(ema != null) {
                ema.addExecutionError(ExecutionErrors.UnknownShipmentTimeTypeName.name(), getShipmentTypeName(shipmentType), shipmentTimeTypeName);
            }
        } else {
            ShipmentTime shipmentTime = shipmentControl.getShipmentTime(shipment, shipmentTimeType);

            if(shipmentTime == null) {
                if(ema != null) {
                    ema.addExecutionError(ExecutionErrors.UnknownShipmentTime.name(), getShipmentTypeName(shipmentType), shipmentDetail.getShipmentName(), shipmentTimeTypeName);
                }
            } else {
                result = shipmentTime.getTime();
            }
        }

        return result;
    }

    public void deleteShipmentTime(final ExecutionErrorAccumulator ema, final Shipment shipment, final String shipmentTimeTypeName, final BasePK deletedBy) {
        ShipmentControl shipmentControl = (ShipmentControl)Session.getModelController(ShipmentControl.class);
        ShipmentDetail shipmentDetail = shipment.getLastDetail();
        ShipmentType shipmentType = shipmentDetail.getShipmentType();
        ShipmentTimeType shipmentTimeType = shipmentControl.getShipmentTimeTypeByName(shipmentType, shipmentTimeTypeName);

        if(shipmentTimeType == null) {
            if(ema != null) {
                ema.addExecutionError(ExecutionErrors.UnknownShipmentTimeTypeName.name(), getShipmentTypeName(shipmentType), shipmentTimeTypeName);
            }
        } else {
            ShipmentTime shipmentTime = shipmentControl.getShipmentTimeForUpdate(shipment, shipmentTimeType);

            if(shipmentTime == null) {
                if(ema != null) {
                    ema.addExecutionError(ExecutionErrors.UnknownShipmentTime.name(), getShipmentTypeName(shipmentType), shipmentDetail.getShipmentName(), shipmentTimeTypeName);
                }
            } else {
                shipmentControl.deleteShipmentTime(shipmentTime, deletedBy);
            }
        }
    }

}
