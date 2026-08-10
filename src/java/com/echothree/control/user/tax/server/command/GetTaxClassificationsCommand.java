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

import com.echothree.control.user.tax.common.form.GetTaxClassificationsForm;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.tax.server.entity.TaxClassification;
import com.echothree.model.data.tax.server.factory.TaxClassificationFactory;
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
public class GetTaxClassificationsCommand
        extends BasePaginatedMultipleEntitiesCommand<TaxClassification, GetTaxClassificationsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TaxClassification.name(), SecurityRoles.List.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    GeoControl geoControl;

    @Inject
    TaxControl taxControl;

    /** Creates a new instance of GetTaxClassificationsCommand */
    public GetTaxClassificationsCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    private GeoCode countryGeoCode;

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
        return countryGeoCode == null ? null : taxControl.countTaxClassificationsByCountryGeoCode(countryGeoCode);
    }

    @Override
    protected Collection<TaxClassification> getEntities() {
        return countryGeoCode == null ? null : taxControl.getTaxClassificationsByCountryGeoCode(countryGeoCode);
    }

    @Override
    protected BaseResult getResult(Collection<TaxClassification> entities) {
        var result = TaxResultFactory.getGetTaxClassificationsResult();

        if(entities != null) {
            result.setCountry(geoControl.getCountryTransfer(getUserVisit(), countryGeoCode));

            if(session.hasLimit(TaxClassificationFactory.class)) {
                result.setTaxClassificationCount(getTotalEntities());
            }

            result.setTaxClassifications(taxControl.getTaxClassificationTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
