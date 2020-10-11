// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.tax.common.form.DeleteTaxDescriptionForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.model.data.tax.server.entity.TaxDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteTaxDescriptionCommand
        extends BaseSimpleCommand<DeleteTaxDescriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TaxName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of DeleteTaxDescriptionCommand */
    public DeleteTaxDescriptionCommand(UserVisitPK userVisitPK, DeleteTaxDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var taxControl = (TaxControl)Session.getModelController(TaxControl.class);
        String taxName = form.getTaxName();
        Tax tax = taxControl.getTaxByName(taxName);
        
        if(tax != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                TaxDescription taxDescription = taxControl.getTaxDescriptionForUpdate(tax, language);
                
                if(taxDescription != null) {
                    taxControl.deleteTaxDescription(taxDescription, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.UnknownTaxDescription.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTaxName.name(), taxName);
        }
        
        return null;
    }
    
}
