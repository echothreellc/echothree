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

package com.echothree.model.control.filter.server.transfer;

import com.echothree.model.control.filter.remote.transfer.FilterStepElementDescriptionTransfer;
import com.echothree.model.control.filter.remote.transfer.FilterStepElementTransfer;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.data.filter.server.entity.FilterStepElementDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class FilterStepElementDescriptionTransferCache
        extends BaseFilterDescriptionTransferCache<FilterStepElementDescription, FilterStepElementDescriptionTransfer> {
    
    /** Creates a new instance of FilterStepElementDescriptionTransferCache */
    public FilterStepElementDescriptionTransferCache(UserVisit userVisit, FilterControl filterControl) {
        super(userVisit, filterControl);
    }
    
    public FilterStepElementDescriptionTransfer getFilterStepElementDescriptionTransfer(FilterStepElementDescription filterStepElementDescription) {
        FilterStepElementDescriptionTransfer filterStepElementDescriptionTransfer = get(filterStepElementDescription);
        
        if(filterStepElementDescriptionTransfer == null) {
            FilterStepElementTransferCache filterStepElementTransferCache = filterControl.getFilterTransferCaches(userVisit).getFilterStepElementTransferCache();
            FilterStepElementTransfer filterStepElementTransfer = filterStepElementTransferCache.getFilterStepElementTransfer(filterStepElementDescription.getFilterStepElement());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, filterStepElementDescription.getLanguage());
            
            filterStepElementDescriptionTransfer = new FilterStepElementDescriptionTransfer(languageTransfer, filterStepElementTransfer, filterStepElementDescription.getDescription());
            put(filterStepElementDescription, filterStepElementDescriptionTransfer);
        }
        
        return filterStepElementDescriptionTransfer;
    }
    
}
