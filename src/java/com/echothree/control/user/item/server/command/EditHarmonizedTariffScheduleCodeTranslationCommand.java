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

package com.echothree.control.user.item.server.command;

import com.echothree.control.user.item.common.edit.HarmonizedTariffScheduleCodeTranslationEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditHarmonizedTariffScheduleCodeTranslationForm;
import com.echothree.control.user.item.common.result.EditHarmonizedTariffScheduleCodeTranslationResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.HarmonizedTariffScheduleCodeTranslationSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCode;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeTranslation;
import com.echothree.model.data.item.server.value.HarmonizedTariffScheduleCodeTranslationValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditHarmonizedTariffScheduleCodeTranslationCommand
        extends BaseAbstractEditCommand<HarmonizedTariffScheduleCodeTranslationSpec, HarmonizedTariffScheduleCodeTranslationEdit, EditHarmonizedTariffScheduleCodeTranslationResult, HarmonizedTariffScheduleCodeTranslation, HarmonizedTariffScheduleCode> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.HarmonizedTariffScheduleCode.name(), SecurityRoles.Translation.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("HarmonizedTariffScheduleCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L),
                new FieldDefinition("OverviewMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Overview", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of EditHarmonizedTariffScheduleCodeTranslationCommand */
    public EditHarmonizedTariffScheduleCodeTranslationCommand(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeTranslationForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditHarmonizedTariffScheduleCodeTranslationResult getResult() {
        return ItemResultFactory.getEditHarmonizedTariffScheduleCodeTranslationResult();
    }

    @Override
    public HarmonizedTariffScheduleCodeTranslationEdit getEdit() {
        return ItemEditFactory.getHarmonizedTariffScheduleCodeTranslationEdit();
    }

    @Override
    public HarmonizedTariffScheduleCodeTranslation getEntity(EditHarmonizedTariffScheduleCodeTranslationResult result) {
        GeoControl geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation = null;
        String countryName = spec.getCountryName();
        GeoCode countryGeoCode = geoControl.getCountryByAlias(countryName);

        if(countryGeoCode != null) {
            ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
            String harmonizedTariffScheduleCodeName = spec.getHarmonizedTariffScheduleCodeName();
            HarmonizedTariffScheduleCode harmonizedTariffScheduleCode = itemControl.getHarmonizedTariffScheduleCodeByName(countryGeoCode, harmonizedTariffScheduleCodeName);

            if(harmonizedTariffScheduleCode != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        harmonizedTariffScheduleCodeTranslation = itemControl.getHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCode, language);
                    } else { // EditMode.UPDATE
                        harmonizedTariffScheduleCodeTranslation = itemControl.getHarmonizedTariffScheduleCodeTranslationForUpdate(harmonizedTariffScheduleCode, language);
                    }

                    if(harmonizedTariffScheduleCodeTranslation == null) {
                        addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeTranslation.name(), countryName, harmonizedTariffScheduleCodeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeName.name(), countryName, harmonizedTariffScheduleCodeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), countryName);
        }

        return harmonizedTariffScheduleCodeTranslation;
    }

    @Override
    public HarmonizedTariffScheduleCode getLockEntity(HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation) {
        return harmonizedTariffScheduleCodeTranslation.getHarmonizedTariffScheduleCode();
    }

    @Override
    public void fillInResult(EditHarmonizedTariffScheduleCodeTranslationResult result, HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);

        result.setHarmonizedTariffScheduleCodeTranslation(itemControl.getHarmonizedTariffScheduleCodeTranslationTransfer(getUserVisit(), harmonizedTariffScheduleCodeTranslation));
    }

    MimeType overviewMimeType;
    
    @Override
    public void doLock(HarmonizedTariffScheduleCodeTranslationEdit edit, HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation) {
        overviewMimeType = harmonizedTariffScheduleCodeTranslation.getOverviewMimeType();
        
        edit.setDescription(harmonizedTariffScheduleCodeTranslation.getDescription());
        edit.setOverviewMimeTypeName(overviewMimeType == null? null: overviewMimeType.getLastDetail().getMimeTypeName());
        edit.setOverview(harmonizedTariffScheduleCodeTranslation.getOverview());
    }

    @Override
    protected void canUpdate(HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation) {
        String overviewMimeTypeName = edit.getOverviewMimeTypeName();
        String overview = edit.getOverview();
        
        overviewMimeType = MimeTypeLogic.getInstance().checkMimeType(this, overviewMimeTypeName, overview, MimeTypeUsageTypes.TEXT.name(),
                ExecutionErrors.MissingRequiredOverviewMimeTypeName.name(), ExecutionErrors.MissingRequiredOverview.name(),
                ExecutionErrors.UnknownOverviewMimeTypeName.name(), ExecutionErrors.UnknownOverviewMimeTypeUsage.name());
    }
    
    @Override
    public void doUpdate(HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        HarmonizedTariffScheduleCodeTranslationValue harmonizedTariffScheduleCodeTranslationValue = itemControl.getHarmonizedTariffScheduleCodeTranslationValue(harmonizedTariffScheduleCodeTranslation);

        harmonizedTariffScheduleCodeTranslationValue.setDescription(edit.getDescription());
        harmonizedTariffScheduleCodeTranslationValue.setOverviewMimeTypePK(overviewMimeType == null ? null : overviewMimeType.getPrimaryKey());
        harmonizedTariffScheduleCodeTranslationValue.setOverview(edit.getOverview());

        itemControl.updateHarmonizedTariffScheduleCodeTranslationFromValue(harmonizedTariffScheduleCodeTranslationValue, getPartyPK());
    }

}
