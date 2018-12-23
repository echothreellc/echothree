// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.tax.common.result.GetTaxClassificationsResult;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tax.server.TaxControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.tax.server.factory.TaxClassificationFactory;
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

public class GetTaxClassificationsCommand
        extends BaseSimpleCommand<GetTaxClassificationsForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.TaxClassification.name(), SecurityRoles.List.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetTaxClassificationsCommand */
    public GetTaxClassificationsCommand(UserVisitPK userVisitPK, GetTaxClassificationsForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        GetTaxClassificationsResult result = TaxResultFactory.getGetTaxClassificationsResult();
        String countryName = form.getCountryName();
        GeoCode geoCode = geoControl.getCountryByAlias(countryName);
        
        if(geoCode != null) {
            TaxControl taxControl = (TaxControl)Session.getModelController(TaxControl.class);
            
            if(session.hasLimit(TaxClassificationFactory.class)) {
                result.setTaxClassificationCount(taxControl.countTaxClassificationsByCountryGeoCode(geoCode));
            }
            
            result.setCountry(geoControl.getCountryTransfer(getUserVisit(), geoCode));
            result.setTaxClassifications(taxControl.getTaxClassificationTransfersByCountryGeoCode(getUserVisit(), geoCode));
        } else {
            addExecutionError(ExecutionErrors.UnknownCountryName.name(), countryName);
        }
        
        return result;
    }
    
}
