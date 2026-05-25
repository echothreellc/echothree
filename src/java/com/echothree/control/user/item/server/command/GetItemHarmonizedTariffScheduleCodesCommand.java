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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.form.GetItemHarmonizedTariffScheduleCodesForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.geo.server.logic.GeoCodeLogic;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.HarmonizedTariffScheduleCodeLogic;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUseType;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemHarmonizedTariffScheduleCode;
import com.echothree.model.data.item.server.factory.ItemHarmonizedTariffScheduleCodeFactory;
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
public class GetItemHarmonizedTariffScheduleCodesCommand
        extends BasePaginatedMultipleEntitiesCommand<ItemHarmonizedTariffScheduleCode, GetItemHarmonizedTariffScheduleCodesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.ItemHarmonizedTariffScheduleCode.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("HarmonizedTariffScheduleCodeUseTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    @Inject
    GeoControl geoControl;

    @Inject
    ItemControl itemControl;

    @Inject
    GeoCodeLogic geoCodeLogic;

    @Inject
    HarmonizedTariffScheduleCodeLogic harmonizedTariffScheduleCodeLogic;

    @Inject
    ItemLogic itemLogic;

    /** Creates a new instance of GetItemHarmonizedTariffScheduleCodesCommand */
    public GetItemHarmonizedTariffScheduleCodesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private Item item;
    private GeoCode countryGeoCode;
    private HarmonizedTariffScheduleCodeUseType harmonizedTariffScheduleCodeUseType;

    @Override
    protected void handleForm() {
        var itemName = form.getItemName();
        var countryName = form.getCountryName();
        var harmonizedTariffScheduleCodeUseTypeName = form.getHarmonizedTariffScheduleCodeUseTypeName();
        var parameterCount = (itemName == null ? 0 : 1) + (countryName == null ? 0 : 1) + (harmonizedTariffScheduleCodeUseTypeName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(itemName != null) {
                item = itemLogic.getItemByName(this, itemName);
            } else if(countryName != null) {
                countryGeoCode = geoCodeLogic.getGeoCodeByAlias(this, geoControl.getGeoCodeTypeByName("COUNTRY"), geoControl.getGeoCodeScopeByName("UTILITY"), "ISO_2_LETTER", countryName);
            } else {
                harmonizedTariffScheduleCodeUseType = harmonizedTariffScheduleCodeLogic.getHarmonizedTariffScheduleCodeUseTypeByName(this, harmonizedTariffScheduleCodeUseTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(item != null) {
                total = itemControl.countItemHarmonizedTariffScheduleCodesByItem(item);
            } else if(countryGeoCode != null) {
                total = itemControl.countItemHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode);
            } else if(harmonizedTariffScheduleCodeUseType != null) {
                total = itemControl.countItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUseType(harmonizedTariffScheduleCodeUseType);
            }
        }

        return total;
    }

    @Override
    protected Collection<ItemHarmonizedTariffScheduleCode> getEntities() {
        Collection<ItemHarmonizedTariffScheduleCode> entities = null;

        if(!hasExecutionErrors()) {
            if(item != null) {
                entities = itemControl.getItemHarmonizedTariffScheduleCodesByItem(item);
            } else if(countryGeoCode != null) {
                entities = itemControl.getItemHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode);
            } else if(harmonizedTariffScheduleCodeUseType != null) {
                entities = itemControl.getItemHarmonizedTariffScheduleCodesByHarmonizedTariffScheduleCodeUse(harmonizedTariffScheduleCodeUseType);
            }
        }

        return entities;
    }

    @Override
    protected BaseResult getResult(Collection<ItemHarmonizedTariffScheduleCode> entities) {
        var result = ItemResultFactory.getGetItemHarmonizedTariffScheduleCodesResult();

        if(entities != null) {
            if(item != null) {
                result.setItem(itemControl.getItemTransfer(getUserVisit(), item));
            } else if(countryGeoCode != null) {
                result.setCountry(geoControl.getCountryTransfer(getUserVisit(), countryGeoCode));
            } else if(harmonizedTariffScheduleCodeUseType != null) {
                result.setHarmonizedTariffScheduleCodeUseType(itemControl.getHarmonizedTariffScheduleCodeUseTypeTransfer(getUserVisit(), harmonizedTariffScheduleCodeUseType));
            }

            if(session.hasLimit(ItemHarmonizedTariffScheduleCodeFactory.class)) {
                result.setItemHarmonizedTariffScheduleCodeCount(getTotalEntities());
            }

            result.setItemHarmonizedTariffScheduleCodes(itemControl.getItemHarmonizedTariffScheduleCodeTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
