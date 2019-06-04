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

import com.echothree.control.user.item.common.edit.HarmonizedTariffScheduleCodeEdit;
import com.echothree.control.user.item.common.edit.ItemEditFactory;
import com.echothree.control.user.item.common.form.EditHarmonizedTariffScheduleCodeForm;
import com.echothree.control.user.item.common.result.EditHarmonizedTariffScheduleCodeResult;
import com.echothree.control.user.item.common.result.ItemResultFactory;
import com.echothree.control.user.item.common.spec.HarmonizedTariffScheduleCodeSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.server.logic.MimeTypeLogic;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeDetail;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCode;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeDetail;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeTranslation;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUnit;
import com.echothree.model.data.item.server.value.HarmonizedTariffScheduleCodeDetailValue;
import com.echothree.model.data.item.server.value.HarmonizedTariffScheduleCodeTranslationValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditHarmonizedTariffScheduleCodeCommand
        extends BaseAbstractEditCommand<HarmonizedTariffScheduleCodeSpec, HarmonizedTariffScheduleCodeEdit, EditHarmonizedTariffScheduleCodeResult, HarmonizedTariffScheduleCode, HarmonizedTariffScheduleCode> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.HarmonizedTariffScheduleCode.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CountryName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("HarmonizedTariffScheduleCodeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("HarmonizedTariffScheduleCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("FirstHarmonizedTariffScheduleCodeUnitName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SecondHarmonizedTariffScheduleCodeUnitName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("OverviewMimeTypeName", FieldType.MIME_TYPE, false, null, null),
                new FieldDefinition("Overview", FieldType.STRING, false, null, null)
                ));
    }

    /** Creates a new instance of EditHarmonizedTariffScheduleCodeCommand */
    public EditHarmonizedTariffScheduleCodeCommand(UserVisitPK userVisitPK, EditHarmonizedTariffScheduleCodeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditHarmonizedTariffScheduleCodeResult getResult() {
        return ItemResultFactory.getEditHarmonizedTariffScheduleCodeResult();
    }

    @Override
    public HarmonizedTariffScheduleCodeEdit getEdit() {
        return ItemEditFactory.getHarmonizedTariffScheduleCodeEdit();
    }

    GeoCode countryGeoCode;

    @Override
    public HarmonizedTariffScheduleCode getEntity(EditHarmonizedTariffScheduleCodeResult result) {
        var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        HarmonizedTariffScheduleCode harmonizedTariffScheduleCode = null;
        String countryName = spec.getCountryName();

        countryGeoCode = geoControl.getCountryByAlias(countryName);

        if(countryGeoCode != null) {
            var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
            String harmonizedTariffScheduleCodeName = spec.getHarmonizedTariffScheduleCodeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                harmonizedTariffScheduleCode = itemControl.getHarmonizedTariffScheduleCodeByName(countryGeoCode, harmonizedTariffScheduleCodeName);
            } else { // EditMode.UPDATE
                harmonizedTariffScheduleCode = itemControl.getHarmonizedTariffScheduleCodeByNameForUpdate(countryGeoCode, harmonizedTariffScheduleCodeName);
            }

            if(harmonizedTariffScheduleCode == null) {
                addExecutionError(ExecutionErrors.UnknownHarmonizedTariffScheduleCodeName.name(), countryName, harmonizedTariffScheduleCodeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), countryName);
        }

        return harmonizedTariffScheduleCode;
    }

    @Override
    public HarmonizedTariffScheduleCode getLockEntity(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        return harmonizedTariffScheduleCode;
    }

    @Override
    public void fillInResult(EditHarmonizedTariffScheduleCodeResult result, HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);

        result.setHarmonizedTariffScheduleCode(itemControl.getHarmonizedTariffScheduleCodeTransfer(getUserVisit(), harmonizedTariffScheduleCode));
    }

    MimeType overviewMimeType;
    
    HarmonizedTariffScheduleCodeUnit firstHarmonizedTariffScheduleCodeUnit;
    HarmonizedTariffScheduleCodeUnit secondHarmonizedTariffScheduleCodeUnit;

    @Override
    public void doLock(HarmonizedTariffScheduleCodeEdit edit, HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation = itemControl.getHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCode, getPreferredLanguage());
        HarmonizedTariffScheduleCodeDetail harmonizedTariffScheduleCodeDetail = harmonizedTariffScheduleCode.getLastDetail();

        firstHarmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCodeDetail.getFirstHarmonizedTariffScheduleCodeUnit();
        secondHarmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCodeDetail.getSecondHarmonizedTariffScheduleCodeUnit();
        
        edit.setHarmonizedTariffScheduleCodeName(harmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCodeName());
        edit.setFirstHarmonizedTariffScheduleCodeUnitName(firstHarmonizedTariffScheduleCodeUnit == null ? null : firstHarmonizedTariffScheduleCodeUnit.getLastDetail().getHarmonizedTariffScheduleCodeUnitName());
        edit.setSecondHarmonizedTariffScheduleCodeUnitName(secondHarmonizedTariffScheduleCodeUnit == null ? null : secondHarmonizedTariffScheduleCodeUnit.getLastDetail().getHarmonizedTariffScheduleCodeUnitName());
        edit.setIsDefault(harmonizedTariffScheduleCodeDetail.getIsDefault().toString());
        edit.setSortOrder(harmonizedTariffScheduleCodeDetail.getSortOrder().toString());

        if(harmonizedTariffScheduleCodeTranslation != null) {
            overviewMimeType = harmonizedTariffScheduleCodeTranslation.getOverviewMimeType();
            
            edit.setDescription(harmonizedTariffScheduleCodeTranslation.getDescription());
            edit.setOverviewMimeTypeName(overviewMimeType == null? null: overviewMimeType.getLastDetail().getMimeTypeName());
            edit.setOverview(harmonizedTariffScheduleCodeTranslation.getOverview());
        }
    }
    
    @Override
    public void canUpdate(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        GeoCodeDetail geoCodeDetail = countryGeoCode.getLastDetail();
        String harmonizedTariffScheduleCodeName = edit.getHarmonizedTariffScheduleCodeName();
        HarmonizedTariffScheduleCode duplicateHarmonizedTariffScheduleCode = itemControl.getHarmonizedTariffScheduleCodeByName(countryGeoCode, harmonizedTariffScheduleCodeName);

        if(duplicateHarmonizedTariffScheduleCode != null && !harmonizedTariffScheduleCode.equals(duplicateHarmonizedTariffScheduleCode)) {
            addExecutionError(ExecutionErrors.DuplicateHarmonizedTariffScheduleCodeName.name(), geoCodeDetail.getGeoCodeName(), harmonizedTariffScheduleCodeName);
        } else {
            String overviewMimeTypeName = edit.getOverviewMimeTypeName();
            String overview = edit.getOverview();

            overviewMimeType = MimeTypeLogic.getInstance().checkMimeType(this, overviewMimeTypeName, overview, MimeTypeUsageTypes.TEXT.name(),
                    ExecutionErrors.MissingRequiredOverviewMimeTypeName.name(), ExecutionErrors.MissingRequiredOverview.name(),
                    ExecutionErrors.UnknownOverviewMimeTypeName.name(), ExecutionErrors.UnknownOverviewMimeTypeUsage.name());
            
            if(!hasExecutionErrors()) {
                String firstHarmonizedTariffScheduleCodeUnitName = edit.getFirstHarmonizedTariffScheduleCodeUnitName();
                
                firstHarmonizedTariffScheduleCodeUnit = firstHarmonizedTariffScheduleCodeUnitName == null ? null : itemControl.getHarmonizedTariffScheduleCodeUnitByName(firstHarmonizedTariffScheduleCodeUnitName);
                
                if(firstHarmonizedTariffScheduleCodeUnitName == null || firstHarmonizedTariffScheduleCodeUnit != null) {
                    String secondHarmonizedTariffScheduleCodeUnitName = edit.getSecondHarmonizedTariffScheduleCodeUnitName();
                    
                    secondHarmonizedTariffScheduleCodeUnit = secondHarmonizedTariffScheduleCodeUnitName == null ? null : itemControl.getHarmonizedTariffScheduleCodeUnitByName(secondHarmonizedTariffScheduleCodeUnitName);

                    if(secondHarmonizedTariffScheduleCodeUnitName != null && secondHarmonizedTariffScheduleCodeUnit == null) {
                        addExecutionError(ExecutionErrors.UnknownSecondHarmonizedTariffScheduleCodeUnitName.name(), secondHarmonizedTariffScheduleCodeUnitName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownFirstHarmonizedTariffScheduleCodeUnitName.name(), firstHarmonizedTariffScheduleCodeUnitName);
                }
            }
        }
    }

    @Override
    public void doUpdate(HarmonizedTariffScheduleCode harmonizedTariffScheduleCode) {
        var itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        PartyPK partyPK = getPartyPK();
        HarmonizedTariffScheduleCodeDetailValue harmonizedTariffScheduleCodeDetailValue = itemControl.getHarmonizedTariffScheduleCodeDetailValueForUpdate(harmonizedTariffScheduleCode);
        HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation = itemControl.getHarmonizedTariffScheduleCodeTranslationForUpdate(harmonizedTariffScheduleCode, getPreferredLanguage());
        String description = edit.getDescription();
        String overview = edit.getOverview();

        harmonizedTariffScheduleCodeDetailValue.setHarmonizedTariffScheduleCodeName(edit.getHarmonizedTariffScheduleCodeName());
        harmonizedTariffScheduleCodeDetailValue.setFirstHarmonizedTariffScheduleCodeUnitPK(firstHarmonizedTariffScheduleCodeUnit == null ? null : firstHarmonizedTariffScheduleCodeUnit.getPrimaryKey());
        harmonizedTariffScheduleCodeDetailValue.setSecondHarmonizedTariffScheduleCodeUnitPK(secondHarmonizedTariffScheduleCodeUnit == null ? null : secondHarmonizedTariffScheduleCodeUnit.getPrimaryKey());
        harmonizedTariffScheduleCodeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        harmonizedTariffScheduleCodeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        itemControl.updateHarmonizedTariffScheduleCodeFromValue(harmonizedTariffScheduleCodeDetailValue, partyPK);

        if(harmonizedTariffScheduleCodeTranslation == null && (description != null || overview != null)) {
            itemControl.createHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCode, getPreferredLanguage(), description, overviewMimeType, overview, partyPK);
        } else if(harmonizedTariffScheduleCodeTranslation != null && (description == null && overview == null)) {
            itemControl.deleteHarmonizedTariffScheduleCodeTranslation(harmonizedTariffScheduleCodeTranslation, partyPK);
        } else if(harmonizedTariffScheduleCodeTranslation != null && (description != null || overview != null)) {
            HarmonizedTariffScheduleCodeTranslationValue harmonizedTariffScheduleCodeTranslationValue = itemControl.getHarmonizedTariffScheduleCodeTranslationValue(harmonizedTariffScheduleCodeTranslation);

            harmonizedTariffScheduleCodeTranslationValue.setDescription(description);
            harmonizedTariffScheduleCodeTranslationValue.setOverviewMimeTypePK(overviewMimeType == null? null: overviewMimeType.getPrimaryKey());
            harmonizedTariffScheduleCodeTranslationValue.setOverview(overview);
            itemControl.updateHarmonizedTariffScheduleCodeTranslationFromValue(harmonizedTariffScheduleCodeTranslationValue, partyPK);
        }
    }

}
