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

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.tax.common.transfer.TaxTransfer;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TaxTransferCache
        extends BaseTaxTransferCache<Tax, TaxTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    
    /** Creates a new instance of TaxTransferCache */
    public TaxTransferCache(TaxControl taxControl) {
        super(taxControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public TaxTransfer getTransfer(UserVisit userVisit, Tax tax) {
        var taxTransfer = get(tax);
        
        if(taxTransfer == null) {
            var taxDetail = tax.getLastDetail();
            var taxName = taxDetail.getTaxName();
            var contactMechanismPurpose = contactControl.getContactMechanismPurposeTransfer(userVisit, taxDetail.getContactMechanismPurpose());
            var glAccount = accountingControl.getGlAccountTransfer(userVisit, taxDetail.getGlAccount());
            var percent = formatFractionalPercent(taxDetail.getPercent());
            var isDefault = taxDetail.getIsDefault();
            var sortOrder = taxDetail.getSortOrder();
            var description = taxControl.getBestTaxDescription(tax, getLanguage(userVisit));
            
            taxTransfer = new TaxTransfer(taxName, contactMechanismPurpose, glAccount, percent, isDefault, sortOrder, description);
            put(userVisit, tax, taxTransfer);
        }
        
        return taxTransfer;
    }
    
}
