// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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
import com.echothree.control.user.scale.common.edit.ScaleTypeEdit;
import com.echothree.control.user.scale.common.form.EditScaleTypeForm;
import com.echothree.control.user.scale.common.result.EditScaleTypeResult;
import com.echothree.control.user.scale.common.result.ScaleResultFactory;
import com.echothree.control.user.scale.common.spec.ScaleTypeSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.scale.server.entity.ScaleType;
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
public class EditScaleTypeCommand
        extends BaseAbstractEditCommand<ScaleTypeSpec, ScaleTypeEdit, EditScaleTypeResult, ScaleType, ScaleType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.ScaleType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ScaleTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ScaleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditScaleTypeCommand */
    public EditScaleTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditScaleTypeResult getResult() {
        return ScaleResultFactory.getEditScaleTypeResult();
    }

    @Override
    public ScaleTypeEdit getEdit() {
        return ScaleEditFactory.getScaleTypeEdit();
    }

    @Override
    public ScaleType getEntity(EditScaleTypeResult result) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        ScaleType scaleType;
        var scaleTypeName = spec.getScaleTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            scaleType = scaleControl.getScaleTypeByName(scaleTypeName);
        } else { // EditMode.UPDATE
            scaleType = scaleControl.getScaleTypeByNameForUpdate(scaleTypeName);
        }

        if(scaleType != null) {
            result.setScaleType(scaleControl.getScaleTypeTransfer(getUserVisit(), scaleType));
        } else {
            addExecutionError(ExecutionErrors.UnknownScaleTypeName.name(), scaleTypeName);
        }

        return scaleType;
    }

    @Override
    public ScaleType getLockEntity(ScaleType scaleType) {
        return scaleType;
    }

    @Override
    public void fillInResult(EditScaleTypeResult result, ScaleType scaleType) {
        var scaleControl = Session.getModelController(ScaleControl.class);

        result.setScaleType(scaleControl.getScaleTypeTransfer(getUserVisit(), scaleType));
    }

    @Override
    public void doLock(ScaleTypeEdit edit, ScaleType scaleType) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        var scaleTypeDescription = scaleControl.getScaleTypeDescription(scaleType, getPreferredLanguage());
        var scaleTypeDetail = scaleType.getLastDetail();

        edit.setScaleTypeName(scaleTypeDetail.getScaleTypeName());
        edit.setIsDefault(scaleTypeDetail.getIsDefault().toString());
        edit.setSortOrder(scaleTypeDetail.getSortOrder().toString());

        if(scaleTypeDescription != null) {
            edit.setDescription(scaleTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(ScaleType scaleType) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        var scaleTypeName = edit.getScaleTypeName();
        var duplicateScaleType = scaleControl.getScaleTypeByName(scaleTypeName);

        if(duplicateScaleType != null && !scaleType.equals(duplicateScaleType)) {
            addExecutionError(ExecutionErrors.DuplicateScaleTypeName.name(), scaleTypeName);
        }
    }

    @Override
    public void doUpdate(ScaleType scaleType) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        var partyPK = getPartyPK();
        var scaleTypeDetailValue = scaleControl.getScaleTypeDetailValueForUpdate(scaleType);
        var scaleTypeDescription = scaleControl.getScaleTypeDescriptionForUpdate(scaleType, getPreferredLanguage());
        var description = edit.getDescription();

        scaleTypeDetailValue.setScaleTypeName(edit.getScaleTypeName());
        scaleTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        scaleTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        scaleControl.updateScaleTypeFromValue(scaleTypeDetailValue, partyPK);

        if(scaleTypeDescription == null && description != null) {
            scaleControl.createScaleTypeDescription(scaleType, getPreferredLanguage(), description, partyPK);
        } else if(scaleTypeDescription != null && description == null) {
            scaleControl.deleteScaleTypeDescription(scaleTypeDescription, partyPK);
        } else if(scaleTypeDescription != null && description != null) {
            var scaleTypeDescriptionValue = scaleControl.getScaleTypeDescriptionValue(scaleTypeDescription);

            scaleTypeDescriptionValue.setDescription(description);
            scaleControl.updateScaleTypeDescriptionFromValue(scaleTypeDescriptionValue, partyPK);
        }
    }

}
