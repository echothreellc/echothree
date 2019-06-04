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

import com.echothree.control.user.offer.common.form.GetUseForm;
import com.echothree.control.user.offer.common.result.GetUseResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.Use;
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

public class GetUseCommand
        extends BaseSingleEntityCommand<Use, GetUseForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Use.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UseName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetUseCommand */
    public GetUseCommand(UserVisitPK userVisitPK, GetUseForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Use getEntity() {
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        String useName = form.getUseName();
        Use use = offerControl.getUseByName(useName);
        
        if(use != null) {
            sendEventUsingNames(use.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownUseName.name(), useName);
        }
        
        return use;
    }
    
    @Override
    protected BaseResult getTransfer(Use use) {
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        GetUseResult result = OfferResultFactory.getGetUseResult();
        
        if(use != null) {
            UserVisit userVisit = getUserVisit();
            
            result.setUse(offerControl.getUseTransfer(userVisit, use));
            result.setUseNameElements(offerControl.getUseNameElementTransfers(userVisit));
        }
        
        return result;
    }
    
}
