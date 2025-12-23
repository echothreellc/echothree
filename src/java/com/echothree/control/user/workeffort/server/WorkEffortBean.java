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

package com.echothree.control.user.workeffort.server;

import com.echothree.control.user.workeffort.common.WorkEffortRemote;
import com.echothree.control.user.workeffort.common.form.*;
import com.echothree.control.user.workeffort.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateWorkEffortTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkEffortTypes(UserVisitPK userVisitPK, GetWorkEffortTypesForm form) {
        return CDI.current().select(GetWorkEffortTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkEffortType(UserVisitPK userVisitPK, GetWorkEffortTypeForm form) {
        return CDI.current().select(GetWorkEffortTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkEffortType(UserVisitPK userVisitPK, DeleteWorkEffortTypeForm form) {
        return CDI.current().select(DeleteWorkEffortTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Work Effort Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createWorkEffortTypeDescription(UserVisitPK userVisitPK, CreateWorkEffortTypeDescriptionForm form) {
        return CDI.current().select(CreateWorkEffortTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWorkEffortTypeDescriptions(UserVisitPK userVisitPK, GetWorkEffortTypeDescriptionsForm form) {
        return CDI.current().select(GetWorkEffortTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editWorkEffortTypeDescription(UserVisitPK userVisitPK, EditWorkEffortTypeDescriptionForm form) {
        return CDI.current().select(EditWorkEffortTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteWorkEffortTypeDescription(UserVisitPK userVisitPK, DeleteWorkEffortTypeDescriptionForm form) {
        return CDI.current().select(DeleteWorkEffortTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Work Effort Scopes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkEffortScope(UserVisitPK userVisitPK, CreateWorkEffortScopeForm form) {
        return CDI.current().select(CreateWorkEffortScopeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkEffortScopes(UserVisitPK userVisitPK, GetWorkEffortScopesForm form) {
        return CDI.current().select(GetWorkEffortScopesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkEffortScope(UserVisitPK userVisitPK, GetWorkEffortScopeForm form) {
        return CDI.current().select(GetWorkEffortScopeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkEffortScopeChoices(UserVisitPK userVisitPK, GetWorkEffortScopeChoicesForm form) {
        return CDI.current().select(GetWorkEffortScopeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultWorkEffortScope(UserVisitPK userVisitPK, SetDefaultWorkEffortScopeForm form) {
        return CDI.current().select(SetDefaultWorkEffortScopeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editWorkEffortScope(UserVisitPK userVisitPK, EditWorkEffortScopeForm form) {
        return CDI.current().select(EditWorkEffortScopeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteWorkEffortScope(UserVisitPK userVisitPK, DeleteWorkEffortScopeForm form) {
        return CDI.current().select(DeleteWorkEffortScopeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Work Effort Scope Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createWorkEffortScopeDescription(UserVisitPK userVisitPK, CreateWorkEffortScopeDescriptionForm form) {
        return CDI.current().select(CreateWorkEffortScopeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getWorkEffortScopeDescription(UserVisitPK userVisitPK, GetWorkEffortScopeDescriptionForm form) {
        return CDI.current().select(GetWorkEffortScopeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getWorkEffortScopeDescriptions(UserVisitPK userVisitPK, GetWorkEffortScopeDescriptionsForm form) {
        return CDI.current().select(GetWorkEffortScopeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editWorkEffortScopeDescription(UserVisitPK userVisitPK, EditWorkEffortScopeDescriptionForm form) {
        return CDI.current().select(EditWorkEffortScopeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteWorkEffortScopeDescription(UserVisitPK userVisitPK, DeleteWorkEffortScopeDescriptionForm form) {
        return CDI.current().select(DeleteWorkEffortScopeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Work Effort
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getWorkEffort(UserVisitPK userVisitPK, GetWorkEffortForm form) {
        return CDI.current().select(GetWorkEffortCommand.class).get().run(userVisitPK, form);
    }
    
}
