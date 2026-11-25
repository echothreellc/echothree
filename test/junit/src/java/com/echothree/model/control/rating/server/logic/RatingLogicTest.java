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

package com.echothree.model.control.rating.server.logic;

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.control.user.rating.common.spec.RatingSpecFactory;
import com.echothree.control.user.rating.common.spec.RatingUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.rating.common.pk.RatingPK;
import com.echothree.model.data.rating.server.entity.Rating;
import com.echothree.model.data.rating.server.value.RatingValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class RatingLogicTest {

    private static class TestLogic extends RatingLogic {
        private final Rating rating = new Rating(new RatingValue(new RatingPK(1L), null, null), EntityPermission.READ_WRITE);
        private int lookups;

        TestLogic(boolean missingEntity, EntityPermission expectedPermission) {
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, UniversalEntitySpec universalSpec,
                        String componentVendorName, String... entityTypeNames) {
                    assertEquals(ComponentVendors.ECHO_THREE.name(), componentVendorName);
                    assertArrayEquals(new String[] { EntityTypes.Rating.name() }, entityTypeNames);
                    if(missingEntity) {
                        eea.addExecutionError(ExecutionErrors.UnknownUuid.name(), universalSpec.getUuid());
                    }
                    return null;
                }
            };
            ratingControl = new RatingControl() {
                @Override
                public Rating getRatingByName(String ratingName, EntityPermission entityPermission) {
                    assertEquals("RATING", ratingName);
                    assertEquals(expectedPermission, entityPermission);
                    lookups++;
                    return missingEntity ? null : rating;
                }

                @Override
                public Rating getRatingByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
                    assertEquals(expectedPermission, entityPermission);
                    lookups++;
                    return rating;
                }
            };
        }
    }

    private RatingUniversalSpec spec(int identifierMask) {
        var spec = RatingSpecFactory.getRatingUniversalSpec();
        if((identifierMask & 1) != 0) {
            spec.setRatingName("RATING");
        }
        if((identifierMask & 2) != 0) {
            spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a");
        }
        if((identifierMask & 4) != 0) {
            spec.setEntityRef("ECHO_THREE.Rating.1");
        }
        return spec;
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 4 })
    public void resolvesEachIdentifierWithRequestedPermission(int identifierMask) {
        for(var entityPermission : EntityPermission.values()) {
            var logic = new TestLogic(false, entityPermission);
            var errors = new DummyExecutionErrorAccumulator();
            var rating = entityPermission == EntityPermission.READ_ONLY
                    ? logic.getRatingByUniversalSpec(errors, spec(identifierMask))
                    : logic.getRatingByUniversalSpecForUpdate(errors, spec(identifierMask));

            assertSame(logic.rating, rating);
            assertFalse(errors.hasExecutionErrors());
            assertEquals(1, logic.lookups);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 3, 5, 6, 7 })
    public void rejectsMissingOrAmbiguousIdentifiersBeforeLookup(int identifierMask) {
        var logic = new TestLogic(false, EntityPermission.READ_WRITE);
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getRatingByUniversalSpecForUpdate(errors, spec(identifierMask)));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, logic.lookups);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 4 })
    public void missingIdentifierStopsLookupAndReportsError(int identifierMask) {
        var logic = new TestLogic(true, EntityPermission.READ_WRITE);
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getRatingByUniversalSpecForUpdate(errors, spec(identifierMask)));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(identifierMask == 1 ? ExecutionErrors.UnknownRatingName.name() : ExecutionErrors.UnknownUuid.name(), error.getKey());
        if(identifierMask == 1) {
            assertArrayEquals(new Object[] { "RATING" }, error.getValues());
        }
        assertEquals(identifierMask == 1 ? 1 : 0, logic.lookups);
    }

}
