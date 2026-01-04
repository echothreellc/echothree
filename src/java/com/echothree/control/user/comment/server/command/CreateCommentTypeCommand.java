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

package com.echothree.control.user.comment.server.command;

import com.echothree.control.user.comment.common.form.CreateCommentTypeForm;
import com.echothree.control.user.comment.common.result.CommentResultFactory;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.core.server.logic.EntityTypeLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateCommentTypeCommand */
    public CreateCommentTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CommentResultFactory.getCreateCommentTypeResult();
        var workflowName = form.getWorkflowName();
        var workflowEntranceName = form.getWorkflowEntranceName();
        var parameterCount = (workflowName == null ? 0 : 1) + (workflowEntranceName == null ? 0 : 1);
        CommentType commentType = null;

        if(parameterCount == 0 || parameterCount == 2) {
            var componentVendorName = form.getComponentVendorName();
            var entityTypeName = form.getEntityTypeName();
            var entityType = EntityTypeLogic.getInstance().getEntityTypeByName(this, componentVendorName, entityTypeName);
                
            if(!hasExecutionErrors()) {
                var entityTypeDetail = entityType.getLastDetail();

                if(entityTypeDetail.getIsExtensible()) {
                    var commentControl = Session.getModelController(CommentControl.class);
                    var commentTypeName = form.getCommentTypeName();

                    commentType = commentControl.getCommentTypeByName(entityType, commentTypeName);

                    if(commentType == null) {
                        var commentSequenceName = form.getCommentSequenceName();
                        Sequence commentSequence = null;

                        if(commentSequenceName != null) {
                            var sequenceControl = Session.getModelController(SequenceControl.class);
                            var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.COMMENT.name());

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
                                var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
                                var mimeTypeUsageTypeName = form.getMimeTypeUsageTypeName();
                                var mimeTypeUsageType = mimeTypeUsageTypeName == null ? null : mimeTypeControl.getMimeTypeUsageTypeByName(mimeTypeUsageTypeName);

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
                    addExecutionError(ExecutionErrors.EntityTypeIsNotExtensible.name(),
                            entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                            entityTypeDetail.getEntityTypeName());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        if(commentType != null) {
            var basePK = commentType.getPrimaryKey();
            var commentTypeDetail = commentType.getLastDetail();
            var entityTypeDetail = commentTypeDetail.getEntityType().getLastDetail();

            result.setComponentVendorName(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName());
            result.setEntityTypeName(entityTypeDetail.getEntityTypeName());
            result.setCommentTypeName(commentTypeDetail.getCommentTypeName());
            result.setEntityRef(basePK.getEntityRef());
        }

        return result;
    }
    
}
