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

import com.echothree.control.user.geo.common.edit.GeoCodeAliasEdit;
import com.echothree.control.user.geo.common.edit.GeoEditFactory;
import com.echothree.control.user.geo.common.form.EditGeoCodeAliasForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeAliasResult;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.spec.GeoCodeAliasSpec;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
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
import java.util.regex.Pattern;

public class EditGeoCodeAliasCommand
        extends BaseAbstractEditCommand<GeoCodeAliasSpec, GeoCodeAliasEdit, EditGeoCodeAliasResult, GeoCodeAlias, GeoCodeAlias> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeAlias.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GeoCodeAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditGeoCodeAliasCommand */
    public EditGeoCodeAliasCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditGeoCodeAliasResult getResult() {
        return GeoResultFactory.getEditGeoCodeAliasResult();
    }

    @Override
    public GeoCodeAliasEdit getEdit() {
        return GeoEditFactory.getGeoCodeAliasEdit();
    }

    GeoCode geoCode;
    GeoCodeAliasType geoCodeAliasType;
    
    @Override
    public GeoCodeAlias getEntity(EditGeoCodeAliasResult result) {
        var geoControl = Session.getModelController(GeoControl.class);
        GeoCodeAlias geoCodeAlias = null;
        var geoCodeName = spec.getGeoCodeName();

        geoCode = geoControl.getGeoCodeByName(geoCodeName);

        if(geoCode != null) {
            var geoCodeType = geoCode.getLastDetail().getGeoCodeType();
            var geoCodeAliasTypeName = spec.getGeoCodeAliasTypeName();

            geoCodeAliasType = geoControl.getGeoCodeAliasTypeByNameForUpdate(geoCodeType, geoCodeAliasTypeName);

            if(geoCodeAliasType != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    geoCodeAlias = geoControl.getGeoCodeAlias(geoCode, geoCodeAliasType);
                } else { // EditMode.UPDATE
                    geoCodeAlias = geoControl.getGeoCodeAliasForUpdate(geoCode, geoCodeAliasType);
                }

                if(geoCodeAlias != null) {
                    result.setGeoCodeAlias(geoControl.getGeoCodeAliasTransfer(getUserVisit(), geoCodeAlias));
                } else {
                    addExecutionError(ExecutionErrors.UnknownGeoCodeAlias.name(), geoCodeName, geoCodeAliasTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownGeoCodeAliasTypeName.name(), geoCodeType.getLastDetail().getGeoCodeTypeName(), geoCodeAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeName.name(), geoCodeName);
        }

        return geoCodeAlias;
    }

    @Override
    public GeoCodeAlias getLockEntity(GeoCodeAlias geoCodeAlias) {
        return geoCodeAlias;
    }

    @Override
    public void fillInResult(EditGeoCodeAliasResult result, GeoCodeAlias geoCodeAlias) {
        var geoControl = Session.getModelController(GeoControl.class);

        result.setGeoCodeAlias(geoControl.getGeoCodeAliasTransfer(getUserVisit(), geoCodeAlias));
    }

    @Override
    public void doLock(GeoCodeAliasEdit edit, GeoCodeAlias geoCodeAlias) {
        edit.setAlias(geoCodeAlias.getAlias());
    }

    @Override
    public void canUpdate(GeoCodeAlias geoCodeAlias) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeScope = geoCode.getLastDetail().getGeoCodeScope();
        var alias = edit.getAlias();
        var duplicateGeoCodeAlias = geoControl.getGeoCodeAliasByAliasWithinScope(geoCodeScope, geoCodeAliasType, alias);

        if(duplicateGeoCodeAlias != null && !geoCodeAlias.equals(duplicateGeoCodeAlias)) {
            addExecutionError(ExecutionErrors.DuplicateGeoCodeAlias.name(), geoCode.getLastDetail().getGeoCodeName(),
                    geoCodeScope.getLastDetail().getGeoCodeScopeName(), geoCodeAliasType.getLastDetail().getGeoCodeAliasTypeName(), alias);
        } else {
            var validationPattern = geoCodeAliasType.getLastDetail().getValidationPattern();

            if(validationPattern != null) {
                var pattern = Pattern.compile(validationPattern);
                var m = pattern.matcher(alias);

                if(!m.matches()) {
                    addExecutionError(ExecutionErrors.InvalidAlias.name(), alias);
                }
            }
        }
    }

    @Override
    public void doUpdate(GeoCodeAlias geoCodeAlias) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeAliasValue = geoControl.getGeoCodeAliasValue(geoCodeAlias);

        geoCodeAliasValue.setAlias(edit.getAlias());

        geoControl.updateGeoCodeAliasFromValue(geoCodeAliasValue, getPartyPK());
    }

}
