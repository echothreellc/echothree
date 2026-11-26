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

package com.echothree.control.user.contact.server.command;

import com.echothree.control.user.contact.common.edit.ContactEditFactory;
import com.echothree.control.user.contact.common.spec.ContactSpecFactory;
import com.echothree.model.control.contact.common.transfer.PostalAddressLineElementTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.contact.server.logic.PostalAddressElementTypeLogic;
import com.echothree.model.control.contact.server.logic.PostalAddressLineElementLogic;
import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.data.contact.common.pk.PostalAddressElementTypePK;
import com.echothree.model.data.contact.common.pk.PostalAddressFormatDetailPK;
import com.echothree.model.data.contact.common.pk.PostalAddressFormatPK;
import com.echothree.model.data.contact.common.pk.PostalAddressLineDetailPK;
import com.echothree.model.data.contact.common.pk.PostalAddressLineElementPK;
import com.echothree.model.data.contact.common.pk.PostalAddressLinePK;
import com.echothree.model.data.contact.server.entity.PostalAddressElementType;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.model.data.contact.server.entity.PostalAddressFormatDetail;
import com.echothree.model.data.contact.server.entity.PostalAddressLine;
import com.echothree.model.data.contact.server.entity.PostalAddressLineDetail;
import com.echothree.model.data.contact.server.entity.PostalAddressLineElement;
import com.echothree.model.data.contact.server.value.PostalAddressElementTypeValue;
import com.echothree.model.data.contact.server.value.PostalAddressFormatDetailValue;
import com.echothree.model.data.contact.server.value.PostalAddressFormatValue;
import com.echothree.model.data.contact.server.value.PostalAddressLineDetailValue;
import com.echothree.model.data.contact.server.value.PostalAddressLineElementValue;
import com.echothree.model.data.contact.server.value.PostalAddressLineValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class EditPostalAddressLineElementCommandTest {

    private static class TestCommand extends EditPostalAddressLineElementCommand {
        private final PostalAddressFormat postalAddressFormat = new PostalAddressFormat(
                new PostalAddressFormatValue(new PostalAddressFormatPK(1L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public PostalAddressFormatDetail getLastDetail() {
                return new PostalAddressFormatDetail(new PostalAddressFormatDetailValue(new PostalAddressFormatDetailPK(2L),
                        getPrimaryKey(), "FORMAT", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        private final PostalAddressLine postalAddressLine = new PostalAddressLine(
                new PostalAddressLineValue(new PostalAddressLinePK(3L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public PostalAddressLineDetail getLastDetail() {
                return new PostalAddressLineDetail(new PostalAddressLineDetailValue(new PostalAddressLineDetailPK(4L),
                        getPrimaryKey(), postalAddressFormat.getPrimaryKey(), 10, null, false, null, false, false, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                    @Override
                    public PostalAddressFormat getPostalAddressFormat() {
                        return postalAddressFormat;
                    }
                };
            }
        };
        private final PostalAddressElementType originalType = new PostalAddressElementType(
                new PostalAddressElementTypeValue(new PostalAddressElementTypePK(5L), "OLD", false, 0), EntityPermission.READ_ONLY);
        private final PostalAddressElementType replacementType = new PostalAddressElementType(
                new PostalAddressElementTypeValue(new PostalAddressElementTypePK(6L), "NEW", false, 0), EntityPermission.READ_ONLY);
        private final PostalAddressLineElement element = new PostalAddressLineElement(value(7L), EntityPermission.READ_WRITE) {
            @Override
            public PostalAddressLine getPostalAddressLine() {
                return postalAddressLine;
            }

            @Override
            public PostalAddressElementType getPostalAddressElementType() {
                return originalType;
            }
        };
        private int duplicateMode;
        private int lookups;
        private boolean missingEntity;
        private boolean missingType;
        private boolean updated;
        private boolean transferred;

        private PostalAddressLineElementValue value(long primaryKey) {
            return new PostalAddressLineElementValue(new PostalAddressLineElementPK(primaryKey), postalAddressLine.getPrimaryKey(),
                    20, originalType.getPrimaryKey(), "old prefix", false, "old suffix", true, 0L, Long.MAX_VALUE);
        }

        TestCommand(EditMode requestedEditMode) {
            editMode = requestedEditMode;
            spec = ContactSpecFactory.getPostalAddressLineElementSpec();
            spec.setPostalAddressFormatName("FORMAT");
            spec.setPostalAddressLineSortOrder("10");
            spec.setPostalAddressLineElementSortOrder("20");
            edit = ContactEditFactory.getPostalAddressLineElementEdit();
            edit.setPostalAddressLineElementSortOrder("30");
            edit.setPostalAddressElementTypeName("NEW");
            edit.setPrefix("prefix");
            edit.setAlwaysIncludePrefix("true");
            edit.setSuffix("suffix");
            edit.setAlwaysIncludeSuffix("false");
            postalAddressLineElementLogic = new PostalAddressLineElementLogic() {
                @Override
                public PostalAddressLineElement getPostalAddressLineElement(ExecutionErrorAccumulator eea, String postalAddressFormatName,
                        Integer postalAddressLineSortOrder, Integer postalAddressLineElementSortOrder, EntityPermission entityPermission) {
                    lookups++;
                    assertEquals("FORMAT", postalAddressFormatName);
                    assertEquals(10, postalAddressLineSortOrder);
                    assertEquals(20, postalAddressLineElementSortOrder);
                    assertEquals(requestedEditMode == EditMode.UPDATE ? EntityPermission.READ_WRITE : EntityPermission.READ_ONLY, entityPermission);
                    if(missingEntity) {
                        eea.addExecutionError(ExecutionErrors.UnknownPostalAddressLineElement.name(), "FORMAT", "10", "20");
                    }
                    return missingEntity ? null : element;
                }
            };
            postalAddressElementTypeLogic = new PostalAddressElementTypeLogic() {
                @Override
                public PostalAddressElementType getPostalAddressElementTypeByName(ExecutionErrorAccumulator eea, String postalAddressElementTypeName) {
                    assertEquals("NEW", postalAddressElementTypeName);
                    if(missingType) {
                        eea.addExecutionError(ExecutionErrors.UnknownPostalAddressElementTypeName.name(), postalAddressElementTypeName);
                    }
                    return missingType ? null : replacementType;
                }
            };
            contactControl = new ContactControl() {
                @Override
                public PostalAddressLineElement getPostalAddressLineElement(PostalAddressLine selectedLine, Integer sortOrder) {
                    assertSame(postalAddressLine, selectedLine);
                    assertEquals(duplicateMode == 1 ? 20 : 30, sortOrder);
                    return switch(duplicateMode) {
                        case 0 -> null;
                        case 1 -> element;
                        case 2 -> new PostalAddressLineElement(value(8L), EntityPermission.READ_ONLY);
                        default -> throw new IllegalArgumentException();
                    };
                }

                @Override
                public void updatePostalAddressLineElementFromValue(PostalAddressLineElementValue value, BasePK updatedBy) {
                    assertEquals(30, value.getPostalAddressLineElementSortOrder());
                    assertEquals(replacementType.getPrimaryKey(), value.getPostalAddressElementTypePK());
                    assertEquals("prefix", value.getPrefix());
                    assertTrue(value.getAlwaysIncludePrefix());
                    assertEquals("suffix", value.getSuffix());
                    assertFalse(value.getAlwaysIncludeSuffix());
                    assertEquals(postalAddressLine.getPrimaryKey(), value.getPostalAddressLinePK());
                    updated = true;
                }

                @Override
                public PostalAddressLineElementTransfer getPostalAddressLineElementTransfer(UserVisit userVisit, PostalAddressLineElement selectedElement) {
                    assertSame(element, selectedElement);
                    transferred = true;
                    return null;
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
            assertSame(element, lockEntity);
            return null;
        }

        void keepPosition() {
            edit.setPostalAddressLineElementSortOrder("20");
        }

        void assertInvalidEditResult() {
            edit.setPostalAddressLineElementSortOrder("invalid");
            assertTrue(validate().getHasErrors());
            assertNotNull(getBaseResultAfterErrors());
            assertEquals(missingEntity, hasExecutionErrors());
            assertEquals(!missingEntity, transferred);
        }

        void assertInvalidSpecStopsLookup() {
            spec.setPostalAddressLineSortOrder("invalid");
            assertTrue(validate().getHasErrors());
            assertEquals(0, lookups);
        }
    }

    @ParameterizedTest
    @EnumSource(EditMode.class)
    public void resolvesElementWithRequestedPermissionAndUsesItAsLockTarget(EditMode editMode) {
        var command = new TestCommand(editMode);
        assertSame(command.element, command.getEntity(command.getResult()));
        assertSame(command.element, command.getLockEntity(command.element));
    }

    @Test
    public void lockCopiesAllEditableFields() {
        var command = new TestCommand(EditMode.LOCK);
        var edit = command.getEdit();
        command.doLock(edit, command.element);
        assertEquals("20", edit.getPostalAddressLineElementSortOrder());
        assertEquals("OLD", edit.getPostalAddressElementTypeName());
        assertEquals("old prefix", edit.getPrefix());
        assertEquals("false", edit.getAlwaysIncludePrefix());
        assertEquals("old suffix", edit.getSuffix());
        assertEquals("true", edit.getAlwaysIncludeSuffix());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2 })
    public void rejectsOtherElementAtRequestedPositionButAllowsSameElement(int duplicateMode) {
        var command = new TestCommand(EditMode.UPDATE);
        command.duplicateMode = duplicateMode;
        if(duplicateMode == 1) {
            command.keepPosition();
        }
        command.canUpdate(command.element);
        assertEquals(duplicateMode == 2, command.hasExecutionErrors());
        if(duplicateMode == 2) {
            var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
            assertEquals(ExecutionErrors.DuplicatePostalAddressLineElement.name(), error.getKey());
            assertArrayEquals(new Object[] { "FORMAT", "10", "30" }, error.getValues());
        }
        assertFalse(command.updated);
    }

    @Test
    public void missingElementTypePreventsUpdate() {
        var command = new TestCommand(EditMode.UPDATE);
        command.missingType = true;
        command.canUpdate(command.element);
        var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.UnknownPostalAddressElementTypeName.name(), error.getKey());
        assertArrayEquals(new Object[] { "NEW" }, error.getValues());
        assertFalse(command.updated);
    }

    @Test
    public void updateCopiesAllFieldsAndKeepsParentLine() {
        var command = new TestCommand(EditMode.UPDATE);
        command.canUpdate(command.element);
        assertFalse(command.hasExecutionErrors());
        command.doUpdate(command.element);
        assertTrue(command.updated);
        assertEquals(20, command.element.getPostalAddressLineElementSortOrder());
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void invalidEditRetainsResultWithoutDereferencingMissingElement(boolean missingEntity) {
        var command = new TestCommand(EditMode.UPDATE);
        command.missingEntity = missingEntity;
        command.assertInvalidEditResult();
    }

    @Test
    public void invalidSortOrderInSpecStopsBeforeLookup() {
        new TestCommand(EditMode.UPDATE).assertInvalidSpecStopsLookup();
    }

}
