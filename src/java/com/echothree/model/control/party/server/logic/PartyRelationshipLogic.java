// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.common.exception.DuplicatePartyRelationshipException;
import com.echothree.model.control.party.common.exception.NotAnEmployeeOfDepartmentsDivisionException;
import com.echothree.model.control.party.common.exception.NotAnEmployeeOfDivisionsCompanyException;
import com.echothree.model.control.party.common.exception.UnknownPartyRelationshipException;
import com.echothree.model.control.party.common.exception.UnknownPartyRelationshipTypeNameException;
import com.echothree.model.control.party.common.exception.UnknownRoleTypeNameException;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.user.server.logic.UserKeyLogic;
import com.echothree.model.control.user.server.logic.UserSessionLogic;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.party.server.entity.PartyDepartment;
import com.echothree.model.data.party.server.entity.PartyDivision;
import com.echothree.model.data.party.server.entity.PartyRelationship;
import com.echothree.model.data.party.server.entity.PartyRelationshipType;
import com.echothree.model.data.party.server.entity.RoleType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.remote.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class PartyRelationshipLogic
        extends BaseLogic {
    
    private PartyRelationshipLogic() {
        super();
    }
    
    private static class PartyRelationshipLogicHolder {
        static PartyRelationshipLogic instance = new PartyRelationshipLogic();
    }
    
    public static PartyRelationshipLogic getInstance() {
        return PartyRelationshipLogicHolder.instance;
    }

    public PartyRelationshipType getPartyRelationshipTypeByName(final ExecutionErrorAccumulator eea, final String partyRelationshipTypeName) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        PartyRelationshipType partyRelationshipType = partyControl.getPartyRelationshipTypeByName(partyRelationshipTypeName);

        if(partyRelationshipType == null) {
            handleExecutionError(UnknownPartyRelationshipTypeNameException.class, eea, ExecutionErrors.UnknownPartyRelationshipTypeName.name(), partyRelationshipTypeName);
        }

        return partyRelationshipType;
    }

    public RoleType getRoleTypeByName(final ExecutionErrorAccumulator eea, final String roleTypeName) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        RoleType roleType = partyControl.getRoleTypeByName(roleTypeName);

        if(roleType == null) {
            handleExecutionError(UnknownRoleTypeNameException.class, eea, ExecutionErrors.UnknownRoleTypeName.name(), roleTypeName);
        }

        return roleType;
    }

    public PartyRelationship createPartyRelationship(final ExecutionErrorAccumulator eea, final PartyRelationshipType partyRelationshipType,
            final Party fromParty, final RoleType fromRoleType, final Party toParty, final RoleType toRoleType, final BasePK createdBy) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        PartyRelationship partyRelationship = partyControl.getPartyRelationship(partyRelationshipType, fromParty, fromRoleType, toParty, toRoleType);

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
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        PartyRelationship partyRelationship = partyControl.getPartyRelationshipForUpdate(partyRelationshipType, fromParty, fromRoleType, toParty, toRoleType);

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
        boolean result = false;

        PartyLogic.getInstance().checkPartyType(eea, company, PartyConstants.PartyType_COMPANY);
        PartyLogic.getInstance().checkPartyType(eea, employee, PartyConstants.PartyType_EMPLOYEE);

        if(!hasExecutionErrors(eea)) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);

            result = partyControl.countPartyRelationships(getPartyRelationshipTypeByName(eea, PartyConstants.PartyRelationshipType_EMPLOYMENT),
                    company, getRoleTypeByName(eea, PartyConstants.RoleType_EMPLOYER),
                    employee, getRoleTypeByName(eea, PartyConstants.PartyType_EMPLOYEE)) == 1;
        }

        return result;
    }

    public PartyRelationship addEmployeeToCompany(final ExecutionErrorAccumulator eea, final Party companyParty, final Party employeeParty, final BasePK createdBy) {
        PartyRelationship partyRelationship = null;

        PartyLogic.getInstance().checkPartyType(eea, employeeParty, PartyConstants.PartyType_EMPLOYEE);
        PartyLogic.getInstance().checkPartyType(eea, companyParty, PartyConstants.PartyType_COMPANY);

        if(!hasExecutionErrors(eea)) {
            partyRelationship = createPartyRelationship(eea, getPartyRelationshipTypeByName(eea, PartyConstants.PartyRelationshipType_EMPLOYMENT),
                        companyParty, getRoleTypeByName(eea, PartyConstants.RoleType_EMPLOYER),
                        employeeParty, getRoleTypeByName(eea, PartyConstants.PartyType_EMPLOYEE), createdBy);
        }

        return partyRelationship;
    }

    public void removeEmployeeFromCompany(final ExecutionErrorAccumulator eea, final Party companyParty, final Party employeeParty, final BasePK deletedBy) {
        PartyLogic.getInstance().checkPartyType(eea, employeeParty, PartyConstants.PartyType_EMPLOYEE);
        PartyLogic.getInstance().checkPartyType(eea, companyParty, PartyConstants.PartyType_COMPANY);

        if(!hasExecutionErrors(eea)) {
            removeEmployeeFromCompanysDivisions(eea, companyParty, employeeParty, deletedBy);

            deletePartyRelationship(eea, getPartyRelationshipTypeByName(eea, PartyConstants.PartyRelationshipType_EMPLOYMENT),
                        companyParty, getRoleTypeByName(eea, PartyConstants.RoleType_EMPLOYER),
                        employeeParty, getRoleTypeByName(eea, PartyConstants.PartyType_EMPLOYEE), deletedBy);
        }
    }

    public boolean isEmployeeOfDivision(final ExecutionErrorAccumulator eea, final Party division, final Party employee) {
        boolean result = false;

        PartyLogic.getInstance().checkPartyType(eea, division, PartyConstants.PartyType_DIVISION);
        PartyLogic.getInstance().checkPartyType(eea, employee, PartyConstants.PartyType_EMPLOYEE);

        if(!hasExecutionErrors(eea)) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);

            result = partyControl.countPartyRelationships(getPartyRelationshipTypeByName(eea, PartyConstants.PartyRelationshipType_EMPLOYMENT),
                    division, getRoleTypeByName(eea, PartyConstants.RoleType_EMPLOYER),
                    employee, getRoleTypeByName(eea, PartyConstants.PartyType_EMPLOYEE)) == 1;
        }

        return result;
    }

    public PartyRelationship addEmployeeToDivision(final ExecutionErrorAccumulator eea, final Party divisionParty, final Party employeeParty, final BasePK createdBy) {
        PartyRelationship partyRelationship = null;

        PartyLogic.getInstance().checkPartyType(eea, employeeParty, PartyConstants.PartyType_EMPLOYEE);
        PartyLogic.getInstance().checkPartyType(eea, divisionParty, PartyConstants.PartyType_DIVISION);

        if(!hasExecutionErrors(eea)) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            PartyDivision partyDivision = partyControl.getPartyDivision(divisionParty);
            PartyCompany partyCompany = partyControl.getPartyCompany(partyDivision.getCompanyParty());
            Party companyParty = partyCompany.getParty();

            if(isEmployeeOfCompany(eea, companyParty, employeeParty)) {
                partyRelationship = createPartyRelationship(eea, getPartyRelationshipTypeByName(eea, PartyConstants.PartyRelationshipType_EMPLOYMENT),
                            divisionParty, getRoleTypeByName(eea, PartyConstants.RoleType_EMPLOYER),
                            employeeParty, getRoleTypeByName(eea, PartyConstants.PartyType_EMPLOYEE), createdBy);
            } else {
                handleExecutionError(NotAnEmployeeOfDivisionsCompanyException.class, eea, ExecutionErrors.NotAnEmployeeOfDivisionsCompany.name(),
                        partyCompany.getPartyCompanyName(), employeeParty.getLastDetail().getPartyName());
            }
        }

        return partyRelationship;
    }

    public void removeEmployeeFromCompanysDivisions(final ExecutionErrorAccumulator eea, final Party companyParty, final Party employeeParty, final BasePK deletedBy) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        List<PartyDivision> partyDivisions = partyControl.getDivisionsByCompany(companyParty);

        partyDivisions.stream().map((partyDivision) -> partyDivision.getParty()).filter((divisionParty) -> (isEmployeeOfDivision(eea, divisionParty, employeeParty))).forEach((divisionParty) -> {
            removeEmployeeFromDivisionsDepartments(eea, employeeParty, employeeParty, deletedBy);
            removeEmployeeFromDivision(eea, divisionParty, employeeParty, deletedBy);
        });
    }

    public void removeEmployeeFromDivision(final ExecutionErrorAccumulator eea, final Party divisionParty, final Party employeeParty, final BasePK deletedBy) {
        PartyLogic.getInstance().checkPartyType(eea, employeeParty, PartyConstants.PartyType_EMPLOYEE);
        PartyLogic.getInstance().checkPartyType(eea, divisionParty, PartyConstants.PartyType_DIVISION);

        if(!hasExecutionErrors(eea)) {
            removeEmployeeFromDivisionsDepartments(eea, divisionParty, employeeParty, deletedBy);

            deletePartyRelationship(eea, getPartyRelationshipTypeByName(eea, PartyConstants.PartyRelationshipType_EMPLOYMENT),
                        divisionParty, getRoleTypeByName(eea, PartyConstants.RoleType_EMPLOYER),
                        employeeParty, getRoleTypeByName(eea, PartyConstants.PartyType_EMPLOYEE), deletedBy);
        }
    }

    public boolean isEmployeeOfDepartment(final ExecutionErrorAccumulator eea, final Party department, final Party employee) {
        boolean result = false;

        PartyLogic.getInstance().checkPartyType(eea, department, PartyConstants.PartyType_DEPARTMENT);
        PartyLogic.getInstance().checkPartyType(eea, employee, PartyConstants.PartyType_EMPLOYEE);

        if(!hasExecutionErrors(eea)) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);

            result = partyControl.countPartyRelationships(getPartyRelationshipTypeByName(eea, PartyConstants.PartyRelationshipType_EMPLOYMENT),
                    department, getRoleTypeByName(eea, PartyConstants.RoleType_EMPLOYER),
                    employee, getRoleTypeByName(eea, PartyConstants.PartyType_EMPLOYEE)) == 1;
        }

        return result;
    }

    public PartyRelationship addEmployeeToDepartment(final ExecutionErrorAccumulator eea, final Party departmentParty, final Party employeeParty, final BasePK createdBy) {
        PartyRelationship partyRelationship = null;

        PartyLogic.getInstance().checkPartyType(eea, employeeParty, PartyConstants.PartyType_EMPLOYEE);
        PartyLogic.getInstance().checkPartyType(eea, departmentParty, PartyConstants.PartyType_DEPARTMENT);

        if(!hasExecutionErrors(eea)) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            PartyDepartment partyDepartment = partyControl.getPartyDepartment(departmentParty);
            PartyDivision partyDivision = partyControl.getPartyDivision(partyDepartment.getDivisionParty());
            PartyCompany partyCompany = partyControl.getPartyCompany(partyDivision.getCompanyParty());
            Party divisionParty = partyDivision.getParty();

            if(isEmployeeOfDivision(eea, divisionParty, employeeParty)) {
                partyRelationship = createPartyRelationship(eea, getPartyRelationshipTypeByName(eea, PartyConstants.PartyRelationshipType_EMPLOYMENT),
                            departmentParty, getRoleTypeByName(eea, PartyConstants.RoleType_EMPLOYER),
                            employeeParty, getRoleTypeByName(eea, PartyConstants.PartyType_EMPLOYEE), createdBy);
            } else {
                handleExecutionError(NotAnEmployeeOfDepartmentsDivisionException.class, eea, ExecutionErrors.NotAnEmployeeOfDepartmentsDivision.name(),
                        partyCompany.getPartyCompanyName(), partyDivision.getPartyDivisionName(), employeeParty.getLastDetail().getPartyName());
            }
        }

        return partyRelationship;
    }

    public void removeEmployeeFromDivisionsDepartments(final ExecutionErrorAccumulator eea, final Party divisionParty, final Party employeeParty, final BasePK deletedBy) {
        PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        List<PartyDepartment> partyDepartments = partyControl.getDepartmentsByDivision(divisionParty);

        partyDepartments.stream().map((partyDepartment) -> partyDepartment.getParty()).filter((departmentParty) -> (isEmployeeOfDepartment(eea, departmentParty, employeeParty))).forEach((departmentParty) -> {
            removeEmployeeFromDepartment(eea, departmentParty, employeeParty, deletedBy);
        });
    }

    public void removeEmployeeFromDepartment(final ExecutionErrorAccumulator eea, final Party departmentParty, final Party employeeParty, final BasePK deletedBy) {
        PartyLogic.getInstance().checkPartyType(eea, employeeParty, PartyConstants.PartyType_EMPLOYEE);
        PartyLogic.getInstance().checkPartyType(eea, departmentParty, PartyConstants.PartyType_DEPARTMENT);

        if(!hasExecutionErrors(eea)) {
            deletePartyRelationship(eea, getPartyRelationshipTypeByName(eea, PartyConstants.PartyRelationshipType_EMPLOYMENT),
                        departmentParty, getRoleTypeByName(eea, PartyConstants.RoleType_EMPLOYER),
                        employeeParty, getRoleTypeByName(eea, PartyConstants.PartyType_EMPLOYEE), deletedBy);
        }
    }

}
