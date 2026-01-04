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

package com.echothree.control.user.inventory.common;

import com.echothree.control.user.inventory.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface InventoryService
        extends InventoryForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Inventory Conditions
    // -------------------------------------------------------------------------
    
    CommandResult createInventoryCondition(UserVisitPK userVisitPK, CreateInventoryConditionForm form);
    
    CommandResult getInventoryConditions(UserVisitPK userVisitPK, GetInventoryConditionsForm form);
    
    CommandResult getInventoryCondition(UserVisitPK userVisitPK, GetInventoryConditionForm form);
    
    CommandResult getInventoryConditionChoices(UserVisitPK userVisitPK, GetInventoryConditionChoicesForm form);
    
    CommandResult setDefaultInventoryCondition(UserVisitPK userVisitPK, SetDefaultInventoryConditionForm form);
    
    CommandResult editInventoryCondition(UserVisitPK userVisitPK, EditInventoryConditionForm form);
    
    CommandResult deleteInventoryCondition(UserVisitPK userVisitPK, DeleteInventoryConditionForm form);
    
    // -------------------------------------------------------------------------
    //   Inventory Condition Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createInventoryConditionDescription(UserVisitPK userVisitPK, CreateInventoryConditionDescriptionForm form);
    
    CommandResult getInventoryConditionDescriptions(UserVisitPK userVisitPK, GetInventoryConditionDescriptionsForm form);
    
    CommandResult editInventoryConditionDescription(UserVisitPK userVisitPK, EditInventoryConditionDescriptionForm form);
    
    CommandResult deleteInventoryConditionDescription(UserVisitPK userVisitPK, DeleteInventoryConditionDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Types
    // --------------------------------------------------------------------------------
    
    CommandResult createInventoryConditionUseType(UserVisitPK userVisitPK, CreateInventoryConditionUseTypeForm form);
    
    CommandResult getInventoryConditionUseTypeChoices(UserVisitPK userVisitPK, GetInventoryConditionUseTypeChoicesForm form);
    
    CommandResult getInventoryConditionUseTypes(UserVisitPK userVisitPK, GetInventoryConditionUseTypesForm form);
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createInventoryConditionUseTypeDescription(UserVisitPK userVisitPK, CreateInventoryConditionUseTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Uses
    // --------------------------------------------------------------------------------
    
    CommandResult createInventoryConditionUse(UserVisitPK userVisitPK, CreateInventoryConditionUseForm form);
    
    CommandResult getInventoryConditionUses(UserVisitPK userVisitPK, GetInventoryConditionUsesForm form);
    
    CommandResult setDefaultInventoryConditionUse(UserVisitPK userVisitPK, SetDefaultInventoryConditionUseForm form);
    
    CommandResult deleteInventoryConditionUse(UserVisitPK userVisitPK, DeleteInventoryConditionUseForm form);
    
    // -------------------------------------------------------------------------
    //   Inventory Location Groups
    // -------------------------------------------------------------------------
    
    CommandResult createInventoryLocationGroup(UserVisitPK userVisitPK, CreateInventoryLocationGroupForm form);
    
    CommandResult getInventoryLocationGroups(UserVisitPK userVisitPK, GetInventoryLocationGroupsForm form);
    
    CommandResult getInventoryLocationGroup(UserVisitPK userVisitPK, GetInventoryLocationGroupForm form);
    
    CommandResult getInventoryLocationGroupChoices(UserVisitPK userVisitPK, GetInventoryLocationGroupChoicesForm form);
    
    CommandResult setDefaultInventoryLocationGroup(UserVisitPK userVisitPK, SetDefaultInventoryLocationGroupForm form);
    
    CommandResult getInventoryLocationGroupStatusChoices(UserVisitPK userVisitPK, GetInventoryLocationGroupStatusChoicesForm form);
    
    CommandResult setInventoryLocationGroupStatus(UserVisitPK userVisitPK, SetInventoryLocationGroupStatusForm form);
    
    CommandResult editInventoryLocationGroup(UserVisitPK userVisitPK, EditInventoryLocationGroupForm form);
    
    CommandResult deleteInventoryLocationGroup(UserVisitPK userVisitPK, DeleteInventoryLocationGroupForm form);
    
    // -------------------------------------------------------------------------
    //   Inventory Location Group Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createInventoryLocationGroupDescription(UserVisitPK userVisitPK, CreateInventoryLocationGroupDescriptionForm form);
    
    CommandResult getInventoryLocationGroupDescriptions(UserVisitPK userVisitPK, GetInventoryLocationGroupDescriptionsForm form);
    
    CommandResult editInventoryLocationGroupDescription(UserVisitPK userVisitPK, EditInventoryLocationGroupDescriptionForm form);
    
    CommandResult deleteInventoryLocationGroupDescription(UserVisitPK userVisitPK, DeleteInventoryLocationGroupDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Capacities
    // --------------------------------------------------------------------------------
    
    CommandResult createInventoryLocationGroupCapacity(UserVisitPK userVisitPK, CreateInventoryLocationGroupCapacityForm form);
    
    CommandResult getInventoryLocationGroupCapacities(UserVisitPK userVisitPK, GetInventoryLocationGroupCapacitiesForm form);
    
    CommandResult editInventoryLocationGroupCapacity(UserVisitPK userVisitPK, EditInventoryLocationGroupCapacityForm form);
    
    CommandResult deleteInventoryLocationGroupCapacity(UserVisitPK userVisitPK, DeleteInventoryLocationGroupCapacityForm form);
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Volumes
    // --------------------------------------------------------------------------------
    
    CommandResult createInventoryLocationGroupVolume(UserVisitPK userVisitPK, CreateInventoryLocationGroupVolumeForm form);
    
    CommandResult editInventoryLocationGroupVolume(UserVisitPK userVisitPK, EditInventoryLocationGroupVolumeForm form);
    
    CommandResult deleteInventoryLocationGroupVolume(UserVisitPK userVisitPK, DeleteInventoryLocationGroupVolumeForm form);

    // -------------------------------------------------------------------------
    //   Lots
    // -------------------------------------------------------------------------

    CommandResult getLots(UserVisitPK userVisitPK, GetLotsForm form);

    CommandResult getLot(UserVisitPK userVisitPK, GetLotForm form);

    // --------------------------------------------------------------------------------
    //   Lot Time Types
    // --------------------------------------------------------------------------------

    CommandResult createLotTimeType(UserVisitPK userVisitPK, CreateLotTimeTypeForm form);

    CommandResult getLotTimeTypeChoices(UserVisitPK userVisitPK, GetLotTimeTypeChoicesForm form);

    CommandResult getLotTimeType(UserVisitPK userVisitPK, GetLotTimeTypeForm form);

    CommandResult getLotTimeTypes(UserVisitPK userVisitPK, GetLotTimeTypesForm form);

    CommandResult setDefaultLotTimeType(UserVisitPK userVisitPK, SetDefaultLotTimeTypeForm form);

    CommandResult editLotTimeType(UserVisitPK userVisitPK, EditLotTimeTypeForm form);

    CommandResult deleteLotTimeType(UserVisitPK userVisitPK, DeleteLotTimeTypeForm form);

    // --------------------------------------------------------------------------------
    //   Lot Time Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createLotTimeTypeDescription(UserVisitPK userVisitPK, CreateLotTimeTypeDescriptionForm form);

    CommandResult getLotTimeTypeDescription(UserVisitPK userVisitPK, GetLotTimeTypeDescriptionForm form);

    CommandResult getLotTimeTypeDescriptions(UserVisitPK userVisitPK, GetLotTimeTypeDescriptionsForm form);

    CommandResult editLotTimeTypeDescription(UserVisitPK userVisitPK, EditLotTimeTypeDescriptionForm form);

    CommandResult deleteLotTimeTypeDescription(UserVisitPK userVisitPK, DeleteLotTimeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Lot Alias Types
    // --------------------------------------------------------------------------------

    CommandResult createLotAliasType(UserVisitPK userVisitPK, CreateLotAliasTypeForm form);

    CommandResult getLotAliasTypeChoices(UserVisitPK userVisitPK, GetLotAliasTypeChoicesForm form);

    CommandResult getLotAliasType(UserVisitPK userVisitPK, GetLotAliasTypeForm form);

    CommandResult getLotAliasTypes(UserVisitPK userVisitPK, GetLotAliasTypesForm form);

    CommandResult setDefaultLotAliasType(UserVisitPK userVisitPK, SetDefaultLotAliasTypeForm form);

    CommandResult editLotAliasType(UserVisitPK userVisitPK, EditLotAliasTypeForm form);

    CommandResult deleteLotAliasType(UserVisitPK userVisitPK, DeleteLotAliasTypeForm form);

    // --------------------------------------------------------------------------------
    //   Lot Alias Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createLotAliasTypeDescription(UserVisitPK userVisitPK, CreateLotAliasTypeDescriptionForm form);

    CommandResult getLotAliasTypeDescription(UserVisitPK userVisitPK, GetLotAliasTypeDescriptionForm form);

    CommandResult getLotAliasTypeDescriptions(UserVisitPK userVisitPK, GetLotAliasTypeDescriptionsForm form);

    CommandResult editLotAliasTypeDescription(UserVisitPK userVisitPK, EditLotAliasTypeDescriptionForm form);

    CommandResult deleteLotAliasTypeDescription(UserVisitPK userVisitPK, DeleteLotAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Lot Aliases
    // --------------------------------------------------------------------------------

    CommandResult createLotAlias(UserVisitPK userVisitPK, CreateLotAliasForm form);

    CommandResult getLotAlias(UserVisitPK userVisitPK, GetLotAliasForm form);

    CommandResult getLotAliases(UserVisitPK userVisitPK, GetLotAliasesForm form);

    CommandResult editLotAlias(UserVisitPK userVisitPK, EditLotAliasForm form);

    CommandResult deleteLotAlias(UserVisitPK userVisitPK, DeleteLotAliasForm form);

    // --------------------------------------------------------------------------------
    //   Party Inventory Levels
    // --------------------------------------------------------------------------------
    
    CommandResult createPartyInventoryLevel(UserVisitPK userVisitPK, CreatePartyInventoryLevelForm form);
    
    CommandResult getPartyInventoryLevel(UserVisitPK userVisitPK, GetPartyInventoryLevelForm form);
    
    CommandResult getPartyInventoryLevels(UserVisitPK userVisitPK, GetPartyInventoryLevelsForm form);
    
    CommandResult editPartyInventoryLevel(UserVisitPK userVisitPK, EditPartyInventoryLevelForm form);
    
    CommandResult deletePartyInventoryLevel(UserVisitPK userVisitPK, DeletePartyInventoryLevelForm form);
    
    // --------------------------------------------------------------------------------
    //   Allocation Priorities
    // --------------------------------------------------------------------------------

    CommandResult createAllocationPriority(UserVisitPK userVisitPK, CreateAllocationPriorityForm form);

    CommandResult getAllocationPriorityChoices(UserVisitPK userVisitPK, GetAllocationPriorityChoicesForm form);

    CommandResult getAllocationPriority(UserVisitPK userVisitPK, GetAllocationPriorityForm form);

    CommandResult getAllocationPriorities(UserVisitPK userVisitPK, GetAllocationPrioritiesForm form);

    CommandResult setDefaultAllocationPriority(UserVisitPK userVisitPK, SetDefaultAllocationPriorityForm form);

    CommandResult editAllocationPriority(UserVisitPK userVisitPK, EditAllocationPriorityForm form);

    CommandResult deleteAllocationPriority(UserVisitPK userVisitPK, DeleteAllocationPriorityForm form);

    // --------------------------------------------------------------------------------
    //   Allocation Priority Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createAllocationPriorityDescription(UserVisitPK userVisitPK, CreateAllocationPriorityDescriptionForm form);

    CommandResult getAllocationPriorityDescription(UserVisitPK userVisitPK, GetAllocationPriorityDescriptionForm form);

    CommandResult getAllocationPriorityDescriptions(UserVisitPK userVisitPK, GetAllocationPriorityDescriptionsForm form);

    CommandResult editAllocationPriorityDescription(UserVisitPK userVisitPK, EditAllocationPriorityDescriptionForm form);

    CommandResult deleteAllocationPriorityDescription(UserVisitPK userVisitPK, DeleteAllocationPriorityDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Inventory Adjustment Types
    // -------------------------------------------------------------------------

    CommandResult createInventoryAdjustmentType(UserVisitPK userVisitPK, CreateInventoryAdjustmentTypeForm form);

    CommandResult getInventoryAdjustmentTypes(UserVisitPK userVisitPK, GetInventoryAdjustmentTypesForm form);

    CommandResult getInventoryAdjustmentType(UserVisitPK userVisitPK, GetInventoryAdjustmentTypeForm form);

    CommandResult getInventoryAdjustmentTypeChoices(UserVisitPK userVisitPK, GetInventoryAdjustmentTypeChoicesForm form);

    CommandResult setDefaultInventoryAdjustmentType(UserVisitPK userVisitPK, SetDefaultInventoryAdjustmentTypeForm form);

    CommandResult editInventoryAdjustmentType(UserVisitPK userVisitPK, EditInventoryAdjustmentTypeForm form);

    CommandResult deleteInventoryAdjustmentType(UserVisitPK userVisitPK, DeleteInventoryAdjustmentTypeForm form);

    // -------------------------------------------------------------------------
    //   Inventory Adjustment Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createInventoryAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateInventoryAdjustmentTypeDescriptionForm form);

    CommandResult getInventoryAdjustmentTypeDescriptions(UserVisitPK userVisitPK, GetInventoryAdjustmentTypeDescriptionsForm form);

    CommandResult editInventoryAdjustmentTypeDescription(UserVisitPK userVisitPK, EditInventoryAdjustmentTypeDescriptionForm form);

    CommandResult deleteInventoryAdjustmentTypeDescription(UserVisitPK userVisitPK, DeleteInventoryAdjustmentTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Inventory Transaction Types
    // -------------------------------------------------------------------------

    CommandResult createInventoryTransactionType(UserVisitPK userVisitPK, CreateInventoryTransactionTypeForm form);

    CommandResult getInventoryTransactionTypes(UserVisitPK userVisitPK, GetInventoryTransactionTypesForm form);

    CommandResult getInventoryTransactionType(UserVisitPK userVisitPK, GetInventoryTransactionTypeForm form);

    CommandResult getInventoryTransactionTypeChoices(UserVisitPK userVisitPK, GetInventoryTransactionTypeChoicesForm form);

    CommandResult setDefaultInventoryTransactionType(UserVisitPK userVisitPK, SetDefaultInventoryTransactionTypeForm form);

    CommandResult editInventoryTransactionType(UserVisitPK userVisitPK, EditInventoryTransactionTypeForm form);

    CommandResult deleteInventoryTransactionType(UserVisitPK userVisitPK, DeleteInventoryTransactionTypeForm form);

    // -------------------------------------------------------------------------
    //   Inventory Transaction Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createInventoryTransactionTypeDescription(UserVisitPK userVisitPK, CreateInventoryTransactionTypeDescriptionForm form);

    CommandResult getInventoryTransactionTypeDescriptions(UserVisitPK userVisitPK, GetInventoryTransactionTypeDescriptionsForm form);

    CommandResult editInventoryTransactionTypeDescription(UserVisitPK userVisitPK, EditInventoryTransactionTypeDescriptionForm form);

    CommandResult deleteInventoryTransactionTypeDescription(UserVisitPK userVisitPK, DeleteInventoryTransactionTypeDescriptionForm form);

}
