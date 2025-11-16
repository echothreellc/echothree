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

package com.echothree.model.control.filter.server.transfer;

import com.echothree.model.control.filter.common.transfer.FilterDescriptionTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FilterDescriptionTransferCache
        extends BaseFilterDescriptionTransferCache<FilterDescription, FilterDescriptionTransfer> {

    FilterControl filterControl = Session.getModelController(FilterControl.class);

    /** Creates a new instance of FilterDescriptionTransferCache */
    public FilterDescriptionTransferCache() {
        super();
    }

    @Override
    public FilterDescriptionTransfer getTransfer(UserVisit userVisit, FilterDescription filterDescription) {
        var filterDescriptionTransfer = get(filterDescription);
        
        if(filterDescriptionTransfer == null) {
            var filterTransfer = filterControl.getFilterTransfer(userVisit, filterDescription.getFilter());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, filterDescription.getLanguage());
            
            filterDescriptionTransfer = new FilterDescriptionTransfer(languageTransfer, filterTransfer, filterDescription.getDescription());
            put(userVisit, filterDescription, filterDescriptionTransfer);
        }
        
        return filterDescriptionTransfer;
    }
    
}
