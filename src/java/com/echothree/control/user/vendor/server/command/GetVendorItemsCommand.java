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
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.model.data.vendor.server.factory.VendorItemFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetVendorItemsCommand
        extends BaseMultipleEntitiesCommand<VendorItem, GetVendorItemsForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.VendorItem.name(), SecurityRoles.List.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetVendorItemsCommand */
    public GetVendorItemsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    Vendor vendor;
    Item item;

    @Override
    protected Collection<VendorItem> getEntities() {
        var vendorControl = Session.getModelController(VendorControl.class);
        var vendorName = form.getVendorName();
        var itemName = form.getItemName();
        var parameterCount = (vendorName == null ? 0 : 1) + (itemName == null ? 0 : 1);
        Collection<VendorItem> entities = null;

        if(parameterCount == 1) {
            if(vendorName != null) {
                vendor = VendorLogic.getInstance().getVendorByName(this, vendorName, null, null);

                if(!hasExecutionErrors()) {
                    var vendorParty = vendor.getParty();

                    entities = vendorControl.getVendorItemsByVendorParty(vendorParty);
                }
            } else if(itemName != null) {
                item = ItemLogic.getInstance().getItemByNameThenAlias(this, itemName);

                if(!hasExecutionErrors()) {
                    entities = vendorControl.getVendorItemsByItem(item);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<VendorItem> entities) {
        var result = VendorResultFactory.getGetVendorItemsResult();

        if(entities != null) {
            var vendorControl = Session.getModelController(VendorControl.class);
            var userVisit = getUserVisit();

            result.setVendorItems(vendorControl.getVendorItemTransfers(userVisit, entities));

            if(vendor != null) {
                var vendorParty = vendor.getParty();

                result.setVendor(vendorControl.getVendorTransfer(userVisit, vendor));

                if(session.hasLimit(VendorItemFactory.class)) {
                    result.setVendorItemCount(vendorControl.countVendorItemsByVendorParty(vendorParty));
                }
            } else if(item != null) {
                var itemControl = Session.getModelController(ItemControl.class);

                result.setItem(itemControl.getItemTransfer(userVisit, item));

                if(session.hasLimit(VendorItemFactory.class)) {
                    result.setVendorItemCount(vendorControl.countVendorItemsByItem(item));
                }
            }
        }

        return result;
    }

}
