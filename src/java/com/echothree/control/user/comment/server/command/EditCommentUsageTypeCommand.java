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
import com.echothree.control.user.comment.common.edit.CommentUsageTypeEdit;
import com.echothree.control.user.comment.common.result.CommentResultFactory;
import com.echothree.control.user.comment.common.result.EditCommentUsageTypeResult;
import com.echothree.control.user.comment.common.spec.CommentUsageTypeUniversalSpec;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.comment.server.logic.CommentUsageTypeLogic;
import com.echothree.model.data.comment.server.entity.CommentUsageType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditCommentUsageTypeCommand
        extends BaseEditCommand<CommentUsageTypeUniversalSpec, CommentUsageTypeEdit, EditCommentUsageTypeResult, CommentUsageType, CommentUsageType> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("CommentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CommentUsageTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CommentUsageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SelectedByDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    @Inject
    CommentControl commentControl;

    @Inject
    CommentUsageTypeLogic commentUsageTypeLogic;

    /** Creates a new instance of EditCommentUsageTypeCommand */
    public EditCommentUsageTypeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditCommentUsageTypeResult getResult() {
        return CommentResultFactory.getEditCommentUsageTypeResult();
    }

    @Override
    public CommentUsageTypeEdit getEdit() {
        return CommentEditFactory.getCommentUsageTypeEdit();
    }

    @Override
    public CommentUsageType getEntity(EditCommentUsageTypeResult result) {
        return commentUsageTypeLogic.getCommentUsageTypeByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public CommentUsageType getLockEntity(CommentUsageType commentUsageType) {
        return commentUsageType;
    }

    @Override
    public void fillInResult(EditCommentUsageTypeResult result, CommentUsageType commentUsageType) {
        result.setCommentUsageType(commentControl.getCommentUsageTypeTransfer(getUserVisit(), commentUsageType));
    }

    @Override
    public void doLock(CommentUsageTypeEdit edit, CommentUsageType commentUsageType) {
        var commentUsageTypeDescription = commentControl.getCommentUsageTypeDescription(commentUsageType, getPreferredLanguage());
        var commentUsageTypeDetail = commentUsageType.getLastDetail();

        edit.setCommentUsageTypeName(commentUsageTypeDetail.getCommentUsageTypeName());
        edit.setSelectedByDefault(commentUsageTypeDetail.getSelectedByDefault().toString());
        edit.setSortOrder(commentUsageTypeDetail.getSortOrder().toString());

        if(commentUsageTypeDescription != null) {
            edit.setDescription(commentUsageTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(CommentUsageType commentUsageType) {
        var commentType = commentUsageType.getLastDetail().getCommentType();
        var commentUsageTypeName = edit.getCommentUsageTypeName();
        var duplicateCommentUsageType = commentControl.getCommentUsageTypeByName(commentType, commentUsageTypeName);

        if(duplicateCommentUsageType != null && !commentUsageType.equals(duplicateCommentUsageType)) {
            var commentTypeDetail = commentType.getLastDetail();
            var entityTypeDetail = commentTypeDetail.getEntityType().getLastDetail();

            addExecutionError(ExecutionErrors.DuplicateCommentUsageTypeName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(),
                    commentTypeDetail.getCommentTypeName(), commentUsageTypeName);
        }
    }

    @Override
    public void doUpdate(CommentUsageType commentUsageType) {
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
    }

}
