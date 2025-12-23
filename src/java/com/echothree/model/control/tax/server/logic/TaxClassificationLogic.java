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

package com.echothree.model.control.tax.server.logic;

import com.echothree.model.control.tax.common.exception.UnknownTaxClassificationNameException;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.tax.server.entity.TaxClassification;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class TaxClassificationLogic
        extends BaseLogic {

    protected TaxClassificationLogic() {
        super();
    }

    public static TaxClassificationLogic getInstance() {
        return CDI.current().select(TaxClassificationLogic.class).get();
    }

    public TaxClassification getTaxClassificationByName(final ExecutionErrorAccumulator eea, final GeoCode countryGeoCode, final String taxClassificationName) {
        var taxControl = Session.getModelController(TaxControl.class);
        var taxClassification = taxControl.getTaxClassificationByName(countryGeoCode, taxClassificationName);

        if(taxClassification == null) {
            handleExecutionError(UnknownTaxClassificationNameException.class, eea, ExecutionErrors.UnknownTaxClassificationName.name(),
                    countryGeoCode.getLastDetail().getGeoCodeName(), taxClassificationName);
        }

        return taxClassification;
    }
    
}
