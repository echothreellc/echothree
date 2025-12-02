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

import com.echothree.control.user.geo.common.edit.GeoCodeAliasTypeDescriptionEdit;
import com.echothree.control.user.geo.common.edit.GeoEditFactory;
import com.echothree.control.user.geo.common.form.EditGeoCodeAliasTypeDescriptionForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeAliasTypeDescriptionResult;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.spec.GeoCodeAliasTypeDescriptionSpec;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasTypeDescription;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditGeoCodeAliasTypeDescriptionCommand
        extends BaseAbstractEditCommand<GeoCodeAliasTypeDescriptionSpec, GeoCodeAliasTypeDescriptionEdit, EditGeoCodeAliasTypeDescriptionResult, GeoCodeAliasTypeDescription, GeoCodeAliasType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeAliasType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GeoCodeAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditGeoCodeAliasTypeDescriptionCommand */
    public EditGeoCodeAliasTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGeoCodeAliasTypeDescriptionResult getResult() {
        return GeoResultFactory.getEditGeoCodeAliasTypeDescriptionResult();
    }

    @Override
    public GeoCodeAliasTypeDescriptionEdit getEdit() {
        return GeoEditFactory.getGeoCodeAliasTypeDescriptionEdit();
    }

    @Override
    public GeoCodeAliasTypeDescription getEntity(EditGeoCodeAliasTypeDescriptionResult result) {
        var geoControl = Session.getModelController(GeoControl.class);
        GeoCodeAliasTypeDescription geoAliasTypeDescription = null;
        var geoCodeTypeName = spec.getGeoCodeTypeName();
        var geoCodeType = geoControl.getGeoCodeTypeByName(geoCodeTypeName);

        if(geoCodeType != null) {
            var geoAliasTypeName = spec.getGeoCodeAliasTypeName();
            var geoAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, geoAliasTypeName);

            if(geoAliasType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        geoAliasTypeDescription = geoControl.getGeoCodeAliasTypeDescription(geoAliasType, language);
                    } else { // EditMode.UPDATE
                        geoAliasTypeDescription = geoControl.getGeoCodeAliasTypeDescriptionForUpdate(geoAliasType, language);
                    }

                    if(geoAliasTypeDescription == null) {
                        addExecutionError(ExecutionErrors.UnknownGeoCodeAliasTypeDescription.name(), geoCodeTypeName, geoAliasTypeName, languageIsoName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGeoCodeAliasTypeName.name(), geoCodeTypeName, geoAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeTypeName.name(), geoCodeTypeName);
        }

        return geoAliasTypeDescription;
    }

    @Override
    public GeoCodeAliasType getLockEntity(GeoCodeAliasTypeDescription geoAliasTypeDescription) {
        return geoAliasTypeDescription.getGeoCodeAliasType();
    }

    @Override
    public void fillInResult(EditGeoCodeAliasTypeDescriptionResult result, GeoCodeAliasTypeDescription geoAliasTypeDescription) {
        var geoControl = Session.getModelController(GeoControl.class);

        result.setGeoCodeAliasTypeDescription(geoControl.getGeoCodeAliasTypeDescriptionTransfer(getUserVisit(), geoAliasTypeDescription));
    }

    @Override
    public void doLock(GeoCodeAliasTypeDescriptionEdit edit, GeoCodeAliasTypeDescription geoAliasTypeDescription) {
        edit.setDescription(geoAliasTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(GeoCodeAliasTypeDescription geoAliasTypeDescription) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoAliasTypeDescriptionValue = geoControl.getGeoCodeAliasTypeDescriptionValue(geoAliasTypeDescription);

        geoAliasTypeDescriptionValue.setDescription(edit.getDescription());

        geoControl.updateGeoCodeAliasTypeDescriptionFromValue(geoAliasTypeDescriptionValue, getPartyPK());
    }


}
