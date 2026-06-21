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

package com.echothree.control.user.offer.common;

import com.echothree.control.user.offer.common.form.*;
import com.echothree.control.user.offer.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface OfferService
        extends OfferForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Offers
    // -------------------------------------------------------------------------
    
    CommandResult<?> createOffer(UserVisitPK userVisitPK, CreateOfferForm form);
    
    CommandResult<GetOffersResult> getOffers(UserVisitPK userVisitPK, GetOffersForm form);
    
    CommandResult<GetOfferResult> getOffer(UserVisitPK userVisitPK, GetOfferForm form);
    
    CommandResult<GetOfferChoicesResult> getOfferChoices(UserVisitPK userVisitPK, GetOfferChoicesForm form);

    CommandResult<?> setDefaultOffer(UserVisitPK userVisitPK, SetDefaultOfferForm form);
    
    CommandResult<EditOfferResult> editOffer(UserVisitPK userVisitPK, EditOfferForm form);
    
    CommandResult<?> deleteOffer(UserVisitPK userVisitPK, DeleteOfferForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createOfferDescription(UserVisitPK userVisitPK, CreateOfferDescriptionForm form);
    
    CommandResult<GetOfferDescriptionsResult> getOfferDescriptions(UserVisitPK userVisitPK, GetOfferDescriptionsForm form);
    
    CommandResult<EditOfferDescriptionResult> editOfferDescription(UserVisitPK userVisitPK, EditOfferDescriptionForm form);
    
    CommandResult<?> deleteOfferDescription(UserVisitPK userVisitPK, DeleteOfferDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Customer Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createOfferCustomerType(UserVisitPK userVisitPK, CreateOfferCustomerTypeForm form);
    
    CommandResult<GetOfferCustomerTypeResult> getOfferCustomerType(UserVisitPK userVisitPK, GetOfferCustomerTypeForm form);

    CommandResult<GetOfferCustomerTypesResult> getOfferCustomerTypes(UserVisitPK userVisitPK, GetOfferCustomerTypesForm form);

    CommandResult<?> setDefaultOfferCustomerType(UserVisitPK userVisitPK, SetDefaultOfferCustomerTypeForm form);

    CommandResult<EditOfferCustomerTypeResult> editOfferCustomerType(UserVisitPK userVisitPK, EditOfferCustomerTypeForm form);
    
    CommandResult<?> deleteOfferCustomerType(UserVisitPK userVisitPK, DeleteOfferCustomerTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Chain Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createOfferChainType(UserVisitPK userVisitPK, CreateOfferChainTypeForm form);

    CommandResult<GetOfferChainTypesResult> getOfferChainTypes(UserVisitPK userVisitPK, GetOfferChainTypesForm form);

    CommandResult<GetOfferChainTypeResult> getOfferChainType(UserVisitPK userVisitPK, GetOfferChainTypeForm form);

    CommandResult<EditOfferChainTypeResult> editOfferChainType(UserVisitPK userVisitPK, EditOfferChainTypeForm form);
    
    CommandResult<?> deleteOfferChainType(UserVisitPK userVisitPK, DeleteOfferChainTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Uses
    // -------------------------------------------------------------------------
    
    CommandResult<?> createOfferUse(UserVisitPK userVisitPK, CreateOfferUseForm form);
    
    CommandResult<GetOfferUsesResult> getOfferUses(UserVisitPK userVisitPK, GetOfferUsesForm form);
    
    CommandResult<GetOfferUseResult> getOfferUse(UserVisitPK userVisitPK, GetOfferUseForm form);
    
    CommandResult<EditOfferUseResult> editOfferUse(UserVisitPK userVisitPK, EditOfferUseForm form);
    
    CommandResult<?> deleteOfferUse(UserVisitPK userVisitPK, DeleteOfferUseForm form);
    
    // -------------------------------------------------------------------------
    //   Sources
    // -------------------------------------------------------------------------
    
    CommandResult<?> createSource(UserVisitPK userVisitPK, CreateSourceForm form);
    
    CommandResult<GetSourceChoicesResult> getSourceChoices(UserVisitPK userVisitPK, GetSourceChoicesForm form);
    
    CommandResult<GetSourcesResult> getSources(UserVisitPK userVisitPK, GetSourcesForm form);
    
    CommandResult<GetSourceResult> getSource(UserVisitPK userVisitPK, GetSourceForm form);
    
    CommandResult<?> setDefaultSource(UserVisitPK userVisitPK, SetDefaultSourceForm form);
    
    CommandResult<EditSourceResult> editSource(UserVisitPK userVisitPK, EditSourceForm form);
    
    CommandResult<?> deleteSource(UserVisitPK userVisitPK, DeleteSourceForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Items
    // -------------------------------------------------------------------------
    
    CommandResult<?> createOfferItem(UserVisitPK userVisitPK, CreateOfferItemForm form);
    
    CommandResult<GetOfferItemsResult> getOfferItems(UserVisitPK userVisitPK, GetOfferItemsForm form);
    
    CommandResult<GetOfferItemResult> getOfferItem(UserVisitPK userVisitPK, GetOfferItemForm form);
    
    CommandResult<?> deleteOfferItem(UserVisitPK userVisitPK, DeleteOfferItemForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Item Prices
    // -------------------------------------------------------------------------
    
    CommandResult<?> createOfferItemPrice(UserVisitPK userVisitPK, CreateOfferItemPriceForm form);
    
    CommandResult<GetOfferItemPriceResult> getOfferItemPrice(UserVisitPK userVisitPK, GetOfferItemPriceForm form);

    CommandResult<GetOfferItemPricesResult> getOfferItemPrices(UserVisitPK userVisitPK, GetOfferItemPricesForm form);
    
    CommandResult<EditOfferItemPriceResult> editOfferItemPrice(UserVisitPK userVisitPK, EditOfferItemPriceForm form);
    
    CommandResult<?> deleteOfferItemPrice(UserVisitPK userVisitPK, DeleteOfferItemPriceForm form);
    
    // -------------------------------------------------------------------------
    //   Use Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createUseType(UserVisitPK userVisitPK, CreateUseTypeForm form);
    
    CommandResult<GetUseTypesResult> getUseTypes(UserVisitPK userVisitPK, GetUseTypesForm form);
    
    CommandResult<GetUseTypeResult> getUseType(UserVisitPK userVisitPK, GetUseTypeForm form);
    
    CommandResult<GetUseTypeChoicesResult> getUseTypeChoices(UserVisitPK userVisitPK, GetUseTypeChoicesForm form);
    
    CommandResult<?> setDefaultUseType(UserVisitPK userVisitPK, SetDefaultUseTypeForm form);
    
    CommandResult<EditUseTypeResult> editUseType(UserVisitPK userVisitPK, EditUseTypeForm form);
    
    CommandResult<?> deleteUseType(UserVisitPK userVisitPK, DeleteUseTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Use Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createUseTypeDescription(UserVisitPK userVisitPK, CreateUseTypeDescriptionForm form);

    CommandResult<GetUseTypeDescriptionsResult> getUseTypeDescriptions(UserVisitPK userVisitPK, GetUseTypeDescriptionsForm form);

    CommandResult<GetUseTypeDescriptionResult> getUseTypeDescription(UserVisitPK userVisitPK, GetUseTypeDescriptionForm form);

    CommandResult<EditUseTypeDescriptionResult> editUseTypeDescription(UserVisitPK userVisitPK, EditUseTypeDescriptionForm form);
    
    CommandResult<?> deleteUseTypeDescription(UserVisitPK userVisitPK, DeleteUseTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Use Name Elements
    // -------------------------------------------------------------------------
    
    CommandResult<?> createUseNameElement(UserVisitPK userVisitPK, CreateUseNameElementForm form);

    CommandResult<GetUseNameElementsResult> getUseNameElements(UserVisitPK userVisitPK, GetUseNameElementsForm form);

    CommandResult<GetUseNameElementResult> getUseNameElement(UserVisitPK userVisitPK, GetUseNameElementForm form);

    CommandResult<EditUseNameElementResult> editUseNameElement(UserVisitPK userVisitPK, EditUseNameElementForm form);
    
    CommandResult<?> deleteUseNameElement(UserVisitPK userVisitPK, DeleteUseNameElementForm form);
    
    // -------------------------------------------------------------------------
    //   Use Name Element Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createUseNameElementDescription(UserVisitPK userVisitPK, CreateUseNameElementDescriptionForm form);

    CommandResult<GetUseNameElementDescriptionsResult> getUseNameElementDescriptions(UserVisitPK userVisitPK, GetUseNameElementDescriptionsForm form);

    CommandResult<GetUseNameElementDescriptionResult> getUseNameElementDescription(UserVisitPK userVisitPK, GetUseNameElementDescriptionForm form);

    CommandResult<EditUseNameElementDescriptionResult> editUseNameElementDescription(UserVisitPK userVisitPK, EditUseNameElementDescriptionForm form);
    
    CommandResult<?> deleteUseNameElementDescription(UserVisitPK userVisitPK, DeleteUseNameElementDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Uses
    // -------------------------------------------------------------------------
    
    CommandResult<?> createUse(UserVisitPK userVisitPK, CreateUseForm form);
    
    CommandResult<GetUseChoicesResult> getUseChoices(UserVisitPK userVisitPK, GetUseChoicesForm form);
    
    CommandResult<GetUsesResult> getUses(UserVisitPK userVisitPK, GetUsesForm form);
    
    CommandResult<GetUseResult> getUse(UserVisitPK userVisitPK, GetUseForm form);
    
    CommandResult<?> setDefaultUse(UserVisitPK userVisitPK, SetDefaultUseForm form);
    
    CommandResult<EditUseResult> editUse(UserVisitPK userVisitPK, EditUseForm form);
    
    CommandResult<?> deleteUse(UserVisitPK userVisitPK, DeleteUseForm form);
    
    // -------------------------------------------------------------------------
    //   Use Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createUseDescription(UserVisitPK userVisitPK, CreateUseDescriptionForm form);
    
    CommandResult<GetUseDescriptionsResult> getUseDescriptions(UserVisitPK userVisitPK, GetUseDescriptionsForm form);
    
    CommandResult<EditUseDescriptionResult> editUseDescription(UserVisitPK userVisitPK, EditUseDescriptionForm form);
    
    CommandResult<?> deleteUseDescription(UserVisitPK userVisitPK, DeleteUseDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Name Elements
    // -------------------------------------------------------------------------
    
    CommandResult<?> createOfferNameElement(UserVisitPK userVisitPK, CreateOfferNameElementForm form);

    CommandResult<GetOfferNameElementsResult> getOfferNameElements(UserVisitPK userVisitPK, GetOfferNameElementsForm form);

    CommandResult<GetOfferNameElementResult> getOfferNameElement(UserVisitPK userVisitPK, GetOfferNameElementForm form);

    CommandResult<EditOfferNameElementResult> editOfferNameElement(UserVisitPK userVisitPK, EditOfferNameElementForm form);
    
    CommandResult<?> deleteOfferNameElement(UserVisitPK userVisitPK, DeleteOfferNameElementForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Name Element Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createOfferNameElementDescription(UserVisitPK userVisitPK, CreateOfferNameElementDescriptionForm form);

    CommandResult<GetOfferNameElementDescriptionsResult> getOfferNameElementDescriptions(UserVisitPK userVisitPK, GetOfferNameElementDescriptionsForm form);

    CommandResult<GetOfferNameElementDescriptionResult> getOfferNameElementDescription(UserVisitPK userVisitPK, GetOfferNameElementDescriptionForm form);

    CommandResult<EditOfferNameElementDescriptionResult> editOfferNameElementDescription(UserVisitPK userVisitPK, EditOfferNameElementDescriptionForm form);
    
    CommandResult<?> deleteOfferNameElementDescription(UserVisitPK userVisitPK, DeleteOfferNameElementDescriptionForm form);
    
}
