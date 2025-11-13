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

package com.echothree.model.control.payment.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.payment.common.PaymentOptions;
import com.echothree.model.control.payment.common.transfer.BillingAccountRoleTransfer;
import com.echothree.model.control.payment.common.transfer.BillingAccountTransfer;
import com.echothree.model.control.payment.server.control.BillingControl;
import com.echothree.model.data.payment.server.entity.BillingAccount;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;

public class BillingAccountTransferCache
        extends BasePaymentTransferCache<BillingAccount, BillingAccountTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    BillingControl billingControl = Session.getModelController(BillingControl.class);
    boolean includeRoles;

    /** Creates a new instance of BillingAccountTransferCache */
    public BillingAccountTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            includeRoles = options.contains(PaymentOptions.BillingAccountIncludeRoles);
        }
        
        setIncludeEntityInstance(true);
    }

    @Override
    public BillingAccountTransfer getTransfer(UserVisit userVisit, BillingAccount billingAccount) {
        var billingAccountTransfer = get(billingAccount);

        if(billingAccountTransfer == null) {
            var billingAccountDetail = billingAccount.getLastDetail();
            var billingAccountName = billingAccountDetail.getBillingAccountName();
            var currency = billingAccountDetail.getCurrency();
            var currencyTransfer = accountingControl.getCurrencyTransfer(userVisit, currency);
            var reference = billingAccountDetail.getReference();
            var description = billingAccountDetail.getDescription();
            String creditLimit = null;
            String potentialCreditLimit = null;
//            BillingAccountStatus billingAccountStatus = paymentControl.getBillingAccountStatus(billingAccount);
//            Integer rawCreditLimit = billingAccountStatus.getCreditLimit();
//            Integer rawPotentialCreditLimit = billingAccountStatus.getPotentialCreditLimit();
//
//            String partyTypeName = fromParty.getLastDetail().getPartyType().getPartyTypeName();
//            if(PartyTypes.CUSTOMER.name().equals(partyTypeName)) {
//                creditLimit = rawCreditLimit == null? null: AmountUtils.getInstance().formatPriceLine(currency, rawCreditLimit);
//                potentialCreditLimit = rawPotentialCreditLimit == null? null: AmountUtils.getInstance().formatPriceLine(currency, rawPotentialCreditLimit);
//            } else if(PartyTypes.COMPANY.name().equals(partyTypeName)) {
//                creditLimit = rawCreditLimit == null? null: AmountUtils.getInstance().formatCostLine(currency, rawCreditLimit);
//                potentialCreditLimit = rawPotentialCreditLimit == null? null: AmountUtils.getInstance().formatCostLine(currency, rawPotentialCreditLimit);
//            }
            
            billingAccountTransfer = new BillingAccountTransfer(billingAccountName, currencyTransfer, reference, description, creditLimit, potentialCreditLimit);
            put(userVisit, billingAccount, billingAccountTransfer);
            
            if(includeRoles) {
                var billingAccountRoleTransfers = billingControl.getBillingAccountRoleTransfersByBillingAccount(userVisit, billingAccount);
                var billingAccountRoles = new MapWrapper<BillingAccountRoleTransfer>(billingAccountRoleTransfers.size());

                billingAccountRoleTransfers.forEach((billingAccountRoleTransfer) -> {
                    billingAccountRoles.put(billingAccountRoleTransfer.getBillingAccountRoleType().getBillingAccountRoleTypeName(), billingAccountRoleTransfer);
                });

                billingAccountTransfer.setBillingAccountRoles(billingAccountRoles);
            }
        }
        
        return billingAccountTransfer;
    }
    
}