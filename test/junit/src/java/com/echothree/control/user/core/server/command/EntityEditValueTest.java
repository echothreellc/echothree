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

import com.echothree.control.user.core.common.edit.EntityGeoPointAttributeEdit;
import com.echothree.model.data.core.server.value.EntityGeoPointAttributeValue;
import com.echothree.util.common.command.BaseEditResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.validation.Validator;
import com.echothree.util.server.validation.fieldtype.UuidFieldType;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@ResourceLock("ValueCacheProvider")
public class EntityEditValueTest {

    static Stream<String> kinds() {
        return EntityEditCompatibilityTest.kinds();
    }

    static Stream<Arguments> pairs() {
        return IntStream.range(0, 16).mapToObj(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("pairs")
    public void geoPointOptionalPairsAreIndependentAndWriteConvertedValues(int mask) {
        var fixture = new EntityEditFixture("EntityGeoPointAttribute", EditMode.UPDATE);
        var edit = (EntityGeoPointAttributeEdit)fixture.edit;
        edit.setElevation((mask & 1) == 0 ? null : "12");
        edit.setElevationUnitOfMeasureTypeName((mask & 2) == 0 ? null : "METER");
        edit.setAltitude((mask & 4) == 0 ? null : "34");
        edit.setAltitudeUnitOfMeasureTypeName((mask & 8) == 0 ? null : "METER");
        fixture.execute();
        boolean elevationValid = (mask & 3) == 0 || (mask & 3) == 3;
        boolean altitudeValid = (mask & 12) == 0 || (mask & 12) == 12;
        if(elevationValid && altitudeValid) {
            assertFalse(fixture.command.hasExecutionErrors());
            var value = (EntityGeoPointAttributeValue)fixture.writtenValue;
            assertEquals((mask & 1) == 0 ? null : 12L, value.getElevation());
            assertEquals((mask & 4) == 0 ? null : 34L, value.getAltitude());
            assertEquals(30, value.getLatitude());
            assertEquals(40, value.getLongitude());
            assertEquals(List.of("update", "unlock"), fixture.locks);
        } else {
            String expected = !elevationValid ? (mask & 1) == 0 ? "MissingRequiredElevation" : "MissingRequiredElevationUnitOfMeasureTypeName"
                    : (mask & 4) == 0 ? "MissingRequiredAltitude" : "MissingRequiredAltitudeUnitOfMeasureTypeName";
            assertEquals(expected, EntityEditValidationTest.error(fixture));
            assertEquals(0, fixture.updates);
            assertEquals(List.of("transfer"), fixture.locks);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Elevation", "Altitude"})
    public void unknownGeoPointUnitsReportFieldSpecificErrorsAndFullUnitKey(String property) {
        var fixture = new EntityEditFixture("EntityGeoPointAttribute", EditMode.UPDATE);
        EntityEditValidationTest.set(fixture.edit, property, "1");
        EntityEditValidationTest.set(fixture.edit, property + "UnitOfMeasureTypeName", "UNKNOWN");
        fixture.execute();
        var error = fixture.command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals("Unknown" + property + "UnitOfMeasureTypeName", error.getKey());
        assertArrayEquals(new Object[]{"LENGTH", "UNKNOWN"}, error.getValues());
        assertEquals(0, fixture.updates);
    }

    @ParameterizedTest
    @ValueSource(strings = {"EntityStringAttribute", "EntityClobAttribute"})
    public void languageUuidAlternativesAndInvalidCombinations(String kind) {
        for(int count : List.of(0, 1, 2)) {
            var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
            EntityEditValidationTest.set(fixture.spec, "LanguageIsoName", count == 2 ? "en" : null);
            EntityEditValidationTest.set(fixture.spec, "LanguageUuid", count == 0 ? null : "11111111-1111-4111-8111-111111111111");
            fixture.execute();
            if(count == 1) {
                assertEquals(1, fixture.updates);
            } else {
                assertEquals("InvalidParameterCount", EntityEditValidationTest.error(fixture));
                assertEquals(0, fixture.valueLookups);
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"names", "ref", "uuid", "none", "ambiguous"})
    public void listItemDefaultUniversalIdentifiersAddressTheDefinition(String identifier) {
        var fixture = new EntityEditFixture("EntityListItemDefault", EditMode.UPDATE);
        if(!identifier.equals("names") && !identifier.equals("ambiguous")) {
            for(var name : List.of("ComponentVendorName", "EntityTypeName", "EntityAttributeName"))
                EntityEditValidationTest.set(fixture.spec, name, null);
        }
        switch(identifier) {
            case "ref", "ambiguous" ->
                    EntityEditValidationTest.set(fixture.spec, "EntityRef", "ECHO_THREE.EntityAttribute.1");
            case "uuid" -> EntityEditValidationTest.set(fixture.spec, "Uuid", "11111111-1111-4111-8111-111111111111");
            default -> {
            }
        }
        fixture.execute();
        if(identifier.equals("none") || identifier.equals("ambiguous")) {
            assertEquals("InvalidParameterCount", EntityEditValidationTest.error(fixture));
            assertTrue(fixture.locks.isEmpty());
        } else {
            assertFalse(fixture.command.hasExecutionErrors());
            assertEquals(List.of("update", "unlock"), fixture.locks);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"EntityListItemDefault", "EntityListItemAttribute"})
    public void listItemAlternativesAndAmbiguity(String kind) {
        for(int count : List.of(0, 1, 2)) {
            var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
            EntityEditValidationTest.set(fixture.edit, "EntityListItemName", count == 2 ? "ITEM" : null);
            EntityEditValidationTest.set(fixture.edit, "EntityListItemUuid", count == 0 ? null : "11111111-1111-4111-8111-111111111111");
            fixture.execute();
            if(count == 1) {
                assertEquals(1, fixture.updates);
            } else {
                assertEquals("InvalidParameterCount", EntityEditValidationTest.error(fixture));
            }
        }
    }

    @Test
    public void stringLanguageUuidUsesUuidValidation()
            throws ReflectiveOperationException {
        var field = EditEntityStringAttributeCommand.class.getDeclaredField("SPEC_FIELD_DEFINITIONS");
        field.setAccessible(true);
        @SuppressWarnings("unchecked") var definitions = (List<FieldDefinition>)field.get(null);
        var definition = definitions.stream().filter(d -> d.getFieldName().equals("LanguageUuid")).findFirst().orElseThrow();
        assertEquals(FieldType.UUID, definition.getFieldType());
        var messages = new Messages();
        new UuidFieldType(null, null, messages, "not-a-uuid", new String[]{"LanguageUuid"}, definition).validate();
        assertTrue(messages.containsKey("LanguageUuid", Validator.ERROR_INVALID_FORMAT));
        messages = new Messages();
        new UuidFieldType(null, null, messages, "11111111-1111-4111-8111-111111111111", new String[]{"LanguageUuid"}, definition).validate();
        assertTrue(messages.isEmpty());
    }

    static Stream<Arguments> updates() {
        return kinds().flatMap(kind -> Stream.of(
                Arguments.of(kind, false), Arguments.of(kind, true)));
    }

    @ParameterizedTest
    @MethodSource("updates")
    public void successfulUpdatesAndPersistenceFailuresAlwaysUnlockCorrectOwner(String kind, boolean failUpdate) {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        fixture.failUpdate = failUpdate;
        fixture.failTransfer = true;
        if(failUpdate) {
            assertThrows(IllegalStateException.class, fixture::execute);
        } else {
            var result = (BaseEditResult<?>)fixture.execute();
            assertNull(result.getEdit());
            assertNull(result.getEntityLock());
            assertNull(EntityEditFixture.call(result, "get" + kind));
            assertFalse(fixture.command.hasExecutionErrors());
            assertEquals(0, fixture.transfers);
        }
        assertEquals(java.util.List.of("update", "unlock"), fixture.locks);
        assertEquals(1, fixture.updates);
        assertEquals(1, fixture.readWriteLookups);
    }

    @ParameterizedTest
    @MethodSource("kinds")
    public void abandonUnlocksOwnerWithoutPopulatingResult(String kind) {
        var fixture = new EntityEditFixture(kind, EditMode.ABANDON);
        fixture.failTransfer = true;
        var result = (BaseEditResult<?>)fixture.execute();
        assertNull(result.getEdit());
        assertNull(result.getEntityLock());
        assertNull(EntityEditFixture.call(result, "get" + kind));
        assertFalse(fixture.command.hasExecutionErrors());
        assertEquals(java.util.List.of("unlock"), fixture.locks);
        assertEquals(0, fixture.updates);
        assertEquals(0, fixture.transfers);
        assertEquals(0, fixture.readWriteLookups);
    }

}
