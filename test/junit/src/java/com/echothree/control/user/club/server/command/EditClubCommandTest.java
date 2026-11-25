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

package com.echothree.control.user.club.server.command;

import com.echothree.control.user.club.common.edit.ClubEditFactory;
import com.echothree.model.control.club.server.control.ClubControl;
import com.echothree.model.control.subscription.common.SubscriptionConstants;
import com.echothree.model.control.subscription.server.logic.SubscriptionTypeLogic;
import com.echothree.model.data.club.common.pk.ClubPK;
import com.echothree.model.data.club.server.entity.Club;
import com.echothree.model.data.club.server.value.ClubValue;
import com.echothree.model.data.subscription.common.pk.SubscriptionTypePK;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.model.data.subscription.server.value.SubscriptionTypeValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class EditClubCommandTest {

    private static class TestCommand extends EditClubCommand {
        private final Club selectedClub = new Club(new ClubValue(new ClubPK(1L), null, null), EntityPermission.READ_WRITE);
        private final Club otherClub = new Club(new ClubValue(new ClubPK(2L), null, null), EntityPermission.READ_ONLY);
        private final SubscriptionType selectedSubscriptionType = new SubscriptionType(
                new SubscriptionTypeValue(new SubscriptionTypePK(3L), null, null), EntityPermission.READ_ONLY);

        TestCommand(String subscriptionOwner) {
            edit = ClubEditFactory.getClubEdit();
            edit.setClubName("CLUB");
            edit.setSubscriptionTypeName("SUBSCRIPTION");
            clubControl = new ClubControl() {
                @Override
                public Club getClubByName(String clubName) {
                    return selectedClub;
                }

                @Override
                public Club getClubBySubscriptionType(SubscriptionType subscriptionType) {
                    assertSame(selectedSubscriptionType, subscriptionType);
                    return switch(subscriptionOwner) {
                        case "same" -> selectedClub;
                        case "other" -> otherClub;
                        case "unused" -> null;
                        default -> throw new IllegalArgumentException(subscriptionOwner);
                    };
                }
            };
            subscriptionTypeLogic = new SubscriptionTypeLogic() {
                @Override
                public SubscriptionType getSubscriptionTypeByName(ExecutionErrorAccumulator eea, String subscriptionKindName,
                        String subscriptionTypeName) {
                    assertEquals(SubscriptionConstants.SubscriptionKind_CLUB, subscriptionKindName);
                    assertEquals("SUBSCRIPTION", subscriptionTypeName);
                    return selectedSubscriptionType;
                }
            };
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "same", "unused", "other" })
    public void subscriptionUniquenessKeepsSelectedClubAsLockTarget(String subscriptionOwner) {
        var command = new TestCommand(subscriptionOwner);
        command.canUpdate(command.selectedClub);

        assertSame(command.selectedClub, command.getLockEntity(command.selectedClub));
        assertEquals("other".equals(subscriptionOwner), command.hasExecutionErrors());
        if(command.hasExecutionErrors()) {
            var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
            assertEquals(ExecutionErrors.DuplicateSubscriptionType.name(), error.getKey());
            assertArrayEquals(new Object[] { SubscriptionConstants.SubscriptionKind_CLUB, "SUBSCRIPTION" }, error.getValues());
        }
    }

}
