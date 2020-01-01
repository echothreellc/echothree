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

package com.echothree.model.control.order.common;

public interface OrderConstants {
    
    String OrderType_PURCHASE_ORDER = "PURCHASE_ORDER";
    String OrderType_SALES_ORDER = "SALES_ORDER";
    String OrderType_WISHLIST = "WISHLIST";
    
    String OrderRoleType_PLACING = "PLACING";
    String OrderRoleType_BILL_TO = "BILL_TO";
    String OrderRoleType_BILL_FROM = "BILL_FROM";
    String OrderRoleType_SHIP_TO = "SHIP_TO";
    String OrderRoleType_END_USER = "END_USER";

    String OrderTimeType_CANCEL_AFTER = "CANCEL_AFTER";
    String OrderTimeType_CURRENT_SCHEDULE_DELIVERY = "CURRENT_SCHEDULE_DELIVERY";
    String OrderTimeType_CURRENT_SCHEDULE_SHIP = "CURRENT_SCHEDULE_SHIP";
    String OrderTimeType_DELIVERY_REQUESTED = "DELIVERY_REQUESTED";
    String OrderTimeType_DO_NOT_DELIVER_AFTER = "DO_NOT_DELIVER_AFTER";
    String OrderTimeType_DO_NOT_DELIVER_BEFORE = "DO_NOT_DELIVER_BEFORE";
    String OrderTimeType_REQUESTED_SHIP = "REQUESTED_SHIP";
    String OrderTimeType_SHIP_NOT_BEFORE = "SHIP_NOT_BEFORE";
    String OrderTimeType_SHIP_NO_LATER = "SHIP_NO_LATER";
    
}
