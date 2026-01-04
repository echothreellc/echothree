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
    
    CommandResult createOffer(UserVisitPK userVisitPK, CreateOfferForm form);
    
    CommandResult getOffers(UserVisitPK userVisitPK, GetOffersForm form);
    
    CommandResult getOffer(UserVisitPK userVisitPK, GetOfferForm form);
    
    CommandResult getOfferChoices(UserVisitPK userVisitPK, GetOfferChoicesForm form);

    CommandResult setDefaultOffer(UserVisitPK userVisitPK, SetDefaultOfferForm form);
    
    CommandResult editOffer(UserVisitPK userVisitPK, EditOfferForm form);
    
    CommandResult deleteOffer(UserVisitPK userVisitPK, DeleteOfferForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createOfferDescription(UserVisitPK userVisitPK, CreateOfferDescriptionForm form);
    
    CommandResult getOfferDescriptions(UserVisitPK userVisitPK, GetOfferDescriptionsForm form);
    
    CommandResult editOfferDescription(UserVisitPK userVisitPK, EditOfferDescriptionForm form);
    
    CommandResult deleteOfferDescription(UserVisitPK userVisitPK, DeleteOfferDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Customer Types
    // -------------------------------------------------------------------------
    
    CommandResult createOfferCustomerType(UserVisitPK userVisitPK, CreateOfferCustomerTypeForm form);
    
    CommandResult getOfferCustomerType(UserVisitPK userVisitPK, GetOfferCustomerTypeForm form);

    CommandResult getOfferCustomerTypes(UserVisitPK userVisitPK, GetOfferCustomerTypesForm form);

    CommandResult setDefaultOfferCustomerType(UserVisitPK userVisitPK, SetDefaultOfferCustomerTypeForm form);

    CommandResult editOfferCustomerType(UserVisitPK userVisitPK, EditOfferCustomerTypeForm form);
    
    CommandResult deleteOfferCustomerType(UserVisitPK userVisitPK, DeleteOfferCustomerTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Chain Types
    // -------------------------------------------------------------------------
    
    CommandResult createOfferChainType(UserVisitPK userVisitPK, CreateOfferChainTypeForm form);

    CommandResult getOfferChainTypes(UserVisitPK userVisitPK, GetOfferChainTypesForm form);

    CommandResult getOfferChainType(UserVisitPK userVisitPK, GetOfferChainTypeForm form);

    CommandResult editOfferChainType(UserVisitPK userVisitPK, EditOfferChainTypeForm form);
    
    CommandResult deleteOfferChainType(UserVisitPK userVisitPK, DeleteOfferChainTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Uses
    // -------------------------------------------------------------------------
    
    CommandResult createOfferUse(UserVisitPK userVisitPK, CreateOfferUseForm form);
    
    CommandResult getOfferUses(UserVisitPK userVisitPK, GetOfferUsesForm form);
    
    CommandResult getOfferUse(UserVisitPK userVisitPK, GetOfferUseForm form);
    
    CommandResult editOfferUse(UserVisitPK userVisitPK, EditOfferUseForm form);
    
    CommandResult deleteOfferUse(UserVisitPK userVisitPK, DeleteOfferUseForm form);
    
    // -------------------------------------------------------------------------
    //   Sources
    // -------------------------------------------------------------------------
    
    CommandResult createSource(UserVisitPK userVisitPK, CreateSourceForm form);
    
    CommandResult getSourceChoices(UserVisitPK userVisitPK, GetSourceChoicesForm form);
    
    CommandResult getSources(UserVisitPK userVisitPK, GetSourcesForm form);
    
    CommandResult getSource(UserVisitPK userVisitPK, GetSourceForm form);
    
    CommandResult setDefaultSource(UserVisitPK userVisitPK, SetDefaultSourceForm form);
    
    CommandResult editSource(UserVisitPK userVisitPK, EditSourceForm form);
    
    CommandResult deleteSource(UserVisitPK userVisitPK, DeleteSourceForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Items
    // -------------------------------------------------------------------------
    
    CommandResult createOfferItem(UserVisitPK userVisitPK, CreateOfferItemForm form);
    
    CommandResult getOfferItems(UserVisitPK userVisitPK, GetOfferItemsForm form);
    
    CommandResult getOfferItem(UserVisitPK userVisitPK, GetOfferItemForm form);
    
    CommandResult deleteOfferItem(UserVisitPK userVisitPK, DeleteOfferItemForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Item Prices
    // -------------------------------------------------------------------------
    
    CommandResult createOfferItemPrice(UserVisitPK userVisitPK, CreateOfferItemPriceForm form);
    
    CommandResult getOfferItemPrice(UserVisitPK userVisitPK, GetOfferItemPriceForm form);

    CommandResult getOfferItemPrices(UserVisitPK userVisitPK, GetOfferItemPricesForm form);
    
    CommandResult editOfferItemPrice(UserVisitPK userVisitPK, EditOfferItemPriceForm form);
    
    CommandResult deleteOfferItemPrice(UserVisitPK userVisitPK, DeleteOfferItemPriceForm form);
    
    // -------------------------------------------------------------------------
    //   Use Types
    // -------------------------------------------------------------------------
    
    CommandResult createUseType(UserVisitPK userVisitPK, CreateUseTypeForm form);
    
    CommandResult getUseTypes(UserVisitPK userVisitPK, GetUseTypesForm form);
    
    CommandResult getUseType(UserVisitPK userVisitPK, GetUseTypeForm form);
    
    CommandResult getUseTypeChoices(UserVisitPK userVisitPK, GetUseTypeChoicesForm form);
    
    CommandResult setDefaultUseType(UserVisitPK userVisitPK, SetDefaultUseTypeForm form);
    
    CommandResult editUseType(UserVisitPK userVisitPK, EditUseTypeForm form);
    
    CommandResult deleteUseType(UserVisitPK userVisitPK, DeleteUseTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Use Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createUseTypeDescription(UserVisitPK userVisitPK, CreateUseTypeDescriptionForm form);

    CommandResult getUseTypeDescriptions(UserVisitPK userVisitPK, GetUseTypeDescriptionsForm form);

    CommandResult getUseTypeDescription(UserVisitPK userVisitPK, GetUseTypeDescriptionForm form);

    CommandResult editUseTypeDescription(UserVisitPK userVisitPK, EditUseTypeDescriptionForm form);
    
    CommandResult deleteUseTypeDescription(UserVisitPK userVisitPK, DeleteUseTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Use Name Elements
    // -------------------------------------------------------------------------
    
    CommandResult createUseNameElement(UserVisitPK userVisitPK, CreateUseNameElementForm form);

    CommandResult getUseNameElements(UserVisitPK userVisitPK, GetUseNameElementsForm form);

    CommandResult getUseNameElement(UserVisitPK userVisitPK, GetUseNameElementForm form);

    CommandResult editUseNameElement(UserVisitPK userVisitPK, EditUseNameElementForm form);
    
    CommandResult deleteUseNameElement(UserVisitPK userVisitPK, DeleteUseNameElementForm form);
    
    // -------------------------------------------------------------------------
    //   Use Name Element Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createUseNameElementDescription(UserVisitPK userVisitPK, CreateUseNameElementDescriptionForm form);

    CommandResult getUseNameElementDescriptions(UserVisitPK userVisitPK, GetUseNameElementDescriptionsForm form);

    CommandResult getUseNameElementDescription(UserVisitPK userVisitPK, GetUseNameElementDescriptionForm form);

    CommandResult editUseNameElementDescription(UserVisitPK userVisitPK, EditUseNameElementDescriptionForm form);
    
    CommandResult deleteUseNameElementDescription(UserVisitPK userVisitPK, DeleteUseNameElementDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Uses
    // -------------------------------------------------------------------------
    
    CommandResult createUse(UserVisitPK userVisitPK, CreateUseForm form);
    
    CommandResult getUseChoices(UserVisitPK userVisitPK, GetUseChoicesForm form);
    
    CommandResult getUses(UserVisitPK userVisitPK, GetUsesForm form);
    
    CommandResult getUse(UserVisitPK userVisitPK, GetUseForm form);
    
    CommandResult setDefaultUse(UserVisitPK userVisitPK, SetDefaultUseForm form);
    
    CommandResult editUse(UserVisitPK userVisitPK, EditUseForm form);
    
    CommandResult deleteUse(UserVisitPK userVisitPK, DeleteUseForm form);
    
    // -------------------------------------------------------------------------
    //   Use Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createUseDescription(UserVisitPK userVisitPK, CreateUseDescriptionForm form);
    
    CommandResult getUseDescriptions(UserVisitPK userVisitPK, GetUseDescriptionsForm form);
    
    CommandResult editUseDescription(UserVisitPK userVisitPK, EditUseDescriptionForm form);
    
    CommandResult deleteUseDescription(UserVisitPK userVisitPK, DeleteUseDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Name Elements
    // -------------------------------------------------------------------------
    
    CommandResult createOfferNameElement(UserVisitPK userVisitPK, CreateOfferNameElementForm form);

    CommandResult getOfferNameElements(UserVisitPK userVisitPK, GetOfferNameElementsForm form);

    CommandResult getOfferNameElement(UserVisitPK userVisitPK, GetOfferNameElementForm form);

    CommandResult editOfferNameElement(UserVisitPK userVisitPK, EditOfferNameElementForm form);
    
    CommandResult deleteOfferNameElement(UserVisitPK userVisitPK, DeleteOfferNameElementForm form);
    
    // -------------------------------------------------------------------------
    //   Offer Name Element Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createOfferNameElementDescription(UserVisitPK userVisitPK, CreateOfferNameElementDescriptionForm form);

    CommandResult getOfferNameElementDescriptions(UserVisitPK userVisitPK, GetOfferNameElementDescriptionsForm form);

    CommandResult getOfferNameElementDescription(UserVisitPK userVisitPK, GetOfferNameElementDescriptionForm form);

    CommandResult editOfferNameElementDescription(UserVisitPK userVisitPK, EditOfferNameElementDescriptionForm form);
    
    CommandResult deleteOfferNameElementDescription(UserVisitPK userVisitPK, DeleteOfferNameElementDescriptionForm form);
    
}
