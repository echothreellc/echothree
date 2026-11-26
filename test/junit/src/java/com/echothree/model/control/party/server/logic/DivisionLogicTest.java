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

package com.echothree.model.control.party.server.logic;

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.control.user.party.common.spec.DivisionUniversalSpec;
import com.echothree.control.user.party.common.spec.PartySpecFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyCompanyPK;
import com.echothree.model.data.party.common.pk.PartyDetailPK;
import com.echothree.model.data.party.common.pk.PartyDivisionPK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PartyTypePK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.value.PartyCompanyValue;
import com.echothree.model.data.party.server.value.PartyDetailValue;
import com.echothree.model.data.party.server.value.PartyDivisionValue;
import com.echothree.model.data.party.server.value.PartyTypeValue;
import com.echothree.model.data.party.server.value.PartyValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment")
public class DivisionLogicTest {

    private static class TestLogic extends DivisionLogic {
        private int divisionLookups;
        private int partyLookups;
        private final Party party;
        private final PartyDivision division;
        private final Party companyParty;
        private final PartyCompany company;

        TestLogic(String failure, EntityPermission expectedPermission) {
            var partyType = new PartyType(new PartyTypeValue(new PartyTypePK(1L), "wrongType".equals(failure) ? "CUSTOMER" : "DIVISION",
                    null, null, false, false, false, 0), EntityPermission.READ_ONLY);
            party = new Party(new PartyValue(new PartyPK(2L), null, null), EntityPermission.READ_ONLY) {
                @Override
                public PartyDetail getLastDetail() {
                    return new PartyDetail(new PartyDetailValue(new PartyDetailPK(3L), getPrimaryKey(), "PARTY", partyType.getPrimaryKey(),
                            null, null, null, null, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                        @Override
                        public PartyType getPartyType() { return partyType; }
                    };
                }
            };
            var companyType = new PartyType(new PartyTypeValue(new PartyTypePK(10L), "COMPANY", null, null, false, false, false, 0),
                    EntityPermission.READ_ONLY);
            companyParty = new Party(new PartyValue(new PartyPK(11L), null, null), EntityPermission.READ_ONLY) {
                @Override
                public PartyDetail getLastDetail() {
                    return new PartyDetail(new PartyDetailValue(new PartyDetailPK(12L), getPrimaryKey(), "COMPANY_PARTY", companyType.getPrimaryKey(),
                            null, null, null, null, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                        @Override
                        public PartyType getPartyType() { return companyType; }
                    };
                }
            };
            company = new PartyCompany(new PartyCompanyValue(new PartyCompanyPK(13L), companyParty.getPrimaryKey(),
                    "COMPANY", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                @Override
                public Party getParty() { return companyParty; }
            };
            companyLogic = new CompanyLogic() {
                @Override
                public PartyCompany getPartyCompanyByName(ExecutionErrorAccumulator eea, String companyName, String partyName,
                        UniversalEntitySpec spec, boolean required) {
                    assertEquals("COMPANY", companyName);
                    if("company".equals(failure)) {
                        eea.addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
                    }
                    return "company".equals(failure) ? null : company;
                }
            };
            division = new PartyDivision(new PartyDivisionValue(new PartyDivisionPK(4L), party.getPrimaryKey(), companyParty.getPrimaryKey(), "DIVISION", false, 0, 0L, Long.MAX_VALUE),
                    EntityPermission.READ_WRITE);
            partyControl = new PartyControl() {
                @Override
                public PartyCompany getPartyCompany(Party selectedCompany) {
                    assertSame(companyParty, selectedCompany);
                    return company;
                }

                @Override
                public PartyDivision getPartyDivisionByName(Party selectedCompany, String name, EntityPermission permission) {
                    assertSame(companyParty, selectedCompany);
                    assertEquals("DIVISION", name);
                    assertEquals(expectedPermission, permission);
                    divisionLookups++;
                    return "division".equals(failure) ? null : division;
                }

                @Override
                public Party getPartyByName(String name, EntityPermission permission) {
                    assertEquals("PARTY", name);
                    assertEquals(EntityPermission.READ_ONLY, permission);
                    partyLookups++;
                    return "party".equals(failure) ? null : party;
                }

                @Override
                public Party getPartyByEntityInstance(EntityInstance instance) {
                    partyLookups++;
                    return party;
                }

                @Override
                public PartyDivision getPartyDivision(Party selectedParty, EntityPermission permission) {
                    assertSame(party, selectedParty);
                    assertEquals(expectedPermission, permission);
                    divisionLookups++;
                    return division;
                }
            };
            entityInstanceLogic = new EntityInstanceLogic() {
                @Override
                public EntityInstance getEntityInstance(ExecutionErrorAccumulator eea, UniversalEntitySpec spec, String vendor, String... types) {
                    assertEquals(ComponentVendors.ECHO_THREE.name(), vendor);
                    assertArrayEquals(new String[] { EntityTypes.Party.name() }, types);
                    if("entity".equals(failure)) {
                        eea.addExecutionError(ExecutionErrors.UnknownUuid.name(), spec.getUuid());
                    }
                    return null;
                }
            };
            partyLogic = new PartyLogic();
            partyLogic.partyControl = partyControl;
        }
    }

    private DivisionUniversalSpec spec(int mask) {
        var spec = PartySpecFactory.getDivisionUniversalSpec();
        if((mask & 1) != 0) { spec.setCompanyName("COMPANY"); spec.setDivisionName("DIVISION"); }
        if((mask & 2) != 0) { spec.setPartyName("PARTY"); }
        if((mask & 4) != 0) { spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a"); }
        if((mask & 8) != 0) { spec.setEntityRef("ECHO_THREE.Party.2"); }
        return spec;
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 4, 8 })
    public void resolvesNamesOrPartyIdentifiersWithRequestedDivisionPermission(int mask) {
        for(var permission : EntityPermission.values()) {
            var logic = new TestLogic(null, permission);
            var errors = new DummyExecutionErrorAccumulator();
            var division = permission == EntityPermission.READ_ONLY
                    ? logic.getPartyDivisionByUniversalSpec(errors, spec(mask))
                    : logic.getPartyDivisionByUniversalSpecForUpdate(errors, spec(mask));
            assertSame(logic.division, division);
            assertFalse(errors.hasExecutionErrors());
            assertEquals(1, logic.divisionLookups);
            assertEquals(mask == 1 ? 0 : 1, logic.partyLookups);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 3, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15 })
    public void rejectsMissingOrAmbiguousIdentifiersBeforeLookup(int mask) {
        var logic = new TestLogic(null, EntityPermission.READ_WRITE);
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPartyDivisionByUniversalSpecForUpdate(errors, spec(mask)));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, logic.divisionLookups);
        assertEquals(0, logic.partyLookups);
    }

    @ParameterizedTest
    @ValueSource(ints = { 2, 4, 8 })
    public void rejectsNonDivisionPartyBeforeDivisionLookup(int mask) {
        var logic = new TestLogic("wrongType", EntityPermission.READ_WRITE);
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPartyDivisionByUniversalSpecForUpdate(errors, spec(mask)));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.InvalidPartyType.name(), error.getKey());
        assertArrayEquals(new Object[] { "CUSTOMER" }, error.getValues());
        assertEquals(0, logic.divisionLookups);
    }

    @ParameterizedTest
    @ValueSource(ints = { 4, 8 })
    public void unknownPartyIdentifierStopsBeforePartyAndDivisionLookup(int mask) {
        var logic = new TestLogic("entity", EntityPermission.READ_WRITE);
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPartyDivisionByUniversalSpecForUpdate(errors, spec(mask)));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.partyLookups);
        assertEquals(0, logic.divisionLookups);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2 })
    public void unknownNamesReportMatchingErrorAndName(int mask) {
        var logic = new TestLogic(mask == 1 ? "division" : "party", EntityPermission.READ_ONLY);
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPartyDivisionByUniversalSpec(errors, spec(mask)));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(mask == 1 ? ExecutionErrors.UnknownDivisionName.name() : ExecutionErrors.UnknownPartyName.name(), error.getKey());
        assertArrayEquals(mask == 1 ? new Object[] { "COMPANY", "DIVISION" } : new Object[] { "PARTY" }, error.getValues());
    }

    @Test
    public void existingOptionalLookupCanOmitDivision() {
        var logic = new TestLogic(null, EntityPermission.READ_ONLY);
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPartyDivisionByName(errors, (Party)null, null, null, null, false));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(0, logic.divisionLookups);
    }

    @Test
    public void unknownCompanyStopsBeforeDivisionLookup() {
        var logic = new TestLogic("company", EntityPermission.READ_ONLY);
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPartyDivisionByUniversalSpec(errors, spec(1)));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.UnknownCompanyName.name(), error.getKey());
        assertArrayEquals(new Object[] { "COMPANY" }, error.getValues());
        assertEquals(0, logic.divisionLookups);
    }

    @Test
    public void divisionNameRequiresCompanyAndCompanyAloneIsInsufficient() {
        var logic = new TestLogic(null, EntityPermission.READ_ONLY);
        var spec = PartySpecFactory.getDivisionUniversalSpec();
        spec.setDivisionName("DIVISION");
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPartyDivisionByUniversalSpec(errors, spec));
        assertEquals(ExecutionErrors.UseOfDivisionNameRequiresCompanyName.name(),
                errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());

        spec.setDivisionName(null);
        spec.setCompanyName("COMPANY");
        errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPartyDivisionByUniversalSpec(errors, spec));
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, logic.divisionLookups);
    }

}
