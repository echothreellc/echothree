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

import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class EntityEditLookupTest {

    static Stream<Arguments> missingValues() {
        return EntityEditCompatibilityTest.kinds().flatMap(kind -> Stream.of(EditMode.values()).map(mode -> Arguments.of(kind, mode)));
    }

    static Stream<Arguments> resolverPermissions() {
        return EntityEditCompatibilityTest.kinds().flatMap(kind -> Stream.of(EntityPermission.values()).map(permission -> Arguments.of(kind, permission)));
    }

    static Stream<String> kinds() {
        return EntityEditCompatibilityTest.kinds();
    }

    static Stream<Arguments> ownerFailures() {
        return kinds().filter(kind -> !kind.equals("EntityListItemDefault")).flatMap(kind -> {
            boolean legacy = kind.equals("EntityAppearance") || kind.equals("EntityBlobAttribute");
            return Stream.of(false, true).filter(uuid -> !legacy || !uuid).flatMap(uuid ->
                    Stream.of(false, true).map(deleted -> Arguments.of(kind, uuid, deleted)));
        });
    }

    @ParameterizedTest
    @MethodSource("ownerFailures")
    public void unknownAndDeletedOwnersStopBeforeDependentLookups(String kind, boolean uuid, boolean deleted) {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        fixture.missingOwner = !deleted;
        fixture.deletedOwner = deleted;
        if(uuid) {
            EntityEditValidationTest.set(fixture.spec, "EntityRef", null);
            EntityEditValidationTest.set(fixture.spec, "Uuid", "11111111-1111-4111-8111-111111111111");
        }
        assertDoesNotThrow(fixture::execute);
        assertEquals((deleted ? "Deleted" : "Unknown") + (uuid ? "Uuid" : "EntityRef"), EntityEditValidationTest.error(fixture));
        assertEquals(0, fixture.valueLookups);
        assertEquals(0, fixture.updates);
        assertTrue(fixture.locks.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    public void clobInvalidOwnerIdentifiersStopBeforeDependentAttributeLookup(boolean ambiguous) {
        var fixture = new EntityEditFixture("EntityClobAttribute", EditMode.UPDATE);
        EntityEditValidationTest.set(fixture.spec, "EntityRef", ambiguous ? "ECHO_THREE.Item.777" : null);
        EntityEditValidationTest.set(fixture.spec, "Uuid", ambiguous ? "11111111-1111-4111-8111-111111111111" : null);
        assertDoesNotThrow(fixture::execute);
        assertEquals("InvalidParameterCount", EntityEditValidationTest.error(fixture));
        assertEquals(0, fixture.valueLookups);
        assertTrue(fixture.locks.isEmpty());
    }

    static Object resolve(EntityEditFixture fixture, Object errors, EntityPermission permission) {
        var arguments = new ArrayList<Object>();
        arguments.add(errors);
        switch(fixture.kind) {
            case "EntityAppearance" -> arguments.add(fixture.entityInstance);
            case "EntityAlias" -> {
                arguments.add(fixture.entityInstance);
                arguments.add(fixture.entityAliasType);
            }
            default -> {
                arguments.add(fixture.entityAttribute);
                if(!fixture.kind.equals("EntityListItemDefault")) {
                    arguments.add(fixture.entityInstance);
                }
                if(List.of("EntityStringAttribute", "EntityClobAttribute", "EntityBlobAttribute").contains(fixture.kind)) {
                    arguments.add(fixture.language);
                }
            }
        }
        arguments.add(permission);
        return EntityEditFixture.call(fixture.dataLogic, "get" + fixture.kind, arguments.toArray());
    }

    @ParameterizedTest
    @MethodSource("resolverPermissions")
    public void resolversHonorPermissionsAndSupportNullAccumulators(String kind, EntityPermission permission) {
        var fixture = new EntityEditFixture(kind, EditMode.LOCK);
        assertNotNull(resolve(fixture, null, permission));
        assertEquals(permission == EntityPermission.READ_WRITE ? 1 : 0, fixture.readWriteLookups);
    }

    @ParameterizedTest
    @MethodSource("kinds")
    public void missingValuesSelectSpecificExceptionsWithoutAccumulator(String kind) {
        var fixture = new EntityEditFixture(kind, EditMode.LOCK);
        fixture.missingValue = true;
        var exception = assertThrows(RuntimeException.class, () -> resolve(fixture, null, EntityPermission.READ_ONLY));
        assertEquals("Unknown" + kind + "Exception", exception.getClass().getSimpleName());
    }

    @ParameterizedTest
    @MethodSource("missingValues")
    public void missingRowsReportCompleteResolvedKeysAndNeverAcquireLocks(String kind, EditMode mode) {
        var fixture = new EntityEditFixture(kind, mode);
        fixture.missingValue = true;
        var result = (com.echothree.util.common.command.BaseEditResult<?>)assertDoesNotThrow(fixture::execute);
        assertNull(result.getEntityLock());
        assertNull(EntityEditFixture.call(result, "get" + kind));
        assertEquals("Unknown" + kind, EntityEditValidationTest.error(fixture));
        assertTrue(fixture.locks.isEmpty());
        assertEquals(0, fixture.transfers);
        assertEquals(0, fixture.updates);
        var values = fixture.command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getValues();
        Object[] expected = switch(kind) {
            case "EntityAppearance" -> new Object[]{"ECHO_THREE.Item.777"};
            case "EntityAlias" -> new Object[]{"ECHO_THREE.Item.777", "ALIAS_TYPE"};
            case "EntityListItemDefault" -> new Object[]{"ECHO_THREE", "Item", "ATTRIBUTE"};
            case "EntityStringAttribute", "EntityBlobAttribute", "EntityClobAttribute" ->
                    new Object[]{"ECHO_THREE.Item.777", "ECHO_THREE", "Item", "ATTRIBUTE", "en"};
            default -> new Object[]{"ECHO_THREE.Item.777", "ECHO_THREE", "Item", "ATTRIBUTE"};
        };
        assertArrayEquals(expected, values);
    }

}
