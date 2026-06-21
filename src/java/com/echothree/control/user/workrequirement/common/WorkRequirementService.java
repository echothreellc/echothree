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

package com.echothree.control.user.workrequirement.common;

import com.echothree.control.user.workrequirement.common.form.*;
import com.echothree.control.user.workrequirement.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface WorkRequirementService
        extends WorkRequirementForms {
    
    // -------------------------------------------------------------------------
    //   Work Requirement Types
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkRequirementType(UserVisitPK userVisitPK, CreateWorkRequirementTypeForm form);
    
    CommandResult<GetWorkRequirementTypesResult> getWorkRequirementTypes(UserVisitPK userVisitPK, GetWorkRequirementTypesForm form);
    
    CommandResult<GetWorkRequirementTypeResult> getWorkRequirementType(UserVisitPK userVisitPK, GetWorkRequirementTypeForm form);
    
    CommandResult<VoidResult> deleteWorkRequirementType(UserVisitPK userVisitPK, DeleteWorkRequirementTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Work Requirement Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<VoidResult> createWorkRequirementTypeDescription(UserVisitPK userVisitPK, CreateWorkRequirementTypeDescriptionForm form);
    
    CommandResult<GetWorkRequirementTypeDescriptionsResult> getWorkRequirementTypeDescriptions(UserVisitPK userVisitPK, GetWorkRequirementTypeDescriptionsForm form);
    
    CommandResult<EditWorkRequirementTypeDescriptionResult> editWorkRequirementTypeDescription(UserVisitPK userVisitPK, EditWorkRequirementTypeDescriptionForm form);
    
    CommandResult<VoidResult> deleteWorkRequirementTypeDescription(UserVisitPK userVisitPK, DeleteWorkRequirementTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Work Requirement Scopes
    // -------------------------------------------------------------------------
    
    CommandResult<GetWorkRequirementScopesResult> getWorkRequirementScopes(UserVisitPK userVisitPK, GetWorkRequirementScopesForm form);
    
    CommandResult<GetWorkRequirementScopeResult> getWorkRequirementScope(UserVisitPK userVisitPK, GetWorkRequirementScopeForm form);
    
    // -------------------------------------------------------------------------
    //   Work Requirements
    // -------------------------------------------------------------------------
    
    CommandResult<GetWorkRequirementResult> getWorkRequirement(UserVisitPK userVisitPK, GetWorkRequirementForm form);
    
    // -------------------------------------------------------------------------
    //   Work Assignments
    // -------------------------------------------------------------------------

    public CommandResult<GetWorkAssignmentsResult> getWorkAssignments(UserVisitPK userVisitPK, GetWorkAssignmentsForm form);

    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
}
