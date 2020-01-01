// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.workrequirement.common.form.CreateWorkRequirementTypeForm;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.control.workeffort.server.WorkEffortControl;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workrequirement.server.WorkRequirementControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workeffort.server.entity.WorkEffortType;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateWorkRequirementTypeCommand
        extends BaseSimpleCommand<CreateWorkRequirementTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WorkEffortTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("WorkRequirementTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("WorkRequirementSequenceName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("WorkflowStepName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("EstimatedTimeAllowedUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("EstimatedTimeAllowed", FieldType.UNSIGNED_INTEGER, false, null, null),
            new FieldDefinition("MaximumTimeAllowedUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("MaximumTimeAllowed", FieldType.UNSIGNED_INTEGER, false, null, null),
            new FieldDefinition("AllowReassignment", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of CreateWorkRequirementTypeCommand */
    public CreateWorkRequirementTypeCommand(UserVisitPK userVisitPK, CreateWorkRequirementTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var workEffortControl = (WorkEffortControl)Session.getModelController(WorkEffortControl.class);
        String workEffortTypeName = form.getWorkEffortTypeName();
        WorkEffortType workEffortType = workEffortControl.getWorkEffortTypeByName(workEffortTypeName);
        
        if(workEffortType != null) {
            var workRequirementControl = (WorkRequirementControl)Session.getModelController(WorkRequirementControl.class);
            String workRequirementTypeName = form.getWorkRequirementTypeName();
            WorkRequirementType workRequirementType = workRequirementControl.getWorkRequirementTypeByName(workEffortType, workRequirementTypeName);
            
            if(workRequirementType == null) {
                var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
                String workRequirementSequenceName = form.getWorkRequirementSequenceName();
                Sequence workRequirementSequence = resolveWorkRequirementSequence(sequenceControl, workRequirementSequenceName);
                
                if(workRequirementSequenceName == null || workRequirementSequence != null) {
                    String workflowName = form.getWorkflowName();
                    String workflowStepName = form.getWorkflowStepName();
                    WorkflowStep workflowStep  = resolveWorkflowStep(workflowStepName, workflowName);

                    if(!hasExecutionErrors()) {
                        var uomControl = (UomControl)Session.getModelController(UomControl.class);
                        UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);

                        if(timeUnitOfMeasureKind != null) {
                            String estimatedTimeAllowedUnitOfMeasureTypeName = form.getEstimatedTimeAllowedUnitOfMeasureTypeName();
                            UnitOfMeasureType estimatedTimeAllowedUnitOfMeasureType = null;
                            String estimatedTimeAllowed = form.getEstimatedTimeAllowed();

                            if(estimatedTimeAllowedUnitOfMeasureTypeName != null && estimatedTimeAllowed != null) {
                                estimatedTimeAllowedUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                        estimatedTimeAllowedUnitOfMeasureTypeName);

                                if(estimatedTimeAllowedUnitOfMeasureType == null) {
                                    addExecutionError(ExecutionErrors.UnknownEstimatedTimeAllowedUnitOfMeasureTypeName.name(),
                                            estimatedTimeAllowedUnitOfMeasureTypeName);
                                }
                            } else if(estimatedTimeAllowedUnitOfMeasureTypeName != null || estimatedTimeAllowed != null) {
                                addExecutionError(ExecutionErrors.InvalidEstimatedTimeAllowed.name());
                            }

                            if(!hasExecutionErrors()) {
                                String maximumTimeAllowedUnitOfMeasureTypeName = form.getMaximumTimeAllowedUnitOfMeasureTypeName();
                                UnitOfMeasureType maximumTimeAllowedUnitOfMeasureType = null;
                                String maximumTimeAllowed = form.getMaximumTimeAllowed();

                                if(maximumTimeAllowedUnitOfMeasureTypeName != null && maximumTimeAllowed != null) {
                                    maximumTimeAllowedUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                            maximumTimeAllowedUnitOfMeasureTypeName);

                                    if(maximumTimeAllowedUnitOfMeasureType == null) {
                                        addExecutionError(ExecutionErrors.UnknownMaximumTimeAllowedUnitOfMeasureTypeName.name(),
                                                maximumTimeAllowedUnitOfMeasureTypeName);
                                    }
                                } else if(maximumTimeAllowedUnitOfMeasureTypeName != null || maximumTimeAllowed != null) {
                                    addExecutionError(ExecutionErrors.InvalidMaximumTimeAllowed.name());
                                }

                                if(!hasExecutionErrors()) {
                                    Conversion estimatedTimeAllowedConversion = estimatedTimeAllowedUnitOfMeasureType == null? null:
                                        new Conversion(uomControl, estimatedTimeAllowedUnitOfMeasureType,
                                            Long.valueOf(estimatedTimeAllowed)).convertToLowestUnitOfMeasureType();
                                    Conversion maximumTimeAllowedConversion = maximumTimeAllowedUnitOfMeasureType == null? null:
                                        new Conversion(uomControl, maximumTimeAllowedUnitOfMeasureType,
                                            Long.valueOf(maximumTimeAllowed)).convertToLowestUnitOfMeasureType();
                                    PartyPK createdBy = getPartyPK();
                                    Boolean allowReassignment = Boolean.valueOf(form.getAllowReassignment());
                                    Integer sortOrder = Integer.valueOf(form.getSortOrder());
                                    String description = form.getDescription();

                                    workRequirementType = workRequirementControl.createWorkRequirementType(workEffortType, workRequirementTypeName,
                                            workRequirementSequence, workflowStep,
                                            estimatedTimeAllowedConversion == null? null: estimatedTimeAllowedConversion.getQuantity(),
                                            maximumTimeAllowedConversion == null? null: maximumTimeAllowedConversion.getQuantity(),
                                            allowReassignment, sortOrder, createdBy);

                                    if(description != null) {
                                        workRequirementControl.createWorkRequirementTypeDescription(workRequirementType, getPreferredLanguage(), description, createdBy);
                                    }

                                    List<WorkEffortScope> workEffortScopes = workEffortControl.getWorkEffortScopes(workEffortType);
                                    for(WorkEffortScope workEffortScope: workEffortScopes) {
                                        workRequirementControl.createWorkRequirementScope(workEffortScope, workRequirementType, null,
                                                null, null, null, null, createdBy);
                                    }
                                }
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownTimeUnitOfMeasureKind.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkRequirementSequenceName.name(), workRequirementSequenceName);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateWorkRequirementTypeName.name(), workRequirementTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkEffortTypeName.name(), workEffortTypeName);
        }
        
        return null;
    }
    
    private WorkflowStep resolveWorkflowStep(final String workflowStepName, final String workflowName) {
        WorkflowStep workflowStep = null;
        
        if(workflowName != null && workflowStepName != null) {
            var workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
            Workflow workflow = workflowControl.getWorkflowByName(workflowName);
            
            if(workflow != null) {
                workflowStep = workflowControl.getWorkflowStepByName(workflow, workflowStepName);
                
                if(workflowStep == null) {
                    addExecutionError(ExecutionErrors.UnknownWorkflowStepName.name(), workflowStepName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
            }
            
        } else if(workflowName != null || workflowStepName != null) {
            addExecutionError(ExecutionErrors.InvalidWorkflowStep.name());
        }
        
        return workflowStep;
    }
    
    private Sequence resolveWorkRequirementSequence(final SequenceControl sequenceControl, final String workRequirementSequenceName) {
        Sequence workRequirementSequence = null;
        
        if(workRequirementSequenceName != null) {
            SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceConstants.SequenceType_WORK_REQUIREMENT);
            
            if(sequenceType != null) {
                workRequirementSequence = sequenceControl.getSequenceByName(sequenceType, workRequirementSequenceName);
            } else {
                addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), SequenceConstants.SequenceType_WORK_REQUIREMENT);
            }
        }
        
        return workRequirementSequence;
    }
    
}
