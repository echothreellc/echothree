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

package com.echothree.model.control.index.common;

public interface IndexConstants {
    
    String IndexType_CONTACT_MECHANISM = "CONTACT_MECHANISM";
    String IndexType_CONTENT_CATEGORY = "CONTENT_CATEGORY";
    String IndexType_CUSTOMER = "CUSTOMER";
    String IndexType_FORUM_MESSAGE = "FORUM_MESSAGE";
    String IndexType_EMPLOYEE = "EMPLOYEE";
    String IndexType_ENTITY_LIST_ITEM = "ENTITY_LIST_ITEM";
    String IndexType_ENTITY_TYPE = "ENTITY_TYPE";
    String IndexType_HARMONIZED_TARIFF_SCHEDULE_CODE = "HARMONIZED_TARIFF_SCHEDULE_CODE";
    String IndexType_ITEM = "ITEM";
    String IndexType_OFFER = "OFFER";
    String IndexType_SECURITY_ROLE = "SECURITY_ROLE";
    String IndexType_SECURITY_ROLE_GROUP = "SECURITY_ROLE_GROUP";
    String IndexType_USE = "USE";
    String IndexType_USE_TYPE = "USE_TYPE";
    String IndexType_VENDOR = "VENDOR";
    
    String Index_CUSTOMER = "CUSTOMER";
    String Index_EMPLOYEE = "EMPLOYEE";
    String Index_VENDOR = "VENDOR";

    String IndexField_Address1 = "address1";
    String IndexField_Address2 = "address2";
    String IndexField_Address3 = "address3";
    String IndexField_Aliases = "aliases";
    String IndexField_AllowAssociatePayments = "allowAssociatePayments";
    String IndexField_AllowClubDiscounts = "allowClubDiscounts";
    String IndexField_AllowCouponDiscounts = "allowCouponDiscounts";
    String IndexField_AllowSolicitation = "allowSolicitation";
    String IndexField_AreaCode = "areaCode";
    String IndexField_Attention = "attention";
    String IndexField_City = "city";
    String IndexField_CityGeoCodeName = "cityGeoCodeName";
    String IndexField_CompanyName = "companyName";
    String IndexField_ComponentVendorName = "componentVendorName";
    String IndexField_ContactMechanismName = "contactMechanismName";
    String IndexField_ContactMechanismPurposeNames = "contactMechanismPurposeNames";
    String IndexField_ContactMechanismTypeName = "contactMechanismTypeName";
    String IndexField_ContentCatalogName = "contentCatalogName";
    String IndexField_ContentCategoryName = "contentCategoryName";
    String IndexField_ContentCollectionName = "contentCollectionName";
    String IndexField_Country = "country";
    String IndexField_CountryGeoCodeName = "countryGeoCodeName";
    String IndexField_County = "county";
    String IndexField_CountyGeoCodeName = "countyGeoCodeName";
    String IndexField_CreatedTime = "createdTime";
    String IndexField_CustomerName = "customerName";
    String IndexField_DeletedTime = "deletedTime";
    String IndexField_Description = "description";
    String IndexField_EmailAddress = "emailAddress";
    String IndexField_EntityAttributeName = "entityAttributeName";
    String IndexField_EntityInstanceId = "entityInstanceId";
    String IndexField_EntityListItemName = "entityListItemName";
    String IndexField_EntityRef = "entityRef"; // Field.Index.NOT_ANALYZED
    String IndexField_EntityTypeName = "entityTypeName";
    String IndexField_FirstName = "firstName";
    String IndexField_ForumMessageName = "forumMessageName";
    String IndexField_ForumNames = "forumNames";
    String IndexField_ForumThreadName = "forumThreadName";
    String IndexField_HarmonizedTariffScheduleCodeName = "harmonizedTariffScheduleCodeName";
    String IndexField_Inet4Address = "inet4Address";
    String IndexField_InventorySerialized = "inventorySerialized";
    String IndexField_IsCommercial = "isCommercial";
    String IndexField_ItemAccountingCategoryName = "itemAccountingCategoryName";
    String IndexField_ItemCategoryName = "itemCategoryName";
    String IndexField_ItemDeliveryTypeName = "itemDeliveryTypeName";
    String IndexField_ItemInventoryTypeName = "itemInventoryTypeName";
    String IndexField_ItemName = "itemName";
    String IndexField_ItemNameAndAliases = "itemNameAndAliases";
    String IndexField_ItemPurchasingCategoryName = "itemPurchasingCategoryName";
    String IndexField_ItemTypeName = "itemTypeName";
    String IndexField_ItemUseTypeName = "itemUseTypeName";
    String IndexField_LastName = "lastName";
    String IndexField_MiddleName = "middleName";
    String IndexField_ModifiedTime = "modifiedTime";
    String IndexField_Name = "name";
    String IndexField_NameSuffix = "nameSuffix";
    String IndexField_OfferName = "offerName";
    String IndexField_Overview = "overview";
    String IndexField_ParentContentCategoryName = "parentContentCategoryName";
    String IndexField_ParentSecurityRoleGroupName = "parentSecurityRoleGroupName";
    String IndexField_PartyEmployeeName = "PartyEmployeeName";
    String IndexField_PartyName = "partyName";
    String IndexField_PartyNames = "partyNames";
    String IndexField_PartyTypeNames = "partyTypeNames";
    String IndexField_PersonalTitle = "personalTitle";
    String IndexField_PostalCode = "postalCode";
    String IndexField_PostalCodeGeoCodeName = "postalCodeGeoCodeName";
    String IndexField_PostedTime = "postedTime";
    String IndexField_PurchaseOrderStartTime = "purchaseOrderStartTime";
    String IndexField_PurchaseOrderEndTime = "purchaseOrderEndTime";
    String IndexField_SalesOrderStartTime = "salesOrderStartTime";
    String IndexField_SalesOrderEndTime = "salesOrderEndTime";
    String IndexField_SecurityRoleGroupName = "securityRoleGroupName";
    String IndexField_SecurityRoleName = "securityRoleName";
    String IndexField_ShippingChargeExempt = "shippingChargeExempt";
    String IndexField_ShippingStartTime = "shippingStartTime";
    String IndexField_ShippingEndTime = "shippingEndTime";
    String IndexField_State = "state";
    String IndexField_StateGeoCodeName = "stateGeoCodeName";
    String IndexField_TelephoneExtension = "telephoneExtension";
    String IndexField_TelephoneNumber = "telephoneNumber";
    String IndexField_Url = "url";
    String IndexField_UseName = "useName";
    String IndexField_UseTypeName = "useTypeName";
    String IndexField_VendorName = "vendorName";

    // Only used behind-the-scenes, may be a character that QueryParser doesn't like.
    String IndexFieldVariationSeparator = ":";
    
    String IndexFieldVariation_Dictionary = "dictionary";
    String IndexFieldVariation_Sortable = "sortable";

    // May be used by users of the system, should be able to be escaped but must not
    // be a character that's valid in a Name.
    String IndexSubfieldSeparator = ".";
    
    String IndexSubfieldLatitude = "latitude";
    String IndexSubfieldLongitude = "longitude";
    String IndexSubfieldElevation = "elevation";
    String IndexSubfieldAltitude = "altitude";
    
}
