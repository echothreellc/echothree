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

import com.echothree.control.user.core.common.edit.EntityAppearanceEdit;
import com.echothree.control.user.core.common.edit.EntityBlobAttributeEdit;
import com.echothree.control.user.core.common.edit.EntityDateAttributeEdit;
import com.echothree.control.user.core.common.edit.EntityGeoPointAttributeEdit;
import com.echothree.control.user.core.common.edit.EntityTimeAttributeEdit;
import com.echothree.util.common.command.BaseEditResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.Messages;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Characterization cases shared with the original implementations.
 */
@ResourceLock("ValueCacheProvider")
public class EntityEditCompatibilityTest {

    @ParameterizedTest
    @MethodSource("kinds")
    public void transferFailuresOccurBeforeAcquiringALock(String kind) {
        var fixture = new EntityEditFixture(kind, EditMode.LOCK);
        fixture.failTransfer = true;
        assertThrows(IllegalStateException.class, fixture::execute);
        assertTrue(fixture.locks.isEmpty());
        assertEquals(0, fixture.updates);
        assertEquals(1, fixture.transfers);
    }

    static Stream<String> kinds() {
        return Stream.of("EntityAppearance", "EntityAlias", "EntityListItemDefault", "EntityBooleanAttribute", "EntityIntegerAttribute", "EntityLongAttribute", "EntityNameAttribute", "EntityStringAttribute", "EntityDateAttribute", "EntityTimeAttribute", "EntityBlobAttribute", "EntityClobAttribute", "EntityEntityAttribute", "EntityListItemAttribute", "EntityGeoPointAttribute");
    }

    @ParameterizedTest
    @MethodSource("kinds")
    public void failedLocksReturnCurrentValueAndOwnerLockInformation(String kind) {
        var fixture = new EntityEditFixture(kind, EditMode.LOCK);
        fixture.allowLock = false;
        fixture.execute();
        assertEquals("EntityLockFailed", fixture.command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(java.util.List.of("lock", "transfer"), fixture.locks);
        assertEquals(0, fixture.updates);
        assertEquals(1, fixture.transfers);
        assertEquals(0, fixture.readWriteLookups);
    }

    @ParameterizedTest
    @MethodSource("kinds")
    @ResourceLock("CDIProvider")
    public void successfulLocksCopyCurrentValuesWithoutWriting(String kind) {
        try(var environment = new EntityEditFormattingEnvironment()) {
            var fixture = new EntityEditFixture(kind, EditMode.LOCK);
            fixture.property("EntityDateAttribute", "dateAttribute", 20261125);
            var result = (BaseEditResult<?>)fixture.execute();
            assertFalse(fixture.command.hasExecutionErrors());
            assertNotNull(result.getEdit());
            assertEquals(List.of("lock", "transfer"), fixture.locks);
            assertEquals(0, fixture.updates);
            assertEquals(1, fixture.transfers);
            assertEquals(fixture.ownerLockTransfer, result.getEntityLock());
            switch(kind) {
                case "EntityDateAttribute" ->
                        assertEquals("2026-11-25", ((EntityDateAttributeEdit)result.getEdit()).getDateAttribute());
                case "EntityTimeAttribute" ->
                        assertEquals("1970-01-01 00:00:00", ((EntityTimeAttributeEdit)result.getEdit()).getTimeAttribute());
                case "EntityBlobAttribute" -> {
                    assertEquals("text/plain", ((EntityBlobAttributeEdit)result.getEdit()).getMimeTypeName());
                    assertNull(((EntityBlobAttributeEdit)result.getEdit()).getBlobAttribute());
                }
                case "EntityAppearance" ->
                        assertEquals("Original", ((EntityAppearanceEdit)result.getEdit()).getAppearanceName());
                case "EntityGeoPointAttribute" -> {
                    var edit = (EntityGeoPointAttributeEdit)result.getEdit();
                    assertEquals("10", edit.getElevation());
                    assertEquals("METER", edit.getElevationUnitOfMeasureTypeName());
                    assertEquals("10", edit.getAltitude());
                    assertEquals("METER", edit.getAltitudeUnitOfMeasureTypeName());
                }
                default -> assertTrue(fixture.transfers > 0);
            }
        }
    }

}
