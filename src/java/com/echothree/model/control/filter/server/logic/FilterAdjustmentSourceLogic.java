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

package com.echothree.model.control.filter.server.logic;

import com.echothree.model.control.filter.common.exception.DuplicateFilterAdjustmentSourceNameException;
import com.echothree.model.control.filter.common.exception.UnknownFilterAdjustmentSourceNameException;
import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentSource;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class FilterAdjustmentSourceLogic
        extends BaseLogic {

    protected FilterAdjustmentSourceLogic() {
        super();
    }

    public static FilterAdjustmentSourceLogic getInstance() {
        return CDI.current().select(FilterAdjustmentSourceLogic.class).get();
    }

    public FilterAdjustmentSource createFilterAdjustmentSource(final ExecutionErrorAccumulator eea, final String filterAdjustmentSourceName,
            final Boolean allowedForInitialAmount, final Boolean isDefault, final Integer sortOrder, final Language language,
            final String description) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterAdjustmentSource = filterControl.getFilterAdjustmentSourceByName(filterAdjustmentSourceName);

        if(filterAdjustmentSource == null) {
            filterAdjustmentSource = filterControl.createFilterAdjustmentSource(filterAdjustmentSourceName, allowedForInitialAmount,
                    isDefault, sortOrder);

            if(description != null) {
                filterControl.createFilterAdjustmentSourceDescription(filterAdjustmentSource, language, description);
            }
        } else {
            handleExecutionError(DuplicateFilterAdjustmentSourceNameException.class, eea, ExecutionErrors.DuplicateFilterAdjustmentSourceName.name(), filterAdjustmentSourceName);
        }

        return filterAdjustmentSource;
    }

    public FilterAdjustmentSource getFilterAdjustmentSourceByName(final ExecutionErrorAccumulator eea, final String filterAdjustmentSourceName) {
        var filterControl = Session.getModelController(FilterControl.class);
        var filterAdjustmentSource = filterControl.getFilterAdjustmentSourceByName(filterAdjustmentSourceName);

        if(filterAdjustmentSource == null) {
            handleExecutionError(UnknownFilterAdjustmentSourceNameException.class, eea, ExecutionErrors.UnknownFilterAdjustmentSourceName.name(), filterAdjustmentSourceName);
        }

        return filterAdjustmentSource;
    }

}
