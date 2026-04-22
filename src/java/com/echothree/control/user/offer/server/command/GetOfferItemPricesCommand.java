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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.form.GetOfferItemPricesForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.factory.OfferItemPriceFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetOfferItemPricesCommand
        extends BasePaginatedMultipleEntitiesCommand<OfferItemPrice, GetOfferItemPricesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferItemPrice.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, 20L),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null)
        );
    }
    
    /** Creates a new instance of GetOfferItemPricesCommand */
    public GetOfferItemPricesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Inject
    ItemControl itemControl;

    @Inject
    OfferControl offerControl;

    @Inject
    OfferItemControl offerItemControl;

    private OfferItem offerItem;

    @Override
    protected void handleForm() {
        var offerName = form.getOfferName();
        var offer = offerControl.getOfferByName(offerName);

        if(offer != null) {
            var itemName = form.getItemName();
            var item = itemControl.getItemByName(itemName);

            if(item != null) {
                offerItem = offerItemControl.getOfferItem(offer, item);

                if(offerItem == null) {
                    addExecutionError(ExecutionErrors.UnknownOfferItem.name(), offer.getLastDetail().getOfferName(),
                            item.getLastDetail().getItemName());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return offerItem != null ? offerItemControl.countOfferItemPricesByOfferItem(offerItem) : null;
    }

    @Override
    protected Collection<OfferItemPrice> getEntities() {
        return offerItem != null ? offerItemControl.getOfferItemPricesByOfferItem(offerItem) : null;
    }
    
    @Override
    protected BaseResult getResult(Collection<OfferItemPrice> entities) {
        var result = OfferResultFactory.getGetOfferItemPricesResult();
        
        if (entities != null) {
            var userVisit = getUserVisit();
            
            result.setOfferItem(offerItemControl.getOfferItemTransfer(userVisit, offerItem));

            if(session.hasLimit(OfferItemPriceFactory.class)) {
                result.setOfferItemPriceCount(getTotalEntities());
            }

            result.setOfferItemPrices(offerItemControl.getOfferItemPriceTransfers(userVisit, entities));
        }
        
        return result;
    }
    
}
