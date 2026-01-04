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

package com.echothree.control.user.authentication.server.command;

import com.echothree.control.user.authentication.common.form.AuthenticationFormFactory;
import com.echothree.control.user.authentication.common.form.GetVendorLoginDefaultsForm;
import com.echothree.control.user.authentication.common.result.AuthenticationResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class GetVendorLoginDefaultsCommand
        extends BaseSimpleCommand<GetVendorLoginDefaultsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }
    
    /** Creates a new instance of GetVendorLoginDefaultsCommand */
    public GetVendorLoginDefaultsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = AuthenticationResultFactory.getGetVendorLoginDefaultsResult();
        var userControl = getUserControl();
        var userSession = userControl.getUserSessionByUserVisit(getUserVisit());
        String username = null;
        
        if(userSession != null) {
            var party = userSession.getParty();
            
            if(party != null) {
                if(party.getLastDetail().getPartyType().getPartyTypeName().equals(PartyTypes.VENDOR.name())) {
                    var userLogin = userControl.getUserLogin(party);
                    
                    username = userLogin.getUsername();
                }
            }
        }

        var vendorLoginForm = AuthenticationFormFactory.getVendorLoginForm();
        vendorLoginForm.setUsername(username);
        result.setVendorLoginForm(vendorLoginForm);
        
        return result;
    }
    
}
