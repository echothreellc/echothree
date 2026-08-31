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

package com.echothree.control.user.workrequirement.server.command;

import com.echothree.control.user.workrequirement.common.edit.WorkRequirementEditFactory;
import com.echothree.control.user.workrequirement.common.edit.WorkRequirementTypeDescriptionEdit;
import com.echothree.control.user.workrequirement.common.result.EditWorkRequirementTypeDescriptionResult;
import com.echothree.control.user.workrequirement.common.result.WorkRequirementResultFactory;
import com.echothree.control.user.workrequirement.common.spec.WorkRequirementTypeDescriptionSpec;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementType;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditWorkRequirementTypeDescriptionCommand
        extends BaseAbstractEditCommand<WorkRequirementTypeDescriptionSpec, WorkRequirementTypeDescriptionEdit,
                EditWorkRequirementTypeDescriptionResult, WorkRequirementTypeDescription, WorkRequirementType> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("WorkEffortTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("WorkRequirementTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    LanguageLogic languageLogic;

    @Inject
    WorkEffortControl workEffortControl;

    @Inject
    WorkRequirementControl workRequirementControl;

    /** Creates a new instance of EditWorkRequirementTypeDescriptionCommand */
    public EditWorkRequirementTypeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditWorkRequirementTypeDescriptionResult getResult() {
        return WorkRequirementResultFactory.getEditWorkRequirementTypeDescriptionResult();
    }

    @Override
    public WorkRequirementTypeDescriptionEdit getEdit() {
        return WorkRequirementEditFactory.getWorkRequirementTypeDescriptionEdit();
    }

    @Override
    public WorkRequirementTypeDescription getEntity(EditWorkRequirementTypeDescriptionResult result) {
        WorkRequirementTypeDescription workRequirementTypeDescription = null;
        var workEffortTypeName = spec.getWorkEffortTypeName();
        var workEffortType = workEffortControl.getWorkEffortTypeByName(workEffortTypeName);
        
        if(workEffortType != null) {
            var workRequirementTypeName = spec.getWorkRequirementTypeName();
            var workRequirementType = workRequirementControl.getWorkRequirementTypeByName(workEffortType, workRequirementTypeName);
            
            if(workRequirementType != null) {
                var languageIsoName = spec.getLanguageIsoName();
                var language = languageLogic.getLanguageByName(this, languageIsoName);
                
                if(!hasExecutionErrors()) {
                    workRequirementTypeDescription = workRequirementControl.getWorkRequirementTypeDescription(workRequirementType, language,
                            editModeToEntityPermission(editMode));

                    if(workRequirementTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownWorkRequirementTypeDescription.name(), workEffortTypeName,
                                workRequirementTypeName, languageIsoName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkRequirementTypeName.name(), workEffortTypeName, workRequirementTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkEffortTypeName.name(), workEffortTypeName);
        }

        return workRequirementTypeDescription;
    }

    @Override
    public WorkRequirementType getLockEntity(WorkRequirementTypeDescription workRequirementTypeDescription) {
        return workRequirementTypeDescription.getWorkRequirementType();
    }

    @Override
    public void fillInResult(EditWorkRequirementTypeDescriptionResult result, WorkRequirementTypeDescription workRequirementTypeDescription) {
        result.setWorkRequirementTypeDescription(workRequirementControl.getWorkRequirementTypeDescriptionTransfer(getUserVisit(), workRequirementTypeDescription));
    }

    @Override
    public void doLock(WorkRequirementTypeDescriptionEdit edit, WorkRequirementTypeDescription workRequirementTypeDescription) {
        edit.setDescription(workRequirementTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(WorkRequirementTypeDescription workRequirementTypeDescription) {
        var workRequirementTypeDescriptionValue = workRequirementControl.getWorkRequirementTypeDescriptionValue(workRequirementTypeDescription);

        workRequirementTypeDescriptionValue.setDescription(edit.getDescription());

        workRequirementControl.updateWorkRequirementTypeDescriptionFromValue(workRequirementTypeDescriptionValue, getPartyPK());
    }
    
}
