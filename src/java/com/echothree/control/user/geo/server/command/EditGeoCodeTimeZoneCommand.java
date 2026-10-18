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

import com.echothree.control.user.geo.common.edit.GeoCodeTimeZoneEdit;
import com.echothree.control.user.geo.common.edit.GeoEditFactory;
import com.echothree.control.user.geo.common.result.EditGeoCodeTimeZoneResult;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.spec.GeoCodeTimeZoneSpec;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.geo.server.logic.GeoCodeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.TimeZoneLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeTimeZone;
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
public class EditGeoCodeTimeZoneCommand
        extends BaseAbstractEditCommand<GeoCodeTimeZoneSpec, GeoCodeTimeZoneEdit, EditGeoCodeTimeZoneResult, GeoCodeTimeZone, GeoCode> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeTimeZone.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("JavaTimeZoneName", FieldType.TIME_ZONE_NAME, true, null, null)
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
    TimeZoneLogic timeZoneLogic;

    /** Creates a new instance of EditGeoCodeTimeZoneCommand */
    public EditGeoCodeTimeZoneCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGeoCodeTimeZoneResult getResult() {
        return GeoResultFactory.getEditGeoCodeTimeZoneResult();
    }

    @Override
    public GeoCodeTimeZoneEdit getEdit() {
        return GeoEditFactory.getGeoCodeTimeZoneEdit();
    }

    @Override
    public GeoCodeTimeZone getEntity(EditGeoCodeTimeZoneResult result) {
        GeoCodeTimeZone geoCodeTimeZone = null;
        var geoCodeName = spec.getGeoCodeName();
        var geoCode = geoCodeLogic.getGeoCodeByName(this, geoCodeName);

        if(!hasExecutionErrors()) {
            var javaTimeZoneName = spec.getJavaTimeZoneName();
            var timeZone = timeZoneLogic.getTimeZoneByName(this, javaTimeZoneName);

            if(!hasExecutionErrors()) {
                geoCodeTimeZone = geoControl.getGeoCodeTimeZone(geoCode, timeZone, editModeToEntityPermission(editMode));

                if(geoCodeTimeZone == null) {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeTimeZone.name(), geoCodeName, javaTimeZoneName);
                }
            }
        }

        return geoCodeTimeZone;
    }

    @Override
    public GeoCode getLockEntity(GeoCodeTimeZone geoCodeTimeZone) {
        return geoCodeTimeZone.getGeoCode();
    }

    @Override
    public void fillInResult(EditGeoCodeTimeZoneResult result, GeoCodeTimeZone geoCodeTimeZone) {
        result.setGeoCodeTimeZone(geoControl.getGeoCodeTimeZoneTransfer(getUserVisit(), geoCodeTimeZone));
    }

    @Override
    public void doLock(GeoCodeTimeZoneEdit edit, GeoCodeTimeZone geoCodeTimeZone) {
        edit.setIsDefault(geoCodeTimeZone.getIsDefault().toString());
        edit.setSortOrder(geoCodeTimeZone.getSortOrder().toString());
    }

    @Override
    public void doUpdate(GeoCodeTimeZone geoCodeTimeZone) {
        var geoCodeTimeZoneValue = geoControl.getGeoCodeTimeZoneValue(geoCodeTimeZone);

        geoCodeTimeZoneValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        geoCodeTimeZoneValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        geoControl.updateGeoCodeTimeZoneFromValue(geoCodeTimeZoneValue, getPartyPK());
    }

}
