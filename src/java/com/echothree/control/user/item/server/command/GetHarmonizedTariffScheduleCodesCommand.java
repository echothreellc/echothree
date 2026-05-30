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

import com.echothree.control.user.item.common.form.GetHarmonizedTariffScheduleCodesForm;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCode;
import com.echothree.model.data.item.server.factory.HarmonizedTariffScheduleCodeFactory;
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
public class GetHarmonizedTariffScheduleCodesCommand
        extends BasePaginatedMultipleEntitiesCommand<HarmonizedTariffScheduleCode, GetHarmonizedTariffScheduleCodesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.HarmonizedTariffScheduleCode.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    GeoControl geoControl;

    @Inject
    ItemControl itemControl;

    private GeoCode countryGeoCode;

    /** Creates a new instance of GetHarmonizedTariffScheduleCodesCommand */
    public GetHarmonizedTariffScheduleCodesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        var countryName = form.getCountryName();

        countryGeoCode = geoControl.getCountryByAlias(countryName);

        if(countryGeoCode == null) {
            addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
        }
    }

    @Override
    protected Long getTotalEntities() {
        return countryGeoCode == null ? null : itemControl.countHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode);
    }

    @Override
    protected Collection<HarmonizedTariffScheduleCode> getEntities() {
        return countryGeoCode == null ? null : itemControl.getHarmonizedTariffScheduleCodesByCountryGeoCode(countryGeoCode);
    }

    @Override
    protected BaseResult getResult(Collection<HarmonizedTariffScheduleCode> entities) {
        var result = ItemResultFactory.getGetHarmonizedTariffScheduleCodesResult();

        if(entities != null) {
            result.setCountry(geoControl.getCountryTransfer(getUserVisit(), countryGeoCode));

            if(session.hasLimit(HarmonizedTariffScheduleCodeFactory.class)) {
                result.setHarmonizedTariffScheduleCodeCount(getTotalEntities());
            }

            result.setHarmonizedTariffScheduleCodes(itemControl.getHarmonizedTariffScheduleCodeTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
