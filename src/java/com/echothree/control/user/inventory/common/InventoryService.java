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
import com.echothree.control.user.inventory.common.result.*;
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
    
    CommandResult<CreateInventoryConditionResult> createInventoryCondition(UserVisitPK userVisitPK, CreateInventoryConditionForm form);
    
    CommandResult<GetInventoryConditionsResult> getInventoryConditions(UserVisitPK userVisitPK, GetInventoryConditionsForm form);
    
    CommandResult<GetInventoryConditionResult> getInventoryCondition(UserVisitPK userVisitPK, GetInventoryConditionForm form);
    
    CommandResult<GetInventoryConditionChoicesResult> getInventoryConditionChoices(UserVisitPK userVisitPK, GetInventoryConditionChoicesForm form);
    
    CommandResult<?> setDefaultInventoryCondition(UserVisitPK userVisitPK, SetDefaultInventoryConditionForm form);
    
    CommandResult<EditInventoryConditionResult> editInventoryCondition(UserVisitPK userVisitPK, EditInventoryConditionForm form);
    
    CommandResult<?> deleteInventoryCondition(UserVisitPK userVisitPK, DeleteInventoryConditionForm form);
    
    // -------------------------------------------------------------------------
    //   Inventory Condition Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createInventoryConditionDescription(UserVisitPK userVisitPK, CreateInventoryConditionDescriptionForm form);
    
    CommandResult<GetInventoryConditionDescriptionsResult> getInventoryConditionDescriptions(UserVisitPK userVisitPK, GetInventoryConditionDescriptionsForm form);
    
    CommandResult<EditInventoryConditionDescriptionResult> editInventoryConditionDescription(UserVisitPK userVisitPK, EditInventoryConditionDescriptionForm form);
    
    CommandResult<?> deleteInventoryConditionDescription(UserVisitPK userVisitPK, DeleteInventoryConditionDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Types
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createInventoryConditionUseType(UserVisitPK userVisitPK, CreateInventoryConditionUseTypeForm form);
    
    CommandResult<GetInventoryConditionUseTypeChoicesResult> getInventoryConditionUseTypeChoices(UserVisitPK userVisitPK, GetInventoryConditionUseTypeChoicesForm form);
    
    CommandResult<GetInventoryConditionUseTypesResult> getInventoryConditionUseTypes(UserVisitPK userVisitPK, GetInventoryConditionUseTypesForm form);
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createInventoryConditionUseTypeDescription(UserVisitPK userVisitPK, CreateInventoryConditionUseTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Uses
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createInventoryConditionUse(UserVisitPK userVisitPK, CreateInventoryConditionUseForm form);
    
    CommandResult<GetInventoryConditionUsesResult> getInventoryConditionUses(UserVisitPK userVisitPK, GetInventoryConditionUsesForm form);
    
    CommandResult<?> setDefaultInventoryConditionUse(UserVisitPK userVisitPK, SetDefaultInventoryConditionUseForm form);
    
    CommandResult<?> deleteInventoryConditionUse(UserVisitPK userVisitPK, DeleteInventoryConditionUseForm form);
    
    // -------------------------------------------------------------------------
    //   Inventory Location Groups
    // -------------------------------------------------------------------------
    
    CommandResult<CreateInventoryLocationGroupResult> createInventoryLocationGroup(UserVisitPK userVisitPK, CreateInventoryLocationGroupForm form);
    
    CommandResult<GetInventoryLocationGroupsResult> getInventoryLocationGroups(UserVisitPK userVisitPK, GetInventoryLocationGroupsForm form);
    
    CommandResult<GetInventoryLocationGroupResult> getInventoryLocationGroup(UserVisitPK userVisitPK, GetInventoryLocationGroupForm form);
    
    CommandResult<GetInventoryLocationGroupChoicesResult> getInventoryLocationGroupChoices(UserVisitPK userVisitPK, GetInventoryLocationGroupChoicesForm form);
    
    CommandResult<?> setDefaultInventoryLocationGroup(UserVisitPK userVisitPK, SetDefaultInventoryLocationGroupForm form);
    
    CommandResult<GetInventoryLocationGroupStatusChoicesResult> getInventoryLocationGroupStatusChoices(UserVisitPK userVisitPK, GetInventoryLocationGroupStatusChoicesForm form);
    
    CommandResult<?> setInventoryLocationGroupStatus(UserVisitPK userVisitPK, SetInventoryLocationGroupStatusForm form);
    
    CommandResult<EditInventoryLocationGroupResult> editInventoryLocationGroup(UserVisitPK userVisitPK, EditInventoryLocationGroupForm form);
    
    CommandResult<?> deleteInventoryLocationGroup(UserVisitPK userVisitPK, DeleteInventoryLocationGroupForm form);
    
    // -------------------------------------------------------------------------
    //   Inventory Location Group Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createInventoryLocationGroupDescription(UserVisitPK userVisitPK, CreateInventoryLocationGroupDescriptionForm form);
    
    CommandResult<GetInventoryLocationGroupDescriptionsResult> getInventoryLocationGroupDescriptions(UserVisitPK userVisitPK, GetInventoryLocationGroupDescriptionsForm form);
    
    CommandResult<EditInventoryLocationGroupDescriptionResult> editInventoryLocationGroupDescription(UserVisitPK userVisitPK, EditInventoryLocationGroupDescriptionForm form);
    
    CommandResult<?> deleteInventoryLocationGroupDescription(UserVisitPK userVisitPK, DeleteInventoryLocationGroupDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Capacities
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createInventoryLocationGroupCapacity(UserVisitPK userVisitPK, CreateInventoryLocationGroupCapacityForm form);
    
    CommandResult<GetInventoryLocationGroupCapacitiesResult> getInventoryLocationGroupCapacities(UserVisitPK userVisitPK, GetInventoryLocationGroupCapacitiesForm form);
    
    CommandResult<EditInventoryLocationGroupCapacityResult> editInventoryLocationGroupCapacity(UserVisitPK userVisitPK, EditInventoryLocationGroupCapacityForm form);
    
    CommandResult<?> deleteInventoryLocationGroupCapacity(UserVisitPK userVisitPK, DeleteInventoryLocationGroupCapacityForm form);
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Volumes
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createInventoryLocationGroupVolume(UserVisitPK userVisitPK, CreateInventoryLocationGroupVolumeForm form);
    
    CommandResult<EditInventoryLocationGroupVolumeResult> editInventoryLocationGroupVolume(UserVisitPK userVisitPK, EditInventoryLocationGroupVolumeForm form);
    
    CommandResult<?> deleteInventoryLocationGroupVolume(UserVisitPK userVisitPK, DeleteInventoryLocationGroupVolumeForm form);

    // -------------------------------------------------------------------------
    //   Lots
    // -------------------------------------------------------------------------

    CommandResult<GetLotsResult> getLots(UserVisitPK userVisitPK, GetLotsForm form);

    CommandResult<GetLotResult> getLot(UserVisitPK userVisitPK, GetLotForm form);

    // --------------------------------------------------------------------------------
    //   Lot Time Types
    // --------------------------------------------------------------------------------

    CommandResult<?> createLotTimeType(UserVisitPK userVisitPK, CreateLotTimeTypeForm form);

    CommandResult<GetLotTimeTypeChoicesResult> getLotTimeTypeChoices(UserVisitPK userVisitPK, GetLotTimeTypeChoicesForm form);

    CommandResult<GetLotTimeTypeResult> getLotTimeType(UserVisitPK userVisitPK, GetLotTimeTypeForm form);

    CommandResult<GetLotTimeTypesResult> getLotTimeTypes(UserVisitPK userVisitPK, GetLotTimeTypesForm form);

    CommandResult<?> setDefaultLotTimeType(UserVisitPK userVisitPK, SetDefaultLotTimeTypeForm form);

    CommandResult<EditLotTimeTypeResult> editLotTimeType(UserVisitPK userVisitPK, EditLotTimeTypeForm form);

    CommandResult<?> deleteLotTimeType(UserVisitPK userVisitPK, DeleteLotTimeTypeForm form);

    // --------------------------------------------------------------------------------
    //   Lot Time Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createLotTimeTypeDescription(UserVisitPK userVisitPK, CreateLotTimeTypeDescriptionForm form);

    CommandResult<GetLotTimeTypeDescriptionResult> getLotTimeTypeDescription(UserVisitPK userVisitPK, GetLotTimeTypeDescriptionForm form);

    CommandResult<GetLotTimeTypeDescriptionsResult> getLotTimeTypeDescriptions(UserVisitPK userVisitPK, GetLotTimeTypeDescriptionsForm form);

    CommandResult<EditLotTimeTypeDescriptionResult> editLotTimeTypeDescription(UserVisitPK userVisitPK, EditLotTimeTypeDescriptionForm form);

    CommandResult<?> deleteLotTimeTypeDescription(UserVisitPK userVisitPK, DeleteLotTimeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Lot Alias Types
    // --------------------------------------------------------------------------------

    CommandResult<?> createLotAliasType(UserVisitPK userVisitPK, CreateLotAliasTypeForm form);

    CommandResult<GetLotAliasTypeChoicesResult> getLotAliasTypeChoices(UserVisitPK userVisitPK, GetLotAliasTypeChoicesForm form);

    CommandResult<GetLotAliasTypeResult> getLotAliasType(UserVisitPK userVisitPK, GetLotAliasTypeForm form);

    CommandResult<GetLotAliasTypesResult> getLotAliasTypes(UserVisitPK userVisitPK, GetLotAliasTypesForm form);

    CommandResult<?> setDefaultLotAliasType(UserVisitPK userVisitPK, SetDefaultLotAliasTypeForm form);

    CommandResult<EditLotAliasTypeResult> editLotAliasType(UserVisitPK userVisitPK, EditLotAliasTypeForm form);

    CommandResult<?> deleteLotAliasType(UserVisitPK userVisitPK, DeleteLotAliasTypeForm form);

    // --------------------------------------------------------------------------------
    //   Lot Alias Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createLotAliasTypeDescription(UserVisitPK userVisitPK, CreateLotAliasTypeDescriptionForm form);

    CommandResult<GetLotAliasTypeDescriptionResult> getLotAliasTypeDescription(UserVisitPK userVisitPK, GetLotAliasTypeDescriptionForm form);

    CommandResult<GetLotAliasTypeDescriptionsResult> getLotAliasTypeDescriptions(UserVisitPK userVisitPK, GetLotAliasTypeDescriptionsForm form);

    CommandResult<EditLotAliasTypeDescriptionResult> editLotAliasTypeDescription(UserVisitPK userVisitPK, EditLotAliasTypeDescriptionForm form);

    CommandResult<?> deleteLotAliasTypeDescription(UserVisitPK userVisitPK, DeleteLotAliasTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Lot Aliases
    // --------------------------------------------------------------------------------

    CommandResult<?> createLotAlias(UserVisitPK userVisitPK, CreateLotAliasForm form);

    CommandResult<GetLotAliasResult> getLotAlias(UserVisitPK userVisitPK, GetLotAliasForm form);

    CommandResult<GetLotAliasesResult> getLotAliases(UserVisitPK userVisitPK, GetLotAliasesForm form);

    CommandResult<EditLotAliasResult> editLotAlias(UserVisitPK userVisitPK, EditLotAliasForm form);

    CommandResult<?> deleteLotAlias(UserVisitPK userVisitPK, DeleteLotAliasForm form);

    // --------------------------------------------------------------------------------
    //   Party Inventory Levels
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createPartyInventoryLevel(UserVisitPK userVisitPK, CreatePartyInventoryLevelForm form);
    
    CommandResult<GetPartyInventoryLevelResult> getPartyInventoryLevel(UserVisitPK userVisitPK, GetPartyInventoryLevelForm form);
    
    CommandResult<GetPartyInventoryLevelsResult> getPartyInventoryLevels(UserVisitPK userVisitPK, GetPartyInventoryLevelsForm form);
    
    CommandResult<EditPartyInventoryLevelResult> editPartyInventoryLevel(UserVisitPK userVisitPK, EditPartyInventoryLevelForm form);
    
    CommandResult<?> deletePartyInventoryLevel(UserVisitPK userVisitPK, DeletePartyInventoryLevelForm form);
    
    // --------------------------------------------------------------------------------
    //   Allocation Priorities
    // --------------------------------------------------------------------------------

    CommandResult<CreateAllocationPriorityResult> createAllocationPriority(UserVisitPK userVisitPK, CreateAllocationPriorityForm form);

    CommandResult<GetAllocationPriorityChoicesResult> getAllocationPriorityChoices(UserVisitPK userVisitPK, GetAllocationPriorityChoicesForm form);

    CommandResult<GetAllocationPriorityResult> getAllocationPriority(UserVisitPK userVisitPK, GetAllocationPriorityForm form);

    CommandResult<GetAllocationPrioritiesResult> getAllocationPriorities(UserVisitPK userVisitPK, GetAllocationPrioritiesForm form);

    CommandResult<?> setDefaultAllocationPriority(UserVisitPK userVisitPK, SetDefaultAllocationPriorityForm form);

    CommandResult<EditAllocationPriorityResult> editAllocationPriority(UserVisitPK userVisitPK, EditAllocationPriorityForm form);

    CommandResult<?> deleteAllocationPriority(UserVisitPK userVisitPK, DeleteAllocationPriorityForm form);

    // --------------------------------------------------------------------------------
    //   Allocation Priority Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createAllocationPriorityDescription(UserVisitPK userVisitPK, CreateAllocationPriorityDescriptionForm form);

    CommandResult<GetAllocationPriorityDescriptionResult> getAllocationPriorityDescription(UserVisitPK userVisitPK, GetAllocationPriorityDescriptionForm form);

    CommandResult<GetAllocationPriorityDescriptionsResult> getAllocationPriorityDescriptions(UserVisitPK userVisitPK, GetAllocationPriorityDescriptionsForm form);

    CommandResult<EditAllocationPriorityDescriptionResult> editAllocationPriorityDescription(UserVisitPK userVisitPK, EditAllocationPriorityDescriptionForm form);

    CommandResult<?> deleteAllocationPriorityDescription(UserVisitPK userVisitPK, DeleteAllocationPriorityDescriptionForm form);

    // -------------------------------------------------------------------------
    //   Inventory Adjustment Types
    // -------------------------------------------------------------------------

    CommandResult<CreateInventoryAdjustmentTypeResult> createInventoryAdjustmentType(UserVisitPK userVisitPK, CreateInventoryAdjustmentTypeForm form);

    CommandResult<GetInventoryAdjustmentTypesResult> getInventoryAdjustmentTypes(UserVisitPK userVisitPK, GetInventoryAdjustmentTypesForm form);

    CommandResult<GetInventoryAdjustmentTypeResult> getInventoryAdjustmentType(UserVisitPK userVisitPK, GetInventoryAdjustmentTypeForm form);

    CommandResult<GetInventoryAdjustmentTypeChoicesResult> getInventoryAdjustmentTypeChoices(UserVisitPK userVisitPK, GetInventoryAdjustmentTypeChoicesForm form);

    CommandResult<?> setDefaultInventoryAdjustmentType(UserVisitPK userVisitPK, SetDefaultInventoryAdjustmentTypeForm form);

    CommandResult<EditInventoryAdjustmentTypeResult> editInventoryAdjustmentType(UserVisitPK userVisitPK, EditInventoryAdjustmentTypeForm form);

    CommandResult<?> deleteInventoryAdjustmentType(UserVisitPK userVisitPK, DeleteInventoryAdjustmentTypeForm form);

    // -------------------------------------------------------------------------
    //   Inventory Adjustment Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createInventoryAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateInventoryAdjustmentTypeDescriptionForm form);

    CommandResult<GetInventoryAdjustmentTypeDescriptionsResult> getInventoryAdjustmentTypeDescriptions(UserVisitPK userVisitPK, GetInventoryAdjustmentTypeDescriptionsForm form);

    CommandResult<EditInventoryAdjustmentTypeDescriptionResult> editInventoryAdjustmentTypeDescription(UserVisitPK userVisitPK, EditInventoryAdjustmentTypeDescriptionForm form);

    CommandResult<?> deleteInventoryAdjustmentTypeDescription(UserVisitPK userVisitPK, DeleteInventoryAdjustmentTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Inventory Transaction Types
    // -------------------------------------------------------------------------

    CommandResult<?> createInventoryTransactionType(UserVisitPK userVisitPK, CreateInventoryTransactionTypeForm form);

    CommandResult<GetInventoryTransactionTypesResult> getInventoryTransactionTypes(UserVisitPK userVisitPK, GetInventoryTransactionTypesForm form);

    CommandResult<GetInventoryTransactionTypeResult> getInventoryTransactionType(UserVisitPK userVisitPK, GetInventoryTransactionTypeForm form);

    CommandResult<GetInventoryTransactionTypeChoicesResult> getInventoryTransactionTypeChoices(UserVisitPK userVisitPK, GetInventoryTransactionTypeChoicesForm form);

    CommandResult<?> setDefaultInventoryTransactionType(UserVisitPK userVisitPK, SetDefaultInventoryTransactionTypeForm form);

    CommandResult<EditInventoryTransactionTypeResult> editInventoryTransactionType(UserVisitPK userVisitPK, EditInventoryTransactionTypeForm form);

    CommandResult<?> deleteInventoryTransactionType(UserVisitPK userVisitPK, DeleteInventoryTransactionTypeForm form);

    // -------------------------------------------------------------------------
    //   Inventory Transaction Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult<?> createInventoryTransactionTypeDescription(UserVisitPK userVisitPK, CreateInventoryTransactionTypeDescriptionForm form);

    CommandResult<GetInventoryTransactionTypeDescriptionsResult> getInventoryTransactionTypeDescriptions(UserVisitPK userVisitPK, GetInventoryTransactionTypeDescriptionsForm form);

    CommandResult<EditInventoryTransactionTypeDescriptionResult> editInventoryTransactionTypeDescription(UserVisitPK userVisitPK, EditInventoryTransactionTypeDescriptionForm form);

    CommandResult<?> deleteInventoryTransactionTypeDescription(UserVisitPK userVisitPK, DeleteInventoryTransactionTypeDescriptionForm form);

}
