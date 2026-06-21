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

package com.echothree.control.user.workeffort.common;

import com.echothree.control.user.workeffort.common.form.*;
import com.echothree.control.user.workeffort.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface WorkEffortService
        extends WorkEffortForms {
    
    // -------------------------------------------------------------------------
    //   Work Effort Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createWorkEffortType(UserVisitPK userVisitPK, CreateWorkEffortTypeForm form);
    
    CommandResult<GetWorkEffortTypesResult> getWorkEffortTypes(UserVisitPK userVisitPK, GetWorkEffortTypesForm form);
    
    CommandResult<GetWorkEffortTypeResult> getWorkEffortType(UserVisitPK userVisitPK, GetWorkEffortTypeForm form);
    
    CommandResult<?> deleteWorkEffortType(UserVisitPK userVisitPK, DeleteWorkEffortTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Work Effort Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createWorkEffortTypeDescription(UserVisitPK userVisitPK, CreateWorkEffortTypeDescriptionForm form);

    CommandResult<GetWorkEffortTypeDescriptionsResult> getWorkEffortTypeDescriptions(UserVisitPK userVisitPK, GetWorkEffortTypeDescriptionsForm form);

    CommandResult<EditWorkEffortTypeDescriptionResult> editWorkEffortTypeDescription(UserVisitPK userVisitPK, EditWorkEffortTypeDescriptionForm form);

    CommandResult<?> deleteWorkEffortTypeDescription(UserVisitPK userVisitPK, DeleteWorkEffortTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Work Effort Scopes
    // -------------------------------------------------------------------------
    
    CommandResult<?> createWorkEffortScope(UserVisitPK userVisitPK, CreateWorkEffortScopeForm form);
    
    CommandResult<GetWorkEffortScopesResult> getWorkEffortScopes(UserVisitPK userVisitPK, GetWorkEffortScopesForm form);
    
    CommandResult<GetWorkEffortScopeResult> getWorkEffortScope(UserVisitPK userVisitPK, GetWorkEffortScopeForm form);
    
    CommandResult<GetWorkEffortScopeChoicesResult> getWorkEffortScopeChoices(UserVisitPK userVisitPK, GetWorkEffortScopeChoicesForm form);
    
    CommandResult<?> setDefaultWorkEffortScope(UserVisitPK userVisitPK, SetDefaultWorkEffortScopeForm form);
    
    CommandResult<EditWorkEffortScopeResult> editWorkEffortScope(UserVisitPK userVisitPK, EditWorkEffortScopeForm form);

    CommandResult<?> deleteWorkEffortScope(UserVisitPK userVisitPK, DeleteWorkEffortScopeForm form);

    // -------------------------------------------------------------------------
    //   Work Effort Scope Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createWorkEffortScopeDescription(UserVisitPK userVisitPK, CreateWorkEffortScopeDescriptionForm form);
    
    CommandResult<GetWorkEffortScopeDescriptionResult> getWorkEffortScopeDescription(UserVisitPK userVisitPK, GetWorkEffortScopeDescriptionForm form);

    CommandResult<GetWorkEffortScopeDescriptionsResult> getWorkEffortScopeDescriptions(UserVisitPK userVisitPK, GetWorkEffortScopeDescriptionsForm form);

    CommandResult<EditWorkEffortScopeDescriptionResult> editWorkEffortScopeDescription(UserVisitPK userVisitPK, EditWorkEffortScopeDescriptionForm form);
    
    CommandResult<?> deleteWorkEffortScopeDescription(UserVisitPK userVisitPK, DeleteWorkEffortScopeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Work Effort
    // -------------------------------------------------------------------------
    
    CommandResult<GetWorkEffortResult> getWorkEffort(UserVisitPK userVisitPK, GetWorkEffortForm form);
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
        
}
