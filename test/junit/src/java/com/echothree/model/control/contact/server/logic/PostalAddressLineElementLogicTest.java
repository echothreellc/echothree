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

package com.echothree.model.control.contact.server.logic;

import com.echothree.model.control.contact.server.control.ContactControl;
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
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class PostalAddressLineElementLogicTest {

    enum MissingEntity { FORMAT, LINE, ELEMENT }

    private static class TestLogic extends PostalAddressLineElementLogic {
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
        private final PostalAddressLineElement postalAddressLineElement = new PostalAddressLineElement(new PostalAddressLineElementValue(
                new PostalAddressLineElementPK(5L), postalAddressLine.getPrimaryKey(), 20, new PostalAddressElementTypePK(6L),
                null, false, null, false, 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE);
        private int lineLookups;
        private int elementLookups;

        TestLogic(MissingEntity missingEntity, EntityPermission expectedPermission) {
            contactControl = new ContactControl() {
                @Override
                public PostalAddressFormat getPostalAddressFormatByName(String postalAddressFormatName, EntityPermission entityPermission) {
                    assertEquals("FORMAT", postalAddressFormatName);
                    assertEquals(EntityPermission.READ_ONLY, entityPermission);
                    return missingEntity == MissingEntity.FORMAT ? null : postalAddressFormat;
                }

                @Override
                public PostalAddressLine getPostalAddressLine(PostalAddressFormat selectedFormat, Integer postalAddressLineSortOrder,
                        EntityPermission entityPermission) {
                    lineLookups++;
                    assertSame(postalAddressFormat, selectedFormat);
                    assertEquals(10, postalAddressLineSortOrder);
                    assertEquals(EntityPermission.READ_ONLY, entityPermission);
                    return missingEntity == MissingEntity.LINE ? null : postalAddressLine;
                }

                @Override
                public PostalAddressLineElement getPostalAddressLineElement(PostalAddressLine selectedLine, Integer postalAddressLineElementSortOrder,
                        EntityPermission entityPermission) {
                    elementLookups++;
                    assertSame(postalAddressLine, selectedLine);
                    assertEquals(20, postalAddressLineElementSortOrder);
                    assertEquals(expectedPermission, entityPermission);
                    return missingEntity == MissingEntity.ELEMENT ? null : postalAddressLineElement;
                }
            };
            postalAddressLineLogic = new PostalAddressLineLogic();
            postalAddressLineLogic.contactControl = contactControl;
            postalAddressLineLogic.postalAddressFormatLogic = new PostalAddressFormatLogic();
            postalAddressLineLogic.postalAddressFormatLogic.contactControl = contactControl;
        }
    }

    @ParameterizedTest
    @EnumSource(EntityPermission.class)
    public void resolvesCompleteKeyWithReadOnlyParentsAndRequestedElementPermission(EntityPermission entityPermission) {
        var logic = new TestLogic(null, entityPermission);
        var errors = new DummyExecutionErrorAccumulator();
        var element = entityPermission == EntityPermission.READ_ONLY
                ? logic.getPostalAddressLineElement(errors, "FORMAT", 10, 20)
                : logic.getPostalAddressLineElementForUpdate(errors, "FORMAT", 10, 20);

        assertSame(logic.postalAddressLineElement, element);
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.lineLookups);
        assertEquals(1, logic.elementLookups);
    }

    @ParameterizedTest
    @EnumSource(MissingEntity.class)
    public void missingEntitiesReportCompleteKeysAndStopDependentLookups(MissingEntity missingEntity) {
        var logic = new TestLogic(missingEntity, EntityPermission.READ_WRITE);
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getPostalAddressLineElementForUpdate(errors, "FORMAT", 10, 20));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        var expectedError = switch(missingEntity) {
            case FORMAT -> ExecutionErrors.UnknownPostalAddressFormatName;
            case LINE -> ExecutionErrors.UnknownPostalAddressLine;
            case ELEMENT -> ExecutionErrors.UnknownPostalAddressLineElement;
        };
        var expectedKey = switch(missingEntity) {
            case FORMAT -> new Object[] { "FORMAT" };
            case LINE -> new Object[] { "FORMAT", "10" };
            case ELEMENT -> new Object[] { "FORMAT", "10", "20" };
        };
        assertEquals(expectedError.name(), error.getKey());
        assertArrayEquals(expectedKey, error.getValues());
        assertEquals(missingEntity == MissingEntity.FORMAT ? 0 : 1, logic.lineLookups);
        assertEquals(missingEntity == MissingEntity.ELEMENT ? 1 : 0, logic.elementLookups);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void elementTypeLookupReturnsEntityOrReportsName(boolean missingType) {
        var elementType = new PostalAddressElementType(new PostalAddressElementTypeValue(new PostalAddressElementTypePK(1L), "TYPE", false, 0),
                EntityPermission.READ_ONLY);
        var logic = new PostalAddressElementTypeLogic();
        logic.contactControl = new ContactControl() {
            @Override
            public PostalAddressElementType getPostalAddressElementTypeByName(String postalAddressElementTypeName) {
                assertEquals("TYPE", postalAddressElementTypeName);
                return missingType ? null : elementType;
            }
        };
        var errors = new DummyExecutionErrorAccumulator();
        var result = logic.getPostalAddressElementTypeByName(errors, "TYPE");
        if(missingType) {
            assertNull(result);
            var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
            assertEquals(ExecutionErrors.UnknownPostalAddressElementTypeName.name(), error.getKey());
            assertArrayEquals(new Object[] { "TYPE" }, error.getValues());
        } else {
            assertSame(elementType, result);
            assertFalse(errors.hasExecutionErrors());
        }
    }

}
