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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.form.GetPartyBucketsForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.server.command.common.PartyInventoryLevelUtil;
import com.echothree.model.control.inventory.server.control.BucketControl;
import com.echothree.model.control.inventory.server.control.InventoryBucketTypeControl;
import com.echothree.model.control.inventory.server.control.InventoryConditionControl;
import com.echothree.model.control.inventory.server.logic.InventoryBucketTypeLogic;
import com.echothree.model.control.inventory.server.logic.InventoryConditionLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.PartyBucket;
import com.echothree.model.data.inventory.server.factory.PartyBucketFactory;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyBucketsCommand
        extends BasePaginatedMultipleEntitiesCommand<PartyBucket, GetPartyBucketsForm> {

    private static final CommandSecurityDefinition COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
            new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
            new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                    new SecurityRoleDefinition(SecurityRoleGroups.PartyBucket.name(), SecurityRoles.List.name())
            ))
    ));

    private static final List<FieldDefinition> FORM_FIELD_DEFINITIONS = List.of(
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("UnitOfMeasureKindName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("InventoryConditionName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("InventoryBucketTypeName", FieldType.ENTITY_NAME, false, null, null)
    );

    @Inject
    BucketControl bucketControl;

    @Inject
    PartyInventoryLevelUtil partyInventoryLevelUtil;

    @Inject
    ItemLogic itemLogic;

    @Inject
    UnitOfMeasureTypeLogic unitOfMeasureTypeLogic;

    @Inject
    InventoryConditionLogic inventoryConditionLogic;

    @Inject
    InventoryBucketTypeLogic inventoryBucketTypeLogic;

    @Inject
    PartyControl partyControl;

    @Inject
    ItemControl itemControl;

    @Inject
    UomControl uomControl;

    @Inject
    InventoryConditionControl inventoryConditionControl;

    @Inject
    InventoryBucketTypeControl inventoryBucketTypeControl;

    private Party party;
    private Item item;
    private UnitOfMeasureType unitOfMeasureType;
    private InventoryCondition inventoryCondition;
    private InventoryBucketType inventoryBucketType;

    public GetPartyBucketsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        var partyName = form.getPartyName();
        var companyName = form.getCompanyName();
        var warehouseName = form.getWarehouseName();
        var itemName = form.getItemName();
        var unitOfMeasureKindName = form.getUnitOfMeasureKindName();
        var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
        var inventoryConditionName = form.getInventoryConditionName();
        var inventoryBucketTypeName = form.getInventoryBucketTypeName();
        var hasCompleteUnitOfMeasureType = unitOfMeasureKindName != null && unitOfMeasureTypeName != null;
        var hasPartialUnitOfMeasureType = (unitOfMeasureKindName == null) != (unitOfMeasureTypeName == null);
        var parameterCount = (partyName == null ? 0 : 1) + (companyName == null ? 0 : 1)
                + (warehouseName == null ? 0 : 1) + (itemName == null ? 0 : 1)
                + (hasCompleteUnitOfMeasureType ? 1 : 0) + (inventoryConditionName == null ? 0 : 1)
                + (inventoryBucketTypeName == null ? 0 : 1);

        if(parameterCount == 1 && !hasPartialUnitOfMeasureType) {
            if(itemName != null) {
                item = itemLogic.getItemByName(this, itemName);
            } else if(hasCompleteUnitOfMeasureType) {
                unitOfMeasureType = unitOfMeasureTypeLogic.getUnitOfMeasureTypeByName(this, unitOfMeasureKindName, unitOfMeasureTypeName);
            } else if(inventoryConditionName != null) {
                inventoryCondition = inventoryConditionLogic.getInventoryConditionByName(this, inventoryConditionName);
            } else if(inventoryBucketTypeName != null) {
                inventoryBucketType = inventoryBucketTypeLogic.getInventoryBucketTypeByName(this, inventoryBucketTypeName);
            } else {
                party = partyInventoryLevelUtil.getParty(this, partyName, companyName, warehouseName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null :
                switch(party != null ? party : item != null ? item : unitOfMeasureType != null ? unitOfMeasureType
                        : inventoryCondition != null ? inventoryCondition : inventoryBucketType) {
                    case Party filterParty -> bucketControl.countPartyBucketsByParty(filterParty);
                    case Item filterItem -> bucketControl.countPartyBucketsByItem(filterItem);
                    case UnitOfMeasureType filterUnitOfMeasureType ->
                            bucketControl.countPartyBucketsByUnitOfMeasureType(filterUnitOfMeasureType);
                    case InventoryCondition filterInventoryCondition ->
                            bucketControl.countPartyBucketsByInventoryCondition(filterInventoryCondition);
                    case InventoryBucketType filterInventoryBucketType ->
                            bucketControl.countPartyBucketsByInventoryBucketType(filterInventoryBucketType);
                    default -> null;
                };
    }

    @Override
    protected Collection<PartyBucket> getEntities() {
        return hasExecutionErrors() ? null :
                switch(party != null ? party : item != null ? item : unitOfMeasureType != null ? unitOfMeasureType
                        : inventoryCondition != null ? inventoryCondition : inventoryBucketType) {
                    case Party filterParty -> bucketControl.getPartyBucketsByParty(filterParty);
                    case Item filterItem -> bucketControl.getPartyBucketsByItem(filterItem);
                    case UnitOfMeasureType filterUnitOfMeasureType ->
                            bucketControl.getPartyBucketsByUnitOfMeasureType(filterUnitOfMeasureType);
                    case InventoryCondition filterInventoryCondition ->
                            bucketControl.getPartyBucketsByInventoryCondition(filterInventoryCondition);
                    case InventoryBucketType filterInventoryBucketType ->
                            bucketControl.getPartyBucketsByInventoryBucketType(filterInventoryBucketType);
                    default -> null;
                };
    }

    @Override
    protected BaseResult getResult(Collection<PartyBucket> entities) {
        var result = InventoryResultFactory.getGetPartyBucketsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(party != null) {
                result.setParty(partyControl.getPartyTransfer(userVisit, party));
            } else if(item != null) {
                result.setItem(itemControl.getItemTransfer(userVisit, item));
            } else if(unitOfMeasureType != null) {
                result.setUnitOfMeasureType(uomControl.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureType));
            } else if(inventoryCondition != null) {
                result.setInventoryCondition(inventoryConditionControl.getInventoryConditionTransfer(userVisit, inventoryCondition));
            } else {
                result.setInventoryBucketType(inventoryBucketTypeControl.getInventoryBucketTypeTransfer(userVisit, inventoryBucketType));
            }

            if(session.hasLimit(PartyBucketFactory.class)) {
                result.setPartyBucketCount(getTotalEntities());
            }

            result.setPartyBuckets(bucketControl.getPartyBucketTransfers(userVisit, entities));
        }

        return result;
    }

}
