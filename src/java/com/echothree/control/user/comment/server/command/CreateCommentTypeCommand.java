// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.control.user.comment.server.command;

import com.echothree.control.user.comment.common.form.CreateCommentTypeForm;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateCommentTypeCommand
        extends BaseSimpleCommand<CreateCommentTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("CommentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommentSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("WorkflowEntranceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MimeTypeUsageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateCommentTypeCommand */
    public CreateCommentTypeCommand(UserVisitPK userVisitPK, CreateCommentTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        String workflowName = form.getWorkflowName();
        String workflowEntranceName = form.getWorkflowEntranceName();
        var parameterCount = (workflowName == null ? 0 : 1) + (workflowEntranceName == null ? 0 : 1);
        
        if(parameterCount == 0 || parameterCount == 2) {
            var coreControl = getCoreControl();
            String componentVendorName = form.getComponentVendorName();
            ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
            
            if(componentVendor != null) {
                String entityTypeName = form.getEntityTypeName();
                EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
                
                if(entityType != null) {
                    var commentControl = Session.getModelController(CommentControl.class);
                    String commentTypeName = form.getCommentTypeName();
                    CommentType commentType = commentControl.getCommentTypeByName(entityType, commentTypeName);
                    
                    if(commentType == null) {
                        String commentSequenceName = form.getCommentSequenceName();
                        Sequence commentSequence = null;
                        
                        if(commentSequenceName != null) {
                            var sequenceControl = Session.getModelController(SequenceControl.class);
                            SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.COMMENT.name());
                            
                            if(sequenceType != null) {
                                commentSequence = sequenceControl.getSequenceByName(sequenceType, commentSequenceName);
                            } else {
                                addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), SequenceTypes.COMMENT.name());
                            }
                        }
                        
                        if(commentSequenceName == null || commentSequence != null) {
                            WorkflowEntrance workflowEntrance = null;
                            
                            if(parameterCount != 0) {
                                var workflowControl = Session.getModelController(WorkflowControl.class);
                                var workflow = workflowControl.getWorkflowByName(workflowName);
                                
                                if(workflow != null) {
                                    workflowEntrance = workflowControl.getWorkflowEntranceByName(workflow, workflowEntranceName);
                                    
                                    if(workflowEntrance == null) {
                                        addExecutionError(ExecutionErrors.UnknownWorkflowEntranceName.name(), workflowEntranceName);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownWorkflowName.name(), workflowName);
                                }
                            }
                            
                            if(!hasExecutionErrors()) {
                                String mimeTypeUsageTypeName = form.getMimeTypeUsageTypeName();
                                MimeTypeUsageType mimeTypeUsageType = mimeTypeUsageTypeName == null? null: coreControl.getMimeTypeUsageTypeByName(mimeTypeUsageTypeName);
                                
                                if(mimeTypeUsageTypeName == null || (mimeTypeUsageTypeName != null && mimeTypeUsageType != null)) {
                                    var partyPK = getPartyPK();
                                    var sortOrder = Integer.valueOf(form.getSortOrder());
                                    var description = form.getDescription();
                                    
                                    commentType = commentControl.createCommentType(entityType, commentTypeName, commentSequence,
                                            workflowEntrance, mimeTypeUsageType, sortOrder, partyPK);
                                    
                                    if(description != null) {
                                        commentControl.createCommentTypeDescription(commentType, getPreferredLanguage(), description,
                                                partyPK);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownMimeTypeUsageTypeName.name(), mimeTypeUsageTypeName);
                                }
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCommentSequenceName.name(), commentSequenceName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateCommentTypeName.name(), commentTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), entityTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return null;
    }
    
}
