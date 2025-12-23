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

package com.echothree.control.user.inventory.server;

import com.echothree.control.user.inventory.common.InventoryRemote;
import com.echothree.control.user.inventory.common.form.*;
import com.echothree.control.user.inventory.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateInventoryConditionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryConditions(UserVisitPK userVisitPK, GetInventoryConditionsForm form) {
        return CDI.current().select(GetInventoryConditionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryCondition(UserVisitPK userVisitPK, GetInventoryConditionForm form) {
        return CDI.current().select(GetInventoryConditionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryConditionChoices(UserVisitPK userVisitPK, GetInventoryConditionChoicesForm form) {
        return CDI.current().select(GetInventoryConditionChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultInventoryCondition(UserVisitPK userVisitPK, SetDefaultInventoryConditionForm form) {
        return CDI.current().select(SetDefaultInventoryConditionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInventoryCondition(UserVisitPK userVisitPK, EditInventoryConditionForm form) {
        return CDI.current().select(EditInventoryConditionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInventoryCondition(UserVisitPK userVisitPK, DeleteInventoryConditionForm form) {
        return CDI.current().select(DeleteInventoryConditionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Inventory Condition Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryConditionDescription(UserVisitPK userVisitPK, CreateInventoryConditionDescriptionForm form) {
        return CDI.current().select(CreateInventoryConditionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryConditionDescriptions(UserVisitPK userVisitPK, GetInventoryConditionDescriptionsForm form) {
        return CDI.current().select(GetInventoryConditionDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInventoryConditionDescription(UserVisitPK userVisitPK, EditInventoryConditionDescriptionForm form) {
        return CDI.current().select(EditInventoryConditionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInventoryConditionDescription(UserVisitPK userVisitPK, DeleteInventoryConditionDescriptionForm form) {
        return CDI.current().select(DeleteInventoryConditionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryConditionUseType(UserVisitPK userVisitPK, CreateInventoryConditionUseTypeForm form) {
        return CDI.current().select(CreateInventoryConditionUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryConditionUseTypeChoices(UserVisitPK userVisitPK, GetInventoryConditionUseTypeChoicesForm form) {
        return CDI.current().select(GetInventoryConditionUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryConditionUseTypes(UserVisitPK userVisitPK, GetInventoryConditionUseTypesForm form) {
        return CDI.current().select(GetInventoryConditionUseTypesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryConditionUseTypeDescription(UserVisitPK userVisitPK, CreateInventoryConditionUseTypeDescriptionForm form) {
        return CDI.current().select(CreateInventoryConditionUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryConditionUse(UserVisitPK userVisitPK, CreateInventoryConditionUseForm form) {
        return CDI.current().select(CreateInventoryConditionUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryConditionUses(UserVisitPK userVisitPK, GetInventoryConditionUsesForm form) {
        return CDI.current().select(GetInventoryConditionUsesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultInventoryConditionUse(UserVisitPK userVisitPK, SetDefaultInventoryConditionUseForm form) {
        return CDI.current().select(SetDefaultInventoryConditionUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInventoryConditionUse(UserVisitPK userVisitPK, DeleteInventoryConditionUseForm form) {
        return CDI.current().select(DeleteInventoryConditionUseCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Inventory Location Groups
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryLocationGroup(UserVisitPK userVisitPK, CreateInventoryLocationGroupForm form) {
        return CDI.current().select(CreateInventoryLocationGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryLocationGroups(UserVisitPK userVisitPK, GetInventoryLocationGroupsForm form) {
        return CDI.current().select(GetInventoryLocationGroupsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryLocationGroup(UserVisitPK userVisitPK, GetInventoryLocationGroupForm form) {
        return CDI.current().select(GetInventoryLocationGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryLocationGroupChoices(UserVisitPK userVisitPK, GetInventoryLocationGroupChoicesForm form) {
        return CDI.current().select(GetInventoryLocationGroupChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultInventoryLocationGroup(UserVisitPK userVisitPK, SetDefaultInventoryLocationGroupForm form) {
        return CDI.current().select(SetDefaultInventoryLocationGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryLocationGroupStatusChoices(UserVisitPK userVisitPK, GetInventoryLocationGroupStatusChoicesForm form) {
        return CDI.current().select(GetInventoryLocationGroupStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setInventoryLocationGroupStatus(UserVisitPK userVisitPK, SetInventoryLocationGroupStatusForm form) {
        return CDI.current().select(SetInventoryLocationGroupStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInventoryLocationGroup(UserVisitPK userVisitPK, EditInventoryLocationGroupForm form) {
        return CDI.current().select(EditInventoryLocationGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInventoryLocationGroup(UserVisitPK userVisitPK, DeleteInventoryLocationGroupForm form) {
        return CDI.current().select(DeleteInventoryLocationGroupCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Inventory Location Group Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryLocationGroupDescription(UserVisitPK userVisitPK, CreateInventoryLocationGroupDescriptionForm form) {
        return CDI.current().select(CreateInventoryLocationGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryLocationGroupDescriptions(UserVisitPK userVisitPK, GetInventoryLocationGroupDescriptionsForm form) {
        return CDI.current().select(GetInventoryLocationGroupDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInventoryLocationGroupDescription(UserVisitPK userVisitPK, EditInventoryLocationGroupDescriptionForm form) {
        return CDI.current().select(EditInventoryLocationGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInventoryLocationGroupDescription(UserVisitPK userVisitPK, DeleteInventoryLocationGroupDescriptionForm form) {
        return CDI.current().select(DeleteInventoryLocationGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Capacities
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryLocationGroupCapacity(UserVisitPK userVisitPK, CreateInventoryLocationGroupCapacityForm form) {
        return CDI.current().select(CreateInventoryLocationGroupCapacityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInventoryLocationGroupCapacities(UserVisitPK userVisitPK, GetInventoryLocationGroupCapacitiesForm form) {
        return CDI.current().select(GetInventoryLocationGroupCapacitiesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInventoryLocationGroupCapacity(UserVisitPK userVisitPK, EditInventoryLocationGroupCapacityForm form) {
        return CDI.current().select(EditInventoryLocationGroupCapacityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInventoryLocationGroupCapacity(UserVisitPK userVisitPK, DeleteInventoryLocationGroupCapacityForm form) {
        return CDI.current().select(DeleteInventoryLocationGroupCapacityCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInventoryLocationGroupVolume(UserVisitPK userVisitPK, CreateInventoryLocationGroupVolumeForm form) {
        return CDI.current().select(CreateInventoryLocationGroupVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInventoryLocationGroupVolume(UserVisitPK userVisitPK, EditInventoryLocationGroupVolumeForm form) {
        return CDI.current().select(EditInventoryLocationGroupVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInventoryLocationGroupVolume(UserVisitPK userVisitPK, DeleteInventoryLocationGroupVolumeForm form) {
        return CDI.current().select(DeleteInventoryLocationGroupVolumeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Lots
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getLots(UserVisitPK userVisitPK, GetLotsForm form) {
        return CDI.current().select(GetLotsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLot(UserVisitPK userVisitPK, GetLotForm form) {
        return CDI.current().select(GetLotCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Lot Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createLotTimeType(UserVisitPK userVisitPK, CreateLotTimeTypeForm form) {
        return CDI.current().select(CreateLotTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotTimeTypeChoices(UserVisitPK userVisitPK, GetLotTimeTypeChoicesForm form) {
        return CDI.current().select(GetLotTimeTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotTimeType(UserVisitPK userVisitPK, GetLotTimeTypeForm form) {
        return CDI.current().select(GetLotTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotTimeTypes(UserVisitPK userVisitPK, GetLotTimeTypesForm form) {
        return CDI.current().select(GetLotTimeTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultLotTimeType(UserVisitPK userVisitPK, SetDefaultLotTimeTypeForm form) {
        return CDI.current().select(SetDefaultLotTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLotTimeType(UserVisitPK userVisitPK, EditLotTimeTypeForm form) {
        return CDI.current().select(EditLotTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteLotTimeType(UserVisitPK userVisitPK, DeleteLotTimeTypeForm form) {
        return CDI.current().select(DeleteLotTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Lot Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createLotTimeTypeDescription(UserVisitPK userVisitPK, CreateLotTimeTypeDescriptionForm form) {
        return CDI.current().select(CreateLotTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotTimeTypeDescription(UserVisitPK userVisitPK, GetLotTimeTypeDescriptionForm form) {
        return CDI.current().select(GetLotTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotTimeTypeDescriptions(UserVisitPK userVisitPK, GetLotTimeTypeDescriptionsForm form) {
        return CDI.current().select(GetLotTimeTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLotTimeTypeDescription(UserVisitPK userVisitPK, EditLotTimeTypeDescriptionForm form) {
        return CDI.current().select(EditLotTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteLotTimeTypeDescription(UserVisitPK userVisitPK, DeleteLotTimeTypeDescriptionForm form) {
        return CDI.current().select(DeleteLotTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Lot Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createLotAliasType(UserVisitPK userVisitPK, CreateLotAliasTypeForm form) {
        return CDI.current().select(CreateLotAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotAliasTypeChoices(UserVisitPK userVisitPK, GetLotAliasTypeChoicesForm form) {
        return CDI.current().select(GetLotAliasTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotAliasType(UserVisitPK userVisitPK, GetLotAliasTypeForm form) {
        return CDI.current().select(GetLotAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotAliasTypes(UserVisitPK userVisitPK, GetLotAliasTypesForm form) {
        return CDI.current().select(GetLotAliasTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultLotAliasType(UserVisitPK userVisitPK, SetDefaultLotAliasTypeForm form) {
        return CDI.current().select(SetDefaultLotAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLotAliasType(UserVisitPK userVisitPK, EditLotAliasTypeForm form) {
        return CDI.current().select(EditLotAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteLotAliasType(UserVisitPK userVisitPK, DeleteLotAliasTypeForm form) {
        return CDI.current().select(DeleteLotAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Lot Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createLotAliasTypeDescription(UserVisitPK userVisitPK, CreateLotAliasTypeDescriptionForm form) {
        return CDI.current().select(CreateLotAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotAliasTypeDescription(UserVisitPK userVisitPK, GetLotAliasTypeDescriptionForm form) {
        return CDI.current().select(GetLotAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotAliasTypeDescriptions(UserVisitPK userVisitPK, GetLotAliasTypeDescriptionsForm form) {
        return CDI.current().select(GetLotAliasTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLotAliasTypeDescription(UserVisitPK userVisitPK, EditLotAliasTypeDescriptionForm form) {
        return CDI.current().select(EditLotAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteLotAliasTypeDescription(UserVisitPK userVisitPK, DeleteLotAliasTypeDescriptionForm form) {
        return CDI.current().select(DeleteLotAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Lot Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createLotAlias(UserVisitPK userVisitPK, CreateLotAliasForm form) {
        return CDI.current().select(CreateLotAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotAlias(UserVisitPK userVisitPK, GetLotAliasForm form) {
        return CDI.current().select(GetLotAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getLotAliases(UserVisitPK userVisitPK, GetLotAliasesForm form) {
        return CDI.current().select(GetLotAliasesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editLotAlias(UserVisitPK userVisitPK, EditLotAliasForm form) {
        return CDI.current().select(EditLotAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteLotAlias(UserVisitPK userVisitPK, DeleteLotAliasForm form) {
        return CDI.current().select(DeleteLotAliasCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Inventory Levels
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyInventoryLevel(UserVisitPK userVisitPK, CreatePartyInventoryLevelForm form) {
        return CDI.current().select(CreatePartyInventoryLevelCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyInventoryLevel(UserVisitPK userVisitPK, GetPartyInventoryLevelForm form) {
        return CDI.current().select(GetPartyInventoryLevelCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartyInventoryLevels(UserVisitPK userVisitPK, GetPartyInventoryLevelsForm form) {
        return CDI.current().select(GetPartyInventoryLevelsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartyInventoryLevel(UserVisitPK userVisitPK, EditPartyInventoryLevelForm form) {
        return CDI.current().select(EditPartyInventoryLevelCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartyInventoryLevel(UserVisitPK userVisitPK, DeletePartyInventoryLevelForm form) {
        return CDI.current().select(DeletePartyInventoryLevelCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Allocation Priorities
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createAllocationPriority(UserVisitPK userVisitPK, CreateAllocationPriorityForm form) {
        return CDI.current().select(CreateAllocationPriorityCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getAllocationPriorityChoices(UserVisitPK userVisitPK, GetAllocationPriorityChoicesForm form) {
        return CDI.current().select(GetAllocationPriorityChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getAllocationPriority(UserVisitPK userVisitPK, GetAllocationPriorityForm form) {
        return CDI.current().select(GetAllocationPriorityCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getAllocationPriorities(UserVisitPK userVisitPK, GetAllocationPrioritiesForm form) {
        return CDI.current().select(GetAllocationPrioritiesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultAllocationPriority(UserVisitPK userVisitPK, SetDefaultAllocationPriorityForm form) {
        return CDI.current().select(SetDefaultAllocationPriorityCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editAllocationPriority(UserVisitPK userVisitPK, EditAllocationPriorityForm form) {
        return CDI.current().select(EditAllocationPriorityCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteAllocationPriority(UserVisitPK userVisitPK, DeleteAllocationPriorityForm form) {
        return CDI.current().select(DeleteAllocationPriorityCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Allocation Priority Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createAllocationPriorityDescription(UserVisitPK userVisitPK, CreateAllocationPriorityDescriptionForm form) {
        return CDI.current().select(CreateAllocationPriorityDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getAllocationPriorityDescription(UserVisitPK userVisitPK, GetAllocationPriorityDescriptionForm form) {
        return CDI.current().select(GetAllocationPriorityDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getAllocationPriorityDescriptions(UserVisitPK userVisitPK, GetAllocationPriorityDescriptionsForm form) {
        return CDI.current().select(GetAllocationPriorityDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editAllocationPriorityDescription(UserVisitPK userVisitPK, EditAllocationPriorityDescriptionForm form) {
        return CDI.current().select(EditAllocationPriorityDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteAllocationPriorityDescription(UserVisitPK userVisitPK, DeleteAllocationPriorityDescriptionForm form) {
        return CDI.current().select(DeleteAllocationPriorityDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Adjustment Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createInventoryAdjustmentType(UserVisitPK userVisitPK, CreateInventoryAdjustmentTypeForm form) {
        return CDI.current().select(CreateInventoryAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryAdjustmentTypes(UserVisitPK userVisitPK, GetInventoryAdjustmentTypesForm form) {
        return CDI.current().select(GetInventoryAdjustmentTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryAdjustmentType(UserVisitPK userVisitPK, GetInventoryAdjustmentTypeForm form) {
        return CDI.current().select(GetInventoryAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryAdjustmentTypeChoices(UserVisitPK userVisitPK, GetInventoryAdjustmentTypeChoicesForm form) {
        return CDI.current().select(GetInventoryAdjustmentTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultInventoryAdjustmentType(UserVisitPK userVisitPK, SetDefaultInventoryAdjustmentTypeForm form) {
        return CDI.current().select(SetDefaultInventoryAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editInventoryAdjustmentType(UserVisitPK userVisitPK, EditInventoryAdjustmentTypeForm form) {
        return CDI.current().select(EditInventoryAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteInventoryAdjustmentType(UserVisitPK userVisitPK, DeleteInventoryAdjustmentTypeForm form) {
        return CDI.current().select(DeleteInventoryAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Adjustment Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createInventoryAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateInventoryAdjustmentTypeDescriptionForm form) {
        return CDI.current().select(CreateInventoryAdjustmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryAdjustmentTypeDescriptions(UserVisitPK userVisitPK, GetInventoryAdjustmentTypeDescriptionsForm form) {
        return CDI.current().select(GetInventoryAdjustmentTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editInventoryAdjustmentTypeDescription(UserVisitPK userVisitPK, EditInventoryAdjustmentTypeDescriptionForm form) {
        return CDI.current().select(EditInventoryAdjustmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteInventoryAdjustmentTypeDescription(UserVisitPK userVisitPK, DeleteInventoryAdjustmentTypeDescriptionForm form) {
        return CDI.current().select(DeleteInventoryAdjustmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Transaction Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createInventoryTransactionType(UserVisitPK userVisitPK, CreateInventoryTransactionTypeForm form) {
        return CDI.current().select(CreateInventoryTransactionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryTransactionTypes(UserVisitPK userVisitPK, GetInventoryTransactionTypesForm form) {
        return CDI.current().select(GetInventoryTransactionTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryTransactionType(UserVisitPK userVisitPK, GetInventoryTransactionTypeForm form) {
        return CDI.current().select(GetInventoryTransactionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryTransactionTypeChoices(UserVisitPK userVisitPK, GetInventoryTransactionTypeChoicesForm form) {
        return CDI.current().select(GetInventoryTransactionTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultInventoryTransactionType(UserVisitPK userVisitPK, SetDefaultInventoryTransactionTypeForm form) {
        return CDI.current().select(SetDefaultInventoryTransactionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editInventoryTransactionType(UserVisitPK userVisitPK, EditInventoryTransactionTypeForm form) {
        return CDI.current().select(EditInventoryTransactionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteInventoryTransactionType(UserVisitPK userVisitPK, DeleteInventoryTransactionTypeForm form) {
        return CDI.current().select(DeleteInventoryTransactionTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Transaction Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createInventoryTransactionTypeDescription(UserVisitPK userVisitPK, CreateInventoryTransactionTypeDescriptionForm form) {
        return CDI.current().select(CreateInventoryTransactionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInventoryTransactionTypeDescriptions(UserVisitPK userVisitPK, GetInventoryTransactionTypeDescriptionsForm form) {
        return CDI.current().select(GetInventoryTransactionTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editInventoryTransactionTypeDescription(UserVisitPK userVisitPK, EditInventoryTransactionTypeDescriptionForm form) {
        return CDI.current().select(EditInventoryTransactionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteInventoryTransactionTypeDescription(UserVisitPK userVisitPK, DeleteInventoryTransactionTypeDescriptionForm form) {
        return CDI.current().select(DeleteInventoryTransactionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

}
