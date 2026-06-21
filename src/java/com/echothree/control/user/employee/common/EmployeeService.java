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
import com.echothree.control.user.employee.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface EmployeeService
        extends EmployeeForms {
    
    // -------------------------------------------------------------------------
    //   Responsibility Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createResponsibilityType(UserVisitPK userVisitPK, CreateResponsibilityTypeForm form);
    
    CommandResult<GetResponsibilityTypesResult> getResponsibilityTypes(UserVisitPK userVisitPK, GetResponsibilityTypesForm form);
    
    CommandResult<GetResponsibilityTypeResult> getResponsibilityType(UserVisitPK userVisitPK, GetResponsibilityTypeForm form);
    
    CommandResult<GetResponsibilityTypeChoicesResult> getResponsibilityTypeChoices(UserVisitPK userVisitPK, GetResponsibilityTypeChoicesForm form);
    
    CommandResult<?> setDefaultResponsibilityType(UserVisitPK userVisitPK, SetDefaultResponsibilityTypeForm form);
    
    CommandResult<EditResponsibilityTypeResult> editResponsibilityType(UserVisitPK userVisitPK, EditResponsibilityTypeForm form);
    
    CommandResult<?> deleteResponsibilityType(UserVisitPK userVisitPK, DeleteResponsibilityTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Responsibility Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createResponsibilityTypeDescription(UserVisitPK userVisitPK, CreateResponsibilityTypeDescriptionForm form);
    
    CommandResult<GetResponsibilityTypeDescriptionsResult> getResponsibilityTypeDescriptions(UserVisitPK userVisitPK, GetResponsibilityTypeDescriptionsForm form);
    
    CommandResult<EditResponsibilityTypeDescriptionResult> editResponsibilityTypeDescription(UserVisitPK userVisitPK, EditResponsibilityTypeDescriptionForm form);
    
    CommandResult<?> deleteResponsibilityTypeDescription(UserVisitPK userVisitPK, DeleteResponsibilityTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Skill Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createSkillType(UserVisitPK userVisitPK, CreateSkillTypeForm form);
    
    CommandResult<GetSkillTypesResult> getSkillTypes(UserVisitPK userVisitPK, GetSkillTypesForm form);
    
    CommandResult<GetSkillTypeResult> getSkillType(UserVisitPK userVisitPK, GetSkillTypeForm form);
    
    CommandResult<GetSkillTypeChoicesResult> getSkillTypeChoices(UserVisitPK userVisitPK, GetSkillTypeChoicesForm form);
    
    CommandResult<?> setDefaultSkillType(UserVisitPK userVisitPK, SetDefaultSkillTypeForm form);
    
    CommandResult<EditSkillTypeResult> editSkillType(UserVisitPK userVisitPK, EditSkillTypeForm form);
    
    CommandResult<?> deleteSkillType(UserVisitPK userVisitPK, DeleteSkillTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Skill Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createSkillTypeDescription(UserVisitPK userVisitPK, CreateSkillTypeDescriptionForm form);
    
    CommandResult<GetSkillTypeDescriptionsResult> getSkillTypeDescriptions(UserVisitPK userVisitPK, GetSkillTypeDescriptionsForm form);
    
    CommandResult<EditSkillTypeDescriptionResult> editSkillTypeDescription(UserVisitPK userVisitPK, EditSkillTypeDescriptionForm form);
    
    CommandResult<?> deleteSkillTypeDescription(UserVisitPK userVisitPK, DeleteSkillTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Leave Types
    // -------------------------------------------------------------------------

    CommandResult<?> createLeaveType(UserVisitPK userVisitPK, CreateLeaveTypeForm form);

    CommandResult<GetLeaveTypesResult> getLeaveTypes(UserVisitPK userVisitPK, GetLeaveTypesForm form);

    CommandResult<GetLeaveTypeResult> getLeaveType(UserVisitPK userVisitPK, GetLeaveTypeForm form);

    CommandResult<GetLeaveTypeChoicesResult> getLeaveTypeChoices(UserVisitPK userVisitPK, GetLeaveTypeChoicesForm form);

    CommandResult<?> setDefaultLeaveType(UserVisitPK userVisitPK, SetDefaultLeaveTypeForm form);

    CommandResult<EditLeaveTypeResult> editLeaveType(UserVisitPK userVisitPK, EditLeaveTypeForm form);

    CommandResult<?> deleteLeaveType(UserVisitPK userVisitPK, DeleteLeaveTypeForm form);

    // -------------------------------------------------------------------------
    //   Leave Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createLeaveTypeDescription(UserVisitPK userVisitPK, CreateLeaveTypeDescriptionForm form);

    CommandResult<GetLeaveTypeDescriptionResult> getLeaveTypeDescription(UserVisitPK userVisitPK, GetLeaveTypeDescriptionForm form);

    CommandResult<GetLeaveTypeDescriptionsResult> getLeaveTypeDescriptions(UserVisitPK userVisitPK, GetLeaveTypeDescriptionsForm form);

    CommandResult<EditLeaveTypeDescriptionResult> editLeaveTypeDescription(UserVisitPK userVisitPK, EditLeaveTypeDescriptionForm form);

    CommandResult<?> deleteLeaveTypeDescription(UserVisitPK userVisitPK, DeleteLeaveTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Leave Reasons
    // -------------------------------------------------------------------------

    CommandResult<?> createLeaveReason(UserVisitPK userVisitPK, CreateLeaveReasonForm form);

    CommandResult<GetLeaveReasonsResult> getLeaveReasons(UserVisitPK userVisitPK, GetLeaveReasonsForm form);

    CommandResult<GetLeaveReasonResult> getLeaveReason(UserVisitPK userVisitPK, GetLeaveReasonForm form);

    CommandResult<GetLeaveReasonChoicesResult> getLeaveReasonChoices(UserVisitPK userVisitPK, GetLeaveReasonChoicesForm form);

    CommandResult<?> setDefaultLeaveReason(UserVisitPK userVisitPK, SetDefaultLeaveReasonForm form);

    CommandResult<EditLeaveReasonResult> editLeaveReason(UserVisitPK userVisitPK, EditLeaveReasonForm form);

    CommandResult<?> deleteLeaveReason(UserVisitPK userVisitPK, DeleteLeaveReasonForm form);

    // -------------------------------------------------------------------------
    //   Leave Reason Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createLeaveReasonDescription(UserVisitPK userVisitPK, CreateLeaveReasonDescriptionForm form);

    CommandResult<GetLeaveReasonDescriptionResult> getLeaveReasonDescription(UserVisitPK userVisitPK, GetLeaveReasonDescriptionForm form);

    CommandResult<GetLeaveReasonDescriptionsResult> getLeaveReasonDescriptions(UserVisitPK userVisitPK, GetLeaveReasonDescriptionsForm form);

    CommandResult<EditLeaveReasonDescriptionResult> editLeaveReasonDescription(UserVisitPK userVisitPK, EditLeaveReasonDescriptionForm form);

    CommandResult<?> deleteLeaveReasonDescription(UserVisitPK userVisitPK, DeleteLeaveReasonDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Leaves
    // -------------------------------------------------------------------------

    CommandResult<?> createLeave(UserVisitPK userVisitPK, CreateLeaveForm form);

    CommandResult<GetLeaveStatusChoicesResult> getLeaveStatusChoices(UserVisitPK userVisitPK, GetLeaveStatusChoicesForm form);

    CommandResult<SetLeaveStatusResult> setLeaveStatus(UserVisitPK userVisitPK, SetLeaveStatusForm form);

    CommandResult<GetLeavesResult> getLeaves(UserVisitPK userVisitPK, GetLeavesForm form);

    CommandResult<GetLeaveResult> getLeave(UserVisitPK userVisitPK, GetLeaveForm form);

    CommandResult<EditLeaveResult> editLeave(UserVisitPK userVisitPK, EditLeaveForm form);

    CommandResult<?> deleteLeave(UserVisitPK userVisitPK, DeleteLeaveForm form);

    // -------------------------------------------------------------------------
    //   Termination Reasons
    // -------------------------------------------------------------------------

    CommandResult<?> createTerminationReason(UserVisitPK userVisitPK, CreateTerminationReasonForm form);

    CommandResult<GetTerminationReasonsResult> getTerminationReasons(UserVisitPK userVisitPK, GetTerminationReasonsForm form);

    CommandResult<GetTerminationReasonResult> getTerminationReason(UserVisitPK userVisitPK, GetTerminationReasonForm form);

    CommandResult<GetTerminationReasonChoicesResult> getTerminationReasonChoices(UserVisitPK userVisitPK, GetTerminationReasonChoicesForm form);

    CommandResult<?> setDefaultTerminationReason(UserVisitPK userVisitPK, SetDefaultTerminationReasonForm form);

    CommandResult<EditTerminationReasonResult> editTerminationReason(UserVisitPK userVisitPK, EditTerminationReasonForm form);

    CommandResult<?> deleteTerminationReason(UserVisitPK userVisitPK, DeleteTerminationReasonForm form);

    // -------------------------------------------------------------------------
    //   Termination Reason Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createTerminationReasonDescription(UserVisitPK userVisitPK, CreateTerminationReasonDescriptionForm form);

    CommandResult<GetTerminationReasonDescriptionsResult> getTerminationReasonDescriptions(UserVisitPK userVisitPK, GetTerminationReasonDescriptionsForm form);

    CommandResult<EditTerminationReasonDescriptionResult> editTerminationReasonDescription(UserVisitPK userVisitPK, EditTerminationReasonDescriptionForm form);

    CommandResult<?> deleteTerminationReasonDescription(UserVisitPK userVisitPK, DeleteTerminationReasonDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Termination Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createTerminationType(UserVisitPK userVisitPK, CreateTerminationTypeForm form);
    
    CommandResult<GetTerminationTypesResult> getTerminationTypes(UserVisitPK userVisitPK, GetTerminationTypesForm form);
    
    CommandResult<GetTerminationTypeResult> getTerminationType(UserVisitPK userVisitPK, GetTerminationTypeForm form);
    
    CommandResult<GetTerminationTypeChoicesResult> getTerminationTypeChoices(UserVisitPK userVisitPK, GetTerminationTypeChoicesForm form);
    
    CommandResult<?> setDefaultTerminationType(UserVisitPK userVisitPK, SetDefaultTerminationTypeForm form);
    
    CommandResult<EditTerminationTypeResult> editTerminationType(UserVisitPK userVisitPK, EditTerminationTypeForm form);
    
    CommandResult<?> deleteTerminationType(UserVisitPK userVisitPK, DeleteTerminationTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Termination Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createTerminationTypeDescription(UserVisitPK userVisitPK, CreateTerminationTypeDescriptionForm form);
    
    CommandResult<GetTerminationTypeDescriptionsResult> getTerminationTypeDescriptions(UserVisitPK userVisitPK, GetTerminationTypeDescriptionsForm form);
    
    CommandResult<EditTerminationTypeDescriptionResult> editTerminationTypeDescription(UserVisitPK userVisitPK, EditTerminationTypeDescriptionForm form);
    
    CommandResult<?> deleteTerminationTypeDescription(UserVisitPK userVisitPK, DeleteTerminationTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Employments
    // -------------------------------------------------------------------------

    CommandResult<?> createEmployment(UserVisitPK userVisitPK, CreateEmploymentForm form);

    CommandResult<GetEmploymentsResult> getEmployments(UserVisitPK userVisitPK, GetEmploymentsForm form);

    CommandResult<GetEmploymentResult> getEmployment(UserVisitPK userVisitPK, GetEmploymentForm form);

    CommandResult<EditEmploymentResult> editEmployment(UserVisitPK userVisitPK, EditEmploymentForm form);

    CommandResult<?> deleteEmployment(UserVisitPK userVisitPK, DeleteEmploymentForm form);

    // -------------------------------------------------------------------------
    //   Party Responsibilities
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPartyResponsibility(UserVisitPK userVisitPK, CreatePartyResponsibilityForm form);
    
    CommandResult<GetPartyResponsibilitiesResult> getPartyResponsibilities(UserVisitPK userVisitPK, GetPartyResponsibilitiesForm form);
    
    CommandResult<?> deletePartyResponsibility(UserVisitPK userVisitPK, DeletePartyResponsibilityForm form);
    
    // -------------------------------------------------------------------------
    //   Party Skills
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPartySkill(UserVisitPK userVisitPK, CreatePartySkillForm form);
    
    CommandResult<GetPartySkillsResult> getPartySkills(UserVisitPK userVisitPK, GetPartySkillsForm form);
    
    CommandResult<?> deletePartySkill(UserVisitPK userVisitPK, DeletePartySkillForm form);
    
    // --------------------------------------------------------------------------------
    //   Employee Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createEmployeeType(UserVisitPK userVisitPK, CreateEmployeeTypeForm form);
    
    CommandResult<GetEmployeeTypeChoicesResult> getEmployeeTypeChoices(UserVisitPK userVisitPK, GetEmployeeTypeChoicesForm form);
    
    CommandResult<GetEmployeeTypeResult> getEmployeeType(UserVisitPK userVisitPK, GetEmployeeTypeForm form);
    
    CommandResult<GetEmployeeTypesResult> getEmployeeTypes(UserVisitPK userVisitPK, GetEmployeeTypesForm form);
    
    CommandResult<?> setDefaultEmployeeType(UserVisitPK userVisitPK, SetDefaultEmployeeTypeForm form);
    
    CommandResult<EditEmployeeTypeResult> editEmployeeType(UserVisitPK userVisitPK, EditEmployeeTypeForm form);
    
    CommandResult<?> deleteEmployeeType(UserVisitPK userVisitPK, DeleteEmployeeTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Employee Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createEmployeeTypeDescription(UserVisitPK userVisitPK, CreateEmployeeTypeDescriptionForm form);
    
    CommandResult<GetEmployeeTypeDescriptionsResult> getEmployeeTypeDescriptions(UserVisitPK userVisitPK, GetEmployeeTypeDescriptionsForm form);
    
    CommandResult<EditEmployeeTypeDescriptionResult> editEmployeeTypeDescription(UserVisitPK userVisitPK, EditEmployeeTypeDescriptionForm form);
    
    CommandResult<?> deleteEmployeeTypeDescription(UserVisitPK userVisitPK, DeleteEmployeeTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Employees
    // -------------------------------------------------------------------------
    
    CommandResult<GetEmployeeResult> getEmployee(UserVisitPK userVisitPK, GetEmployeeForm form);
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
        
}
