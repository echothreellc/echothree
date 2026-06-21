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

package com.echothree.control.user.chain.server;

import com.echothree.control.user.chain.common.ChainRemote;
import com.echothree.control.user.chain.common.form.*;
import com.echothree.control.user.chain.common.result.*;
import com.echothree.control.user.chain.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
    public CommandResult<?> createChainActionType(UserVisitPK userVisitPK, CreateChainActionTypeForm form) {
        return CDI.current().select(CreateChainActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainActionTypesResult> getChainActionTypes(UserVisitPK userVisitPK, GetChainActionTypesForm form) {
        return CDI.current().select(GetChainActionTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainActionTypeResult> getChainActionType(UserVisitPK userVisitPK, GetChainActionTypeForm form) {
        return CDI.current().select(GetChainActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainActionTypeChoicesResult> getChainActionTypeChoices(UserVisitPK userVisitPK, GetChainActionTypeChoicesForm form) {
        return CDI.current().select(GetChainActionTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultChainActionType(UserVisitPK userVisitPK, SetDefaultChainActionTypeForm form) {
        return CDI.current().select(SetDefaultChainActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditChainActionTypeResult> editChainActionType(UserVisitPK userVisitPK, EditChainActionTypeForm form) {
        return CDI.current().select(EditChainActionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteChainActionType(UserVisitPK userVisitPK, DeleteChainActionTypeForm form) {
        return CDI.current().select(DeleteChainActionTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Action Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createChainActionTypeDescription(UserVisitPK userVisitPK, CreateChainActionTypeDescriptionForm form) {
        return CDI.current().select(CreateChainActionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainActionTypeDescriptionsResult> getChainActionTypeDescriptions(UserVisitPK userVisitPK, GetChainActionTypeDescriptionsForm form) {
        return CDI.current().select(GetChainActionTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainActionTypeDescriptionResult> getChainActionTypeDescription(UserVisitPK userVisitPK, GetChainActionTypeDescriptionForm form) {
        return CDI.current().select(GetChainActionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditChainActionTypeDescriptionResult> editChainActionTypeDescription(UserVisitPK userVisitPK, EditChainActionTypeDescriptionForm form) {
        return CDI.current().select(EditChainActionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteChainActionTypeDescription(UserVisitPK userVisitPK, DeleteChainActionTypeDescriptionForm form) {
        return CDI.current().select(DeleteChainActionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Chain Action Type Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> createChainActionTypeUse(UserVisitPK userVisitPK, CreateChainActionTypeUseForm form) {
        return CDI.current().select(CreateChainActionTypeUseCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Chain Kinds
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createChainKind(UserVisitPK userVisitPK, CreateChainKindForm form) {
        return CDI.current().select(CreateChainKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainKindsResult> getChainKinds(UserVisitPK userVisitPK, GetChainKindsForm form) {
        return CDI.current().select(GetChainKindsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainKindResult> getChainKind(UserVisitPK userVisitPK, GetChainKindForm form) {
        return CDI.current().select(GetChainKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainKindChoicesResult> getChainKindChoices(UserVisitPK userVisitPK, GetChainKindChoicesForm form) {
        return CDI.current().select(GetChainKindChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultChainKind(UserVisitPK userVisitPK, SetDefaultChainKindForm form) {
        return CDI.current().select(SetDefaultChainKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditChainKindResult> editChainKind(UserVisitPK userVisitPK, EditChainKindForm form) {
        return CDI.current().select(EditChainKindCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteChainKind(UserVisitPK userVisitPK, DeleteChainKindForm form) {
        return CDI.current().select(DeleteChainKindCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Kind Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createChainKindDescription(UserVisitPK userVisitPK, CreateChainKindDescriptionForm form) {
        return CDI.current().select(CreateChainKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainKindDescriptionsResult> getChainKindDescriptions(UserVisitPK userVisitPK, GetChainKindDescriptionsForm form) {
        return CDI.current().select(GetChainKindDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainKindDescriptionResult> getChainKindDescription(UserVisitPK userVisitPK, GetChainKindDescriptionForm form) {
        return CDI.current().select(GetChainKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditChainKindDescriptionResult> editChainKindDescription(UserVisitPK userVisitPK, EditChainKindDescriptionForm form) {
        return CDI.current().select(EditChainKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteChainKindDescription(UserVisitPK userVisitPK, DeleteChainKindDescriptionForm form) {
        return CDI.current().select(DeleteChainKindDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createChainType(UserVisitPK userVisitPK, CreateChainTypeForm form) {
        return CDI.current().select(CreateChainTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainTypesResult> getChainTypes(UserVisitPK userVisitPK, GetChainTypesForm form) {
        return CDI.current().select(GetChainTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainTypeResult> getChainType(UserVisitPK userVisitPK, GetChainTypeForm form) {
        return CDI.current().select(GetChainTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainTypeChoicesResult> getChainTypeChoices(UserVisitPK userVisitPK, GetChainTypeChoicesForm form) {
        return CDI.current().select(GetChainTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultChainType(UserVisitPK userVisitPK, SetDefaultChainTypeForm form) {
        return CDI.current().select(SetDefaultChainTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditChainTypeResult> editChainType(UserVisitPK userVisitPK, EditChainTypeForm form) {
        return CDI.current().select(EditChainTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteChainType(UserVisitPK userVisitPK, DeleteChainTypeForm form) {
        return CDI.current().select(DeleteChainTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createChainTypeDescription(UserVisitPK userVisitPK, CreateChainTypeDescriptionForm form) {
        return CDI.current().select(CreateChainTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainTypeDescriptionsResult> getChainTypeDescriptions(UserVisitPK userVisitPK, GetChainTypeDescriptionsForm form) {
        return CDI.current().select(GetChainTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainTypeDescriptionResult> getChainTypeDescription(UserVisitPK userVisitPK, GetChainTypeDescriptionForm form) {
        return CDI.current().select(GetChainTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditChainTypeDescriptionResult> editChainTypeDescription(UserVisitPK userVisitPK, EditChainTypeDescriptionForm form) {
        return CDI.current().select(EditChainTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteChainTypeDescription(UserVisitPK userVisitPK, DeleteChainTypeDescriptionForm form) {
        return CDI.current().select(DeleteChainTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Entity Role Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createChainEntityRoleType(UserVisitPK userVisitPK, CreateChainEntityRoleTypeForm form) {
        return CDI.current().select(CreateChainEntityRoleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainEntityRoleTypesResult> getChainEntityRoleTypes(UserVisitPK userVisitPK, GetChainEntityRoleTypesForm form) {
        return CDI.current().select(GetChainEntityRoleTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainEntityRoleTypeResult> getChainEntityRoleType(UserVisitPK userVisitPK, GetChainEntityRoleTypeForm form) {
        return CDI.current().select(GetChainEntityRoleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditChainEntityRoleTypeResult> editChainEntityRoleType(UserVisitPK userVisitPK, EditChainEntityRoleTypeForm form) {
        return CDI.current().select(EditChainEntityRoleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteChainEntityRoleType(UserVisitPK userVisitPK, DeleteChainEntityRoleTypeForm form) {
        return CDI.current().select(DeleteChainEntityRoleTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Entity Role Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createChainEntityRoleTypeDescription(UserVisitPK userVisitPK, CreateChainEntityRoleTypeDescriptionForm form) {
        return CDI.current().select(CreateChainEntityRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainEntityRoleTypeDescriptionsResult> getChainEntityRoleTypeDescriptions(UserVisitPK userVisitPK, GetChainEntityRoleTypeDescriptionsForm form) {
        return CDI.current().select(GetChainEntityRoleTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainEntityRoleTypeDescriptionResult> getChainEntityRoleTypeDescription(UserVisitPK userVisitPK, GetChainEntityRoleTypeDescriptionForm form) {
        return CDI.current().select(GetChainEntityRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditChainEntityRoleTypeDescriptionResult> editChainEntityRoleTypeDescription(UserVisitPK userVisitPK, EditChainEntityRoleTypeDescriptionForm form) {
        return CDI.current().select(EditChainEntityRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteChainEntityRoleTypeDescription(UserVisitPK userVisitPK, DeleteChainEntityRoleTypeDescriptionForm form) {
        return CDI.current().select(DeleteChainEntityRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chains
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createChain(UserVisitPK userVisitPK, CreateChainForm form) {
        return CDI.current().select(CreateChainCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainsResult> getChains(UserVisitPK userVisitPK, GetChainsForm form) {
        return CDI.current().select(GetChainsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainResult> getChain(UserVisitPK userVisitPK, GetChainForm form) {
        return CDI.current().select(GetChainCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainChoicesResult> getChainChoices(UserVisitPK userVisitPK, GetChainChoicesForm form) {
        return CDI.current().select(GetChainChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultChain(UserVisitPK userVisitPK, SetDefaultChainForm form) {
        return CDI.current().select(SetDefaultChainCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditChainResult> editChain(UserVisitPK userVisitPK, EditChainForm form) {
        return CDI.current().select(EditChainCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteChain(UserVisitPK userVisitPK, DeleteChainForm form) {
        return CDI.current().select(DeleteChainCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createChainDescription(UserVisitPK userVisitPK, CreateChainDescriptionForm form) {
        return CDI.current().select(CreateChainDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainDescriptionsResult> getChainDescriptions(UserVisitPK userVisitPK, GetChainDescriptionsForm form) {
        return CDI.current().select(GetChainDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainDescriptionResult> getChainDescription(UserVisitPK userVisitPK, GetChainDescriptionForm form) {
        return CDI.current().select(GetChainDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditChainDescriptionResult> editChainDescription(UserVisitPK userVisitPK, EditChainDescriptionForm form) {
        return CDI.current().select(EditChainDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteChainDescription(UserVisitPK userVisitPK, DeleteChainDescriptionForm form) {
        return CDI.current().select(DeleteChainDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Action Sets
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createChainActionSet(UserVisitPK userVisitPK, CreateChainActionSetForm form) {
        return CDI.current().select(CreateChainActionSetCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainActionSetsResult> getChainActionSets(UserVisitPK userVisitPK, GetChainActionSetsForm form) {
        return CDI.current().select(GetChainActionSetsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainActionSetResult> getChainActionSet(UserVisitPK userVisitPK, GetChainActionSetForm form) {
        return CDI.current().select(GetChainActionSetCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainActionSetChoicesResult> getChainActionSetChoices(UserVisitPK userVisitPK, GetChainActionSetChoicesForm form) {
        return CDI.current().select(GetChainActionSetChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> setDefaultChainActionSet(UserVisitPK userVisitPK, SetDefaultChainActionSetForm form) {
        return CDI.current().select(SetDefaultChainActionSetCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditChainActionSetResult> editChainActionSet(UserVisitPK userVisitPK, EditChainActionSetForm form) {
        return CDI.current().select(EditChainActionSetCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteChainActionSet(UserVisitPK userVisitPK, DeleteChainActionSetForm form) {
        return CDI.current().select(DeleteChainActionSetCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Action Set Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createChainActionSetDescription(UserVisitPK userVisitPK, CreateChainActionSetDescriptionForm form) {
        return CDI.current().select(CreateChainActionSetDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainActionSetDescriptionsResult> getChainActionSetDescriptions(UserVisitPK userVisitPK, GetChainActionSetDescriptionsForm form) {
        return CDI.current().select(GetChainActionSetDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainActionSetDescriptionResult> getChainActionSetDescription(UserVisitPK userVisitPK, GetChainActionSetDescriptionForm form) {
        return CDI.current().select(GetChainActionSetDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditChainActionSetDescriptionResult> editChainActionSetDescription(UserVisitPK userVisitPK, EditChainActionSetDescriptionForm form) {
        return CDI.current().select(EditChainActionSetDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteChainActionSetDescription(UserVisitPK userVisitPK, DeleteChainActionSetDescriptionForm form) {
        return CDI.current().select(DeleteChainActionSetDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Actions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createChainAction(UserVisitPK userVisitPK, CreateChainActionForm form) {
        return CDI.current().select(CreateChainActionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainActionsResult> getChainActions(UserVisitPK userVisitPK, GetChainActionsForm form) {
        return CDI.current().select(GetChainActionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainActionResult> getChainAction(UserVisitPK userVisitPK, GetChainActionForm form) {
        return CDI.current().select(GetChainActionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditChainActionResult> editChainAction(UserVisitPK userVisitPK, EditChainActionForm form) {
        return CDI.current().select(EditChainActionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteChainAction(UserVisitPK userVisitPK, DeleteChainActionForm form) {
        return CDI.current().select(DeleteChainActionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Action Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<?> createChainActionDescription(UserVisitPK userVisitPK, CreateChainActionDescriptionForm form) {
        return CDI.current().select(CreateChainActionDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainActionDescriptionsResult> getChainActionDescriptions(UserVisitPK userVisitPK, GetChainActionDescriptionsForm form) {
        return CDI.current().select(GetChainActionDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetChainActionDescriptionResult> getChainActionDescription(UserVisitPK userVisitPK, GetChainActionDescriptionForm form) {
        return CDI.current().select(GetChainActionDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditChainActionDescriptionResult> editChainActionDescription(UserVisitPK userVisitPK, EditChainActionDescriptionForm form) {
        return CDI.current().select(EditChainActionDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<?> deleteChainActionDescription(UserVisitPK userVisitPK, DeleteChainActionDescriptionForm form) {
        return CDI.current().select(DeleteChainActionDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Chain Instances
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<GetChainInstanceResult> getChainInstance(UserVisitPK userVisitPK, GetChainInstanceForm form) {
        return CDI.current().select(GetChainInstanceCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetChainInstancesResult> getChainInstances(UserVisitPK userVisitPK, GetChainInstancesForm form) {
        return CDI.current().select(GetChainInstancesCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Chain Instance Statuses
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<?> processChainInstanceStatuses(UserVisitPK userVisitPK) {
        return CDI.current().select(ProcessChainInstanceStatusesCommand.class).get().run(userVisitPK);
    }
    
}
