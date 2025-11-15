// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.tax.common.transfer.TaxClassificationTranslationTransfer;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.tax.server.entity.TaxClassificationTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TaxClassificationTranslationTransferCache
        extends BaseTaxDescriptionTransferCache<TaxClassificationTranslation, TaxClassificationTranslationTransfer> {

    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    TaxControl taxControl = Session.getModelController(TaxControl.class);

    /** Creates a new instance of TaxClassificationTranslationTransferCache */
    public TaxClassificationTranslationTransferCache() {
        super();
    }
    
    @Override
    public TaxClassificationTranslationTransfer getTransfer(UserVisit userVisit, TaxClassificationTranslation taxClassificationTranslation) {
        var taxClassificationTranslationTransfer = get(taxClassificationTranslation);
        
        if(taxClassificationTranslationTransfer == null) {
            var taxClassificationTransfer = taxControl.getTaxClassificationTransfer(userVisit, taxClassificationTranslation.getTaxClassification());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, taxClassificationTranslation.getLanguage());
            var description = taxClassificationTranslation.getDescription();
            var overviewMimeType = taxClassificationTranslation.getOverviewMimeType();
            var overviewMimeTypeTransfer = overviewMimeType == null? null: mimeTypeControl.getMimeTypeTransfer(userVisit, overviewMimeType);
            var overview = taxClassificationTranslation.getOverview();
            
            taxClassificationTranslationTransfer = new TaxClassificationTranslationTransfer(languageTransfer,
                    taxClassificationTransfer, description, overviewMimeTypeTransfer, overview);
            put(userVisit, taxClassificationTranslation, taxClassificationTranslationTransfer);
        }
        
        return taxClassificationTranslationTransfer;
    }
    
}
