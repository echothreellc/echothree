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

package com.echothree.control.user.workeffort.server.command;

import com.echothree.control.user.workeffort.common.form.CreateWorkEffortScopeForm;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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
public class CreateWorkEffortScopeCommand
        extends BaseSimpleCommand<CreateWorkEffortScopeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WorkEffortTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("WorkEffortScopeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("WorkEffortSequenceName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("ScheduledTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("ScheduledTime", FieldType.UNSIGNED_INTEGER, false, null, null),
            new FieldDefinition("EstimatedTimeAllowedUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("EstimatedTimeAllowed", FieldType.UNSIGNED_INTEGER, false, null, null),
            new FieldDefinition("MaximumTimeAllowedUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("MaximumTimeAllowed", FieldType.UNSIGNED_INTEGER, false, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of CreateWorkEffortScopeCommand */
    public CreateWorkEffortScopeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var workEffortControl = Session.getModelController(WorkEffortControl.class);
        var workEffortTypeName = form.getWorkEffortTypeName();
        var workEffortType = workEffortControl.getWorkEffortTypeByName(workEffortTypeName);
        
        if(workEffortType != null) {
            var workEffortScopeName = form.getWorkEffortScopeName();
            var workEffortScope = workEffortControl.getWorkEffortScopeByName(workEffortType, workEffortScopeName);
            
            if(workEffortScope == null) {
                var workEffortSequenceName = form.getWorkEffortSequenceName();
                Sequence workEffortSequence = null;
                
                if(workEffortSequenceName != null) {
                    var sequenceControl = Session.getModelController(SequenceControl.class);
                    var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.WORK_EFFORT.name());
                    
                    if(sequenceType != null) {
                        workEffortSequence = sequenceControl.getSequenceByName(sequenceType, workEffortSequenceName);
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), SequenceTypes.WORK_EFFORT.name());
                    }
                }
                
                if(workEffortSequenceName == null || workEffortSequence != null) {
                    var uomControl = Session.getModelController(UomControl.class);
                    var timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
                    
                    if(timeUnitOfMeasureKind != null) {
                        var scheduledTimeUnitOfMeasureTypeName = form.getScheduledTimeUnitOfMeasureTypeName();
                        UnitOfMeasureType scheduledTimeUnitOfMeasureType = null;
                        var scheduledTime = form.getScheduledTime();
                        
                        if(scheduledTimeUnitOfMeasureTypeName != null && scheduledTime != null) {
                            scheduledTimeUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind,
                                    scheduledTimeUnitOfMeasureTypeName);
                            
                            if(scheduledTimeUnitOfMeasureType == null) {
                                addExecutionError(ExecutionErrors.UnknownScheduledTimeUnitOfMeasureTypeName.name(),
                                        scheduledTimeUnitOfMeasureTypeName);
                            }
                        } else if(scheduledTimeUnitOfMeasureTypeName != null || scheduledTime != null) {
                            addExecutionError(ExecutionErrors.InvalidScheduledTime.name());
                        }
                        
                        if(!hasExecutionErrors()) {
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
                                    var workRequirementControl = Session.getModelController(WorkRequirementControl.class);
                                    var scheduledTimeConversion = scheduledTimeUnitOfMeasureType == null? null:
                                        new Conversion(uomControl, scheduledTimeUnitOfMeasureType,
                                            Long.valueOf(scheduledTime)).convertToLowestUnitOfMeasureType();
                                    var estimatedTimeAllowedConversion = estimatedTimeAllowedUnitOfMeasureType == null? null:
                                        new Conversion(uomControl, estimatedTimeAllowedUnitOfMeasureType,
                                            Long.valueOf(estimatedTimeAllowed)).convertToLowestUnitOfMeasureType();
                                    var maximumTimeAllowedConversion = maximumTimeAllowedUnitOfMeasureType == null? null:
                                        new Conversion(uomControl, maximumTimeAllowedUnitOfMeasureType,
                                            Long.valueOf(maximumTimeAllowed)).convertToLowestUnitOfMeasureType();
                                    var createdBy = getPartyPK();
                                    var isDefault = Boolean.valueOf(form.getIsDefault());
                                    var sortOrder = Integer.valueOf(form.getSortOrder());
                                    var description = form.getDescription();
                                    
                                    workEffortScope = workEffortControl.createWorkEffortScope(workEffortType, workEffortScopeName,
                                            workEffortSequence, scheduledTimeConversion == null? null: scheduledTimeConversion.getQuantity(),
                                            estimatedTimeAllowedConversion == null? null: estimatedTimeAllowedConversion.getQuantity(),
                                            maximumTimeAllowedConversion == null? null: maximumTimeAllowedConversion.getQuantity(),
                                            isDefault, sortOrder, createdBy);
                                    
                                    if(description != null) {
                                        workEffortControl.createWorkEffortScopeDescription(workEffortScope, getPreferredLanguage(), description, createdBy);
                                    }

                                    var workRequirementTypes = workRequirementControl.getWorkRequirementTypes(workEffortType);
                                    for(var workRequirementType : workRequirementTypes) {
                                        workRequirementControl.createWorkRequirementScope(workEffortScope, workRequirementType,
                                                null, null, null, null, null, createdBy);
                                    }
                                }
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTimeUnitOfMeasureKind.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownWorkEffortSequenceName.name(), workEffortSequenceName);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateWorkEffortScopeName.name(), workEffortScopeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkEffortTypeName.name(), workEffortTypeName);
        }
        
        return null;
    }
    
}
