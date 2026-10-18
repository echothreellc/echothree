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

import com.echothree.control.user.geo.common.edit.GeoCodeDateTimeFormatEdit;
import com.echothree.control.user.geo.common.edit.GeoEditFactory;
import com.echothree.control.user.geo.common.result.EditGeoCodeDateTimeFormatResult;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.spec.GeoCodeDateTimeFormatSpec;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.geo.server.logic.GeoCodeDateTimeFormatLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeDateTimeFormat;
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
public class EditGeoCodeDateTimeFormatCommand
        extends BaseAbstractEditCommand<GeoCodeDateTimeFormatSpec, GeoCodeDateTimeFormatEdit, EditGeoCodeDateTimeFormatResult, GeoCodeDateTimeFormat, GeoCode> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeDateTimeFormat.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DateTimeFormatName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }

    @Inject
    GeoControl geoControl;

    @Inject
    GeoCodeDateTimeFormatLogic geoCodeDateTimeFormatLogic;

    /** Creates a new instance of EditGeoCodeDateTimeFormatCommand */
    public EditGeoCodeDateTimeFormatCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGeoCodeDateTimeFormatResult getResult() {
        return GeoResultFactory.getEditGeoCodeDateTimeFormatResult();
    }

    @Override
    public GeoCodeDateTimeFormatEdit getEdit() {
        return GeoEditFactory.getGeoCodeDateTimeFormatEdit();
    }

    @Override
    public GeoCodeDateTimeFormat getEntity(EditGeoCodeDateTimeFormatResult result) {
        return geoCodeDateTimeFormatLogic.getGeoCodeDateTimeFormatBySpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public GeoCode getLockEntity(GeoCodeDateTimeFormat geoCodeDateTimeFormat) {
        return geoCodeDateTimeFormat.getGeoCode();
    }

    @Override
    public void fillInResult(EditGeoCodeDateTimeFormatResult result, GeoCodeDateTimeFormat geoCodeDateTimeFormat) {
        result.setGeoCodeDateTimeFormat(geoControl.getGeoCodeDateTimeFormatTransfer(getUserVisit(), geoCodeDateTimeFormat));
    }

    @Override
    public void doLock(GeoCodeDateTimeFormatEdit edit, GeoCodeDateTimeFormat geoCodeDateTimeFormat) {
        edit.setIsDefault(geoCodeDateTimeFormat.getIsDefault().toString());
        edit.setSortOrder(geoCodeDateTimeFormat.getSortOrder().toString());
    }

    @Override
    public void doUpdate(GeoCodeDateTimeFormat geoCodeDateTimeFormat) {
        var geoCodeDateTimeFormatValue = geoControl.getGeoCodeDateTimeFormatValue(geoCodeDateTimeFormat);

        geoCodeDateTimeFormatValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        geoCodeDateTimeFormatValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        geoControl.updateGeoCodeDateTimeFormatFromValue(geoCodeDateTimeFormatValue, getPartyPK());
    }

}
