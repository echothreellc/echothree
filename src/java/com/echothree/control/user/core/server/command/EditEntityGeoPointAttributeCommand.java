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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.edit.CoreEditFactory;
import com.echothree.control.user.core.common.edit.EntityGeoPointAttributeEdit;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.EditEntityGeoPointAttributeResult;
import com.echothree.control.user.core.common.spec.EntityGeoPointAttributeSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityDataLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityGeoPointAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.string.GeoPointUtils;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.BaseEntity;
import com.echothree.util.server.persistence.PersistenceUtils;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditEntityGeoPointAttributeCommand
        extends BaseAbstractEditCommand<EntityGeoPointAttributeSpec, EntityGeoPointAttributeEdit, EditEntityGeoPointAttributeResult, EntityGeoPointAttribute, EntityInstance> {

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
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeUuid", FieldType.UUID, false, null, null)
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

    @Inject
    EntityAttributeLogic entityAttributeLogic;

    @Inject
    EntityDataLogic entityDataLogic;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    UnitOfMeasureTypeLogic unitOfMeasureTypeLogic;

    private EntityInstance entityInstance;
    private EntityAttribute entityAttribute;
    private Long elevation;
    private Long altitude;

    /** Creates a new instance of EditEntityGeoPointAttributeCommand */
    public EditEntityGeoPointAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEntityGeoPointAttributeResult getResult() {
        return CoreResultFactory.getEditEntityGeoPointAttributeResult();
    }

    @Override
    public EntityGeoPointAttributeEdit getEdit() {
        return CoreEditFactory.getEntityGeoPointAttributeEdit();
    }

    @Override
    public EntityGeoPointAttribute getEntity(EditEntityGeoPointAttributeResult result) {
        EntityGeoPointAttribute entity = null;

        entityInstance = entityInstanceLogic.getEntityInstance(this, spec);

        if(!hasExecutionErrors()) {
            entityAttribute = entityAttributeLogic.getEntityAttribute(this, entityInstance, spec, spec);
        }

        if(!hasExecutionErrors()) {
            entity = entityDataLogic.getEntityGeoPointAttribute(this, entityAttribute, entityInstance, editModeToEntityPermission(editMode));
        }

        return entity;
    }

    @Override
    public EntityInstance getLockEntity(EntityGeoPointAttribute entityGeoPointAttribute) {
        return entityInstance;
    }

    @Override
    protected BasePK getLockTargetPK(BaseEntity lockTarget) {
        return PersistenceUtils.getInstance().getBasePKFromEntityInstance((EntityInstance)lockTarget);
    }

    @Override
    public void fillInResult(EditEntityGeoPointAttributeResult result, EntityGeoPointAttribute entityGeoPointAttribute) {
        result.setEntityGeoPointAttribute(coreControl.getEntityGeoPointAttributeTransfer(getUserVisit(), entityGeoPointAttribute, entityInstance));
    }

    @Override
    public void doLock(EntityGeoPointAttributeEdit edit, EntityGeoPointAttribute entityGeoPointAttribute) {
        var geoPointUtils = GeoPointUtils.getInstance();
        UnitOfMeasureTypeLogic.StringUnitOfMeasure stringUnitOfMeasure;

        edit.setLatitude(geoPointUtils.formatDegrees(entityGeoPointAttribute.getLatitude()));
        edit.setLongitude(geoPointUtils.formatDegrees(entityGeoPointAttribute.getLongitude()));
        stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_ELEVATION, entityGeoPointAttribute.getElevation());
        edit.setElevationUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
        edit.setElevation(stringUnitOfMeasure.getValue());
        stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_ALTITUDE, entityGeoPointAttribute.getAltitude());
        edit.setAltitudeUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
        edit.setAltitude(stringUnitOfMeasure.getValue());
    }

    @Override
    protected void canUpdate(EntityGeoPointAttribute entityGeoPointAttribute) {
        elevation = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_ELEVATION,
                edit.getElevation(), edit.getElevationUnitOfMeasureTypeName(),
                null, ExecutionErrors.MissingRequiredElevation.name(), null, ExecutionErrors.MissingRequiredElevationUnitOfMeasureTypeName.name(),
                null, ExecutionErrors.UnknownElevationUnitOfMeasureTypeName.name());

        if(!hasExecutionErrors()) {
            altitude = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_ALTITUDE,
                    edit.getAltitude(), edit.getAltitudeUnitOfMeasureTypeName(),
                    null, ExecutionErrors.MissingRequiredAltitude.name(), null, ExecutionErrors.MissingRequiredAltitudeUnitOfMeasureTypeName.name(),
                    null, ExecutionErrors.UnknownAltitudeUnitOfMeasureTypeName.name());
        }
    }

    @Override
    public void doUpdate(EntityGeoPointAttribute entityGeoPointAttribute) {
        var entityGeoPointAttributeValue = coreControl.getEntityGeoPointAttributeValueForUpdate(entityGeoPointAttribute);

        entityGeoPointAttributeValue.setLatitude(Integer.valueOf(edit.getLatitude()));
        entityGeoPointAttributeValue.setLongitude(Integer.valueOf(edit.getLongitude()));
        entityGeoPointAttributeValue.setElevation(elevation);
        entityGeoPointAttributeValue.setAltitude(altitude);

        coreControl.updateEntityGeoPointAttributeFromValue(entityGeoPointAttributeValue, getPartyPK());
    }

}
