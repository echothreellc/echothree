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

package com.echothree.control.user.chain.common;

import com.echothree.control.user.chain.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface ChainService
        extends ChainForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Chain Action Types
    // -------------------------------------------------------------------------

    CommandResult createChainActionType(UserVisitPK userVisitPK, CreateChainActionTypeForm form);

    CommandResult getChainActionTypes(UserVisitPK userVisitPK, GetChainActionTypesForm form);

    CommandResult getChainActionType(UserVisitPK userVisitPK, GetChainActionTypeForm form);

    CommandResult getChainActionTypeChoices(UserVisitPK userVisitPK, GetChainActionTypeChoicesForm form);

    CommandResult setDefaultChainActionType(UserVisitPK userVisitPK, SetDefaultChainActionTypeForm form);

    CommandResult editChainActionType(UserVisitPK userVisitPK, EditChainActionTypeForm form);

    CommandResult deleteChainActionType(UserVisitPK userVisitPK, DeleteChainActionTypeForm form);

    // -------------------------------------------------------------------------
    //   Chain Action Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createChainActionTypeDescription(UserVisitPK userVisitPK, CreateChainActionTypeDescriptionForm form);

    CommandResult getChainActionTypeDescriptions(UserVisitPK userVisitPK, GetChainActionTypeDescriptionsForm form);

    CommandResult getChainActionTypeDescription(UserVisitPK userVisitPK, GetChainActionTypeDescriptionForm form);

    CommandResult editChainActionTypeDescription(UserVisitPK userVisitPK, EditChainActionTypeDescriptionForm form);

    CommandResult deleteChainActionTypeDescription(UserVisitPK userVisitPK, DeleteChainActionTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Chain Action Type Uses
    // --------------------------------------------------------------------------------
    
    CommandResult createChainActionTypeUse(UserVisitPK userVisitPK, CreateChainActionTypeUseForm form);
    
    // -------------------------------------------------------------------------
    //   Chain Kinds
    // -------------------------------------------------------------------------

    CommandResult createChainKind(UserVisitPK userVisitPK, CreateChainKindForm form);

    CommandResult getChainKinds(UserVisitPK userVisitPK, GetChainKindsForm form);

    CommandResult getChainKind(UserVisitPK userVisitPK, GetChainKindForm form);

    CommandResult getChainKindChoices(UserVisitPK userVisitPK, GetChainKindChoicesForm form);

    CommandResult setDefaultChainKind(UserVisitPK userVisitPK, SetDefaultChainKindForm form);

    CommandResult editChainKind(UserVisitPK userVisitPK, EditChainKindForm form);

    CommandResult deleteChainKind(UserVisitPK userVisitPK, DeleteChainKindForm form);

    // -------------------------------------------------------------------------
    //   Chain Kind Descriptions
    // -------------------------------------------------------------------------

    CommandResult createChainKindDescription(UserVisitPK userVisitPK, CreateChainKindDescriptionForm form);

    CommandResult getChainKindDescriptions(UserVisitPK userVisitPK, GetChainKindDescriptionsForm form);

    CommandResult getChainKindDescription(UserVisitPK userVisitPK, GetChainKindDescriptionForm form);

    CommandResult editChainKindDescription(UserVisitPK userVisitPK, EditChainKindDescriptionForm form);

    CommandResult deleteChainKindDescription(UserVisitPK userVisitPK, DeleteChainKindDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Chain Types
    // -------------------------------------------------------------------------

    CommandResult createChainType(UserVisitPK userVisitPK, CreateChainTypeForm form);

    CommandResult getChainTypes(UserVisitPK userVisitPK, GetChainTypesForm form);

    CommandResult getChainType(UserVisitPK userVisitPK, GetChainTypeForm form);

    CommandResult getChainTypeChoices(UserVisitPK userVisitPK, GetChainTypeChoicesForm form);

    CommandResult setDefaultChainType(UserVisitPK userVisitPK, SetDefaultChainTypeForm form);

    CommandResult editChainType(UserVisitPK userVisitPK, EditChainTypeForm form);

    CommandResult deleteChainType(UserVisitPK userVisitPK, DeleteChainTypeForm form);

    // -------------------------------------------------------------------------
    //   Chain Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createChainTypeDescription(UserVisitPK userVisitPK, CreateChainTypeDescriptionForm form);

    CommandResult getChainTypeDescriptions(UserVisitPK userVisitPK, GetChainTypeDescriptionsForm form);

    CommandResult getChainTypeDescription(UserVisitPK userVisitPK, GetChainTypeDescriptionForm form);

    CommandResult editChainTypeDescription(UserVisitPK userVisitPK, EditChainTypeDescriptionForm form);

    CommandResult deleteChainTypeDescription(UserVisitPK userVisitPK, DeleteChainTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Chain Entity Role Types
    // -------------------------------------------------------------------------

    CommandResult createChainEntityRoleType(UserVisitPK userVisitPK, CreateChainEntityRoleTypeForm form);

    CommandResult getChainEntityRoleTypes(UserVisitPK userVisitPK, GetChainEntityRoleTypesForm form);

    CommandResult getChainEntityRoleType(UserVisitPK userVisitPK, GetChainEntityRoleTypeForm form);

    CommandResult editChainEntityRoleType(UserVisitPK userVisitPK, EditChainEntityRoleTypeForm form);

    CommandResult deleteChainEntityRoleType(UserVisitPK userVisitPK, DeleteChainEntityRoleTypeForm form);

    // -------------------------------------------------------------------------
    //   Chain Entity Role Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createChainEntityRoleTypeDescription(UserVisitPK userVisitPK, CreateChainEntityRoleTypeDescriptionForm form);

    CommandResult getChainEntityRoleTypeDescriptions(UserVisitPK userVisitPK, GetChainEntityRoleTypeDescriptionsForm form);

    CommandResult getChainEntityRoleTypeDescription(UserVisitPK userVisitPK, GetChainEntityRoleTypeDescriptionForm form);

    CommandResult editChainEntityRoleTypeDescription(UserVisitPK userVisitPK, EditChainEntityRoleTypeDescriptionForm form);

    CommandResult deleteChainEntityRoleTypeDescription(UserVisitPK userVisitPK, DeleteChainEntityRoleTypeDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Chains
    // -------------------------------------------------------------------------

    CommandResult createChain(UserVisitPK userVisitPK, CreateChainForm form);

    CommandResult getChains(UserVisitPK userVisitPK, GetChainsForm form);

    CommandResult getChain(UserVisitPK userVisitPK, GetChainForm form);

    CommandResult getChainChoices(UserVisitPK userVisitPK, GetChainChoicesForm form);

    CommandResult setDefaultChain(UserVisitPK userVisitPK, SetDefaultChainForm form);

    CommandResult editChain(UserVisitPK userVisitPK, EditChainForm form);

    CommandResult deleteChain(UserVisitPK userVisitPK, DeleteChainForm form);

    // -------------------------------------------------------------------------
    //   Chain Descriptions
    // -------------------------------------------------------------------------

    CommandResult createChainDescription(UserVisitPK userVisitPK, CreateChainDescriptionForm form);

    CommandResult getChainDescriptions(UserVisitPK userVisitPK, GetChainDescriptionsForm form);

    CommandResult getChainDescription(UserVisitPK userVisitPK, GetChainDescriptionForm form);

    CommandResult editChainDescription(UserVisitPK userVisitPK, EditChainDescriptionForm form);

    CommandResult deleteChainDescription(UserVisitPK userVisitPK, DeleteChainDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Chain Action Sets
    // -------------------------------------------------------------------------

    CommandResult createChainActionSet(UserVisitPK userVisitPK, CreateChainActionSetForm form);

    CommandResult getChainActionSets(UserVisitPK userVisitPK, GetChainActionSetsForm form);

    CommandResult getChainActionSet(UserVisitPK userVisitPK, GetChainActionSetForm form);

    CommandResult getChainActionSetChoices(UserVisitPK userVisitPK, GetChainActionSetChoicesForm form);

    CommandResult setDefaultChainActionSet(UserVisitPK userVisitPK, SetDefaultChainActionSetForm form);

    CommandResult editChainActionSet(UserVisitPK userVisitPK, EditChainActionSetForm form);

    CommandResult deleteChainActionSet(UserVisitPK userVisitPK, DeleteChainActionSetForm form);

    // -------------------------------------------------------------------------
    //   Chain Action Set Descriptions
    // -------------------------------------------------------------------------

    CommandResult createChainActionSetDescription(UserVisitPK userVisitPK, CreateChainActionSetDescriptionForm form);

    CommandResult getChainActionSetDescriptions(UserVisitPK userVisitPK, GetChainActionSetDescriptionsForm form);

    CommandResult getChainActionSetDescription(UserVisitPK userVisitPK, GetChainActionSetDescriptionForm form);

    CommandResult editChainActionSetDescription(UserVisitPK userVisitPK, EditChainActionSetDescriptionForm form);

    CommandResult deleteChainActionSetDescription(UserVisitPK userVisitPK, DeleteChainActionSetDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Chain Actions
    // -------------------------------------------------------------------------
    
    CommandResult createChainAction(UserVisitPK userVisitPK, CreateChainActionForm form);
    
    CommandResult getChainActions(UserVisitPK userVisitPK, GetChainActionsForm form);
    
    CommandResult getChainAction(UserVisitPK userVisitPK, GetChainActionForm form);
    
    CommandResult editChainAction(UserVisitPK userVisitPK, EditChainActionForm form);
    
    CommandResult deleteChainAction(UserVisitPK userVisitPK, DeleteChainActionForm form);
    
    // -------------------------------------------------------------------------
    //   Chain Action Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createChainActionDescription(UserVisitPK userVisitPK, CreateChainActionDescriptionForm form);
    
    CommandResult getChainActionDescriptions(UserVisitPK userVisitPK, GetChainActionDescriptionsForm form);
    
    CommandResult getChainActionDescription(UserVisitPK userVisitPK, GetChainActionDescriptionForm form);
    
    CommandResult editChainActionDescription(UserVisitPK userVisitPK, EditChainActionDescriptionForm form);
    
    CommandResult deleteChainActionDescription(UserVisitPK userVisitPK, DeleteChainActionDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Chain Instances
    // -------------------------------------------------------------------------
    
    CommandResult getChainInstance(UserVisitPK userVisitPK, GetChainInstanceForm form);
    
    CommandResult getChainInstances(UserVisitPK userVisitPK, GetChainInstancesForm form);
    
    // -------------------------------------------------------------------------
    //   Chain Instance Statuses
    // -------------------------------------------------------------------------
    
    CommandResult processChainInstanceStatuses(UserVisitPK userVisitPK);
    
}
