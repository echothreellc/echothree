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

import com.echothree.control.user.offer.common.form.GetUseTypeForm;
import com.echothree.control.user.offer.common.result.GetUseTypeResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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

public class GetUseTypeCommand
        extends BaseSingleEntityCommand<UseType, GetUseTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.UseType.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetUseTypeCommand */
    public GetUseTypeCommand(UserVisitPK userVisitPK, GetUseTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected UseType getEntity() {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        String useTypeName = form.getUseTypeName();
        UseType useType = offerControl.getUseTypeByName(useTypeName);
        
        if(useType != null) {
            sendEventUsingNames(useType.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownUseTypeName.name(), useTypeName);
        }
        
        return useType;
    }
    
    @Override
    protected BaseResult getTransfer(UseType useType) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        GetUseTypeResult result = OfferResultFactory.getGetUseTypeResult();
        
        if(useType != null) {
            result.setUseType(offerControl.getUseTypeTransfer(getUserVisit(), useType));
        }
        
        return result;
    }
    
}
