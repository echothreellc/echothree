// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.employee.server;

import com.echothree.control.user.employee.common.EmployeeRemote;
import com.echothree.control.user.employee.common.form.*;
import com.echothree.control.user.employee.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class EmployeeBean
        extends EmployeeFormsImpl
        implements EmployeeRemote, EmployeeLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "EmployeeBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Responsibility Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createResponsibilityType(UserVisitPK userVisitPK, CreateResponsibilityTypeForm form) {
        return new CreateResponsibilityTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getResponsibilityTypes(UserVisitPK userVisitPK, GetResponsibilityTypesForm form) {
        return new GetResponsibilityTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getResponsibilityType(UserVisitPK userVisitPK, GetResponsibilityTypeForm form) {
        return new GetResponsibilityTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getResponsibilityTypeChoices(UserVisitPK userVisitPK, GetResponsibilityTypeChoicesForm form) {
        return new GetResponsibilityTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultResponsibilityType(UserVisitPK userVisitPK, SetDefaultResponsibilityTypeForm form) {
        return new SetDefaultResponsibilityTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editResponsibilityType(UserVisitPK userVisitPK, EditResponsibilityTypeForm form) {
        return new EditResponsibilityTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteResponsibilityType(UserVisitPK userVisitPK, DeleteResponsibilityTypeForm form) {
        return new DeleteResponsibilityTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Responsibility Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createResponsibilityTypeDescription(UserVisitPK userVisitPK, CreateResponsibilityTypeDescriptionForm form) {
        return new CreateResponsibilityTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getResponsibilityTypeDescriptions(UserVisitPK userVisitPK, GetResponsibilityTypeDescriptionsForm form) {
        return new GetResponsibilityTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editResponsibilityTypeDescription(UserVisitPK userVisitPK, EditResponsibilityTypeDescriptionForm form) {
        return new EditResponsibilityTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteResponsibilityTypeDescription(UserVisitPK userVisitPK, DeleteResponsibilityTypeDescriptionForm form) {
        return new DeleteResponsibilityTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Skill Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSkillType(UserVisitPK userVisitPK, CreateSkillTypeForm form) {
        return new CreateSkillTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSkillTypes(UserVisitPK userVisitPK, GetSkillTypesForm form) {
        return new GetSkillTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSkillType(UserVisitPK userVisitPK, GetSkillTypeForm form) {
        return new GetSkillTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSkillTypeChoices(UserVisitPK userVisitPK, GetSkillTypeChoicesForm form) {
        return new GetSkillTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultSkillType(UserVisitPK userVisitPK, SetDefaultSkillTypeForm form) {
        return new SetDefaultSkillTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSkillType(UserVisitPK userVisitPK, EditSkillTypeForm form) {
        return new EditSkillTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSkillType(UserVisitPK userVisitPK, DeleteSkillTypeForm form) {
        return new DeleteSkillTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Skill Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSkillTypeDescription(UserVisitPK userVisitPK, CreateSkillTypeDescriptionForm form) {
        return new CreateSkillTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSkillTypeDescriptions(UserVisitPK userVisitPK, GetSkillTypeDescriptionsForm form) {
        return new GetSkillTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSkillTypeDescription(UserVisitPK userVisitPK, EditSkillTypeDescriptionForm form) {
        return new EditSkillTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSkillTypeDescription(UserVisitPK userVisitPK, DeleteSkillTypeDescriptionForm form) {
        return new DeleteSkillTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Leave Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createLeaveType(UserVisitPK userVisitPK, CreateLeaveTypeForm form) {
        return new CreateLeaveTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLeaveTypes(UserVisitPK userVisitPK, GetLeaveTypesForm form) {
        return new GetLeaveTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLeaveType(UserVisitPK userVisitPK, GetLeaveTypeForm form) {
        return new GetLeaveTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLeaveTypeChoices(UserVisitPK userVisitPK, GetLeaveTypeChoicesForm form) {
        return new GetLeaveTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultLeaveType(UserVisitPK userVisitPK, SetDefaultLeaveTypeForm form) {
        return new SetDefaultLeaveTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editLeaveType(UserVisitPK userVisitPK, EditLeaveTypeForm form) {
        return new EditLeaveTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteLeaveType(UserVisitPK userVisitPK, DeleteLeaveTypeForm form) {
        return new DeleteLeaveTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Leave Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createLeaveTypeDescription(UserVisitPK userVisitPK, CreateLeaveTypeDescriptionForm form) {
        return new CreateLeaveTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLeaveTypeDescriptions(UserVisitPK userVisitPK, GetLeaveTypeDescriptionsForm form) {
        return new GetLeaveTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLeaveTypeDescription(UserVisitPK userVisitPK, GetLeaveTypeDescriptionForm form) {
        return new GetLeaveTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editLeaveTypeDescription(UserVisitPK userVisitPK, EditLeaveTypeDescriptionForm form) {
        return new EditLeaveTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteLeaveTypeDescription(UserVisitPK userVisitPK, DeleteLeaveTypeDescriptionForm form) {
        return new DeleteLeaveTypeDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Leave Reasons
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createLeaveReason(UserVisitPK userVisitPK, CreateLeaveReasonForm form) {
        return new CreateLeaveReasonCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLeaveReasons(UserVisitPK userVisitPK, GetLeaveReasonsForm form) {
        return new GetLeaveReasonsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLeaveReason(UserVisitPK userVisitPK, GetLeaveReasonForm form) {
        return new GetLeaveReasonCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLeaveReasonChoices(UserVisitPK userVisitPK, GetLeaveReasonChoicesForm form) {
        return new GetLeaveReasonChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultLeaveReason(UserVisitPK userVisitPK, SetDefaultLeaveReasonForm form) {
        return new SetDefaultLeaveReasonCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editLeaveReason(UserVisitPK userVisitPK, EditLeaveReasonForm form) {
        return new EditLeaveReasonCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteLeaveReason(UserVisitPK userVisitPK, DeleteLeaveReasonForm form) {
        return new DeleteLeaveReasonCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Leave Reason Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createLeaveReasonDescription(UserVisitPK userVisitPK, CreateLeaveReasonDescriptionForm form) {
        return new CreateLeaveReasonDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLeaveReasonDescription(UserVisitPK userVisitPK, GetLeaveReasonDescriptionForm form) {
        return new GetLeaveReasonDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLeaveReasonDescriptions(UserVisitPK userVisitPK, GetLeaveReasonDescriptionsForm form) {
        return new GetLeaveReasonDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editLeaveReasonDescription(UserVisitPK userVisitPK, EditLeaveReasonDescriptionForm form) {
        return new EditLeaveReasonDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteLeaveReasonDescription(UserVisitPK userVisitPK, DeleteLeaveReasonDescriptionForm form) {
        return new DeleteLeaveReasonDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Leaves
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createLeave(UserVisitPK userVisitPK, CreateLeaveForm form) {
        return new CreateLeaveCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLeaveStatusChoices(UserVisitPK userVisitPK, GetLeaveStatusChoicesForm form) {
        return new GetLeaveStatusChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setLeaveStatus(UserVisitPK userVisitPK, SetLeaveStatusForm form) {
        return new SetLeaveStatusCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLeaves(UserVisitPK userVisitPK, GetLeavesForm form) {
        return new GetLeavesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLeave(UserVisitPK userVisitPK, GetLeaveForm form) {
        return new GetLeaveCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editLeave(UserVisitPK userVisitPK, EditLeaveForm form) {
        return new EditLeaveCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteLeave(UserVisitPK userVisitPK, DeleteLeaveForm form) {
        return new DeleteLeaveCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Termination Reasons
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createTerminationReason(UserVisitPK userVisitPK, CreateTerminationReasonForm form) {
        return new CreateTerminationReasonCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getTerminationReasons(UserVisitPK userVisitPK, GetTerminationReasonsForm form) {
        return new GetTerminationReasonsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getTerminationReason(UserVisitPK userVisitPK, GetTerminationReasonForm form) {
        return new GetTerminationReasonCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getTerminationReasonChoices(UserVisitPK userVisitPK, GetTerminationReasonChoicesForm form) {
        return new GetTerminationReasonChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultTerminationReason(UserVisitPK userVisitPK, SetDefaultTerminationReasonForm form) {
        return new SetDefaultTerminationReasonCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editTerminationReason(UserVisitPK userVisitPK, EditTerminationReasonForm form) {
        return new EditTerminationReasonCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteTerminationReason(UserVisitPK userVisitPK, DeleteTerminationReasonForm form) {
        return new DeleteTerminationReasonCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Termination Reason Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createTerminationReasonDescription(UserVisitPK userVisitPK, CreateTerminationReasonDescriptionForm form) {
        return new CreateTerminationReasonDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getTerminationReasonDescriptions(UserVisitPK userVisitPK, GetTerminationReasonDescriptionsForm form) {
        return new GetTerminationReasonDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editTerminationReasonDescription(UserVisitPK userVisitPK, EditTerminationReasonDescriptionForm form) {
        return new EditTerminationReasonDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteTerminationReasonDescription(UserVisitPK userVisitPK, DeleteTerminationReasonDescriptionForm form) {
        return new DeleteTerminationReasonDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Termination Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTerminationType(UserVisitPK userVisitPK, CreateTerminationTypeForm form) {
        return new CreateTerminationTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTerminationTypes(UserVisitPK userVisitPK, GetTerminationTypesForm form) {
        return new GetTerminationTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTerminationType(UserVisitPK userVisitPK, GetTerminationTypeForm form) {
        return new GetTerminationTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTerminationTypeChoices(UserVisitPK userVisitPK, GetTerminationTypeChoicesForm form) {
        return new GetTerminationTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultTerminationType(UserVisitPK userVisitPK, SetDefaultTerminationTypeForm form) {
        return new SetDefaultTerminationTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTerminationType(UserVisitPK userVisitPK, EditTerminationTypeForm form) {
        return new EditTerminationTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTerminationType(UserVisitPK userVisitPK, DeleteTerminationTypeForm form) {
        return new DeleteTerminationTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Termination Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTerminationTypeDescription(UserVisitPK userVisitPK, CreateTerminationTypeDescriptionForm form) {
        return new CreateTerminationTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTerminationTypeDescriptions(UserVisitPK userVisitPK, GetTerminationTypeDescriptionsForm form) {
        return new GetTerminationTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTerminationTypeDescription(UserVisitPK userVisitPK, EditTerminationTypeDescriptionForm form) {
        return new EditTerminationTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTerminationTypeDescription(UserVisitPK userVisitPK, DeleteTerminationTypeDescriptionForm form) {
        return new DeleteTerminationTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Employments
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createEmployment(UserVisitPK userVisitPK, CreateEmploymentForm form) {
        return new CreateEmploymentCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEmployments(UserVisitPK userVisitPK, GetEmploymentsForm form) {
        return new GetEmploymentsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEmployment(UserVisitPK userVisitPK, GetEmploymentForm form) {
        return new GetEmploymentCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEmployment(UserVisitPK userVisitPK, EditEmploymentForm form) {
        return new EditEmploymentCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEmployment(UserVisitPK userVisitPK, DeleteEmploymentForm form) {
        return new DeleteEmploymentCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Party Responsibilities
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyResponsibility(UserVisitPK userVisitPK, CreatePartyResponsibilityForm form) {
        return new CreatePartyResponsibilityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartyResponsibilities(UserVisitPK userVisitPK, GetPartyResponsibilitiesForm form) {
        return new GetPartyResponsibilitiesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartyResponsibility(UserVisitPK userVisitPK, DeletePartyResponsibilityForm form) {
        return new DeletePartyResponsibilityCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Skills
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartySkill(UserVisitPK userVisitPK, CreatePartySkillForm form) {
        return new CreatePartySkillCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartySkills(UserVisitPK userVisitPK, GetPartySkillsForm form) {
        return new GetPartySkillsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartySkill(UserVisitPK userVisitPK, DeletePartySkillForm form) {
        return new DeletePartySkillCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Employee Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEmployeeType(UserVisitPK userVisitPK, CreateEmployeeTypeForm form) {
        return new CreateEmployeeTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEmployeeTypeChoices(UserVisitPK userVisitPK, GetEmployeeTypeChoicesForm form) {
        return new GetEmployeeTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEmployeeType(UserVisitPK userVisitPK, GetEmployeeTypeForm form) {
        return new GetEmployeeTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEmployeeTypes(UserVisitPK userVisitPK, GetEmployeeTypesForm form) {
        return new GetEmployeeTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultEmployeeType(UserVisitPK userVisitPK, SetDefaultEmployeeTypeForm form) {
        return new SetDefaultEmployeeTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEmployeeType(UserVisitPK userVisitPK, EditEmployeeTypeForm form) {
        return new EditEmployeeTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEmployeeType(UserVisitPK userVisitPK, DeleteEmployeeTypeForm form) {
        return new DeleteEmployeeTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Employee Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEmployeeTypeDescription(UserVisitPK userVisitPK, CreateEmployeeTypeDescriptionForm form) {
        return new CreateEmployeeTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEmployeeTypeDescriptions(UserVisitPK userVisitPK, GetEmployeeTypeDescriptionsForm form) {
        return new GetEmployeeTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEmployeeTypeDescription(UserVisitPK userVisitPK, EditEmployeeTypeDescriptionForm form) {
        return new EditEmployeeTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEmployeeTypeDescription(UserVisitPK userVisitPK, DeleteEmployeeTypeDescriptionForm form) {
        return new DeleteEmployeeTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Employees
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getEmployee(UserVisitPK userVisitPK, GetEmployeeForm form) {
        return new GetEmployeeCommand(userVisitPK, form).run();
    }
    
}
