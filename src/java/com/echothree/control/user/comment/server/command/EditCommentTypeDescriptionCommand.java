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
import com.echothree.control.user.comment.common.edit.CommentTypeDescriptionEdit;
import com.echothree.control.user.comment.common.result.CommentResultFactory;
import com.echothree.control.user.comment.common.result.EditCommentTypeDescriptionResult;
import com.echothree.control.user.comment.common.spec.CommentTypeDescriptionSpec;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.comment.server.logic.CommentTypeLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.comment.server.entity.CommentTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditCommentTypeDescriptionCommand
        extends BaseEditCommand<CommentTypeDescriptionSpec, CommentTypeDescriptionEdit, EditCommentTypeDescriptionResult, CommentTypeDescription, CommentType> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("CommentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    CommentControl commentControl;

    @Inject
    CommentTypeLogic commentTypeLogic;

    @Inject
    LanguageLogic languageLogic;

    /** Creates a new instance of EditCommentTypeDescriptionCommand */
    public EditCommentTypeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditCommentTypeDescriptionResult getResult() {
        return CommentResultFactory.getEditCommentTypeDescriptionResult();
    }

    @Override
    public CommentTypeDescriptionEdit getEdit() {
        return CommentEditFactory.getCommentTypeDescriptionEdit();
    }

    @Override
    public CommentTypeDescription getEntity(EditCommentTypeDescriptionResult result) {
        CommentTypeDescription commentTypeDescription = null;
        var componentVendorName = spec.getComponentVendorName();
        var entityTypeName = spec.getEntityTypeName();
        var commentTypeName = spec.getCommentTypeName();
        var languageIsoName = spec.getLanguageIsoName();
        var commentType = commentTypeLogic.getCommentTypeByName(this, componentVendorName, entityTypeName, commentTypeName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            commentTypeDescription = commentControl.getCommentTypeDescription(commentType, language,
                    editModeToEntityPermission(editMode));

            if(commentTypeDescription == null) {
                addExecutionError(ExecutionErrors.UnknownCommentTypeDescription.name(), componentVendorName, entityTypeName, commentTypeName, languageIsoName);
            }
        }

        return commentTypeDescription;
    }

    @Override
    public CommentType getLockEntity(CommentTypeDescription commentTypeDescription) {
        return commentTypeDescription.getCommentType();
    }

    @Override
    public void fillInResult(EditCommentTypeDescriptionResult result, CommentTypeDescription commentTypeDescription) {
        result.setCommentTypeDescription(commentControl.getCommentTypeDescriptionTransfer(getUserVisit(), commentTypeDescription));
    }

    @Override
    public void doLock(CommentTypeDescriptionEdit edit, CommentTypeDescription commentTypeDescription) {
        edit.setDescription(commentTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(CommentTypeDescription commentTypeDescription) {
        var commentTypeDescriptionValue = commentControl.getCommentTypeDescriptionValue(commentTypeDescription);

        commentTypeDescriptionValue.setDescription(edit.getDescription());

        commentControl.updateCommentTypeDescriptionFromValue(commentTypeDescriptionValue, getPartyPK());
    }

}
