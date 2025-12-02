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

import com.echothree.control.user.tax.common.edit.TaxEdit;
import com.echothree.control.user.tax.common.edit.TaxEditFactory;
import com.echothree.control.user.tax.common.form.EditTaxForm;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.control.user.tax.common.spec.TaxSpec;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.tax.server.control.TaxControl;
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
import javax.enterprise.context.Dependent;

@Dependent
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
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditTaxCommand */
    public EditTaxCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var taxControl = Session.getModelController(TaxControl.class);
        var result = TaxResultFactory.getEditTaxResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var taxName = spec.getTaxName();
            var tax = taxControl.getTaxByName(taxName);
            
            if(tax != null) {
                result.setTax(taxControl.getTaxTransfer(getUserVisit(), tax));
                
                if(lockEntity(tax)) {
                    var taxDescription = taxControl.getTaxDescription(tax, getPreferredLanguage());
                    var edit = TaxEditFactory.getTaxEdit();
                    var taxDetail = tax.getLastDetail();
                    
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
            var taxName = spec.getTaxName();
            var tax = taxControl.getTaxByNameForUpdate(taxName);
            
            if(tax != null) {
                taxName = edit.getTaxName();
                var duplicateTax = taxControl.getTaxByName(taxName);
                
                if(duplicateTax == null || tax.equals(duplicateTax)) {
                    var contactControl = Session.getModelController(ContactControl.class);
                    var contactMechanismPurposeName = edit.getContactMechanismPurposeName();
                    var contactMechanismPurpose = contactControl.getContactMechanismPurposeByName(contactMechanismPurposeName);
                    
                    if(contactMechanismPurpose != null) {
                        var contactMechanismType = contactControl.getContactMechanismTypeByName(ContactMechanismTypes.POSTAL_ADDRESS.name());
                        
                        if(contactMechanismPurpose.getContactMechanismType().equals(contactMechanismType)) {
                            var accountingControl = Session.getModelController(AccountingControl.class);
                            var glAccountName = edit.getGlAccountName();
                            var glAccount = accountingControl.getGlAccountByName(glAccountName);
                            
                            if(glAccount != null) {
                                if(lockEntityForUpdate(tax)) {
                                    try {
                                        var partyPK = getPartyPK();
                                        var taxDetailValue = taxControl.getTaxDetailValueForUpdate(tax);
                                        var taxDescription = taxControl.getTaxDescriptionForUpdate(tax, getPreferredLanguage());
                                        var description = edit.getDescription();
                                        
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
                                            var taxDescriptionValue = taxControl.getTaxDescriptionValue(taxDescription);
                                            
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
