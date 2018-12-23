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

package com.echothree.model.control.search.common;

public interface SearchConstants {
    
    String SearchUseType_FACETS_ONLY = "FACETS_ONLY";
    String SearchUseType_INITIAL = "INITIAL";
    String SearchUseType_REFINEMENT = "REFINEMENT";
    String SearchUseType_APPEARANCE = "APPEARANCE";
    
    String SearchResultActionType_CONSIDERED = "CONSIDERED";
    String SearchResultActionType_SELECTED = "SELECTED";
    
    String SearchCheckSpellingActionType_NO_SUGGESTIONS = "NO_SUGGESTIONS";
    String SearchCheckSpellingActionType_HAS_SUGGESTIONS = "HAS_SUGGESTIONS";
    String SearchCheckSpellingActionType_IGNORED = "IGNORED";
    
    String SearchDefaultOperator_AND = "AND";
    String SearchDefaultOperator_OR = "OR";
    
    String SearchSortDirection_ASCENDING = "ASCENDING";
    String SearchSortDirection_DESCENDING = "DESCENDING";
    
    String SearchKind_CONTACT_MECHANISM = "CONTACT_MECHANISM";
    String SearchKind_CONTENT_CATEGORY = "CONTENT_CATEGORY";
    String SearchKind_CUSTOMER = "CUSTOMER";
    String SearchKind_EMPLOYEE = "EMPLOYEE";
    String SearchKind_ENTITY_LIST_ITEM = "ENTITY_LIST_ITEM";
    String SearchKind_ENTITY_TYPE = "ENTITY_TYPE";
    String SearchKind_FORUM_MESSAGE = "FORUM_MESSAGE";
    String SearchKind_HARMONIZED_TARIFF_SCHEDULE_CODE = "HARMONIZED_TARIFF_SCHEDULE_CODE";
    String SearchKind_ITEM = "ITEM";
    String SearchKind_LEAVE = "LEAVE";
    String SearchKind_OFFER = "OFFER";
    String SearchKind_SALES_ORDER = "SALES_ORDER";
    String SearchKind_SALES_ORDER_BATCH = "SALES_ORDER_BATCH";
    String SearchKind_SECURITY_ROLE = "ECURITY_ROLE";
    String SearchKind_SECURITY_ROLE_GROUP = "SECURITY_ROLE_GROUP";
    String SearchKind_USE = "USE";
    String SearchKind_USE_TYPE = "USE_TYPE";
    String SearchKind_VENDOR = "VENDOR";
    
    String SearchType_CUSTOMER = "CUSTOMER";
    String SearchType_EMPLOYEE = "EMPLOYEE";
    String SearchType_HUMAN_RESOURCES = "HUMAN_RESOURCES";
    String SearchType_ITEM_MAINTAINENCE = "ITEM_MAINTAINENCE";
    String SearchType_LEAVE_MAINTAINENCE = "LEAVE_MAINTAINENCE";
    String SearchType_ORDER_ENTRY = "ORDER_ENTRY";
    String SearchType_SALES_ORDER_BATCH_MAINTAINENCE = "SALES_ORDER_BATCH_MAINTAINENCE";
    String SearchType_VENDOR_REVIEW = "VENDOR_REVIEW";
    
    String SearchSortOrder_CONTACT_MECHANISM_NAME = "CONTACT_MECHANISM_NAME";
    String SearchSortOrder_CONTENT_CATEGORY_NAME = "CONTENT_CATEGORY_NAME";
    String SearchSortOrder_CREATED_TIME = "CREATED_TIME";
    String SearchSortOrder_CUSTOMER_NAME = "CUSTOMER_NAME";
    String SearchSortOrder_DEFAULT_DESCRIPTION = "DEFAULT_DESCRIPTION";
    String SearchSortOrder_DELETED_TIME = "DELETED_TIME";
    String SearchSortOrder_DESCRIPTION = "DESCRIPTION";
    String SearchSortOrder_ENTITY_TYPE_NAME = "ENTITY_TYPE_NAME";
    String SearchSortOrder_FIRST_NAME = "FIRST_NAME";
    String SearchSortOrder_HARMONIZED_TARIFF_SCHEDULE_CODE_NAME = "HARMONIZED_TARIFF_SCHEDULE_CODE_NAME";
    String SearchSortOrder_ITEM_NAME = "ITEM_NAME";
    String SearchSortOrder_LAST_NAME = "LAST_NAME";
    String SearchSortOrder_LEAVE_NAME = "LEAVE_NAME";
    String SearchSortOrder_ENTITY_LIST_ITEM_NAME = "ENTITY_LIST_ITEM_NAME";
    String SearchSortOrder_MODIFIED_TIME = "MODIFIED_TIME";
    String SearchSortOrder_OFFER_NAME = "OFFER_NAME";
    String SearchSortOrder_POSTED_TIME = "POSTED_TIME";
    String SearchSortOrder_SALES_ORDER_NAME = "SALES_ORDER_NAME";
    String SearchSortOrder_SALES_ORDER_BATCH_NAME = "SALES_ORDER_BATCH_NAME";
    String SearchSortOrder_SECRUITY_ROLE_GROUP_NAME = "SECRUITY_ROLE_GROUP_NAME";
    String SearchSortOrder_SECRUITY_ROLE_NAME = "SECRUITY_ROLE_NAME";
    String SearchSortOrder_SCORE = "SCORE";
    String SearchSortOrder_TITLE = "TITLE";
    String SearchSortOrder_USE_NAME = "USE_NAME";
    String SearchSortOrder_USE_TYPE_NAME = "USE_TYPE_NAME";
    String SearchSortOrder_VENDOR_NAME = "VENDOR_NAME";
    
}
