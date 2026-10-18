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

package com.echothree.control.user.geo.server.command;

import com.echothree.control.user.geo.common.edit.GeoCodeLanguageEdit;
import com.echothree.control.user.geo.common.edit.GeoEditFactory;
import com.echothree.control.user.geo.common.result.EditGeoCodeLanguageResult;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.spec.GeoCodeLanguageSpec;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.geo.server.logic.GeoCodeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeLanguage;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditGeoCodeLanguageCommand
        extends BaseAbstractEditCommand<GeoCodeLanguageSpec, GeoCodeLanguageEdit, EditGeoCodeLanguageResult, GeoCodeLanguage, GeoCode> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeLanguage.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }

    @Inject
    GeoControl geoControl;

    @Inject
    GeoCodeLogic geoCodeLogic;

    @Inject
    LanguageLogic languageLogic;

    /** Creates a new instance of EditGeoCodeLanguageCommand */
    public EditGeoCodeLanguageCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGeoCodeLanguageResult getResult() {
        return GeoResultFactory.getEditGeoCodeLanguageResult();
    }

    @Override
    public GeoCodeLanguageEdit getEdit() {
        return GeoEditFactory.getGeoCodeLanguageEdit();
    }

    @Override
    public GeoCodeLanguage getEntity(EditGeoCodeLanguageResult result) {
        GeoCodeLanguage geoCodeLanguage = null;
        var geoCodeName = spec.getGeoCodeName();
        var geoCode = geoCodeLogic.getGeoCodeByName(this, geoCodeName);

        if(!hasExecutionErrors()) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(this, languageIsoName);

            if(!hasExecutionErrors()) {
                geoCodeLanguage = geoControl.getGeoCodeLanguage(geoCode, language, editModeToEntityPermission(editMode));

                if(geoCodeLanguage == null) {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeLanguage.name(), geoCodeName, languageIsoName);
                }
            }
        }

        return geoCodeLanguage;
    }

    @Override
    public GeoCode getLockEntity(GeoCodeLanguage geoCodeLanguage) {
        return geoCodeLanguage.getGeoCode();
    }

    @Override
    public void fillInResult(EditGeoCodeLanguageResult result, GeoCodeLanguage geoCodeLanguage) {
        result.setGeoCodeLanguage(geoControl.getGeoCodeLanguageTransfer(getUserVisit(), geoCodeLanguage));
    }

    @Override
    public void doLock(GeoCodeLanguageEdit edit, GeoCodeLanguage geoCodeLanguage) {
        edit.setIsDefault(geoCodeLanguage.getIsDefault().toString());
        edit.setSortOrder(geoCodeLanguage.getSortOrder().toString());
    }

    @Override
    public void doUpdate(GeoCodeLanguage geoCodeLanguage) {
        var geoCodeLanguageValue = geoControl.getGeoCodeLanguageValue(geoCodeLanguage);

        geoCodeLanguageValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        geoCodeLanguageValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        geoControl.updateGeoCodeLanguageFromValue(geoCodeLanguageValue, getPartyPK());
    }

}
