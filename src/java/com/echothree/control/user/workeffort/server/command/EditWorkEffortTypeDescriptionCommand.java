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

package com.echothree.control.user.workeffort.server.command;

import com.echothree.control.user.workeffort.common.edit.WorkEffortEditFactory;
import com.echothree.control.user.workeffort.common.edit.WorkEffortTypeDescriptionEdit;
import com.echothree.control.user.workeffort.common.result.EditWorkEffortTypeDescriptionResult;
import com.echothree.control.user.workeffort.common.result.WorkEffortResultFactory;
import com.echothree.control.user.workeffort.common.spec.WorkEffortTypeDescriptionSpec;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workeffort.server.logic.WorkEffortTypeLogic;
import com.echothree.model.data.workeffort.server.entity.WorkEffortType;
import com.echothree.model.data.workeffort.server.entity.WorkEffortTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditWorkEffortTypeDescriptionCommand
        extends BaseEditCommand<WorkEffortTypeDescriptionSpec, WorkEffortTypeDescriptionEdit, EditWorkEffortTypeDescriptionResult, WorkEffortTypeDescription, WorkEffortType> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkEffortTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    WorkEffortControl workEffortControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    WorkEffortTypeLogic workEffortTypeLogic;

    /** Creates a new instance of EditWorkEffortTypeDescriptionCommand */
    public EditWorkEffortTypeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditWorkEffortTypeDescriptionResult getResult() {
        return WorkEffortResultFactory.getEditWorkEffortTypeDescriptionResult();
    }

    @Override
    public WorkEffortTypeDescriptionEdit getEdit() {
        return WorkEffortEditFactory.getWorkEffortTypeDescriptionEdit();
    }

    @Override
    public WorkEffortTypeDescription getEntity(EditWorkEffortTypeDescriptionResult result) {
        WorkEffortTypeDescription workEffortTypeDescription = null;
        var workEffortTypeName = spec.getWorkEffortTypeName();
        var languageIsoName = spec.getLanguageIsoName();
        var workEffortType = workEffortTypeLogic.getWorkEffortTypeByName(this, workEffortTypeName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            workEffortTypeDescription = workEffortControl.getWorkEffortTypeDescription(workEffortType, language,
                    editModeToEntityPermission(editMode));

            if(workEffortTypeDescription == null) {
                addExecutionError(ExecutionErrors.UnknownWorkEffortTypeDescription.name(), workEffortTypeName, languageIsoName);
            }
        }

        return workEffortTypeDescription;
    }

    @Override
    public WorkEffortType getLockEntity(WorkEffortTypeDescription workEffortTypeDescription) {
        return workEffortTypeDescription.getWorkEffortType();
    }

    @Override
    public void fillInResult(EditWorkEffortTypeDescriptionResult result, WorkEffortTypeDescription workEffortTypeDescription) {
        result.setWorkEffortTypeDescription(workEffortControl.getWorkEffortTypeDescriptionTransfer(getUserVisit(), workEffortTypeDescription));
    }

    @Override
    public void doLock(WorkEffortTypeDescriptionEdit edit, WorkEffortTypeDescription workEffortTypeDescription) {
        edit.setDescription(workEffortTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(WorkEffortTypeDescription workEffortTypeDescription) {
        var workEffortTypeDescriptionValue = workEffortControl.getWorkEffortTypeDescriptionValue(workEffortTypeDescription);

        workEffortTypeDescriptionValue.setDescription(edit.getDescription());

        workEffortControl.updateWorkEffortTypeDescriptionFromValue(workEffortTypeDescriptionValue, getPartyPK());
    }

}
