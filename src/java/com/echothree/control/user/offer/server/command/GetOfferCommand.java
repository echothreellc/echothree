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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.form.GetOfferForm;
import com.echothree.control.user.offer.common.result.GetOfferResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetOfferCommand
        extends BaseSingleEntityCommand<Offer, GetOfferForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Offer.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("OfferName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetOfferCommand */
    public GetOfferCommand(UserVisitPK userVisitPK, GetOfferForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Offer getEntity() {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        String offerName = form.getOfferName();
        Offer offer = offerControl.getOfferByName(offerName);
        
        if(offer != null) {
            sendEventUsingNames(offer.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownOfferName.name(), offerName);
        }
        
        return offer;
    }
    
    @Override
    protected BaseResult getTransfer(Offer offer) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        GetOfferResult result = OfferResultFactory.getGetOfferResult();
        
        if(offer != null) {
            UserVisit userVisit = getUserVisit();
            
            result.setOffer(offerControl.getOfferTransfer(userVisit, offer));
            result.setOfferNameElements(offerControl.getOfferNameElementTransfers(userVisit));
        }
        
        return result;
    }
    
}
