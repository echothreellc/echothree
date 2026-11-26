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
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class EntityEditValidationTest {
    @Test
    public void clobAccumulatesIndependentLanguageAndAttributeLookupErrors() {
        var fixture = new EntityEditFixture("EntityClobAttribute", EditMode.UPDATE);
        fixture.missingLanguage = true;
        fixture.missingAttribute = true;
        fixture.execute();
        var errors = fixture.command.getExecutionErrors().get(Messages.EXECUTION_ERROR);
        var keys = new java.util.HashSet<String>();
        errors.forEachRemaining(error -> keys.add(error.getKey()));
        assertEquals(java.util.Set.of("UnknownLanguageIsoName", "UnknownEntityAttributeName"), keys);
        assertEquals(0, fixture.valueLookups);
        assertTrue(fixture.locks.isEmpty());
    }

    static Stream<String> kinds() {
        return EntityEditCompatibilityTest.kinds();
    }

    static Stream<String> universalOwners() {
        return kinds().filter(kind -> !List.of("EntityAppearance", "EntityBlobAttribute", "EntityListItemDefault").contains(kind));
    }

    static Stream<Arguments> missingValues() {
        return kinds().flatMap(kind -> Stream.of(EditMode.values()).map(mode -> Arguments.of(kind, mode)));
    }

    static Stream<Arguments> identifiers() {
        return universalOwners().flatMap(kind -> Stream.of(false, true).flatMap(ownerUuid -> Stream.of(false, true)
                .map(attributeUuid -> Arguments.of(kind, ownerUuid, attributeUuid))));
    }

    static Stream<Arguments> invalidIdentifiers() {
        return universalOwners().flatMap(kind -> Stream.of("owner", "definition")
                .filter(identifier -> !kind.equals("EntityClobAttribute") || !identifier.equals("owner"))
                .flatMap(identifier -> Stream.of(0, 2)
                        .map(count -> Arguments.of(kind, identifier, count))));
    }

    static String error(EntityEditFixture fixture) {
        return fixture.command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey();
    }

    static void set(Object target, String name, String value) {
        EntityEditFixture.call(target, "set" + name, value);
    }

    @ParameterizedTest
    @MethodSource("kinds")
    public void staleUpdatesReturnCurrentValueWithoutWritingOrUnlocking(String kind) {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        fixture.allowLock = false;
        fixture.execute();
        assertEquals("EntityLockStale", error(fixture));
        assertEquals(List.of("update", "transfer"), fixture.locks);
        assertEquals(0, fixture.updates);
        assertTrue(fixture.transfers > 0);
    }

    @ParameterizedTest
    @MethodSource("identifiers")
    public void ownerAndDefinitionIdentifierAlternativesRetainOwnerLock(String kind, boolean ownerUuid, boolean definitionUuid) {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        if(ownerUuid) {
            set(fixture.spec, "EntityRef", null);
            set(fixture.spec, "Uuid", "11111111-1111-4111-8111-111111111111");
        }
        if(definitionUuid) {
            boolean alias = kind.equals("EntityAlias");
            set(fixture.spec, alias ? "EntityAliasTypeName" : "EntityAttributeName", null);
            set(fixture.spec, alias ? "EntityAliasTypeUuid" : "EntityAttributeUuid", "22222222-2222-4222-8222-222222222222");
        }
        fixture.execute();
        assertFalse(fixture.command.hasExecutionErrors());
        assertEquals(List.of("update", "unlock"), fixture.locks);
        assertEquals(1, fixture.updates);
    }

    @ParameterizedTest
    @MethodSource("invalidIdentifiers")
    public void missingAndAmbiguousIdentifiersStopBeforeValueLookup(String kind, String identifier, int count) {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        if(identifier.equals("owner")) {
            set(fixture.spec, "EntityRef", count == 0 ? null : "ECHO_THREE.Item.777");
            set(fixture.spec, "Uuid", count == 0 ? null : "11111111-1111-4111-8111-111111111111");
        } else {
            boolean alias = kind.equals("EntityAlias");
            set(fixture.spec, alias ? "EntityAliasTypeName" : "EntityAttributeName", count == 0 ? null : "ATTRIBUTE");
            set(fixture.spec, alias ? "EntityAliasTypeUuid" : "EntityAttributeUuid", count == 0 ? null : "22222222-2222-4222-8222-222222222222");
        }
        fixture.execute();
        assertEquals("InvalidParameterCount", error(fixture));
        assertEquals(0, fixture.valueLookups);
        assertEquals(0, fixture.updates);
        assertTrue(fixture.locks.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("universalOwners")
    public void definitionUuidMustBelongToOwnerType(String kind) {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        boolean alias = kind.equals("EntityAlias");
        fixture.wrongAttributeOwner = !alias;
        fixture.wrongAliasOwner = alias;
        set(fixture.spec, alias ? "EntityAliasTypeName" : "EntityAttributeName", null);
        set(fixture.spec, alias ? "EntityAliasTypeUuid" : "EntityAttributeUuid", "22222222-2222-4222-8222-222222222222");
        fixture.execute();
        assertEquals("MismatchedEntityType", error(fixture));
        assertEquals(0, fixture.valueLookups);
        assertTrue(fixture.locks.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"EntityNameAttribute", "EntityStringAttribute", "EntityAlias"})
    public void invalidPatternsWinOverMissingValues(String kind) {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        fixture.property(kind.equals("EntityAlias") ? "EntityAliasTypeDetail" : "EntityAttributeString", "validationPattern", "[0-9]+");
        fixture.missingValue = true;
        fixture.execute();
        assertEquals(switch(kind) {
            case "EntityAlias" -> "InvalidAlias";
            case "EntityNameAttribute" -> "InvalidNameAttribute";
            default -> "InvalidStringAttribute";
        }, error(fixture));
        assertEquals(0, fixture.valueLookups);
        assertEquals(List.of("transfer"), fixture.locks);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Integer", "Long"})
    public void numericBoundsAcceptEndpointsAndRejectOutsideValuesBeforeLookup(String type) {
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
                assertEquals(0, fixture.valueLookups);
                assertEquals(0, fixture.updates);
            } else {
                assertFalse(fixture.command.hasExecutionErrors());
                assertEquals(1, fixture.updates);
            }
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    public void aliasDuplicatesAreScopedAndExcludeTheCurrentAlias(boolean currentAlias) {
        var fixture = new EntityEditFixture("EntityAlias", EditMode.UPDATE);
        fixture.duplicateAlias = !currentAlias;
        fixture.sameAlias = currentAlias;
        if(currentAlias) {
            set(fixture.edit, "Alias", "Original");
        }
        fixture.execute();
        if(currentAlias) {
            assertEquals(1, fixture.updates);
        } else {
            assertEquals("DuplicateEntityAlias", error(fixture));
            assertEquals(List.of("transfer"), fixture.locks);
            assertEquals(0, fixture.updates);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"EntityListItemAttribute", "EntityListItemDefault"})
    public void referencedListItemMustBelongToAttributeAndValidationPrecedesValueLookup(String kind) {
        var fixture = new EntityEditFixture(kind, EditMode.UPDATE);
        fixture.wrongListItem = true;
        fixture.missingValue = true;
        set(fixture.edit, "EntityListItemName", null);
        set(fixture.edit, "EntityListItemUuid", "33333333-3333-4333-8333-333333333333");
        fixture.execute();
        assertEquals("MismatchedEntityListItem", error(fixture));
        assertEquals(0, fixture.valueLookups);
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
            assertEquals(0, fixture.valueLookups);
            assertEquals(0, fixture.updates);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"EntityBlobAttribute", "EntityClobAttribute"})
    public void missingRowsPrecedeMimeValidationAndIncompatibleMimeTypesPreventWrites(String kind) {
        var missing = new EntityEditFixture(kind, EditMode.UPDATE);
        missing.missingValue = true;
        missing.missingMimeType = true;
        missing.execute();
        assertEquals("Unknown" + kind, error(missing));

        var invalid = new EntityEditFixture(kind, EditMode.UPDATE);
        invalid.mimeStorageType = kind.equals("EntityBlobAttribute") ? "CLOB" : "BLOB";
        invalid.execute();
        assertEquals("InvalidMimeType", error(invalid));
        assertEquals(0, invalid.updates);
    }

    @Test
    public void blobRequiresPayloadAndAppearanceReplacementPrecedesMissingRelationship() {
        var blob = new EntityEditFixture("EntityBlobAttribute", EditMode.UPDATE);
        EntityEditFixture.call(blob.edit, "setBlobAttribute", (Object)null);
        blob.execute();
        assertEquals("MissingBlobAttribute", error(blob));
        assertEquals(0, blob.updates);
        var appearance = new EntityEditFixture("EntityAppearance", EditMode.UPDATE);
        appearance.missingAppearance = true;
        appearance.missingValue = true;
        appearance.execute();
        assertEquals("UnknownAppearanceName", error(appearance));
        assertEquals(0, appearance.valueLookups);
    }

}
