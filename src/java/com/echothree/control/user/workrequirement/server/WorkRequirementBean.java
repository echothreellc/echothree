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

package com.echothree.control.user.workrequirement.server;

import com.echothree.control.user.workrequirement.common.WorkRequirementRemote;
import com.echothree.control.user.workrequirement.common.form.*;
import com.echothree.control.user.workrequirement.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class WorkRequirementBean
        extends WorkRequirementFormsImpl
        implements WorkRequirementRemote, WorkRequirementLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "WorkRequirementBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Work Requirement Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkRequirementType(UserVisitPK userVisitPK, CreateWorkRequirementTypeForm form) {
        return new CreateWorkRequirementTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkRequirementTypes(UserVisitPK userVisitPK, GetWorkRequirementTypesForm form) {
        return new GetWorkRequirementTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkRequirementType(UserVisitPK userVisitPK, GetWorkRequirementTypeForm form) {
        return new GetWorkRequirementTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkRequirementType(UserVisitPK userVisitPK, DeleteWorkRequirementTypeForm form) {
        return new DeleteWorkRequirementTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Work Requirement Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkRequirementTypeDescription(UserVisitPK userVisitPK, CreateWorkRequirementTypeDescriptionForm form) {
        return new CreateWorkRequirementTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkRequirementTypeDescriptions(UserVisitPK userVisitPK, GetWorkRequirementTypeDescriptionsForm form) {
        return new GetWorkRequirementTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWorkRequirementTypeDescription(UserVisitPK userVisitPK, EditWorkRequirementTypeDescriptionForm form) {
        return new EditWorkRequirementTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkRequirementTypeDescription(UserVisitPK userVisitPK, DeleteWorkRequirementTypeDescriptionForm form) {
        return new DeleteWorkRequirementTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Work Requirement Scopes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getWorkRequirementScopes(UserVisitPK userVisitPK, GetWorkRequirementScopesForm form) {
        return new GetWorkRequirementScopesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkRequirementScope(UserVisitPK userVisitPK, GetWorkRequirementScopeForm form) {
        return new GetWorkRequirementScopeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Work Requirements
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getWorkRequirement(UserVisitPK userVisitPK, GetWorkRequirementForm form) {
        return new GetWorkRequirementCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Work Assignments
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getWorkAssignments(UserVisitPK userVisitPK, GetWorkAssignmentsForm form) {
        return new GetWorkAssignmentsCommand().run(userVisitPK, form);
    }

}
