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
import com.echothree.control.user.comment.common.edit.CommentTypeEdit;
import com.echothree.control.user.comment.common.result.CommentResultFactory;
import com.echothree.control.user.comment.common.result.EditCommentTypeResult;
import com.echothree.control.user.comment.common.spec.CommentTypeUniversalSpec;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.comment.server.logic.CommentTypeLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceLogic;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditCommentTypeCommand
        extends BaseEditCommand<CommentTypeUniversalSpec, CommentTypeEdit, EditCommentTypeResult, CommentType, CommentType> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("CommentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CommentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommentSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    private Sequence commentSequence;

    @Inject
    CommentControl commentControl;

    @Inject
    CommentTypeLogic commentTypeLogic;

    @Inject
    SequenceLogic sequenceLogic;

    /** Creates a new instance of EditCommentTypeCommand */
    public EditCommentTypeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditCommentTypeResult getResult() {
        return CommentResultFactory.getEditCommentTypeResult();
    }

    @Override
    public CommentTypeEdit getEdit() {
        return CommentEditFactory.getCommentTypeEdit();
    }

    @Override
    public CommentType getEntity(EditCommentTypeResult result) {
        return commentTypeLogic.getCommentTypeByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public CommentType getLockEntity(CommentType commentType) {
        return commentType;
    }

    @Override
    public void fillInResult(EditCommentTypeResult result, CommentType commentType) {
        result.setCommentType(commentControl.getCommentTypeTransfer(getUserVisit(), commentType));
    }

    @Override
    public void doLock(CommentTypeEdit edit, CommentType commentType) {
        var commentTypeDescription = commentControl.getCommentTypeDescription(commentType, getPreferredLanguage());
        var commentTypeDetail = commentType.getLastDetail();
        var commentSequence = commentTypeDetail.getCommentSequence();

        edit.setCommentTypeName(commentTypeDetail.getCommentTypeName());
        edit.setCommentSequenceName(commentSequence == null? null: commentSequence.getLastDetail().getSequenceName());
        edit.setSortOrder(commentTypeDetail.getSortOrder().toString());

        if(commentTypeDescription != null) {
            edit.setDescription(commentTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(CommentType commentType) {
        var entityType = commentType.getLastDetail().getEntityType();
        var commentTypeName = edit.getCommentTypeName();
        var duplicateCommentType = commentControl.getCommentTypeByName(entityType, commentTypeName);
        var commentSequenceName = edit.getCommentSequenceName();

        if(duplicateCommentType != null && !commentType.equals(duplicateCommentType)) {
            var entityTypeDetail = entityType.getLastDetail();

            addExecutionError(ExecutionErrors.DuplicateCommentTypeName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), commentTypeName);
        }

        commentSequence = commentSequenceName == null ? null : sequenceLogic.getSequenceByName(this,
                SequenceTypes.COMMENT.name(), commentSequenceName);
    }

    @Override
    public void doUpdate(CommentType commentType) {
        var partyPK = getPartyPK();
        var commentTypeDetailValue = commentControl.getCommentTypeDetailValueForUpdate(commentType);
        var commentTypeDescription = commentControl.getCommentTypeDescriptionForUpdate(commentType, getPreferredLanguage());
        var description = edit.getDescription();

        commentTypeDetailValue.setCommentTypeName(edit.getCommentTypeName());
        commentTypeDetailValue.setCommentSequencePK(commentSequence == null ? null : commentSequence.getPrimaryKey());
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
    }

}
