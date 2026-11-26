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
import com.echothree.control.user.party.common.spec.DepartmentUniversalSpec;
import com.echothree.control.user.party.common.spec.PartySpecFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyCompanyPK;
import com.echothree.model.data.party.common.pk.PartyDepartmentPK;
import com.echothree.model.data.party.common.pk.PartyDetailPK;
import com.echothree.model.data.party.common.pk.PartyDivisionPK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PartyTypePK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.model.data.party.server.entity.PartyDetail;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.party.server.value.PartyCompanyValue;
import com.echothree.model.data.party.server.value.PartyDepartmentValue;
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
public class DepartmentLogicTest {

    private static class TestLogic extends DepartmentLogic {
        private final Party companyParty;
        private final Party divisionParty;
        private final Party departmentParty;
        private final PartyCompany company;
        private final PartyDivision division;
        private final PartyDepartment department;
        private int departmentLookups;
        private int partyLookups;

        private Party party(long id, String typeName) {
            var type = new PartyType(new PartyTypeValue(new PartyTypePK(id), typeName, null, null, false, false, false, 0),
                    EntityPermission.READ_ONLY);
            return new Party(new PartyValue(new PartyPK(id), null, null), EntityPermission.READ_ONLY) {
                @Override
                public PartyDetail getLastDetail() {
                    return new PartyDetail(new PartyDetailValue(new PartyDetailPK(id), getPrimaryKey(), "PARTY", type.getPrimaryKey(),
                            null, null, null, null, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                        @Override
                        public PartyType getPartyType() { return type; }
                    };
                }
            };
        }

        TestLogic(String failure, EntityPermission expectedPermission) {
            companyParty = party(1L, "COMPANY");
            divisionParty = party(2L, "DIVISION");
            departmentParty = party(3L, "wrongType".equals(failure) ? "CUSTOMER" : "DEPARTMENT");
            company = new PartyCompany(new PartyCompanyValue(new PartyCompanyPK(4L), companyParty.getPrimaryKey(),
                    "COMPANY", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                @Override
                public Party getParty() { return companyParty; }
            };
            division = new PartyDivision(new PartyDivisionValue(new PartyDivisionPK(5L), divisionParty.getPrimaryKey(),
                    companyParty.getPrimaryKey(), "DIVISION", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                @Override
                public Party getParty() { return divisionParty; }
                @Override
                public Party getCompanyParty() { return companyParty; }
            };
            department = new PartyDepartment(new PartyDepartmentValue(new PartyDepartmentPK(6L), departmentParty.getPrimaryKey(),
                    divisionParty.getPrimaryKey(), "DEPARTMENT", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE);
            partyControl = new PartyControl() {
                @Override
                public PartyCompany getPartyCompanyByName(String name, EntityPermission permission) {
                    assertEquals("COMPANY", name);
                    assertEquals(EntityPermission.READ_ONLY, permission);
                    return "company".equals(failure) ? null : company;
                }
                @Override
                public PartyCompany getPartyCompany(Party party) {
                    assertSame(companyParty, party);
                    return company;
                }
                @Override
                public PartyDivision getPartyDivisionByName(Party party, String name, EntityPermission permission) {
                    assertSame(companyParty, party);
                    assertEquals("DIVISION", name);
                    assertEquals(EntityPermission.READ_ONLY, permission);
                    return "division".equals(failure) ? null : division;
                }
                @Override
                public PartyDivision getPartyDivision(Party party) {
                    assertSame(divisionParty, party);
                    return division;
                }
                @Override
                public PartyDepartment getPartyDepartmentByName(Party party, String name, EntityPermission permission) {
                    departmentLookups++;
                    assertSame(divisionParty, party);
                    assertEquals("DEPARTMENT", name);
                    assertEquals(expectedPermission, permission);
                    return "department".equals(failure) ? null : department;
                }
                @Override
                public Party getPartyByName(String name, EntityPermission permission) {
                    partyLookups++;
                    assertEquals("PARTY", name);
                    assertEquals(EntityPermission.READ_ONLY, permission);
                    return "party".equals(failure) ? null : departmentParty;
                }
                @Override
                public Party getPartyByEntityInstance(EntityInstance instance) {
                    partyLookups++;
                    return departmentParty;
                }
                @Override
                public PartyDepartment getPartyDepartment(Party party, EntityPermission permission) {
                    departmentLookups++;
                    assertSame(departmentParty, party);
                    assertEquals(expectedPermission, permission);
                    return department;
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
            divisionLogic = new DivisionLogic();
            divisionLogic.partyControl = partyControl;
            divisionLogic.partyLogic = partyLogic;
            divisionLogic.entityInstanceLogic = entityInstanceLogic;
            divisionLogic.companyLogic = new CompanyLogic();
            divisionLogic.companyLogic.partyControl = partyControl;
            divisionLogic.companyLogic.partyLogic = partyLogic;
            divisionLogic.companyLogic.entityInstanceLogic = entityInstanceLogic;
        }
    }

    private DepartmentUniversalSpec spec(int mask) {
        var spec = PartySpecFactory.getDepartmentUniversalSpec();
        if((mask & 1) != 0) { spec.setCompanyName("COMPANY"); }
        if((mask & 2) != 0) { spec.setDivisionName("DIVISION"); }
        if((mask & 4) != 0) { spec.setDepartmentName("DEPARTMENT"); }
        if((mask & 8) != 0) { spec.setPartyName("PARTY"); }
        if((mask & 16) != 0) { spec.setUuid("c20fef36-8c15-49cc-8d5b-97b9d851e86a"); }
        if((mask & 32) != 0) { spec.setEntityRef("ECHO_THREE.Party.3"); }
        return spec;
    }

    @ParameterizedTest
    @ValueSource(ints = { 7, 8, 16, 32 })
    public void resolvesCompleteNamesOrPartyIdentifierWithRequestedPermission(int mask) {
        for(var permission : EntityPermission.values()) {
            var logic = new TestLogic(null, permission);
            var errors = new DummyExecutionErrorAccumulator();
            var result = permission == EntityPermission.READ_ONLY
                    ? logic.getPartyDepartmentByUniversalSpec(errors, spec(mask))
                    : logic.getPartyDepartmentByUniversalSpecForUpdate(errors, spec(mask));
            assertSame(logic.department, result);
            assertFalse(errors.hasExecutionErrors());
            assertEquals(1, logic.departmentLookups);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 9, 15, 23, 39, 24, 40, 48, 63 })
    public void rejectsPartialAndMixedIdentifiersBeforeLookup(int mask) {
        var logic = new TestLogic(null, EntityPermission.READ_WRITE);
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPartyDepartmentByUniversalSpecForUpdate(errors, spec(mask)));
        assertEquals(mask == 4 ? ExecutionErrors.UseOfDepartmentNameRequiresDivisionName.name() : ExecutionErrors.InvalidParameterCount.name(),
                errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, logic.departmentLookups);
        assertEquals(0, logic.partyLookups);
    }

    @ParameterizedTest
    @ValueSource(ints = { 8, 16, 32 })
    public void rejectsNonDepartmentPartyBeforeDepartmentLookup(int mask) {
        var logic = new TestLogic("wrongType", EntityPermission.READ_WRITE);
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPartyDepartmentByUniversalSpecForUpdate(errors, spec(mask)));
        assertEquals(ExecutionErrors.InvalidPartyType.name(), errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        assertEquals(0, logic.departmentLookups);
    }

    @ParameterizedTest
    @ValueSource(ints = { 16, 32 })
    public void unknownPartyIdentifierStopsBeforePartyAndDepartmentLookup(int mask) {
        var logic = new TestLogic("entity", EntityPermission.READ_WRITE);
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPartyDepartmentByUniversalSpecForUpdate(errors, spec(mask)));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, logic.partyLookups);
        assertEquals(0, logic.departmentLookups);
    }

    @ParameterizedTest
    @ValueSource(strings = { "company", "division", "department", "party" })
    public void missingNamesReportCompleteKeys(String failure) {
        var logic = new TestLogic(failure, EntityPermission.READ_ONLY);
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPartyDepartmentByUniversalSpec(errors, spec("party".equals(failure) ? 8 : 7)));
        var expected = switch(failure) {
            case "company" -> ExecutionErrors.UnknownCompanyName;
            case "division" -> ExecutionErrors.UnknownDivisionName;
            case "department" -> ExecutionErrors.UnknownDepartmentName;
            case "party" -> ExecutionErrors.UnknownPartyName;
            default -> throw new IllegalArgumentException();
        };
        var key = switch(failure) {
            case "company" -> new Object[] { "COMPANY" };
            case "division" -> new Object[] { "COMPANY", "DIVISION" };
            case "department" -> new Object[] { "COMPANY", "DIVISION", "DEPARTMENT" };
            case "party" -> new Object[] { "PARTY" };
            default -> throw new IllegalArgumentException();
        };
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(expected.name(), error.getKey());
        assertArrayEquals(key, error.getValues());
    }

    @Test
    public void existingOptionalLookupCanOmitDepartment() {
        var logic = new TestLogic(null, EntityPermission.READ_ONLY);
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.getPartyDepartmentByName(errors, (Party)null, null, null, null, false));
        assertFalse(errors.hasExecutionErrors());
        assertEquals(0, logic.departmentLookups);
    }

}
