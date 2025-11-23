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

package com.echothree.control.user.tax.server.command;

import com.echothree.control.user.tax.common.edit.TaxDescriptionEdit;
import com.echothree.control.user.tax.common.edit.TaxEditFactory;
import com.echothree.control.user.tax.common.form.EditTaxDescriptionForm;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.control.user.tax.common.spec.TaxDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditTaxDescriptionCommand
        extends BaseEditCommand<TaxDescriptionSpec, TaxDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TaxName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditTaxDescriptionCommand */
    public EditTaxDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var taxControl = Session.getModelController(TaxControl.class);
        var result = TaxResultFactory.getEditTaxDescriptionResult();
        var taxName = spec.getTaxName();
        var tax = taxControl.getTaxByName(taxName);
        
        if(tax != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var taxDescription = taxControl.getTaxDescription(tax, language);
                    
                    if(taxDescription != null) {
                        result.setTaxDescription(taxControl.getTaxDescriptionTransfer(getUserVisit(), taxDescription));
                        
                        if(lockEntity(tax)) {
                            var edit = TaxEditFactory.getTaxDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(taxDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(tax));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTaxDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var taxDescriptionValue = taxControl.getTaxDescriptionValueForUpdate(tax, language);
                    
                    if(taxDescriptionValue != null) {
                        if(lockEntityForUpdate(tax)) {
                            try {
                                var description = edit.getDescription();
                                
                                taxDescriptionValue.setDescription(description);
                                
                                taxControl.updateTaxDescriptionFromValue(taxDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(tax);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTaxDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTaxName.name(), taxName);
        }
        
        return result;
    }
    
}
