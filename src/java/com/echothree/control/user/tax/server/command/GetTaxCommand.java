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

package com.echothree.control.user.tax.server.command;

import com.echothree.control.user.tax.remote.form.GetTaxForm;
import com.echothree.control.user.tax.remote.result.GetTaxResult;
import com.echothree.control.user.tax.remote.result.TaxResultFactory;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.tax.server.TaxControl;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetTaxCommand
        extends BaseSimpleCommand<GetTaxForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TaxName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetTaxCommand */
    public GetTaxCommand(UserVisitPK userVisitPK, GetTaxForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        TaxControl taxControl = (TaxControl)Session.getModelController(TaxControl.class);
        GetTaxResult result = TaxResultFactory.getGetTaxResult();
        String taxName = form.getTaxName();
        Tax tax = taxControl.getTaxByName(taxName);
        
        if(tax != null) {
            result.setTax(taxControl.getTaxTransfer(getUserVisit(), tax));
            
            sendEventUsingNames(tax.getPrimaryKey(), EventTypes.READ.name(), null, null, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownTaxName.name(), taxName);
        }
        
        return result;
    }
    
}
