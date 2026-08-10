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

package com.echothree.control.user.tax.server.command;

import com.echothree.control.user.tax.common.form.GetItemTaxClassificationsForm;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.tax.server.entity.ItemTaxClassification;
import com.echothree.model.data.tax.server.factory.ItemTaxClassificationFactory;
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
public class GetItemTaxClassificationsCommand
        extends BasePaginatedMultipleEntitiesCommand<ItemTaxClassification, GetItemTaxClassificationsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemTaxClassification.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    GeoControl geoControl;

    @Inject
    ItemControl itemControl;

    @Inject
    TaxControl taxControl;

    @Inject
    ItemLogic itemLogic;

    
    /** Creates a new instance of GetItemTaxClassificationsCommand */
    public GetItemTaxClassificationsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    private Item item;
    private GeoCode countryGeoCode;

    @Override
    protected void handleForm() {
        var itemName = form.getItemName();
        var countryName = form.getCountryName();
        var parameterCount = (itemName == null ? 0 : 1) + (countryName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(itemName != null) {
                item = itemLogic.getItemByName(this, itemName);
            } else {
                countryGeoCode = geoControl.getCountryByAlias(countryName);

                if(countryGeoCode == null) {
                    addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : item == null
                ? taxControl.countItemTaxClassificationByCountryGeoCode(countryGeoCode)
                : taxControl.countItemTaxClassificationByItem(item);
    }

    @Override
    protected Collection<ItemTaxClassification> getEntities() {
        return hasExecutionErrors() ? null : item == null
                ? taxControl.getItemTaxClassificationsByCountryGeoCode(countryGeoCode)
                : taxControl.getItemTaxClassificationsByItem(item);
    }

    @Override
    protected BaseResult getResult(Collection<ItemTaxClassification> entities) {
        var result = TaxResultFactory.getGetItemTaxClassificationsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(item == null) {
                result.setCountry(geoControl.getCountryTransfer(userVisit, countryGeoCode));
            } else {
                result.setItem(itemControl.getItemTransfer(userVisit, item));
            }

            if(session.hasLimit(ItemTaxClassificationFactory.class)) {
                result.setItemTaxClassificationCount(getTotalEntities());
            }

            result.setItemTaxClassifications(taxControl.getItemTaxClassificationTransfers(userVisit, entities));
        }

        return result;
    }
    
}
