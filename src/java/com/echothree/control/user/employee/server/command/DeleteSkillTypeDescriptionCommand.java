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

package com.echothree.control.user.employee.server.command;

import com.echothree.control.user.employee.common.form.DeleteSkillTypeDescriptionForm;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.employee.server.entity.SkillType;
import com.echothree.model.data.employee.server.entity.SkillTypeDescription;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteSkillTypeDescriptionCommand
        extends BaseSimpleCommand<DeleteSkillTypeDescriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SkillTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of DeleteSkillTypeDescriptionCommand */
    public DeleteSkillTypeDescriptionCommand(UserVisitPK userVisitPK, DeleteSkillTypeDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
        String skillTypeName = form.getSkillTypeName();
        SkillType skillType = employeeControl.getSkillTypeByName(skillTypeName);
        
        if(skillType != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                SkillTypeDescription skillTypeDescription = employeeControl.getSkillTypeDescriptionForUpdate(skillType, language);
                
                if(skillTypeDescription != null) {
                    employeeControl.deleteSkillTypeDescription(skillTypeDescription, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownSkillTypeDescription.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSkillTypeName.name(), skillTypeName);
        }
        
        return null;
    }
    
}
