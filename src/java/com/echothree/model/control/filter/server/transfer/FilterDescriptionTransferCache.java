// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.filter.server.transfer;

import com.echothree.model.control.filter.common.transfer.FilterDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterTransfer;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.filter.server.entity.FilterDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class FilterDescriptionTransferCache
        extends BaseFilterDescriptionTransferCache<FilterDescription, FilterDescriptionTransfer> {
    
    /** Creates a new instance of FilterDescriptionTransferCache */
    public FilterDescriptionTransferCache(UserVisit userVisit, FilterControl filterControl) {
        super(userVisit, filterControl);
    }
    
    public FilterDescriptionTransfer getFilterDescriptionTransfer(FilterDescription filterDescription) {
        FilterDescriptionTransfer filterDescriptionTransfer = get(filterDescription);
        
        if(filterDescriptionTransfer == null) {
            FilterTransferCache filterTransferCache = filterControl.getFilterTransferCaches(userVisit).getFilterTransferCache();
            FilterTransfer filterTransfer = filterTransferCache.getFilterTransfer(filterDescription.getFilter());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, filterDescription.getLanguage());
            
            filterDescriptionTransfer = new FilterDescriptionTransfer(languageTransfer, filterTransfer, filterDescription.getDescription());
            put(filterDescription, filterDescriptionTransfer);
        }
        
        return filterDescriptionTransfer;
    }
    
}
