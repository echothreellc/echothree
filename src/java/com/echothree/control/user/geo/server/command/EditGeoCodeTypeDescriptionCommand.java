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

package com.echothree.control.user.geo.server.command;

import com.echothree.control.user.geo.common.edit.GeoCodeTypeDescriptionEdit;
import com.echothree.control.user.geo.common.edit.GeoEditFactory;
import com.echothree.control.user.geo.common.form.EditGeoCodeTypeDescriptionForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeTypeDescriptionResult;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.spec.GeoCodeTypeDescriptionSpec;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.geo.server.entity.GeoCodeTypeDescription;
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

public class EditGeoCodeTypeDescriptionCommand
        extends BaseAbstractEditCommand<GeoCodeTypeDescriptionSpec, GeoCodeTypeDescriptionEdit, EditGeoCodeTypeDescriptionResult, GeoCodeTypeDescription, GeoCodeType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditGeoCodeTypeDescriptionCommand */
    public EditGeoCodeTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGeoCodeTypeDescriptionResult getResult() {
        return GeoResultFactory.getEditGeoCodeTypeDescriptionResult();
    }

    @Override
    public GeoCodeTypeDescriptionEdit getEdit() {
        return GeoEditFactory.getGeoCodeTypeDescriptionEdit();
    }

    @Override
    public GeoCodeTypeDescription getEntity(EditGeoCodeTypeDescriptionResult result) {
        var geoControl = Session.getModelController(GeoControl.class);
        GeoCodeTypeDescription geoCodeTypeDescription = null;
        var geoCodeTypeName = spec.getGeoCodeTypeName();
        var geoCodeType = geoControl.getGeoCodeTypeByName(geoCodeTypeName);

        if(geoCodeType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    geoCodeTypeDescription = geoControl.getGeoCodeTypeDescription(geoCodeType, language);
                } else { // EditMode.UPDATE
                    geoCodeTypeDescription = geoControl.getGeoCodeTypeDescriptionForUpdate(geoCodeType, language);
                }

                if(geoCodeTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeTypeDescription.name(), geoCodeTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeTypeName.name(), geoCodeTypeName);
        }

        return geoCodeTypeDescription;
    }

    @Override
    public GeoCodeType getLockEntity(GeoCodeTypeDescription geoCodeTypeDescription) {
        return geoCodeTypeDescription.getGeoCodeType();
    }

    @Override
    public void fillInResult(EditGeoCodeTypeDescriptionResult result, GeoCodeTypeDescription geoCodeTypeDescription) {
        var geoControl = Session.getModelController(GeoControl.class);

        result.setGeoCodeTypeDescription(geoControl.getGeoCodeTypeDescriptionTransfer(getUserVisit(), geoCodeTypeDescription));
    }

    @Override
    public void doLock(GeoCodeTypeDescriptionEdit edit, GeoCodeTypeDescription geoCodeTypeDescription) {
        edit.setDescription(geoCodeTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(GeoCodeTypeDescription geoCodeTypeDescription) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeTypeDescriptionValue = geoControl.getGeoCodeTypeDescriptionValue(geoCodeTypeDescription);
        geoCodeTypeDescriptionValue.setDescription(edit.getDescription());

        geoControl.updateGeoCodeTypeDescriptionFromValue(geoCodeTypeDescriptionValue, getPartyPK());
    }

}
