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

package com.echothree.model.control.tax.server.transfer;

import com.echothree.model.control.tax.common.transfer.TaxDescriptionTransfer;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.tax.server.entity.TaxDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TaxDescriptionTransferCache
        extends BaseTaxDescriptionTransferCache<TaxDescription, TaxDescriptionTransfer> {

    TaxControl taxControl = Session.getModelController(TaxControl.class);

    /** Creates a new instance of TaxDescriptionTransferCache */
    protected TaxDescriptionTransferCache() {
        super();
    }
    
    @Override
    public TaxDescriptionTransfer getTransfer(UserVisit userVisit, TaxDescription taxDescription) {
        var taxDescriptionTransfer = get(taxDescription);
        
        if(taxDescriptionTransfer == null) {
            var taxTransfer = taxControl.getTaxTransfer(userVisit, taxDescription.getTax());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, taxDescription.getLanguage());
            
            taxDescriptionTransfer = new TaxDescriptionTransfer(languageTransfer, taxTransfer, taxDescription.getDescription());
            put(userVisit, taxDescription, taxDescriptionTransfer);
        }
        
        return taxDescriptionTransfer;
    }
    
}
