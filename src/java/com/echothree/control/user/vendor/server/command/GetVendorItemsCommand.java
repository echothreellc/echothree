// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.control.user.vendor.common.result.GetVendorItemsResult;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.factory.VendorItemFactory;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetVendorItemsCommand
        extends BaseSimpleCommand<GetVendorItemsForm> {
    
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
    public GetVendorItemsCommand(UserVisitPK userVisitPK, GetVendorItemsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var vendorControl = Session.getModelController(VendorControl.class);
        GetVendorItemsResult result = VendorResultFactory.getGetVendorItemsResult();
        String vendorName = form.getVendorName();
        String itemName = form.getItemName();
        int parameterCount = (vendorName == null? 0: 1) + (itemName == null? 0: 1);
        
        if(parameterCount == 1) {
            UserVisit userVisit = getUserVisit();
            
            if(vendorName != null) {
                Vendor vendor = vendorControl.getVendorByName(vendorName);
                
                if(vendor != null) {
                    Party vendorParty = vendor.getParty();
                    
                    result.setVendor(vendorControl.getVendorTransfer(userVisit, vendor));
                    result.setVendorItems(vendorControl.getVendorItemTransfersByVendorParty(userVisit, vendorParty));
                    
                    if(session.hasLimit(VendorItemFactory.class)) {
                        result.setVendorItemCount(vendorControl.countVendorItemsByVendorParty(vendorParty));
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownVendorName.name(), vendorName);
                }
            } else if(itemName != null) {
                var itemControl = Session.getModelController(ItemControl.class);
                Item item = itemControl.getItemByNameThenAlias(itemName);
                
                if(item != null) {
                    result.setItem(itemControl.getItemTransfer(userVisit, item));
                    result.setVendorItems(vendorControl.getVendorItemTransfersByItem(userVisit, item));
                    
                    if(session.hasLimit(VendorItemFactory.class)) {
                        result.setVendorItemCount(vendorControl.countVendorItemsByItem(item));
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownItemName.name(), itemName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
