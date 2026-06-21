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

package com.echothree.control.user.employee.server;

import com.echothree.control.user.employee.common.EmployeeRemote;
import com.echothree.control.user.employee.common.form.*;
import com.echothree.control.user.employee.common.result.*;
import com.echothree.control.user.employee.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
    public CommandResult<VoidResult> createResponsibilityType(UserVisitPK userVisitPK, CreateResponsibilityTypeForm form) {
        return CDI.current().select(CreateResponsibilityTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetResponsibilityTypesResult> getResponsibilityTypes(UserVisitPK userVisitPK, GetResponsibilityTypesForm form) {
        return CDI.current().select(GetResponsibilityTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetResponsibilityTypeResult> getResponsibilityType(UserVisitPK userVisitPK, GetResponsibilityTypeForm form) {
        return CDI.current().select(GetResponsibilityTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetResponsibilityTypeChoicesResult> getResponsibilityTypeChoices(UserVisitPK userVisitPK, GetResponsibilityTypeChoicesForm form) {
        return CDI.current().select(GetResponsibilityTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultResponsibilityType(UserVisitPK userVisitPK, SetDefaultResponsibilityTypeForm form) {
        return CDI.current().select(SetDefaultResponsibilityTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditResponsibilityTypeResult> editResponsibilityType(UserVisitPK userVisitPK, EditResponsibilityTypeForm form) {
        return CDI.current().select(EditResponsibilityTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteResponsibilityType(UserVisitPK userVisitPK, DeleteResponsibilityTypeForm form) {
        return CDI.current().select(DeleteResponsibilityTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Responsibility Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createResponsibilityTypeDescription(UserVisitPK userVisitPK, CreateResponsibilityTypeDescriptionForm form) {
        return CDI.current().select(CreateResponsibilityTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetResponsibilityTypeDescriptionsResult> getResponsibilityTypeDescriptions(UserVisitPK userVisitPK, GetResponsibilityTypeDescriptionsForm form) {
        return CDI.current().select(GetResponsibilityTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditResponsibilityTypeDescriptionResult> editResponsibilityTypeDescription(UserVisitPK userVisitPK, EditResponsibilityTypeDescriptionForm form) {
        return CDI.current().select(EditResponsibilityTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteResponsibilityTypeDescription(UserVisitPK userVisitPK, DeleteResponsibilityTypeDescriptionForm form) {
        return CDI.current().select(DeleteResponsibilityTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Skill Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createSkillType(UserVisitPK userVisitPK, CreateSkillTypeForm form) {
        return CDI.current().select(CreateSkillTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSkillTypesResult> getSkillTypes(UserVisitPK userVisitPK, GetSkillTypesForm form) {
        return CDI.current().select(GetSkillTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSkillTypeResult> getSkillType(UserVisitPK userVisitPK, GetSkillTypeForm form) {
        return CDI.current().select(GetSkillTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSkillTypeChoicesResult> getSkillTypeChoices(UserVisitPK userVisitPK, GetSkillTypeChoicesForm form) {
        return CDI.current().select(GetSkillTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultSkillType(UserVisitPK userVisitPK, SetDefaultSkillTypeForm form) {
        return CDI.current().select(SetDefaultSkillTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditSkillTypeResult> editSkillType(UserVisitPK userVisitPK, EditSkillTypeForm form) {
        return CDI.current().select(EditSkillTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteSkillType(UserVisitPK userVisitPK, DeleteSkillTypeForm form) {
        return CDI.current().select(DeleteSkillTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Skill Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createSkillTypeDescription(UserVisitPK userVisitPK, CreateSkillTypeDescriptionForm form) {
        return CDI.current().select(CreateSkillTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetSkillTypeDescriptionsResult> getSkillTypeDescriptions(UserVisitPK userVisitPK, GetSkillTypeDescriptionsForm form) {
        return CDI.current().select(GetSkillTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditSkillTypeDescriptionResult> editSkillTypeDescription(UserVisitPK userVisitPK, EditSkillTypeDescriptionForm form) {
        return CDI.current().select(EditSkillTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteSkillTypeDescription(UserVisitPK userVisitPK, DeleteSkillTypeDescriptionForm form) {
        return CDI.current().select(DeleteSkillTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Leave Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createLeaveType(UserVisitPK userVisitPK, CreateLeaveTypeForm form) {
        return CDI.current().select(CreateLeaveTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLeaveTypesResult> getLeaveTypes(UserVisitPK userVisitPK, GetLeaveTypesForm form) {
        return CDI.current().select(GetLeaveTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLeaveTypeResult> getLeaveType(UserVisitPK userVisitPK, GetLeaveTypeForm form) {
        return CDI.current().select(GetLeaveTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLeaveTypeChoicesResult> getLeaveTypeChoices(UserVisitPK userVisitPK, GetLeaveTypeChoicesForm form) {
        return CDI.current().select(GetLeaveTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultLeaveType(UserVisitPK userVisitPK, SetDefaultLeaveTypeForm form) {
        return CDI.current().select(SetDefaultLeaveTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditLeaveTypeResult> editLeaveType(UserVisitPK userVisitPK, EditLeaveTypeForm form) {
        return CDI.current().select(EditLeaveTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteLeaveType(UserVisitPK userVisitPK, DeleteLeaveTypeForm form) {
        return CDI.current().select(DeleteLeaveTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Leave Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createLeaveTypeDescription(UserVisitPK userVisitPK, CreateLeaveTypeDescriptionForm form) {
        return CDI.current().select(CreateLeaveTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLeaveTypeDescriptionsResult> getLeaveTypeDescriptions(UserVisitPK userVisitPK, GetLeaveTypeDescriptionsForm form) {
        return CDI.current().select(GetLeaveTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLeaveTypeDescriptionResult> getLeaveTypeDescription(UserVisitPK userVisitPK, GetLeaveTypeDescriptionForm form) {
        return CDI.current().select(GetLeaveTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditLeaveTypeDescriptionResult> editLeaveTypeDescription(UserVisitPK userVisitPK, EditLeaveTypeDescriptionForm form) {
        return CDI.current().select(EditLeaveTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteLeaveTypeDescription(UserVisitPK userVisitPK, DeleteLeaveTypeDescriptionForm form) {
        return CDI.current().select(DeleteLeaveTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Leave Reasons
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createLeaveReason(UserVisitPK userVisitPK, CreateLeaveReasonForm form) {
        return CDI.current().select(CreateLeaveReasonCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLeaveReasonsResult> getLeaveReasons(UserVisitPK userVisitPK, GetLeaveReasonsForm form) {
        return CDI.current().select(GetLeaveReasonsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLeaveReasonResult> getLeaveReason(UserVisitPK userVisitPK, GetLeaveReasonForm form) {
        return CDI.current().select(GetLeaveReasonCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLeaveReasonChoicesResult> getLeaveReasonChoices(UserVisitPK userVisitPK, GetLeaveReasonChoicesForm form) {
        return CDI.current().select(GetLeaveReasonChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultLeaveReason(UserVisitPK userVisitPK, SetDefaultLeaveReasonForm form) {
        return CDI.current().select(SetDefaultLeaveReasonCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditLeaveReasonResult> editLeaveReason(UserVisitPK userVisitPK, EditLeaveReasonForm form) {
        return CDI.current().select(EditLeaveReasonCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteLeaveReason(UserVisitPK userVisitPK, DeleteLeaveReasonForm form) {
        return CDI.current().select(DeleteLeaveReasonCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Leave Reason Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createLeaveReasonDescription(UserVisitPK userVisitPK, CreateLeaveReasonDescriptionForm form) {
        return CDI.current().select(CreateLeaveReasonDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLeaveReasonDescriptionResult> getLeaveReasonDescription(UserVisitPK userVisitPK, GetLeaveReasonDescriptionForm form) {
        return CDI.current().select(GetLeaveReasonDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLeaveReasonDescriptionsResult> getLeaveReasonDescriptions(UserVisitPK userVisitPK, GetLeaveReasonDescriptionsForm form) {
        return CDI.current().select(GetLeaveReasonDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditLeaveReasonDescriptionResult> editLeaveReasonDescription(UserVisitPK userVisitPK, EditLeaveReasonDescriptionForm form) {
        return CDI.current().select(EditLeaveReasonDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteLeaveReasonDescription(UserVisitPK userVisitPK, DeleteLeaveReasonDescriptionForm form) {
        return CDI.current().select(DeleteLeaveReasonDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Leaves
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createLeave(UserVisitPK userVisitPK, CreateLeaveForm form) {
        return CDI.current().select(CreateLeaveCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLeaveStatusChoicesResult> getLeaveStatusChoices(UserVisitPK userVisitPK, GetLeaveStatusChoicesForm form) {
        return CDI.current().select(GetLeaveStatusChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<SetLeaveStatusResult> setLeaveStatus(UserVisitPK userVisitPK, SetLeaveStatusForm form) {
        return CDI.current().select(SetLeaveStatusCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLeavesResult> getLeaves(UserVisitPK userVisitPK, GetLeavesForm form) {
        return CDI.current().select(GetLeavesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLeaveResult> getLeave(UserVisitPK userVisitPK, GetLeaveForm form) {
        return CDI.current().select(GetLeaveCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditLeaveResult> editLeave(UserVisitPK userVisitPK, EditLeaveForm form) {
        return CDI.current().select(EditLeaveCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteLeave(UserVisitPK userVisitPK, DeleteLeaveForm form) {
        return CDI.current().select(DeleteLeaveCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Termination Reasons
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createTerminationReason(UserVisitPK userVisitPK, CreateTerminationReasonForm form) {
        return CDI.current().select(CreateTerminationReasonCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetTerminationReasonsResult> getTerminationReasons(UserVisitPK userVisitPK, GetTerminationReasonsForm form) {
        return CDI.current().select(GetTerminationReasonsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetTerminationReasonResult> getTerminationReason(UserVisitPK userVisitPK, GetTerminationReasonForm form) {
        return CDI.current().select(GetTerminationReasonCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetTerminationReasonChoicesResult> getTerminationReasonChoices(UserVisitPK userVisitPK, GetTerminationReasonChoicesForm form) {
        return CDI.current().select(GetTerminationReasonChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultTerminationReason(UserVisitPK userVisitPK, SetDefaultTerminationReasonForm form) {
        return CDI.current().select(SetDefaultTerminationReasonCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditTerminationReasonResult> editTerminationReason(UserVisitPK userVisitPK, EditTerminationReasonForm form) {
        return CDI.current().select(EditTerminationReasonCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteTerminationReason(UserVisitPK userVisitPK, DeleteTerminationReasonForm form) {
        return CDI.current().select(DeleteTerminationReasonCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Termination Reason Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createTerminationReasonDescription(UserVisitPK userVisitPK, CreateTerminationReasonDescriptionForm form) {
        return CDI.current().select(CreateTerminationReasonDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetTerminationReasonDescriptionsResult> getTerminationReasonDescriptions(UserVisitPK userVisitPK, GetTerminationReasonDescriptionsForm form) {
        return CDI.current().select(GetTerminationReasonDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditTerminationReasonDescriptionResult> editTerminationReasonDescription(UserVisitPK userVisitPK, EditTerminationReasonDescriptionForm form) {
        return CDI.current().select(EditTerminationReasonDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteTerminationReasonDescription(UserVisitPK userVisitPK, DeleteTerminationReasonDescriptionForm form) {
        return CDI.current().select(DeleteTerminationReasonDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Termination Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createTerminationType(UserVisitPK userVisitPK, CreateTerminationTypeForm form) {
        return CDI.current().select(CreateTerminationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTerminationTypesResult> getTerminationTypes(UserVisitPK userVisitPK, GetTerminationTypesForm form) {
        return CDI.current().select(GetTerminationTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTerminationTypeResult> getTerminationType(UserVisitPK userVisitPK, GetTerminationTypeForm form) {
        return CDI.current().select(GetTerminationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTerminationTypeChoicesResult> getTerminationTypeChoices(UserVisitPK userVisitPK, GetTerminationTypeChoicesForm form) {
        return CDI.current().select(GetTerminationTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultTerminationType(UserVisitPK userVisitPK, SetDefaultTerminationTypeForm form) {
        return CDI.current().select(SetDefaultTerminationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditTerminationTypeResult> editTerminationType(UserVisitPK userVisitPK, EditTerminationTypeForm form) {
        return CDI.current().select(EditTerminationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteTerminationType(UserVisitPK userVisitPK, DeleteTerminationTypeForm form) {
        return CDI.current().select(DeleteTerminationTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Termination Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createTerminationTypeDescription(UserVisitPK userVisitPK, CreateTerminationTypeDescriptionForm form) {
        return CDI.current().select(CreateTerminationTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetTerminationTypeDescriptionsResult> getTerminationTypeDescriptions(UserVisitPK userVisitPK, GetTerminationTypeDescriptionsForm form) {
        return CDI.current().select(GetTerminationTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditTerminationTypeDescriptionResult> editTerminationTypeDescription(UserVisitPK userVisitPK, EditTerminationTypeDescriptionForm form) {
        return CDI.current().select(EditTerminationTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteTerminationTypeDescription(UserVisitPK userVisitPK, DeleteTerminationTypeDescriptionForm form) {
        return CDI.current().select(DeleteTerminationTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Employments
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createEmployment(UserVisitPK userVisitPK, CreateEmploymentForm form) {
        return CDI.current().select(CreateEmploymentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetEmploymentsResult> getEmployments(UserVisitPK userVisitPK, GetEmploymentsForm form) {
        return CDI.current().select(GetEmploymentsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetEmploymentResult> getEmployment(UserVisitPK userVisitPK, GetEmploymentForm form) {
        return CDI.current().select(GetEmploymentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditEmploymentResult> editEmployment(UserVisitPK userVisitPK, EditEmploymentForm form) {
        return CDI.current().select(EditEmploymentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteEmployment(UserVisitPK userVisitPK, DeleteEmploymentForm form) {
        return CDI.current().select(DeleteEmploymentCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Responsibilities
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createPartyResponsibility(UserVisitPK userVisitPK, CreatePartyResponsibilityForm form) {
        return CDI.current().select(CreatePartyResponsibilityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetPartyResponsibilitiesResult> getPartyResponsibilities(UserVisitPK userVisitPK, GetPartyResponsibilitiesForm form) {
        return CDI.current().select(GetPartyResponsibilitiesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deletePartyResponsibility(UserVisitPK userVisitPK, DeletePartyResponsibilityForm form) {
        return CDI.current().select(DeletePartyResponsibilityCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Skills
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createPartySkill(UserVisitPK userVisitPK, CreatePartySkillForm form) {
        return CDI.current().select(CreatePartySkillCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetPartySkillsResult> getPartySkills(UserVisitPK userVisitPK, GetPartySkillsForm form) {
        return CDI.current().select(GetPartySkillsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deletePartySkill(UserVisitPK userVisitPK, DeletePartySkillForm form) {
        return CDI.current().select(DeletePartySkillCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Employee Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createEmployeeType(UserVisitPK userVisitPK, CreateEmployeeTypeForm form) {
        return CDI.current().select(CreateEmployeeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetEmployeeTypeChoicesResult> getEmployeeTypeChoices(UserVisitPK userVisitPK, GetEmployeeTypeChoicesForm form) {
        return CDI.current().select(GetEmployeeTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetEmployeeTypeResult> getEmployeeType(UserVisitPK userVisitPK, GetEmployeeTypeForm form) {
        return CDI.current().select(GetEmployeeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetEmployeeTypesResult> getEmployeeTypes(UserVisitPK userVisitPK, GetEmployeeTypesForm form) {
        return CDI.current().select(GetEmployeeTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultEmployeeType(UserVisitPK userVisitPK, SetDefaultEmployeeTypeForm form) {
        return CDI.current().select(SetDefaultEmployeeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditEmployeeTypeResult> editEmployeeType(UserVisitPK userVisitPK, EditEmployeeTypeForm form) {
        return CDI.current().select(EditEmployeeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteEmployeeType(UserVisitPK userVisitPK, DeleteEmployeeTypeForm form) {
        return CDI.current().select(DeleteEmployeeTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Employee Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createEmployeeTypeDescription(UserVisitPK userVisitPK, CreateEmployeeTypeDescriptionForm form) {
        return CDI.current().select(CreateEmployeeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetEmployeeTypeDescriptionsResult> getEmployeeTypeDescriptions(UserVisitPK userVisitPK, GetEmployeeTypeDescriptionsForm form) {
        return CDI.current().select(GetEmployeeTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditEmployeeTypeDescriptionResult> editEmployeeTypeDescription(UserVisitPK userVisitPK, EditEmployeeTypeDescriptionForm form) {
        return CDI.current().select(EditEmployeeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteEmployeeTypeDescription(UserVisitPK userVisitPK, DeleteEmployeeTypeDescriptionForm form) {
        return CDI.current().select(DeleteEmployeeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Employees
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<GetEmployeeResult> getEmployee(UserVisitPK userVisitPK, GetEmployeeForm form) {
        return CDI.current().select(GetEmployeeCommand.class).get().run(userVisitPK, form);
    }
    
}
