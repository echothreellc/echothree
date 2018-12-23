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

package com.echothree.model.control.selector.common;

public interface SelectorConstants {
    
    String SelectorKind_CUSTOMER = "CUSTOMER";
    String SelectorKind_EMPLOYEE = "EMPLOYEE";
    String SelectorKind_INVOICE = "INVOICE";
    String SelectorKind_ITEM = "ITEM";
    String SelectorKind_ORDER = "ORDER";
    String SelectorKind_POSTAL_ADDRESS = "POSTAL_ADDRESS";
    String SelectorKind_SALES_ORDER_ITEM = "SALES_ORDER_ITEM";
    String SelectorKind_SHIPMENT = "SHIPMENT";
    String SelectorKind_VENDOR = "VENDOR";
    
    String SelectorType_CARRIER = "CARRIER";
    String SelectorType_CARRIER_OPTION = "CARRIER_OPTION";
    String SelectorType_CARRIER_SERVICE = "CARRIER_SERVICE";
    String SelectorType_CARRIER_SERVICE_OPTION = "CARRIER_SERVICE_OPTION";
    String SelectorType_CONTENT_CATEGORY = "CONTENT_CATEGORY";
    String SelectorType_EMAIL_REVIEW = "EMAIL_REVIEW";
    String SelectorType_FILTER = "FILTER";
    String SelectorType_OFFER = "OFFER";
    String SelectorType_OFFER_DISCOUNT = "OFFER_DISCOUNT";
    String SelectorType_PAYMENT_METHOD = "PAYMENT_METHOD";
    String SelectorType_SECURITY_ROLE = "SECURITY_ROLE";
    String SelectorType_SHIPPING_METHOD = "SHIPPING_METHOD";
    String SelectorType_WORK_ASSIGNMENT = "WORK_ASSIGNMENT";
    
    String SelectorBooleanType_AND = "AND";
    String SelectorBooleanType_OR = "OR";
    
    String SelectorComparisonType_GT = "GT";
    String SelectorComparisonType_LT = "LT";
    String SelectorComparisonType_EQUAL = "EQUAL";
    String SelectorComparisonType_GT_EQUAL = "GT_EQUAL";
    String SelectorComparisonType_LT_EQUAL = "LT_EQUAL";
    
    String SelectorNodeType_BOOLEAN = "BOOLEAN";
    String SelectorNodeType_ITEM_ACCOUNTING_CATEGORY = "ITEM_ACCOUNTING_CATEGORY";
    String SelectorNodeType_ITEM_CATEGORY = "ITEM_CATEGORY";
    String SelectorNodeType_ITEM_PURCHASING_CATEGORY = "ITEM_PURCHASING_CATEGORY";
    String SelectorNodeType_ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
    String SelectorNodeType_ENTITY_LIST_ITEM = "ENTITY_LIST_ITEM";
    String SelectorNodeType_ENTITY_LONG = "ENTITY_LONG";
    String SelectorNodeType_ENTITY_INTEGER = "ENTITY_INTEGER";
    String SelectorNodeType_ENTITY_DATE = "ENTITY_DATE";
    String SelectorNodeType_ENTITY_TIME = "ENTITY_TIME";
    String SelectorNodeType_ENTITY_STRING = "ENTITY_STRING";
    String SelectorNodeType_ENTITY_CLOB = "ENTITY_CLOB";
    String SelectorNodeType_ENTITY_BOOLEAN = "ENTITY_BOOLEAN";
    String SelectorNodeType_GEO_CODE = "GEO_CODE";
    String SelectorNodeType_PAYMENT_METHOD = "PAYMENT_METHOD";
    String SelectorNodeType_PAYMENT_PROCESSOR = "PAYMENT_PROCESSOR";
    String SelectorNodeType_RESPONSIBILITY_TYPE = "RESPONSIBILITY_TYPE";
    String SelectorNodeType_SKILL_TYPE = "SKILL_TYPE";
    String SelectorNodeType_TRAINING_CLASS = "TRAINING_CLASS";
    String SelectorNodeType_WORKFLOW_STEP = "WORKFLOW_STEP";
    
    String SelectorTextSearchType_CONTAINS_WORD = "CONTAINS_WORD";
    String SelectorTextSearchType_STARTS_WITH = "STARTS_WITH";
    
}
