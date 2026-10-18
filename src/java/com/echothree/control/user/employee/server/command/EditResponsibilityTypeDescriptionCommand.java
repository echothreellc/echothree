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
import com.echothree.control.user.employee.common.edit.ResponsibilityTypeDescriptionEdit;
import com.echothree.control.user.employee.common.result.EditResponsibilityTypeDescriptionResult;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.ResponsibilityTypeDescriptionSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.employee.server.logic.ResponsibilityTypeLogic;
import com.echothree.model.data.employee.server.entity.ResponsibilityType;
import com.echothree.model.data.employee.server.entity.ResponsibilityTypeDescription;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditResponsibilityTypeDescriptionCommand
        extends BaseAbstractEditCommand<ResponsibilityTypeDescriptionSpec, ResponsibilityTypeDescriptionEdit, EditResponsibilityTypeDescriptionResult, ResponsibilityTypeDescription, ResponsibilityType> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ResponsibilityTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    EmployeeControl employeeControl;

    @Inject
    ResponsibilityTypeLogic responsibilityTypeLogic;

    /** Creates a new instance of EditResponsibilityTypeDescriptionCommand */
    public EditResponsibilityTypeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditResponsibilityTypeDescriptionResult getResult() {
        return EmployeeResultFactory.getEditResponsibilityTypeDescriptionResult();
    }

    @Override
    public ResponsibilityTypeDescriptionEdit getEdit() {
        return EmployeeEditFactory.getResponsibilityTypeDescriptionEdit();
    }

    @Override
    public ResponsibilityTypeDescription getEntity(EditResponsibilityTypeDescriptionResult result) {
        return responsibilityTypeLogic.getResponsibilityTypeDescriptionBySpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public ResponsibilityType getLockEntity(ResponsibilityTypeDescription responsibilityTypeDescription) {
        return responsibilityTypeDescription.getResponsibilityType();
    }

    @Override
    public void fillInResult(EditResponsibilityTypeDescriptionResult result, ResponsibilityTypeDescription responsibilityTypeDescription) {
        result.setResponsibilityTypeDescription(employeeControl.getResponsibilityTypeDescriptionTransfer(getUserVisit(), responsibilityTypeDescription));
    }

    @Override
    public void doLock(ResponsibilityTypeDescriptionEdit edit, ResponsibilityTypeDescription responsibilityTypeDescription) {
        edit.setDescription(responsibilityTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(ResponsibilityTypeDescription responsibilityTypeDescription) {
        var responsibilityTypeDescriptionValue = employeeControl.getResponsibilityTypeDescriptionValue(responsibilityTypeDescription);

        responsibilityTypeDescriptionValue.setDescription(edit.getDescription());

        employeeControl.updateResponsibilityTypeDescriptionFromValue(responsibilityTypeDescriptionValue, getPartyPK());
    }

}
