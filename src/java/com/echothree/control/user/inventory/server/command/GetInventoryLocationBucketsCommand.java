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

import com.echothree.control.user.inventory.common.form.GetInventoryLocationBucketsForm;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.model.control.inventory.server.control.BucketControl;
import com.echothree.model.control.inventory.server.control.InventoryBucketTypeControl;
import com.echothree.model.control.inventory.server.control.InventoryLocationControl;
import com.echothree.model.control.inventory.server.logic.InventoryBucketTypeLogic;
import com.echothree.model.control.inventory.server.logic.InventoryLocationLogic;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.warehouse.server.logic.LocationLogic;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
import com.echothree.model.data.inventory.server.entity.InventoryLocation;
import com.echothree.model.data.inventory.server.entity.InventoryLocationBucket;
import com.echothree.model.data.inventory.server.factory.InventoryLocationBucketFactory;
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
public class GetInventoryLocationBucketsCommand
        extends BasePaginatedMultipleEntitiesCommand<InventoryLocationBucket, GetInventoryLocationBucketsForm> {

    private static final CommandSecurityDefinition COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
            new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
            new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                    new SecurityRoleDefinition(SecurityRoleGroups.InventoryLocationBucket.name(), SecurityRoles.List.name())
            ))
    ));

    private static final List<FieldDefinition> FORM_FIELD_DEFINITIONS = List.of(
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("WarehouseName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("LocationName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("InventoryBucketTypeName", FieldType.ENTITY_NAME, false, null, null)
    );

    @Inject
    BucketControl bucketControl;

    @Inject
    InventoryLocationControl inventoryLocationControl;

    @Inject
    InventoryBucketTypeControl inventoryBucketTypeControl;

    @Inject
    LocationLogic locationLogic;

    @Inject
    ItemLogic itemLogic;

    @Inject
    InventoryLocationLogic inventoryLocationLogic;

    @Inject
    InventoryBucketTypeLogic inventoryBucketTypeLogic;

    private InventoryLocation inventoryLocation;
    private InventoryBucketType inventoryBucketType;

    public GetInventoryLocationBucketsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        var partyName = form.getPartyName();
        var warehouseName = form.getWarehouseName();
        var locationName = form.getLocationName();
        var itemName = form.getItemName();
        var inventoryBucketTypeName = form.getInventoryBucketTypeName();
        var warehouseParameterCount = (partyName == null ? 0 : 1) + (warehouseName == null ? 0 : 1);
        var hasAnyInventoryLocationParameter = warehouseParameterCount != 0 || locationName != null || itemName != null;
        var hasCompleteInventoryLocation = warehouseParameterCount == 1 && locationName != null && itemName != null;
        var selectorCount = (hasCompleteInventoryLocation ? 1 : 0) + (inventoryBucketTypeName == null ? 0 : 1);

        if((hasAnyInventoryLocationParameter && !hasCompleteInventoryLocation) || selectorCount != 1) {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        } else if(hasCompleteInventoryLocation) {
            var location = locationLogic.getLocation(this, partyName, warehouseName, locationName);
            var item = itemLogic.getItemByName(this, itemName);

            if(!hasExecutionErrors()) {
                inventoryLocation = inventoryLocationLogic.getInventoryLocation(this, location, item);
            }
        } else {
            inventoryBucketType = inventoryBucketTypeLogic.getInventoryBucketTypeByName(this,
                    inventoryBucketTypeName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : inventoryLocation != null
                ? bucketControl.countInventoryLocationBucketsByInventoryLocation(inventoryLocation)
                : bucketControl.countInventoryLocationBucketsByInventoryBucketType(inventoryBucketType);
    }

    @Override
    protected Collection<InventoryLocationBucket> getEntities() {
        return hasExecutionErrors() ? null : inventoryLocation != null
                ? bucketControl.getInventoryLocationBucketsByInventoryLocation(inventoryLocation)
                : bucketControl.getInventoryLocationBucketsByInventoryBucketType(inventoryBucketType);
    }

    @Override
    protected BaseResult getResult(Collection<InventoryLocationBucket> entities) {
        var result = InventoryResultFactory.getGetInventoryLocationBucketsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(inventoryLocation != null) {
                result.setInventoryLocation(inventoryLocationControl.getInventoryLocationTransfer(userVisit,
                        inventoryLocation));
            } else {
                result.setInventoryBucketType(inventoryBucketTypeControl.getInventoryBucketTypeTransfer(userVisit,
                        inventoryBucketType));
            }

            if(session.hasLimit(InventoryLocationBucketFactory.class)) {
                result.setInventoryLocationBucketCount(getTotalEntities());
            }

            result.setInventoryLocationBuckets(bucketControl.getInventoryLocationBucketTransfers(userVisit, entities));
        }

        return result;
    }

}
