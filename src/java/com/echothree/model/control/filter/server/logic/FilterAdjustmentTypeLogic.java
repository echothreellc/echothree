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

import com.echothree.model.control.filter.common.exception.DuplicateFilterAdjustmentTypeNameException;
import com.echothree.model.control.filter.common.exception.UnknownFilterAdjustmentTypeNameException;
import com.echothree.model.control.filter.server.control.FilterAdjustmentControl;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class FilterAdjustmentTypeLogic
        extends BaseLogic {

    @Inject
    FilterAdjustmentControl filterAdjustmentControl;

    protected FilterAdjustmentTypeLogic() {
        super();
    }

    public static FilterAdjustmentTypeLogic getInstance() {
        return CDI.current().select(FilterAdjustmentTypeLogic.class).get();
    }

    public FilterAdjustmentType createFilterAdjustmentType(final ExecutionErrorAccumulator eea, final String filterAdjustmentTypeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description) {
        var filterAdjustmentType = filterAdjustmentControl.getFilterAdjustmentTypeByName(filterAdjustmentTypeName);

        if(filterAdjustmentType == null) {
            filterAdjustmentType = filterAdjustmentControl.createFilterAdjustmentType(filterAdjustmentTypeName, isDefault, sortOrder);

            if(description != null) {
                filterAdjustmentControl.createFilterAdjustmentTypeDescription(filterAdjustmentType, language, description);
            }
        } else {
            handleExecutionError(DuplicateFilterAdjustmentTypeNameException.class, eea, ExecutionErrors.DuplicateFilterAdjustmentTypeName.name(), filterAdjustmentTypeName);
        }

        return filterAdjustmentType;
    }

    public FilterAdjustmentType getFilterAdjustmentTypeByName(final ExecutionErrorAccumulator eea, final String filterAdjustmentTypeName) {
        var filterAdjustmentType = filterAdjustmentControl.getFilterAdjustmentTypeByName(filterAdjustmentTypeName);

        if(filterAdjustmentType == null) {
            handleExecutionError(UnknownFilterAdjustmentTypeNameException.class, eea, ExecutionErrors.UnknownFilterAdjustmentTypeName.name(), filterAdjustmentTypeName);
        }

        return filterAdjustmentType;
    }

}
