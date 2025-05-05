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

import com.echothree.control.user.geo.common.edit.GeoCodeScopeEdit;
import com.echothree.control.user.geo.common.edit.GeoEditFactory;
import com.echothree.control.user.geo.common.form.EditGeoCodeScopeForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeScopeResult;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.spec.GeoCodeScopeSpec;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
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

public class EditGeoCodeScopeCommand
        extends BaseAbstractEditCommand<GeoCodeScopeSpec, GeoCodeScopeEdit, EditGeoCodeScopeResult, GeoCodeScope, GeoCodeScope> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeScope.name(), SecurityRoles.Edit.name())
                    )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeScopeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeScopeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditGeoCodeScopeCommand */
    public EditGeoCodeScopeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGeoCodeScopeResult getResult() {
        return GeoResultFactory.getEditGeoCodeScopeResult();
    }

    @Override
    public GeoCodeScopeEdit getEdit() {
        return GeoEditFactory.getGeoCodeScopeEdit();
    }

    @Override
    public GeoCodeScope getEntity(EditGeoCodeScopeResult result) {
        var geoControl = Session.getModelController(GeoControl.class);
        GeoCodeScope geoCodeScope;
        var geoCodeScopeName = spec.getGeoCodeScopeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            geoCodeScope = geoControl.getGeoCodeScopeByName(geoCodeScopeName);
        } else { // EditMode.UPDATE
            geoCodeScope = geoControl.getGeoCodeScopeByNameForUpdate(geoCodeScopeName);
        }

        if(geoCodeScope != null) {
            result.setGeoCodeScope(geoControl.getGeoCodeScopeTransfer(getUserVisit(), geoCodeScope));
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeScopeName.name(), geoCodeScopeName);
        }

        return geoCodeScope;
    }

    @Override
    public GeoCodeScope getLockEntity(GeoCodeScope geoCodeScope) {
        return geoCodeScope;
    }

    @Override
    public void fillInResult(EditGeoCodeScopeResult result, GeoCodeScope geoCodeScope) {
        var geoControl = Session.getModelController(GeoControl.class);

        result.setGeoCodeScope(geoControl.getGeoCodeScopeTransfer(getUserVisit(), geoCodeScope));
    }

    @Override
    public void doLock(GeoCodeScopeEdit edit, GeoCodeScope geoCodeScope) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeScopeDescription = geoControl.getGeoCodeScopeDescription(geoCodeScope, getPreferredLanguage());
        var geoCodeScopeDetail = geoCodeScope.getLastDetail();

        edit.setGeoCodeScopeName(geoCodeScopeDetail.getGeoCodeScopeName());
        edit.setIsDefault(geoCodeScopeDetail.getIsDefault().toString());
        edit.setSortOrder(geoCodeScopeDetail.getSortOrder().toString());

        if(geoCodeScopeDescription != null) {
            edit.setDescription(geoCodeScopeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(GeoCodeScope geoCodeScope) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeScopeName = edit.getGeoCodeScopeName();
        var duplicateGeoCodeScope = geoControl.getGeoCodeScopeByName(geoCodeScopeName);

        if(duplicateGeoCodeScope != null && geoCodeScope.equals(duplicateGeoCodeScope)) {
            addExecutionError(ExecutionErrors.DuplicateGeoCodeScopeName.name(), geoCodeScopeName);
        }
    }

    @Override
    public void doUpdate(GeoCodeScope geoCodeScope) {
        var geoControl = Session.getModelController(GeoControl.class);
        var partyPK = getPartyPK();
        var geoCodeScopeDetailValue = geoControl.getGeoCodeScopeDetailValueForUpdate(geoCodeScope);
        var geoCodeScopeDescription = geoControl.getGeoCodeScopeDescriptionForUpdate(geoCodeScope, getPreferredLanguage());
        var description = edit.getDescription();

        geoCodeScopeDetailValue.setGeoCodeScopeName(edit.getGeoCodeScopeName());
        geoCodeScopeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        geoCodeScopeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        geoControl.updateGeoCodeScopeFromValue(geoCodeScopeDetailValue, partyPK);

        if(geoCodeScopeDescription == null && description != null) {
            geoControl.createGeoCodeScopeDescription(geoCodeScope, getPreferredLanguage(), description, partyPK);
        } else {
            if(geoCodeScopeDescription != null && description == null) {
                geoControl.deleteGeoCodeScopeDescription(geoCodeScopeDescription, partyPK);
            } else {
                if(geoCodeScopeDescription != null && description != null) {
                    var geoCodeScopeDescriptionValue = geoControl.getGeoCodeScopeDescriptionValue(geoCodeScopeDescription);

                    geoCodeScopeDescriptionValue.setDescription(description);
                    geoControl.updateGeoCodeScopeDescriptionFromValue(geoCodeScopeDescriptionValue, partyPK);
                }
            }
        }
    }

}
