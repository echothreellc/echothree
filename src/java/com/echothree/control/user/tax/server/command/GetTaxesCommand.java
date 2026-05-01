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

package com.echothree.control.user.tax.server.command;

import com.echothree.control.user.tax.common.form.GetTaxesForm;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.model.data.tax.server.factory.TaxFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetTaxesCommand
        extends BasePaginatedMultipleEntitiesCommand<Tax, GetTaxesForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    @Inject
    TaxControl taxControl;

    /** Creates a new instance of GetTaxesCommand */
    public GetTaxesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields to handle
    }

    @Override
    protected Long getTotalEntities() {
        return taxControl.countTaxes();
    }

    @Override
    protected Collection<Tax> getEntities() {
        return taxControl.getTaxes();
    }

    @Override
    protected BaseResult getResult(Collection<Tax> entities) {
        var result = TaxResultFactory.getGetTaxesResult();

        if(entities != null) {
            if(session.hasLimit(TaxFactory.class)) {
                result.setTaxCount(getTotalEntities());
            }

            result.setTaxes(taxControl.getTaxTransfers(getUserVisit(), entities));
        }

        return result;
    }
    
}
