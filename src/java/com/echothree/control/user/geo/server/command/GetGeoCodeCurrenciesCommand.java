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

package com.echothree.control.user.geo.server.command;

import com.echothree.control.user.geo.common.form.GetGeoCodeCurrenciesForm;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeCurrency;
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
public class GetGeoCodeCurrenciesCommand
        extends BasePaginatedMultipleEntitiesCommand<GeoCodeCurrency, GetGeoCodeCurrenciesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeCurrency.name(), SecurityRoles.List.name())
                        ))
                ));
        
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CurrencyIsoName", FieldType.ENTITY_NAME, false, null, null)
                );
    }
    
    /** Creates a new instance of GetGeoCodeCurrenciesCommand */
    public GetGeoCodeCurrenciesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Inject
    GeoControl geoControl;

    @Inject
    AccountingControl accountingControl;

    GeoCode geoCode;
    Currency currency;

    @Override
    protected void handleForm() {
        var geoCodeName = form.getGeoCodeName();
        var currencyIsoName = form.getCurrencyIsoName();
        var parameterCount = (geoCodeName != null ? 1 : 0) + (currencyIsoName != null ? 1 : 0);

        if(parameterCount == 1) {
            if(geoCodeName != null) {
                geoCode = geoControl.getGeoCodeByName(geoCodeName);

                if(geoCode == null) {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
                }
            } else {
                currency = accountingControl.getCurrencyByIsoName(currencyIsoName);

                if(currency == null) {
                    addExecutionError(ExecutionErrors.UnknownCurrencyIsoName.name(), currencyIsoName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long totalEntities = null;

        if(!hasExecutionErrors()) {
            if(geoCode != null) {
                totalEntities = geoControl.countGeoCodeCurrenciesByGeoCode(geoCode);
            } else {
                totalEntities = geoControl.countGeoCodeCurrenciesByCurrency(currency);
            }
        }

        return totalEntities;
    }

    @Override
    protected Collection<GeoCodeCurrency> getEntities() {
        Collection<GeoCodeCurrency> entities = null;

        if(!hasExecutionErrors()) {
            if(geoCode != null) {
                entities = geoControl.getGeoCodeCurrenciesByGeoCode(geoCode);
            } else {
                entities = geoControl.getGeoCodeCurrenciesByCurrency(currency);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<GeoCodeCurrency> entities) {
        var result = GeoResultFactory.getGetGeoCodeCurrenciesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(geoCode != null) {
                result.setGeoCode(geoControl.getGeoCodeTransfer(userVisit, geoCode));
            } else {
                result.setCurrency(accountingControl.getCurrencyTransfer(userVisit, currency));
            }

            result.setGeoCodeCurrencies(geoControl.getGeoCodeCurrencyTransfers(userVisit, entities));
        }

        return result;
    }
    
}
