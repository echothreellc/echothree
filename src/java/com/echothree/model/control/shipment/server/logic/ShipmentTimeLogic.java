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

package com.echothree.model.control.shipment.server.logic;

import com.echothree.model.control.shipment.server.control.ShipmentControl;
import com.echothree.model.data.shipment.server.entity.Shipment;
import com.echothree.model.data.shipment.server.entity.ShipmentType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ShipmentTimeLogic
        extends BaseLogic {

    protected ShipmentTimeLogic() {
        super();
    }

    public static ShipmentTimeLogic getInstance() {
        return CDI.current().select(ShipmentTimeLogic.class).get();
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
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var shipmentDetail = shipment.getLastDetail();
        var shipmentType = shipmentDetail.getShipmentType();
        var shipmentTimeType = shipmentControl.getShipmentTimeTypeByName(shipmentType, shipmentTimeTypeName);

        if(shipmentTimeType == null) {
            if(ema != null) {
                ema.addExecutionError(ExecutionErrors.UnknownShipmentTimeTypeName.name(), getShipmentTypeName(shipmentType), shipmentTimeTypeName);
            }
        } else {
            var shipmentTimeValue = shipmentControl.getShipmentTimeValueForUpdate(shipment, shipmentTimeType);

            if(shipmentTimeValue == null) {
                shipmentControl.createShipmentTime(shipment, shipmentTimeType, time, partyPK);
            } else {
                shipmentTimeValue.setTime(time);
                shipmentControl.updateShipmentTimeFromValue(shipmentTimeValue, partyPK);
            }
        }
    }

    public Long getShipmentTime(final ExecutionErrorAccumulator ema, final Shipment shipment, final String shipmentTimeTypeName) {
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var shipmentDetail = shipment.getLastDetail();
        var shipmentType = shipmentDetail.getShipmentType();
        var shipmentTimeType = shipmentControl.getShipmentTimeTypeByName(shipmentType, shipmentTimeTypeName);
        Long result = null;

        if(shipmentTimeType == null) {
            if(ema != null) {
                ema.addExecutionError(ExecutionErrors.UnknownShipmentTimeTypeName.name(), getShipmentTypeName(shipmentType), shipmentTimeTypeName);
            }
        } else {
            var shipmentTime = shipmentControl.getShipmentTime(shipment, shipmentTimeType);

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
        var shipmentControl = Session.getModelController(ShipmentControl.class);
        var shipmentDetail = shipment.getLastDetail();
        var shipmentType = shipmentDetail.getShipmentType();
        var shipmentTimeType = shipmentControl.getShipmentTimeTypeByName(shipmentType, shipmentTimeTypeName);

        if(shipmentTimeType == null) {
            if(ema != null) {
                ema.addExecutionError(ExecutionErrors.UnknownShipmentTimeTypeName.name(), getShipmentTypeName(shipmentType), shipmentTimeTypeName);
            }
        } else {
            var shipmentTime = shipmentControl.getShipmentTimeForUpdate(shipment, shipmentTimeType);

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
