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

package com.echothree.control.user.employee.server.command;

import com.echothree.control.user.employee.common.edit.EmployeeEditFactory;
import com.echothree.control.user.employee.common.edit.ResponsibilityTypeEdit;
import com.echothree.control.user.employee.common.result.EditResponsibilityTypeResult;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.ResponsibilityTypeSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.employee.server.logic.ResponsibilityTypeLogic;
import com.echothree.model.data.employee.server.entity.ResponsibilityType;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditResponsibilityTypeCommand
        extends BaseAbstractEditCommand<ResponsibilityTypeSpec, ResponsibilityTypeEdit, EditResponsibilityTypeResult, ResponsibilityType, ResponsibilityType> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ResponsibilityTypeName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ResponsibilityTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    EmployeeControl employeeControl;

    @Inject
    ResponsibilityTypeLogic responsibilityTypeLogic;

    /** Creates a new instance of EditResponsibilityTypeCommand */
    public EditResponsibilityTypeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditResponsibilityTypeResult getResult() {
        return EmployeeResultFactory.getEditResponsibilityTypeResult();
    }

    @Override
    public ResponsibilityTypeEdit getEdit() {
        return EmployeeEditFactory.getResponsibilityTypeEdit();
    }

    @Override
    public ResponsibilityType getEntity(EditResponsibilityTypeResult result) {
        return responsibilityTypeLogic.getResponsibilityTypeByName(this, spec.getResponsibilityTypeName(), editModeToEntityPermission(editMode));
    }

    @Override
    public ResponsibilityType getLockEntity(ResponsibilityType responsibilityType) {
        return responsibilityType;
    }

    @Override
    public void fillInResult(EditResponsibilityTypeResult result, ResponsibilityType responsibilityType) {
        result.setResponsibilityType(employeeControl.getResponsibilityTypeTransfer(getUserVisit(), responsibilityType));
    }

    @Override
    public void doLock(ResponsibilityTypeEdit edit, ResponsibilityType responsibilityType) {
        var responsibilityTypeDescription = employeeControl.getResponsibilityTypeDescription(responsibilityType, getPreferredLanguage());
        var responsibilityTypeDetail = responsibilityType.getLastDetail();

        edit.setResponsibilityTypeName(responsibilityTypeDetail.getResponsibilityTypeName());
        edit.setIsDefault(responsibilityTypeDetail.getIsDefault().toString());
        edit.setSortOrder(responsibilityTypeDetail.getSortOrder().toString());

        if(responsibilityTypeDescription != null) {
            edit.setDescription(responsibilityTypeDescription.getDescription());
        }
    }

    @Override
    public void doUpdate(ResponsibilityType responsibilityType) {
        var partyPK = getPartyPK();
        var responsibilityTypeDetailValue = employeeControl.getResponsibilityTypeDetailValueForUpdate(responsibilityType);
        var responsibilityTypeDescription = employeeControl.getResponsibilityTypeDescriptionForUpdate(responsibilityType, getPreferredLanguage());
        var description = edit.getDescription();

        responsibilityTypeDetailValue.setResponsibilityTypeName(edit.getResponsibilityTypeName());
        responsibilityTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        responsibilityTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        employeeControl.updateResponsibilityTypeFromValue(responsibilityTypeDetailValue, partyPK);

        if(responsibilityTypeDescription == null && description != null) {
            employeeControl.createResponsibilityTypeDescription(responsibilityType, getPreferredLanguage(), description, partyPK);
        } else if(responsibilityTypeDescription != null && description == null) {
            employeeControl.deleteResponsibilityTypeDescription(responsibilityTypeDescription, partyPK);
        } else if(responsibilityTypeDescription != null && description != null) {
            var responsibilityTypeDescriptionValue = employeeControl.getResponsibilityTypeDescriptionValue(responsibilityTypeDescription);

            responsibilityTypeDescriptionValue.setDescription(description);
            employeeControl.updateResponsibilityTypeDescriptionFromValue(responsibilityTypeDescriptionValue, partyPK);
        }
    }

}
