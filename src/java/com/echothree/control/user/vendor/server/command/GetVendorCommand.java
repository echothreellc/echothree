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

import com.echothree.control.user.vendor.common.form.GetVendorForm;
import com.echothree.control.user.vendor.common.result.VendorResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.vendor.server.logic.VendorLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.vendor.server.entity.Vendor;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.SecurityResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetVendorCommand
        extends BaseSingleEntityCommand<Vendor, GetVendorForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.VENDOR.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Vendor.name(), SecurityRoles.Review.name())
                ))
        ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("VendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    /** Creates a new instance of GetVendorCommand */
    public GetVendorCommand(UserVisitPK userVisitPK, GetVendorForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    String vendorName;
    String partyName;
    int parameterCount;

    @Override
    public SecurityResult security() {
        var securityResult = super.security();

        vendorName = form.getVendorName();
        partyName = form.getPartyName();
        parameterCount = (vendorName == null ? 0 : 1) + (partyName == null ? 0 : 1);

        if(!canSpecifyParty() && parameterCount != 0) {
            securityResult = getInsufficientSecurityResult();
        }

        return securityResult;
    }

    @Override
    protected Vendor getEntity() {
        Vendor vendor = null;

        if(parameterCount == 0) {
            var party = getParty();

            if(PartyTypes.VENDOR.name().equals(party.getLastDetail().getPartyType().getPartyTypeName())) {
                var vendorControl = Session.getModelController(VendorControl.class);

                vendor = vendorControl.getVendor(party);
            }
        } else {
            vendor = VendorLogic.getInstance().getVendorByName(this, vendorName, partyName);
        }

        if(vendor != null) {
            sendEventUsingNames(vendor.getPartyPK(), EventTypes.READ.name(), null, null, getPartyPK());
        }

        return vendor;
    }

    @Override
    protected BaseResult getTransfer(Vendor vendor) {
        var result = VendorResultFactory.getGetVendorResult();

        if(vendor != null) {
            var vendorControl = Session.getModelController(VendorControl.class);

            result.setVendor(vendorControl.getVendorTransfer(getUserVisit(), vendor));
        }

        return result;
    }

}
