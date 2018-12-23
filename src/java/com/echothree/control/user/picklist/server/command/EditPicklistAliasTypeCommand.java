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

package com.echothree.control.user.picklist.server.command;

import com.echothree.control.user.picklist.common.edit.PicklistAliasTypeEdit;
import com.echothree.control.user.picklist.common.edit.PicklistEditFactory;
import com.echothree.control.user.picklist.common.form.EditPicklistAliasTypeForm;
import com.echothree.control.user.picklist.common.result.EditPicklistAliasTypeResult;
import com.echothree.control.user.picklist.common.result.PicklistResultFactory;
import com.echothree.control.user.picklist.common.spec.PicklistAliasTypeSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.picklist.server.PicklistControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.picklist.server.entity.PicklistAliasType;
import com.echothree.model.data.picklist.server.entity.PicklistAliasTypeDescription;
import com.echothree.model.data.picklist.server.entity.PicklistAliasTypeDetail;
import com.echothree.model.data.picklist.server.entity.PicklistType;
import com.echothree.model.data.picklist.server.value.PicklistAliasTypeDescriptionValue;
import com.echothree.model.data.picklist.server.value.PicklistAliasTypeDetailValue;
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

public class EditPicklistAliasTypeCommand
        extends BaseAbstractEditCommand<PicklistAliasTypeSpec, PicklistAliasTypeEdit, EditPicklistAliasTypeResult, PicklistAliasType, PicklistAliasType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PicklistAliasType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PicklistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PicklistAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PicklistAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditPicklistAliasTypeCommand */
    public EditPicklistAliasTypeCommand(UserVisitPK userVisitPK, EditPicklistAliasTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPicklistAliasTypeResult getResult() {
        return PicklistResultFactory.getEditPicklistAliasTypeResult();
    }

    @Override
    public PicklistAliasTypeEdit getEdit() {
        return PicklistEditFactory.getPicklistAliasTypeEdit();
    }

    PicklistType picklistType;

    @Override
    public PicklistAliasType getEntity(EditPicklistAliasTypeResult result) {
        PicklistControl picklistControl = (PicklistControl)Session.getModelController(PicklistControl.class);
        PicklistAliasType picklistAliasType = null;
        String picklistTypeName = spec.getPicklistTypeName();

        picklistType = picklistControl.getPicklistTypeByName(picklistTypeName);

        if(picklistType != null) {
            String picklistAliasTypeName = spec.getPicklistAliasTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                picklistAliasType = picklistControl.getPicklistAliasTypeByName(picklistType, picklistAliasTypeName);
            } else { // EditMode.UPDATE
                picklistAliasType = picklistControl.getPicklistAliasTypeByNameForUpdate(picklistType, picklistAliasTypeName);
            }

            if(picklistAliasType != null) {
                result.setPicklistAliasType(picklistControl.getPicklistAliasTypeTransfer(getUserVisit(), picklistAliasType));
            } else {
                addExecutionError(ExecutionErrors.UnknownPicklistAliasTypeName.name(), picklistTypeName, picklistAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPicklistTypeName.name(), picklistTypeName);
        }

        return picklistAliasType;
    }

    @Override
    public PicklistAliasType getLockEntity(PicklistAliasType picklistAliasType) {
        return picklistAliasType;
    }

    @Override
    public void fillInResult(EditPicklistAliasTypeResult result, PicklistAliasType picklistAliasType) {
        PicklistControl picklistControl = (PicklistControl)Session.getModelController(PicklistControl.class);

        result.setPicklistAliasType(picklistControl.getPicklistAliasTypeTransfer(getUserVisit(), picklistAliasType));
    }

    @Override
    public void doLock(PicklistAliasTypeEdit edit, PicklistAliasType picklistAliasType) {
        PicklistControl picklistControl = (PicklistControl)Session.getModelController(PicklistControl.class);
        PicklistAliasTypeDescription picklistAliasTypeDescription = picklistControl.getPicklistAliasTypeDescription(picklistAliasType, getPreferredLanguage());
        PicklistAliasTypeDetail picklistAliasTypeDetail = picklistAliasType.getLastDetail();

        edit.setPicklistAliasTypeName(picklistAliasTypeDetail.getPicklistAliasTypeName());
        edit.setValidationPattern(picklistAliasTypeDetail.getValidationPattern());
        edit.setIsDefault(picklistAliasTypeDetail.getIsDefault().toString());
        edit.setSortOrder(picklistAliasTypeDetail.getSortOrder().toString());

        if(picklistAliasTypeDescription != null) {
            edit.setDescription(picklistAliasTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(PicklistAliasType picklistAliasType) {
        PicklistControl picklistControl = (PicklistControl)Session.getModelController(PicklistControl.class);
        String picklistAliasTypeName = edit.getPicklistAliasTypeName();
        PicklistAliasType duplicatePicklistAliasType = picklistControl.getPicklistAliasTypeByName(picklistType, picklistAliasTypeName);

        if(duplicatePicklistAliasType != null && !picklistAliasType.equals(duplicatePicklistAliasType)) {
            addExecutionError(ExecutionErrors.DuplicatePicklistAliasTypeName.name(), spec.getPicklistTypeName(), picklistAliasTypeName);
        }
    }

    @Override
    public void doUpdate(PicklistAliasType picklistAliasType) {
        PicklistControl picklistControl = (PicklistControl)Session.getModelController(PicklistControl.class);
        PartyPK partyPK = getPartyPK();
        PicklistAliasTypeDetailValue picklistAliasTypeDetailValue = picklistControl.getPicklistAliasTypeDetailValueForUpdate(picklistAliasType);
        PicklistAliasTypeDescription picklistAliasTypeDescription = picklistControl.getPicklistAliasTypeDescriptionForUpdate(picklistAliasType, getPreferredLanguage());
        String description = edit.getDescription();

        picklistAliasTypeDetailValue.setPicklistAliasTypeName(edit.getPicklistAliasTypeName());
        picklistAliasTypeDetailValue.setValidationPattern(edit.getValidationPattern());
        picklistAliasTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        picklistAliasTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        picklistControl.updatePicklistAliasTypeFromValue(picklistAliasTypeDetailValue, partyPK);

        if(picklistAliasTypeDescription == null && description != null) {
            picklistControl.createPicklistAliasTypeDescription(picklistAliasType, getPreferredLanguage(), description, partyPK);
        } else if(picklistAliasTypeDescription != null && description == null) {
            picklistControl.deletePicklistAliasTypeDescription(picklistAliasTypeDescription, partyPK);
        } else if(picklistAliasTypeDescription != null && description != null) {
            PicklistAliasTypeDescriptionValue picklistAliasTypeDescriptionValue = picklistControl.getPicklistAliasTypeDescriptionValue(picklistAliasTypeDescription);

            picklistAliasTypeDescriptionValue.setDescription(description);
            picklistControl.updatePicklistAliasTypeDescriptionFromValue(picklistAliasTypeDescriptionValue, partyPK);
        }
    }

}
