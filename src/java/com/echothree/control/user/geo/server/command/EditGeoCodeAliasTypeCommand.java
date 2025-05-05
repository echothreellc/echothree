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

import com.echothree.control.user.geo.common.edit.GeoCodeAliasTypeEdit;
import com.echothree.control.user.geo.common.edit.GeoEditFactory;
import com.echothree.control.user.geo.common.form.EditGeoCodeAliasTypeForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeAliasTypeResult;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.spec.GeoCodeAliasTypeSpec;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
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

public class EditGeoCodeAliasTypeCommand
        extends BaseAbstractEditCommand<GeoCodeAliasTypeSpec, GeoCodeAliasTypeEdit, EditGeoCodeAliasTypeResult, GeoCodeAliasType, GeoCodeAliasType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeAliasType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("GeoCodeAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeAliasTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ValidationPattern", FieldType.REGULAR_EXPRESSION, false, null, null),
                new FieldDefinition("IsRequired", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditGeoCodeAliasTypeCommand */
    public EditGeoCodeAliasTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGeoCodeAliasTypeResult getResult() {
        return GeoResultFactory.getEditGeoCodeAliasTypeResult();
    }

    @Override
    public GeoCodeAliasTypeEdit getEdit() {
        return GeoEditFactory.getGeoCodeAliasTypeEdit();
    }

    GeoCodeType geoCodeType;

    @Override
    public GeoCodeAliasType getEntity(EditGeoCodeAliasTypeResult result) {
        var geoControl = Session.getModelController(GeoControl.class);
        GeoCodeAliasType geoAliasType = null;
        var geoCodeTypeName = spec.getGeoCodeTypeName();

        geoCodeType = geoControl.getGeoCodeTypeByName(geoCodeTypeName);

        if(geoCodeType != null) {
            var geoAliasTypeName = spec.getGeoCodeAliasTypeName();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                geoAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, geoAliasTypeName);
            } else { // EditMode.UPDATE
                geoAliasType = geoControl.getGeoCodeAliasTypeByNameForUpdate(geoCodeType, geoAliasTypeName);
            }

            if(geoAliasType != null) {
                result.setGeoCodeAliasType(geoControl.getGeoCodeAliasTypeTransfer(getUserVisit(), geoAliasType));
            } else {
                addExecutionError(ExecutionErrors.UnknownGeoCodeAliasTypeName.name(), geoCodeTypeName, geoAliasTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeTypeName.name(), geoCodeTypeName);
        }

        return geoAliasType;
    }

    @Override
    public GeoCodeAliasType getLockEntity(GeoCodeAliasType geoAliasType) {
        return geoAliasType;
    }

    @Override
    public void fillInResult(EditGeoCodeAliasTypeResult result, GeoCodeAliasType geoAliasType) {
        var geoControl = Session.getModelController(GeoControl.class);

        result.setGeoCodeAliasType(geoControl.getGeoCodeAliasTypeTransfer(getUserVisit(), geoAliasType));
    }

    @Override
    public void doLock(GeoCodeAliasTypeEdit edit, GeoCodeAliasType geoAliasType) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoAliasTypeDescription = geoControl.getGeoCodeAliasTypeDescription(geoAliasType, getPreferredLanguage());
        var geoAliasTypeDetail = geoAliasType.getLastDetail();

        edit.setGeoCodeAliasTypeName(geoAliasTypeDetail.getGeoCodeAliasTypeName());
        edit.setValidationPattern(geoAliasTypeDetail.getValidationPattern());
        edit.setIsRequired(geoAliasTypeDetail.getIsRequired().toString());
        edit.setIsDefault(geoAliasTypeDetail.getIsDefault().toString());
        edit.setSortOrder(geoAliasTypeDetail.getSortOrder().toString());

        if(geoAliasTypeDescription != null) {
            edit.setDescription(geoAliasTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(GeoCodeAliasType geoAliasType) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoAliasTypeName = edit.getGeoCodeAliasTypeName();
        var duplicateGeoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, geoAliasTypeName);

        if(duplicateGeoCodeAliasType != null && !geoAliasType.equals(duplicateGeoCodeAliasType)) {
            addExecutionError(ExecutionErrors.DuplicateGeoCodeAliasTypeName.name(), spec.getGeoCodeTypeName(), geoAliasTypeName);
        }
    }

    @Override
    public void doUpdate(GeoCodeAliasType geoAliasType) {
        var geoControl = Session.getModelController(GeoControl.class);
        var partyPK = getPartyPK();
        var geoAliasTypeDetailValue = geoControl.getGeoCodeAliasTypeDetailValueForUpdate(geoAliasType);
        var geoAliasTypeDescription = geoControl.getGeoCodeAliasTypeDescriptionForUpdate(geoAliasType, getPreferredLanguage());
        var description = edit.getDescription();

        geoAliasTypeDetailValue.setGeoCodeAliasTypeName(edit.getGeoCodeAliasTypeName());
        geoAliasTypeDetailValue.setValidationPattern(edit.getValidationPattern());
        geoAliasTypeDetailValue.setIsRequired(Boolean.valueOf(edit.getIsRequired()));
        geoAliasTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        geoAliasTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        geoControl.updateGeoCodeAliasTypeFromValue(geoAliasTypeDetailValue, partyPK);

        if(geoAliasTypeDescription == null && description != null) {
            geoControl.createGeoCodeAliasTypeDescription(geoAliasType, getPreferredLanguage(), description, partyPK);
        } else if(geoAliasTypeDescription != null && description == null) {
            geoControl.deleteGeoCodeAliasTypeDescription(geoAliasTypeDescription, partyPK);
        } else if(geoAliasTypeDescription != null && description != null) {
            var geoAliasTypeDescriptionValue = geoControl.getGeoCodeAliasTypeDescriptionValue(geoAliasTypeDescription);

            geoAliasTypeDescriptionValue.setDescription(description);
            geoControl.updateGeoCodeAliasTypeDescriptionFromValue(geoAliasTypeDescriptionValue, partyPK);
        }
    }

}
