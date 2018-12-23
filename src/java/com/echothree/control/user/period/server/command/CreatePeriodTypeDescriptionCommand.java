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

package com.echothree.control.user.period.server.command;

import com.echothree.control.user.period.common.form.CreatePeriodTypeDescriptionForm;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.period.server.PeriodControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.model.data.period.server.entity.PeriodTypeDescription;
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

public class CreatePeriodTypeDescriptionCommand
        extends BaseSimpleCommand<CreatePeriodTypeDescriptionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PeriodType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PeriodKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PeriodTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreatePeriodTypeDescriptionCommand */
    public CreatePeriodTypeDescriptionCommand(UserVisitPK userVisitPK, CreatePeriodTypeDescriptionForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        PeriodControl periodControl = (PeriodControl)Session.getModelController(PeriodControl.class);
        String periodKindName = form.getPeriodKindName();
        PeriodKind periodKind = periodControl.getPeriodKindByName(periodKindName);
        
        if(periodKind != null) {
            String periodTypeName = form.getPeriodTypeName();
            PeriodType periodType = periodControl.getPeriodTypeByName(periodKind, periodTypeName);
            
            if(periodType != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = form.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    PeriodTypeDescription periodTypeDescription = periodControl.getPeriodTypeDescription(periodType, language);
                    
                    if(periodTypeDescription == null) {
                        String description = form.getDescription();
                        
                        periodControl.createPeriodTypeDescription(periodType, language, description, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.DuplicatePeriodTypeDescription.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPeriodTypeName.name(), periodTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPeriodKindName.name(), periodKindName);
        }
        
        return null;
    }
    
}
