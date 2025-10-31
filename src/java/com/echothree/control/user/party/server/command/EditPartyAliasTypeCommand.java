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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.PartyAliasTypeEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.form.EditPartyAliasTypeForm;
import com.echothree.control.user.party.common.result.EditPartyAliasTypeResult;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.PartyAliasTypeUniversalSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyAliasTypeLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.PartyAliasType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
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
public class EditPartyAliasTypeCommand
        extends BaseAbstractEditCommand<PartyAliasTypeUniversalSpec, PartyAliasTypeEdit, EditPartyAliasTypeResult, PartyAliasType, PartyAliasType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyAliasType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyAliasTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditPartyAliasTypeCommand */
    public EditPartyAliasTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPartyAliasTypeResult getResult() {
        return PartyResultFactory.getEditPartyAliasTypeResult();
    }
    
    @Override
    public PartyAliasTypeEdit getEdit() {
        return PartyEditFactory.getPartyAliasTypeEdit();
    }

    @Override
    public PartyAliasType getEntity(EditPartyAliasTypeResult result) {
        return PartyAliasTypeLogic.getInstance().getPartyAliasTypeByUniversalSpec(this, spec, false,
                editModeToEntityPermission(editMode));
    }
    
    @Override
    public PartyAliasType getLockEntity(PartyAliasType partyAliasType) {
        return partyAliasType;
    }
    
    @Override
    public void fillInResult(EditPartyAliasTypeResult result, PartyAliasType partyAliasType) {
        var partyControl = Session.getModelController(PartyControl.class);
        
        result.setPartyAliasType(partyControl.getPartyAliasTypeTransfer(getUserVisit(), partyAliasType));
    }
    
    @Override
    public void doLock(PartyAliasTypeEdit edit, PartyAliasType partyAliasType) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyAliasTypeDescription = partyControl.getPartyAliasTypeDescription(partyAliasType, getPreferredLanguage());
        var partyAliasTypeDetail = partyAliasType.getLastDetail();
        
        edit.setPartyAliasTypeName(partyAliasTypeDetail.getPartyAliasTypeName());
        edit.setValidationPattern(partyAliasTypeDetail.getValidationPattern());
        edit.setIsDefault(partyAliasTypeDetail.getIsDefault().toString());
        edit.setSortOrder(partyAliasTypeDetail.getSortOrder().toString());

        if(partyAliasTypeDescription != null) {
            edit.setDescription(partyAliasTypeDescription.getDescription());
        }
    }
        
    @Override
    public void canUpdate(PartyAliasType partyAliasType) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyAliasTypeName = edit.getPartyAliasTypeName();
        var duplicatePartyAliasType = partyControl.getPartyAliasTypeByName(partyAliasType.getActiveDetail().getPartyType(), partyAliasTypeName);

        if(duplicatePartyAliasType != null && !partyAliasType.equals(duplicatePartyAliasType)) {
            addExecutionError(ExecutionErrors.DuplicatePartyAliasTypeName.name(), spec.getPartyTypeName(), partyAliasTypeName);
        }
    }
    
    @Override
    public void doUpdate(PartyAliasType partyAliasType) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyPK = getPartyPK();
        var partyAliasTypeDetailValue = partyControl.getPartyAliasTypeDetailValueForUpdate(partyAliasType);
        var partyAliasTypeDescription = partyControl.getPartyAliasTypeDescriptionForUpdate(partyAliasType, getPreferredLanguage());
        var description = edit.getDescription();

        partyAliasTypeDetailValue.setPartyAliasTypeName(edit.getPartyAliasTypeName());
        partyAliasTypeDetailValue.setValidationPattern(edit.getValidationPattern());
        partyAliasTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        partyAliasTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        PartyAliasTypeLogic.getInstance().updatePartyAliasTypeFromValue(this, partyAliasTypeDetailValue, partyPK);

        if(partyAliasTypeDescription == null && description != null) {
            partyControl.createPartyAliasTypeDescription(partyAliasType, getPreferredLanguage(), description, partyPK);
        } else if(partyAliasTypeDescription != null && description == null) {
            partyControl.deletePartyAliasTypeDescription(partyAliasTypeDescription, partyPK);
        } else if(partyAliasTypeDescription != null && description != null) {
            var partyAliasTypeDescriptionValue = partyControl.getPartyAliasTypeDescriptionValue(partyAliasTypeDescription);

            partyAliasTypeDescriptionValue.setDescription(description);
            partyControl.updatePartyAliasTypeDescriptionFromValue(partyAliasTypeDescriptionValue, partyPK);
        }
    }
    
}
