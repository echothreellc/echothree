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

package com.echothree.control.user.comment.server.command;

import com.echothree.control.user.comment.common.edit.CommentEditFactory;
import com.echothree.control.user.comment.common.edit.CommentUsageTypeDescriptionEdit;
import com.echothree.control.user.comment.common.form.EditCommentUsageTypeDescriptionForm;
import com.echothree.control.user.comment.common.result.CommentResultFactory;
import com.echothree.control.user.comment.common.result.EditCommentUsageTypeDescriptionResult;
import com.echothree.control.user.comment.common.spec.CommentUsageTypeDescriptionSpec;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.comment.server.entity.CommentUsageType;
import com.echothree.model.data.comment.server.entity.CommentUsageTypeDescription;
import com.echothree.model.data.comment.server.value.CommentUsageTypeDescriptionValue;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditCommentUsageTypeDescriptionCommand
        extends BaseEditCommand<CommentUsageTypeDescriptionSpec, CommentUsageTypeDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("CommentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommentUsageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditCommentUsageTypeDescriptionCommand */
    public EditCommentUsageTypeDescriptionCommand(UserVisitPK userVisitPK, EditCommentUsageTypeDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var coreControl = getCoreControl();
        EditCommentUsageTypeDescriptionResult result = CommentResultFactory.getEditCommentUsageTypeDescriptionResult();
        String componentVendorName = spec.getComponentVendorName();
        ComponentVendor componentVendor = coreControl.getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            String entityTypeName = spec.getEntityTypeName();
            EntityType entityType = coreControl.getEntityTypeByName(componentVendor, entityTypeName);
            
            if(entityType != null) {
                var commentControl = Session.getModelController(CommentControl.class);
                String commentTypeName = spec.getCommentTypeName();
                CommentType commentType = commentControl.getCommentTypeByName(entityType, commentTypeName);
                
                if(commentType != null) {
                    String commentUsageTypeName = spec.getCommentUsageTypeName();
                    CommentUsageType commentUsageType = commentControl.getCommentUsageTypeByName(commentType, commentUsageTypeName);
                    
                    if(commentUsageType != null) {
                        var partyControl = Session.getModelController(PartyControl.class);
                        String languageIsoName = spec.getLanguageIsoName();
                        Language language = partyControl.getLanguageByIsoName(languageIsoName);
                        
                        if(language != null) {
                            if(editMode.equals(EditMode.LOCK)) {
                                CommentUsageTypeDescription commentUsageTypeDescription = commentControl.getCommentUsageTypeDescription(commentUsageType, language);
                                
                                if(commentUsageTypeDescription != null) {
                                    result.setCommentUsageTypeDescription(commentControl.getCommentUsageTypeDescriptionTransfer(getUserVisit(), commentUsageTypeDescription));
                                    
                                    if(lockEntity(commentUsageType)) {
                                        CommentUsageTypeDescriptionEdit edit = CommentEditFactory.getCommentUsageTypeDescriptionEdit();
                                        
                                        result.setEdit(edit);
                                        edit.setDescription(commentUsageTypeDescription.getDescription());
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                    }
                                    
                                    result.setEntityLock(getEntityLockTransfer(commentUsageType));
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownCommentUsageTypeDescription.name());
                                }
                            } else if(editMode.equals(EditMode.UPDATE)) {
                                CommentUsageTypeDescriptionValue commentUsageTypeDescriptionValue = commentControl.getCommentUsageTypeDescriptionValueForUpdate(commentUsageType, language);
                                
                                if(commentUsageTypeDescriptionValue != null) {
                                    if(lockEntityForUpdate(commentUsageType)) {
                                        try {
                                            String description = edit.getDescription();
                                            
                                            commentUsageTypeDescriptionValue.setDescription(description);
                                            
                                            commentControl.updateCommentUsageTypeDescriptionFromValue(commentUsageTypeDescriptionValue, getPartyPK());
                                        } finally {
                                            unlockEntity(commentUsageType);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownCommentUsageTypeDescription.name());
                                }
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCommentUsageTypeName.name(), commentUsageTypeName);
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
