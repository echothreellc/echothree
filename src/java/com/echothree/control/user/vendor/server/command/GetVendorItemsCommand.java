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

package com.echothree.control.user.vendor.server.command;

import com.echothree.control.user.vendor.common.form.GetVendorItemsForm;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.vendor.server.logic.VendorLogic;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.model.data.vendor.server.factory.VendorItemFactory;
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
public class GetVendorItemsCommand
        extends BasePaginatedMultipleEntitiesCommand<VendorItem, GetVendorItemsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.VendorItem.name(), SecurityRoles.List.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetVendorItemsCommand */
    public GetVendorItemsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Inject
    ItemControl itemControl;

    @Inject
    VendorControl vendorControl;

    @Inject
    ItemLogic itemLogic;

    @Inject
    VendorLogic vendorLogic;

    private Vendor vendor;
    private Item item;

    @Override
    protected void handleForm() {
        var vendorName = form.getVendorName();
        var itemName = form.getItemName();
        var parameterCount = (vendorName == null ? 0 : 1) + (itemName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(vendorName != null) {
                vendor = vendorLogic.getVendorByName(this, vendorName, null, null);
            } else {
                item = itemLogic.getItemByNameThenAlias(this, itemName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(vendor != null) {
                total = vendorControl.countVendorItemsByVendorParty(vendor.getParty());
            } else {
                total = vendorControl.countVendorItemsByItem(item);
            }
        }

        return total;
    }

    @Override
    protected Collection<VendorItem> getEntities() {
        Collection<VendorItem> entities = null;

        if(!hasExecutionErrors()) {
            if(vendor != null) {
                entities = vendorControl.getVendorItemsByVendorParty(vendor.getParty());
            } else {
                entities = vendorControl.getVendorItemsByItem(item);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<VendorItem> entities) {
        var result = VendorResultFactory.getGetVendorItemsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(vendor != null) {
                result.setVendor(vendorControl.getVendorTransfer(userVisit, vendor));
            } else if(item != null) {
                result.setItem(itemControl.getItemTransfer(userVisit, item));
            }

            if(session.hasLimit(VendorItemFactory.class)) {
                result.setVendorItemCount(getTotalEntities());
            }

            result.setVendorItems(vendorControl.getVendorItemTransfers(userVisit, entities));
        }

        return result;
    }

}
