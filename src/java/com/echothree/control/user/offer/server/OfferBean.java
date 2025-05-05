// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.control.user.offer.server;

import com.echothree.control.user.offer.common.OfferRemote;
import com.echothree.control.user.offer.common.form.*;
import com.echothree.control.user.offer.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class OfferBean
        extends OfferFormsImpl
        implements OfferRemote, OfferLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "OfferBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Offers
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOffer(UserVisitPK userVisitPK, CreateOfferForm form) {
        return new CreateOfferCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOffers(UserVisitPK userVisitPK, GetOffersForm form) {
        return new GetOffersCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOffer(UserVisitPK userVisitPK, GetOfferForm form) {
        return new GetOfferCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferChoices(UserVisitPK userVisitPK, GetOfferChoicesForm form) {
        return new GetOfferChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOffer(UserVisitPK userVisitPK, SetDefaultOfferForm form) {
        return new SetDefaultOfferCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editOffer(UserVisitPK userVisitPK, EditOfferForm form) {
        return new EditOfferCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOffer(UserVisitPK userVisitPK, DeleteOfferForm form) {
        return new DeleteOfferCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferDescription(UserVisitPK userVisitPK, CreateOfferDescriptionForm form) {
        return new CreateOfferDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferDescriptions(UserVisitPK userVisitPK, GetOfferDescriptionsForm form) {
        return new GetOfferDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editOfferDescription(UserVisitPK userVisitPK, EditOfferDescriptionForm form) {
        return new EditOfferDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOfferDescription(UserVisitPK userVisitPK, DeleteOfferDescriptionForm form) {
        return new DeleteOfferDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Customer Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferCustomerType(UserVisitPK userVisitPK, CreateOfferCustomerTypeForm form) {
        return new CreateOfferCustomerTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferCustomerType(UserVisitPK userVisitPK, GetOfferCustomerTypeForm form) {
        return new GetOfferCustomerTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferCustomerTypes(UserVisitPK userVisitPK, GetOfferCustomerTypesForm form) {
        return new GetOfferCustomerTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOfferCustomerType(UserVisitPK userVisitPK, SetDefaultOfferCustomerTypeForm form) {
        return new SetDefaultOfferCustomerTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOfferCustomerType(UserVisitPK userVisitPK, EditOfferCustomerTypeForm form) {
        return new EditOfferCustomerTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOfferCustomerType(UserVisitPK userVisitPK, DeleteOfferCustomerTypeForm form) {
        return new DeleteOfferCustomerTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Chain Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferChainType(UserVisitPK userVisitPK, CreateOfferChainTypeForm form) {
        return new CreateOfferChainTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferChainTypes(UserVisitPK userVisitPK, GetOfferChainTypesForm form) {
        return new GetOfferChainTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferChainType(UserVisitPK userVisitPK, GetOfferChainTypeForm form) {
        return new GetOfferChainTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOfferChainType(UserVisitPK userVisitPK, EditOfferChainTypeForm form) {
        return new EditOfferChainTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOfferChainType(UserVisitPK userVisitPK, DeleteOfferChainTypeForm form) {
        return new DeleteOfferChainTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Uses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferUse(UserVisitPK userVisitPK, CreateOfferUseForm form) {
        return new CreateOfferUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferUses(UserVisitPK userVisitPK, GetOfferUsesForm form) {
        return new GetOfferUsesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferUse(UserVisitPK userVisitPK, GetOfferUseForm form) {
        return new GetOfferUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editOfferUse(UserVisitPK userVisitPK, EditOfferUseForm form) {
        return new EditOfferUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOfferUse(UserVisitPK userVisitPK, DeleteOfferUseForm form) {
        return new DeleteOfferUseCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sources
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSource(UserVisitPK userVisitPK, CreateSourceForm form) {
        return new CreateSourceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSourceChoices(UserVisitPK userVisitPK, GetSourceChoicesForm form) {
        return new GetSourceChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSources(UserVisitPK userVisitPK, GetSourcesForm form) {
        return new GetSourcesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSource(UserVisitPK userVisitPK, GetSourceForm form) {
        return new GetSourceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSource(UserVisitPK userVisitPK, SetDefaultSourceForm form) {
        return new SetDefaultSourceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSource(UserVisitPK userVisitPK, EditSourceForm form) {
        return new EditSourceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSource(UserVisitPK userVisitPK, DeleteSourceForm form) {
        return new DeleteSourceCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferItem(UserVisitPK userVisitPK, CreateOfferItemForm form) {
        return new CreateOfferItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferItems(UserVisitPK userVisitPK, GetOfferItemsForm form) {
        return new GetOfferItemsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferItem(UserVisitPK userVisitPK, GetOfferItemForm form) {
        return new GetOfferItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOfferItem(UserVisitPK userVisitPK, DeleteOfferItemForm form) {
        return new DeleteOfferItemCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Item Prices
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferItemPrice(UserVisitPK userVisitPK, CreateOfferItemPriceForm form) {
        return new CreateOfferItemPriceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferItemPrice(UserVisitPK userVisitPK, GetOfferItemPriceForm form) {
        return new GetOfferItemPriceCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferItemPrices(UserVisitPK userVisitPK, GetOfferItemPricesForm form) {
        return new GetOfferItemPricesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOfferItemPrice(UserVisitPK userVisitPK, EditOfferItemPriceForm form) {
        return new EditOfferItemPriceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOfferItemPrice(UserVisitPK userVisitPK, DeleteOfferItemPriceForm form) {
        return new DeleteOfferItemPriceCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Use Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUseType(UserVisitPK userVisitPK, CreateUseTypeForm form) {
        return new CreateUseTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseTypes(UserVisitPK userVisitPK, GetUseTypesForm form) {
        return new GetUseTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseType(UserVisitPK userVisitPK, GetUseTypeForm form) {
        return new GetUseTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseTypeChoices(UserVisitPK userVisitPK, GetUseTypeChoicesForm form) {
        return new GetUseTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultUseType(UserVisitPK userVisitPK, SetDefaultUseTypeForm form) {
        return new SetDefaultUseTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUseType(UserVisitPK userVisitPK, EditUseTypeForm form) {
        return new EditUseTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUseType(UserVisitPK userVisitPK, DeleteUseTypeForm form) {
        return new DeleteUseTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Use Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUseTypeDescription(UserVisitPK userVisitPK, CreateUseTypeDescriptionForm form) {
        return new CreateUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getUseTypeDescriptions(UserVisitPK userVisitPK, GetUseTypeDescriptionsForm form) {
        return new GetUseTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getUseTypeDescription(UserVisitPK userVisitPK, GetUseTypeDescriptionForm form) {
        return new GetUseTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editUseTypeDescription(UserVisitPK userVisitPK, EditUseTypeDescriptionForm form) {
        return new EditUseTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUseTypeDescription(UserVisitPK userVisitPK, DeleteUseTypeDescriptionForm form) {
        return new DeleteUseTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Use Name Elements
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUseNameElement(UserVisitPK userVisitPK, CreateUseNameElementForm form) {
        return new CreateUseNameElementCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getUseNameElements(UserVisitPK userVisitPK, GetUseNameElementsForm form) {
        return new GetUseNameElementsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getUseNameElement(UserVisitPK userVisitPK, GetUseNameElementForm form) {
        return new GetUseNameElementCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editUseNameElement(UserVisitPK userVisitPK, EditUseNameElementForm form) {
        return new EditUseNameElementCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUseNameElement(UserVisitPK userVisitPK, DeleteUseNameElementForm form) {
        return new DeleteUseNameElementCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Use Name Element Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUseNameElementDescription(UserVisitPK userVisitPK, CreateUseNameElementDescriptionForm form) {
        return new CreateUseNameElementDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getUseNameElementDescriptions(UserVisitPK userVisitPK, GetUseNameElementDescriptionsForm form) {
        return new GetUseNameElementDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getUseNameElementDescription(UserVisitPK userVisitPK, GetUseNameElementDescriptionForm form) {
        return new GetUseNameElementDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editUseNameElementDescription(UserVisitPK userVisitPK, EditUseNameElementDescriptionForm form) {
        return new EditUseNameElementDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUseNameElementDescription(UserVisitPK userVisitPK, DeleteUseNameElementDescriptionForm form) {
        return new DeleteUseNameElementDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Uses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUse(UserVisitPK userVisitPK, CreateUseForm form) {
        return new CreateUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseChoices(UserVisitPK userVisitPK, GetUseChoicesForm form) {
        return new GetUseChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUses(UserVisitPK userVisitPK, GetUsesForm form) {
        return new GetUsesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUse(UserVisitPK userVisitPK, GetUseForm form) {
        return new GetUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultUse(UserVisitPK userVisitPK, SetDefaultUseForm form) {
        return new SetDefaultUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUse(UserVisitPK userVisitPK, EditUseForm form) {
        return new EditUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUse(UserVisitPK userVisitPK, DeleteUseForm form) {
        return new DeleteUseCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Use Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUseDescription(UserVisitPK userVisitPK, CreateUseDescriptionForm form) {
        return new CreateUseDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseDescriptions(UserVisitPK userVisitPK, GetUseDescriptionsForm form) {
        return new GetUseDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUseDescription(UserVisitPK userVisitPK, EditUseDescriptionForm form) {
        return new EditUseDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUseDescription(UserVisitPK userVisitPK, DeleteUseDescriptionForm form) {
        return new DeleteUseDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Name Elements
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferNameElement(UserVisitPK userVisitPK, CreateOfferNameElementForm form) {
        return new CreateOfferNameElementCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferNameElements(UserVisitPK userVisitPK, GetOfferNameElementsForm form) {
        return new GetOfferNameElementsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferNameElement(UserVisitPK userVisitPK, GetOfferNameElementForm form) {
        return new GetOfferNameElementCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOfferNameElement(UserVisitPK userVisitPK, EditOfferNameElementForm form) {
        return new EditOfferNameElementCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOfferNameElement(UserVisitPK userVisitPK, DeleteOfferNameElementForm form) {
        return new DeleteOfferNameElementCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Name Element Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferNameElementDescription(UserVisitPK userVisitPK, CreateOfferNameElementDescriptionForm form) {
        return new CreateOfferNameElementDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferNameElementDescriptions(UserVisitPK userVisitPK, GetOfferNameElementDescriptionsForm form) {
        return new GetOfferNameElementDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferNameElementDescription(UserVisitPK userVisitPK, GetOfferNameElementDescriptionForm form) {
        return new GetOfferNameElementDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOfferNameElementDescription(UserVisitPK userVisitPK, EditOfferNameElementDescriptionForm form) {
        return new EditOfferNameElementDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOfferNameElementDescription(UserVisitPK userVisitPK, DeleteOfferNameElementDescriptionForm form) {
        return new DeleteOfferNameElementDescriptionCommand().run(userVisitPK, form);
    }
    
}
