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

package com.echothree.control.user.inventory.server;

import com.echothree.control.user.inventory.common.InventoryRemote;
import com.echothree.control.user.inventory.common.form.*;
import com.echothree.control.user.inventory.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class InventoryBean
        extends InventoryFormsImpl
        implements InventoryRemote, InventoryLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "InventoryBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Inventory Conditions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryCondition(UserVisitPK userVisitPK, CreateInventoryConditionForm form) {
        return new CreateInventoryConditionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInventoryConditions(UserVisitPK userVisitPK, GetInventoryConditionsForm form) {
        return new GetInventoryConditionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInventoryCondition(UserVisitPK userVisitPK, GetInventoryConditionForm form) {
        return new GetInventoryConditionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInventoryConditionChoices(UserVisitPK userVisitPK, GetInventoryConditionChoicesForm form) {
        return new GetInventoryConditionChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultInventoryCondition(UserVisitPK userVisitPK, SetDefaultInventoryConditionForm form) {
        return new SetDefaultInventoryConditionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editInventoryCondition(UserVisitPK userVisitPK, EditInventoryConditionForm form) {
        return new EditInventoryConditionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteInventoryCondition(UserVisitPK userVisitPK, DeleteInventoryConditionForm form) {
        return new DeleteInventoryConditionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Inventory Condition Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryConditionDescription(UserVisitPK userVisitPK, CreateInventoryConditionDescriptionForm form) {
        return new CreateInventoryConditionDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInventoryConditionDescriptions(UserVisitPK userVisitPK, GetInventoryConditionDescriptionsForm form) {
        return new GetInventoryConditionDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editInventoryConditionDescription(UserVisitPK userVisitPK, EditInventoryConditionDescriptionForm form) {
        return new EditInventoryConditionDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteInventoryConditionDescription(UserVisitPK userVisitPK, DeleteInventoryConditionDescriptionForm form) {
        return new DeleteInventoryConditionDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryConditionUseType(UserVisitPK userVisitPK, CreateInventoryConditionUseTypeForm form) {
        return new CreateInventoryConditionUseTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInventoryConditionUseTypeChoices(UserVisitPK userVisitPK, GetInventoryConditionUseTypeChoicesForm form) {
        return new GetInventoryConditionUseTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInventoryConditionUseTypes(UserVisitPK userVisitPK, GetInventoryConditionUseTypesForm form) {
        return new GetInventoryConditionUseTypesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryConditionUseTypeDescription(UserVisitPK userVisitPK, CreateInventoryConditionUseTypeDescriptionForm form) {
        return new CreateInventoryConditionUseTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryConditionUse(UserVisitPK userVisitPK, CreateInventoryConditionUseForm form) {
        return new CreateInventoryConditionUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInventoryConditionUses(UserVisitPK userVisitPK, GetInventoryConditionUsesForm form) {
        return new GetInventoryConditionUsesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultInventoryConditionUse(UserVisitPK userVisitPK, SetDefaultInventoryConditionUseForm form) {
        return new SetDefaultInventoryConditionUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteInventoryConditionUse(UserVisitPK userVisitPK, DeleteInventoryConditionUseForm form) {
        return new DeleteInventoryConditionUseCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Inventory Location Groups
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryLocationGroup(UserVisitPK userVisitPK, CreateInventoryLocationGroupForm form) {
        return new CreateInventoryLocationGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInventoryLocationGroups(UserVisitPK userVisitPK, GetInventoryLocationGroupsForm form) {
        return new GetInventoryLocationGroupsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInventoryLocationGroup(UserVisitPK userVisitPK, GetInventoryLocationGroupForm form) {
        return new GetInventoryLocationGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInventoryLocationGroupChoices(UserVisitPK userVisitPK, GetInventoryLocationGroupChoicesForm form) {
        return new GetInventoryLocationGroupChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultInventoryLocationGroup(UserVisitPK userVisitPK, SetDefaultInventoryLocationGroupForm form) {
        return new SetDefaultInventoryLocationGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInventoryLocationGroupStatusChoices(UserVisitPK userVisitPK, GetInventoryLocationGroupStatusChoicesForm form) {
        return new GetInventoryLocationGroupStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setInventoryLocationGroupStatus(UserVisitPK userVisitPK, SetInventoryLocationGroupStatusForm form) {
        return new SetInventoryLocationGroupStatusCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editInventoryLocationGroup(UserVisitPK userVisitPK, EditInventoryLocationGroupForm form) {
        return new EditInventoryLocationGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteInventoryLocationGroup(UserVisitPK userVisitPK, DeleteInventoryLocationGroupForm form) {
        return new DeleteInventoryLocationGroupCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Inventory Location Group Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryLocationGroupDescription(UserVisitPK userVisitPK, CreateInventoryLocationGroupDescriptionForm form) {
        return new CreateInventoryLocationGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInventoryLocationGroupDescriptions(UserVisitPK userVisitPK, GetInventoryLocationGroupDescriptionsForm form) {
        return new GetInventoryLocationGroupDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editInventoryLocationGroupDescription(UserVisitPK userVisitPK, EditInventoryLocationGroupDescriptionForm form) {
        return new EditInventoryLocationGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteInventoryLocationGroupDescription(UserVisitPK userVisitPK, DeleteInventoryLocationGroupDescriptionForm form) {
        return new DeleteInventoryLocationGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Capacities
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryLocationGroupCapacity(UserVisitPK userVisitPK, CreateInventoryLocationGroupCapacityForm form) {
        return new CreateInventoryLocationGroupCapacityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInventoryLocationGroupCapacities(UserVisitPK userVisitPK, GetInventoryLocationGroupCapacitiesForm form) {
        return new GetInventoryLocationGroupCapacitiesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editInventoryLocationGroupCapacity(UserVisitPK userVisitPK, EditInventoryLocationGroupCapacityForm form) {
        return new EditInventoryLocationGroupCapacityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteInventoryLocationGroupCapacity(UserVisitPK userVisitPK, DeleteInventoryLocationGroupCapacityForm form) {
        return new DeleteInventoryLocationGroupCapacityCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryLocationGroupVolume(UserVisitPK userVisitPK, CreateInventoryLocationGroupVolumeForm form) {
        return new CreateInventoryLocationGroupVolumeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editInventoryLocationGroupVolume(UserVisitPK userVisitPK, EditInventoryLocationGroupVolumeForm form) {
        return new EditInventoryLocationGroupVolumeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteInventoryLocationGroupVolume(UserVisitPK userVisitPK, DeleteInventoryLocationGroupVolumeForm form) {
        return new DeleteInventoryLocationGroupVolumeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Lots
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getLots(UserVisitPK userVisitPK, GetLotsForm form) {
        return new GetLotsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLot(UserVisitPK userVisitPK, GetLotForm form) {
        return new GetLotCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Lot Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createLotTimeType(UserVisitPK userVisitPK, CreateLotTimeTypeForm form) {
        return new CreateLotTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLotTimeTypeChoices(UserVisitPK userVisitPK, GetLotTimeTypeChoicesForm form) {
        return new GetLotTimeTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLotTimeType(UserVisitPK userVisitPK, GetLotTimeTypeForm form) {
        return new GetLotTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLotTimeTypes(UserVisitPK userVisitPK, GetLotTimeTypesForm form) {
        return new GetLotTimeTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultLotTimeType(UserVisitPK userVisitPK, SetDefaultLotTimeTypeForm form) {
        return new SetDefaultLotTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editLotTimeType(UserVisitPK userVisitPK, EditLotTimeTypeForm form) {
        return new EditLotTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteLotTimeType(UserVisitPK userVisitPK, DeleteLotTimeTypeForm form) {
        return new DeleteLotTimeTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Lot Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createLotTimeTypeDescription(UserVisitPK userVisitPK, CreateLotTimeTypeDescriptionForm form) {
        return new CreateLotTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLotTimeTypeDescription(UserVisitPK userVisitPK, GetLotTimeTypeDescriptionForm form) {
        return new GetLotTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLotTimeTypeDescriptions(UserVisitPK userVisitPK, GetLotTimeTypeDescriptionsForm form) {
        return new GetLotTimeTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editLotTimeTypeDescription(UserVisitPK userVisitPK, EditLotTimeTypeDescriptionForm form) {
        return new EditLotTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteLotTimeTypeDescription(UserVisitPK userVisitPK, DeleteLotTimeTypeDescriptionForm form) {
        return new DeleteLotTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Lot Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createLotAliasType(UserVisitPK userVisitPK, CreateLotAliasTypeForm form) {
        return new CreateLotAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLotAliasTypeChoices(UserVisitPK userVisitPK, GetLotAliasTypeChoicesForm form) {
        return new GetLotAliasTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLotAliasType(UserVisitPK userVisitPK, GetLotAliasTypeForm form) {
        return new GetLotAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLotAliasTypes(UserVisitPK userVisitPK, GetLotAliasTypesForm form) {
        return new GetLotAliasTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultLotAliasType(UserVisitPK userVisitPK, SetDefaultLotAliasTypeForm form) {
        return new SetDefaultLotAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editLotAliasType(UserVisitPK userVisitPK, EditLotAliasTypeForm form) {
        return new EditLotAliasTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteLotAliasType(UserVisitPK userVisitPK, DeleteLotAliasTypeForm form) {
        return new DeleteLotAliasTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Lot Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createLotAliasTypeDescription(UserVisitPK userVisitPK, CreateLotAliasTypeDescriptionForm form) {
        return new CreateLotAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLotAliasTypeDescription(UserVisitPK userVisitPK, GetLotAliasTypeDescriptionForm form) {
        return new GetLotAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLotAliasTypeDescriptions(UserVisitPK userVisitPK, GetLotAliasTypeDescriptionsForm form) {
        return new GetLotAliasTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editLotAliasTypeDescription(UserVisitPK userVisitPK, EditLotAliasTypeDescriptionForm form) {
        return new EditLotAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteLotAliasTypeDescription(UserVisitPK userVisitPK, DeleteLotAliasTypeDescriptionForm form) {
        return new DeleteLotAliasTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Lot Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createLotAlias(UserVisitPK userVisitPK, CreateLotAliasForm form) {
        return new CreateLotAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLotAlias(UserVisitPK userVisitPK, GetLotAliasForm form) {
        return new GetLotAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getLotAliases(UserVisitPK userVisitPK, GetLotAliasesForm form) {
        return new GetLotAliasesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editLotAlias(UserVisitPK userVisitPK, EditLotAliasForm form) {
        return new EditLotAliasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteLotAlias(UserVisitPK userVisitPK, DeleteLotAliasForm form) {
        return new DeleteLotAliasCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Party Inventory Levels
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyInventoryLevel(UserVisitPK userVisitPK, CreatePartyInventoryLevelForm form) {
        return new CreatePartyInventoryLevelCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartyInventoryLevel(UserVisitPK userVisitPK, GetPartyInventoryLevelForm form) {
        return new GetPartyInventoryLevelCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartyInventoryLevels(UserVisitPK userVisitPK, GetPartyInventoryLevelsForm form) {
        return new GetPartyInventoryLevelsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPartyInventoryLevel(UserVisitPK userVisitPK, EditPartyInventoryLevelForm form) {
        return new EditPartyInventoryLevelCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartyInventoryLevel(UserVisitPK userVisitPK, DeletePartyInventoryLevelForm form) {
        return new DeletePartyInventoryLevelCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Allocation Priorities
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createAllocationPriority(UserVisitPK userVisitPK, CreateAllocationPriorityForm form) {
        return new CreateAllocationPriorityCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getAllocationPriorityChoices(UserVisitPK userVisitPK, GetAllocationPriorityChoicesForm form) {
        return new GetAllocationPriorityChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getAllocationPriority(UserVisitPK userVisitPK, GetAllocationPriorityForm form) {
        return new GetAllocationPriorityCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getAllocationPriorities(UserVisitPK userVisitPK, GetAllocationPrioritiesForm form) {
        return new GetAllocationPrioritiesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultAllocationPriority(UserVisitPK userVisitPK, SetDefaultAllocationPriorityForm form) {
        return new SetDefaultAllocationPriorityCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editAllocationPriority(UserVisitPK userVisitPK, EditAllocationPriorityForm form) {
        return new EditAllocationPriorityCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteAllocationPriority(UserVisitPK userVisitPK, DeleteAllocationPriorityForm form) {
        return new DeleteAllocationPriorityCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Allocation Priority Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createAllocationPriorityDescription(UserVisitPK userVisitPK, CreateAllocationPriorityDescriptionForm form) {
        return new CreateAllocationPriorityDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getAllocationPriorityDescription(UserVisitPK userVisitPK, GetAllocationPriorityDescriptionForm form) {
        return new GetAllocationPriorityDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getAllocationPriorityDescriptions(UserVisitPK userVisitPK, GetAllocationPriorityDescriptionsForm form) {
        return new GetAllocationPriorityDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editAllocationPriorityDescription(UserVisitPK userVisitPK, EditAllocationPriorityDescriptionForm form) {
        return new EditAllocationPriorityDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteAllocationPriorityDescription(UserVisitPK userVisitPK, DeleteAllocationPriorityDescriptionForm form) {
        return new DeleteAllocationPriorityDescriptionCommand(userVisitPK, form).run();
    }

}
