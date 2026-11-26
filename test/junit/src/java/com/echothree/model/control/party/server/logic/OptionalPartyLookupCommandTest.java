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

import com.echothree.control.user.party.common.form.PartyFormFactory;
import com.echothree.control.user.party.server.command.AddEmployeeToDepartmentCommand;
import com.echothree.control.user.party.server.command.AddEmployeeToDivisionCommand;
import com.echothree.control.user.party.server.command.GetDepartmentChoicesCommand;
import com.echothree.control.user.party.server.command.RemoveEmployeeFromDivisionCommand;
import com.echothree.control.user.party.server.command.RemoveEmployeFromDepartmentCommand;
import com.echothree.control.user.search.common.form.SearchFormFactory;
import com.echothree.control.user.search.server.command.SearchLeavesCommand;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.employee.server.logic.LeaveLogic;
import com.echothree.model.control.party.common.choice.DepartmentChoicesBean;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.data.employee.common.pk.EmployeeTypePK;
import com.echothree.model.data.employee.common.pk.PartyEmployeePK;
import com.echothree.model.data.employee.server.entity.LeaveType;
import com.echothree.model.data.employee.server.entity.PartyEmployee;
import com.echothree.model.data.employee.server.value.PartyEmployeeValue;
import com.echothree.model.data.party.common.pk.PartyCompanyPK;
import com.echothree.model.data.party.common.pk.PartyDepartmentPK;
import com.echothree.model.data.party.common.pk.PartyDivisionPK;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import com.echothree.model.data.party.server.value.PartyCompanyValue;
import com.echothree.model.data.party.server.value.PartyDepartmentValue;
import com.echothree.model.data.party.server.value.PartyDivisionValue;
import com.echothree.model.data.party.server.value.PartyValue;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchType;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/** Exercises the actual command callers with real parent-resolution Logic. */
@SuppressWarnings("UnnecessaryAssignment")
public class OptionalPartyLookupCommandTest {

    private static void inject(Object target, String fieldName, Object value) throws Exception {
        var declaringClass = target.getClass();
        while(declaringClass != null) {
            try {
                var field = declaringClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target, value);
                break;
            } catch(NoSuchFieldException exception) {
                declaringClass = declaringClass.getSuperclass();
            }
        }
        assertNotNull(declaringClass, fieldName);
    }

    private static void execute(BaseSimpleCommand<?> command) throws Exception {
        Class<?> declaringClass = command.getClass();
        while(declaringClass.isAnonymousClass()) {
            declaringClass = declaringClass.getSuperclass();
        }
        var method = declaringClass.getDeclaredMethod("execute");
        method.setAccessible(true);
        method.invoke(command);
    }

    private static class Fixture {
        private final List<String> lookups = new ArrayList<>();
        private int relationships;
        private int choices;
        private final Party companyParty = party(1L);
        private final Party divisionParty = party(2L);
        private final Party departmentParty = party(3L);
        private final Party employeeParty = party(4L);
        private final PartyCompany company = new PartyCompany(new PartyCompanyValue(new PartyCompanyPK(1L), companyParty.getPrimaryKey(),
                "COMPANY", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
            @Override public Party getParty() { return companyParty; }
        };
        private final PartyDivision division = new PartyDivision(new PartyDivisionValue(new PartyDivisionPK(2L), divisionParty.getPrimaryKey(),
                companyParty.getPrimaryKey(), "DIVISION", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
            @Override public Party getParty() { return divisionParty; }
            @Override public Party getCompanyParty() { return companyParty; }
        };
        private final PartyDepartment department = new PartyDepartment(new PartyDepartmentValue(new PartyDepartmentPK(3L), departmentParty.getPrimaryKey(),
                divisionParty.getPrimaryKey(), "DEPARTMENT", false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
            @Override public Party getParty() { return departmentParty; }
        };
        private final CompanyLogic companyLogic = new CompanyLogic();
        private final DivisionLogic divisionLogic = new DivisionLogic();
        private final DepartmentLogic departmentLogic = new DepartmentLogic();
        private final PartyControl partyControl = new PartyControl() {
            @Override public PartyCompany getPartyCompanyByName(String name, EntityPermission permission) {
                lookups.add("company:" + name);
                assertEquals(EntityPermission.READ_ONLY, permission);
                return "COMPANY".equals(name) ? company : null;
            }
            @Override public PartyDivision getPartyDivisionByName(Party parent, String name, EntityPermission permission) {
                assertSame(companyParty, parent);
                lookups.add("division:" + name);
                assertEquals(EntityPermission.READ_ONLY, permission);
                return "DIVISION".equals(name) ? division : null;
            }
            @Override public PartyDepartment getPartyDepartmentByName(Party parent, String name, EntityPermission permission) {
                assertSame(divisionParty, parent);
                lookups.add("department:" + name);
                assertEquals(EntityPermission.READ_ONLY, permission);
                return "DEPARTMENT".equals(name) ? department : null;
            }
            @Override public PartyCompany getPartyCompany(Party party) {
                assertSame(companyParty, party);
                return company;
            }
            @Override public PartyDivision getPartyDivision(Party party) {
                assertSame(divisionParty, party);
                return division;
            }
            @Override public PartyDivision getPartyDivision(Party party, EntityPermission permission) {
                assertSame(divisionParty, party);
                return division;
            }
            @Override public DepartmentChoicesBean getDepartmentChoices(Party party, String defaultChoice, boolean allowNull) {
                assertSame(divisionParty, party);
                assertEquals("DEPARTMENT", defaultChoice);
                assertTrue(allowNull);
                choices++;
                return new DepartmentChoicesBean(List.of("Department"), List.of("DEPARTMENT"), defaultChoice);
            }
            @Override public PartyCompany getDefaultPartyCompany(EntityPermission permission) {
                throw new AssertionError("Optional lookup must not select a default company");
            }
            @Override public PartyDivision getDefaultPartyDivision(Party parent, EntityPermission permission) {
                throw new AssertionError("Optional lookup must not select a default division");
            }
        };
        private final PartyRelationshipLogic partyRelationshipLogic = new PartyRelationshipLogic() {
            private void record(Party target, Party employee, Party expected) {
                assertSame(expected, target);
                assertSame(employeeParty, employee);
                relationships++;
            }
            @Override public PartyRelationship addEmployeeToDivision(ExecutionErrorAccumulator eea, Party target, Party employee, BasePK createdBy) {
                record(target, employee, divisionParty);
                return null;
            }
            @Override public void removeEmployeeFromDivision(ExecutionErrorAccumulator eea, Party target, Party employee, BasePK deletedBy) {
                record(target, employee, divisionParty);
            }
            @Override public PartyRelationship addEmployeeToDepartment(ExecutionErrorAccumulator eea, Party target, Party employee, BasePK createdBy) {
                record(target, employee, departmentParty);
                return null;
            }
            @Override public void removeEmployeeFromDepartment(ExecutionErrorAccumulator eea, Party target, Party employee, BasePK deletedBy) {
                record(target, employee, departmentParty);
            }
        };

        private static Party party(long identifier) {
            return new Party(new PartyValue(new PartyPK(identifier), null, null), EntityPermission.READ_ONLY);
        }

        Fixture() {
            var partyLogic = new PartyLogic() {
                @Override public String checkPartyType(ExecutionErrorAccumulator eea, Party party, String... types) {
                    return types[0];
                }
                @Override public Party getPartyByName(ExecutionErrorAccumulator eea, String name, String... types) {
                    lookups.add("party:" + name);
                    assertArrayEquals(new String[] { "DIVISION" }, types);
                    if(!"DIVISION_PARTY".equals(name)) {
                        eea.addExecutionError(ExecutionErrors.UnknownPartyName.name(), name);
                    }
                    return "DIVISION_PARTY".equals(name) ? divisionParty : null;
                }
            };
            companyLogic.partyControl = partyControl;
            companyLogic.partyLogic = partyLogic;
            companyLogic.entityInstanceLogic = new EntityInstanceLogic() {};
            divisionLogic.partyControl = partyControl;
            divisionLogic.companyLogic = companyLogic;
            divisionLogic.partyLogic = partyLogic;
            divisionLogic.entityInstanceLogic = new EntityInstanceLogic() {};
            departmentLogic.partyControl = partyControl;
            departmentLogic.divisionLogic = divisionLogic;
            departmentLogic.partyLogic = partyLogic;
            departmentLogic.entityInstanceLogic = new EntityInstanceLogic() {};
        }

        void configureMembership(BaseSimpleCommand<?> command, BaseForm form, boolean departmentTarget) throws Exception {
            inject(command, "form", form);
            inject(command, "companyLogic", companyLogic);
            inject(command, "divisionLogic", divisionLogic);
            if(departmentTarget) {
                inject(command, "departmentLogic", departmentLogic);
            }
            inject(command, "partyRelationshipLogic", partyRelationshipLogic);
            inject(command, "employeeLogic", new EmployeeLogic() {
                @Override public PartyEmployee getPartyEmployeeByName(ExecutionErrorAccumulator eea, String employeeName, String partyName) {
                    return new PartyEmployee(new PartyEmployeeValue(new PartyEmployeePK(4L), employeeParty.getPrimaryKey(), "EMPLOYEE", new EmployeeTypePK(1L),
                            0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
                        @Override public Party getParty() { return employeeParty; }
                    };
                }
            });
        }
    }

    private static void assertError(BaseSimpleCommand<?> command, ExecutionErrors expected, Object... values) {
        assertTrue(command.hasExecutionErrors());
        var errors = command.getExecutionErrors().get(Messages.EXECUTION_ERROR);
        var error = errors.next();
        assertEquals(expected.name(), error.getKey());
        assertArrayEquals(values, error.getValues());
        assertFalse(errors.hasNext());
    }

    @ParameterizedTest
    @ValueSource(strings = { "addDivision", "removeDivision", "addDepartment", "removeDepartment" })
    public void membershipPreservesSuccessAndParentErrors(String operation) throws Exception {
        for(var scenario : List.of("valid", "noCompany", "noDivision", "noParents", "unknownCompany", "unknownDivision", "unknownTarget")) {
            var fixture = new Fixture();
            var departmentTarget = operation.endsWith("Department");
            BaseSimpleCommand<?> command = switch(operation) {
                case "addDivision" -> new AddEmployeeToDivisionCommand() {
                    @Override public PartyPK getPartyPK() { return null; }
                };
                case "removeDivision" -> new RemoveEmployeeFromDivisionCommand() {
                    @Override public PartyPK getPartyPK() { return null; }
                };
                case "addDepartment" -> new AddEmployeeToDepartmentCommand() {
                    @Override public PartyPK getPartyPK() { return null; }
                };
                case "removeDepartment" -> new RemoveEmployeFromDepartmentCommand() {
                    @Override public PartyPK getPartyPK() { return null; }
                };
                default -> throw new IllegalArgumentException(operation);
            };
            BaseForm form = switch(operation) {
                case "addDivision" -> PartyFormFactory.getAddEmployeeToDivisionForm();
                case "removeDivision" -> PartyFormFactory.getRemoveEmployeeFromDivisionForm();
                case "addDepartment" -> PartyFormFactory.getAddEmployeeToDepartmentForm();
                case "removeDepartment" -> PartyFormFactory.getRemoveEmployeeFromDepartmentForm();
                default -> throw new IllegalArgumentException(operation);
            };
            form.set("CompanyName", ("noCompany".equals(scenario) || "noParents".equals(scenario)) ? null : "unknownCompany".equals(scenario) ? "UNKNOWN" : "COMPANY");
            form.set("DivisionName", ("noDivision".equals(scenario) || "noParents".equals(scenario)) ? null
                    : "unknownDivision".equals(scenario) || (!departmentTarget && "unknownTarget".equals(scenario)) ? "UNKNOWN" : "DIVISION");
            form.set("DepartmentName", "unknownTarget".equals(scenario) ? "UNKNOWN" : "DEPARTMENT");
            fixture.configureMembership(command, form, departmentTarget);
            execute(command);
            switch(scenario) {
                case "valid" -> assertFalse(command.hasExecutionErrors());
                case "noCompany" -> assertError(command, ExecutionErrors.UseOfDivisionNameRequiresCompanyName);
                case "noDivision", "noParents" -> assertError(command, departmentTarget ? ExecutionErrors.UseOfDepartmentNameRequiresDivisionName : ExecutionErrors.InvalidParameterCount);
                case "unknownCompany" -> assertError(command, ExecutionErrors.UnknownCompanyName, "UNKNOWN");
                case "unknownDivision" -> assertError(command, ExecutionErrors.UnknownDivisionName, "COMPANY", "UNKNOWN");
                case "unknownTarget" -> {
                    if(departmentTarget) {
                        assertError(command, ExecutionErrors.UnknownDepartmentName, "COMPANY", "DIVISION", "UNKNOWN");
                    } else {
                        assertError(command, ExecutionErrors.UnknownDivisionName, "COMPANY", "UNKNOWN");
                    }
                }
                default -> throw new IllegalArgumentException(scenario);
            }
            assertEquals("valid".equals(scenario) ? 1 : 0, fixture.relationships);
        }
    }

    @ParameterizedTest
    @CsvSource({
        ",,DIVISION_PARTY,", "COMPANY,DIVISION,,", ",DIVISION,,UseOfDivisionNameRequiresCompanyName",
        ",,,InvalidParameterCount", "UNKNOWN,DIVISION,,UnknownCompanyName", "COMPANY,UNKNOWN,,UnknownDivisionName",
        ",,UNKNOWN,UnknownPartyName", "COMPANY,,DIVISION_PARTY,InvalidParameterCount", "COMPANY,DIVISION,DIVISION_PARTY,InvalidParameterCount"
    })
    public void choicesPreserveDirectPartyIdentificationAndParentErrors(String companyName, String divisionName, String partyName, String errorKey) throws Exception {
        var fixture = new Fixture();
        var command = new GetDepartmentChoicesCommand();
        var form = PartyFormFactory.getGetDepartmentChoicesForm();
        form.setCompanyName(companyName);
        form.setDivisionName(divisionName);
        form.setPartyName(partyName);
        form.setDefaultDepartmentChoice("DEPARTMENT");
        form.setAllowNullChoice("true");
        inject(command, "form", form);
        inject(command, "companyLogic", fixture.companyLogic);
        inject(command, "divisionLogic", fixture.divisionLogic);
        inject(command, "partyControl", fixture.partyControl);
        execute(command);
        assertEquals(errorKey != null, command.hasExecutionErrors());
        if(errorKey != null) {
            assertEquals(errorKey, command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next().getKey());
        }
        assertEquals(errorKey == null ? 1 : 0, fixture.choices);
        if(companyName == null) {
            assertTrue(fixture.lookups.stream().noneMatch(lookup -> lookup.startsWith("company:")));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = { "omitted", "COMPANY", "UNKNOWN" })
    public void searchCompanyRemainsOptionalWithoutDefaulting(String companyName) throws Exception {
        var fixture = new Fixture();
        var command = new SearchLeavesCommand();
        var form = SearchFormFactory.getSearchLeavesForm();
        form.setCompanyName("omitted".equals(companyName) ? null : companyName);
        form.setLeaveTypeName("STOP");
        inject(command, "form", form);
        inject(command, "companyLogic", fixture.companyLogic);
        inject(command, "searchLogic", new SearchLogic() {
            @Override public SearchKind getSearchKindByName(ExecutionErrorAccumulator eea, String name) { return null; }
            @Override public SearchType getSearchTypeByName(ExecutionErrorAccumulator eea, SearchKind kind, String name) { return null; }
        });
        inject(command, "leaveLogic", new LeaveLogic() {
            @Override public LeaveType getLeaveTypeByName(ExecutionErrorAccumulator eea, String name) {
                fixture.lookups.add("leaveType:" + name);
                eea.addExecutionError(ExecutionErrors.UnknownLeaveTypeName.name(), name);
                return null;
            }
        });
        execute(command);
        switch(companyName) {
            case "omitted" -> {
                assertError(command, ExecutionErrors.UnknownLeaveTypeName, "STOP");
                assertEquals(List.of("leaveType:STOP"), fixture.lookups);
            }
            case "COMPANY" -> {
                assertError(command, ExecutionErrors.UnknownLeaveTypeName, "STOP");
                assertEquals(List.of("company:COMPANY", "leaveType:STOP"), fixture.lookups);
            }
            case "UNKNOWN" -> {
                assertError(command, ExecutionErrors.UnknownCompanyName, "UNKNOWN");
                assertEquals(List.of("company:UNKNOWN"), fixture.lookups);
            }
            default -> throw new IllegalArgumentException(companyName);
        }
    }

}
