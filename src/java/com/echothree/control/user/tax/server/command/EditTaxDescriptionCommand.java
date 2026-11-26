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

import com.echothree.control.user.tax.common.edit.TaxDescriptionEdit;
import com.echothree.control.user.tax.common.edit.TaxEditFactory;
import com.echothree.control.user.tax.common.result.EditTaxDescriptionResult;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.control.user.tax.common.spec.TaxDescriptionSpec;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.control.tax.server.logic.TaxLogic;
import com.echothree.model.data.tax.server.entity.Tax;
import com.echothree.model.data.tax.server.entity.TaxDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditTaxDescriptionCommand
        extends BaseEditCommand<TaxDescriptionSpec, TaxDescriptionEdit, EditTaxDescriptionResult, TaxDescription, Tax> {

    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TaxName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    TaxControl taxControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    TaxLogic taxLogic;

    /** Creates a new instance of EditTaxDescriptionCommand */
    public EditTaxDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTaxDescriptionResult getResult() {
        return TaxResultFactory.getEditTaxDescriptionResult();
    }

    @Override
    public TaxDescriptionEdit getEdit() {
        return TaxEditFactory.getTaxDescriptionEdit();
    }

    @Override
    public TaxDescription getEntity(EditTaxDescriptionResult result) {
        TaxDescription taxDescription = null;
        var taxName = spec.getTaxName();
        var languageIsoName = spec.getLanguageIsoName();
        var tax = taxLogic.getTaxByName(this, taxName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            taxDescription = taxControl.getTaxDescription(tax, language,
                    editModeToEntityPermission(editMode));

            if(taxDescription == null) {
                addExecutionError(ExecutionErrors.UnknownTaxDescription.name(), taxName, languageIsoName);
            }
        }

        return taxDescription;
    }

    @Override
    public Tax getLockEntity(TaxDescription taxDescription) {
        return taxDescription.getTax();
    }

    @Override
    public void fillInResult(EditTaxDescriptionResult result, TaxDescription taxDescription) {
        result.setTaxDescription(taxControl.getTaxDescriptionTransfer(getUserVisit(), taxDescription));
    }

    @Override
    public void doLock(TaxDescriptionEdit edit, TaxDescription taxDescription) {
        edit.setDescription(taxDescription.getDescription());
    }

    @Override
    public void doUpdate(TaxDescription taxDescription) {
        var taxDescriptionValue = taxControl.getTaxDescriptionValue(taxDescription);

        taxDescriptionValue.setDescription(edit.getDescription());

        taxControl.updateTaxDescriptionFromValue(taxDescriptionValue, getPartyPK());
    }

}
