// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
        return new CreateWorkRequirementTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkRequirementTypes(UserVisitPK userVisitPK, GetWorkRequirementTypesForm form) {
        return new GetWorkRequirementTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkRequirementType(UserVisitPK userVisitPK, GetWorkRequirementTypeForm form) {
        return new GetWorkRequirementTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkRequirementType(UserVisitPK userVisitPK, DeleteWorkRequirementTypeForm form) {
        return new DeleteWorkRequirementTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Work Requirement Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkRequirementTypeDescription(UserVisitPK userVisitPK, CreateWorkRequirementTypeDescriptionForm form) {
        return new CreateWorkRequirementTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkRequirementTypeDescriptions(UserVisitPK userVisitPK, GetWorkRequirementTypeDescriptionsForm form) {
        return new GetWorkRequirementTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editWorkRequirementTypeDescription(UserVisitPK userVisitPK, EditWorkRequirementTypeDescriptionForm form) {
        return new EditWorkRequirementTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteWorkRequirementTypeDescription(UserVisitPK userVisitPK, DeleteWorkRequirementTypeDescriptionForm form) {
        return new DeleteWorkRequirementTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Work Requirement Scopes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getWorkRequirementScopes(UserVisitPK userVisitPK, GetWorkRequirementScopesForm form) {
        return new GetWorkRequirementScopesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getWorkRequirementScope(UserVisitPK userVisitPK, GetWorkRequirementScopeForm form) {
        return new GetWorkRequirementScopeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Work Requirements
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getWorkRequirement(UserVisitPK userVisitPK, GetWorkRequirementForm form) {
        return new GetWorkRequirementCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Work Assignments
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getWorkAssignments(UserVisitPK userVisitPK, GetWorkAssignmentsForm form) {
        return new GetWorkAssignmentsCommand(userVisitPK, form).run();
    }

}
