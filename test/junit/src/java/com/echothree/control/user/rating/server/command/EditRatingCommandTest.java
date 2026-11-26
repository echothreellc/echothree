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

package com.echothree.control.user.rating.server.command;

import com.echothree.control.user.rating.common.edit.RatingEditFactory;
import com.echothree.control.user.rating.common.spec.RatingSpecFactory;
import com.echothree.control.user.rating.common.spec.RatingUniversalSpec;
import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.rating.common.transfer.RatingTransfer;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.rating.server.logic.RatingLogic;
import com.echothree.model.control.rating.server.logic.RatingTypeListItemLogic;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.rating.common.pk.RatingDetailPK;
import com.echothree.model.data.rating.common.pk.RatingPK;
import com.echothree.model.data.rating.common.pk.RatingTypeListItemDetailPK;
import com.echothree.model.data.rating.common.pk.RatingTypeListItemPK;
import com.echothree.model.data.rating.common.pk.RatingTypePK;
import com.echothree.model.data.rating.server.entity.Rating;
import com.echothree.model.data.rating.server.entity.RatingDetail;
import com.echothree.model.data.rating.server.entity.RatingType;
import com.echothree.model.data.rating.server.entity.RatingTypeListItem;
import com.echothree.model.data.rating.server.entity.RatingTypeListItemDetail;
import com.echothree.model.data.rating.server.value.RatingDetailValue;
import com.echothree.model.data.rating.server.value.RatingTypeListItemDetailValue;
import com.echothree.model.data.rating.server.value.RatingTypeListItemValue;
import com.echothree.model.data.rating.server.value.RatingTypeValue;
import com.echothree.model.data.rating.server.value.RatingValue;
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
public class EditRatingCommandTest {

    private static class TestCommand extends EditRatingCommand {
        private final RatingType ratingType = new RatingType(new RatingTypeValue(new RatingTypePK(1L), null, null), EntityPermission.READ_ONLY);
        private final RatingTypeListItem currentListItem = new RatingTypeListItem(
                new RatingTypeListItemValue(new RatingTypeListItemPK(2L), null, null), EntityPermission.READ_ONLY) {
            @Override
            public RatingTypeListItemDetail getLastDetail() {
                return new RatingTypeListItemDetail(new RatingTypeListItemDetailValue(new RatingTypeListItemDetailPK(3L),
                        getPrimaryKey(), ratingType.getPrimaryKey(), "OLD", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                    @Override
                    public RatingType getRatingType() {
                        return ratingType;
                    }
                };
            }
        };
        private final RatingTypeListItem replacementListItem = new RatingTypeListItem(
                new RatingTypeListItemValue(new RatingTypeListItemPK(4L), null, null), EntityPermission.READ_ONLY);
        private final Rating rating = new Rating(new RatingValue(new RatingPK(5L), null, null), EntityPermission.READ_WRITE) {
            @Override
            public RatingDetail getLastDetail() {
                return new RatingDetail(detailValue(), EntityPermission.READ_ONLY) {
                    @Override
                    public RatingTypeListItem getRatingTypeListItem() {
                        return currentListItem;
                    }
                };
            }
        };
        private boolean missingListItem;
        private boolean missingRating;
        private boolean updated;
        private boolean transferred;

        private RatingDetailValue detailValue() {
            return new RatingDetailValue(new RatingDetailPK(6L), rating.getPrimaryKey(), "RATING", currentListItem.getPrimaryKey(),
                    new EntityInstancePK(7L), new EntityInstancePK(8L), 0L, Long.MAX_VALUE);
        }

        TestCommand(EditMode requestedEditMode) {
            editMode = requestedEditMode;
            spec = RatingSpecFactory.getRatingUniversalSpec();
            spec.setRatingName("RATING");
            edit = RatingEditFactory.getRatingEdit();
            edit.setRatingTypeListItemName("NEW");
            ratingLogic = new RatingLogic() {
                @Override
                public Rating getRatingByUniversalSpec(ExecutionErrorAccumulator eea, RatingUniversalSpec universalSpec, EntityPermission entityPermission) {
                    assertSame(spec, universalSpec);
                    assertEquals(editMode == EditMode.UPDATE ? EntityPermission.READ_WRITE : EntityPermission.READ_ONLY, entityPermission);
                    if(missingRating) {
                        eea.addExecutionError(ExecutionErrors.UnknownRatingName.name(), universalSpec.getRatingName());
                    }
                    return missingRating ? null : rating;
                }
            };
            ratingTypeListItemLogic = new RatingTypeListItemLogic() {
                @Override
                public RatingTypeListItem getRatingTypeListItemByName(ExecutionErrorAccumulator eea, RatingType selectedRatingType, String ratingTypeListItemName) {
                    assertSame(ratingType, selectedRatingType);
                    assertEquals("NEW", ratingTypeListItemName);
                    if(missingListItem) {
                        eea.addExecutionError(ExecutionErrors.UnknownRatingTypeListItemName.name(), "ECHO_THREE", "Item", "TYPE", ratingTypeListItemName);
                    }
                    return missingListItem ? null : replacementListItem;
                }
            };
            ratingControl = new RatingControl() {
                @Override
                public RatingDetailValue getRatingDetailValueForUpdate(Rating selectedRating) {
                    assertSame(rating, selectedRating);
                    return detailValue();
                }

                @Override
                public void updateRatingFromValue(RatingDetailValue value, BasePK updatedBy) {
                    assertEquals(replacementListItem.getPrimaryKey(), value.getRatingTypeListItemPK());
                    assertEquals("RATING", value.getRatingName());
                    assertEquals(new EntityInstancePK(7L), value.getRatedEntityInstancePK());
                    assertEquals(new EntityInstancePK(8L), value.getRatedByEntityInstancePK());
                    updated = true;
                }

                @Override
                public RatingTransfer getRatingTransfer(UserVisit userVisit, Rating selectedRating) {
                    assertSame(rating, selectedRating);
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
            assertSame(rating, lockEntity);
            return null;
        }

        void assertInvalidEditResult() {
            edit.setRatingTypeListItemName(null);
            assertTrue(validate().getHasErrors());
            assertNotNull(getBaseResultAfterErrors());
            assertEquals(missingRating, hasExecutionErrors());
            assertEquals(!missingRating, transferred);
        }
    }

    @ParameterizedTest
    @EnumSource(EditMode.class)
    public void resolvesRatingWithRequestedPermissionAndUsesItAsLockTarget(EditMode editMode) {
        var command = new TestCommand(editMode);
        assertSame(command.rating, command.getEntity(command.getResult()));
        assertSame(command.rating, command.getLockEntity(command.rating));
    }

    @Test
    public void lockCopiesCurrentListItemName() {
        var command = new TestCommand(EditMode.LOCK);
        var edit = command.getEdit();
        command.doLock(edit, command.rating);
        assertEquals("OLD", edit.getRatingTypeListItemName());
    }

    @Test
    public void updateUsesReplacementFromExistingRatingType() {
        var command = new TestCommand(EditMode.UPDATE);
        command.canUpdate(command.rating);
        assertFalse(command.hasExecutionErrors());
        command.doUpdate(command.rating);
        assertTrue(command.updated);
        assertSame(command.currentListItem, command.rating.getLastDetail().getRatingTypeListItem());
    }

    @Test
    public void missingReplacementPropagatesFullKeyAndPreventsUpdate() {
        var command = new TestCommand(EditMode.UPDATE);
        command.missingListItem = true;
        command.canUpdate(command.rating);
        var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.UnknownRatingTypeListItemName.name(), error.getKey());
        assertArrayEquals(new Object[] { "ECHO_THREE", "Item", "TYPE", "NEW" }, error.getValues());
        assertFalse(command.updated);
    }

    @ParameterizedTest
    @ValueSource(booleans = { false, true })
    public void invalidEditRetainsResultWithoutDereferencingMissingRating(boolean missingRating) {
        var command = new TestCommand(EditMode.UPDATE);
        command.missingRating = missingRating;
        command.assertInvalidEditResult();
    }

}
