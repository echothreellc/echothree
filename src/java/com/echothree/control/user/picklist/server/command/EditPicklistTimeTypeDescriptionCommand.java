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

package com.echothree.control.user.picklist.server.command;

import com.echothree.control.user.picklist.common.edit.PicklistEditFactory;
import com.echothree.control.user.picklist.common.edit.PicklistTimeTypeDescriptionEdit;
import com.echothree.control.user.picklist.common.form.EditPicklistTimeTypeDescriptionForm;
import com.echothree.control.user.picklist.common.result.EditPicklistTimeTypeDescriptionResult;
import com.echothree.control.user.picklist.common.result.PicklistResultFactory;
import com.echothree.control.user.picklist.common.spec.PicklistTimeTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.picklist.server.entity.PicklistTimeType;
import com.echothree.model.data.picklist.server.entity.PicklistTimeTypeDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditPicklistTimeTypeDescriptionCommand
        extends BaseAbstractEditCommand<PicklistTimeTypeDescriptionSpec, PicklistTimeTypeDescriptionEdit, EditPicklistTimeTypeDescriptionResult, PicklistTimeTypeDescription, PicklistTimeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PicklistTimeType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PicklistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PicklistTimeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditPicklistTimeTypeDescriptionCommand */
    public EditPicklistTimeTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPicklistTimeTypeDescriptionResult getResult() {
        return PicklistResultFactory.getEditPicklistTimeTypeDescriptionResult();
    }

    @Override
    public PicklistTimeTypeDescriptionEdit getEdit() {
        return PicklistEditFactory.getPicklistTimeTypeDescriptionEdit();
    }

    @Override
    public PicklistTimeTypeDescription getEntity(EditPicklistTimeTypeDescriptionResult result) {
        var picklistControl = Session.getModelController(PicklistControl.class);
        PicklistTimeTypeDescription picklistTimeTypeDescription = null;
        var picklistTypeName = spec.getPicklistTypeName();
        var picklistType = picklistControl.getPicklistTypeByName(picklistTypeName);

        if(picklistType != null) {
            var picklistTimeTypeName = spec.getPicklistTimeTypeName();
            var picklistTimeType = picklistControl.getPicklistTimeTypeByName(picklistType, picklistTimeTypeName);

            if(picklistTimeType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        picklistTimeTypeDescription = picklistControl.getPicklistTimeTypeDescription(picklistTimeType, language);
                    } else { // EditMode.UPDATE
                        picklistTimeTypeDescription = picklistControl.getPicklistTimeTypeDescriptionForUpdate(picklistTimeType, language);
                    }

                    if(picklistTimeTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownPicklistTimeTypeDescription.name(), picklistTypeName, picklistTimeTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPicklistTimeTypeName.name(), picklistTypeName, picklistTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPicklistTypeName.name(), picklistTypeName);
        }

        return picklistTimeTypeDescription;
    }

    @Override
    public PicklistTimeType getLockEntity(PicklistTimeTypeDescription picklistTimeTypeDescription) {
        return picklistTimeTypeDescription.getPicklistTimeType();
    }

    @Override
    public void fillInResult(EditPicklistTimeTypeDescriptionResult result, PicklistTimeTypeDescription picklistTimeTypeDescription) {
        var picklistControl = Session.getModelController(PicklistControl.class);

        result.setPicklistTimeTypeDescription(picklistControl.getPicklistTimeTypeDescriptionTransfer(getUserVisit(), picklistTimeTypeDescription));
    }

    @Override
    public void doLock(PicklistTimeTypeDescriptionEdit edit, PicklistTimeTypeDescription picklistTimeTypeDescription) {
        edit.setDescription(picklistTimeTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(PicklistTimeTypeDescription picklistTimeTypeDescription) {
        var picklistControl = Session.getModelController(PicklistControl.class);
        var picklistTimeTypeDescriptionValue = picklistControl.getPicklistTimeTypeDescriptionValue(picklistTimeTypeDescription);
        picklistTimeTypeDescriptionValue.setDescription(edit.getDescription());

        picklistControl.updatePicklistTimeTypeDescriptionFromValue(picklistTimeTypeDescriptionValue, getPartyPK());
    }
    
}
