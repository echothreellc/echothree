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

package com.echothree.model.control.chain.common;

public interface ChainConstants {
    
    String ChainKind_CONTACT_LIST = "CONTACT_LIST";
    String ChainKind_CUSTOMER = "CUSTOMER";
    String ChainKind_CUSTOMER_RETURN = "CUSTOMER_RETURN";
    String ChainKind_CUSTOMER_SHIPMENT = "CUSTOMER_SHIPMENT";
    String ChainKind_PAYMENT_METHOD = "PAYMENT_METHOD";
    String ChainKind_PURCHASE_ORDER = "PURCHASE_ORDER";
    String ChainKind_SALES_ORDER = "SALES_ORDER";
    String ChainKind_SUBSCRIPTION = "SUBSCRIPTION";
    
    String ChainType_BACKORDER_NOTIFICATION = "BACKORDER_NOTIFICATION";
    String ChainType_CC_AVS_FAILURE = "CC_AVS_FAILURE";
    String ChainType_CC_DECLINE = "CC_DECLINE";
    String ChainType_CONFIRMATION_REQUEST = "CONFIRMATION_REQUEST";
    String ChainType_EXPIRATION = "EXPIRATION";
    String ChainType_EXPIRATION_WARNING = "EXPIRATION_WARNING";
    String ChainType_INITIAL = "INITIAL";
    String ChainType_ITEM_CANCELLATION = "ITEM_CANCELLATION";
    String ChainType_ORDER_CANCELLATION = "ORDER_CANCELLATION";
    String ChainType_ORDER_CONFIRMATION = "ORDER_CONFIRMATION";
    String ChainType_PARTY_CREDIT_LIMIT_CHANGED = "PARTY_CREDIT_LIMIT_CHANGED";
    String ChainType_PARTY_CREDIT_STATUS_CHANGED = "PARTY_CREDIT_STATUS_CHANGED";
    String ChainType_PARTY_TERM_CHANGED = "PARTY_TERM_CHANGED";
    String ChainType_PASSWORD_RECOVERY = "PASSWORD_RECOVERY";
    String ChainType_RENEWAL = "RENEWAL";
    String ChainType_RETURN_AUTHORIZATION = "RETURN_AUTHORIZATION";
    String ChainType_RETURN_NOT_RECEIVED = "RETURN_NOT_RECEIVED";
    String ChainType_RETURN_RECEIVED = "RETURN_RECEIVED";
    String ChainType_SHIPPING_NOTIFICATION = "SHIPPING_NOTIFICATION";
    String ChainType_SUBSCRIBE = "SUBSCRIBE";
    String ChainType_UNSUBSCRIBE = "UNSUBSCRIBE";
    String ChainType_WELCOME = "WELCOME";
    
    String ChainEntityRoleType_CUSTOMER = "CUSTOMER";
    String ChainEntityRoleType_EMPLOYEE = "EMPLOYEE";
    String ChainEntityRoleType_PARTY_CONTACT_LIST = "PARTY_CONTACT_LIST";
    String ChainEntityRoleType_SUBSCRIPTION = "SUBSCRIPTION";
    String ChainEntityRoleType_VENDOR = "VENDOR";
    
    String ChainActionType_LETTER = "LETTER";
    String ChainActionType_SURVEY = "SURVEY";
    String ChainActionType_CHAIN_ACTION_SET = "CHAIN_ACTION_SET";
    
}
