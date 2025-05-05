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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.EntityGeoPointDefaultEdit;
import com.echothree.control.user.core.common.form.EditEntityGeoPointDefaultForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityGeoPointDefaultResult;
import com.echothree.control.user.core.common.spec.EntityGeoPointDefaultSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityGeoPointDefault;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.string.GeoPointUtils;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import java.util.List;

public class EditEntityGeoPointDefaultCommand
        extends BaseAbstractEditCommand<EntityGeoPointDefaultSpec, EntityGeoPointDefaultEdit, EditEntityGeoPointDefaultResult, EntityGeoPointDefault, EntityAttribute> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Latitude", FieldType.LATITUDE, true, null, null),
                new FieldDefinition("Longitude", FieldType.LONGITUDE, true, null, null),
                new FieldDefinition("Elevation", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ElevationUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Altitude", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("AltitudeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null)
        );
    }

    /** Creates a new instance of EditEntityGeoPointDefaultCommand */
    public EditEntityGeoPointDefaultCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEntityGeoPointDefaultResult getResult() {
        return CoreResultFactory.getEditEntityGeoPointDefaultResult();
    }

    @Override
    public EntityGeoPointDefaultEdit getEdit() {
        return CoreEditFactory.getEntityGeoPointDefaultEdit();
    }

    @Override
    public EntityGeoPointDefault getEntity(EditEntityGeoPointDefaultResult result) {
        var entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByUniversalSpec(this, spec);
        EntityGeoPointDefault entityGeoPointDefault = null;

        if(!hasExecutionErrors()) {
            var coreControl = getCoreControl();

            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                entityGeoPointDefault = coreControl.getEntityGeoPointDefault(entityAttribute);
            } else { // EditMode.UPDATE
                entityGeoPointDefault = coreControl.getEntityGeoPointDefaultForUpdate(entityAttribute);
            }

            if(entityGeoPointDefault == null) {
                addExecutionError(ExecutionErrors.UnknownEntityGeoPointDefault.name());
            }
        }

        return entityGeoPointDefault;
    }

    @Override
    public EntityAttribute getLockEntity(EntityGeoPointDefault entityGeoPointDefault) {
        return entityGeoPointDefault.getEntityAttribute();
    }

    @Override
    public void fillInResult(EditEntityGeoPointDefaultResult result, EntityGeoPointDefault entityGeoPointDefault) {
        var coreControl = getCoreControl();

        result.setEntityGeoPointDefault(coreControl.getEntityGeoPointDefaultTransfer(getUserVisit(), entityGeoPointDefault));
    }

    @Override
    public void doLock(EntityGeoPointDefaultEdit edit, EntityGeoPointDefault entityGeoPointDefault) {
        var unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();
        var geoPointUtils = GeoPointUtils.getInstance();
        UnitOfMeasureTypeLogic.StringUnitOfMeasure stringUnitOfMeasure;

        edit.setLatitude(geoPointUtils.formatDegrees(entityGeoPointDefault.getLatitude()));
        edit.setLongitude(geoPointUtils.formatDegrees(entityGeoPointDefault.getLongitude()));
        stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_ELEVATION, entityGeoPointDefault.getElevation());
        edit.setElevationUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
        edit.setElevation(stringUnitOfMeasure.getValue());
        stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_ALTITUDE, entityGeoPointDefault.getAltitude());
        edit.setAltitudeUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
        edit.setAltitude(stringUnitOfMeasure.getValue());
    }

    Long elevation;
    Long altitude;

    @Override
    public void canUpdate(EntityGeoPointDefault entityGeoPointDefault) {
        var unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();

        elevation = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_ELEVATION,
                edit.getElevation(), edit.getElevationUnitOfMeasureTypeName(),
                null, ExecutionErrors.MissingRequiredElevation.name(), null, ExecutionErrors.MissingRequiredElevationUnitOfMeasureTypeName.name(),
                null, ExecutionErrors.UnknownElevationUnitOfMeasureTypeName.name());

        altitude = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_ALTITUDE,
                edit.getAltitude(), edit.getAltitudeUnitOfMeasureTypeName(),
                null, ExecutionErrors.MissingRequiredAltitude.name(), null, ExecutionErrors.MissingRequiredAltitudeUnitOfMeasureTypeName.name(),
                null, ExecutionErrors.UnknownAltitudeUnitOfMeasureTypeName.name());
    }

    @Override
    public void doUpdate(EntityGeoPointDefault entityGeoPointDefault) {
        var coreControl = getCoreControl();
        var entityGeoPointDefaultValue = coreControl.getEntityGeoPointDefaultValueForUpdate(entityGeoPointDefault);

        entityGeoPointDefaultValue.setLatitude(Integer.valueOf(edit.getLatitude()));
        entityGeoPointDefaultValue.setLongitude(Integer.valueOf(edit.getLongitude()));
        entityGeoPointDefaultValue.setElevation(elevation);
        entityGeoPointDefaultValue.setAltitude(altitude);

        coreControl.updateEntityGeoPointDefaultFromValue(entityGeoPointDefaultValue, getPartyPK());
    }
    
}
