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

package com.echothree.control.user.offer.server;

import com.echothree.control.user.offer.common.OfferRemote;
import com.echothree.control.user.offer.common.form.*;
import com.echothree.control.user.offer.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateOfferCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOffers(UserVisitPK userVisitPK, GetOffersForm form) {
        return CDI.current().select(GetOffersCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOffer(UserVisitPK userVisitPK, GetOfferForm form) {
        return CDI.current().select(GetOfferCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferChoices(UserVisitPK userVisitPK, GetOfferChoicesForm form) {
        return CDI.current().select(GetOfferChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOffer(UserVisitPK userVisitPK, SetDefaultOfferForm form) {
        return CDI.current().select(SetDefaultOfferCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editOffer(UserVisitPK userVisitPK, EditOfferForm form) {
        return CDI.current().select(EditOfferCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOffer(UserVisitPK userVisitPK, DeleteOfferForm form) {
        return CDI.current().select(DeleteOfferCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferDescription(UserVisitPK userVisitPK, CreateOfferDescriptionForm form) {
        return CDI.current().select(CreateOfferDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferDescriptions(UserVisitPK userVisitPK, GetOfferDescriptionsForm form) {
        return CDI.current().select(GetOfferDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editOfferDescription(UserVisitPK userVisitPK, EditOfferDescriptionForm form) {
        return CDI.current().select(EditOfferDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOfferDescription(UserVisitPK userVisitPK, DeleteOfferDescriptionForm form) {
        return CDI.current().select(DeleteOfferDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Customer Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferCustomerType(UserVisitPK userVisitPK, CreateOfferCustomerTypeForm form) {
        return CDI.current().select(CreateOfferCustomerTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferCustomerType(UserVisitPK userVisitPK, GetOfferCustomerTypeForm form) {
        return CDI.current().select(GetOfferCustomerTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferCustomerTypes(UserVisitPK userVisitPK, GetOfferCustomerTypesForm form) {
        return CDI.current().select(GetOfferCustomerTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultOfferCustomerType(UserVisitPK userVisitPK, SetDefaultOfferCustomerTypeForm form) {
        return CDI.current().select(SetDefaultOfferCustomerTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOfferCustomerType(UserVisitPK userVisitPK, EditOfferCustomerTypeForm form) {
        return CDI.current().select(EditOfferCustomerTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteOfferCustomerType(UserVisitPK userVisitPK, DeleteOfferCustomerTypeForm form) {
        return CDI.current().select(DeleteOfferCustomerTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Chain Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferChainType(UserVisitPK userVisitPK, CreateOfferChainTypeForm form) {
        return CDI.current().select(CreateOfferChainTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferChainTypes(UserVisitPK userVisitPK, GetOfferChainTypesForm form) {
        return CDI.current().select(GetOfferChainTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferChainType(UserVisitPK userVisitPK, GetOfferChainTypeForm form) {
        return CDI.current().select(GetOfferChainTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOfferChainType(UserVisitPK userVisitPK, EditOfferChainTypeForm form) {
        return CDI.current().select(EditOfferChainTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOfferChainType(UserVisitPK userVisitPK, DeleteOfferChainTypeForm form) {
        return CDI.current().select(DeleteOfferChainTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Uses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferUse(UserVisitPK userVisitPK, CreateOfferUseForm form) {
        return CDI.current().select(CreateOfferUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferUses(UserVisitPK userVisitPK, GetOfferUsesForm form) {
        return CDI.current().select(GetOfferUsesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferUse(UserVisitPK userVisitPK, GetOfferUseForm form) {
        return CDI.current().select(GetOfferUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editOfferUse(UserVisitPK userVisitPK, EditOfferUseForm form) {
        return CDI.current().select(EditOfferUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOfferUse(UserVisitPK userVisitPK, DeleteOfferUseForm form) {
        return CDI.current().select(DeleteOfferUseCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Sources
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSource(UserVisitPK userVisitPK, CreateSourceForm form) {
        return CDI.current().select(CreateSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSourceChoices(UserVisitPK userVisitPK, GetSourceChoicesForm form) {
        return CDI.current().select(GetSourceChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSources(UserVisitPK userVisitPK, GetSourcesForm form) {
        return CDI.current().select(GetSourcesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSource(UserVisitPK userVisitPK, GetSourceForm form) {
        return CDI.current().select(GetSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSource(UserVisitPK userVisitPK, SetDefaultSourceForm form) {
        return CDI.current().select(SetDefaultSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSource(UserVisitPK userVisitPK, EditSourceForm form) {
        return CDI.current().select(EditSourceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSource(UserVisitPK userVisitPK, DeleteSourceForm form) {
        return CDI.current().select(DeleteSourceCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferItem(UserVisitPK userVisitPK, CreateOfferItemForm form) {
        return CDI.current().select(CreateOfferItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferItems(UserVisitPK userVisitPK, GetOfferItemsForm form) {
        return CDI.current().select(GetOfferItemsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferItem(UserVisitPK userVisitPK, GetOfferItemForm form) {
        return CDI.current().select(GetOfferItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOfferItem(UserVisitPK userVisitPK, DeleteOfferItemForm form) {
        return CDI.current().select(DeleteOfferItemCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Item Prices
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferItemPrice(UserVisitPK userVisitPK, CreateOfferItemPriceForm form) {
        return CDI.current().select(CreateOfferItemPriceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getOfferItemPrice(UserVisitPK userVisitPK, GetOfferItemPriceForm form) {
        return CDI.current().select(GetOfferItemPriceCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferItemPrices(UserVisitPK userVisitPK, GetOfferItemPricesForm form) {
        return CDI.current().select(GetOfferItemPricesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOfferItemPrice(UserVisitPK userVisitPK, EditOfferItemPriceForm form) {
        return CDI.current().select(EditOfferItemPriceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOfferItemPrice(UserVisitPK userVisitPK, DeleteOfferItemPriceForm form) {
        return CDI.current().select(DeleteOfferItemPriceCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Use Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUseType(UserVisitPK userVisitPK, CreateUseTypeForm form) {
        return CDI.current().select(CreateUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseTypes(UserVisitPK userVisitPK, GetUseTypesForm form) {
        return CDI.current().select(GetUseTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseType(UserVisitPK userVisitPK, GetUseTypeForm form) {
        return CDI.current().select(GetUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseTypeChoices(UserVisitPK userVisitPK, GetUseTypeChoicesForm form) {
        return CDI.current().select(GetUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultUseType(UserVisitPK userVisitPK, SetDefaultUseTypeForm form) {
        return CDI.current().select(SetDefaultUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUseType(UserVisitPK userVisitPK, EditUseTypeForm form) {
        return CDI.current().select(EditUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUseType(UserVisitPK userVisitPK, DeleteUseTypeForm form) {
        return CDI.current().select(DeleteUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Use Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUseTypeDescription(UserVisitPK userVisitPK, CreateUseTypeDescriptionForm form) {
        return CDI.current().select(CreateUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getUseTypeDescriptions(UserVisitPK userVisitPK, GetUseTypeDescriptionsForm form) {
        return CDI.current().select(GetUseTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getUseTypeDescription(UserVisitPK userVisitPK, GetUseTypeDescriptionForm form) {
        return CDI.current().select(GetUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editUseTypeDescription(UserVisitPK userVisitPK, EditUseTypeDescriptionForm form) {
        return CDI.current().select(EditUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUseTypeDescription(UserVisitPK userVisitPK, DeleteUseTypeDescriptionForm form) {
        return CDI.current().select(DeleteUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Use Name Elements
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUseNameElement(UserVisitPK userVisitPK, CreateUseNameElementForm form) {
        return CDI.current().select(CreateUseNameElementCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getUseNameElements(UserVisitPK userVisitPK, GetUseNameElementsForm form) {
        return CDI.current().select(GetUseNameElementsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getUseNameElement(UserVisitPK userVisitPK, GetUseNameElementForm form) {
        return CDI.current().select(GetUseNameElementCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editUseNameElement(UserVisitPK userVisitPK, EditUseNameElementForm form) {
        return CDI.current().select(EditUseNameElementCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUseNameElement(UserVisitPK userVisitPK, DeleteUseNameElementForm form) {
        return CDI.current().select(DeleteUseNameElementCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Use Name Element Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUseNameElementDescription(UserVisitPK userVisitPK, CreateUseNameElementDescriptionForm form) {
        return CDI.current().select(CreateUseNameElementDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getUseNameElementDescriptions(UserVisitPK userVisitPK, GetUseNameElementDescriptionsForm form) {
        return CDI.current().select(GetUseNameElementDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getUseNameElementDescription(UserVisitPK userVisitPK, GetUseNameElementDescriptionForm form) {
        return CDI.current().select(GetUseNameElementDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editUseNameElementDescription(UserVisitPK userVisitPK, EditUseNameElementDescriptionForm form) {
        return CDI.current().select(EditUseNameElementDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUseNameElementDescription(UserVisitPK userVisitPK, DeleteUseNameElementDescriptionForm form) {
        return CDI.current().select(DeleteUseNameElementDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Uses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUse(UserVisitPK userVisitPK, CreateUseForm form) {
        return CDI.current().select(CreateUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseChoices(UserVisitPK userVisitPK, GetUseChoicesForm form) {
        return CDI.current().select(GetUseChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUses(UserVisitPK userVisitPK, GetUsesForm form) {
        return CDI.current().select(GetUsesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUse(UserVisitPK userVisitPK, GetUseForm form) {
        return CDI.current().select(GetUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultUse(UserVisitPK userVisitPK, SetDefaultUseForm form) {
        return CDI.current().select(SetDefaultUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUse(UserVisitPK userVisitPK, EditUseForm form) {
        return CDI.current().select(EditUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUse(UserVisitPK userVisitPK, DeleteUseForm form) {
        return CDI.current().select(DeleteUseCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Use Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createUseDescription(UserVisitPK userVisitPK, CreateUseDescriptionForm form) {
        return CDI.current().select(CreateUseDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getUseDescriptions(UserVisitPK userVisitPK, GetUseDescriptionsForm form) {
        return CDI.current().select(GetUseDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editUseDescription(UserVisitPK userVisitPK, EditUseDescriptionForm form) {
        return CDI.current().select(EditUseDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteUseDescription(UserVisitPK userVisitPK, DeleteUseDescriptionForm form) {
        return CDI.current().select(DeleteUseDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Name Elements
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferNameElement(UserVisitPK userVisitPK, CreateOfferNameElementForm form) {
        return CDI.current().select(CreateOfferNameElementCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferNameElements(UserVisitPK userVisitPK, GetOfferNameElementsForm form) {
        return CDI.current().select(GetOfferNameElementsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferNameElement(UserVisitPK userVisitPK, GetOfferNameElementForm form) {
        return CDI.current().select(GetOfferNameElementCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOfferNameElement(UserVisitPK userVisitPK, EditOfferNameElementForm form) {
        return CDI.current().select(EditOfferNameElementCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOfferNameElement(UserVisitPK userVisitPK, DeleteOfferNameElementForm form) {
        return CDI.current().select(DeleteOfferNameElementCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Offer Name Element Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createOfferNameElementDescription(UserVisitPK userVisitPK, CreateOfferNameElementDescriptionForm form) {
        return CDI.current().select(CreateOfferNameElementDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferNameElementDescriptions(UserVisitPK userVisitPK, GetOfferNameElementDescriptionsForm form) {
        return CDI.current().select(GetOfferNameElementDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getOfferNameElementDescription(UserVisitPK userVisitPK, GetOfferNameElementDescriptionForm form) {
        return CDI.current().select(GetOfferNameElementDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editOfferNameElementDescription(UserVisitPK userVisitPK, EditOfferNameElementDescriptionForm form) {
        return CDI.current().select(EditOfferNameElementDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteOfferNameElementDescription(UserVisitPK userVisitPK, DeleteOfferNameElementDescriptionForm form) {
        return CDI.current().select(DeleteOfferNameElementDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
}
