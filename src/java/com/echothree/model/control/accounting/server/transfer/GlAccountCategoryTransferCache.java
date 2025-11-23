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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.common.transfer.GlAccountCategoryTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GlAccountCategoryTransferCache
        extends BaseAccountingTransferCache<GlAccountCategory, GlAccountCategoryTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);

    /** Creates a new instance of GlAccountCategoryTransferCache */
    protected GlAccountCategoryTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }

    @Override
    public GlAccountCategoryTransfer getTransfer(UserVisit userVisit, GlAccountCategory glAccountCategory) {
        var glAccountCategoryTransfer = get(glAccountCategory);

        if(glAccountCategoryTransfer == null) {
            var glAccountCategoryDetail = glAccountCategory.getLastDetail();
            var glAccountCategoryName = glAccountCategoryDetail.getGlAccountCategoryName();
            var parentGlAccountCategory = glAccountCategoryDetail.getParentGlAccountCategory();
            var parentGlAccountCategoryTransfer = parentGlAccountCategory == null ? null : getTransfer(userVisit, parentGlAccountCategory);
            var isDefault = glAccountCategoryDetail.getIsDefault();
            var sortOrder = glAccountCategoryDetail.getSortOrder();
            var description = accountingControl.getBestGlAccountCategoryDescription(glAccountCategory, getLanguage(userVisit));

            glAccountCategoryTransfer = new GlAccountCategoryTransfer(glAccountCategoryName, parentGlAccountCategoryTransfer, isDefault, sortOrder, description);
            put(userVisit, glAccountCategory, glAccountCategoryTransfer);
        }

        return glAccountCategoryTransfer;
    }

}
