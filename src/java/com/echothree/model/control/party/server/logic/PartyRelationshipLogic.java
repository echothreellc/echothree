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

import com.echothree.model.control.party.common.PartyRelationshipTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.common.RoleTypes;
import com.echothree.model.control.party.common.exception.DuplicatePartyRelationshipException;
import com.echothree.model.control.party.common.exception.NotAnEmployeeOfDepartmentsDivisionException;
import com.echothree.model.control.party.common.exception.NotAnEmployeeOfDivisionsCompanyException;
import com.echothree.model.control.party.common.exception.UnknownPartyRelationshipException;
import com.echothree.model.control.party.common.exception.UnknownPartyRelationshipTypeNameException;
import com.echothree.model.control.party.common.exception.UnknownRoleTypeNameException;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.logic.UserKeyLogic;
import com.echothree.model.control.user.server.logic.UserSessionLogic;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import com.echothree.model.data.party.server.entity.PartyRelationshipType;
import com.echothree.model.data.party.server.entity.RoleType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PartyRelationshipLogic
        extends BaseLogic {

    protected PartyRelationshipLogic() {
        super();
    }

    public static PartyRelationshipLogic getInstance() {
        return CDI.current().select(PartyRelationshipLogic.class).get();
    }

    public PartyRelationshipType getPartyRelationshipTypeByName(final ExecutionErrorAccumulator eea, final String partyRelationshipTypeName) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyRelationshipType = partyControl.getPartyRelationshipTypeByName(partyRelationshipTypeName);

        if(partyRelationshipType == null) {
            handleExecutionError(UnknownPartyRelationshipTypeNameException.class, eea, ExecutionErrors.UnknownPartyRelationshipTypeName.name(), partyRelationshipTypeName);
        }

        return partyRelationshipType;
    }

    public RoleType getRoleTypeByName(final ExecutionErrorAccumulator eea, final String roleTypeName) {
        var partyControl = Session.getModelController(PartyControl.class);
        var roleType = partyControl.getRoleTypeByName(roleTypeName);

        if(roleType == null) {
            handleExecutionError(UnknownRoleTypeNameException.class, eea, ExecutionErrors.UnknownRoleTypeName.name(), roleTypeName);
        }

        return roleType;
    }

    public PartyRelationship createPartyRelationship(final ExecutionErrorAccumulator eea, final PartyRelationshipType partyRelationshipType,
            final Party fromParty, final RoleType fromRoleType, final Party toParty, final RoleType toRoleType, final BasePK createdBy) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyRelationship = partyControl.getPartyRelationship(partyRelationshipType, fromParty, fromRoleType, toParty, toRoleType);

        if(partyRelationship == null) {
            partyControl.createPartyRelationship(partyRelationshipType, fromParty, fromRoleType, toParty, toRoleType, createdBy);
        } else {
            handleExecutionError(DuplicatePartyRelationshipException.class, eea, ExecutionErrors.DuplicatePartyRelationship.name(),
                    partyRelationshipType.getPartyRelationshipTypeName(),
                    fromParty.getLastDetail().getPartyName(), fromRoleType.getRoleTypeName(),
                    toParty.getLastDetail().getPartyName(), toRoleType.getRoleTypeName());
        }

        return partyRelationship;
    }

    public void deletePartyRelationship(final ExecutionErrorAccumulator eea, final PartyRelationshipType partyRelationshipType, final Party fromParty,
            final RoleType fromRoleType, final Party toParty, final RoleType toRoleType, final BasePK deletedBy) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyRelationship = partyControl.getPartyRelationshipForUpdate(partyRelationshipType, fromParty, fromRoleType, toParty, toRoleType);

        if(partyRelationship == null) {
            handleExecutionError(UnknownPartyRelationshipException.class, eea, ExecutionErrors.UnknownPartyRelationship.name(),
                    partyRelationshipType.getPartyRelationshipTypeName(),
                    fromParty.getLastDetail().getPartyName(), fromRoleType.getRoleTypeName(),
                    toParty.getLastDetail().getPartyName(), toRoleType.getRoleTypeName());
        } else {
            UserKeyLogic.getInstance().clearUserKeysByPartyRelationship(partyRelationship);
            UserSessionLogic.getInstance().deleteUserSessionsByPartyRelationship(partyRelationship);
            partyControl.deletePartyRelationship(partyRelationship, deletedBy);
        }
    }

    public boolean isEmployeeOfCompany(final ExecutionErrorAccumulator eea, final Party company, final Party employee) {
        var result = false;

        PartyLogic.getInstance().checkPartyType(eea, company, PartyTypes.COMPANY.name());
        PartyLogic.getInstance().checkPartyType(eea, employee, PartyTypes.EMPLOYEE.name());

        if(!hasExecutionErrors(eea)) {
            var partyControl = Session.getModelController(PartyControl.class);

            result = partyControl.countPartyRelationships(getPartyRelationshipTypeByName(eea, PartyRelationshipTypes.EMPLOYMENT.name()),
                    company, getRoleTypeByName(eea, RoleTypes.EMPLOYER.name()),
                    employee, getRoleTypeByName(eea, PartyTypes.EMPLOYEE.name())) == 1;
        }

        return result;
    }

    public PartyRelationship addEmployeeToCompany(final ExecutionErrorAccumulator eea, final Party companyParty, final Party employeeParty, final BasePK createdBy) {
        PartyRelationship partyRelationship = null;

        PartyLogic.getInstance().checkPartyType(eea, employeeParty, PartyTypes.EMPLOYEE.name());
        PartyLogic.getInstance().checkPartyType(eea, companyParty, PartyTypes.COMPANY.name());

        if(!hasExecutionErrors(eea)) {
            partyRelationship = createPartyRelationship(eea, getPartyRelationshipTypeByName(eea, PartyRelationshipTypes.EMPLOYMENT.name()),
                        companyParty, getRoleTypeByName(eea, RoleTypes.EMPLOYER.name()),
                        employeeParty, getRoleTypeByName(eea, PartyTypes.EMPLOYEE.name()), createdBy);
        }

        return partyRelationship;
    }

    public void removeEmployeeFromCompany(final ExecutionErrorAccumulator eea, final Party companyParty, final Party employeeParty, final BasePK deletedBy) {
        PartyLogic.getInstance().checkPartyType(eea, employeeParty, PartyTypes.EMPLOYEE.name());
        PartyLogic.getInstance().checkPartyType(eea, companyParty, PartyTypes.COMPANY.name());

        if(!hasExecutionErrors(eea)) {
            removeEmployeeFromCompanysDivisions(eea, companyParty, employeeParty, deletedBy);

            deletePartyRelationship(eea, getPartyRelationshipTypeByName(eea, PartyRelationshipTypes.EMPLOYMENT.name()),
                        companyParty, getRoleTypeByName(eea, RoleTypes.EMPLOYER.name()),
                        employeeParty, getRoleTypeByName(eea, PartyTypes.EMPLOYEE.name()), deletedBy);
        }
    }

    public boolean isEmployeeOfDivision(final ExecutionErrorAccumulator eea, final Party division, final Party employee) {
        var result = false;

        PartyLogic.getInstance().checkPartyType(eea, division, PartyTypes.DIVISION.name());
        PartyLogic.getInstance().checkPartyType(eea, employee, PartyTypes.EMPLOYEE.name());

        if(!hasExecutionErrors(eea)) {
            var partyControl = Session.getModelController(PartyControl.class);

            result = partyControl.countPartyRelationships(getPartyRelationshipTypeByName(eea, PartyRelationshipTypes.EMPLOYMENT.name()),
                    division, getRoleTypeByName(eea, RoleTypes.EMPLOYER.name()),
                    employee, getRoleTypeByName(eea, PartyTypes.EMPLOYEE.name())) == 1;
        }

        return result;
    }

    public PartyRelationship addEmployeeToDivision(final ExecutionErrorAccumulator eea, final Party divisionParty, final Party employeeParty, final BasePK createdBy) {
        PartyRelationship partyRelationship = null;

        PartyLogic.getInstance().checkPartyType(eea, employeeParty, PartyTypes.EMPLOYEE.name());
        PartyLogic.getInstance().checkPartyType(eea, divisionParty, PartyTypes.DIVISION.name());

        if(!hasExecutionErrors(eea)) {
            var partyControl = Session.getModelController(PartyControl.class);
            var partyDivision = partyControl.getPartyDivision(divisionParty);
            var partyCompany = partyControl.getPartyCompany(partyDivision.getCompanyParty());
            var companyParty = partyCompany.getParty();

            if(isEmployeeOfCompany(eea, companyParty, employeeParty)) {
                partyRelationship = createPartyRelationship(eea, getPartyRelationshipTypeByName(eea, PartyRelationshipTypes.EMPLOYMENT.name()),
                            divisionParty, getRoleTypeByName(eea, RoleTypes.EMPLOYER.name()),
                            employeeParty, getRoleTypeByName(eea, PartyTypes.EMPLOYEE.name()), createdBy);
            } else {
                handleExecutionError(NotAnEmployeeOfDivisionsCompanyException.class, eea, ExecutionErrors.NotAnEmployeeOfDivisionsCompany.name(),
                        partyCompany.getPartyCompanyName(), employeeParty.getLastDetail().getPartyName());
            }
        }

        return partyRelationship;
    }

    public void removeEmployeeFromCompanysDivisions(final ExecutionErrorAccumulator eea, final Party companyParty, final Party employeeParty, final BasePK deletedBy) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyDivisions = partyControl.getDivisionsByCompany(companyParty);

        partyDivisions.stream().map((partyDivision) -> partyDivision.getParty()).filter((divisionParty) -> isEmployeeOfDivision(eea, divisionParty, employeeParty)).forEach((divisionParty) -> {
            removeEmployeeFromDivisionsDepartments(eea, employeeParty, employeeParty, deletedBy);
            removeEmployeeFromDivision(eea, divisionParty, employeeParty, deletedBy);
        });
    }

    public void removeEmployeeFromDivision(final ExecutionErrorAccumulator eea, final Party divisionParty, final Party employeeParty, final BasePK deletedBy) {
        PartyLogic.getInstance().checkPartyType(eea, employeeParty, PartyTypes.EMPLOYEE.name());
        PartyLogic.getInstance().checkPartyType(eea, divisionParty, PartyTypes.DIVISION.name());

        if(!hasExecutionErrors(eea)) {
            removeEmployeeFromDivisionsDepartments(eea, divisionParty, employeeParty, deletedBy);

            deletePartyRelationship(eea, getPartyRelationshipTypeByName(eea, PartyRelationshipTypes.EMPLOYMENT.name()),
                        divisionParty, getRoleTypeByName(eea, RoleTypes.EMPLOYER.name()),
                        employeeParty, getRoleTypeByName(eea, PartyTypes.EMPLOYEE.name()), deletedBy);
        }
    }

    public boolean isEmployeeOfDepartment(final ExecutionErrorAccumulator eea, final Party department, final Party employee) {
        var result = false;

        PartyLogic.getInstance().checkPartyType(eea, department, PartyTypes.DEPARTMENT.name());
        PartyLogic.getInstance().checkPartyType(eea, employee, PartyTypes.EMPLOYEE.name());

        if(!hasExecutionErrors(eea)) {
            var partyControl = Session.getModelController(PartyControl.class);

            result = partyControl.countPartyRelationships(getPartyRelationshipTypeByName(eea, PartyRelationshipTypes.EMPLOYMENT.name()),
                    department, getRoleTypeByName(eea, RoleTypes.EMPLOYER.name()),
                    employee, getRoleTypeByName(eea, PartyTypes.EMPLOYEE.name())) == 1;
        }

        return result;
    }

    public PartyRelationship addEmployeeToDepartment(final ExecutionErrorAccumulator eea, final Party departmentParty, final Party employeeParty, final BasePK createdBy) {
        PartyRelationship partyRelationship = null;

        PartyLogic.getInstance().checkPartyType(eea, employeeParty, PartyTypes.EMPLOYEE.name());
        PartyLogic.getInstance().checkPartyType(eea, departmentParty, PartyTypes.DEPARTMENT.name());

        if(!hasExecutionErrors(eea)) {
            var partyControl = Session.getModelController(PartyControl.class);
            var partyDepartment = partyControl.getPartyDepartment(departmentParty);
            var partyDivision = partyControl.getPartyDivision(partyDepartment.getDivisionParty());
            var partyCompany = partyControl.getPartyCompany(partyDivision.getCompanyParty());
            var divisionParty = partyDivision.getParty();

            if(isEmployeeOfDivision(eea, divisionParty, employeeParty)) {
                partyRelationship = createPartyRelationship(eea, getPartyRelationshipTypeByName(eea, PartyRelationshipTypes.EMPLOYMENT.name()),
                            departmentParty, getRoleTypeByName(eea, RoleTypes.EMPLOYER.name()),
                            employeeParty, getRoleTypeByName(eea, PartyTypes.EMPLOYEE.name()), createdBy);
            } else {
                handleExecutionError(NotAnEmployeeOfDepartmentsDivisionException.class, eea, ExecutionErrors.NotAnEmployeeOfDepartmentsDivision.name(),
                        partyCompany.getPartyCompanyName(), partyDivision.getPartyDivisionName(), employeeParty.getLastDetail().getPartyName());
            }
        }

        return partyRelationship;
    }

    public void removeEmployeeFromDivisionsDepartments(final ExecutionErrorAccumulator eea, final Party divisionParty, final Party employeeParty, final BasePK deletedBy) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyDepartments = partyControl.getDepartmentsByDivision(divisionParty);

        partyDepartments.stream().map((partyDepartment) -> partyDepartment.getParty()).filter((departmentParty) -> isEmployeeOfDepartment(eea, departmentParty, employeeParty)).forEach((departmentParty) -> {
            removeEmployeeFromDepartment(eea, departmentParty, employeeParty, deletedBy);
        });
    }

    public void removeEmployeeFromDepartment(final ExecutionErrorAccumulator eea, final Party departmentParty, final Party employeeParty, final BasePK deletedBy) {
        PartyLogic.getInstance().checkPartyType(eea, employeeParty, PartyTypes.EMPLOYEE.name());
        PartyLogic.getInstance().checkPartyType(eea, departmentParty, PartyTypes.DEPARTMENT.name());

        if(!hasExecutionErrors(eea)) {
            deletePartyRelationship(eea, getPartyRelationshipTypeByName(eea, PartyRelationshipTypes.EMPLOYMENT.name()),
                        departmentParty, getRoleTypeByName(eea, RoleTypes.EMPLOYER.name()),
                        employeeParty, getRoleTypeByName(eea, PartyTypes.EMPLOYEE.name()), deletedBy);
        }
    }

}
