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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface WorkRequirementService
        extends WorkRequirementForms {
    
    // -------------------------------------------------------------------------
    //   Work Requirement Types
    // -------------------------------------------------------------------------
    
    CommandResult createWorkRequirementType(UserVisitPK userVisitPK, CreateWorkRequirementTypeForm form);
    
    CommandResult getWorkRequirementTypes(UserVisitPK userVisitPK, GetWorkRequirementTypesForm form);
    
    CommandResult getWorkRequirementType(UserVisitPK userVisitPK, GetWorkRequirementTypeForm form);
    
    CommandResult deleteWorkRequirementType(UserVisitPK userVisitPK, DeleteWorkRequirementTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Work Requirement Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createWorkRequirementTypeDescription(UserVisitPK userVisitPK, CreateWorkRequirementTypeDescriptionForm form);
    
    CommandResult getWorkRequirementTypeDescriptions(UserVisitPK userVisitPK, GetWorkRequirementTypeDescriptionsForm form);
    
    CommandResult editWorkRequirementTypeDescription(UserVisitPK userVisitPK, EditWorkRequirementTypeDescriptionForm form);
    
    CommandResult deleteWorkRequirementTypeDescription(UserVisitPK userVisitPK, DeleteWorkRequirementTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Work Requirement Scopes
    // -------------------------------------------------------------------------
    
    CommandResult getWorkRequirementScopes(UserVisitPK userVisitPK, GetWorkRequirementScopesForm form);
    
    CommandResult getWorkRequirementScope(UserVisitPK userVisitPK, GetWorkRequirementScopeForm form);
    
    // -------------------------------------------------------------------------
    //   Work Requirements
    // -------------------------------------------------------------------------
    
    CommandResult getWorkRequirement(UserVisitPK userVisitPK, GetWorkRequirementForm form);
    
    // -------------------------------------------------------------------------
    //   Work Assignments
    // -------------------------------------------------------------------------

    public CommandResult getWorkAssignments(UserVisitPK userVisitPK, GetWorkAssignmentsForm form);

    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
}
