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
import com.echothree.control.user.workeffort.common.edit.WorkEffortScopeDescriptionEdit;
import com.echothree.control.user.workeffort.common.result.EditWorkEffortScopeDescriptionResult;
import com.echothree.control.user.workeffort.common.result.WorkEffortResultFactory;
import com.echothree.control.user.workeffort.common.spec.WorkEffortScopeDescriptionSpec;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workeffort.server.logic.WorkEffortScopeLogic;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScopeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditWorkEffortScopeDescriptionCommand
        extends BaseEditCommand<WorkEffortScopeDescriptionSpec, WorkEffortScopeDescriptionEdit, EditWorkEffortScopeDescriptionResult, WorkEffortScopeDescription, WorkEffortScope> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkEffortTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkEffortScopeName", FieldType.ENTITY_NAME, true, null, null),
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
    WorkEffortScopeLogic workEffortScopeLogic;

    /** Creates a new instance of EditWorkEffortScopeDescriptionCommand */
    public EditWorkEffortScopeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditWorkEffortScopeDescriptionResult getResult() {
        return WorkEffortResultFactory.getEditWorkEffortScopeDescriptionResult();
    }

    @Override
    public WorkEffortScopeDescriptionEdit getEdit() {
        return WorkEffortEditFactory.getWorkEffortScopeDescriptionEdit();
    }

    @Override
    public WorkEffortScopeDescription getEntity(EditWorkEffortScopeDescriptionResult result) {
        WorkEffortScopeDescription workEffortScopeDescription = null;
        var workEffortTypeName = spec.getWorkEffortTypeName();
        var workEffortScopeName = spec.getWorkEffortScopeName();
        var languageIsoName = spec.getLanguageIsoName();
        var workEffortScope = workEffortScopeLogic.getWorkEffortScopeByName(this, workEffortTypeName, workEffortScopeName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            workEffortScopeDescription = workEffortControl.getWorkEffortScopeDescription(workEffortScope, language,
                    editModeToEntityPermission(editMode));

            if(workEffortScopeDescription == null) {
                addExecutionError(ExecutionErrors.UnknownWorkEffortScopeDescription.name(), workEffortTypeName, workEffortScopeName, languageIsoName);
            }
        }

        return workEffortScopeDescription;
    }

    @Override
    public WorkEffortScope getLockEntity(WorkEffortScopeDescription workEffortScopeDescription) {
        return workEffortScopeDescription.getWorkEffortScope();
    }

    @Override
    public void fillInResult(EditWorkEffortScopeDescriptionResult result, WorkEffortScopeDescription workEffortScopeDescription) {
        result.setWorkEffortScopeDescription(workEffortControl.getWorkEffortScopeDescriptionTransfer(getUserVisit(), workEffortScopeDescription));
    }

    @Override
    public void doLock(WorkEffortScopeDescriptionEdit edit, WorkEffortScopeDescription workEffortScopeDescription) {
        edit.setDescription(workEffortScopeDescription.getDescription());
    }

    @Override
    public void doUpdate(WorkEffortScopeDescription workEffortScopeDescription) {
        var workEffortScopeDescriptionValue = workEffortControl.getWorkEffortScopeDescriptionValue(workEffortScopeDescription);

        workEffortScopeDescriptionValue.setDescription(edit.getDescription());

        workEffortControl.updateWorkEffortScopeDescriptionFromValue(workEffortScopeDescriptionValue, getPartyPK());
    }

}
