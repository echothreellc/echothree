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

package com.echothree.control.user.workeffort.server;

import com.echothree.control.user.workeffort.common.WorkEffortRemote;
import com.echothree.control.user.workeffort.common.form.*;
import com.echothree.control.user.workeffort.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class WorkEffortBean
        extends WorkEffortFormsImpl
        implements WorkEffortRemote, WorkEffortLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "WorkEffortBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Work Effort Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkEffortType(UserVisitPK userVisitPK, CreateWorkEffortTypeForm form) {
        return new CreateWorkEffortTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkEffortTypes(UserVisitPK userVisitPK, GetWorkEffortTypesForm form) {
        return new GetWorkEffortTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkEffortType(UserVisitPK userVisitPK, GetWorkEffortTypeForm form) {
        return new GetWorkEffortTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkEffortType(UserVisitPK userVisitPK, DeleteWorkEffortTypeForm form) {
        return new DeleteWorkEffortTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Work Effort Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createWorkEffortTypeDescription(UserVisitPK userVisitPK, CreateWorkEffortTypeDescriptionForm form) {
        return new CreateWorkEffortTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWorkEffortTypeDescriptions(UserVisitPK userVisitPK, GetWorkEffortTypeDescriptionsForm form) {
        return new GetWorkEffortTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editWorkEffortTypeDescription(UserVisitPK userVisitPK, EditWorkEffortTypeDescriptionForm form) {
        return new EditWorkEffortTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteWorkEffortTypeDescription(UserVisitPK userVisitPK, DeleteWorkEffortTypeDescriptionForm form) {
        return new DeleteWorkEffortTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Work Effort Scopes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkEffortScope(UserVisitPK userVisitPK, CreateWorkEffortScopeForm form) {
        return new CreateWorkEffortScopeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkEffortScopes(UserVisitPK userVisitPK, GetWorkEffortScopesForm form) {
        return new GetWorkEffortScopesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkEffortScope(UserVisitPK userVisitPK, GetWorkEffortScopeForm form) {
        return new GetWorkEffortScopeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkEffortScopeChoices(UserVisitPK userVisitPK, GetWorkEffortScopeChoicesForm form) {
        return new GetWorkEffortScopeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultWorkEffortScope(UserVisitPK userVisitPK, SetDefaultWorkEffortScopeForm form) {
        return new SetDefaultWorkEffortScopeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWorkEffortScope(UserVisitPK userVisitPK, EditWorkEffortScopeForm form) {
        return new EditWorkEffortScopeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteWorkEffortScope(UserVisitPK userVisitPK, DeleteWorkEffortScopeForm form) {
        return new DeleteWorkEffortScopeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Work Effort Scope Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkEffortScopeDescription(UserVisitPK userVisitPK, CreateWorkEffortScopeDescriptionForm form) {
        return new CreateWorkEffortScopeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkEffortScopeDescription(UserVisitPK userVisitPK, GetWorkEffortScopeDescriptionForm form) {
        return new GetWorkEffortScopeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWorkEffortScopeDescriptions(UserVisitPK userVisitPK, GetWorkEffortScopeDescriptionsForm form) {
        return new GetWorkEffortScopeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editWorkEffortScopeDescription(UserVisitPK userVisitPK, EditWorkEffortScopeDescriptionForm form) {
        return new EditWorkEffortScopeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkEffortScopeDescription(UserVisitPK userVisitPK, DeleteWorkEffortScopeDescriptionForm form) {
        return new DeleteWorkEffortScopeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Work Effort
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getWorkEffort(UserVisitPK userVisitPK, GetWorkEffortForm form) {
        return new GetWorkEffortCommand().run(userVisitPK, form);
    }
    
}
