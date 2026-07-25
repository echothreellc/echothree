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

import com.echothree.control.user.tax.common.form.GetGeoCodeTaxesForm;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.geo.server.logic.GeoCodeLogic;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.control.tax.server.logic.TaxLogic;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.tax.server.entity.GeoCodeTax;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.model.data.tax.server.factory.GeoCodeTaxFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetGeoCodeTaxesCommand
        extends BasePaginatedMultipleEntitiesCommand<GeoCodeTax, GetGeoCodeTaxesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("TaxName", FieldType.ENTITY_NAME, false, null, null)
                );
    }

    @Inject
    GeoControl geoControl;

    @Inject
    TaxControl taxControl;

    @Inject
    GeoCodeLogic geoCodeLogic;

    @Inject
    TaxLogic taxLogic;

    /** Creates a new instance of GetGeoCodeTaxesCommand */
    public GetGeoCodeTaxesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    GeoCode geoCode;
    Tax tax;

    @Override
    protected void handleForm() {
        var geoCodeName = form.getGeoCodeName();
        var taxName = form.getTaxName();
        var parameterCount = (geoCodeName == null ? 0 : 1) + (taxName == null ? 0 : 1);

        if(parameterCount == 1) {
            if(geoCodeName == null) {
                tax = taxLogic.getTaxByName(this, taxName);
            } else {
                geoCode = geoCodeLogic.getGeoCodeByName(this, geoCodeName);
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : geoCode == null
                ? taxControl.countGeoCodeTaxesByTax(tax)
                : taxControl.countGeoCodeTaxesByGeoCode(geoCode);
    }

    @Override
    protected Collection<GeoCodeTax> getEntities() {
        return hasExecutionErrors() ? null : geoCode == null
                ? taxControl.getGeoCodeTaxesByTax(tax)
                : taxControl.getGeoCodeTaxesByGeoCode(geoCode);
    }

    @Override
    protected BaseResult getResult(Collection<GeoCodeTax> entities) {
        var result = TaxResultFactory.getGetGeoCodeTaxesResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(geoCode == null) {
                result.setTax(taxControl.getTaxTransfer(userVisit, tax));
            } else {
                result.setGeoCode(geoControl.getGeoCodeTransfer(userVisit, geoCode));
            }

            if(session.hasLimit(GeoCodeTaxFactory.class)) {
                result.setGeoCodeTaxCount(getTotalEntities());
            }

            result.setGeoCodeTaxes(taxControl.getGeoCodeTaxTransfers(userVisit, entities));
        }

        return result;
    }
    
}
