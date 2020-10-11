// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.tax.common.transfer.TaxClassificationTransfer;
import com.echothree.model.control.tax.common.transfer.TaxClassificationTranslationTransfer;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.tax.server.entity.TaxClassificationTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TaxClassificationTranslationTransferCache
        extends BaseTaxDescriptionTransferCache<TaxClassificationTranslation, TaxClassificationTranslationTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of TaxClassificationTranslationTransferCache */
    public TaxClassificationTranslationTransferCache(UserVisit userVisit, TaxControl taxControl) {
        super(userVisit, taxControl);
    }
    
    @Override
    public TaxClassificationTranslationTransfer getTransfer(TaxClassificationTranslation taxClassificationTranslation) {
        TaxClassificationTranslationTransfer taxClassificationTranslationTransfer = get(taxClassificationTranslation);
        
        if(taxClassificationTranslationTransfer == null) {
            TaxClassificationTransfer taxClassificationTransfer = taxControl.getTaxClassificationTransfer(userVisit, taxClassificationTranslation.getTaxClassification());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, taxClassificationTranslation.getLanguage());
            String description = taxClassificationTranslation.getDescription();
            MimeType overviewMimeType = taxClassificationTranslation.getOverviewMimeType();
            MimeTypeTransfer overviewMimeTypeTransfer = overviewMimeType == null? null: coreControl.getMimeTypeTransfer(userVisit, overviewMimeType);
            String overview = taxClassificationTranslation.getOverview();
            
            taxClassificationTranslationTransfer = new TaxClassificationTranslationTransfer(languageTransfer,
                    taxClassificationTransfer, description, overviewMimeTypeTransfer, overview);
            put(taxClassificationTranslation, taxClassificationTranslationTransfer);
        }
        
        return taxClassificationTranslationTransfer;
    }
    
}
