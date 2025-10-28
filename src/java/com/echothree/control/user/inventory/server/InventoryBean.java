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
        return new CreateInventoryConditionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryConditions(UserVisitPK userVisitPK, GetInventoryConditionsForm form) {
        return new GetInventoryConditionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryCondition(UserVisitPK userVisitPK, GetInventoryConditionForm form) {
        return new GetInventoryConditionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryConditionChoices(UserVisitPK userVisitPK, GetInventoryConditionChoicesForm form) {
        return new GetInventoryConditionChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultInventoryCondition(UserVisitPK userVisitPK, SetDefaultInventoryConditionForm form) {
        return new SetDefaultInventoryConditionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInventoryCondition(UserVisitPK userVisitPK, EditInventoryConditionForm form) {
        return new EditInventoryConditionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInventoryCondition(UserVisitPK userVisitPK, DeleteInventoryConditionForm form) {
        return new DeleteInventoryConditionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Inventory Condition Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryConditionDescription(UserVisitPK userVisitPK, CreateInventoryConditionDescriptionForm form) {
        return new CreateInventoryConditionDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryConditionDescriptions(UserVisitPK userVisitPK, GetInventoryConditionDescriptionsForm form) {
        return new GetInventoryConditionDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInventoryConditionDescription(UserVisitPK userVisitPK, EditInventoryConditionDescriptionForm form) {
        return new EditInventoryConditionDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInventoryConditionDescription(UserVisitPK userVisitPK, DeleteInventoryConditionDescriptionForm form) {
        return new DeleteInventoryConditionDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryConditionUseType(UserVisitPK userVisitPK, CreateInventoryConditionUseTypeForm form) {
        return new CreateInventoryConditionUseTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryConditionUseTypeChoices(UserVisitPK userVisitPK, GetInventoryConditionUseTypeChoicesForm form) {
        return new GetInventoryConditionUseTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryConditionUseTypes(UserVisitPK userVisitPK, GetInventoryConditionUseTypesForm form) {
        return new GetInventoryConditionUseTypesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryConditionUseTypeDescription(UserVisitPK userVisitPK, CreateInventoryConditionUseTypeDescriptionForm form) {
        return new CreateInventoryConditionUseTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryConditionUse(UserVisitPK userVisitPK, CreateInventoryConditionUseForm form) {
        return new CreateInventoryConditionUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryConditionUses(UserVisitPK userVisitPK, GetInventoryConditionUsesForm form) {
        return new GetInventoryConditionUsesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultInventoryConditionUse(UserVisitPK userVisitPK, SetDefaultInventoryConditionUseForm form) {
        return new SetDefaultInventoryConditionUseCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInventoryConditionUse(UserVisitPK userVisitPK, DeleteInventoryConditionUseForm form) {
        return new DeleteInventoryConditionUseCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Inventory Location Groups
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryLocationGroup(UserVisitPK userVisitPK, CreateInventoryLocationGroupForm form) {
        return new CreateInventoryLocationGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryLocationGroups(UserVisitPK userVisitPK, GetInventoryLocationGroupsForm form) {
        return new GetInventoryLocationGroupsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryLocationGroup(UserVisitPK userVisitPK, GetInventoryLocationGroupForm form) {
        return new GetInventoryLocationGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryLocationGroupChoices(UserVisitPK userVisitPK, GetInventoryLocationGroupChoicesForm form) {
        return new GetInventoryLocationGroupChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultInventoryLocationGroup(UserVisitPK userVisitPK, SetDefaultInventoryLocationGroupForm form) {
        return new SetDefaultInventoryLocationGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryLocationGroupStatusChoices(UserVisitPK userVisitPK, GetInventoryLocationGroupStatusChoicesForm form) {
        return new GetInventoryLocationGroupStatusChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setInventoryLocationGroupStatus(UserVisitPK userVisitPK, SetInventoryLocationGroupStatusForm form) {
        return new SetInventoryLocationGroupStatusCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInventoryLocationGroup(UserVisitPK userVisitPK, EditInventoryLocationGroupForm form) {
        return new EditInventoryLocationGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInventoryLocationGroup(UserVisitPK userVisitPK, DeleteInventoryLocationGroupForm form) {
        return new DeleteInventoryLocationGroupCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Inventory Location Group Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryLocationGroupDescription(UserVisitPK userVisitPK, CreateInventoryLocationGroupDescriptionForm form) {
        return new CreateInventoryLocationGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryLocationGroupDescriptions(UserVisitPK userVisitPK, GetInventoryLocationGroupDescriptionsForm form) {
        return new GetInventoryLocationGroupDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInventoryLocationGroupDescription(UserVisitPK userVisitPK, EditInventoryLocationGroupDescriptionForm form) {
        return new EditInventoryLocationGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInventoryLocationGroupDescription(UserVisitPK userVisitPK, DeleteInventoryLocationGroupDescriptionForm form) {
        return new DeleteInventoryLocationGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Capacities
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryLocationGroupCapacity(UserVisitPK userVisitPK, CreateInventoryLocationGroupCapacityForm form) {
        return new CreateInventoryLocationGroupCapacityCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryLocationGroupCapacities(UserVisitPK userVisitPK, GetInventoryLocationGroupCapacitiesForm form) {
        return new GetInventoryLocationGroupCapacitiesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInventoryLocationGroupCapacity(UserVisitPK userVisitPK, EditInventoryLocationGroupCapacityForm form) {
        return new EditInventoryLocationGroupCapacityCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInventoryLocationGroupCapacity(UserVisitPK userVisitPK, DeleteInventoryLocationGroupCapacityForm form) {
        return new DeleteInventoryLocationGroupCapacityCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryLocationGroupVolume(UserVisitPK userVisitPK, CreateInventoryLocationGroupVolumeForm form) {
        return new CreateInventoryLocationGroupVolumeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInventoryLocationGroupVolume(UserVisitPK userVisitPK, EditInventoryLocationGroupVolumeForm form) {
        return new EditInventoryLocationGroupVolumeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInventoryLocationGroupVolume(UserVisitPK userVisitPK, DeleteInventoryLocationGroupVolumeForm form) {
        return new DeleteInventoryLocationGroupVolumeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Lots
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getLots(UserVisitPK userVisitPK, GetLotsForm form) {
        return new GetLotsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLot(UserVisitPK userVisitPK, GetLotForm form) {
        return new GetLotCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Lot Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createLotTimeType(UserVisitPK userVisitPK, CreateLotTimeTypeForm form) {
        return new CreateLotTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotTimeTypeChoices(UserVisitPK userVisitPK, GetLotTimeTypeChoicesForm form) {
        return new GetLotTimeTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotTimeType(UserVisitPK userVisitPK, GetLotTimeTypeForm form) {
        return new GetLotTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotTimeTypes(UserVisitPK userVisitPK, GetLotTimeTypesForm form) {
        return new GetLotTimeTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultLotTimeType(UserVisitPK userVisitPK, SetDefaultLotTimeTypeForm form) {
        return new SetDefaultLotTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLotTimeType(UserVisitPK userVisitPK, EditLotTimeTypeForm form) {
        return new EditLotTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteLotTimeType(UserVisitPK userVisitPK, DeleteLotTimeTypeForm form) {
        return new DeleteLotTimeTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Lot Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createLotTimeTypeDescription(UserVisitPK userVisitPK, CreateLotTimeTypeDescriptionForm form) {
        return new CreateLotTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotTimeTypeDescription(UserVisitPK userVisitPK, GetLotTimeTypeDescriptionForm form) {
        return new GetLotTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotTimeTypeDescriptions(UserVisitPK userVisitPK, GetLotTimeTypeDescriptionsForm form) {
        return new GetLotTimeTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLotTimeTypeDescription(UserVisitPK userVisitPK, EditLotTimeTypeDescriptionForm form) {
        return new EditLotTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteLotTimeTypeDescription(UserVisitPK userVisitPK, DeleteLotTimeTypeDescriptionForm form) {
        return new DeleteLotTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Lot Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createLotAliasType(UserVisitPK userVisitPK, CreateLotAliasTypeForm form) {
        return new CreateLotAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotAliasTypeChoices(UserVisitPK userVisitPK, GetLotAliasTypeChoicesForm form) {
        return new GetLotAliasTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotAliasType(UserVisitPK userVisitPK, GetLotAliasTypeForm form) {
        return new GetLotAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotAliasTypes(UserVisitPK userVisitPK, GetLotAliasTypesForm form) {
        return new GetLotAliasTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultLotAliasType(UserVisitPK userVisitPK, SetDefaultLotAliasTypeForm form) {
        return new SetDefaultLotAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLotAliasType(UserVisitPK userVisitPK, EditLotAliasTypeForm form) {
        return new EditLotAliasTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteLotAliasType(UserVisitPK userVisitPK, DeleteLotAliasTypeForm form) {
        return new DeleteLotAliasTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Lot Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createLotAliasTypeDescription(UserVisitPK userVisitPK, CreateLotAliasTypeDescriptionForm form) {
        return new CreateLotAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotAliasTypeDescription(UserVisitPK userVisitPK, GetLotAliasTypeDescriptionForm form) {
        return new GetLotAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotAliasTypeDescriptions(UserVisitPK userVisitPK, GetLotAliasTypeDescriptionsForm form) {
        return new GetLotAliasTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLotAliasTypeDescription(UserVisitPK userVisitPK, EditLotAliasTypeDescriptionForm form) {
        return new EditLotAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteLotAliasTypeDescription(UserVisitPK userVisitPK, DeleteLotAliasTypeDescriptionForm form) {
        return new DeleteLotAliasTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Lot Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createLotAlias(UserVisitPK userVisitPK, CreateLotAliasForm form) {
        return new CreateLotAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotAlias(UserVisitPK userVisitPK, GetLotAliasForm form) {
        return new GetLotAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotAliases(UserVisitPK userVisitPK, GetLotAliasesForm form) {
        return new GetLotAliasesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLotAlias(UserVisitPK userVisitPK, EditLotAliasForm form) {
        return new EditLotAliasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteLotAlias(UserVisitPK userVisitPK, DeleteLotAliasForm form) {
        return new DeleteLotAliasCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Inventory Levels
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyInventoryLevel(UserVisitPK userVisitPK, CreatePartyInventoryLevelForm form) {
        return new CreatePartyInventoryLevelCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyInventoryLevel(UserVisitPK userVisitPK, GetPartyInventoryLevelForm form) {
        return new GetPartyInventoryLevelCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyInventoryLevels(UserVisitPK userVisitPK, GetPartyInventoryLevelsForm form) {
        return new GetPartyInventoryLevelsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartyInventoryLevel(UserVisitPK userVisitPK, EditPartyInventoryLevelForm form) {
        return new EditPartyInventoryLevelCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyInventoryLevel(UserVisitPK userVisitPK, DeletePartyInventoryLevelForm form) {
        return new DeletePartyInventoryLevelCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Allocation Priorities
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createAllocationPriority(UserVisitPK userVisitPK, CreateAllocationPriorityForm form) {
        return new CreateAllocationPriorityCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getAllocationPriorityChoices(UserVisitPK userVisitPK, GetAllocationPriorityChoicesForm form) {
        return new GetAllocationPriorityChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getAllocationPriority(UserVisitPK userVisitPK, GetAllocationPriorityForm form) {
        return new GetAllocationPriorityCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getAllocationPriorities(UserVisitPK userVisitPK, GetAllocationPrioritiesForm form) {
        return new GetAllocationPrioritiesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultAllocationPriority(UserVisitPK userVisitPK, SetDefaultAllocationPriorityForm form) {
        return new SetDefaultAllocationPriorityCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editAllocationPriority(UserVisitPK userVisitPK, EditAllocationPriorityForm form) {
        return new EditAllocationPriorityCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteAllocationPriority(UserVisitPK userVisitPK, DeleteAllocationPriorityForm form) {
        return new DeleteAllocationPriorityCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Allocation Priority Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createAllocationPriorityDescription(UserVisitPK userVisitPK, CreateAllocationPriorityDescriptionForm form) {
        return new CreateAllocationPriorityDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getAllocationPriorityDescription(UserVisitPK userVisitPK, GetAllocationPriorityDescriptionForm form) {
        return new GetAllocationPriorityDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getAllocationPriorityDescriptions(UserVisitPK userVisitPK, GetAllocationPriorityDescriptionsForm form) {
        return new GetAllocationPriorityDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editAllocationPriorityDescription(UserVisitPK userVisitPK, EditAllocationPriorityDescriptionForm form) {
        return new EditAllocationPriorityDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteAllocationPriorityDescription(UserVisitPK userVisitPK, DeleteAllocationPriorityDescriptionForm form) {
        return new DeleteAllocationPriorityDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Adjustment Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createInventoryAdjustmentType(UserVisitPK userVisitPK, CreateInventoryAdjustmentTypeForm form) {
        return new CreateInventoryAdjustmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryAdjustmentTypes(UserVisitPK userVisitPK, GetInventoryAdjustmentTypesForm form) {
        return new GetInventoryAdjustmentTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryAdjustmentType(UserVisitPK userVisitPK, GetInventoryAdjustmentTypeForm form) {
        return new GetInventoryAdjustmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryAdjustmentTypeChoices(UserVisitPK userVisitPK, GetInventoryAdjustmentTypeChoicesForm form) {
        return new GetInventoryAdjustmentTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultInventoryAdjustmentType(UserVisitPK userVisitPK, SetDefaultInventoryAdjustmentTypeForm form) {
        return new SetDefaultInventoryAdjustmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editInventoryAdjustmentType(UserVisitPK userVisitPK, EditInventoryAdjustmentTypeForm form) {
        return new EditInventoryAdjustmentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteInventoryAdjustmentType(UserVisitPK userVisitPK, DeleteInventoryAdjustmentTypeForm form) {
        return new DeleteInventoryAdjustmentTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Adjustment Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createInventoryAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateInventoryAdjustmentTypeDescriptionForm form) {
        return new CreateInventoryAdjustmentTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryAdjustmentTypeDescriptions(UserVisitPK userVisitPK, GetInventoryAdjustmentTypeDescriptionsForm form) {
        return new GetInventoryAdjustmentTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editInventoryAdjustmentTypeDescription(UserVisitPK userVisitPK, EditInventoryAdjustmentTypeDescriptionForm form) {
        return new EditInventoryAdjustmentTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteInventoryAdjustmentTypeDescription(UserVisitPK userVisitPK, DeleteInventoryAdjustmentTypeDescriptionForm form) {
        return new DeleteInventoryAdjustmentTypeDescriptionCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Transaction Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createInventoryTransactionType(UserVisitPK userVisitPK, CreateInventoryTransactionTypeForm form) {
        return new CreateInventoryTransactionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryTransactionTypes(UserVisitPK userVisitPK, GetInventoryTransactionTypesForm form) {
        return new GetInventoryTransactionTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryTransactionType(UserVisitPK userVisitPK, GetInventoryTransactionTypeForm form) {
        return new GetInventoryTransactionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryTransactionTypeChoices(UserVisitPK userVisitPK, GetInventoryTransactionTypeChoicesForm form) {
        return new GetInventoryTransactionTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultInventoryTransactionType(UserVisitPK userVisitPK, SetDefaultInventoryTransactionTypeForm form) {
        return new SetDefaultInventoryTransactionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editInventoryTransactionType(UserVisitPK userVisitPK, EditInventoryTransactionTypeForm form) {
        return new EditInventoryTransactionTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteInventoryTransactionType(UserVisitPK userVisitPK, DeleteInventoryTransactionTypeForm form) {
        return new DeleteInventoryTransactionTypeCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Transaction Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createInventoryTransactionTypeDescription(UserVisitPK userVisitPK, CreateInventoryTransactionTypeDescriptionForm form) {
        return new CreateInventoryTransactionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryTransactionTypeDescriptions(UserVisitPK userVisitPK, GetInventoryTransactionTypeDescriptionsForm form) {
        return new GetInventoryTransactionTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editInventoryTransactionTypeDescription(UserVisitPK userVisitPK, EditInventoryTransactionTypeDescriptionForm form) {
        return new EditInventoryTransactionTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteInventoryTransactionTypeDescription(UserVisitPK userVisitPK, DeleteInventoryTransactionTypeDescriptionForm form) {
        return new DeleteInventoryTransactionTypeDescriptionCommand().run(userVisitPK, form);
    }

}
