// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
        return new CreateChainActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainActionTypes(UserVisitPK userVisitPK, GetChainActionTypesForm form) {
        return new GetChainActionTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainActionType(UserVisitPK userVisitPK, GetChainActionTypeForm form) {
        return new GetChainActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainActionTypeChoices(UserVisitPK userVisitPK, GetChainActionTypeChoicesForm form) {
        return new GetChainActionTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultChainActionType(UserVisitPK userVisitPK, SetDefaultChainActionTypeForm form) {
        return new SetDefaultChainActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editChainActionType(UserVisitPK userVisitPK, EditChainActionTypeForm form) {
        return new EditChainActionTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteChainActionType(UserVisitPK userVisitPK, DeleteChainActionTypeForm form) {
        return new DeleteChainActionTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Chain Action Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainActionTypeDescription(UserVisitPK userVisitPK, CreateChainActionTypeDescriptionForm form) {
        return new CreateChainActionTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainActionTypeDescriptions(UserVisitPK userVisitPK, GetChainActionTypeDescriptionsForm form) {
        return new GetChainActionTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainActionTypeDescription(UserVisitPK userVisitPK, GetChainActionTypeDescriptionForm form) {
        return new GetChainActionTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editChainActionTypeDescription(UserVisitPK userVisitPK, EditChainActionTypeDescriptionForm form) {
        return new EditChainActionTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteChainActionTypeDescription(UserVisitPK userVisitPK, DeleteChainActionTypeDescriptionForm form) {
        return new DeleteChainActionTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Chain Action Type Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createChainActionTypeUse(UserVisitPK userVisitPK, CreateChainActionTypeUseForm form) {
        return new CreateChainActionTypeUseCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Chain Kinds
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainKind(UserVisitPK userVisitPK, CreateChainKindForm form) {
        return new CreateChainKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainKinds(UserVisitPK userVisitPK, GetChainKindsForm form) {
        return new GetChainKindsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainKind(UserVisitPK userVisitPK, GetChainKindForm form) {
        return new GetChainKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainKindChoices(UserVisitPK userVisitPK, GetChainKindChoicesForm form) {
        return new GetChainKindChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultChainKind(UserVisitPK userVisitPK, SetDefaultChainKindForm form) {
        return new SetDefaultChainKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editChainKind(UserVisitPK userVisitPK, EditChainKindForm form) {
        return new EditChainKindCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteChainKind(UserVisitPK userVisitPK, DeleteChainKindForm form) {
        return new DeleteChainKindCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Chain Kind Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainKindDescription(UserVisitPK userVisitPK, CreateChainKindDescriptionForm form) {
        return new CreateChainKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainKindDescriptions(UserVisitPK userVisitPK, GetChainKindDescriptionsForm form) {
        return new GetChainKindDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainKindDescription(UserVisitPK userVisitPK, GetChainKindDescriptionForm form) {
        return new GetChainKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editChainKindDescription(UserVisitPK userVisitPK, EditChainKindDescriptionForm form) {
        return new EditChainKindDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteChainKindDescription(UserVisitPK userVisitPK, DeleteChainKindDescriptionForm form) {
        return new DeleteChainKindDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Chain Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainType(UserVisitPK userVisitPK, CreateChainTypeForm form) {
        return new CreateChainTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainTypes(UserVisitPK userVisitPK, GetChainTypesForm form) {
        return new GetChainTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainType(UserVisitPK userVisitPK, GetChainTypeForm form) {
        return new GetChainTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainTypeChoices(UserVisitPK userVisitPK, GetChainTypeChoicesForm form) {
        return new GetChainTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultChainType(UserVisitPK userVisitPK, SetDefaultChainTypeForm form) {
        return new SetDefaultChainTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editChainType(UserVisitPK userVisitPK, EditChainTypeForm form) {
        return new EditChainTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteChainType(UserVisitPK userVisitPK, DeleteChainTypeForm form) {
        return new DeleteChainTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Chain Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainTypeDescription(UserVisitPK userVisitPK, CreateChainTypeDescriptionForm form) {
        return new CreateChainTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainTypeDescriptions(UserVisitPK userVisitPK, GetChainTypeDescriptionsForm form) {
        return new GetChainTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainTypeDescription(UserVisitPK userVisitPK, GetChainTypeDescriptionForm form) {
        return new GetChainTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editChainTypeDescription(UserVisitPK userVisitPK, EditChainTypeDescriptionForm form) {
        return new EditChainTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteChainTypeDescription(UserVisitPK userVisitPK, DeleteChainTypeDescriptionForm form) {
        return new DeleteChainTypeDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Chain Entity Role Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainEntityRoleType(UserVisitPK userVisitPK, CreateChainEntityRoleTypeForm form) {
        return new CreateChainEntityRoleTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainEntityRoleTypes(UserVisitPK userVisitPK, GetChainEntityRoleTypesForm form) {
        return new GetChainEntityRoleTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainEntityRoleType(UserVisitPK userVisitPK, GetChainEntityRoleTypeForm form) {
        return new GetChainEntityRoleTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editChainEntityRoleType(UserVisitPK userVisitPK, EditChainEntityRoleTypeForm form) {
        return new EditChainEntityRoleTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteChainEntityRoleType(UserVisitPK userVisitPK, DeleteChainEntityRoleTypeForm form) {
        return new DeleteChainEntityRoleTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Chain Entity Role Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainEntityRoleTypeDescription(UserVisitPK userVisitPK, CreateChainEntityRoleTypeDescriptionForm form) {
        return new CreateChainEntityRoleTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainEntityRoleTypeDescriptions(UserVisitPK userVisitPK, GetChainEntityRoleTypeDescriptionsForm form) {
        return new GetChainEntityRoleTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainEntityRoleTypeDescription(UserVisitPK userVisitPK, GetChainEntityRoleTypeDescriptionForm form) {
        return new GetChainEntityRoleTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editChainEntityRoleTypeDescription(UserVisitPK userVisitPK, EditChainEntityRoleTypeDescriptionForm form) {
        return new EditChainEntityRoleTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteChainEntityRoleTypeDescription(UserVisitPK userVisitPK, DeleteChainEntityRoleTypeDescriptionForm form) {
        return new DeleteChainEntityRoleTypeDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Chains
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChain(UserVisitPK userVisitPK, CreateChainForm form) {
        return new CreateChainCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChains(UserVisitPK userVisitPK, GetChainsForm form) {
        return new GetChainsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChain(UserVisitPK userVisitPK, GetChainForm form) {
        return new GetChainCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainChoices(UserVisitPK userVisitPK, GetChainChoicesForm form) {
        return new GetChainChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultChain(UserVisitPK userVisitPK, SetDefaultChainForm form) {
        return new SetDefaultChainCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editChain(UserVisitPK userVisitPK, EditChainForm form) {
        return new EditChainCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteChain(UserVisitPK userVisitPK, DeleteChainForm form) {
        return new DeleteChainCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Chain Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainDescription(UserVisitPK userVisitPK, CreateChainDescriptionForm form) {
        return new CreateChainDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainDescriptions(UserVisitPK userVisitPK, GetChainDescriptionsForm form) {
        return new GetChainDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainDescription(UserVisitPK userVisitPK, GetChainDescriptionForm form) {
        return new GetChainDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editChainDescription(UserVisitPK userVisitPK, EditChainDescriptionForm form) {
        return new EditChainDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteChainDescription(UserVisitPK userVisitPK, DeleteChainDescriptionForm form) {
        return new DeleteChainDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Chain Action Sets
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainActionSet(UserVisitPK userVisitPK, CreateChainActionSetForm form) {
        return new CreateChainActionSetCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainActionSets(UserVisitPK userVisitPK, GetChainActionSetsForm form) {
        return new GetChainActionSetsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainActionSet(UserVisitPK userVisitPK, GetChainActionSetForm form) {
        return new GetChainActionSetCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainActionSetChoices(UserVisitPK userVisitPK, GetChainActionSetChoicesForm form) {
        return new GetChainActionSetChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultChainActionSet(UserVisitPK userVisitPK, SetDefaultChainActionSetForm form) {
        return new SetDefaultChainActionSetCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editChainActionSet(UserVisitPK userVisitPK, EditChainActionSetForm form) {
        return new EditChainActionSetCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteChainActionSet(UserVisitPK userVisitPK, DeleteChainActionSetForm form) {
        return new DeleteChainActionSetCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Chain Action Set Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainActionSetDescription(UserVisitPK userVisitPK, CreateChainActionSetDescriptionForm form) {
        return new CreateChainActionSetDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainActionSetDescriptions(UserVisitPK userVisitPK, GetChainActionSetDescriptionsForm form) {
        return new GetChainActionSetDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainActionSetDescription(UserVisitPK userVisitPK, GetChainActionSetDescriptionForm form) {
        return new GetChainActionSetDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editChainActionSetDescription(UserVisitPK userVisitPK, EditChainActionSetDescriptionForm form) {
        return new EditChainActionSetDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteChainActionSetDescription(UserVisitPK userVisitPK, DeleteChainActionSetDescriptionForm form) {
        return new DeleteChainActionSetDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Chain Actions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainAction(UserVisitPK userVisitPK, CreateChainActionForm form) {
        return new CreateChainActionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainActions(UserVisitPK userVisitPK, GetChainActionsForm form) {
        return new GetChainActionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainAction(UserVisitPK userVisitPK, GetChainActionForm form) {
        return new GetChainActionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editChainAction(UserVisitPK userVisitPK, EditChainActionForm form) {
        return new EditChainActionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteChainAction(UserVisitPK userVisitPK, DeleteChainActionForm form) {
        return new DeleteChainActionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Chain Action Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createChainActionDescription(UserVisitPK userVisitPK, CreateChainActionDescriptionForm form) {
        return new CreateChainActionDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainActionDescriptions(UserVisitPK userVisitPK, GetChainActionDescriptionsForm form) {
        return new GetChainActionDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getChainActionDescription(UserVisitPK userVisitPK, GetChainActionDescriptionForm form) {
        return new GetChainActionDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editChainActionDescription(UserVisitPK userVisitPK, EditChainActionDescriptionForm form) {
        return new EditChainActionDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteChainActionDescription(UserVisitPK userVisitPK, DeleteChainActionDescriptionForm form) {
        return new DeleteChainActionDescriptionCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Chain Instances
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getChainInstance(UserVisitPK userVisitPK, GetChainInstanceForm form) {
        return new GetChainInstanceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getChainInstances(UserVisitPK userVisitPK, GetChainInstancesForm form) {
        return new GetChainInstancesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Chain Instance Statuses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult processChainInstanceStatuses(UserVisitPK userVisitPK) {
        return new ProcessChainInstanceStatusesCommand(userVisitPK).run();
    }
    
}
