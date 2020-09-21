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

import com.echothree.control.user.offer.common.form.CreateUseForm;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.UseTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateUseCommand
        extends BaseSimpleCommand<CreateUseForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Use.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("UseName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("UseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateUseCommand */
    public CreateUseCommand(UserVisitPK userVisitPK, CreateUseForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = OfferResultFactory.getCreateUseResult();
        var offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        String useName = form.getUseName();
        Use use = offerControl.getUseByName(useName);
        
        if(use == null) {
            var useTypeControl = (UseTypeControl)Session.getModelController(UseTypeControl.class);
            String useTypeName = form.getUseTypeName();
            UseType useType = useTypeControl.getUseTypeByName(useTypeName);
            
            if(useType != null) {
                var partyPK = getPartyPK();
                var isDefault = Boolean.valueOf(form.getIsDefault());
                var sortOrder = Integer.valueOf(form.getSortOrder());
                var description = form.getDescription();
                
                use = offerControl.createUse(useName, useType, isDefault, sortOrder, partyPK);
                
                if(description != null) {
                    offerControl.createUseDescription(use, getPreferredLanguage(), description, partyPK);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownUseTypeName.name(), useTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateUseName.name(), useName);
        }

        if(use != null) {
            result.setUseName(use.getLastDetail().getUseName());
            result.setEntityRef(use.getPrimaryKey().getEntityRef());
        }

        return result;
    }
    
}
