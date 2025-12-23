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

package com.echothree.control.user.employee.common;

import com.echothree.control.user.employee.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface EmployeeService
        extends EmployeeForms {
    
    // -------------------------------------------------------------------------
    //   Responsibility Types
    // -------------------------------------------------------------------------
    
    CommandResult createResponsibilityType(UserVisitPK userVisitPK, CreateResponsibilityTypeForm form);
    
    CommandResult getResponsibilityTypes(UserVisitPK userVisitPK, GetResponsibilityTypesForm form);
    
    CommandResult getResponsibilityType(UserVisitPK userVisitPK, GetResponsibilityTypeForm form);
    
    CommandResult getResponsibilityTypeChoices(UserVisitPK userVisitPK, GetResponsibilityTypeChoicesForm form);
    
    CommandResult setDefaultResponsibilityType(UserVisitPK userVisitPK, SetDefaultResponsibilityTypeForm form);
    
    CommandResult editResponsibilityType(UserVisitPK userVisitPK, EditResponsibilityTypeForm form);
    
    CommandResult deleteResponsibilityType(UserVisitPK userVisitPK, DeleteResponsibilityTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Responsibility Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createResponsibilityTypeDescription(UserVisitPK userVisitPK, CreateResponsibilityTypeDescriptionForm form);
    
    CommandResult getResponsibilityTypeDescriptions(UserVisitPK userVisitPK, GetResponsibilityTypeDescriptionsForm form);
    
    CommandResult editResponsibilityTypeDescription(UserVisitPK userVisitPK, EditResponsibilityTypeDescriptionForm form);
    
    CommandResult deleteResponsibilityTypeDescription(UserVisitPK userVisitPK, DeleteResponsibilityTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Skill Types
    // -------------------------------------------------------------------------
    
    CommandResult createSkillType(UserVisitPK userVisitPK, CreateSkillTypeForm form);
    
    CommandResult getSkillTypes(UserVisitPK userVisitPK, GetSkillTypesForm form);
    
    CommandResult getSkillType(UserVisitPK userVisitPK, GetSkillTypeForm form);
    
    CommandResult getSkillTypeChoices(UserVisitPK userVisitPK, GetSkillTypeChoicesForm form);
    
    CommandResult setDefaultSkillType(UserVisitPK userVisitPK, SetDefaultSkillTypeForm form);
    
    CommandResult editSkillType(UserVisitPK userVisitPK, EditSkillTypeForm form);
    
    CommandResult deleteSkillType(UserVisitPK userVisitPK, DeleteSkillTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Skill Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createSkillTypeDescription(UserVisitPK userVisitPK, CreateSkillTypeDescriptionForm form);
    
    CommandResult getSkillTypeDescriptions(UserVisitPK userVisitPK, GetSkillTypeDescriptionsForm form);
    
    CommandResult editSkillTypeDescription(UserVisitPK userVisitPK, EditSkillTypeDescriptionForm form);
    
    CommandResult deleteSkillTypeDescription(UserVisitPK userVisitPK, DeleteSkillTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Leave Types
    // -------------------------------------------------------------------------

    CommandResult createLeaveType(UserVisitPK userVisitPK, CreateLeaveTypeForm form);

    CommandResult getLeaveTypes(UserVisitPK userVisitPK, GetLeaveTypesForm form);

    CommandResult getLeaveType(UserVisitPK userVisitPK, GetLeaveTypeForm form);

    CommandResult getLeaveTypeChoices(UserVisitPK userVisitPK, GetLeaveTypeChoicesForm form);

    CommandResult setDefaultLeaveType(UserVisitPK userVisitPK, SetDefaultLeaveTypeForm form);

    CommandResult editLeaveType(UserVisitPK userVisitPK, EditLeaveTypeForm form);

    CommandResult deleteLeaveType(UserVisitPK userVisitPK, DeleteLeaveTypeForm form);

    // -------------------------------------------------------------------------
    //   Leave Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createLeaveTypeDescription(UserVisitPK userVisitPK, CreateLeaveTypeDescriptionForm form);

    CommandResult getLeaveTypeDescription(UserVisitPK userVisitPK, GetLeaveTypeDescriptionForm form);

    CommandResult getLeaveTypeDescriptions(UserVisitPK userVisitPK, GetLeaveTypeDescriptionsForm form);

    CommandResult editLeaveTypeDescription(UserVisitPK userVisitPK, EditLeaveTypeDescriptionForm form);

    CommandResult deleteLeaveTypeDescription(UserVisitPK userVisitPK, DeleteLeaveTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Leave Reasons
    // -------------------------------------------------------------------------

    CommandResult createLeaveReason(UserVisitPK userVisitPK, CreateLeaveReasonForm form);

    CommandResult getLeaveReasons(UserVisitPK userVisitPK, GetLeaveReasonsForm form);

    CommandResult getLeaveReason(UserVisitPK userVisitPK, GetLeaveReasonForm form);

    CommandResult getLeaveReasonChoices(UserVisitPK userVisitPK, GetLeaveReasonChoicesForm form);

    CommandResult setDefaultLeaveReason(UserVisitPK userVisitPK, SetDefaultLeaveReasonForm form);

    CommandResult editLeaveReason(UserVisitPK userVisitPK, EditLeaveReasonForm form);

    CommandResult deleteLeaveReason(UserVisitPK userVisitPK, DeleteLeaveReasonForm form);

    // -------------------------------------------------------------------------
    //   Leave Reason Descriptions
    // -------------------------------------------------------------------------

    CommandResult createLeaveReasonDescription(UserVisitPK userVisitPK, CreateLeaveReasonDescriptionForm form);

    CommandResult getLeaveReasonDescription(UserVisitPK userVisitPK, GetLeaveReasonDescriptionForm form);

    CommandResult getLeaveReasonDescriptions(UserVisitPK userVisitPK, GetLeaveReasonDescriptionsForm form);

    CommandResult editLeaveReasonDescription(UserVisitPK userVisitPK, EditLeaveReasonDescriptionForm form);

    CommandResult deleteLeaveReasonDescription(UserVisitPK userVisitPK, DeleteLeaveReasonDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Leaves
    // -------------------------------------------------------------------------

    CommandResult createLeave(UserVisitPK userVisitPK, CreateLeaveForm form);

    CommandResult getLeaveStatusChoices(UserVisitPK userVisitPK, GetLeaveStatusChoicesForm form);

    CommandResult setLeaveStatus(UserVisitPK userVisitPK, SetLeaveStatusForm form);

    CommandResult getLeaves(UserVisitPK userVisitPK, GetLeavesForm form);

    CommandResult getLeave(UserVisitPK userVisitPK, GetLeaveForm form);

    CommandResult editLeave(UserVisitPK userVisitPK, EditLeaveForm form);

    CommandResult deleteLeave(UserVisitPK userVisitPK, DeleteLeaveForm form);

    // -------------------------------------------------------------------------
    //   Termination Reasons
    // -------------------------------------------------------------------------

    CommandResult createTerminationReason(UserVisitPK userVisitPK, CreateTerminationReasonForm form);

    CommandResult getTerminationReasons(UserVisitPK userVisitPK, GetTerminationReasonsForm form);

    CommandResult getTerminationReason(UserVisitPK userVisitPK, GetTerminationReasonForm form);

    CommandResult getTerminationReasonChoices(UserVisitPK userVisitPK, GetTerminationReasonChoicesForm form);

    CommandResult setDefaultTerminationReason(UserVisitPK userVisitPK, SetDefaultTerminationReasonForm form);

    CommandResult editTerminationReason(UserVisitPK userVisitPK, EditTerminationReasonForm form);

    CommandResult deleteTerminationReason(UserVisitPK userVisitPK, DeleteTerminationReasonForm form);

    // -------------------------------------------------------------------------
    //   Termination Reason Descriptions
    // -------------------------------------------------------------------------

    CommandResult createTerminationReasonDescription(UserVisitPK userVisitPK, CreateTerminationReasonDescriptionForm form);

    CommandResult getTerminationReasonDescriptions(UserVisitPK userVisitPK, GetTerminationReasonDescriptionsForm form);

    CommandResult editTerminationReasonDescription(UserVisitPK userVisitPK, EditTerminationReasonDescriptionForm form);

    CommandResult deleteTerminationReasonDescription(UserVisitPK userVisitPK, DeleteTerminationReasonDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Termination Types
    // -------------------------------------------------------------------------
    
    CommandResult createTerminationType(UserVisitPK userVisitPK, CreateTerminationTypeForm form);
    
    CommandResult getTerminationTypes(UserVisitPK userVisitPK, GetTerminationTypesForm form);
    
    CommandResult getTerminationType(UserVisitPK userVisitPK, GetTerminationTypeForm form);
    
    CommandResult getTerminationTypeChoices(UserVisitPK userVisitPK, GetTerminationTypeChoicesForm form);
    
    CommandResult setDefaultTerminationType(UserVisitPK userVisitPK, SetDefaultTerminationTypeForm form);
    
    CommandResult editTerminationType(UserVisitPK userVisitPK, EditTerminationTypeForm form);
    
    CommandResult deleteTerminationType(UserVisitPK userVisitPK, DeleteTerminationTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Termination Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createTerminationTypeDescription(UserVisitPK userVisitPK, CreateTerminationTypeDescriptionForm form);
    
    CommandResult getTerminationTypeDescriptions(UserVisitPK userVisitPK, GetTerminationTypeDescriptionsForm form);
    
    CommandResult editTerminationTypeDescription(UserVisitPK userVisitPK, EditTerminationTypeDescriptionForm form);
    
    CommandResult deleteTerminationTypeDescription(UserVisitPK userVisitPK, DeleteTerminationTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Employments
    // -------------------------------------------------------------------------

    CommandResult createEmployment(UserVisitPK userVisitPK, CreateEmploymentForm form);

    CommandResult getEmployments(UserVisitPK userVisitPK, GetEmploymentsForm form);

    CommandResult getEmployment(UserVisitPK userVisitPK, GetEmploymentForm form);

    CommandResult editEmployment(UserVisitPK userVisitPK, EditEmploymentForm form);

    CommandResult deleteEmployment(UserVisitPK userVisitPK, DeleteEmploymentForm form);

    // -------------------------------------------------------------------------
    //   Party Responsibilities
    // -------------------------------------------------------------------------
    
    CommandResult createPartyResponsibility(UserVisitPK userVisitPK, CreatePartyResponsibilityForm form);
    
    CommandResult getPartyResponsibilities(UserVisitPK userVisitPK, GetPartyResponsibilitiesForm form);
    
    CommandResult deletePartyResponsibility(UserVisitPK userVisitPK, DeletePartyResponsibilityForm form);
    
    // -------------------------------------------------------------------------
    //   Party Skills
    // -------------------------------------------------------------------------
    
    CommandResult createPartySkill(UserVisitPK userVisitPK, CreatePartySkillForm form);
    
    CommandResult getPartySkills(UserVisitPK userVisitPK, GetPartySkillsForm form);
    
    CommandResult deletePartySkill(UserVisitPK userVisitPK, DeletePartySkillForm form);
    
    // --------------------------------------------------------------------------------
    //   Employee Types
    // --------------------------------------------------------------------------------
    
    CommandResult createEmployeeType(UserVisitPK userVisitPK, CreateEmployeeTypeForm form);
    
    CommandResult getEmployeeTypeChoices(UserVisitPK userVisitPK, GetEmployeeTypeChoicesForm form);
    
    CommandResult getEmployeeType(UserVisitPK userVisitPK, GetEmployeeTypeForm form);
    
    CommandResult getEmployeeTypes(UserVisitPK userVisitPK, GetEmployeeTypesForm form);
    
    CommandResult setDefaultEmployeeType(UserVisitPK userVisitPK, SetDefaultEmployeeTypeForm form);
    
    CommandResult editEmployeeType(UserVisitPK userVisitPK, EditEmployeeTypeForm form);
    
    CommandResult deleteEmployeeType(UserVisitPK userVisitPK, DeleteEmployeeTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Employee Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createEmployeeTypeDescription(UserVisitPK userVisitPK, CreateEmployeeTypeDescriptionForm form);
    
    CommandResult getEmployeeTypeDescriptions(UserVisitPK userVisitPK, GetEmployeeTypeDescriptionsForm form);
    
    CommandResult editEmployeeTypeDescription(UserVisitPK userVisitPK, EditEmployeeTypeDescriptionForm form);
    
    CommandResult deleteEmployeeTypeDescription(UserVisitPK userVisitPK, DeleteEmployeeTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Employees
    // -------------------------------------------------------------------------
    
    CommandResult getEmployee(UserVisitPK userVisitPK, GetEmployeeForm form);
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
        
}
