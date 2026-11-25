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

import com.echothree.control.user.comment.common.edit.CommentEdit;
import com.echothree.control.user.comment.common.edit.CommentEditFactory;
import com.echothree.control.user.comment.common.result.CommentResultFactory;
import com.echothree.control.user.comment.common.result.EditCommentResult;
import com.echothree.control.user.comment.common.spec.CommentUniversalSpec;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.comment.server.logic.CommentLogic;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.comment.server.entity.Comment;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditCommentCommand
        extends BaseAbstractEditCommand<CommentUniversalSpec, CommentEdit, EditCommentResult, Comment, Comment> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CommentName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("ClobComment", FieldType.STRING, false, 1L, null),
                // BlobComment is not validated
                new FieldDefinition("StringComment", FieldType.STRING, false, 1L, 512L)
        );
    }

    private Language language;
    private MimeType mimeType;
    private ByteArray blobComment;
    private String clobComment;
    private String stringComment;

    @Inject
    CommentControl commentControl;

    @Inject
    CommentLogic commentLogic;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    MimeTypeLogic mimeTypeLogic;

    /** Creates a new instance of EditCommentCommand */
    public EditCommentCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditCommentResult getResult() {
        return CommentResultFactory.getEditCommentResult();
    }

    @Override
    public CommentEdit getEdit() {
        return CommentEditFactory.getCommentEdit();
    }

    @Override
    public Comment getEntity(EditCommentResult result) {
        return commentLogic.getCommentByUniversalSpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public Comment getLockEntity(Comment comment) {
        return comment;
    }

    @Override
    public void fillInResult(EditCommentResult result, Comment comment) {
        result.setComment(commentControl.getCommentTransfer(getUserVisit(), comment));
    }

    @Override
    public void doLock(CommentEdit edit, Comment comment) {
        var commentDetail = comment.getLastDetail();
        var mimeType = commentDetail.getMimeType();

        edit.setLanguageIsoName(commentDetail.getLanguage().getLanguageIsoName());
        edit.setDescription(commentDetail.getDescription());
        edit.setMimeTypeName(mimeType == null ? null : mimeType.getLastDetail().getMimeTypeName());

        if(mimeType == null) {
            var commentString = commentControl.getCommentString(comment);

            if(commentString != null) {
                edit.setStringComment(commentString.getString());
            }
        } else {
            var entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

            // EntityAttributeTypes.BLOB.name() does not return anything in edit
            if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                var commentClob = commentControl.getCommentClob(comment);

                if(commentClob != null) {
                    edit.setClobComment(commentClob.getClob());
                }
            }
        }
    }

    @Override
    public void canUpdate(Comment comment) {
        language = languageLogic.getLanguageByName(this, edit.getLanguageIsoName());
        var mimeTypeName = edit.getMimeTypeName();
        mimeType = mimeTypeName == null ? null : mimeTypeLogic.getMimeTypeByName(this, mimeTypeName);
        blobComment = null;
        clobComment = null;
        stringComment = null;

        if(!hasExecutionErrors()) {
            var mimeTypeUsageType = comment.getLastDetail().getCommentType().getLastDetail().getMimeTypeUsageType();

            if(mimeType == null) {
                if(mimeTypeUsageType == null) {
                    stringComment = edit.getStringComment();

                    if(stringComment == null) {
                        addExecutionError(ExecutionErrors.MissingCommentString.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidMimeType.name());
                }
            } else if(mimeTypeUsageType == null) {
                addExecutionError(ExecutionErrors.InvalidMimeType.name());
            } else {
                mimeTypeLogic.getMimeTypeUsage(this, mimeType, mimeTypeUsageType);

                if(!hasExecutionErrors()) {
                    var entityAttributeTypeName = mimeType.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

                    switch(entityAttributeTypeName) {
                        case "BLOB" -> {
                            blobComment = edit.getBlobComment();

                            if(blobComment == null) {
                                addExecutionError(ExecutionErrors.MissingCommentBlob.name());
                            }
                        }
                        case "CLOB" -> {
                            clobComment = edit.getClobComment();

                            if(clobComment == null) {
                                addExecutionError(ExecutionErrors.MissingCommentClob.name());
                            }
                        }
                        default -> addExecutionError(ExecutionErrors.UnknownEntityAttributeTypeName.name(), entityAttributeTypeName);
                    }
                }
            }
        }
    }

    @Override
    public void doUpdate(Comment comment) {
        var partyPK = getPartyPK();
        var commentDetailValue = commentControl.getCommentDetailValueForUpdate(comment);

        commentDetailValue.setLanguagePK(language.getPrimaryKey());
        commentDetailValue.setDescription(edit.getDescription());
        commentDetailValue.setMimeTypePK(mimeType == null? null: mimeType.getPrimaryKey());
        commentControl.updateCommentFromValue(commentDetailValue, partyPK);

        var commentBlob = commentControl.getCommentBlobForUpdate(comment);

        if(commentBlob != null) {
            if(blobComment == null) {
                commentControl.deleteCommentBlob(commentBlob, partyPK);
            } else {
                var commentBlobValue = commentControl.getCommentBlobValue(commentBlob);

                commentBlobValue.setBlob(blobComment);
                commentControl.updateCommentBlobFromValue(commentBlobValue, partyPK);
            }
        } else if(blobComment != null) {
            commentControl.createCommentBlob(comment, blobComment, partyPK);
        }

        var commentClob = commentControl.getCommentClobForUpdate(comment);

        if(commentClob != null) {
            if(clobComment == null) {
                commentControl.deleteCommentClob(commentClob, partyPK);
            } else {
                var commentClobValue = commentControl.getCommentClobValue(commentClob);

                commentClobValue.setClob(clobComment);
                commentControl.updateCommentClobFromValue(commentClobValue, partyPK);
            }
        } else if(clobComment != null) {
            commentControl.createCommentClob(comment, clobComment, partyPK);
        }

        var commentString = commentControl.getCommentStringForUpdate(comment);

        if(commentString != null) {
            if(stringComment == null) {
                commentControl.deleteCommentString(commentString, partyPK);
            } else {
                var commentStringValue = commentControl.getCommentStringValue(commentString);

                commentStringValue.setString(stringComment);
                commentControl.updateCommentStringFromValue(commentStringValue, partyPK);
            }
        } else if(stringComment != null) {
            commentControl.createCommentString(comment, stringComment, partyPK);
        }
    }

}
