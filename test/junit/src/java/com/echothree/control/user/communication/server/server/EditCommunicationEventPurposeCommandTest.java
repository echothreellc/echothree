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

package com.echothree.control.user.communication.server.server;

import com.echothree.control.user.communication.common.edit.CommunicationEditFactory;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.data.communication.common.pk.CommunicationEventPurposePK;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurpose;
import com.echothree.model.data.communication.server.value.CommunicationEventPurposeValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class EditCommunicationEventPurposeCommandTest {

    private static class TestCommand extends EditCommunicationEventPurposeCommand {
        private final CommunicationEventPurpose selectedPurpose = new CommunicationEventPurpose(
                new CommunicationEventPurposeValue(new CommunicationEventPurposePK(1L), null, null), EntityPermission.READ_WRITE);
        private final CommunicationEventPurpose otherPurpose = new CommunicationEventPurpose(
                new CommunicationEventPurposeValue(new CommunicationEventPurposePK(2L), null, null), EntityPermission.READ_ONLY);

        TestCommand(String nameOwner) {
            edit = CommunicationEditFactory.getCommunicationEventPurposeEdit();
            edit.setCommunicationEventPurposeName("PURPOSE");
            communicationControl = new CommunicationControl() {
                @Override
                public CommunicationEventPurpose getCommunicationEventPurposeByName(String communicationEventPurposeName) {
                    assertEquals("PURPOSE", communicationEventPurposeName);
                    return switch(nameOwner) {
                        case "same" -> selectedPurpose;
                        case "other" -> otherPurpose;
                        case "unused" -> null;
                        default -> throw new IllegalArgumentException(nameOwner);
                    };
                }
            };
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "same", "unused", "other" })
    public void permitsAvailableNamesAndRejectsNamesOwnedByAnotherPurpose(String nameOwner) {
        var command = new TestCommand(nameOwner);
        command.canUpdate(command.selectedPurpose);

        assertSame(command.selectedPurpose, command.getLockEntity(command.selectedPurpose));
        assertEquals("other".equals(nameOwner), command.hasExecutionErrors());
        if(command.hasExecutionErrors()) {
            var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
            assertEquals(ExecutionErrors.DuplicateCommunicationEventPurposeName.name(), error.getKey());
            assertArrayEquals(new Object[] { "PURPOSE" }, error.getValues());
        }
    }

}
