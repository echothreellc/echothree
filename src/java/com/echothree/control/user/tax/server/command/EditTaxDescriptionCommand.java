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

import com.echothree.control.user.tax.common.edit.TaxDescriptionEdit;
import com.echothree.control.user.tax.common.edit.TaxEditFactory;
import com.echothree.control.user.tax.common.form.EditTaxDescriptionForm;
import com.echothree.control.user.tax.common.result.EditTaxDescriptionResult;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.control.user.tax.common.spec.TaxDescriptionSpec;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.tax.server.TaxControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.model.data.tax.server.entity.TaxDescription;
import com.echothree.model.data.tax.server.value.TaxDescriptionValue;
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
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditTaxDescriptionCommand */
    public EditTaxDescriptionCommand(UserVisitPK userVisitPK, EditTaxDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        TaxControl taxControl = (TaxControl)Session.getModelController(TaxControl.class);
        EditTaxDescriptionResult result = TaxResultFactory.getEditTaxDescriptionResult();
        String taxName = spec.getTaxName();
        Tax tax = taxControl.getTaxByName(taxName);
        
        if(tax != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    TaxDescription taxDescription = taxControl.getTaxDescription(tax, language);
                    
                    if(taxDescription != null) {
                        result.setTaxDescription(taxControl.getTaxDescriptionTransfer(getUserVisit(), taxDescription));
                        
                        if(lockEntity(tax)) {
                            TaxDescriptionEdit edit = TaxEditFactory.getTaxDescriptionEdit();
                            
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
                    TaxDescriptionValue taxDescriptionValue = taxControl.getTaxDescriptionValueForUpdate(tax, language);
                    
                    if(taxDescriptionValue != null) {
                        if(lockEntityForUpdate(tax)) {
                            try {
                                String description = edit.getDescription();
                                
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
