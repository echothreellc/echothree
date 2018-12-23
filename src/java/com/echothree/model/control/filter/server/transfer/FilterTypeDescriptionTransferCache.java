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

import com.echothree.model.control.filter.common.transfer.FilterTypeDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterTypeTransfer;
import com.echothree.model.control.filter.server.FilterControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.filter.server.entity.FilterTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class FilterTypeDescriptionTransferCache
        extends BaseFilterDescriptionTransferCache<FilterTypeDescription, FilterTypeDescriptionTransfer> {
    
    /** Creates a new instance of FilterTypeDescriptionTransferCache */
    public FilterTypeDescriptionTransferCache(UserVisit userVisit, FilterControl filterControl) {
        super(userVisit, filterControl);
    }
    
    public FilterTypeDescriptionTransfer getFilterTypeDescriptionTransfer(FilterTypeDescription filterTypeDescription) {
        FilterTypeDescriptionTransfer filterTypeDescriptionTransfer = get(filterTypeDescription);
        
        if(filterTypeDescriptionTransfer == null) {
            FilterTypeTransfer filterTypeTransfer = filterControl.getFilterTypeTransfer(userVisit, filterTypeDescription.getFilterType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, filterTypeDescription.getLanguage());
            
            filterTypeDescriptionTransfer = new FilterTypeDescriptionTransfer(languageTransfer, filterTypeTransfer, filterTypeDescription.getDescription());
            put(filterTypeDescription, filterTypeDescriptionTransfer);
        }
        
        return filterTypeDescriptionTransfer;
    }
    
}
