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
        return new CreateOfferCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getOffers(UserVisitPK userVisitPK, GetOffersForm form) {
        return new GetOffersCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getOffer(UserVisitPK userVisitPK, GetOfferForm form) {
        return new GetOfferCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getOfferChoices(UserVisitPK userVisitPK, GetOfferChoicesForm form) {
        return new GetOfferChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultOffer(UserVisitPK userVisitPK, SetDefaultOfferForm form) {
        return new SetDefaultOfferCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editOffer(UserVisitPK userVisitPK, EditOfferForm form) {
        return new EditOfferCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteOffer(UserVisitPK userVisitPK, DeleteOfferForm form) {
        return new DeleteOfferCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Offer Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferDescription(UserVisitPK userVisitPK, CreateOfferDescriptionForm form) {
        return new CreateOfferDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getOfferDescriptions(UserVisitPK userVisitPK, GetOfferDescriptionsForm form) {
        return new GetOfferDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editOfferDescription(UserVisitPK userVisitPK, EditOfferDescriptionForm form) {
        return new EditOfferDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteOfferDescription(UserVisitPK userVisitPK, DeleteOfferDescriptionForm form) {
        return new DeleteOfferDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Offer Customer Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferCustomerType(UserVisitPK userVisitPK, CreateOfferCustomerTypeForm form) {
        return new CreateOfferCustomerTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getOfferCustomerType(UserVisitPK userVisitPK, GetOfferCustomerTypeForm form) {
        return new GetOfferCustomerTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOfferCustomerTypes(UserVisitPK userVisitPK, GetOfferCustomerTypesForm form) {
        return new GetOfferCustomerTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultOfferCustomerType(UserVisitPK userVisitPK, SetDefaultOfferCustomerTypeForm form) {
        return new SetDefaultOfferCustomerTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOfferCustomerType(UserVisitPK userVisitPK, EditOfferCustomerTypeForm form) {
        return new EditOfferCustomerTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteOfferCustomerType(UserVisitPK userVisitPK, DeleteOfferCustomerTypeForm form) {
        return new DeleteOfferCustomerTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Offer Chain Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferChainType(UserVisitPK userVisitPK, CreateOfferChainTypeForm form) {
        return new CreateOfferChainTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOfferChainTypes(UserVisitPK userVisitPK, GetOfferChainTypesForm form) {
        return new GetOfferChainTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOfferChainType(UserVisitPK userVisitPK, GetOfferChainTypeForm form) {
        return new GetOfferChainTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOfferChainType(UserVisitPK userVisitPK, EditOfferChainTypeForm form) {
        return new EditOfferChainTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteOfferChainType(UserVisitPK userVisitPK, DeleteOfferChainTypeForm form) {
        return new DeleteOfferChainTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Offer Uses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferUse(UserVisitPK userVisitPK, CreateOfferUseForm form) {
        return new CreateOfferUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getOfferUses(UserVisitPK userVisitPK, GetOfferUsesForm form) {
        return new GetOfferUsesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getOfferUse(UserVisitPK userVisitPK, GetOfferUseForm form) {
        return new GetOfferUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editOfferUse(UserVisitPK userVisitPK, EditOfferUseForm form) {
        return new EditOfferUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteOfferUse(UserVisitPK userVisitPK, DeleteOfferUseForm form) {
        return new DeleteOfferUseCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Sources
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSource(UserVisitPK userVisitPK, CreateSourceForm form) {
        return new CreateSourceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSourceChoices(UserVisitPK userVisitPK, GetSourceChoicesForm form) {
        return new GetSourceChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSources(UserVisitPK userVisitPK, GetSourcesForm form) {
        return new GetSourcesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSource(UserVisitPK userVisitPK, GetSourceForm form) {
        return new GetSourceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultSource(UserVisitPK userVisitPK, SetDefaultSourceForm form) {
        return new SetDefaultSourceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSource(UserVisitPK userVisitPK, EditSourceForm form) {
        return new EditSourceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSource(UserVisitPK userVisitPK, DeleteSourceForm form) {
        return new DeleteSourceCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Offer Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferItem(UserVisitPK userVisitPK, CreateOfferItemForm form) {
        return new CreateOfferItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getOfferItems(UserVisitPK userVisitPK, GetOfferItemsForm form) {
        return new GetOfferItemsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getOfferItem(UserVisitPK userVisitPK, GetOfferItemForm form) {
        return new GetOfferItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteOfferItem(UserVisitPK userVisitPK, DeleteOfferItemForm form) {
        return new DeleteOfferItemCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Offer Item Prices
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferItemPrice(UserVisitPK userVisitPK, CreateOfferItemPriceForm form) {
        return new CreateOfferItemPriceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getOfferItemPrice(UserVisitPK userVisitPK, GetOfferItemPriceForm form) {
        return new GetOfferItemPriceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getOfferItemPrices(UserVisitPK userVisitPK, GetOfferItemPricesForm form) {
        return new GetOfferItemPricesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editOfferItemPrice(UserVisitPK userVisitPK, EditOfferItemPriceForm form) {
        return new EditOfferItemPriceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteOfferItemPrice(UserVisitPK userVisitPK, DeleteOfferItemPriceForm form) {
        return new DeleteOfferItemPriceCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Use Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUseType(UserVisitPK userVisitPK, CreateUseTypeForm form) {
        return new CreateUseTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUseTypes(UserVisitPK userVisitPK, GetUseTypesForm form) {
        return new GetUseTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUseType(UserVisitPK userVisitPK, GetUseTypeForm form) {
        return new GetUseTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUseTypeChoices(UserVisitPK userVisitPK, GetUseTypeChoicesForm form) {
        return new GetUseTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultUseType(UserVisitPK userVisitPK, SetDefaultUseTypeForm form) {
        return new SetDefaultUseTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editUseType(UserVisitPK userVisitPK, EditUseTypeForm form) {
        return new EditUseTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteUseType(UserVisitPK userVisitPK, DeleteUseTypeForm form) {
        return new DeleteUseTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Use Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUseTypeDescription(UserVisitPK userVisitPK, CreateUseTypeDescriptionForm form) {
        return new CreateUseTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUseTypeDescriptions(UserVisitPK userVisitPK, GetUseTypeDescriptionsForm form) {
        return new GetUseTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editUseTypeDescription(UserVisitPK userVisitPK, EditUseTypeDescriptionForm form) {
        return new EditUseTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteUseTypeDescription(UserVisitPK userVisitPK, DeleteUseTypeDescriptionForm form) {
        return new DeleteUseTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Use Name Elements
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUseNameElement(UserVisitPK userVisitPK, CreateUseNameElementForm form) {
        return new CreateUseNameElementCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUseNameElements(UserVisitPK userVisitPK, GetUseNameElementsForm form) {
        return new GetUseNameElementsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editUseNameElement(UserVisitPK userVisitPK, EditUseNameElementForm form) {
        return new EditUseNameElementCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteUseNameElement(UserVisitPK userVisitPK, DeleteUseNameElementForm form) {
        return new DeleteUseNameElementCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Use Name Element Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUseNameElementDescription(UserVisitPK userVisitPK, CreateUseNameElementDescriptionForm form) {
        return new CreateUseNameElementDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUseNameElementDescriptions(UserVisitPK userVisitPK, GetUseNameElementDescriptionsForm form) {
        return new GetUseNameElementDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editUseNameElementDescription(UserVisitPK userVisitPK, EditUseNameElementDescriptionForm form) {
        return new EditUseNameElementDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteUseNameElementDescription(UserVisitPK userVisitPK, DeleteUseNameElementDescriptionForm form) {
        return new DeleteUseNameElementDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Uses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUse(UserVisitPK userVisitPK, CreateUseForm form) {
        return new CreateUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUseChoices(UserVisitPK userVisitPK, GetUseChoicesForm form) {
        return new GetUseChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUses(UserVisitPK userVisitPK, GetUsesForm form) {
        return new GetUsesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUse(UserVisitPK userVisitPK, GetUseForm form) {
        return new GetUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultUse(UserVisitPK userVisitPK, SetDefaultUseForm form) {
        return new SetDefaultUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editUse(UserVisitPK userVisitPK, EditUseForm form) {
        return new EditUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteUse(UserVisitPK userVisitPK, DeleteUseForm form) {
        return new DeleteUseCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Use Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUseDescription(UserVisitPK userVisitPK, CreateUseDescriptionForm form) {
        return new CreateUseDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getUseDescriptions(UserVisitPK userVisitPK, GetUseDescriptionsForm form) {
        return new GetUseDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editUseDescription(UserVisitPK userVisitPK, EditUseDescriptionForm form) {
        return new EditUseDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteUseDescription(UserVisitPK userVisitPK, DeleteUseDescriptionForm form) {
        return new DeleteUseDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Offer Name Elements
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferNameElement(UserVisitPK userVisitPK, CreateOfferNameElementForm form) {
        return new CreateOfferNameElementCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getOfferNameElements(UserVisitPK userVisitPK, GetOfferNameElementsForm form) {
        return new GetOfferNameElementsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editOfferNameElement(UserVisitPK userVisitPK, EditOfferNameElementForm form) {
        return new EditOfferNameElementCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteOfferNameElement(UserVisitPK userVisitPK, DeleteOfferNameElementForm form) {
        return new DeleteOfferNameElementCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Offer Name Element Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferNameElementDescription(UserVisitPK userVisitPK, CreateOfferNameElementDescriptionForm form) {
        return new CreateOfferNameElementDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getOfferNameElementDescriptions(UserVisitPK userVisitPK, GetOfferNameElementDescriptionsForm form) {
        return new GetOfferNameElementDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editOfferNameElementDescription(UserVisitPK userVisitPK, EditOfferNameElementDescriptionForm form) {
        return new EditOfferNameElementDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteOfferNameElementDescription(UserVisitPK userVisitPK, DeleteOfferNameElementDescriptionForm form) {
        return new DeleteOfferNameElementDescriptionCommand(userVisitPK, form).run();
    }
    
}
