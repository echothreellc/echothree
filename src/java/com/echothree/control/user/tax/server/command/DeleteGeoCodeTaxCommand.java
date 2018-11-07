// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.tax.common.form.DeleteGeoCodeTaxForm;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.tax.server.TaxControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.tax.server.entity.GeoCodeTax;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteGeoCodeTaxCommand
        extends BaseSimpleCommand<DeleteGeoCodeTaxForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TaxName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteGeoCodeTaxCommand */
    public DeleteGeoCodeTaxCommand(UserVisitPK userVisitPK, DeleteGeoCodeTaxForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        String geoCodeName = form.getGeoCodeName();
        GeoCode geoCode = geoControl.getGeoCodeByName(geoCodeName);
        
        if(geoCode != null) {
            TaxControl taxControl = (TaxControl)Session.getModelController(TaxControl.class);
            String taxName = form.getTaxName();
            Tax tax = taxControl.getTaxByName(taxName);
            
            if(tax != null) {
                GeoCodeTax geoCodeTax = taxControl.getGeoCodeTaxForUpdate(geoCode, tax);
                
                if(geoCodeTax != null) {
                    taxControl.deleteGeoCodeTax(geoCodeTax, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeTax.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTaxName.name(), taxName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }
        
        return null;
    }
    
}
