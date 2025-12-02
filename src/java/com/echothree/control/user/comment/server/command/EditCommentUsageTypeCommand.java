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
import com.echothree.control.user.comment.common.edit.CommentUsageTypeEdit;
import com.echothree.control.user.comment.common.form.EditCommentUsageTypeForm;
import com.echothree.control.user.comment.common.result.CommentResultFactory;
import com.echothree.control.user.comment.common.spec.CommentUsageTypeSpec;
import com.echothree.model.control.comment.server.control.CommentControl;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditCommentUsageTypeCommand
        extends BaseEditCommand<CommentUsageTypeSpec, CommentUsageTypeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("CommentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommentUsageTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CommentUsageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectedByDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditCommentUsageTypeCommand */
    public EditCommentUsageTypeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CommentResultFactory.getEditCommentUsageTypeResult();
        var componentVendorName = spec.getComponentVendorName();
        var componentVendor = componentControl.getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            var entityTypeName = spec.getEntityTypeName();
            var entityType = entityTypeControl.getEntityTypeByName(componentVendor, entityTypeName);
            
            if(entityType != null) {
                var commentControl = Session.getModelController(CommentControl.class);
                var commentTypeName = spec.getCommentTypeName();
                var commentType = commentControl.getCommentTypeByName(entityType, commentTypeName);
                
                if(commentType != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        var commentUsageTypeName = spec.getCommentUsageTypeName();
                        var commentUsageType = commentControl.getCommentUsageTypeByName(commentType, commentUsageTypeName);
                        
                        if(commentUsageType != null) {
                            result.setCommentUsageType(commentControl.getCommentUsageTypeTransfer(getUserVisit(), commentUsageType));
                            
                            if(lockEntity(commentUsageType)) {
                                var commentUsageTypeDescription = commentControl.getCommentUsageTypeDescription(commentUsageType, getPreferredLanguage());
                                var edit = CommentEditFactory.getCommentUsageTypeEdit();
                                var commentUsageTypeDetail = commentUsageType.getLastDetail();
                                
                                result.setEdit(edit);
                                edit.setCommentUsageTypeName(commentUsageTypeDetail.getCommentUsageTypeName());
                                edit.setSelectedByDefault(commentUsageTypeDetail.getSelectedByDefault().toString());
                                edit.setSortOrder(commentUsageTypeDetail.getSortOrder().toString());
                                
                                if(commentUsageTypeDescription != null)
                                    edit.setDescription(commentUsageTypeDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(commentUsageType));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCommentUsageTypeName.name(), commentUsageTypeName);
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        var commentUsageTypeName = spec.getCommentUsageTypeName();
                        var commentUsageType = commentControl.getCommentUsageTypeByNameForUpdate(commentType, commentUsageTypeName);
                        
                        if(commentUsageType != null) {
                            commentUsageTypeName = edit.getCommentUsageTypeName();
                            var duplicateCommentUsageType = commentControl.getCommentUsageTypeByName(commentType, commentUsageTypeName);
                            
                            if(duplicateCommentUsageType == null || commentUsageType.equals(duplicateCommentUsageType)) {
                                    if(lockEntityForUpdate(commentUsageType)) {
                                        try {
                                            var partyPK = getPartyPK();
                                            var commentUsageTypeDetailValue = commentControl.getCommentUsageTypeDetailValueForUpdate(commentUsageType);
                                            var commentUsageTypeDescription = commentControl.getCommentUsageTypeDescriptionForUpdate(commentUsageType, getPreferredLanguage());
                                            var description = edit.getDescription();
                                            
                                            commentUsageTypeDetailValue.setCommentUsageTypeName(edit.getCommentUsageTypeName());
                                            commentUsageTypeDetailValue.setSelectedByDefault(Boolean.valueOf(edit.getSelectedByDefault()));
                                            commentUsageTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                            
                                            commentControl.updateCommentUsageTypeFromValue(commentUsageTypeDetailValue, partyPK);
                                            
                                            if(commentUsageTypeDescription == null && description != null) {
                                                commentControl.createCommentUsageTypeDescription(commentUsageType, getPreferredLanguage(), description, partyPK);
                                            } else if(commentUsageTypeDescription != null && description == null) {
                                                commentControl.deleteCommentUsageTypeDescription(commentUsageTypeDescription, partyPK);
                                            } else if(commentUsageTypeDescription != null && description != null) {
                                                var commentUsageTypeDescriptionValue = commentControl.getCommentUsageTypeDescriptionValue(commentUsageTypeDescription);
                                                
                                                commentUsageTypeDescriptionValue.setDescription(description);
                                                commentControl.updateCommentUsageTypeDescriptionFromValue(commentUsageTypeDescriptionValue, partyPK);
                                            }
                                        } finally {
                                            unlockEntity(commentUsageType);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                            } else {
                                addExecutionError(ExecutionErrors.DuplicateCommentUsageTypeName.name(), commentUsageTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownCommentUsageTypeName.name(), commentUsageTypeName);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownCommentTypeName.name(), commentTypeName);
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
