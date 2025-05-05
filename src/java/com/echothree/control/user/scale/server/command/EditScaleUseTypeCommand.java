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

package com.echothree.control.user.scale.server.command;

import com.echothree.control.user.scale.common.edit.ScaleEditFactory;
import com.echothree.control.user.scale.common.edit.ScaleUseTypeEdit;
import com.echothree.control.user.scale.common.form.EditScaleUseTypeForm;
import com.echothree.control.user.scale.common.result.EditScaleUseTypeResult;
import com.echothree.control.user.scale.common.result.ScaleResultFactory;
import com.echothree.control.user.scale.common.spec.ScaleUseTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.scale.server.entity.ScaleUseType;
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

public class EditScaleUseTypeCommand
        extends BaseAbstractEditCommand<ScaleUseTypeSpec, ScaleUseTypeEdit, EditScaleUseTypeResult, ScaleUseType, ScaleUseType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ScaleUseType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ScaleUseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ScaleUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditScaleUseTypeCommand */
    public EditScaleUseTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditScaleUseTypeResult getResult() {
        return ScaleResultFactory.getEditScaleUseTypeResult();
    }

    @Override
    public ScaleUseTypeEdit getEdit() {
        return ScaleEditFactory.getScaleUseTypeEdit();
    }

    @Override
    public ScaleUseType getEntity(EditScaleUseTypeResult result) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        ScaleUseType scaleUseType;
        var scaleUseTypeName = spec.getScaleUseTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            scaleUseType = scaleControl.getScaleUseTypeByName(scaleUseTypeName);
        } else { // EditMode.UPDATE
            scaleUseType = scaleControl.getScaleUseTypeByNameForUpdate(scaleUseTypeName);
        }

        if(scaleUseType != null) {
            result.setScaleUseType(scaleControl.getScaleUseTypeTransfer(getUserVisit(), scaleUseType));
        } else {
            addExecutionError(ExecutionErrors.UnknownScaleUseTypeName.name(), scaleUseTypeName);
        }

        return scaleUseType;
    }

    @Override
    public ScaleUseType getLockEntity(ScaleUseType scaleUseType) {
        return scaleUseType;
    }

    @Override
    public void fillInResult(EditScaleUseTypeResult result, ScaleUseType scaleUseType) {
        var scaleControl = Session.getModelController(ScaleControl.class);

        result.setScaleUseType(scaleControl.getScaleUseTypeTransfer(getUserVisit(), scaleUseType));
    }

    @Override
    public void doLock(ScaleUseTypeEdit edit, ScaleUseType scaleUseType) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        var scaleUseTypeDescription = scaleControl.getScaleUseTypeDescription(scaleUseType, getPreferredLanguage());
        var scaleUseTypeDetail = scaleUseType.getLastDetail();

        edit.setScaleUseTypeName(scaleUseTypeDetail.getScaleUseTypeName());
        edit.setIsDefault(scaleUseTypeDetail.getIsDefault().toString());
        edit.setSortOrder(scaleUseTypeDetail.getSortOrder().toString());

        if(scaleUseTypeDescription != null) {
            edit.setDescription(scaleUseTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ScaleUseType scaleUseType) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        var scaleUseTypeName = edit.getScaleUseTypeName();
        var duplicateScaleUseType = scaleControl.getScaleUseTypeByName(scaleUseTypeName);

        if(duplicateScaleUseType != null && !scaleUseType.equals(duplicateScaleUseType)) {
            addExecutionError(ExecutionErrors.DuplicateScaleUseTypeName.name(), scaleUseTypeName);
        }
    }

    @Override
    public void doUpdate(ScaleUseType scaleUseType) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        var partyPK = getPartyPK();
        var scaleUseTypeDetailValue = scaleControl.getScaleUseTypeDetailValueForUpdate(scaleUseType);
        var scaleUseTypeDescription = scaleControl.getScaleUseTypeDescriptionForUpdate(scaleUseType, getPreferredLanguage());
        var description = edit.getDescription();

        scaleUseTypeDetailValue.setScaleUseTypeName(edit.getScaleUseTypeName());
        scaleUseTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        scaleUseTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        scaleControl.updateScaleUseTypeFromValue(scaleUseTypeDetailValue, partyPK);

        if(scaleUseTypeDescription == null && description != null) {
            scaleControl.createScaleUseTypeDescription(scaleUseType, getPreferredLanguage(), description, partyPK);
        } else if(scaleUseTypeDescription != null && description == null) {
            scaleControl.deleteScaleUseTypeDescription(scaleUseTypeDescription, partyPK);
        } else if(scaleUseTypeDescription != null && description != null) {
            var scaleUseTypeDescriptionValue = scaleControl.getScaleUseTypeDescriptionValue(scaleUseTypeDescription);

            scaleUseTypeDescriptionValue.setDescription(description);
            scaleControl.updateScaleUseTypeDescriptionFromValue(scaleUseTypeDescriptionValue, partyPK);
        }
    }

}
