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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.PartyEntityTypeEdit;
import com.echothree.control.user.core.common.form.EditPartyEntityTypeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditPartyEntityTypeResult;
import com.echothree.control.user.core.common.spec.PartyEntityTypeSpec;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.PartyEntityType;
import com.echothree.model.data.core.server.value.PartyEntityTypeValue;
import com.echothree.model.data.party.server.entity.Party;
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


public class EditPartyEntityTypeCommand
        extends BaseAbstractEditCommand<PartyEntityTypeSpec, PartyEntityTypeEdit, EditPartyEntityTypeResult, PartyEntityType, Party> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyEntityType.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ConfirmDelete", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyEntityTypeCommand */
    public EditPartyEntityTypeCommand(UserVisitPK userVisitPK, EditPartyEntityTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
   public EditPartyEntityTypeResult getResult() {
        return CoreResultFactory.getEditPartyEntityTypeResult();
    }

    @Override
    public PartyEntityTypeEdit getEdit() {
        return CoreEditFactory.getPartyEntityTypeEdit();
    }

    @Override
    public PartyEntityType getEntity(EditPartyEntityTypeResult result) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        PartyEntityType partyEntityType = null;
        String partyName = spec.getPartyName();
        Party party = partyName == null ? getParty() : partyControl.getPartyByName(partyName);

        if(party != null) {
            CoreControl coreControl = getCoreControl();
            String componentVendorName = spec.getComponentVendorName();
            ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);

            if(componentVendor != null) {
                String entityTypeName = spec.getEntityTypeName();
                EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);

                if(entityType != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        partyEntityType = coreControl.getPartyEntityType(party, entityType);
                    } else { // EditMode.UPDATE
                        partyEntityType = coreControl.getPartyEntityTypeForUpdate(party, entityType);
                    }

                    if(partyEntityType == null) {
                        addExecutionError(ExecutionErrors.UnknownPartyEntityType.name(), partyName, componentVendorName, entityTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), componentVendorName, entityTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return partyEntityType;
    }

    @Override
    public Party getLockEntity(PartyEntityType partyEntityType) {
        return partyEntityType.getParty();
    }

    @Override
    public void fillInResult(EditPartyEntityTypeResult result, PartyEntityType partyEntityType) {
        CoreControl coreControl = getCoreControl();

        result.setPartyEntityType(coreControl.getPartyEntityTypeTransfer(getUserVisit(), partyEntityType));
    }

    @Override
    public void doLock(PartyEntityTypeEdit edit, PartyEntityType partyEntityType) {
        edit.setConfirmDelete(partyEntityType.getConfirmDelete().toString());
    }

    @Override
    public void doUpdate(PartyEntityType partyEntityType) {
        CoreControl coreControl = getCoreControl();
        PartyEntityTypeValue partyEntityTypeValue = coreControl.getPartyEntityTypeValue(partyEntityType);
        
        partyEntityTypeValue.setConfirmDelete(Boolean.valueOf(edit.getConfirmDelete()));

        coreControl.updatePartyEntityTypeFromValue(partyEntityTypeValue, getPartyPK());
    }
    
}
