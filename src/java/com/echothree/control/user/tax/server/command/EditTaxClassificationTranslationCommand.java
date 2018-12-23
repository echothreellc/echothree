// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.tax.common.edit.TaxClassificationTranslationEdit;
import com.echothree.control.user.tax.common.edit.TaxEditFactory;
import com.echothree.control.user.tax.common.form.EditTaxClassificationTranslationForm;
import com.echothree.control.user.tax.common.result.EditTaxClassificationTranslationResult;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.control.user.tax.common.spec.TaxClassificationTranslationSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tax.server.TaxControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.tax.server.entity.TaxClassification;
import com.echothree.model.data.tax.server.entity.TaxClassificationTranslation;
import com.echothree.model.data.tax.server.value.TaxClassificationTranslationValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditTaxClassificationTranslationCommand
        extends BaseAbstractEditCommand<TaxClassificationTranslationSpec, TaxClassificationTranslationEdit, EditTaxClassificationTranslationResult, TaxClassificationTranslation, TaxClassification> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TaxClassification.name(), SecurityRoles.Translation.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TaxClassificationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("OverviewMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Overview", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of EditTaxClassificationTranslationCommand */
    public EditTaxClassificationTranslationCommand(UserVisitPK userVisitPK, EditTaxClassificationTranslationForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTaxClassificationTranslationResult getResult() {
        return TaxResultFactory.getEditTaxClassificationTranslationResult();
    }

    @Override
    public TaxClassificationTranslationEdit getEdit() {
        return TaxEditFactory.getTaxClassificationTranslationEdit();
    }

    @Override
    public TaxClassificationTranslation getEntity(EditTaxClassificationTranslationResult result) {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        TaxClassificationTranslation taxClassificationTranslation = null;
        String countryName = spec.getCountryName();
        GeoCode countryGeoCode = geoControl.getCountryByAlias(countryName);

        if(countryGeoCode != null) {
            TaxControl taxControl = (TaxControl)Session.getModelController(TaxControl.class);
            String taxClassificationName = spec.getTaxClassificationName();
            TaxClassification taxClassification = taxControl.getTaxClassificationByName(countryGeoCode, taxClassificationName);

            if(taxClassification != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        taxClassificationTranslation = taxControl.getTaxClassificationTranslation(taxClassification, language);
                    } else { // EditMode.UPDATE
                        taxClassificationTranslation = taxControl.getTaxClassificationTranslationForUpdate(taxClassification, language);
                    }

                    if(taxClassificationTranslation == null) {
                        addExecutionError(ExecutionErrors.UnknownTaxClassificationTranslation.name(), countryName, taxClassificationName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTaxClassificationName.name(), countryName, taxClassificationName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), countryName);
        }

        return taxClassificationTranslation;
    }

    @Override
    public TaxClassification getLockEntity(TaxClassificationTranslation taxClassificationTranslation) {
        return taxClassificationTranslation.getTaxClassification();
    }

    @Override
    public void fillInResult(EditTaxClassificationTranslationResult result, TaxClassificationTranslation taxClassificationTranslation) {
        TaxControl taxControl = (TaxControl)Session.getModelController(TaxControl.class);

        result.setTaxClassificationTranslation(taxControl.getTaxClassificationTranslationTransfer(getUserVisit(), taxClassificationTranslation));
    }

    MimeType overviewMimeType;
    
    @Override
    public void doLock(TaxClassificationTranslationEdit edit, TaxClassificationTranslation taxClassificationTranslation) {
        overviewMimeType = taxClassificationTranslation.getOverviewMimeType();
        
        edit.setDescription(taxClassificationTranslation.getDescription());
        edit.setOverviewMimeTypeName(overviewMimeType == null? null: overviewMimeType.getLastDetail().getMimeTypeName());
        edit.setOverview(taxClassificationTranslation.getOverview());
    }

    @Override
    protected void canUpdate(TaxClassificationTranslation taxClassificationTranslation) {
        String overviewMimeTypeName = edit.getOverviewMimeTypeName();
        String overview = edit.getOverview();
        
        overviewMimeType = MimeTypeLogic.getInstance().checkMimeType(this, overviewMimeTypeName, overview, MimeTypeUsageTypes.TEXT.name(),
                ExecutionErrors.MissingRequiredOverviewMimeTypeName.name(), ExecutionErrors.MissingRequiredOverview.name(),
                ExecutionErrors.UnknownOverviewMimeTypeName.name(), ExecutionErrors.UnknownOverviewMimeTypeUsage.name());
    }
    
    @Override
    public void doUpdate(TaxClassificationTranslation taxClassificationTranslation) {
        TaxControl taxControl = (TaxControl)Session.getModelController(TaxControl.class);
        TaxClassificationTranslationValue taxClassificationTranslationValue = taxControl.getTaxClassificationTranslationValue(taxClassificationTranslation);

        taxClassificationTranslationValue.setDescription(edit.getDescription());
        taxClassificationTranslationValue.setOverviewMimeTypePK(overviewMimeType == null ? null : overviewMimeType.getPrimaryKey());
        taxClassificationTranslationValue.setOverview(edit.getOverview());

        taxControl.updateTaxClassificationTranslationFromValue(taxClassificationTranslationValue, getPartyPK());
    }

}
