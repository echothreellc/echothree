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

package com.echothree.model.control.term.server.transfer;

import com.echothree.model.control.term.common.TermTypes;
import com.echothree.model.control.term.common.transfer.TermTransfer;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.PercentUtils;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TermTransferCache
        extends BaseTermTransferCache<Term, TermTransfer> {

    TermControl termControl = Session.getModelController(TermControl.class);

    /** Creates a new instance of TermTransferCache */
    public TermTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public TermTransfer getTermTransfer(UserVisit userVisit, Term term) {
        var termTransfer = get(term);
        
        if(termTransfer == null) {
            var termDetail = term.getLastDetail();
            var termName = termDetail.getTermName();
            var termTypeTransferCache = termControl.getTermTransferCaches().getTermTypeTransferCache();
            var termType = termTypeTransferCache.getTermTypeTransfer(userVisit, termDetail.getTermType());
            var isDefault = termDetail.getIsDefault();
            var sortOrder = termDetail.getSortOrder();
            var description = termControl.getBestTermDescription(term, getLanguage(userVisit));
            String netDueDays = null;
            String discountPercentage = null;
            String discountDays = null;
            String netDueDayOfMonth = null;
            String dueNextMonthDays = null;
            String discountBeforeDayOfMonth = null;

            var termTypeName = termDetail.getTermType().getTermTypeName();
            if(termTypeName.equals(TermTypes.STANDARD.name())) {
                var standardTerm = termControl.getStandardTerm(term);
                
                netDueDays = standardTerm.getNetDueDays().toString();
                discountPercentage = PercentUtils.getInstance().formatFractionalPercent(standardTerm.getDiscountPercentage());
                discountDays = standardTerm.getDiscountDays().toString();
            } else if(termTypeName.equals(TermTypes.DATE_DRIVEN.name())) {
                var dateDrivenTerm = termControl.getDateDrivenTerm(term);
                
                netDueDayOfMonth = dateDrivenTerm.getNetDueDayOfMonth().toString();
                dueNextMonthDays = dateDrivenTerm.getDueNextMonthDays().toString();
                discountPercentage = PercentUtils.getInstance().formatFractionalPercent(dateDrivenTerm.getDiscountPercentage());
                discountBeforeDayOfMonth = dateDrivenTerm.getDiscountBeforeDayOfMonth().toString();
            }
            
            termTransfer = new TermTransfer(termName, termType, isDefault, sortOrder, description,
                    discountPercentage, netDueDays, discountDays, netDueDayOfMonth, dueNextMonthDays, discountBeforeDayOfMonth);
            put(userVisit, term, termTransfer);
        }
        return termTransfer;
    }
    
}
