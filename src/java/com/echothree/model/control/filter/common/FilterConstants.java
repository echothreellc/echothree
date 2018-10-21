// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.filter.common;

public interface FilterConstants {
    
    String FilterKind_COST  = "COST";
    String FilterKind_PRICE = "PRICE";
    
    String FilterType_CLUB             = "CLUB";
    String FilterType_COUPON           = "COUPON";
    String FilterType_OFFER_ITEM_PRICE = "OFFER_ITEM_PRICE";
    
    String FilterAdjustmentSource_CURRENT             = "CURRENT";
    String FilterAdjustmentSource_INVENTORY_COST      = "INVENTORY_COST";
    String FilterAdjustmentSource_ITEM_PRICE          = "ITEM_PRICE";
    String FilterAdjustmentSource_PRIMARY_VENDOR_COST = "PRIMARY_VENDOR_COST";
    String FilterAdjustmentSource_VENDOR_COST         = "VENDOR_COST";
    String FilterAdjustmentSource_SET_AMOUNT          = "SET_AMOUNT";
    
    String FilterAdjustmentType_AMOUNT       = "AMOUNT";
    String FilterAdjustmentType_FIXED_AMOUNT = "FIXED_AMOUNT";
    String FilterAdjustmentType_PERCENT      = "PERCENT";
    
}
