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

import com.echothree.control.user.tax.common.edit.TaxEdit;
import com.echothree.control.user.tax.common.edit.TaxEditFactory;
import com.echothree.control.user.tax.common.form.EditTaxForm;
import com.echothree.control.user.tax.common.result.EditTaxResult;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.control.user.tax.common.spec.TaxSpec;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.ContactControl;
import com.echothree.model.control.tax.server.TaxControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contact.server.entity.ContactMechanismType;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.model.data.tax.server.entity.TaxDescription;
import com.echothree.model.data.tax.server.entity.TaxDetail;
import com.echothree.model.data.tax.server.value.TaxDescriptionValue;
import com.echothree.model.data.tax.server.value.TaxDetailValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.PercentUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditTaxCommand
        extends BaseEditCommand<TaxSpec, TaxEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TaxName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TaxName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ContactMechanismPurposeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GlAccountName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IncludeShippingCharge", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IncludeProcessingCharge", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IncludeInsuranceCharge", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Percent", FieldType.FRACTIONAL_PERCENT, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditTaxCommand */
    public EditTaxCommand(UserVisitPK userVisitPK, EditTaxForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var taxControl = (TaxControl)Session.getModelController(TaxControl.class);
        EditTaxResult result = TaxResultFactory.getEditTaxResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String taxName = spec.getTaxName();
            Tax tax = taxControl.getTaxByName(taxName);
            
            if(tax != null) {
                result.setTax(taxControl.getTaxTransfer(getUserVisit(), tax));
                
                if(lockEntity(tax)) {
                    TaxDescription taxDescription = taxControl.getTaxDescription(tax, getPreferredLanguage());
                    TaxEdit edit = TaxEditFactory.getTaxEdit();
                    TaxDetail taxDetail = tax.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setTaxName(taxDetail.getTaxName());
                    edit.setContactMechanismPurposeName(taxDetail.getContactMechanismPurpose().getContactMechanismPurposeName());
                    edit.setGlAccountName(taxDetail.getGlAccount().getLastDetail().getGlAccountName());
                    edit.setIncludeShippingCharge(taxDetail.getIncludeShippingCharge().toString());
                    edit.setIncludeProcessingCharge(taxDetail.getIncludeProcessingCharge().toString());
                    edit.setIncludeInsuranceCharge(taxDetail.getIncludeInsuranceCharge().toString());
                    edit.setPercent(PercentUtils.getInstance().formatFractionalPercent(taxDetail.getPercent()));
                    edit.setIsDefault(taxDetail.getIsDefault().toString());
                    edit.setSortOrder(taxDetail.getSortOrder().toString());
                    
                    if(taxDescription != null) {
                        edit.setDescription(taxDescription.getDescription());
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(tax));
            } else {
                addExecutionError(ExecutionErrors.UnknownTaxName.name(), taxName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String taxName = spec.getTaxName();
            Tax tax = taxControl.getTaxByNameForUpdate(taxName);
            
            if(tax != null) {
                taxName = edit.getTaxName();
                Tax duplicateTax = taxControl.getTaxByName(taxName);
                
                if(duplicateTax == null || tax.equals(duplicateTax)) {
                    var contactControl = (ContactControl)Session.getModelController(ContactControl.class);
                    String contactMechanismPurposeName = edit.getContactMechanismPurposeName();
                    ContactMechanismPurpose contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(contactMechanismPurposeName);
                    
                    if(contactMechanismPurpose != null) {
                        ContactMechanismType contactMechanismType = contactControl.getContactMechanismTypeByName(ContactMechanismTypes.POSTAL_ADDRESS.name());
                        
                        if(contactMechanismPurpose.getContactMechanismType().equals(contactMechanismType)) {
                            var accountingControl = (AccountingControl)Session.getModelController(AccountingControl.class);
                            String glAccountName = edit.getGlAccountName();
                            GlAccount glAccount = accountingControl.getGlAccountByName(glAccountName);
                            
                            if(glAccount != null) {
                                if(lockEntityForUpdate(tax)) {
                                    try {
                                        PartyPK partyPK = getPartyPK();
                                        TaxDetailValue taxDetailValue = taxControl.getTaxDetailValueForUpdate(tax);
                                        TaxDescription taxDescription = taxControl.getTaxDescriptionForUpdate(tax, getPreferredLanguage());
                                        String description = edit.getDescription();
                                        
                                        taxDetailValue.setTaxName(edit.getTaxName());
                                        taxDetailValue.setContactMechanismPurposePK(contactMechanismPurpose.getPrimaryKey());
                                        taxDetailValue.setGlAccountPK(glAccount.getPrimaryKey());
                                        taxDetailValue.setPercent(Integer.valueOf(edit.getPercent()));
                                        taxDetailValue.setIncludeShippingCharge(Boolean.valueOf(edit.getIncludeShippingCharge()));
                                        taxDetailValue.setIncludeProcessingCharge(Boolean.valueOf(edit.getIncludeProcessingCharge()));
                                        taxDetailValue.setIncludeInsuranceCharge(Boolean.valueOf(edit.getIncludeInsuranceCharge()));
                                        taxDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                        taxDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                        
                                        taxControl.updateTaxFromValue(taxDetailValue, partyPK);
                                        
                                        if(taxDescription == null && description != null) {
                                            taxControl.createTaxDescription(tax, getPreferredLanguage(), description, partyPK);
                                        } else if(taxDescription != null && description == null) {
                                            taxControl.deleteTaxDescription(taxDescription, partyPK);
                                        } else if(taxDescription != null && description != null) {
                                            TaxDescriptionValue taxDescriptionValue = taxControl.getTaxDescriptionValue(taxDescription);
                                            
                                            taxDescriptionValue.setDescription(description);
                                            taxControl.updateTaxDescriptionFromValue(taxDescriptionValue, partyPK);
                                        }
                                    } finally {
                                        unlockEntity(tax);
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.EntityLockStale.name());
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
            } else {
                addExecutionError(ExecutionErrors.UnknownTaxName.name(), taxName);
            }
            
            if(hasExecutionErrors()) {
                result.setTax(taxControl.getTaxTransfer(getUserVisit(), tax));
                result.setEntityLock(getEntityLockTransfer(tax));
            }
        }
        
        return result;
    }
    
}
