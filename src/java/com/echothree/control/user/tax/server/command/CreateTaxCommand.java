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

import com.echothree.control.user.tax.remote.form.CreateTaxForm;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.contact.common.ContactConstants;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.tax.server.TaxControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contact.server.entity.ContactMechanismType;
import com.echothree.model.data.party.remote.pk.PartyPK;
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
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateTaxCommand */
    public CreateTaxCommand(UserVisitPK userVisitPK, CreateTaxForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        TaxControl taxControl = (TaxControl)Session.getModelController(TaxControl.class);
        String taxName = form.getTaxName();
        Tax tax = taxControl.getTaxByName(taxName);
        
        if(tax == null) {
            ContactControl contactControl = (ContactControl)Session.getModelController(ContactControl.class);
            String contactMechanismPurposeName = form.getContactMechanismPurposeName();
            ContactMechanismPurpose contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(contactMechanismPurposeName);
            
            if(contactMechanismPurpose != null) {
                ContactMechanismType contactMechanismType = contactControl.getContactMechanismTypeByName(ContactConstants.ContactMechanismType_POSTAL_ADDRESS);
                
                if(contactMechanismPurpose.getContactMechanismType().equals(contactMechanismType)) {
                    AccountingControl accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                    String glAccountName = form.getGlAccountName();
                    GlAccount glAccount = accountingControl.getGlAccountByName(glAccountName);
                    
                    if(glAccount != null) {
                        PartyPK partyPK = getPartyPK();
                        Boolean includeShippingCharge = Boolean.valueOf(form.getIncludeShippingCharge());
                        Boolean includeProcessingCharge = Boolean.valueOf(form.getIncludeProcessingCharge());
                        Boolean includeInsuranceCharge = Boolean.valueOf(form.getIncludeInsuranceCharge());
                        Integer percent = Integer.valueOf(form.getPercent());
                        Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                        Integer sortOrder = Integer.valueOf(form.getSortOrder());
                        String description = form.getDescription();
                        
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
