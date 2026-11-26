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

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.util.common.command.BaseEditResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.form.BaseEdit;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.server.control.BaseCommand;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Exercise actual field validation and inherited recovery without running UPDATE.
 */
public class EntityEditRecoveryTest {

    static Stream<String> kinds() {
        return EntityEditCompatibilityTest.kinds();
    }

    private static ValidationResult validate(EntityEditFixture fixture)
            throws Exception {
        var method = BaseEditCommand.class.getDeclaredMethod("validateUpdate");
        method.setAccessible(true);
        return assertDoesNotThrow(() -> (ValidationResult)method.invoke(fixture.command));
    }

    private static BaseEditResult<?> result(EntityEditFixture fixture)
            throws Exception {
        var method = BaseCommand.class.getDeclaredMethod("getBaseResultAfterErrors");
        method.setAccessible(true);
        return (BaseEditResult<?>)method.invoke(fixture.command);
    }

    private static void invalidEdit(EntityEditFixture fixture)
            throws Exception {
        var factory = CoreEditFactory.class.getMethod("get"
                + (fixture.kind.equals("EntityListItemDefault") ? "EntityListItemAttribute" : fixture.kind) + "Edit");
        fixture.edit = (BaseEdit)factory.invoke(null);
        if(fixture.kind.equals("EntityListItemDefault") || fixture.kind.equals("EntityListItemAttribute")) {
            EntityEditFixture.call(fixture.edit, "setEntityListItemName", "!");
        } else if(fixture.kind.equals("EntityEntityAttribute")) {
            EntityEditFixture.call(fixture.edit, "setEntityRefAttribute", "!");
        }
        EntityEditFixture.inject(fixture.command, "edit", fixture.edit);
    }

    private static void assertRecovered(EntityEditFixture fixture)
            throws Exception {
        assertTrue(validate(fixture).getHasErrors());
        var recovered = result(fixture);
        assertNotNull(recovered);
        assertNotNull(EntityEditFixture.call(recovered, "get" + fixture.kind));
        assertSame(fixture.ownerLockTransfer, recovered.getEntityLock());
        assertNull(recovered.getEdit());
        assertEquals(List.of("transfer"), fixture.locks);
        assertEquals(1, fixture.valueLookups);
        assertEquals(1, fixture.readWriteLookups);
        assertEquals(1, fixture.transfers);
        assertEquals(0, fixture.updates);
        assertEquals(0, fixture.duplicateAliasLookups);
        assertFalse(fixture.command.hasExecutionErrors());
    }

    @ParameterizedTest
    @MethodSource("kinds")
    public void invalidEditFieldsReturnCurrentValueAndOwnerLock(String kind)
            throws Exception {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        invalidEdit(fixture);
        switch(kind) {
            case "EntityAlias" -> {
                fixture.property("EntityAliasTypeDetail", "validationPattern", "[0-9]+");
                fixture.duplicateAlias = true;
            }
            case "EntityNameAttribute", "EntityStringAttribute" ->
                    fixture.property("EntityAttributeString", "validationPattern", "[0-9]+");
            case "EntityAppearance" -> fixture.missingAppearance = true;
            case "EntityBlobAttribute", "EntityClobAttribute" -> fixture.missingMimeType = true;
            case "EntityListItemAttribute", "EntityListItemDefault" -> fixture.wrongListItem = true;
            case "EntityEntityAttribute" -> fixture.restrictReferencedTypes = true;
            default -> {
            }
        }
        assertRecovered(fixture);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Integer", "Long"})
    public void malformedAndOverflowingNumbersNeverReachReplacementParsingDuringRecovery(String type)
            throws Exception {
        var badValues = type.equals("Integer") ? List.of("abc", "2147483648", "-2147483649", "1.5")
                : List.of("abc", "9223372036854775808", "-9223372036854775809", "1.5");
        for(var badValue : badValues) {
            var fixture = new EntityEditFixture("Entity" + type + "Attribute", EditMode.UPDATE);
            EntityEditValidationTest.set(fixture.edit, type + "Attribute", badValue);
            assertRecovered(fixture);
        }
    }

    @ParameterizedTest
    @MethodSource("kinds")
    public void entityLookupDoesNotReadReplacementFields(String kind) {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        EntityEditFixture.inject(fixture.command, "edit", null);
        var editResult = EntityEditFixture.call(fixture.command, "getResult");
        assertNotNull(assertDoesNotThrow(() -> EntityEditFixture.call(fixture.command, "getEntity", editResult)));
        assertFalse(fixture.command.hasExecutionErrors());
        assertEquals(1, fixture.valueLookups);
        assertTrue(fixture.locks.isEmpty());
        assertEquals(0, fixture.transfers);
        assertEquals(0, fixture.updates);
    }

    @ParameterizedTest
    @MethodSource("kinds")
    public void invalidSpecsDoNotAttemptRecoveryLookups(String kind)
            throws Exception {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        invalidEdit(fixture);
        EntityEditValidationTest.set(fixture.spec, "EntityRef", "!");
        assertTrue(validate(fixture).getHasErrors());
        assertNull(result(fixture));
        assertEquals(0, fixture.valueLookups);
        assertTrue(fixture.locks.isEmpty());
        assertEquals(0, fixture.transfers);
        assertEquals(0, fixture.updates);
    }

    @ParameterizedTest
    @MethodSource("kinds")
    public void missingValuesDuringRecoveryReturnErrorsWithoutLockDecoration(String kind)
            throws Exception {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        invalidEdit(fixture);
        fixture.missingValue = true;
        assertTrue(validate(fixture).getHasErrors());
        var recovered = result(fixture);
        assertNotNull(recovered);
        assertNull(EntityEditFixture.call(recovered, "get" + kind));
        assertNull(recovered.getEntityLock());
        assertEquals("Unknown" + kind, EntityEditValidationTest.error(fixture));
        assertEquals(1, fixture.valueLookups);
        assertTrue(fixture.locks.isEmpty());
        assertEquals(0, fixture.transfers);
        assertEquals(0, fixture.updates);
    }

}
