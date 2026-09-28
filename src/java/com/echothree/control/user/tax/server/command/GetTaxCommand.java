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

import com.echothree.control.user.tax.common.form.GetTaxForm;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.control.tax.server.logic.TaxLogic;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetTaxCommand
        extends BaseSingleEntityCommand<Tax, GetTaxForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TaxName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    TaxControl taxControl;

    @Inject
    TaxLogic taxLogic;

    /** Creates a new instance of GetTaxCommand */
    public GetTaxCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected Tax getEntity() {
        var tax = taxLogic.getTaxByName(this, form.getTaxName());

        if(!hasExecutionErrors()) {
            sendEvent(tax.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return tax;
    }

    @Override
    protected BaseResult getResult(Tax tax) {
        var result = TaxResultFactory.getGetTaxResult();

        if(!hasExecutionErrors()) {
            result.setTax(taxControl.getTaxTransfer(getUserVisit(), tax));
        }

        return result;
    }

}
