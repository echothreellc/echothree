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

import com.echothree.control.user.tax.common.edit.TaxEdit;
import com.echothree.control.user.tax.common.edit.TaxEditFactory;
import com.echothree.control.user.tax.common.result.EditTaxResult;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.control.user.tax.common.spec.TaxUniversalSpec;
import com.echothree.model.control.accounting.server.logic.GlAccountLogic;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.server.logic.ContactMechanismPurposeLogic;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.control.tax.server.logic.TaxLogic;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.string.PercentUtils;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditTaxCommand
        extends BaseAbstractEditCommand<TaxUniversalSpec, TaxEdit, EditTaxResult, Tax, Tax> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TaxName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
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
        );
    }

    private ContactMechanismPurpose contactMechanismPurpose;
    private GlAccount glAccount;

    @Inject
    TaxControl taxControl;

    @Inject
    ContactMechanismPurposeLogic contactMechanismPurposeLogic;

    @Inject
    GlAccountLogic glAccountLogic;

    @Inject
    TaxLogic taxLogic;

    /** Creates a new instance of EditTaxCommand */
    public EditTaxCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTaxResult getResult() {
        return TaxResultFactory.getEditTaxResult();
    }

    @Override
    public TaxEdit getEdit() {
        return TaxEditFactory.getTaxEdit();
    }

    @Override
    public Tax getEntity(EditTaxResult result) {
        return taxLogic.getTaxByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public Tax getLockEntity(Tax tax) {
        return tax;
    }

    @Override
    public void fillInResult(EditTaxResult result, Tax tax) {
        result.setTax(taxControl.getTaxTransfer(getUserVisit(), tax));
    }

    @Override
    public void doLock(TaxEdit edit, Tax tax) {
        var taxDescription = taxControl.getTaxDescription(tax, getPreferredLanguage());
        var taxDetail = tax.getLastDetail();

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
    }

    @Override
    public void canUpdate(Tax tax) {
        var taxName = edit.getTaxName();
        var duplicateTax = taxControl.getTaxByName(taxName);
        var contactMechanismPurposeName = edit.getContactMechanismPurposeName();
        var glAccountName = edit.getGlAccountName();

        if(duplicateTax != null && !tax.equals(duplicateTax)) {
            addExecutionError(ExecutionErrors.DuplicateTaxName.name(), taxName);
        }

        contactMechanismPurpose = contactMechanismPurposeLogic.getContactMechanismPurposeByName(this, contactMechanismPurposeName);
        glAccount = glAccountLogic.getGlAccountByName(this, glAccountName);

        if(!hasExecutionErrors()) {
            if(!ContactMechanismTypes.POSTAL_ADDRESS.name().equals(contactMechanismPurpose.getContactMechanismType().getContactMechanismTypeName())) {
                addExecutionError(ExecutionErrors.InvalidContactMechanismPurpose.name(), contactMechanismPurposeName);
            }
        }
    }

    @Override
    public void doUpdate(Tax tax) {
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
    }

}
