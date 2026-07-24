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

import com.echothree.control.user.tax.common.form.GetTaxClassificationForm;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.control.tax.server.logic.TaxClassificationLogic;
import com.echothree.model.data.tax.server.entity.TaxClassification;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetTaxClassificationCommand
        extends BaseSingleEntityCommand<TaxClassification, GetTaxClassificationForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TaxClassification.name(), SecurityRoles.Review.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TaxClassificationName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    GeoControl geoControl;

    @Inject
    TaxControl taxControl;

    @Inject
    TaxClassificationLogic taxClassificationLogic;

    /** Creates a new instance of GetTaxClassificationCommand */
    public GetTaxClassificationCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected TaxClassification getEntity() {
        TaxClassification taxClassification = null;
        var countryName = form.getCountryName();
        var countryGeoCode = geoControl.getCountryByAlias(countryName);

        if(countryGeoCode == null) {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), countryName);
        } else {
            var taxClassificationName = form.getTaxClassificationName();

            taxClassification = taxClassificationLogic.getTaxClassificationByName(this, countryGeoCode, taxClassificationName);

            if(taxClassification != null) {
                sendEvent(taxClassification.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            }
        }

        return taxClassification;
    }

    @Override
    protected BaseResult getResult(TaxClassification taxClassification) {
        var result = TaxResultFactory.getGetTaxClassificationResult();

        if(taxClassification != null) {
            result.setTaxClassification(taxControl.getTaxClassificationTransfer(getUserVisit(), taxClassification));
        }

        return result;
    }
    
}
