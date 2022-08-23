// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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
import com.echothree.control.user.comment.common.result.EditCommentTypeResult;
import com.echothree.control.user.comment.common.spec.CommentTypeSpec;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.comment.server.entity.CommentTypeDescription;
import com.echothree.model.data.comment.server.entity.CommentTypeDetail;
import com.echothree.model.data.comment.server.value.CommentTypeDescriptionValue;
import com.echothree.model.data.comment.server.value.CommentTypeDetailValue;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
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
    public EditCommentTypeCommand(UserVisitPK userVisitPK, EditCommentTypeForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        EditCommentTypeResult result = CommentResultFactory.getEditCommentTypeResult();
        String componentVendorName = spec.getComponentVendorName();
        ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            String entityTypeName = spec.getEntityTypeName();
            EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
            
            if(entityType != null) {
                var commentControl = Session.getModelController(CommentControl.class);
                
                if(editMode.equals(EditMode.LOCK)) {
                    String commentTypeName = spec.getCommentTypeName();
                    CommentType commentType = commentControl.getCommentTypeByName(entityType, commentTypeName);
                    
                    if(commentType != null) {
                        result.setCommentType(commentControl.getCommentTypeTransfer(getUserVisit(), commentType));
                        
                        if(lockEntity(commentType)) {
                            CommentTypeDescription commentTypeDescription = commentControl.getCommentTypeDescription(commentType, getPreferredLanguage());
                            CommentTypeEdit edit = CommentEditFactory.getCommentTypeEdit();
                            CommentTypeDetail commentTypeDetail = commentType.getLastDetail();
                            Sequence commentSequence = commentTypeDetail.getCommentSequence();
                            
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
                    String commentTypeName = spec.getCommentTypeName();
                    CommentType commentType = commentControl.getCommentTypeByNameForUpdate(entityType, commentTypeName);
                    
                    if(commentType != null) {
                        commentTypeName = edit.getCommentTypeName();
                        CommentType duplicateCommentType = commentControl.getCommentTypeByName(entityType, commentTypeName);
                        
                        if(duplicateCommentType == null || commentType.equals(duplicateCommentType)) {
                            String commentSequenceName = edit.getCommentSequenceName();
                            Sequence commentSequence = null;
                            
                            if(commentSequenceName != null) {
                                var sequenceControl = Session.getModelController(SequenceControl.class);
                                SequenceType sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.COMMENT.name());
                                
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
                                        CommentTypeDetailValue commentTypeDetailValue = commentControl.getCommentTypeDetailValueForUpdate(commentType);
                                        CommentTypeDescription commentTypeDescription = commentControl.getCommentTypeDescriptionForUpdate(commentType, getPreferredLanguage());
                                        String description = edit.getDescription();
                                        
                                        commentTypeDetailValue.setCommentTypeName(edit.getCommentTypeName());
                                        commentTypeDetailValue.setCommentSequencePK(commentSequence == null? null: commentSequence.getPrimaryKey());
                                        commentTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                        
                                        commentControl.updateCommentTypeFromValue(commentTypeDetailValue, partyPK);
                                        
                                        if(commentTypeDescription == null && description != null) {
                                            commentControl.createCommentTypeDescription(commentType, getPreferredLanguage(), description, partyPK);
                                        } else if(commentTypeDescription != null && description == null) {
                                            commentControl.deleteCommentTypeDescription(commentTypeDescription, partyPK);
                                        } else if(commentTypeDescription != null && description != null) {
                                            CommentTypeDescriptionValue commentTypeDescriptionValue = commentControl.getCommentTypeDescriptionValue(commentTypeDescription);
                                            
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
