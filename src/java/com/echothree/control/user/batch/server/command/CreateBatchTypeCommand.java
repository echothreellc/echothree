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

package com.echothree.control.user.batch.server.command;

import com.echothree.control.user.batch.common.form.CreateBatchTypeForm;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateBatchTypeCommand
        extends BaseSimpleCommand<CreateBatchTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.BatchType.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("BatchTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentBatchTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BatchSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BatchWorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("BatchWorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateBatchTypeCommand */
    public CreateBatchTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var batchControl = Session.getModelController(BatchControl.class);
        var batchTypeName = form.getBatchTypeName();
        var batchType = batchControl.getBatchTypeByName(batchTypeName);

        if(batchType == null) {
            var parentBatchTypeName = form.getParentBatchTypeName();
            BatchType parentBatchType = null;

            if(parentBatchTypeName != null) {
                parentBatchType = batchControl.getBatchTypeByName(parentBatchTypeName);
            }

            if(parentBatchTypeName == null || parentBatchType != null) {
                var sequenceControl = Session.getModelController(SequenceControl.class);
                var batchSequenceTypeName = form.getBatchSequenceTypeName();
                var batchSequenceType = sequenceControl.getSequenceTypeByName(batchSequenceTypeName);

                if(batchSequenceTypeName == null || batchSequenceType != null) {
                    var workflowControl = Session.getModelController(WorkflowControl.class);
                    var batchWorkflowName = form.getBatchWorkflowName();
                    var batchWorkflow = batchWorkflowName == null ? null : workflowControl.getWorkflowByName(batchWorkflowName);

                    if(batchWorkflowName == null || batchWorkflow != null) {
                        var batchWorkflowEntranceName = form.getBatchWorkflowEntranceName();

                        if(batchWorkflowEntranceName == null || (batchWorkflow != null && batchWorkflowEntranceName != null)) {
                            var batchWorkflowEntrance = batchWorkflowEntranceName == null ? null : workflowControl.getWorkflowEntranceByName(batchWorkflow, batchWorkflowEntranceName);

                            if(batchWorkflowEntranceName == null || batchWorkflowEntrance != null) {
                                var partyPK = getPartyPK();
                                var isDefault = Boolean.valueOf(form.getIsDefault());
                                var sortOrder = Integer.valueOf(form.getSortOrder());
                                var description = form.getDescription();

                                batchType = batchControl.createBatchType(batchTypeName, parentBatchType, batchSequenceType, batchWorkflow,
                                        batchWorkflowEntrance, isDefault, sortOrder, partyPK);

                                if(description != null) {
                                    batchControl.createBatchTypeDescription(batchType, getPreferredLanguage(), description, partyPK);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownBatchWorkflowEntranceName.name(), batchWorkflowName, batchWorkflowEntranceName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.MissingRequiredBatchWorkflowName.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownBatchWorkflowName.name(), batchWorkflowName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownBatchSequenceTypeName.name(), batchSequenceTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentBatchTypeName.name(), parentBatchTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateBatchTypeName.name(), batchTypeName);
        }

        return null;
    }
    
}
