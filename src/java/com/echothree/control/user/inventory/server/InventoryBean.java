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
import com.echothree.control.user.inventory.common.result.*;
import com.echothree.control.user.inventory.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;
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
    //   Inventory Costing Methods
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<CreateInventoryCostingMethodResult> createInventoryCostingMethod(UserVisitPK userVisitPK, CreateInventoryCostingMethodForm form) {
        return CDI.current().select(CreateInventoryCostingMethodCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryCostingMethodsResult> getInventoryCostingMethods(UserVisitPK userVisitPK, GetInventoryCostingMethodsForm form) {
        return CDI.current().select(GetInventoryCostingMethodsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryCostingMethodResult> getInventoryCostingMethod(UserVisitPK userVisitPK, GetInventoryCostingMethodForm form) {
        return CDI.current().select(GetInventoryCostingMethodCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryCostingMethodChoicesResult> getInventoryCostingMethodChoices(UserVisitPK userVisitPK, GetInventoryCostingMethodChoicesForm form) {
        return CDI.current().select(GetInventoryCostingMethodChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultInventoryCostingMethod(UserVisitPK userVisitPK, SetDefaultInventoryCostingMethodForm form) {
        return CDI.current().select(SetDefaultInventoryCostingMethodCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInventoryCostingMethodResult> editInventoryCostingMethod(UserVisitPK userVisitPK, EditInventoryCostingMethodForm form) {
        return CDI.current().select(EditInventoryCostingMethodCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInventoryCostingMethod(UserVisitPK userVisitPK, DeleteInventoryCostingMethodForm form) {
        return CDI.current().select(DeleteInventoryCostingMethodCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Party Inventory Costing Methods
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createPartyInventoryCostingMethod(UserVisitPK userVisitPK, CreatePartyInventoryCostingMethodForm form) {
        return CDI.current().select(CreatePartyInventoryCostingMethodCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyInventoryCostingMethodsResult> getPartyInventoryCostingMethods(UserVisitPK userVisitPK, GetPartyInventoryCostingMethodsForm form) {
        return CDI.current().select(GetPartyInventoryCostingMethodsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyInventoryCostingMethodResult> getPartyInventoryCostingMethod(UserVisitPK userVisitPK, GetPartyInventoryCostingMethodForm form) {
        return CDI.current().select(GetPartyInventoryCostingMethodCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditPartyInventoryCostingMethodResult> editPartyInventoryCostingMethod(UserVisitPK userVisitPK, EditPartyInventoryCostingMethodForm form) {
        return CDI.current().select(EditPartyInventoryCostingMethodCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deletePartyInventoryCostingMethod(UserVisitPK userVisitPK, DeletePartyInventoryCostingMethodForm form) {
        return CDI.current().select(DeletePartyInventoryCostingMethodCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Costing Method Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createInventoryCostingMethodDescription(UserVisitPK userVisitPK, CreateInventoryCostingMethodDescriptionForm form) {
        return CDI.current().select(CreateInventoryCostingMethodDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryCostingMethodDescriptionsResult> getInventoryCostingMethodDescriptions(UserVisitPK userVisitPK, GetInventoryCostingMethodDescriptionsForm form) {
        return CDI.current().select(GetInventoryCostingMethodDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInventoryCostingMethodDescriptionResult> editInventoryCostingMethodDescription(UserVisitPK userVisitPK, EditInventoryCostingMethodDescriptionForm form) {
        return CDI.current().select(EditInventoryCostingMethodDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInventoryCostingMethodDescription(UserVisitPK userVisitPK, DeleteInventoryCostingMethodDescriptionForm form) {
        return CDI.current().select(DeleteInventoryCostingMethodDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Conditions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<CreateInventoryConditionResult> createInventoryCondition(UserVisitPK userVisitPK, CreateInventoryConditionForm form) {
        return CDI.current().select(CreateInventoryConditionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryConditionsResult> getInventoryConditions(UserVisitPK userVisitPK, GetInventoryConditionsForm form) {
        return CDI.current().select(GetInventoryConditionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryConditionResult> getInventoryCondition(UserVisitPK userVisitPK, GetInventoryConditionForm form) {
        return CDI.current().select(GetInventoryConditionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryConditionChoicesResult> getInventoryConditionChoices(UserVisitPK userVisitPK, GetInventoryConditionChoicesForm form) {
        return CDI.current().select(GetInventoryConditionChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultInventoryCondition(UserVisitPK userVisitPK, SetDefaultInventoryConditionForm form) {
        return CDI.current().select(SetDefaultInventoryConditionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInventoryConditionResult> editInventoryCondition(UserVisitPK userVisitPK, EditInventoryConditionForm form) {
        return CDI.current().select(EditInventoryConditionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInventoryCondition(UserVisitPK userVisitPK, DeleteInventoryConditionForm form) {
        return CDI.current().select(DeleteInventoryConditionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Condition Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createInventoryConditionDescription(UserVisitPK userVisitPK, CreateInventoryConditionDescriptionForm form) {
        return CDI.current().select(CreateInventoryConditionDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryConditionDescriptionsResult> getInventoryConditionDescriptions(UserVisitPK userVisitPK, GetInventoryConditionDescriptionsForm form) {
        return CDI.current().select(GetInventoryConditionDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInventoryConditionDescriptionResult> editInventoryConditionDescription(UserVisitPK userVisitPK, EditInventoryConditionDescriptionForm form) {
        return CDI.current().select(EditInventoryConditionDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInventoryConditionDescription(UserVisitPK userVisitPK, DeleteInventoryConditionDescriptionForm form) {
        return CDI.current().select(DeleteInventoryConditionDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createInventoryConditionUseType(UserVisitPK userVisitPK, CreateInventoryConditionUseTypeForm form) {
        return CDI.current().select(CreateInventoryConditionUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInventoryConditionUseTypeChoicesResult> getInventoryConditionUseTypeChoices(UserVisitPK userVisitPK, GetInventoryConditionUseTypeChoicesForm form) {
        return CDI.current().select(GetInventoryConditionUseTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInventoryConditionUseTypesResult> getInventoryConditionUseTypes(UserVisitPK userVisitPK, GetInventoryConditionUseTypesForm form) {
        return CDI.current().select(GetInventoryConditionUseTypesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createInventoryConditionUseTypeDescription(UserVisitPK userVisitPK, CreateInventoryConditionUseTypeDescriptionForm form) {
        return CDI.current().select(CreateInventoryConditionUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createInventoryConditionUse(UserVisitPK userVisitPK, CreateInventoryConditionUseForm form) {
        return CDI.current().select(CreateInventoryConditionUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInventoryConditionUsesResult> getInventoryConditionUses(UserVisitPK userVisitPK, GetInventoryConditionUsesForm form) {
        return CDI.current().select(GetInventoryConditionUsesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultInventoryConditionUse(UserVisitPK userVisitPK, SetDefaultInventoryConditionUseForm form) {
        return CDI.current().select(SetDefaultInventoryConditionUseCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteInventoryConditionUse(UserVisitPK userVisitPK, DeleteInventoryConditionUseForm form) {
        return CDI.current().select(DeleteInventoryConditionUseCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Location Groups
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateInventoryLocationGroupResult> createInventoryLocationGroup(UserVisitPK userVisitPK, CreateInventoryLocationGroupForm form) {
        return CDI.current().select(CreateInventoryLocationGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInventoryLocationGroupsResult> getInventoryLocationGroups(UserVisitPK userVisitPK, GetInventoryLocationGroupsForm form) {
        return CDI.current().select(GetInventoryLocationGroupsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInventoryLocationGroupResult> getInventoryLocationGroup(UserVisitPK userVisitPK, GetInventoryLocationGroupForm form) {
        return CDI.current().select(GetInventoryLocationGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInventoryLocationGroupChoicesResult> getInventoryLocationGroupChoices(UserVisitPK userVisitPK, GetInventoryLocationGroupChoicesForm form) {
        return CDI.current().select(GetInventoryLocationGroupChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultInventoryLocationGroup(UserVisitPK userVisitPK, SetDefaultInventoryLocationGroupForm form) {
        return CDI.current().select(SetDefaultInventoryLocationGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInventoryLocationGroupStatusChoicesResult> getInventoryLocationGroupStatusChoices(UserVisitPK userVisitPK, GetInventoryLocationGroupStatusChoicesForm form) {
        return CDI.current().select(GetInventoryLocationGroupStatusChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setInventoryLocationGroupStatus(UserVisitPK userVisitPK, SetInventoryLocationGroupStatusForm form) {
        return CDI.current().select(SetInventoryLocationGroupStatusCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditInventoryLocationGroupResult> editInventoryLocationGroup(UserVisitPK userVisitPK, EditInventoryLocationGroupForm form) {
        return CDI.current().select(EditInventoryLocationGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteInventoryLocationGroup(UserVisitPK userVisitPK, DeleteInventoryLocationGroupForm form) {
        return CDI.current().select(DeleteInventoryLocationGroupCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Inventory Location Group Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createInventoryLocationGroupDescription(UserVisitPK userVisitPK, CreateInventoryLocationGroupDescriptionForm form) {
        return CDI.current().select(CreateInventoryLocationGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInventoryLocationGroupDescriptionsResult> getInventoryLocationGroupDescriptions(UserVisitPK userVisitPK, GetInventoryLocationGroupDescriptionsForm form) {
        return CDI.current().select(GetInventoryLocationGroupDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditInventoryLocationGroupDescriptionResult> editInventoryLocationGroupDescription(UserVisitPK userVisitPK, EditInventoryLocationGroupDescriptionForm form) {
        return CDI.current().select(EditInventoryLocationGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteInventoryLocationGroupDescription(UserVisitPK userVisitPK, DeleteInventoryLocationGroupDescriptionForm form) {
        return CDI.current().select(DeleteInventoryLocationGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Capacities
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createInventoryLocationGroupCapacity(UserVisitPK userVisitPK, CreateInventoryLocationGroupCapacityForm form) {
        return CDI.current().select(CreateInventoryLocationGroupCapacityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInventoryLocationGroupCapacitiesResult> getInventoryLocationGroupCapacities(UserVisitPK userVisitPK, GetInventoryLocationGroupCapacitiesForm form) {
        return CDI.current().select(GetInventoryLocationGroupCapacitiesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditInventoryLocationGroupCapacityResult> editInventoryLocationGroupCapacity(UserVisitPK userVisitPK, EditInventoryLocationGroupCapacityForm form) {
        return CDI.current().select(EditInventoryLocationGroupCapacityCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteInventoryLocationGroupCapacity(UserVisitPK userVisitPK, DeleteInventoryLocationGroupCapacityForm form) {
        return CDI.current().select(DeleteInventoryLocationGroupCapacityCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Volumes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createInventoryLocationGroupVolume(UserVisitPK userVisitPK, CreateInventoryLocationGroupVolumeForm form) {
        return CDI.current().select(CreateInventoryLocationGroupVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditInventoryLocationGroupVolumeResult> editInventoryLocationGroupVolume(UserVisitPK userVisitPK, EditInventoryLocationGroupVolumeForm form) {
        return CDI.current().select(EditInventoryLocationGroupVolumeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteInventoryLocationGroupVolume(UserVisitPK userVisitPK, DeleteInventoryLocationGroupVolumeForm form) {
        return CDI.current().select(DeleteInventoryLocationGroupVolumeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Locations
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createInventoryLocation(UserVisitPK userVisitPK, CreateInventoryLocationForm form) {
        return CDI.current().select(CreateInventoryLocationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryLocationsResult> getInventoryLocations(UserVisitPK userVisitPK, GetInventoryLocationsForm form) {
        return CDI.current().select(GetInventoryLocationsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryLocationResult> getInventoryLocation(UserVisitPK userVisitPK, GetInventoryLocationForm form) {
        return CDI.current().select(GetInventoryLocationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInventoryLocation(UserVisitPK userVisitPK, DeleteInventoryLocationForm form) {
        return CDI.current().select(DeleteInventoryLocationCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Location Buckets
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<GetInventoryLocationBucketResult> getInventoryLocationBucket(UserVisitPK userVisitPK, GetInventoryLocationBucketForm form) {
        return CDI.current().select(GetInventoryLocationBucketCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryLocationBucketsResult> getInventoryLocationBuckets(UserVisitPK userVisitPK, GetInventoryLocationBucketsForm form) {
        return CDI.current().select(GetInventoryLocationBucketsCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Lots
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<GetLotsResult> getLots(UserVisitPK userVisitPK, GetLotsForm form) {
        return CDI.current().select(GetLotsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLotResult> getLot(UserVisitPK userVisitPK, GetLotForm form) {
        return CDI.current().select(GetLotCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Lot Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createLotTimeType(UserVisitPK userVisitPK, CreateLotTimeTypeForm form) {
        return CDI.current().select(CreateLotTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLotTimeTypeChoicesResult> getLotTimeTypeChoices(UserVisitPK userVisitPK, GetLotTimeTypeChoicesForm form) {
        return CDI.current().select(GetLotTimeTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLotTimeTypeResult> getLotTimeType(UserVisitPK userVisitPK, GetLotTimeTypeForm form) {
        return CDI.current().select(GetLotTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLotTimeTypesResult> getLotTimeTypes(UserVisitPK userVisitPK, GetLotTimeTypesForm form) {
        return CDI.current().select(GetLotTimeTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultLotTimeType(UserVisitPK userVisitPK, SetDefaultLotTimeTypeForm form) {
        return CDI.current().select(SetDefaultLotTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditLotTimeTypeResult> editLotTimeType(UserVisitPK userVisitPK, EditLotTimeTypeForm form) {
        return CDI.current().select(EditLotTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteLotTimeType(UserVisitPK userVisitPK, DeleteLotTimeTypeForm form) {
        return CDI.current().select(DeleteLotTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Lot Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createLotTimeTypeDescription(UserVisitPK userVisitPK, CreateLotTimeTypeDescriptionForm form) {
        return CDI.current().select(CreateLotTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLotTimeTypeDescriptionResult> getLotTimeTypeDescription(UserVisitPK userVisitPK, GetLotTimeTypeDescriptionForm form) {
        return CDI.current().select(GetLotTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLotTimeTypeDescriptionsResult> getLotTimeTypeDescriptions(UserVisitPK userVisitPK, GetLotTimeTypeDescriptionsForm form) {
        return CDI.current().select(GetLotTimeTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditLotTimeTypeDescriptionResult> editLotTimeTypeDescription(UserVisitPK userVisitPK, EditLotTimeTypeDescriptionForm form) {
        return CDI.current().select(EditLotTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteLotTimeTypeDescription(UserVisitPK userVisitPK, DeleteLotTimeTypeDescriptionForm form) {
        return CDI.current().select(DeleteLotTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Lot Alias Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createLotAliasType(UserVisitPK userVisitPK, CreateLotAliasTypeForm form) {
        return CDI.current().select(CreateLotAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLotAliasTypeChoicesResult> getLotAliasTypeChoices(UserVisitPK userVisitPK, GetLotAliasTypeChoicesForm form) {
        return CDI.current().select(GetLotAliasTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLotAliasTypeResult> getLotAliasType(UserVisitPK userVisitPK, GetLotAliasTypeForm form) {
        return CDI.current().select(GetLotAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLotAliasTypesResult> getLotAliasTypes(UserVisitPK userVisitPK, GetLotAliasTypesForm form) {
        return CDI.current().select(GetLotAliasTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultLotAliasType(UserVisitPK userVisitPK, SetDefaultLotAliasTypeForm form) {
        return CDI.current().select(SetDefaultLotAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditLotAliasTypeResult> editLotAliasType(UserVisitPK userVisitPK, EditLotAliasTypeForm form) {
        return CDI.current().select(EditLotAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteLotAliasType(UserVisitPK userVisitPK, DeleteLotAliasTypeForm form) {
        return CDI.current().select(DeleteLotAliasTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Lot Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createLotAliasTypeDescription(UserVisitPK userVisitPK, CreateLotAliasTypeDescriptionForm form) {
        return CDI.current().select(CreateLotAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLotAliasTypeDescriptionResult> getLotAliasTypeDescription(UserVisitPK userVisitPK, GetLotAliasTypeDescriptionForm form) {
        return CDI.current().select(GetLotAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLotAliasTypeDescriptionsResult> getLotAliasTypeDescriptions(UserVisitPK userVisitPK, GetLotAliasTypeDescriptionsForm form) {
        return CDI.current().select(GetLotAliasTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditLotAliasTypeDescriptionResult> editLotAliasTypeDescription(UserVisitPK userVisitPK, EditLotAliasTypeDescriptionForm form) {
        return CDI.current().select(EditLotAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteLotAliasTypeDescription(UserVisitPK userVisitPK, DeleteLotAliasTypeDescriptionForm form) {
        return CDI.current().select(DeleteLotAliasTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Lot Aliases
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createLotAlias(UserVisitPK userVisitPK, CreateLotAliasForm form) {
        return CDI.current().select(CreateLotAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLotAliasResult> getLotAlias(UserVisitPK userVisitPK, GetLotAliasForm form) {
        return CDI.current().select(GetLotAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetLotAliasesResult> getLotAliases(UserVisitPK userVisitPK, GetLotAliasesForm form) {
        return CDI.current().select(GetLotAliasesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditLotAliasResult> editLotAlias(UserVisitPK userVisitPK, EditLotAliasForm form) {
        return CDI.current().select(EditLotAliasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteLotAlias(UserVisitPK userVisitPK, DeleteLotAliasForm form) {
        return CDI.current().select(DeleteLotAliasCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Inventory Levels
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createPartyInventoryLevel(UserVisitPK userVisitPK, CreatePartyInventoryLevelForm form) {
        return CDI.current().select(CreatePartyInventoryLevelCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetPartyInventoryLevelResult> getPartyInventoryLevel(UserVisitPK userVisitPK, GetPartyInventoryLevelForm form) {
        return CDI.current().select(GetPartyInventoryLevelCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetPartyInventoryLevelsResult> getPartyInventoryLevels(UserVisitPK userVisitPK, GetPartyInventoryLevelsForm form) {
        return CDI.current().select(GetPartyInventoryLevelsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditPartyInventoryLevelResult> editPartyInventoryLevel(UserVisitPK userVisitPK, EditPartyInventoryLevelForm form) {
        return CDI.current().select(EditPartyInventoryLevelCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deletePartyInventoryLevel(UserVisitPK userVisitPK, DeletePartyInventoryLevelForm form) {
        return CDI.current().select(DeletePartyInventoryLevelCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Allocation Priorities
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<CreateAllocationPriorityResult> createAllocationPriority(UserVisitPK userVisitPK, CreateAllocationPriorityForm form) {
        return CDI.current().select(CreateAllocationPriorityCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetAllocationPriorityChoicesResult> getAllocationPriorityChoices(UserVisitPK userVisitPK, GetAllocationPriorityChoicesForm form) {
        return CDI.current().select(GetAllocationPriorityChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetAllocationPriorityResult> getAllocationPriority(UserVisitPK userVisitPK, GetAllocationPriorityForm form) {
        return CDI.current().select(GetAllocationPriorityCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetAllocationPrioritiesResult> getAllocationPriorities(UserVisitPK userVisitPK, GetAllocationPrioritiesForm form) {
        return CDI.current().select(GetAllocationPrioritiesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultAllocationPriority(UserVisitPK userVisitPK, SetDefaultAllocationPriorityForm form) {
        return CDI.current().select(SetDefaultAllocationPriorityCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditAllocationPriorityResult> editAllocationPriority(UserVisitPK userVisitPK, EditAllocationPriorityForm form) {
        return CDI.current().select(EditAllocationPriorityCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteAllocationPriority(UserVisitPK userVisitPK, DeleteAllocationPriorityForm form) {
        return CDI.current().select(DeleteAllocationPriorityCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Allocation Priority Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createAllocationPriorityDescription(UserVisitPK userVisitPK, CreateAllocationPriorityDescriptionForm form) {
        return CDI.current().select(CreateAllocationPriorityDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetAllocationPriorityDescriptionResult> getAllocationPriorityDescription(UserVisitPK userVisitPK, GetAllocationPriorityDescriptionForm form) {
        return CDI.current().select(GetAllocationPriorityDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetAllocationPriorityDescriptionsResult> getAllocationPriorityDescriptions(UserVisitPK userVisitPK, GetAllocationPriorityDescriptionsForm form) {
        return CDI.current().select(GetAllocationPriorityDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditAllocationPriorityDescriptionResult> editAllocationPriorityDescription(UserVisitPK userVisitPK, EditAllocationPriorityDescriptionForm form) {
        return CDI.current().select(EditAllocationPriorityDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteAllocationPriorityDescription(UserVisitPK userVisitPK, DeleteAllocationPriorityDescriptionForm form) {
        return CDI.current().select(DeleteAllocationPriorityDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Adjustment Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<CreateInventoryAdjustmentTypeResult> createInventoryAdjustmentType(UserVisitPK userVisitPK, CreateInventoryAdjustmentTypeForm form) {
        return CDI.current().select(CreateInventoryAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryAdjustmentTypesResult> getInventoryAdjustmentTypes(UserVisitPK userVisitPK, GetInventoryAdjustmentTypesForm form) {
        return CDI.current().select(GetInventoryAdjustmentTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryAdjustmentTypeResult> getInventoryAdjustmentType(UserVisitPK userVisitPK, GetInventoryAdjustmentTypeForm form) {
        return CDI.current().select(GetInventoryAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryAdjustmentTypeChoicesResult> getInventoryAdjustmentTypeChoices(UserVisitPK userVisitPK, GetInventoryAdjustmentTypeChoicesForm form) {
        return CDI.current().select(GetInventoryAdjustmentTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultInventoryAdjustmentType(UserVisitPK userVisitPK, SetDefaultInventoryAdjustmentTypeForm form) {
        return CDI.current().select(SetDefaultInventoryAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInventoryAdjustmentTypeResult> editInventoryAdjustmentType(UserVisitPK userVisitPK, EditInventoryAdjustmentTypeForm form) {
        return CDI.current().select(EditInventoryAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInventoryAdjustmentType(UserVisitPK userVisitPK, DeleteInventoryAdjustmentTypeForm form) {
        return CDI.current().select(DeleteInventoryAdjustmentTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Adjustment Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createInventoryAdjustmentTypeDescription(UserVisitPK userVisitPK, CreateInventoryAdjustmentTypeDescriptionForm form) {
        return CDI.current().select(CreateInventoryAdjustmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryAdjustmentTypeDescriptionsResult> getInventoryAdjustmentTypeDescriptions(UserVisitPK userVisitPK, GetInventoryAdjustmentTypeDescriptionsForm form) {
        return CDI.current().select(GetInventoryAdjustmentTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInventoryAdjustmentTypeDescriptionResult> editInventoryAdjustmentTypeDescription(UserVisitPK userVisitPK, EditInventoryAdjustmentTypeDescriptionForm form) {
        return CDI.current().select(EditInventoryAdjustmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInventoryAdjustmentTypeDescription(UserVisitPK userVisitPK, DeleteInventoryAdjustmentTypeDescriptionForm form) {
        return CDI.current().select(DeleteInventoryAdjustmentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Transaction Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<CreateInventoryTransactionTypeResult> createInventoryTransactionType(UserVisitPK userVisitPK, CreateInventoryTransactionTypeForm form) {
        return CDI.current().select(CreateInventoryTransactionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryTransactionTypesResult> getInventoryTransactionTypes(UserVisitPK userVisitPK, GetInventoryTransactionTypesForm form) {
        return CDI.current().select(GetInventoryTransactionTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryTransactionTypeResult> getInventoryTransactionType(UserVisitPK userVisitPK, GetInventoryTransactionTypeForm form) {
        return CDI.current().select(GetInventoryTransactionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryTransactionTypeChoicesResult> getInventoryTransactionTypeChoices(UserVisitPK userVisitPK, GetInventoryTransactionTypeChoicesForm form) {
        return CDI.current().select(GetInventoryTransactionTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultInventoryTransactionType(UserVisitPK userVisitPK, SetDefaultInventoryTransactionTypeForm form) {
        return CDI.current().select(SetDefaultInventoryTransactionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInventoryTransactionTypeResult> editInventoryTransactionType(UserVisitPK userVisitPK, EditInventoryTransactionTypeForm form) {
        return CDI.current().select(EditInventoryTransactionTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInventoryTransactionType(UserVisitPK userVisitPK, DeleteInventoryTransactionTypeForm form) {
        return CDI.current().select(DeleteInventoryTransactionTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Transaction Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createInventoryTransactionTypeDescription(UserVisitPK userVisitPK, CreateInventoryTransactionTypeDescriptionForm form) {
        return CDI.current().select(CreateInventoryTransactionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryTransactionTypeDescriptionsResult> getInventoryTransactionTypeDescriptions(UserVisitPK userVisitPK, GetInventoryTransactionTypeDescriptionsForm form) {
        return CDI.current().select(GetInventoryTransactionTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInventoryTransactionTypeDescriptionResult> editInventoryTransactionTypeDescription(UserVisitPK userVisitPK, EditInventoryTransactionTypeDescriptionForm form) {
        return CDI.current().select(EditInventoryTransactionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInventoryTransactionTypeDescription(UserVisitPK userVisitPK, DeleteInventoryTransactionTypeDescriptionForm form) {
        return CDI.current().select(DeleteInventoryTransactionTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Transaction Time Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<CreateInventoryTransactionTimeTypeResult> createInventoryTransactionTimeType(UserVisitPK userVisitPK,
            CreateInventoryTransactionTimeTypeForm form) {
        return CDI.current().select(CreateInventoryTransactionTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryTransactionTimeTypesResult> getInventoryTransactionTimeTypes(UserVisitPK userVisitPK,
            GetInventoryTransactionTimeTypesForm form) {
        return CDI.current().select(GetInventoryTransactionTimeTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryTransactionTimeTypeResult> getInventoryTransactionTimeType(UserVisitPK userVisitPK,
            GetInventoryTransactionTimeTypeForm form) {
        return CDI.current().select(GetInventoryTransactionTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryTransactionTimeTypeChoicesResult> getInventoryTransactionTimeTypeChoices(UserVisitPK userVisitPK,
            GetInventoryTransactionTimeTypeChoicesForm form) {
        return CDI.current().select(GetInventoryTransactionTimeTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultInventoryTransactionTimeType(UserVisitPK userVisitPK,
            SetDefaultInventoryTransactionTimeTypeForm form) {
        return CDI.current().select(SetDefaultInventoryTransactionTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInventoryTransactionTimeTypeResult> editInventoryTransactionTimeType(UserVisitPK userVisitPK,
            EditInventoryTransactionTimeTypeForm form) {
        return CDI.current().select(EditInventoryTransactionTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInventoryTransactionTimeType(UserVisitPK userVisitPK, DeleteInventoryTransactionTimeTypeForm form) {
        return CDI.current().select(DeleteInventoryTransactionTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Transaction Time Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createInventoryTransactionTimeTypeDescription(UserVisitPK userVisitPK, CreateInventoryTransactionTimeTypeDescriptionForm form) {
        return CDI.current().select(CreateInventoryTransactionTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryTransactionTimeTypeDescriptionsResult> getInventoryTransactionTimeTypeDescriptions(UserVisitPK userVisitPK, GetInventoryTransactionTimeTypeDescriptionsForm form) {
        return CDI.current().select(GetInventoryTransactionTimeTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInventoryTransactionTimeTypeDescriptionResult> editInventoryTransactionTimeTypeDescription(UserVisitPK userVisitPK, EditInventoryTransactionTimeTypeDescriptionForm form) {
        return CDI.current().select(EditInventoryTransactionTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInventoryTransactionTimeTypeDescription(UserVisitPK userVisitPK, DeleteInventoryTransactionTimeTypeDescriptionForm form) {
        return CDI.current().select(DeleteInventoryTransactionTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Transaction Role Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<CreateInventoryTransactionRoleTypeResult> createInventoryTransactionRoleType(UserVisitPK userVisitPK, CreateInventoryTransactionRoleTypeForm form) {
        return CDI.current().select(CreateInventoryTransactionRoleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryTransactionRoleTypesResult> getInventoryTransactionRoleTypes(UserVisitPK userVisitPK, GetInventoryTransactionRoleTypesForm form) {
        return CDI.current().select(GetInventoryTransactionRoleTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryTransactionRoleTypeResult> getInventoryTransactionRoleType(UserVisitPK userVisitPK, GetInventoryTransactionRoleTypeForm form) {
        return CDI.current().select(GetInventoryTransactionRoleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryTransactionRoleTypeChoicesResult> getInventoryTransactionRoleTypeChoices(UserVisitPK userVisitPK, GetInventoryTransactionRoleTypeChoicesForm form) {
        return CDI.current().select(GetInventoryTransactionRoleTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultInventoryTransactionRoleType(UserVisitPK userVisitPK, SetDefaultInventoryTransactionRoleTypeForm form) {
        return CDI.current().select(SetDefaultInventoryTransactionRoleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInventoryTransactionRoleTypeResult> editInventoryTransactionRoleType(UserVisitPK userVisitPK, EditInventoryTransactionRoleTypeForm form) {
        return CDI.current().select(EditInventoryTransactionRoleTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInventoryTransactionRoleType(UserVisitPK userVisitPK, DeleteInventoryTransactionRoleTypeForm form) {
        return CDI.current().select(DeleteInventoryTransactionRoleTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Transaction Role Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createInventoryTransactionRoleTypeDescription(UserVisitPK userVisitPK, CreateInventoryTransactionRoleTypeDescriptionForm form) {
        return CDI.current().select(CreateInventoryTransactionRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryTransactionRoleTypeDescriptionsResult> getInventoryTransactionRoleTypeDescriptions(UserVisitPK userVisitPK, GetInventoryTransactionRoleTypeDescriptionsForm form) {
        return CDI.current().select(GetInventoryTransactionRoleTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInventoryTransactionRoleTypeDescriptionResult> editInventoryTransactionRoleTypeDescription(UserVisitPK userVisitPK, EditInventoryTransactionRoleTypeDescriptionForm form) {
        return CDI.current().select(EditInventoryTransactionRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInventoryTransactionRoleTypeDescription(UserVisitPK userVisitPK, DeleteInventoryTransactionRoleTypeDescriptionForm form) {
        return CDI.current().select(DeleteInventoryTransactionRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Bucket Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<CreateInventoryBucketTypeResult> createInventoryBucketType(UserVisitPK userVisitPK, CreateInventoryBucketTypeForm form) {
        return CDI.current().select(CreateInventoryBucketTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryBucketTypesResult> getInventoryBucketTypes(UserVisitPK userVisitPK, GetInventoryBucketTypesForm form) {
        return CDI.current().select(GetInventoryBucketTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryBucketTypeResult> getInventoryBucketType(UserVisitPK userVisitPK, GetInventoryBucketTypeForm form) {
        return CDI.current().select(GetInventoryBucketTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryBucketTypeChoicesResult> getInventoryBucketTypeChoices(UserVisitPK userVisitPK, GetInventoryBucketTypeChoicesForm form) {
        return CDI.current().select(GetInventoryBucketTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultInventoryBucketType(UserVisitPK userVisitPK, SetDefaultInventoryBucketTypeForm form) {
        return CDI.current().select(SetDefaultInventoryBucketTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInventoryBucketTypeResult> editInventoryBucketType(UserVisitPK userVisitPK, EditInventoryBucketTypeForm form) {
        return CDI.current().select(EditInventoryBucketTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInventoryBucketType(UserVisitPK userVisitPK, DeleteInventoryBucketTypeForm form) {
        return CDI.current().select(DeleteInventoryBucketTypeCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Inventory Bucket Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createInventoryBucketTypeDescription(UserVisitPK userVisitPK, CreateInventoryBucketTypeDescriptionForm form) {
        return CDI.current().select(CreateInventoryBucketTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInventoryBucketTypeDescriptionsResult> getInventoryBucketTypeDescriptions(UserVisitPK userVisitPK, GetInventoryBucketTypeDescriptionsForm form) {
        return CDI.current().select(GetInventoryBucketTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInventoryBucketTypeDescriptionResult> editInventoryBucketTypeDescription(UserVisitPK userVisitPK, EditInventoryBucketTypeDescriptionForm form) {
        return CDI.current().select(EditInventoryBucketTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInventoryBucketTypeDescription(UserVisitPK userVisitPK, DeleteInventoryBucketTypeDescriptionForm form) {
        return CDI.current().select(DeleteInventoryBucketTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Buckets
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<GetPartyBucketResult> getPartyBucket(UserVisitPK userVisitPK, GetPartyBucketForm form) {
        return CDI.current().select(GetPartyBucketCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetPartyBucketsResult> getPartyBuckets(UserVisitPK userVisitPK, GetPartyBucketsForm form) {
        return CDI.current().select(GetPartyBucketsCommand.class).get().run(userVisitPK, form);
    }

}
