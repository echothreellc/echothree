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

package com.echothree.model.control.accounting.server.control;

import com.echothree.model.control.accounting.server.transfer.CurrencyDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.CurrencyTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlAccountCategoryDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlAccountCategoryTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlAccountClassDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlAccountClassTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlAccountDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlAccountTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlAccountTypeTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlResourceTypeDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.GlResourceTypeTransferCache;
import com.echothree.model.control.accounting.server.transfer.ItemAccountingCategoryDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.ItemAccountingCategoryTransferCache;
import com.echothree.model.control.accounting.server.transfer.SymbolPositionDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.SymbolPositionTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionEntityRoleTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionEntityRoleTypeDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionEntityRoleTypeTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionGlAccountCategoryDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionGlAccountCategoryTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionGlAccountTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionGlEntryTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionGroupTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionTimeTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionTimeTypeDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionTimeTypeTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionTypeDescriptionTransferCache;
import com.echothree.model.control.accounting.server.transfer.TransactionTypeTransferCache;
import com.echothree.util.server.control.BaseModelControl;
import javax.inject.Inject;

public class BaseAccountingControl
        extends BaseModelControl {

    /** Creates a new instance of AccountingControl */
    protected BaseAccountingControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Accounting Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    CurrencyTransferCache currencyTransferCache;

    @Inject
    CurrencyDescriptionTransferCache currencyDescriptionTransferCache;

    @Inject
    GlAccountClassDescriptionTransferCache glAccountClassDescriptionTransferCache;

    @Inject
    GlAccountClassTransferCache glAccountClassTransferCache;

    @Inject
    GlAccountDescriptionTransferCache glAccountDescriptionTransferCache;

    @Inject
    GlAccountTransferCache glAccountTransferCache;

    @Inject
    GlAccountCategoryDescriptionTransferCache glAccountCategoryDescriptionTransferCache;

    @Inject
    GlAccountCategoryTransferCache glAccountCategoryTransferCache;

    @Inject
    GlResourceTypeDescriptionTransferCache glResourceTypeDescriptionTransferCache;

    @Inject
    GlResourceTypeTransferCache glResourceTypeTransferCache;

    @Inject
    ItemAccountingCategoryDescriptionTransferCache itemAccountingCategoryDescriptionTransferCache;

    @Inject
    ItemAccountingCategoryTransferCache itemAccountingCategoryTransferCache;

    @Inject
    TransactionEntityRoleTransferCache transactionEntityRoleTransferCache;

    @Inject
    TransactionEntityRoleTypeTransferCache transactionEntityRoleTypeTransferCache;

    @Inject
    TransactionGlEntryTransferCache transactionGlEntryTransferCache;

    @Inject
    TransactionGlAccountTransferCache transactionGlAccountTransferCache;

    @Inject
    TransactionGlAccountCategoryTransferCache transactionGlAccountCategoryTransferCache;

    @Inject
    TransactionGroupTransferCache transactionGroupTransferCache;

    @Inject
    TransactionTransferCache transactionTransferCache;

    @Inject
    TransactionTypeTransferCache transactionTypeTransferCache;

    @Inject
    GlAccountTypeTransferCache glAccountTypeTransferCache;

    @Inject
    TransactionTypeDescriptionTransferCache transactionTypeDescriptionTransferCache;

    @Inject
    TransactionGlAccountCategoryDescriptionTransferCache transactionGlAccountCategoryDescriptionTransferCache;

    @Inject
    TransactionEntityRoleTypeDescriptionTransferCache transactionEntityRoleTypeDescriptionTransferCache;

    @Inject
    SymbolPositionDescriptionTransferCache symbolPositionDescriptionTransferCache;

    @Inject
    SymbolPositionTransferCache symbolPositionTransferCache;

    @Inject
    TransactionTimeTypeTransferCache transactionTimeTypeTransferCache;

    @Inject
    TransactionTimeTypeDescriptionTransferCache transactionTimeTypeDescriptionTransferCache;

    @Inject
    TransactionTimeTransferCache transactionTimeTransferCache;

}
