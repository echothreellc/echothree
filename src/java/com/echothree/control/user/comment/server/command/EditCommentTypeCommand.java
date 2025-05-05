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

package com.echothree.control.user.comment.server.command;

import com.echothree.control.user.comment.common.edit.CommentEditFactory;
import com.echothree.control.user.comment.common.edit.CommentTypeEdit;
import com.echothree.control.user.comment.common.form.EditCommentTypeForm;
import com.echothree.control.user.comment.common.result.CommentResultFactory;
import com.echothree.control.user.comment.common.spec.CommentTypeSpec;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditCommentTypeCommand
        extends BaseEditCommand<CommentTypeSpec, CommentTypeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("CommentTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CommentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommentSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCommentTypeCommand */
    public EditCommentTypeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CommentResultFactory.getEditCommentTypeResult();
        var componentVendorName = spec.getComponentVendorName();
        var componentVendor = getComponentControl().getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            var entityTypeName = spec.getEntityTypeName();
            var entityType = getEntityTypeControl().getEntityTypeByName(componentVendor, entityTypeName);
            
            if(entityType != null) {
                var commentControl = Session.getModelController(CommentControl.class);
                
                if(editMode.equals(EditMode.LOCK)) {
                    var commentTypeName = spec.getCommentTypeName();
                    var commentType = commentControl.getCommentTypeByName(entityType, commentTypeName);
                    
                    if(commentType != null) {
                        result.setCommentType(commentControl.getCommentTypeTransfer(getUserVisit(), commentType));
                        
                        if(lockEntity(commentType)) {
                            var commentTypeDescription = commentControl.getCommentTypeDescription(commentType, getPreferredLanguage());
                            var edit = CommentEditFactory.getCommentTypeEdit();
                            var commentTypeDetail = commentType.getLastDetail();
                            var commentSequence = commentTypeDetail.getCommentSequence();
                            
                            result.setEdit(edit);
                            edit.setCommentTypeName(commentTypeDetail.getCommentTypeName());
                            edit.setCommentSequenceName(commentSequence == null? null: commentSequence.getLastDetail().getSequenceName());
                            edit.setSortOrder(commentTypeDetail.getSortOrder().toString());
                            
                            if(commentTypeDescription != null)
                                edit.setDescription(commentTypeDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(commentType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCommentTypeName.name(), commentTypeName);
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var commentTypeName = spec.getCommentTypeName();
                    var commentType = commentControl.getCommentTypeByNameForUpdate(entityType, commentTypeName);
                    
                    if(commentType != null) {
                        commentTypeName = edit.getCommentTypeName();
                        var duplicateCommentType = commentControl.getCommentTypeByName(entityType, commentTypeName);
                        
                        if(duplicateCommentType == null || commentType.equals(duplicateCommentType)) {
                            var commentSequenceName = edit.getCommentSequenceName();
                            Sequence commentSequence = null;
                            
                            if(commentSequenceName != null) {
                                var sequenceControl = Session.getModelController(SequenceControl.class);
                                var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.COMMENT.name());
                                
                                if(sequenceType != null) {
                                    commentSequence = sequenceControl.getSequenceByName(sequenceType, commentSequenceName);
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownSequenceTypeName.name(), SequenceTypes.RATING.name());
                                }
                            }
                            
                            if(commentSequenceName == null || commentSequence != null) {
                                if(lockEntityForUpdate(commentType)) {
                                    try {
                                        var partyPK = getPartyPK();
                                        var commentTypeDetailValue = commentControl.getCommentTypeDetailValueForUpdate(commentType);
                                        var commentTypeDescription = commentControl.getCommentTypeDescriptionForUpdate(commentType, getPreferredLanguage());
                                        var description = edit.getDescription();
                                        
                                        commentTypeDetailValue.setCommentTypeName(edit.getCommentTypeName());
                                        commentTypeDetailValue.setCommentSequencePK(commentSequence == null? null: commentSequence.getPrimaryKey());
                                        commentTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                        
                                        commentControl.updateCommentTypeFromValue(commentTypeDetailValue, partyPK);
                                        
                                        if(commentTypeDescription == null && description != null) {
                                            commentControl.createCommentTypeDescription(commentType, getPreferredLanguage(), description, partyPK);
                                        } else if(commentTypeDescription != null && description == null) {
                                            commentControl.deleteCommentTypeDescription(commentTypeDescription, partyPK);
                                        } else if(commentTypeDescription != null && description != null) {
                                            var commentTypeDescriptionValue = commentControl.getCommentTypeDescriptionValue(commentTypeDescription);
                                            
                                            commentTypeDescriptionValue.setDescription(description);
                                            commentControl.updateCommentTypeDescriptionFromValue(commentTypeDescriptionValue, partyPK);
                                        }
                                    } finally {
                                        unlockEntity(commentType);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownCommentSequenceName.name(), commentSequenceName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateCommentTypeName.name(), commentTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCommentTypeName.name(), commentTypeName);
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }
        
        return result;
    }
    
}
