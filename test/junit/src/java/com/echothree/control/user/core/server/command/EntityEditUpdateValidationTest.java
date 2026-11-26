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

package com.echothree.control.user.core.server.command;

import static com.echothree.control.user.core.server.command.EntityEditValidationTest.error;
import static com.echothree.control.user.core.server.command.EntityEditValidationTest.set;
import com.echothree.util.common.command.EditMode;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Replacement validation runs after resolving the current value.
 */
public class EntityEditUpdateValidationTest {

    @ParameterizedTest
    @ValueSource(strings = {"EntityNameAttribute", "EntityStringAttribute", "EntityAlias"})
    public void missingValuesPrecedeReplacementPatternValidation(String kind) {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        fixture.property(kind.equals("EntityAlias") ? "EntityAliasTypeDetail" : "EntityAttributeString", "validationPattern", "[0-9]+");
        fixture.missingValue = true;
        fixture.execute();
        assertEquals("Unknown" + kind, error(fixture));
        assertEquals(1, fixture.valueLookups);
        assertTrue(fixture.locks.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Integer", "Long"})
    public void numericBoundsAcceptEndpointsAndRejectOutsideValuesAfterLookup(String type) {
        for(int value : List.of(-11, -10, 0, 10, 11)) {
            var fixture = new EntityEditFixture("Entity" + type + "Attribute", EditMode.UPDATE);
            Object lower = type.equals("Integer") ? (Object)Integer.valueOf(-10) : Long.valueOf(-10);
            Object upper = type.equals("Integer") ? (Object)Integer.valueOf(10) : Long.valueOf(10);
            fixture.property("EntityAttribute" + type, "lowerRange" + type + "Value", lower);
            fixture.property("EntityAttribute" + type, "upperRange" + type + "Value", upper);
            set(fixture.edit, type + "Attribute", Integer.toString(value));
            fixture.execute();
            if(value < -10 || value > 10) {
                assertEquals(value < -10 ? "LowerRangeExceeded" : "UpperRangeExceeded", error(fixture));
                assertEquals(1, fixture.valueLookups);
                assertEquals(0, fixture.updates);
            } else {
                assertFalse(fixture.command.hasExecutionErrors());
                assertEquals(1, fixture.updates);
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"EntityListItemAttribute", "EntityListItemDefault"})
    public void missingValuesPrecedeReplacementListItemValidation(String kind) {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        fixture.wrongListItem = true;
        fixture.missingValue = true;
        set(fixture.edit, "EntityListItemName", null);
        set(fixture.edit, "EntityListItemUuid", "33333333-3333-4333-8333-333333333333");
        fixture.execute();
        assertEquals("Unknown" + kind, error(fixture));
        assertEquals(1, fixture.valueLookups);
        assertEquals(0, fixture.updates);
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    public void referencedEntityHonorsConfiguredAllowedTypes(boolean allowed) {
        var fixture = new EntityEditFixture("EntityEntityAttribute", EditMode.UPDATE);
        fixture.restrictReferencedTypes = true;
        fixture.referencedTypeAllowed = allowed;
        fixture.execute();
        if(allowed) {
            assertEquals(1, fixture.updates);
        } else {
            assertEquals("UnknownEntityAttributeEntityType", error(fixture));
            assertEquals(1, fixture.valueLookups);
            assertEquals(0, fixture.updates);
        }
    }

    @Test
    public void blobRequiresPayloadAndMissingAppearanceRelationshipPrecedesReplacement() {
        var blob = new EntityEditFixture("EntityBlobAttribute", EditMode.UPDATE);
        EntityEditFixture.call(blob.edit, "setBlobAttribute", (Object)null);
        blob.execute();
        assertEquals("MissingBlobAttribute", error(blob));
        assertEquals(0, blob.updates);
        var appearance = new EntityEditFixture("EntityAppearance", EditMode.UPDATE);
        appearance.missingAppearance = true;
        appearance.missingValue = true;
        appearance.execute();
        assertEquals("UnknownEntityAppearance", error(appearance));
        assertEquals(1, appearance.valueLookups);
    }

    @ParameterizedTest
    @ValueSource(strings = {"EntityNameAttribute", "EntityStringAttribute", "EntityAlias"})
    public void invalidPatternsOnExistingValuesReturnCurrentDataAndLockWithoutUpdating(String kind) {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        fixture.property(kind.equals("EntityAlias") ? "EntityAliasTypeDetail" : "EntityAttributeString", "validationPattern", "[0-9]+");
        fixture.duplicateAlias = true;
        var result = (com.echothree.util.common.command.BaseEditResult<?>)fixture.execute();
        assertEquals(switch(kind) {
            case "EntityAlias" -> "InvalidAlias";
            case "EntityNameAttribute" -> "InvalidNameAttribute";
            default -> "InvalidStringAttribute";
        }, error(fixture));
        assertNotNull(EntityEditFixture.call(result, "get" + kind));
        assertSame(fixture.ownerLockTransfer, result.getEntityLock());
        assertEquals(List.of("transfer"), fixture.locks);
        assertEquals(1, fixture.valueLookups);
        assertEquals(0, fixture.updates);
        assertEquals(0, fixture.duplicateAliasLookups);
    }

    @ParameterizedTest
    @ValueSource(strings = {"EntityListItemAttribute", "EntityListItemDefault"})
    public void replacementListItemMustStillBelongToAttribute(String kind) {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        fixture.wrongListItem = true;
        set(fixture.edit, "EntityListItemName", null);
        set(fixture.edit, "EntityListItemUuid", "33333333-3333-4333-8333-333333333333");
        var result = (com.echothree.util.common.command.BaseEditResult<?>)fixture.execute();
        assertEquals("MismatchedEntityListItem", error(fixture));
        assertNotNull(EntityEditFixture.call(result, "get" + kind));
        assertSame(fixture.ownerLockTransfer, result.getEntityLock());
        assertEquals(List.of("transfer"), fixture.locks);
        assertEquals(1, fixture.valueLookups);
        assertEquals(0, fixture.updates);
    }

    @Test
    public void unknownAppearanceOnExistingRelationshipReturnsCurrentDataAndLock() {
        var fixture = new EntityEditFixture("EntityAppearance", EditMode.UPDATE);
        fixture.missingAppearance = true;
        var result = (com.echothree.util.common.command.BaseEditResult<?>)fixture.execute();
        assertEquals("UnknownAppearanceName", error(fixture));
        assertNotNull(EntityEditFixture.call(result, "getEntityAppearance"));
        assertSame(fixture.ownerLockTransfer, result.getEntityLock());
        assertEquals(List.of("transfer"), fixture.locks);
        assertEquals(1, fixture.valueLookups);
        assertEquals(0, fixture.updates);
    }

}
