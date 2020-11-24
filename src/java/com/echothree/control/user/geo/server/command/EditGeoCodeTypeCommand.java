// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.geo.common.edit.GeoCodeTypeEdit;
import com.echothree.control.user.geo.common.edit.GeoEditFactory;
import com.echothree.control.user.geo.common.form.EditGeoCodeTypeForm;
import com.echothree.control.user.geo.common.result.EditGeoCodeTypeResult;
import com.echothree.control.user.geo.common.result.GeoResultFactory;
import com.echothree.control.user.geo.common.spec.GeoCodeTypeSpec;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.geo.server.entity.GeoCodeTypeDescription;
import com.echothree.model.data.geo.server.entity.GeoCodeTypeDetail;
import com.echothree.model.data.geo.server.value.GeoCodeTypeDescriptionValue;
import com.echothree.model.data.geo.server.value.GeoCodeTypeDetailValue;
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

public class EditGeoCodeTypeCommand
        extends BaseAbstractEditCommand<GeoCodeTypeSpec, GeoCodeTypeEdit, EditGeoCodeTypeResult, GeoCodeType, GeoCodeType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.GeoCodeType.name(), SecurityRoles.Edit.name())
                    )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("GeoCodeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentGeoCodeTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditGeoCodeTypeCommand */
    public EditGeoCodeTypeCommand(UserVisitPK userVisitPK, EditGeoCodeTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditGeoCodeTypeResult getResult() {
        return GeoResultFactory.getEditGeoCodeTypeResult();
    }

    @Override
    public GeoCodeTypeEdit getEdit() {
        return GeoEditFactory.getGeoCodeTypeEdit();
    }

    @Override
    public GeoCodeType getEntity(EditGeoCodeTypeResult result) {
        var geoControl = Session.getModelController(GeoControl.class);
        GeoCodeType geoCodeType = null;
        String geoCodeTypeName = spec.getGeoCodeTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            geoCodeType = geoControl.getGeoCodeTypeByName(geoCodeTypeName);
        } else { // EditMode.UPDATE
            geoCodeType = geoControl.getGeoCodeTypeByNameForUpdate(geoCodeTypeName);
        }

        if(geoCodeType != null) {
            result.setGeoCodeType(geoControl.getGeoCodeTypeTransfer(getUserVisit(), geoCodeType));
        } else {
            addExecutionError(ExecutionErrors.UnknownGeoCodeTypeName.name(), geoCodeTypeName);
        }

        return geoCodeType;
    }

    @Override
    public GeoCodeType getLockEntity(GeoCodeType geoCodeType) {
        return geoCodeType;
    }

    @Override
    public void fillInResult(EditGeoCodeTypeResult result, GeoCodeType geoCodeType) {
        var geoControl = Session.getModelController(GeoControl.class);

        result.setGeoCodeType(geoControl.getGeoCodeTypeTransfer(getUserVisit(), geoCodeType));
    }

    GeoCodeType parentGeoCodeType = null;

    @Override
    public void doLock(GeoCodeTypeEdit edit, GeoCodeType geoCodeType) {
        var geoControl = Session.getModelController(GeoControl.class);
        GeoCodeTypeDescription geoCodeTypeDescription = geoControl.getGeoCodeTypeDescription(geoCodeType, getPreferredLanguage());
        GeoCodeTypeDetail geoCodeTypeDetail = geoCodeType.getLastDetail();

        parentGeoCodeType = geoCodeTypeDetail.getParentGeoCodeType();

        edit.setGeoCodeTypeName(geoCodeTypeDetail.getGeoCodeTypeName());
        edit.setParentGeoCodeTypeName(parentGeoCodeType == null ? null : parentGeoCodeType.getLastDetail().getGeoCodeTypeName());
        edit.setIsDefault(geoCodeTypeDetail.getIsDefault().toString());
        edit.setSortOrder(geoCodeTypeDetail.getSortOrder().toString());

        if(geoCodeTypeDescription != null) {
            edit.setDescription(geoCodeTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(GeoCodeType geoCodeType) {
        var geoControl = Session.getModelController(GeoControl.class);
        String geoCodeTypeName = edit.getGeoCodeTypeName();
        GeoCodeType duplicateGeoCodeType = geoControl.getGeoCodeTypeByName(geoCodeTypeName);

        if(duplicateGeoCodeType == null || geoCodeType.equals(duplicateGeoCodeType)) {
            String parentGeoCodeTypeName = edit.getParentGeoCodeTypeName();

            if(parentGeoCodeTypeName != null) {
                parentGeoCodeType = geoControl.getGeoCodeTypeByName(parentGeoCodeTypeName);
            }

            if(parentGeoCodeTypeName == null || parentGeoCodeType != null) {
                if(!geoControl.isParentGeoCodeTypeSafe(geoCodeType, parentGeoCodeType)) {
                    addExecutionError(ExecutionErrors.InvalidParentGeoCodeType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentGeoCodeTypeName.name(), parentGeoCodeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateGeoCodeTypeName.name(), geoCodeTypeName);
        }
    }

    @Override
    public void doUpdate(GeoCodeType geoCodeType) {
        var geoControl = Session.getModelController(GeoControl.class);
        var partyPK = getPartyPK();
        GeoCodeTypeDetailValue geoCodeTypeDetailValue = geoControl.getGeoCodeTypeDetailValueForUpdate(geoCodeType);
        GeoCodeTypeDescription geoCodeTypeDescription = geoControl.getGeoCodeTypeDescriptionForUpdate(geoCodeType, getPreferredLanguage());
        String description = edit.getDescription();

        geoCodeTypeDetailValue.setGeoCodeTypeName(edit.getGeoCodeTypeName());
        geoCodeTypeDetailValue.setParentGeoCodeTypePK(parentGeoCodeType == null ? null : parentGeoCodeType.getPrimaryKey());
        geoCodeTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        geoCodeTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        geoControl.updateGeoCodeTypeFromValue(geoCodeTypeDetailValue, partyPK);

        if(geoCodeTypeDescription == null && description != null) {
            geoControl.createGeoCodeTypeDescription(geoCodeType, getPreferredLanguage(), description, partyPK);
        } else {
            if(geoCodeTypeDescription != null && description == null) {
                geoControl.deleteGeoCodeTypeDescription(geoCodeTypeDescription, partyPK);
            } else {
                if(geoCodeTypeDescription != null && description != null) {
                    GeoCodeTypeDescriptionValue geoCodeTypeDescriptionValue = geoControl.getGeoCodeTypeDescriptionValue(geoCodeTypeDescription);

                    geoCodeTypeDescriptionValue.setDescription(description);
                    geoControl.updateGeoCodeTypeDescriptionFromValue(geoCodeTypeDescriptionValue, partyPK);
                }
            }
        }
    }

}
