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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.form.GetOfferItemsForm;
import com.echothree.control.user.offer.common.result.GetOfferItemsResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.factory.OfferItemFactory;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
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

public class GetOfferItemsCommand
        extends BaseMultipleEntitiesCommand<OfferItem, GetOfferItemsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.OfferItem.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, 20L)
                ));
    }
    
    /** Creates a new instance of GetOfferItemsCommand */
    public GetOfferItemsCommand(UserVisitPK userVisitPK, GetOfferItemsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    private Offer offer;
    
    @Override
    protected Collection<OfferItem> getEntities() {
        var offerControl = Session.getModelController(OfferControl.class);
        String offerName = form.getOfferName();
        Collection<OfferItem> offerItems = null;
        
        offer = offerControl.getOfferByName(offerName);
        
        if(offer != null) {
            var offerItemControl = Session.getModelController(OfferItemControl.class);
            offerItems = offerItemControl.getOfferItemsByOffer(offer);
        } else {
            addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
        }
        
        return offerItems;
    }
    
    @Override
    protected BaseResult getTransfers(Collection<OfferItem> entities) {
        GetOfferItemsResult result = OfferResultFactory.getGetOfferItemsResult();
        
        if(entities != null) {
            var offerControl = Session.getModelController(OfferControl.class);
            var offerItemControl = Session.getModelController(OfferItemControl.class);
            UserVisit userVisit = getUserVisit();
            
            if(session.hasLimit(OfferItemFactory.class)) {
                result.setOfferItemCount(offerItemControl.countOfferItemsByOffer(offer));
            }

            result.setOffer(offerControl.getOfferTransfer(userVisit, offer));
            result.setOfferItems(offerItemControl.getOfferItemTransfers(userVisit, entities));
        }
        
        return result;
    }
    
}
