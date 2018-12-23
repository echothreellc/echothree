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

import com.echothree.control.user.picklist.common.edit.PicklistEditFactory;
import com.echothree.control.user.picklist.common.edit.PicklistTimeTypeEdit;
import com.echothree.control.user.picklist.common.form.EditPicklistTimeTypeForm;
import com.echothree.control.user.picklist.common.result.EditPicklistTimeTypeResult;
import com.echothree.control.user.picklist.common.result.PicklistResultFactory;
import com.echothree.control.user.picklist.common.spec.PicklistTimeTypeSpec;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.picklist.server.PicklistControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.picklist.server.entity.PicklistTimeType;
import com.echothree.model.data.picklist.server.entity.PicklistTimeTypeDescription;
import com.echothree.model.data.picklist.server.entity.PicklistTimeTypeDetail;
import com.echothree.model.data.picklist.server.entity.PicklistType;
import com.echothree.model.data.picklist.server.value.PicklistTimeTypeDescriptionValue;
import com.echothree.model.data.picklist.server.value.PicklistTimeTypeDetailValue;
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

public class EditPicklistTimeTypeCommand
        extends BaseAbstractEditCommand<PicklistTimeTypeSpec, PicklistTimeTypeEdit, EditPicklistTimeTypeResult, PicklistTimeType, PicklistTimeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PicklistTimeType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PicklistTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("PicklistTimeTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PicklistTimeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditPicklistTimeTypeCommand */
    public EditPicklistTimeTypeCommand(UserVisitPK userVisitPK, EditPicklistTimeTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditPicklistTimeTypeResult getResult() {
        return PicklistResultFactory.getEditPicklistTimeTypeResult();
    }

    @Override
    public PicklistTimeTypeEdit getEdit() {
        return PicklistEditFactory.getPicklistTimeTypeEdit();
    }

    @Override
    public PicklistTimeType getEntity(EditPicklistTimeTypeResult result) {
        PicklistControl picklistControl = (PicklistControl)Session.getModelController(PicklistControl.class);
        PicklistTimeType picklistTimeType = null;
        String picklistTypeName = spec.getPicklistTypeName();
        PicklistType picklistType = picklistControl.getPicklistTypeByName(picklistTypeName);

        if(picklistType != null) {
            String picklistTimeTypeName = spec.getPicklistTimeTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                picklistTimeType = picklistControl.getPicklistTimeTypeByName(picklistType, picklistTimeTypeName);
            } else { // EditMode.UPDATE
                picklistTimeType = picklistControl.getPicklistTimeTypeByNameForUpdate(picklistType, picklistTimeTypeName);
            }

            if(picklistTimeType != null) {
                result.setPicklistTimeType(picklistControl.getPicklistTimeTypeTransfer(getUserVisit(), picklistTimeType));
            } else {
                addExecutionError(ExecutionErrors.UnknownPicklistTimeTypeName.name(), picklistTypeName, picklistTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPicklistTypeName.name(), picklistTypeName);
        }

        return picklistTimeType;
    }

    @Override
    public PicklistTimeType getLockEntity(PicklistTimeType picklistTimeType) {
        return picklistTimeType;
    }

    @Override
    public void fillInResult(EditPicklistTimeTypeResult result, PicklistTimeType picklistTimeType) {
        PicklistControl picklistControl = (PicklistControl)Session.getModelController(PicklistControl.class);

        result.setPicklistTimeType(picklistControl.getPicklistTimeTypeTransfer(getUserVisit(), picklistTimeType));
    }

    @Override
    public void doLock(PicklistTimeTypeEdit edit, PicklistTimeType picklistTimeType) {
        PicklistControl picklistControl = (PicklistControl)Session.getModelController(PicklistControl.class);
        PicklistTimeTypeDescription picklistTimeTypeDescription = picklistControl.getPicklistTimeTypeDescription(picklistTimeType, getPreferredLanguage());
        PicklistTimeTypeDetail picklistTimeTypeDetail = picklistTimeType.getLastDetail();

        edit.setPicklistTimeTypeName(picklistTimeTypeDetail.getPicklistTimeTypeName());
        edit.setIsDefault(picklistTimeTypeDetail.getIsDefault().toString());
        edit.setSortOrder(picklistTimeTypeDetail.getSortOrder().toString());

        if(picklistTimeTypeDescription != null) {
            edit.setDescription(picklistTimeTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(PicklistTimeType picklistTimeType) {
        PicklistControl picklistControl = (PicklistControl)Session.getModelController(PicklistControl.class);
        String picklistTypeName = spec.getPicklistTypeName();
        PicklistType picklistType = picklistControl.getPicklistTypeByName(picklistTypeName);

        if(picklistType != null) {
            String picklistTimeTypeName = edit.getPicklistTimeTypeName();
            PicklistTimeType duplicatePicklistTimeType = picklistControl.getPicklistTimeTypeByName(picklistType, picklistTimeTypeName);

            if(duplicatePicklistTimeType != null && !picklistTimeType.equals(duplicatePicklistTimeType)) {
                addExecutionError(ExecutionErrors.DuplicatePicklistTimeTypeName.name(), picklistTypeName, picklistTimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPicklistTypeName.name(), picklistTypeName);
        }
    }

    @Override
    public void doUpdate(PicklistTimeType picklistTimeType) {
        PicklistControl picklistControl = (PicklistControl)Session.getModelController(PicklistControl.class);
        PartyPK partyPK = getPartyPK();
        PicklistTimeTypeDetailValue picklistTimeTypeDetailValue = picklistControl.getPicklistTimeTypeDetailValueForUpdate(picklistTimeType);
        PicklistTimeTypeDescription picklistTimeTypeDescription = picklistControl.getPicklistTimeTypeDescriptionForUpdate(picklistTimeType, getPreferredLanguage());
        String description = edit.getDescription();

        picklistTimeTypeDetailValue.setPicklistTimeTypeName(edit.getPicklistTimeTypeName());
        picklistTimeTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        picklistTimeTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        picklistControl.updatePicklistTimeTypeFromValue(picklistTimeTypeDetailValue, partyPK);

        if(picklistTimeTypeDescription == null && description != null) {
            picklistControl.createPicklistTimeTypeDescription(picklistTimeType, getPreferredLanguage(), description, partyPK);
        } else {
            if(picklistTimeTypeDescription != null && description == null) {
                picklistControl.deletePicklistTimeTypeDescription(picklistTimeTypeDescription, partyPK);
            } else {
                if(picklistTimeTypeDescription != null && description != null) {
                    PicklistTimeTypeDescriptionValue picklistTimeTypeDescriptionValue = picklistControl.getPicklistTimeTypeDescriptionValue(picklistTimeTypeDescription);

                    picklistTimeTypeDescriptionValue.setDescription(description);
                    picklistControl.updatePicklistTimeTypeDescriptionFromValue(picklistTimeTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
