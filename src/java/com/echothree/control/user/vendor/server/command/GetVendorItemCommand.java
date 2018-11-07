// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.vendor.common.form.GetVendorItemForm;
import com.echothree.control.user.vendor.common.result.GetVendorItemResult;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.model.data.vendor.server.entity.VendorItem;
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

public class GetVendorItemCommand
        extends BaseSimpleCommand<GetVendorItemForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.VendorItem.name(), SecurityRoles.Review.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("VendorItemName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of GetVendorItemCommand */
    public GetVendorItemCommand(UserVisitPK userVisitPK, GetVendorItemForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    @Override
    protected BaseResult execute() {
        GetVendorItemResult result = VendorResultFactory.getGetVendorItemResult();
        String vendorName = form.getVendorName();
        String partyName = form.getPartyName();
        int parameterCount = (vendorName == null? 0: 1) + (partyName == null? 0: 1);

        if(parameterCount == 1) {
            VendorControl vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
            Vendor vendor = null;

            if(vendorName != null) {
                vendor = vendorControl.getVendorByName(vendorName);

                if(vendor == null) {
                    addExecutionError(ExecutionErrors.UnknownVendorName.name(), vendorName);
                }
            } else if(partyName != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                Party party = partyControl.getPartyByName(partyName);

                if(party != null) {
                    if(party.getLastDetail().getPartyType().getPartyTypeName().equals(PartyConstants.PartyType_VENDOR)) {
                        vendor = vendorControl.getVendor(party);
                    } else {
                        addExecutionError(ExecutionErrors.InvalidPartyType.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            }

            if(vendor != null) {
                Party vendorParty = vendor.getParty();
                String vendorItemName = form.getVendorItemName();
                VendorItem vendorItem = vendorControl.getVendorItemByVendorPartyAndVendorItemName(vendorParty, vendorItemName);

                if(vendorItem != null) {
                    result.setVendorItem(vendorControl.getVendorItemTransfer(getUserVisit(), vendorItem));
                } else {
                    addExecutionError(ExecutionErrors.UnknownVendorItemName.name(), vendorName, vendorItemName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return result;
    }

}
