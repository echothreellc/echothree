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

import com.echothree.control.user.tax.common.form.CreateTaxForm;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.tax.server.control.TaxControl;
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
import javax.enterprise.context.Dependent;

@Dependent
public class CreateTaxCommand
        extends BaseSimpleCommand<CreateTaxForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TaxName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactMechanismPurposeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IncludeShippingCharge", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IncludeProcessingCharge", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IncludeInsuranceCharge", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Percent", FieldType.FRACTIONAL_PERCENT, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateTaxCommand */
    public CreateTaxCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var taxControl = Session.getModelController(TaxControl.class);
        var taxName = form.getTaxName();
        var tax = taxControl.getTaxByName(taxName);
        
        if(tax == null) {
            var contactControl = Session.getModelController(ContactControl.class);
            var contactMechanismPurposeName = form.getContactMechanismPurposeName();
            var contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(contactMechanismPurposeName);
            
            if(contactMechanismPurpose != null) {
                var contactMechanismType = contactControl.getContactMechanismTypeByName(ContactMechanismTypes.POSTAL_ADDRESS.name());
                
                if(contactMechanismPurpose.getContactMechanismType().equals(contactMechanismType)) {
                    var accountingControl = Session.getModelController(AccountingControl.class);
                    var glAccountName = form.getGlAccountName();
                    var glAccount = accountingControl.getGlAccountByName(glAccountName);
                    
                    if(glAccount != null) {
                        var partyPK = getPartyPK();
                        var includeShippingCharge = Boolean.valueOf(form.getIncludeShippingCharge());
                        var includeProcessingCharge = Boolean.valueOf(form.getIncludeProcessingCharge());
                        var includeInsuranceCharge = Boolean.valueOf(form.getIncludeInsuranceCharge());
                        var percent = Integer.valueOf(form.getPercent());
                        var isDefault = Boolean.valueOf(form.getIsDefault());
                        var sortOrder = Integer.valueOf(form.getSortOrder());
                        var description = form.getDescription();
                        
                        // TODO: Check glAccount's glAccountCategory to make sure it is appropriate for tax
                        
                        tax = taxControl.createTax(taxName, contactMechanismPurpose, glAccount, includeShippingCharge,
                                includeProcessingCharge, includeInsuranceCharge, percent, isDefault, sortOrder, partyPK);
                        
                        if(description != null) {
                            taxControl.createTaxDescription(tax, getPreferredLanguage(), description, partyPK);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownGlAccountName.name(), glAccountName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidContactMechanismPurpose.name(), contactMechanismPurposeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownContactMechanismPurposeName.name(), contactMechanismPurposeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateTaxName.name(), taxName);
        }
        
        return null;
    }
    
}
