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

package com.echothree.model.control.shipment.common;

public interface ShipmentConstants {
    
    String ShipmentType_INCOMING_SHIPMENT = "INCOMING_SHIPMENT";
    String ShipmentType_OUTGOING_SHIPMENT = "OUTGOING_SHIPMENT";
    String ShipmentType_DROP_SHIPMENT     = "DROP_SHIPMENT";
    String ShipmentType_TRANSFER          = "TRANSFER";
    String ShipmentType_CUSTOMER_RETURN   = "CUSTOMER_RETURN";
    String ShipmentType_PURCHASE_SHIPMENT = "PURCHASE_SHIPMENT";
    String ShipmentType_CUSTOMER_SHIPMENT = "CUSTOMER_SHIPMENT";
    String ShipmentType_PURCHASE_RETURN   = "PURCHASE_RETURN";

    String ShipmentTimeType_ESTIMATED_READY = "ESTIMATED_READY";
    String ShipmentTimeType_ESTIMATED_SHIP = "ESTIMATED_SHIP";
    String ShipmentTimeType_ESTIMATED_DELIVERY = "ESTIMATED_DELIVERY";

}
