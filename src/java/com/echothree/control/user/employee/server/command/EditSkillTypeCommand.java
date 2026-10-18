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
import com.echothree.control.user.employee.common.edit.SkillTypeEdit;
import com.echothree.control.user.employee.common.result.EditSkillTypeResult;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.SkillTypeSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.employee.server.logic.SkillTypeLogic;
import com.echothree.model.data.employee.server.entity.SkillType;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditSkillTypeCommand
        extends BaseAbstractEditCommand<SkillTypeSpec, SkillTypeEdit, EditSkillTypeResult, SkillType, SkillType> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SkillTypeName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SkillTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    EmployeeControl employeeControl;

    @Inject
    SkillTypeLogic skillTypeLogic;

    /** Creates a new instance of EditSkillTypeCommand */
    public EditSkillTypeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSkillTypeResult getResult() {
        return EmployeeResultFactory.getEditSkillTypeResult();
    }

    @Override
    public SkillTypeEdit getEdit() {
        return EmployeeEditFactory.getSkillTypeEdit();
    }

    @Override
    public SkillType getEntity(EditSkillTypeResult result) {
        return skillTypeLogic.getSkillTypeByName(this, spec.getSkillTypeName(), editModeToEntityPermission(editMode));
    }

    @Override
    public SkillType getLockEntity(SkillType skillType) {
        return skillType;
    }

    @Override
    public void fillInResult(EditSkillTypeResult result, SkillType skillType) {
        result.setSkillType(employeeControl.getSkillTypeTransfer(getUserVisit(), skillType));
    }

    @Override
    public void doLock(SkillTypeEdit edit, SkillType skillType) {
        var skillTypeDescription = employeeControl.getSkillTypeDescription(skillType, getPreferredLanguage());
        var skillTypeDetail = skillType.getLastDetail();

        edit.setSkillTypeName(skillTypeDetail.getSkillTypeName());
        edit.setIsDefault(skillTypeDetail.getIsDefault().toString());
        edit.setSortOrder(skillTypeDetail.getSortOrder().toString());

        if(skillTypeDescription != null) {
            edit.setDescription(skillTypeDescription.getDescription());
        }
    }

    @Override
    public void doUpdate(SkillType skillType) {
        var partyPK = getPartyPK();
        var skillTypeDetailValue = employeeControl.getSkillTypeDetailValueForUpdate(skillType);
        var skillTypeDescription = employeeControl.getSkillTypeDescriptionForUpdate(skillType, getPreferredLanguage());
        var description = edit.getDescription();

        skillTypeDetailValue.setSkillTypeName(edit.getSkillTypeName());
        skillTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        skillTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        employeeControl.updateSkillTypeFromValue(skillTypeDetailValue, partyPK);

        if(skillTypeDescription == null && description != null) {
            employeeControl.createSkillTypeDescription(skillType, getPreferredLanguage(), description, partyPK);
        } else if(skillTypeDescription != null && description == null) {
            employeeControl.deleteSkillTypeDescription(skillTypeDescription, partyPK);
        } else if(skillTypeDescription != null && description != null) {
            var skillTypeDescriptionValue = employeeControl.getSkillTypeDescriptionValue(skillTypeDescription);

            skillTypeDescriptionValue.setDescription(description);
            employeeControl.updateSkillTypeDescriptionFromValue(skillTypeDescriptionValue, partyPK);
        }
    }

}
