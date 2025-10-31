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

import com.echothree.control.user.tax.common.edit.TaxClassificationEdit;
import com.echothree.control.user.tax.common.edit.TaxEditFactory;
import com.echothree.control.user.tax.common.form.EditTaxClassificationForm;
import com.echothree.control.user.tax.common.result.EditTaxClassificationResult;
import com.echothree.control.user.tax.common.result.TaxResultFactory;
import com.echothree.control.user.tax.common.spec.TaxClassificationSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.tax.server.control.TaxControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.tax.server.entity.TaxClassification;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditTaxClassificationCommand
        extends BaseAbstractEditCommand<TaxClassificationSpec, TaxClassificationEdit, EditTaxClassificationResult, TaxClassification, TaxClassification> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.TaxClassification.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TaxClassificationName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TaxClassificationName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("OverviewMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Overview", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of EditTaxClassificationCommand */
    public EditTaxClassificationCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTaxClassificationResult getResult() {
        return TaxResultFactory.getEditTaxClassificationResult();
    }

    @Override
    public TaxClassificationEdit getEdit() {
        return TaxEditFactory.getTaxClassificationEdit();
    }

    GeoCode countryGeoCode;

    @Override
    public TaxClassification getEntity(EditTaxClassificationResult result) {
        var geoControl = Session.getModelController(GeoControl.class);
        TaxClassification taxClassification = null;
        var countryName = spec.getCountryName();

        countryGeoCode = geoControl.getCountryByAlias(countryName);

        if(countryGeoCode != null) {
            var taxControl = Session.getModelController(TaxControl.class);
            var taxClassificationName = spec.getTaxClassificationName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                taxClassification = taxControl.getTaxClassificationByName(countryGeoCode, taxClassificationName);
            } else { // EditMode.UPDATE
                taxClassification = taxControl.getTaxClassificationByNameForUpdate(countryGeoCode, taxClassificationName);
            }

            if(taxClassification == null) {
                addExecutionError(ExecutionErrors.UnknownTaxClassificationName.name(), countryName, taxClassificationName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), countryName);
        }

        return taxClassification;
    }

    @Override
    public TaxClassification getLockEntity(TaxClassification taxClassification) {
        return taxClassification;
    }

    @Override
    public void fillInResult(EditTaxClassificationResult result, TaxClassification taxClassification) {
        var taxControl = Session.getModelController(TaxControl.class);

        result.setTaxClassification(taxControl.getTaxClassificationTransfer(getUserVisit(), taxClassification));
    }

    MimeType overviewMimeType;
    
    @Override
    public void doLock(TaxClassificationEdit edit, TaxClassification taxClassification) {
        var taxControl = Session.getModelController(TaxControl.class);
        var taxClassificationTranslation = taxControl.getTaxClassificationTranslation(taxClassification, getPreferredLanguage());
        var taxClassificationDetail = taxClassification.getLastDetail();

        edit.setTaxClassificationName(taxClassificationDetail.getTaxClassificationName());
        edit.setIsDefault(taxClassificationDetail.getIsDefault().toString());
        edit.setSortOrder(taxClassificationDetail.getSortOrder().toString());

        if(taxClassificationTranslation != null) {
            overviewMimeType = taxClassificationTranslation.getOverviewMimeType();
            
            edit.setDescription(taxClassificationTranslation.getDescription());
            edit.setOverviewMimeTypeName(overviewMimeType == null? null: overviewMimeType.getLastDetail().getMimeTypeName());
            edit.setOverview(taxClassificationTranslation.getOverview());
        }
    }
    
    @Override
    public void canUpdate(TaxClassification taxClassification) {
        var taxControl = Session.getModelController(TaxControl.class);
        var geoCodeDetail = countryGeoCode.getLastDetail();
        var taxClassificationName = edit.getTaxClassificationName();
        var duplicateTaxClassification = taxControl.getTaxClassificationByName(countryGeoCode, taxClassificationName);

        if(duplicateTaxClassification != null && !taxClassification.equals(duplicateTaxClassification)) {
            addExecutionError(ExecutionErrors.DuplicateTaxClassificationName.name(), geoCodeDetail.getGeoCodeName(), taxClassificationName);
        } else {
            var overviewMimeTypeName = edit.getOverviewMimeTypeName();
            var overview = edit.getOverview();

            overviewMimeType = MimeTypeLogic.getInstance().checkMimeType(this, overviewMimeTypeName, overview, MimeTypeUsageTypes.TEXT.name(),
                    ExecutionErrors.MissingRequiredOverviewMimeTypeName.name(), ExecutionErrors.MissingRequiredOverview.name(),
                    ExecutionErrors.UnknownOverviewMimeTypeName.name(), ExecutionErrors.UnknownOverviewMimeTypeUsage.name());
        }
    }

    @Override
    public void doUpdate(TaxClassification taxClassification) {
        var taxControl = Session.getModelController(TaxControl.class);
        var partyPK = getPartyPK();
        var taxClassificationDetailValue = taxControl.getTaxClassificationDetailValueForUpdate(taxClassification);
        var taxClassificationTranslation = taxControl.getTaxClassificationTranslationForUpdate(taxClassification, getPreferredLanguage());
        var description = edit.getDescription();
        var overview = edit.getOverview();

        taxClassificationDetailValue.setTaxClassificationName(edit.getTaxClassificationName());
        taxClassificationDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        taxClassificationDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        taxControl.updateTaxClassificationFromValue(taxClassificationDetailValue, partyPK);

        if(taxClassificationTranslation == null && (description != null || overview != null)) {
            taxControl.createTaxClassificationTranslation(taxClassification, getPreferredLanguage(), description, overviewMimeType, overview, partyPK);
        } else if(taxClassificationTranslation != null && (description == null && overview == null)) {
            taxControl.deleteTaxClassificationTranslation(taxClassificationTranslation, partyPK);
        } else if(taxClassificationTranslation != null && (description != null || overview != null)) {
            var taxClassificationTranslationValue = taxControl.getTaxClassificationTranslationValue(taxClassificationTranslation);

            taxClassificationTranslationValue.setDescription(description);
            taxClassificationTranslationValue.setOverviewMimeTypePK(overviewMimeType == null? null: overviewMimeType.getPrimaryKey());
            taxClassificationTranslationValue.setOverview(overview);
            taxControl.updateTaxClassificationTranslationFromValue(taxClassificationTranslationValue, partyPK);
        }
    }

}
