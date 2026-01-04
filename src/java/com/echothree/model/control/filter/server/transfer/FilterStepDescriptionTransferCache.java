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

package com.echothree.model.control.filter.server.transfer;

import com.echothree.model.control.filter.common.transfer.FilterStepDescriptionTransfer;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterStepDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FilterStepDescriptionTransferCache
        extends BaseFilterDescriptionTransferCache<FilterStepDescription, FilterStepDescriptionTransfer> {

    FilterControl filterControl = Session.getModelController(FilterControl.class);

    /** Creates a new instance of FilterStepDescriptionTransferCache */
    protected FilterStepDescriptionTransferCache() {
        super();
    }

    @Override
    public FilterStepDescriptionTransfer getTransfer(UserVisit userVisit, FilterStepDescription filterStepDescription) {
        var filterStepDescriptionTransfer = get(filterStepDescription);
        
        if(filterStepDescriptionTransfer == null) {
            var filterStepTransfer = filterControl.getFilterStepTransfer(userVisit, filterStepDescription.getFilterStep());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, filterStepDescription.getLanguage());
            
            filterStepDescriptionTransfer = new FilterStepDescriptionTransfer(languageTransfer, filterStepTransfer, filterStepDescription.getDescription());
            put(userVisit, filterStepDescription, filterStepDescriptionTransfer);
        }
        
        return filterStepDescriptionTransfer;
    }
    
}
