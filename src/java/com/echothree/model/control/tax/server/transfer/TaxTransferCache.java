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

package com.echothree.model.control.tax.server.transfer;

import com.echothree.model.control.accounting.remote.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.contact.remote.transfer.ContactMechanismPurposeTransfer;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.tax.remote.transfer.TaxTransfer;
import com.echothree.model.control.tax.server.TaxControl;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.model.data.tax.server.entity.TaxDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TaxTransferCache
        extends BaseTaxTransferCache<Tax, TaxTransfer> {
    
    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
    ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
    
    /** Creates a new instance of TaxTransferCache */
    public TaxTransferCache(UserVisit userVisit, TaxControl taxControl) {
        super(userVisit, taxControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public TaxTransfer getTransfer(Tax tax) {
        TaxTransfer taxTransfer = get(tax);
        
        if(taxTransfer == null) {
            TaxDetail taxDetail = tax.getLastDetail();
            String taxName = taxDetail.getTaxName();
            ContactMechanismPurposeTransfer contactMechanismPurpose = contactControl.getContactMechanismPurposeTransfer(userVisit, taxDetail.getContactMechanismPurpose());
            GlAccountTransfer glAccount = accountingControl.getGlAccountTransfer(userVisit, taxDetail.getGlAccount());
            String percent = formatFractionalPercent(taxDetail.getPercent());
            Boolean isDefault = taxDetail.getIsDefault();
            Integer sortOrder = taxDetail.getSortOrder();
            String description = taxControl.getBestTaxDescription(tax, getLanguage());
            
            taxTransfer = new TaxTransfer(taxName, contactMechanismPurpose, glAccount, percent, isDefault, sortOrder, description);
            put(tax, taxTransfer);
        }
        
        return taxTransfer;
    }
    
}
