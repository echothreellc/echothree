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

import com.echothree.control.user.contact.common.spec.ContactSpecFactory;
import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.data.contact.common.pk.PostalAddressFormatDetailPK;
import com.echothree.model.data.contact.common.pk.PostalAddressFormatPK;
import com.echothree.model.data.contact.common.pk.PostalAddressLinePK;
import com.echothree.model.data.contact.server.entity.PostalAddressFormat;
import com.echothree.model.data.contact.server.entity.PostalAddressFormatDetail;
import com.echothree.model.data.contact.server.entity.PostalAddressLine;
import com.echothree.model.data.contact.server.value.PostalAddressFormatDetailValue;
import com.echothree.model.data.contact.server.value.PostalAddressFormatValue;
import com.echothree.model.data.contact.server.value.PostalAddressLineValue;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class PostalAddressLineLogicTest {

    private static class TestLogic extends PostalAddressLineLogic {
        private final PostalAddressFormat postalAddressFormat = new PostalAddressFormat(
                new PostalAddressFormatValue(new PostalAddressFormatPK(1L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public PostalAddressFormatDetail getLastDetail() {
                return new PostalAddressFormatDetail(new PostalAddressFormatDetailValue(new PostalAddressFormatDetailPK(2L),
                        getPrimaryKey(), "FORMAT", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        private final PostalAddressLine postalAddressLine = new PostalAddressLine(
                new PostalAddressLineValue(new PostalAddressLinePK(3L), null, null), EntityPermission.READ_WRITE);
        private int lineLookups;
        private EntityPermission selectedPermission;
        private boolean missingUuid;

        TestLogic(boolean missingFormat, boolean missingLine) {
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, UniversalEntitySpec universalSpec,
                        String componentVendorName, String... entityTypeNames) {
                    assertEquals(ComponentVendors.ECHO_THREE.name(), componentVendorName);
                    assertArrayEquals(new String[] { EntityTypes.PostalAddressLine.name() }, entityTypeNames);
                    if(missingUuid) {
                        eea.addExecutionError(ExecutionErrors.UnknownUuid.name(), universalSpec.getUuid());
                    }
                    return null;
                }
            };
            postalAddressFormatLogic = new PostalAddressFormatLogic() {
                @Override
                public PostalAddressFormat getPostalAddressFormatByName(ExecutionErrorAccumulator eea, String postalAddressFormatName) {
                    assertEquals("FORMAT", postalAddressFormatName);
                    if(missingFormat) {
                        eea.addExecutionError(ExecutionErrors.UnknownPostalAddressFormatName.name(), postalAddressFormatName);
                    }
                    return missingFormat ? null : postalAddressFormat;
                }
            };
            contactControl = new ContactControl() {
                @Override
                public PostalAddressLine getPostalAddressLineByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
                    lineLookups++;
                    selectedPermission = entityPermission;
                    return postalAddressLine;
                }

                @Override
                public PostalAddressLine getPostalAddressLine(PostalAddressFormat selectedFormat, Integer postalAddressLineSortOrder,
                        EntityPermission entityPermission) {
                    assertSame(postalAddressFormat, selectedFormat);
                    assertEquals(10, postalAddressLineSortOrder);
                    lineLookups++;
                    selectedPermission = entityPermission;
                    return missingLine ? null : postalAddressLine;
                }
            };
        }
    }

    @ParameterizedTest
    @EnumSource(EntityPermission.class)
    public void resolvesFormatAndSortOrderWithRequestedPermission(EntityPermission entityPermission) {
        var logic = new TestLogic(false, false);
        var errors = new DummyExecutionErrorAccumulator();

        assertSame(logic.postalAddressLine, logic.getPostalAddressLine(errors, "FORMAT", 10, entityPermission));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(1, logic.lineLookups);
        assertEquals(entityPermission, logic.selectedPermission);
    }

    @Test
    public void missingFormatStopsBeforeLineLookup() {
        var logic = new TestLogic(true, false);
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getPostalAddressLine(errors, "FORMAT", 10));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.UnknownPostalAddressFormatName.name(), error.getKey());
        assertArrayEquals(new Object[] { "FORMAT" }, error.getValues());
        assertEquals(0, logic.lineLookups);
    }

    @Test
    public void missingLineReportsCompleteFormatAndSortOrderKey() {
        var logic = new TestLogic(false, true);
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getPostalAddressLineForUpdate(errors, "FORMAT", 10));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.UnknownPostalAddressLine.name(), error.getKey());
        assertArrayEquals(new Object[] { "FORMAT", "10" }, error.getValues());
        assertEquals(EntityPermission.READ_WRITE, logic.selectedPermission);
    }

    @ParameterizedTest
    @ValueSource(strings = { "key", "uuid", "entityRef" })
    public void universalLookupAcceptsEachIdentifier(String identifier) {
        var logic = new TestLogic(false, false);
        var spec = ContactSpecFactory.getPostalAddressLineUniversalSpec();
        switch(identifier) {
            case "key" -> {
                spec.setPostalAddressFormatName("FORMAT");
                spec.setPostalAddressLineSortOrder("10");
            }
            case "uuid" -> spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
            case "entityRef" -> spec.setEntityRef("ECHO_THREE.PostalAddressLine.3");
            default -> throw new IllegalArgumentException(identifier);
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertSame(logic.postalAddressLine, logic.getPostalAddressLineByUniversalSpecForUpdate(errors, spec));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(EntityPermission.READ_WRITE, logic.selectedPermission);
        assertEquals(1, logic.lineLookups);
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4 })
    public void rejectsMissingPartialAndMixedIdentifiers(int combination) {
        var logic = new TestLogic(false, false);
        var spec = ContactSpecFactory.getPostalAddressLineUniversalSpec();
        switch(combination) {
            case 0 -> { }
            case 1 -> spec.setPostalAddressFormatName("FORMAT");
            case 2 -> spec.setPostalAddressLineSortOrder("10");
            case 3 -> {
                spec.setPostalAddressFormatName("FORMAT");
                spec.setPostalAddressLineSortOrder("10");
                spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
            }
            case 4 -> {
                spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
                spec.setEntityRef("ECHO_THREE.PostalAddressLine.3");
            }
            default -> throw new IllegalArgumentException(Integer.toString(combination));
        }
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getPostalAddressLineByUniversalSpec(errors, spec));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, logic.lineLookups);
    }

    @Test
    public void unknownUuidStopsBeforeLineLookup() {
        var logic = new TestLogic(false, false);
        logic.missingUuid = true;
        var spec = ContactSpecFactory.getPostalAddressLineUniversalSpec();
        spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getPostalAddressLineByUniversalSpec(errors, spec));
        assertEquals(ExecutionErrors.UnknownUuid.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, logic.lineLookups);
    }

}
