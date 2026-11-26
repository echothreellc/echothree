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

package com.echothree.model.control.term.server.logic;

import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.party.common.pk.PartyDetailPK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PartyTypePK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.value.PartyDetailValue;
import com.echothree.model.data.party.server.value.PartyValue;
import com.echothree.model.data.term.common.pk.PartyTermPK;
import com.echothree.model.data.term.common.pk.TermPK;
import com.echothree.model.data.term.server.entity.PartyTerm;
import com.echothree.model.data.term.server.value.PartyTermValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment")
public class TermLogicPartyTermTest {

    private static class TestLogic extends TermLogic {
        private int lookups;
        private final Party party = new Party(new PartyValue(new PartyPK(1L), null, null), EntityPermission.READ_ONLY) {
            @Override public PartyDetail getLastDetail() {
                return new PartyDetail(new PartyDetailValue(new PartyDetailPK(2L), getPrimaryKey(), "PARTY", new PartyTypePK(1L),
                        null, null, null, null, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);
            }
        };
        private final PartyTerm partyTerm = new PartyTerm(new PartyTermValue(new PartyTermPK(3L), party.getPrimaryKey(),
                new TermPK(4L), true, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);

        TestLogic(boolean missing, EntityPermission expectedPermission) {
            termControl = new TermControl() {
                @Override public PartyTerm getPartyTerm(Party selectedParty, EntityPermission permission) {
                    assertSame(party, selectedParty);
                    assertEquals(expectedPermission, permission);
                    lookups++;
                    return missing ? null : partyTerm;
                }
            };
        }
    }

    @ParameterizedTest
    @EnumSource(EntityPermission.class)
    public void permissionAndConvenienceLookupsReturnSelectedPartyTerm(EntityPermission permission) {
        var logic = new TestLogic(false, permission);
        var errors = new DummyExecutionErrorAccumulator();
        assertSame(logic.partyTerm, logic.getPartyTerm(errors, logic.party, permission));
        assertSame(logic.partyTerm, permission == EntityPermission.READ_ONLY
                ? logic.getPartyTerm(errors, logic.party) : logic.getPartyTermForUpdate(errors, logic.party));
        assertSame(logic.partyTerm, logic.getPartyTerm(null, logic.party, permission));
        assertEquals(3, logic.lookups);
        assertFalse(errors.hasExecutionErrors());
    }

    @ParameterizedTest
    @EnumSource(EntityPermission.class)
    public void missingPartyTermAccumulatesErrorWithPartyName(EntityPermission permission) {
        var logic = new TestLogic(true, permission);
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPartyTerm(errors, logic.party, permission));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.UnknownPartyTerm.name(), error.getKey());
        assertArrayEquals(new Object[] { "PARTY" }, error.getValues());
        assertEquals(1, logic.lookups);
    }

}
