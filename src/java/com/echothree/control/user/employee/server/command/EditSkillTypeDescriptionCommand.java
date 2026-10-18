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
import com.echothree.control.user.employee.common.edit.SkillTypeDescriptionEdit;
import com.echothree.control.user.employee.common.result.EditSkillTypeDescriptionResult;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.SkillTypeDescriptionSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.employee.server.logic.SkillTypeLogic;
import com.echothree.model.data.employee.server.entity.SkillType;
import com.echothree.model.data.employee.server.entity.SkillTypeDescription;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditSkillTypeDescriptionCommand
        extends BaseAbstractEditCommand<SkillTypeDescriptionSpec, SkillTypeDescriptionEdit, EditSkillTypeDescriptionResult, SkillTypeDescription, SkillType> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SkillTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    EmployeeControl employeeControl;

    @Inject
    SkillTypeLogic skillTypeLogic;

    /** Creates a new instance of EditSkillTypeDescriptionCommand */
    public EditSkillTypeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSkillTypeDescriptionResult getResult() {
        return EmployeeResultFactory.getEditSkillTypeDescriptionResult();
    }

    @Override
    public SkillTypeDescriptionEdit getEdit() {
        return EmployeeEditFactory.getSkillTypeDescriptionEdit();
    }

    @Override
    public SkillTypeDescription getEntity(EditSkillTypeDescriptionResult result) {
        return skillTypeLogic.getSkillTypeDescriptionBySpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public SkillType getLockEntity(SkillTypeDescription skillTypeDescription) {
        return skillTypeDescription.getSkillType();
    }

    @Override
    public void fillInResult(EditSkillTypeDescriptionResult result, SkillTypeDescription skillTypeDescription) {
        result.setSkillTypeDescription(employeeControl.getSkillTypeDescriptionTransfer(getUserVisit(), skillTypeDescription));
    }

    @Override
    public void doLock(SkillTypeDescriptionEdit edit, SkillTypeDescription skillTypeDescription) {
        edit.setDescription(skillTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(SkillTypeDescription skillTypeDescription) {
        var skillTypeDescriptionValue = employeeControl.getSkillTypeDescriptionValue(skillTypeDescription);

        skillTypeDescriptionValue.setDescription(edit.getDescription());

        employeeControl.updateSkillTypeDescriptionFromValue(skillTypeDescriptionValue, getPartyPK());
    }

}
