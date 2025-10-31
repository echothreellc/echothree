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

package com.echothree.control.user.geo.server.command;

import com.echothree.control.user.geo.common.form.SetDefaultGeoCodeCurrencyForm;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class SetDefaultGeoCodeCurrencyCommand
        extends BaseSimpleCommand<SetDefaultGeoCodeCurrencyForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeCurrency.name(), SecurityRoles.Edit.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of SetDefaultGeoCodeCurrencyCommand */
    public SetDefaultGeoCodeCurrencyCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeName = form.getGeoCodeName();
        var geoCode = geoControl.getGeoCodeByName(geoCodeName);
        
        if(geoCode != null) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            var currencyIsoName = form.getCurrencyIsoName();
            var currency = accountingControl.getCurrencyByIsoName(currencyIsoName);
            
            if(currency != null) {
                var geoCodeCurrencyValue = geoControl.getGeoCodeCurrencyValueForUpdate(geoCode, currency);
                
                if(geoCodeCurrencyValue != null) {
                    geoCodeCurrencyValue.setIsDefault(true);
                    geoControl.updateGeoCodeCurrencyFromValue(geoCodeCurrencyValue, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeCurrency.name(), geoCodeName, currencyIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }
        
        return null;
    }
    
}
