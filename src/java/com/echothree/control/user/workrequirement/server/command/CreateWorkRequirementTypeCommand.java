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

package com.echothree.control.user.workrequirement.server.command;

import com.echothree.control.user.workrequirement.common.form.CreateWorkRequirementTypeForm;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowStep;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
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
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of CreateWorkRequirementTypeCommand */
    public CreateWorkRequirementTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var workEffortControl = Session.getModelController(WorkEffortControl.class);
        var workEffortTypeName = form.getWorkEffortTypeName();
        var workEffortType = workEffortControl.getWorkEffortTypeByName(workEffortTypeName);
        
        if(workEffortType != null) {
            var workRequirementControl = Session.getModelController(WorkRequirementControl.class);
            var workRequirementTypeName = form.getWorkRequirementTypeName();
            var workRequirementType = workRequirementControl.getWorkRequirementTypeByName(workEffortType, workRequirementTypeName);
            
            if(workRequirementType == null) {
                var sequenceControl = Session.getModelController(SequenceControl.class);
                var workRequirementSequenceName = form.getWorkRequirementSequenceName();
                var workRequirementSequence = resolveWorkRequirementSequence(sequenceControl, workRequirementSequenceName);
                
                if(workRequirementSequenceName == null || workRequirementSequence != null) {
                    var workflowName = form.getWorkflowName();
                    var workflowStepName = form.getWorkflowStepName();
                    var workflowStep  = resolveWorkflowStep(workflowStepName, workflowName);

                    if(!hasExecutionErrors()) {
                        var uomControl = Session.getModelController(UomControl.class);
                        var timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);

                        if(timeUnitOfMeasureKind != null) {
                            var estimatedTimeAllowedUnitOfMeasureTypeName = form.getEstimatedTimeAllowedUnitOfMeasureTypeName();
                            UnitOfMeasureType estimatedTimeAllowedUnitOfMeasureType = null;
                            var estimatedTimeAllowed = form.getEstimatedTimeAllowed();

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
                                var maximumTimeAllowedUnitOfMeasureTypeName = form.getMaximumTimeAllowedUnitOfMeasureTypeName();
                                UnitOfMeasureType maximumTimeAllowedUnitOfMeasureType = null;
                                var maximumTimeAllowed = form.getMaximumTimeAllowed();

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
                                    var estimatedTimeAllowedConversion = estimatedTimeAllowedUnitOfMeasureType == null? null:
                                        new Conversion(uomControl, estimatedTimeAllowedUnitOfMeasureType,
                                            Long.valueOf(estimatedTimeAllowed)).convertToLowestUnitOfMeasureType();
                                    var maximumTimeAllowedConversion = maximumTimeAllowedUnitOfMeasureType == null? null:
                                        new Conversion(uomControl, maximumTimeAllowedUnitOfMeasureType,
                                            Long.valueOf(maximumTimeAllowed)).convertToLowestUnitOfMeasureType();
                                    var createdBy = getPartyPK();
                                    var allowReassignment = Boolean.valueOf(form.getAllowReassignment());
                                    var sortOrder = Integer.valueOf(form.getSortOrder());
                                    var description = form.getDescription();

                                    workRequirementType = workRequirementControl.createWorkRequirementType(workEffortType, workRequirementTypeName,
                                            workRequirementSequence, workflowStep,
                                            estimatedTimeAllowedConversion == null? null: estimatedTimeAllowedConversion.getQuantity(),
                                            maximumTimeAllowedConversion == null? null: maximumTimeAllowedConversion.getQuantity(),
                                            allowReassignment, sortOrder, createdBy);

                                    if(description != null) {
                                        workRequirementControl.createWorkRequirementTypeDescription(workRequirementType, getPreferredLanguage(), description, createdBy);
                                    }

                                    var workEffortScopes = workEffortControl.getWorkEffortScopes(workEffortType);
                                    for(var workEffortScope : workEffortScopes) {
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
            var workflowControl = Session.getModelController(WorkflowControl.class);
            var workflow = workflowControl.getWorkflowByName(workflowName);
            
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
            var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.WORK_REQUIREMENT.name());
            
            if(sequenceType != null) {
                workRequirementSequence = sequenceControl.getSequenceByName(sequenceType, workRequirementSequenceName);
            } else {
                addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), SequenceTypes.WORK_REQUIREMENT.name());
            }
        }
        
        return workRequirementSequence;
    }
    
}
