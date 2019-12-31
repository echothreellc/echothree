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

package com.echothree.model.control.item.common;

public interface ItemConstants {
    
    String ItemAliasChecksumType_ISBN_10 = "ISBN_10";
    String ItemAliasChecksumType_ISBN_13 = "ISBN_13";
    String ItemAliasChecksumType_NONE = "NONE";
    String ItemAliasChecksumType_UPC_A = "UPC_A";

    String ItemType_KIT = "KIT";
    String ItemType_REGULAR = "REGULAR";
    String ItemType_STYLE = "STYLE";
    
    String ItemDeliveryType_PHYSICAL = "PHYSICAL";
    String ItemDeliveryType_ELECTRONIC = "ELECTRONIC";
    String ItemDeliveryType_NONE = "NONE";
    
    String ItemInventoryType_INVENTORY = "INVENTORY";
    String ItemInventoryType_NONINVENTORY = "NONINVENTORY";
    String ItemInventoryType_DROP_SHIP = "DROP_SHIP";
    
    String ItemUseType_CLUB = "CLUB";
    String ItemUseType_REGULAR = "REGULAR";
    
    String ItemDescriptionType_DEFAULT_DESCRIPTION = "DEFAULT_DESCRIPTION";
    String ItemDescriptionType_DEFAULT_DETAILS = "DEFAULT_DETAILS";
    String ItemDescriptionType_INDEXED_DESCRIPTION = "INDEXED_DESCRIPTION";
    String ItemDescriptionType_INVOICE_DESCRIPTION = "INVOICE_DESCRIPTION";
    String ItemDescriptionType_LARGE_IMAGE = "LARGE_IMAGE";
    String ItemDescriptionType_ORDER_DESCRIPTION = "ORDER_DESCRIPTION";
    String ItemDescriptionType_PRIMARY_IMAGE = "PRIMARY_IMAGE";
    String ItemDescriptionType_PURCHASE_ORDER_DESCRIPTION = "PURCHASE_ORDER_DESCRIPTION";
    String ItemDescriptionType_SMALL_IMAGE = "SMALL_IMAGE";
    String ItemDescriptionType_WEB_CATEGORY_DETAILS = "WEB_CATEGORY_DETAILS";
    String ItemDescriptionType_WEB_DESCRIPTION = "WEB_DESCRIPTION";
    String ItemDescriptionType_WEB_DETAILS = "WEB_DETAILS";
    String ItemDescriptionType_WEB_ITEM_DETAILS = "WEB_ITEM_DETAILS";
    
    String RelatedItemType_SALES_ORDER_CROSS_SELLS = "SALES_ORDER_CROSS_SELLS";
    String RelatedItemType_SALES_ORDER_UPSELLS = "SALES_ORDER_UPSELLS";
    String RelatedItemType_SALES_ORDER_INTERESTED_IN = "SALES_ORDER_INTERESTED_IN";
    String RelatedItemType_SALES_ORDER_ALSO_BOUGHT = "SALES_ORDER_ALSO_BOUGHT";
    String RelatedItemType_SUGGESTED_REPLACEMENTS_OUT_OF_STOCK = "SUGGESTED_REPLACEMENTS_OUT_OF_STOCK";
    String RelatedItemType_SUGGESTED_REPLACEMENTS_DISCONTINUED = "SUGGESTED_REPLACEMENTS_DISCONTINUED";

    String HarmonizedTariffScheduleCodeUseType_EXPORT = "EXPORT";
    String HarmonizedTariffScheduleCodeUseType_IMPORT = "IMPORT";
    
}
