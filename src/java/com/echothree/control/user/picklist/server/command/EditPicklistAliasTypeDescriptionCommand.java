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

import com.echothree.control.user.picklist.common.edit.PicklistAliasTypeDescriptionEdit;
import com.echothree.control.user.picklist.common.edit.PicklistEditFactory;
import com.echothree.control.user.picklist.common.form.EditPicklistAliasTypeDescriptionForm;
import com.echothree.control.user.picklist.common.result.EditPicklistAliasTypeDescriptionResult;
import com.echothree.control.user.picklist.common.result.PicklistResultFactory;
import com.echothree.control.user.picklist.common.spec.PicklistAliasTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.picklist.server.entity.PicklistAliasType;
import com.echothree.model.data.picklist.server.entity.PicklistAliasTypeDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditPicklistAliasTypeDescriptionCommand
        extends BaseAbstractEditCommand<PicklistAliasTypeDescriptionSpec, PicklistAliasTypeDescriptionEdit, EditPicklistAliasTypeDescriptionResult, PicklistAliasTypeDescription, PicklistAliasType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PicklistAliasType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PicklistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PicklistAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditPicklistAliasTypeDescriptionCommand */
    public EditPicklistAliasTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPicklistAliasTypeDescriptionResult getResult() {
        return PicklistResultFactory.getEditPicklistAliasTypeDescriptionResult();
    }

    @Override
    public PicklistAliasTypeDescriptionEdit getEdit() {
        return PicklistEditFactory.getPicklistAliasTypeDescriptionEdit();
    }

    @Override
    public PicklistAliasTypeDescription getEntity(EditPicklistAliasTypeDescriptionResult result) {
        var picklistControl = Session.getModelController(PicklistControl.class);
        PicklistAliasTypeDescription picklistAliasTypeDescription = null;
        var picklistTypeName = spec.getPicklistTypeName();
        var picklistType = picklistControl.getPicklistTypeByName(picklistTypeName);

        if(picklistType != null) {
            var picklistAliasTypeName = spec.getPicklistAliasTypeName();
            var picklistAliasType = picklistControl.getPicklistAliasTypeByName(picklistType, picklistAliasTypeName);

            if(picklistAliasType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        picklistAliasTypeDescription = picklistControl.getPicklistAliasTypeDescription(picklistAliasType, language);
                    } else { // EditMode.UPDATE
                        picklistAliasTypeDescription = picklistControl.getPicklistAliasTypeDescriptionForUpdate(picklistAliasType, language);
                    }

                    if(picklistAliasTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownPicklistAliasTypeDescription.name(), picklistTypeName, picklistAliasTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPicklistAliasTypeName.name(), picklistTypeName, picklistAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPicklistTypeName.name(), picklistTypeName);
        }

        return picklistAliasTypeDescription;
    }

    @Override
    public PicklistAliasType getLockEntity(PicklistAliasTypeDescription picklistAliasTypeDescription) {
        return picklistAliasTypeDescription.getPicklistAliasType();
    }

    @Override
    public void fillInResult(EditPicklistAliasTypeDescriptionResult result, PicklistAliasTypeDescription picklistAliasTypeDescription) {
        var picklistControl = Session.getModelController(PicklistControl.class);

        result.setPicklistAliasTypeDescription(picklistControl.getPicklistAliasTypeDescriptionTransfer(getUserVisit(), picklistAliasTypeDescription));
    }

    @Override
    public void doLock(PicklistAliasTypeDescriptionEdit edit, PicklistAliasTypeDescription picklistAliasTypeDescription) {
        edit.setDescription(picklistAliasTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(PicklistAliasTypeDescription picklistAliasTypeDescription) {
        var picklistControl = Session.getModelController(PicklistControl.class);
        var picklistAliasTypeDescriptionValue = picklistControl.getPicklistAliasTypeDescriptionValue(picklistAliasTypeDescription);

        picklistAliasTypeDescriptionValue.setDescription(edit.getDescription());

        picklistControl.updatePicklistAliasTypeDescriptionFromValue(picklistAliasTypeDescriptionValue, getPartyPK());
    }


}
