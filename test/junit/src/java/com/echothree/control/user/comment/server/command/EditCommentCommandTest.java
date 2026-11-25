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
import com.echothree.control.user.comment.common.spec.CommentSpecFactory;
import com.echothree.control.user.comment.common.spec.CommentUniversalSpec;
import com.echothree.model.control.comment.common.transfer.CommentTransfer;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.comment.server.logic.CommentLogic;
import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.comment.common.pk.CommentBlobPK;
import com.echothree.model.data.comment.common.pk.CommentClobPK;
import com.echothree.model.data.comment.common.pk.CommentDetailPK;
import com.echothree.model.data.comment.common.pk.CommentPK;
import com.echothree.model.data.comment.common.pk.CommentStringPK;
import com.echothree.model.data.comment.common.pk.CommentTypeDetailPK;
import com.echothree.model.data.comment.common.pk.CommentTypePK;
import com.echothree.model.data.comment.server.entity.Comment;
import com.echothree.model.data.comment.server.entity.CommentBlob;
import com.echothree.model.data.comment.server.entity.CommentClob;
import com.echothree.model.data.comment.server.entity.CommentDetail;
import com.echothree.model.data.comment.server.entity.CommentString;
import com.echothree.model.data.comment.server.entity.CommentType;
import com.echothree.model.data.comment.server.entity.CommentTypeDetail;
import com.echothree.model.data.comment.server.value.CommentBlobValue;
import com.echothree.model.data.comment.server.value.CommentClobValue;
import com.echothree.model.data.comment.server.value.CommentDetailValue;
import com.echothree.model.data.comment.server.value.CommentStringValue;
import com.echothree.model.data.comment.server.value.CommentTypeDetailValue;
import com.echothree.model.data.comment.server.value.CommentTypeValue;
import com.echothree.model.data.comment.server.value.CommentValue;
import com.echothree.model.data.core.common.pk.EntityAttributeTypePK;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.common.pk.MimeTypeDetailPK;
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.common.pk.MimeTypeUsageTypePK;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeDetail;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.core.server.value.EntityAttributeTypeValue;
import com.echothree.model.data.core.server.value.MimeTypeDetailValue;
import com.echothree.model.data.core.server.value.MimeTypeUsageTypeValue;
import com.echothree.model.data.core.server.value.MimeTypeValue;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.value.LanguageValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class EditCommentCommandTest {

    private static class TestCommand extends EditCommentCommand {
        private String attributeType;
        private boolean requiresMimeType;
        private boolean missingLanguage;
        private boolean missingMimeType;
        private boolean missingUsage;
        private boolean missingComment;
        private boolean updated;
        private boolean transferred;
        private final List<String> operations = new ArrayList<>();
        private final ByteArray bytes = new ByteArray(new byte[] { 1, 2, 3 });
        private final Language languageEntity = new Language(new LanguageValue(new LanguagePK(1L), "en", false, 0), EntityPermission.READ_ONLY);
        private final MimeTypeUsageType usageType = new MimeTypeUsageType(new MimeTypeUsageTypeValue(
                new MimeTypeUsageTypePK(2L), "COMMENT", false, 0), EntityPermission.READ_ONLY);
        private final CommentType commentType = new CommentType(new CommentTypeValue(new CommentTypePK(3L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public CommentTypeDetail getLastDetail() {
                return new CommentTypeDetail(new CommentTypeDetailValue(new CommentTypeDetailPK(4L), getPrimaryKey(),
                        new EntityTypePK(1L), "TYPE", null, null, null, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                    @Override
                    public MimeTypeUsageType getMimeTypeUsageType() {
                        return requiresMimeType ? usageType : null;
                    }
                };
            }
        };
        private final MimeType mimeTypeEntity = new MimeType(new MimeTypeValue(new MimeTypePK(5L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public MimeTypeDetail getLastDetail() {
                return new MimeTypeDetail(new MimeTypeDetailValue(new MimeTypeDetailPK(6L), getPrimaryKey(),
                        "text/plain", new EntityAttributeTypePK(1L), false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                    @Override
                    public EntityAttributeType getEntityAttributeType() {
                        return new EntityAttributeType(new EntityAttributeTypeValue(new EntityAttributeTypePK(1L), attributeType), EntityPermission.READ_ONLY);
                    }
                };
            }
        };
        private final Comment comment = new Comment(new CommentValue(new CommentPK(7L), null, null), EntityPermission.READ_WRITE) {
            @Override
            public CommentDetail getLastDetail() {
                return new CommentDetail(detailValue(), EntityPermission.READ_ONLY) {
                    @Override
                    public Language getLanguage() {
                        return languageEntity;
                    }

                    @Override
                    public MimeType getMimeType() {
                        return requiresMimeType ? mimeTypeEntity : null;
                    }

                    @Override
                    public CommentType getCommentType() {
                        return commentType;
                    }
                };
            }
        };

        private CommentDetailValue detailValue() {
            return new CommentDetailValue(new CommentDetailPK(8L), comment.getPrimaryKey(), "COMMENT", commentType.getPrimaryKey(),
                    new EntityInstancePK(1L), new EntityInstancePK(2L), languageEntity.getPrimaryKey(), "old description",
                    requiresMimeType ? mimeTypeEntity.getPrimaryKey() : null, 0L, Long.MAX_VALUE);
        }

        TestCommand(String contentType, boolean existingContent) {
            attributeType = contentType;
            requiresMimeType = !"STRING".equals(contentType);
            editMode = EditMode.UPDATE;
            spec = CommentSpecFactory.getCommentUniversalSpec();
            spec.setCommentName("COMMENT");
            edit = CommentEditFactory.getCommentEdit();
            edit.setLanguageIsoName("en");
            edit.setDescription("new description");
            edit.setMimeTypeName(requiresMimeType ? "text/plain" : null);
            edit.setStringComment("new string");
            edit.setClobComment("new clob");
            edit.setBlobComment(bytes);
            languageLogic = new LanguageLogic() {
                @Override
                public Language getLanguageByName(ExecutionErrorAccumulator eea, String languageIsoName) {
                    assertEquals("en", languageIsoName);
                    if(missingLanguage) {
                        eea.addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                    return missingLanguage ? null : languageEntity;
                }
            };
            mimeTypeLogic = new MimeTypeLogic() {
                @Override
                public MimeType getMimeTypeByName(ExecutionErrorAccumulator eea, String mimeTypeName) {
                    assertEquals("text/plain", mimeTypeName);
                    if(missingMimeType) {
                        eea.addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
                    }
                    return missingMimeType ? null : mimeTypeEntity;
                }

                @Override
                public com.echothree.model.data.core.server.entity.MimeTypeUsage getMimeTypeUsage(ExecutionErrorAccumulator eea,
                        MimeType mimeType, MimeTypeUsageType mimeTypeUsageType) {
                    assertSame(mimeTypeEntity, mimeType);
                    assertSame(usageType, mimeTypeUsageType);
                    if(missingUsage) {
                        eea.addExecutionError(ExecutionErrors.UnknownMimeTypeUsage.name(), "text/plain", "COMMENT");
                    }
                    return null;
                }
            };
            commentLogic = new CommentLogic() {
                @Override
                public Comment getCommentByUniversalSpec(ExecutionErrorAccumulator eea, CommentUniversalSpec universalSpec,
                        EntityPermission entityPermission) {
                    assertSame(spec, universalSpec);
                    assertEquals(editMode == EditMode.UPDATE ? EntityPermission.READ_WRITE : EntityPermission.READ_ONLY, entityPermission);
                    if(missingComment) {
                        eea.addExecutionError(ExecutionErrors.UnknownCommentName.name(), universalSpec.getCommentName());
                    }
                    return missingComment ? null : comment;
                }
            };
            commentControl = new CommentControl() {
                @Override
                public CommentDetailValue getCommentDetailValueForUpdate(Comment selectedComment) {
                    assertSame(comment, selectedComment);
                    return detailValue();
                }

                @Override
                public void updateCommentFromValue(CommentDetailValue value, BasePK updatedBy) {
                    assertEquals(languageEntity.getPrimaryKey(), value.getLanguagePK());
                    assertEquals("new description", value.getDescription());
                    assertEquals(requiresMimeType ? mimeTypeEntity.getPrimaryKey() : null, value.getMimeTypePK());
                    updated = true;
                }

                @Override
                public CommentTransfer getCommentTransfer(UserVisit userVisit, Comment selectedComment) {
                    assertSame(comment, selectedComment);
                    transferred = true;
                    return null;
                }
                @Override
                public CommentBlob getCommentBlob(Comment selectedComment) {
                    return new CommentBlob(new CommentBlobValue(new CommentBlobPK(9L), selectedComment.getPrimaryKey(),
                            bytes, 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE);
                }

                @Override
                public CommentBlob getCommentBlobForUpdate(Comment selectedComment) {
                    return existingContent ? getCommentBlob(selectedComment) : null;
                }

                @Override
                public CommentBlob createCommentBlob(Comment selectedComment, ByteArray blob, BasePK createdBy) {
                    assertEquals(bytes, blob);
                    operations.add("createBlob");
                    return null;
                }

                @Override
                public void updateCommentBlobFromValue(CommentBlobValue value, BasePK updatedBy) {
                    assertEquals(bytes, value.getBlob());
                    operations.add("updateBlob");
                }

                @Override
                public void deleteCommentBlob(CommentBlob commentBlob, BasePK deletedBy) {
                    operations.add("deleteBlob");
                }

                @Override
                public CommentClob getCommentClob(Comment selectedComment) {
                    return new CommentClob(new CommentClobValue(new CommentClobPK(9L), selectedComment.getPrimaryKey(),
                            "old clob", 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE);
                }

                @Override
                public CommentClob getCommentClobForUpdate(Comment selectedComment) {
                    return existingContent ? getCommentClob(selectedComment) : null;
                }

                @Override
                public CommentClob createCommentClob(Comment selectedComment, String clob, BasePK createdBy) {
                    assertEquals("new clob", clob);
                    operations.add("createClob");
                    return null;
                }

                @Override
                public void updateCommentClobFromValue(CommentClobValue value, BasePK updatedBy) {
                    assertEquals("new clob", value.getClob());
                    operations.add("updateClob");
                }

                @Override
                public void deleteCommentClob(CommentClob commentClob, BasePK deletedBy) {
                    operations.add("deleteClob");
                }

                @Override
                public CommentString getCommentString(Comment selectedComment) {
                    return new CommentString(new CommentStringValue(new CommentStringPK(9L), selectedComment.getPrimaryKey(),
                            "old string", 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE);
                }

                @Override
                public CommentString getCommentStringForUpdate(Comment selectedComment) {
                    return existingContent ? getCommentString(selectedComment) : null;
                }

                @Override
                public CommentString createCommentString(Comment selectedComment, String string, BasePK createdBy) {
                    assertEquals("new string", string);
                    operations.add("createString");
                    return null;
                }

                @Override
                public void updateCommentStringFromValue(CommentStringValue value, BasePK updatedBy) {
                    assertEquals("new string", value.getString());
                    operations.add("updateString");
                }

                @Override
                public void deleteCommentString(CommentString commentString, BasePK deletedBy) {
                    operations.add("deleteString");
                }

            };
        }

        @Override
        public PartyPK getPartyPK() {
            return null;
        }

        @Override
        public UserVisit getUserVisit() {
            return null;
        }

        @Override
        public EntityLockTransfer getEntityLockTransfer(BaseEntity lockEntity) {
            assertSame(comment, lockEntity);
            return null;
        }

        void setMode(EditMode requestedEditMode) {
            editMode = requestedEditMode;
        }

        void clearMimeType() {
            edit.setMimeTypeName(null);
        }

        boolean hasValidationErrors() {
            return validate().getHasErrors();
        }

        void removeContent() {
            edit.setStringComment(null);
            edit.setClobComment(null);
            edit.setBlobComment(null);
        }

        void assertValidationResult(boolean missingEntity) {
            edit.setStringComment("x".repeat(513));
            missingComment = missingEntity;
            var result = validate();
            assertTrue(result.getHasErrors());
            assertNotNull(getBaseResultAfterErrors());
            assertEquals(missingEntity, hasExecutionErrors());
            assertEquals(!missingEntity, transferred);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "STRING", "CLOB", "BLOB" })
    public void writesSelectedPayloadAndDeletesObsoletePayloads(String contentType) {
        for(boolean existingContent : new boolean[] { false, true }) {
            var command = new TestCommand(contentType, existingContent);
            command.canUpdate(command.comment);
            assertFalse(command.hasExecutionErrors());
            command.doUpdate(command.comment);
            assertTrue(command.updated);
            for(var otherContentType : List.of("Blob", "Clob", "String")) {
                var selected = otherContentType.equalsIgnoreCase(contentType);
                assertEquals(selected || existingContent, command.operations.contains(
                        selected ? (existingContent ? "update" : "create") + otherContentType : "delete" + otherContentType));
            }
            assertEquals(existingContent ? 3 : 1, command.operations.size());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "STRING", "CLOB", "BLOB" })
    public void lockCopiesEditableContentAndDoesNotReturnBlob(String contentType) {
        var command = new TestCommand(contentType, true);
        var edit = command.getEdit();
        command.doLock(edit, command.comment);
        assertEquals("en", edit.getLanguageIsoName());
        assertEquals("old description", edit.getDescription());
        assertEquals("STRING".equals(contentType) ? null : "text/plain", edit.getMimeTypeName());
        assertEquals("STRING".equals(contentType) ? "old string" : null, edit.getStringComment());
        assertEquals("CLOB".equals(contentType) ? "old clob" : null, edit.getClobComment());
        assertNull(edit.getBlobComment());
    }

    @ParameterizedTest
    @ValueSource(strings = { "STRING", "CLOB", "BLOB" })
    public void rejectsMissingPayloadBeforeWriting(String contentType) {
        var command = new TestCommand(contentType, true);
        command.removeContent();
        command.canUpdate(command.comment);
        var expectedError = switch(contentType) {
            case "STRING" -> ExecutionErrors.MissingCommentString;
            case "CLOB" -> ExecutionErrors.MissingCommentClob;
            case "BLOB" -> ExecutionErrors.MissingCommentBlob;
            default -> throw new IllegalArgumentException(contentType);
        };
        assertEquals(expectedError.name(), command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertFalse(command.updated);
        assertTrue(command.operations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = { "language", "mimeType", "usage", "unsupported", "missingMimeType", "unexpectedMimeType" })
    public void rejectsInvalidLanguageAndMimeTypeBeforeWriting(String failure) {
        var command = new TestCommand("CLOB", true);
        switch(failure) {
            case "language" -> command.missingLanguage = true;
            case "mimeType" -> command.missingMimeType = true;
            case "usage" -> command.missingUsage = true;
            case "unsupported" -> command.attributeType = "STRING";
            case "missingMimeType" -> command.clearMimeType();
            case "unexpectedMimeType" -> command.requiresMimeType = false;
            default -> throw new IllegalArgumentException(failure);
        }
        command.canUpdate(command.comment);
        assertTrue(command.hasExecutionErrors());
        assertFalse(command.updated);
        assertTrue(command.operations.isEmpty());
    }

    @ParameterizedTest
    @EnumSource(EditMode.class)
    public void resolvesCommentWithRequestedPermissionAndUsesItAsLockTarget(EditMode editMode) {
        var command = new TestCommand("STRING", false);
        command.setMode(editMode);
        assertSame(command.comment, command.getEntity(command.getResult()));
        assertSame(command.comment, command.getLockEntity(command.comment));
    }

    @Test
    public void plainStringCommentPassesValidationWithoutMimeType() {
        var command = new TestCommand("STRING", false);
        assertFalse(command.hasValidationErrors());
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void invalidEditRetainsResultWithoutDereferencingMissingComment(boolean missingEntity) {
        new TestCommand("STRING", false).assertValidationResult(missingEntity);
    }

}
