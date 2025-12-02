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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.form.GetOfferItemPricesForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetOfferItemPricesCommand
        extends BaseMultipleEntitiesCommand<OfferItemPrice, GetOfferItemPricesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferItemPrice.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, 20L),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetOfferItemPricesCommand */
    public GetOfferItemPricesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    private Offer offer;
    private Item item;
    private OfferItem offerItem;
    
    @Override
    protected Collection<OfferItemPrice> getEntities() {
        var offerControl = Session.getModelController(OfferControl.class);
        var offerName = form.getOfferName();
        Collection<OfferItemPrice> offerItemPrices = null;
        
        offer = offerControl.getOfferByName(offerName);
        
        if(offer != null) {
            var itemControl = Session.getModelController(ItemControl.class);
            var itemName = form.getItemName();
           
            item = itemControl.getItemByName(itemName);
            
            if(item != null) {
                var offerItemControl = Session.getModelController(OfferItemControl.class);
                offerItem = offerItemControl.getOfferItem(offer, item);
                
                if(offerItem != null) {
                    offerItemPrices = offerItemControl.getOfferItemPricesByOfferItem(offerItem);
                } else {
                    addExecutionError(ExecutionErrors.UnknownOfferItem.name(), offer.getLastDetail().getOfferName(),
                            item.getLastDetail().getItemName());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
        }
        
        return offerItemPrices;
    }
    
    @Override
    protected BaseResult getResult(Collection<OfferItemPrice> entities) {
        var result = OfferResultFactory.getGetOfferItemPricesResult();
        
        if (entities != null) {
            var offerControl = Session.getModelController(OfferControl.class);
            var offerItemControl = Session.getModelController(OfferItemControl.class);
            var itemControl = Session.getModelController(ItemControl.class);
            var userVisit = getUserVisit();
            
            result.setOffer(offerControl.getOfferTransfer(userVisit, offer));
            result.setItem(itemControl.getItemTransfer(userVisit, item));
            result.setOfferItem(offerItemControl.getOfferItemTransfer(userVisit, offerItem));
            result.setOfferItemPrices(offerItemControl.getOfferItemPriceTransfers(userVisit, entities));
        }
        
        return result;
    }
    
}
