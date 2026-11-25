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

import com.echothree.control.user.comment.common.edit.CommentEditFactory;
import com.echothree.control.user.comment.common.edit.CommentUsageTypeDescriptionEdit;
import com.echothree.control.user.comment.common.result.CommentResultFactory;
import com.echothree.control.user.comment.common.result.EditCommentUsageTypeDescriptionResult;
import com.echothree.control.user.comment.common.spec.CommentUsageTypeDescriptionSpec;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.comment.server.logic.CommentUsageTypeLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.comment.server.entity.CommentUsageType;
import com.echothree.model.data.comment.server.entity.CommentUsageTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditCommentUsageTypeDescriptionCommand
        extends BaseAbstractEditCommand<CommentUsageTypeDescriptionSpec, CommentUsageTypeDescriptionEdit, EditCommentUsageTypeDescriptionResult, CommentUsageTypeDescription, CommentUsageType> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("CommentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommentUsageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    CommentControl commentControl;

    @Inject
    CommentUsageTypeLogic commentUsageTypeLogic;

    @Inject
    LanguageLogic languageLogic;

    /** Creates a new instance of EditCommentUsageTypeDescriptionCommand */
    public EditCommentUsageTypeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditCommentUsageTypeDescriptionResult getResult() {
        return CommentResultFactory.getEditCommentUsageTypeDescriptionResult();
    }

    @Override
    public CommentUsageTypeDescriptionEdit getEdit() {
        return CommentEditFactory.getCommentUsageTypeDescriptionEdit();
    }

    @Override
    public CommentUsageTypeDescription getEntity(EditCommentUsageTypeDescriptionResult result) {
        CommentUsageTypeDescription commentUsageTypeDescription = null;
        var componentVendorName = spec.getComponentVendorName();
        var entityTypeName = spec.getEntityTypeName();
        var commentTypeName = spec.getCommentTypeName();
        var commentUsageTypeName = spec.getCommentUsageTypeName();
        var languageIsoName = spec.getLanguageIsoName();
        var commentUsageType = commentUsageTypeLogic.getCommentUsageTypeByName(this, componentVendorName, entityTypeName, commentTypeName, commentUsageTypeName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            commentUsageTypeDescription = commentControl.getCommentUsageTypeDescription(commentUsageType, language,
                    editModeToEntityPermission(editMode));

            if(commentUsageTypeDescription == null) {
                addExecutionError(ExecutionErrors.UnknownCommentUsageTypeDescription.name(), componentVendorName, entityTypeName, commentTypeName, commentUsageTypeName, languageIsoName);
            }
        }

        return commentUsageTypeDescription;
    }

    @Override
    public CommentUsageType getLockEntity(CommentUsageTypeDescription commentUsageTypeDescription) {
        return commentUsageTypeDescription.getCommentUsageType();
    }

    @Override
    public void fillInResult(EditCommentUsageTypeDescriptionResult result, CommentUsageTypeDescription commentUsageTypeDescription) {
        result.setCommentUsageTypeDescription(commentControl.getCommentUsageTypeDescriptionTransfer(getUserVisit(), commentUsageTypeDescription));
    }

    @Override
    public void doLock(CommentUsageTypeDescriptionEdit edit, CommentUsageTypeDescription commentUsageTypeDescription) {
        edit.setDescription(commentUsageTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(CommentUsageTypeDescription commentUsageTypeDescription) {
        var commentUsageTypeDescriptionValue = commentControl.getCommentUsageTypeDescriptionValue(commentUsageTypeDescription);

        commentUsageTypeDescriptionValue.setDescription(edit.getDescription());

        commentControl.updateCommentUsageTypeDescriptionFromValue(commentUsageTypeDescriptionValue, getPartyPK());
    }

}
