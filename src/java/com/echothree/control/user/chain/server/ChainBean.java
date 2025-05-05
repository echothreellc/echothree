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

package com.echothree.control.user.chain.server;

import com.echothree.control.user.chain.common.ChainRemote;
import com.echothree.control.user.chain.common.form.*;
import com.echothree.control.user.chain.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class ChainBean
        extends ChainFormsImpl
        implements ChainRemote, ChainLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "ChainBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Chain Action Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainActionType(UserVisitPK userVisitPK, CreateChainActionTypeForm form) {
        return new CreateChainActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainActionTypes(UserVisitPK userVisitPK, GetChainActionTypesForm form) {
        return new GetChainActionTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainActionType(UserVisitPK userVisitPK, GetChainActionTypeForm form) {
        return new GetChainActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainActionTypeChoices(UserVisitPK userVisitPK, GetChainActionTypeChoicesForm form) {
        return new GetChainActionTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultChainActionType(UserVisitPK userVisitPK, SetDefaultChainActionTypeForm form) {
        return new SetDefaultChainActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editChainActionType(UserVisitPK userVisitPK, EditChainActionTypeForm form) {
        return new EditChainActionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteChainActionType(UserVisitPK userVisitPK, DeleteChainActionTypeForm form) {
        return new DeleteChainActionTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Action Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainActionTypeDescription(UserVisitPK userVisitPK, CreateChainActionTypeDescriptionForm form) {
        return new CreateChainActionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainActionTypeDescriptions(UserVisitPK userVisitPK, GetChainActionTypeDescriptionsForm form) {
        return new GetChainActionTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainActionTypeDescription(UserVisitPK userVisitPK, GetChainActionTypeDescriptionForm form) {
        return new GetChainActionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editChainActionTypeDescription(UserVisitPK userVisitPK, EditChainActionTypeDescriptionForm form) {
        return new EditChainActionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteChainActionTypeDescription(UserVisitPK userVisitPK, DeleteChainActionTypeDescriptionForm form) {
        return new DeleteChainActionTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Chain Action Type Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createChainActionTypeUse(UserVisitPK userVisitPK, CreateChainActionTypeUseForm form) {
        return new CreateChainActionTypeUseCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Chain Kinds
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainKind(UserVisitPK userVisitPK, CreateChainKindForm form) {
        return new CreateChainKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainKinds(UserVisitPK userVisitPK, GetChainKindsForm form) {
        return new GetChainKindsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainKind(UserVisitPK userVisitPK, GetChainKindForm form) {
        return new GetChainKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainKindChoices(UserVisitPK userVisitPK, GetChainKindChoicesForm form) {
        return new GetChainKindChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultChainKind(UserVisitPK userVisitPK, SetDefaultChainKindForm form) {
        return new SetDefaultChainKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editChainKind(UserVisitPK userVisitPK, EditChainKindForm form) {
        return new EditChainKindCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteChainKind(UserVisitPK userVisitPK, DeleteChainKindForm form) {
        return new DeleteChainKindCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Kind Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainKindDescription(UserVisitPK userVisitPK, CreateChainKindDescriptionForm form) {
        return new CreateChainKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainKindDescriptions(UserVisitPK userVisitPK, GetChainKindDescriptionsForm form) {
        return new GetChainKindDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainKindDescription(UserVisitPK userVisitPK, GetChainKindDescriptionForm form) {
        return new GetChainKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editChainKindDescription(UserVisitPK userVisitPK, EditChainKindDescriptionForm form) {
        return new EditChainKindDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteChainKindDescription(UserVisitPK userVisitPK, DeleteChainKindDescriptionForm form) {
        return new DeleteChainKindDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainType(UserVisitPK userVisitPK, CreateChainTypeForm form) {
        return new CreateChainTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainTypes(UserVisitPK userVisitPK, GetChainTypesForm form) {
        return new GetChainTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainType(UserVisitPK userVisitPK, GetChainTypeForm form) {
        return new GetChainTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainTypeChoices(UserVisitPK userVisitPK, GetChainTypeChoicesForm form) {
        return new GetChainTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultChainType(UserVisitPK userVisitPK, SetDefaultChainTypeForm form) {
        return new SetDefaultChainTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editChainType(UserVisitPK userVisitPK, EditChainTypeForm form) {
        return new EditChainTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteChainType(UserVisitPK userVisitPK, DeleteChainTypeForm form) {
        return new DeleteChainTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainTypeDescription(UserVisitPK userVisitPK, CreateChainTypeDescriptionForm form) {
        return new CreateChainTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainTypeDescriptions(UserVisitPK userVisitPK, GetChainTypeDescriptionsForm form) {
        return new GetChainTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainTypeDescription(UserVisitPK userVisitPK, GetChainTypeDescriptionForm form) {
        return new GetChainTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editChainTypeDescription(UserVisitPK userVisitPK, EditChainTypeDescriptionForm form) {
        return new EditChainTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteChainTypeDescription(UserVisitPK userVisitPK, DeleteChainTypeDescriptionForm form) {
        return new DeleteChainTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Entity Role Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainEntityRoleType(UserVisitPK userVisitPK, CreateChainEntityRoleTypeForm form) {
        return new CreateChainEntityRoleTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainEntityRoleTypes(UserVisitPK userVisitPK, GetChainEntityRoleTypesForm form) {
        return new GetChainEntityRoleTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainEntityRoleType(UserVisitPK userVisitPK, GetChainEntityRoleTypeForm form) {
        return new GetChainEntityRoleTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editChainEntityRoleType(UserVisitPK userVisitPK, EditChainEntityRoleTypeForm form) {
        return new EditChainEntityRoleTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteChainEntityRoleType(UserVisitPK userVisitPK, DeleteChainEntityRoleTypeForm form) {
        return new DeleteChainEntityRoleTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Entity Role Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainEntityRoleTypeDescription(UserVisitPK userVisitPK, CreateChainEntityRoleTypeDescriptionForm form) {
        return new CreateChainEntityRoleTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainEntityRoleTypeDescriptions(UserVisitPK userVisitPK, GetChainEntityRoleTypeDescriptionsForm form) {
        return new GetChainEntityRoleTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainEntityRoleTypeDescription(UserVisitPK userVisitPK, GetChainEntityRoleTypeDescriptionForm form) {
        return new GetChainEntityRoleTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editChainEntityRoleTypeDescription(UserVisitPK userVisitPK, EditChainEntityRoleTypeDescriptionForm form) {
        return new EditChainEntityRoleTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteChainEntityRoleTypeDescription(UserVisitPK userVisitPK, DeleteChainEntityRoleTypeDescriptionForm form) {
        return new DeleteChainEntityRoleTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chains
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChain(UserVisitPK userVisitPK, CreateChainForm form) {
        return new CreateChainCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChains(UserVisitPK userVisitPK, GetChainsForm form) {
        return new GetChainsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChain(UserVisitPK userVisitPK, GetChainForm form) {
        return new GetChainCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainChoices(UserVisitPK userVisitPK, GetChainChoicesForm form) {
        return new GetChainChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultChain(UserVisitPK userVisitPK, SetDefaultChainForm form) {
        return new SetDefaultChainCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editChain(UserVisitPK userVisitPK, EditChainForm form) {
        return new EditChainCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteChain(UserVisitPK userVisitPK, DeleteChainForm form) {
        return new DeleteChainCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainDescription(UserVisitPK userVisitPK, CreateChainDescriptionForm form) {
        return new CreateChainDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainDescriptions(UserVisitPK userVisitPK, GetChainDescriptionsForm form) {
        return new GetChainDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainDescription(UserVisitPK userVisitPK, GetChainDescriptionForm form) {
        return new GetChainDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editChainDescription(UserVisitPK userVisitPK, EditChainDescriptionForm form) {
        return new EditChainDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteChainDescription(UserVisitPK userVisitPK, DeleteChainDescriptionForm form) {
        return new DeleteChainDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Action Sets
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainActionSet(UserVisitPK userVisitPK, CreateChainActionSetForm form) {
        return new CreateChainActionSetCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainActionSets(UserVisitPK userVisitPK, GetChainActionSetsForm form) {
        return new GetChainActionSetsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainActionSet(UserVisitPK userVisitPK, GetChainActionSetForm form) {
        return new GetChainActionSetCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainActionSetChoices(UserVisitPK userVisitPK, GetChainActionSetChoicesForm form) {
        return new GetChainActionSetChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultChainActionSet(UserVisitPK userVisitPK, SetDefaultChainActionSetForm form) {
        return new SetDefaultChainActionSetCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editChainActionSet(UserVisitPK userVisitPK, EditChainActionSetForm form) {
        return new EditChainActionSetCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteChainActionSet(UserVisitPK userVisitPK, DeleteChainActionSetForm form) {
        return new DeleteChainActionSetCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Action Set Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainActionSetDescription(UserVisitPK userVisitPK, CreateChainActionSetDescriptionForm form) {
        return new CreateChainActionSetDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainActionSetDescriptions(UserVisitPK userVisitPK, GetChainActionSetDescriptionsForm form) {
        return new GetChainActionSetDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainActionSetDescription(UserVisitPK userVisitPK, GetChainActionSetDescriptionForm form) {
        return new GetChainActionSetDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editChainActionSetDescription(UserVisitPK userVisitPK, EditChainActionSetDescriptionForm form) {
        return new EditChainActionSetDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteChainActionSetDescription(UserVisitPK userVisitPK, DeleteChainActionSetDescriptionForm form) {
        return new DeleteChainActionSetDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Actions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainAction(UserVisitPK userVisitPK, CreateChainActionForm form) {
        return new CreateChainActionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainActions(UserVisitPK userVisitPK, GetChainActionsForm form) {
        return new GetChainActionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainAction(UserVisitPK userVisitPK, GetChainActionForm form) {
        return new GetChainActionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editChainAction(UserVisitPK userVisitPK, EditChainActionForm form) {
        return new EditChainActionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteChainAction(UserVisitPK userVisitPK, DeleteChainActionForm form) {
        return new DeleteChainActionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Action Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainActionDescription(UserVisitPK userVisitPK, CreateChainActionDescriptionForm form) {
        return new CreateChainActionDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainActionDescriptions(UserVisitPK userVisitPK, GetChainActionDescriptionsForm form) {
        return new GetChainActionDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getChainActionDescription(UserVisitPK userVisitPK, GetChainActionDescriptionForm form) {
        return new GetChainActionDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editChainActionDescription(UserVisitPK userVisitPK, EditChainActionDescriptionForm form) {
        return new EditChainActionDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteChainActionDescription(UserVisitPK userVisitPK, DeleteChainActionDescriptionForm form) {
        return new DeleteChainActionDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Instances
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getChainInstance(UserVisitPK userVisitPK, GetChainInstanceForm form) {
        return new GetChainInstanceCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getChainInstances(UserVisitPK userVisitPK, GetChainInstancesForm form) {
        return new GetChainInstancesCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Chain Instance Statuses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult processChainInstanceStatuses(UserVisitPK userVisitPK) {
        return new ProcessChainInstanceStatusesCommand().run(userVisitPK);
    }
    
}
