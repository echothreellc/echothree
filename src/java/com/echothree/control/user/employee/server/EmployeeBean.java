// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
        return new CreateResponsibilityTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getResponsibilityTypes(UserVisitPK userVisitPK, GetResponsibilityTypesForm form) {
        return new GetResponsibilityTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getResponsibilityType(UserVisitPK userVisitPK, GetResponsibilityTypeForm form) {
        return new GetResponsibilityTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getResponsibilityTypeChoices(UserVisitPK userVisitPK, GetResponsibilityTypeChoicesForm form) {
        return new GetResponsibilityTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultResponsibilityType(UserVisitPK userVisitPK, SetDefaultResponsibilityTypeForm form) {
        return new SetDefaultResponsibilityTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editResponsibilityType(UserVisitPK userVisitPK, EditResponsibilityTypeForm form) {
        return new EditResponsibilityTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteResponsibilityType(UserVisitPK userVisitPK, DeleteResponsibilityTypeForm form) {
        return new DeleteResponsibilityTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Responsibility Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createResponsibilityTypeDescription(UserVisitPK userVisitPK, CreateResponsibilityTypeDescriptionForm form) {
        return new CreateResponsibilityTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getResponsibilityTypeDescriptions(UserVisitPK userVisitPK, GetResponsibilityTypeDescriptionsForm form) {
        return new GetResponsibilityTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editResponsibilityTypeDescription(UserVisitPK userVisitPK, EditResponsibilityTypeDescriptionForm form) {
        return new EditResponsibilityTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteResponsibilityTypeDescription(UserVisitPK userVisitPK, DeleteResponsibilityTypeDescriptionForm form) {
        return new DeleteResponsibilityTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Skill Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSkillType(UserVisitPK userVisitPK, CreateSkillTypeForm form) {
        return new CreateSkillTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSkillTypes(UserVisitPK userVisitPK, GetSkillTypesForm form) {
        return new GetSkillTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSkillType(UserVisitPK userVisitPK, GetSkillTypeForm form) {
        return new GetSkillTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSkillTypeChoices(UserVisitPK userVisitPK, GetSkillTypeChoicesForm form) {
        return new GetSkillTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSkillType(UserVisitPK userVisitPK, SetDefaultSkillTypeForm form) {
        return new SetDefaultSkillTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSkillType(UserVisitPK userVisitPK, EditSkillTypeForm form) {
        return new EditSkillTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSkillType(UserVisitPK userVisitPK, DeleteSkillTypeForm form) {
        return new DeleteSkillTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Skill Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSkillTypeDescription(UserVisitPK userVisitPK, CreateSkillTypeDescriptionForm form) {
        return new CreateSkillTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSkillTypeDescriptions(UserVisitPK userVisitPK, GetSkillTypeDescriptionsForm form) {
        return new GetSkillTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSkillTypeDescription(UserVisitPK userVisitPK, EditSkillTypeDescriptionForm form) {
        return new EditSkillTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSkillTypeDescription(UserVisitPK userVisitPK, DeleteSkillTypeDescriptionForm form) {
        return new DeleteSkillTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Leave Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createLeaveType(UserVisitPK userVisitPK, CreateLeaveTypeForm form) {
        return new CreateLeaveTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLeaveTypes(UserVisitPK userVisitPK, GetLeaveTypesForm form) {
        return new GetLeaveTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLeaveType(UserVisitPK userVisitPK, GetLeaveTypeForm form) {
        return new GetLeaveTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLeaveTypeChoices(UserVisitPK userVisitPK, GetLeaveTypeChoicesForm form) {
        return new GetLeaveTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultLeaveType(UserVisitPK userVisitPK, SetDefaultLeaveTypeForm form) {
        return new SetDefaultLeaveTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLeaveType(UserVisitPK userVisitPK, EditLeaveTypeForm form) {
        return new EditLeaveTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteLeaveType(UserVisitPK userVisitPK, DeleteLeaveTypeForm form) {
        return new DeleteLeaveTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Leave Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createLeaveTypeDescription(UserVisitPK userVisitPK, CreateLeaveTypeDescriptionForm form) {
        return new CreateLeaveTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLeaveTypeDescriptions(UserVisitPK userVisitPK, GetLeaveTypeDescriptionsForm form) {
        return new GetLeaveTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLeaveTypeDescription(UserVisitPK userVisitPK, GetLeaveTypeDescriptionForm form) {
        return new GetLeaveTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLeaveTypeDescription(UserVisitPK userVisitPK, EditLeaveTypeDescriptionForm form) {
        return new EditLeaveTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteLeaveTypeDescription(UserVisitPK userVisitPK, DeleteLeaveTypeDescriptionForm form) {
        return new DeleteLeaveTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Leave Reasons
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createLeaveReason(UserVisitPK userVisitPK, CreateLeaveReasonForm form) {
        return new CreateLeaveReasonCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLeaveReasons(UserVisitPK userVisitPK, GetLeaveReasonsForm form) {
        return new GetLeaveReasonsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLeaveReason(UserVisitPK userVisitPK, GetLeaveReasonForm form) {
        return new GetLeaveReasonCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLeaveReasonChoices(UserVisitPK userVisitPK, GetLeaveReasonChoicesForm form) {
        return new GetLeaveReasonChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultLeaveReason(UserVisitPK userVisitPK, SetDefaultLeaveReasonForm form) {
        return new SetDefaultLeaveReasonCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLeaveReason(UserVisitPK userVisitPK, EditLeaveReasonForm form) {
        return new EditLeaveReasonCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteLeaveReason(UserVisitPK userVisitPK, DeleteLeaveReasonForm form) {
        return new DeleteLeaveReasonCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Leave Reason Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createLeaveReasonDescription(UserVisitPK userVisitPK, CreateLeaveReasonDescriptionForm form) {
        return new CreateLeaveReasonDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLeaveReasonDescription(UserVisitPK userVisitPK, GetLeaveReasonDescriptionForm form) {
        return new GetLeaveReasonDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLeaveReasonDescriptions(UserVisitPK userVisitPK, GetLeaveReasonDescriptionsForm form) {
        return new GetLeaveReasonDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLeaveReasonDescription(UserVisitPK userVisitPK, EditLeaveReasonDescriptionForm form) {
        return new EditLeaveReasonDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteLeaveReasonDescription(UserVisitPK userVisitPK, DeleteLeaveReasonDescriptionForm form) {
        return new DeleteLeaveReasonDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Leaves
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createLeave(UserVisitPK userVisitPK, CreateLeaveForm form) {
        return new CreateLeaveCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLeaveStatusChoices(UserVisitPK userVisitPK, GetLeaveStatusChoicesForm form) {
        return new GetLeaveStatusChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setLeaveStatus(UserVisitPK userVisitPK, SetLeaveStatusForm form) {
        return new SetLeaveStatusCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLeaves(UserVisitPK userVisitPK, GetLeavesForm form) {
        return new GetLeavesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLeave(UserVisitPK userVisitPK, GetLeaveForm form) {
        return new GetLeaveCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLeave(UserVisitPK userVisitPK, EditLeaveForm form) {
        return new EditLeaveCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteLeave(UserVisitPK userVisitPK, DeleteLeaveForm form) {
        return new DeleteLeaveCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Termination Reasons
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createTerminationReason(UserVisitPK userVisitPK, CreateTerminationReasonForm form) {
        return new CreateTerminationReasonCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTerminationReasons(UserVisitPK userVisitPK, GetTerminationReasonsForm form) {
        return new GetTerminationReasonsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTerminationReason(UserVisitPK userVisitPK, GetTerminationReasonForm form) {
        return new GetTerminationReasonCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTerminationReasonChoices(UserVisitPK userVisitPK, GetTerminationReasonChoicesForm form) {
        return new GetTerminationReasonChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultTerminationReason(UserVisitPK userVisitPK, SetDefaultTerminationReasonForm form) {
        return new SetDefaultTerminationReasonCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editTerminationReason(UserVisitPK userVisitPK, EditTerminationReasonForm form) {
        return new EditTerminationReasonCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteTerminationReason(UserVisitPK userVisitPK, DeleteTerminationReasonForm form) {
        return new DeleteTerminationReasonCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Termination Reason Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createTerminationReasonDescription(UserVisitPK userVisitPK, CreateTerminationReasonDescriptionForm form) {
        return new CreateTerminationReasonDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTerminationReasonDescriptions(UserVisitPK userVisitPK, GetTerminationReasonDescriptionsForm form) {
        return new GetTerminationReasonDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editTerminationReasonDescription(UserVisitPK userVisitPK, EditTerminationReasonDescriptionForm form) {
        return new EditTerminationReasonDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteTerminationReasonDescription(UserVisitPK userVisitPK, DeleteTerminationReasonDescriptionForm form) {
        return new DeleteTerminationReasonDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Termination Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTerminationType(UserVisitPK userVisitPK, CreateTerminationTypeForm form) {
        return new CreateTerminationTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTerminationTypes(UserVisitPK userVisitPK, GetTerminationTypesForm form) {
        return new GetTerminationTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTerminationType(UserVisitPK userVisitPK, GetTerminationTypeForm form) {
        return new GetTerminationTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTerminationTypeChoices(UserVisitPK userVisitPK, GetTerminationTypeChoicesForm form) {
        return new GetTerminationTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultTerminationType(UserVisitPK userVisitPK, SetDefaultTerminationTypeForm form) {
        return new SetDefaultTerminationTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTerminationType(UserVisitPK userVisitPK, EditTerminationTypeForm form) {
        return new EditTerminationTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTerminationType(UserVisitPK userVisitPK, DeleteTerminationTypeForm form) {
        return new DeleteTerminationTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Termination Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTerminationTypeDescription(UserVisitPK userVisitPK, CreateTerminationTypeDescriptionForm form) {
        return new CreateTerminationTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTerminationTypeDescriptions(UserVisitPK userVisitPK, GetTerminationTypeDescriptionsForm form) {
        return new GetTerminationTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTerminationTypeDescription(UserVisitPK userVisitPK, EditTerminationTypeDescriptionForm form) {
        return new EditTerminationTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTerminationTypeDescription(UserVisitPK userVisitPK, DeleteTerminationTypeDescriptionForm form) {
        return new DeleteTerminationTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Employments
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createEmployment(UserVisitPK userVisitPK, CreateEmploymentForm form) {
        return new CreateEmploymentCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEmployments(UserVisitPK userVisitPK, GetEmploymentsForm form) {
        return new GetEmploymentsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getEmployment(UserVisitPK userVisitPK, GetEmploymentForm form) {
        return new GetEmploymentCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editEmployment(UserVisitPK userVisitPK, EditEmploymentForm form) {
        return new EditEmploymentCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteEmployment(UserVisitPK userVisitPK, DeleteEmploymentForm form) {
        return new DeleteEmploymentCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Responsibilities
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyResponsibility(UserVisitPK userVisitPK, CreatePartyResponsibilityForm form) {
        return new CreatePartyResponsibilityCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyResponsibilities(UserVisitPK userVisitPK, GetPartyResponsibilitiesForm form) {
        return new GetPartyResponsibilitiesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyResponsibility(UserVisitPK userVisitPK, DeletePartyResponsibilityForm form) {
        return new DeletePartyResponsibilityCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Skills
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartySkill(UserVisitPK userVisitPK, CreatePartySkillForm form) {
        return new CreatePartySkillCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySkills(UserVisitPK userVisitPK, GetPartySkillsForm form) {
        return new GetPartySkillsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartySkill(UserVisitPK userVisitPK, DeletePartySkillForm form) {
        return new DeletePartySkillCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Employee Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEmployeeType(UserVisitPK userVisitPK, CreateEmployeeTypeForm form) {
        return new CreateEmployeeTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEmployeeTypeChoices(UserVisitPK userVisitPK, GetEmployeeTypeChoicesForm form) {
        return new GetEmployeeTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEmployeeType(UserVisitPK userVisitPK, GetEmployeeTypeForm form) {
        return new GetEmployeeTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEmployeeTypes(UserVisitPK userVisitPK, GetEmployeeTypesForm form) {
        return new GetEmployeeTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultEmployeeType(UserVisitPK userVisitPK, SetDefaultEmployeeTypeForm form) {
        return new SetDefaultEmployeeTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEmployeeType(UserVisitPK userVisitPK, EditEmployeeTypeForm form) {
        return new EditEmployeeTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEmployeeType(UserVisitPK userVisitPK, DeleteEmployeeTypeForm form) {
        return new DeleteEmployeeTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Employee Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEmployeeTypeDescription(UserVisitPK userVisitPK, CreateEmployeeTypeDescriptionForm form) {
        return new CreateEmployeeTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getEmployeeTypeDescriptions(UserVisitPK userVisitPK, GetEmployeeTypeDescriptionsForm form) {
        return new GetEmployeeTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editEmployeeTypeDescription(UserVisitPK userVisitPK, EditEmployeeTypeDescriptionForm form) {
        return new EditEmployeeTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteEmployeeTypeDescription(UserVisitPK userVisitPK, DeleteEmployeeTypeDescriptionForm form) {
        return new DeleteEmployeeTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Employees
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getEmployee(UserVisitPK userVisitPK, GetEmployeeForm form) {
        return new GetEmployeeCommand().run(userVisitPK, form);
    }
    
}
