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
import com.echothree.control.user.core.common.edit.EntityGeoPointAttributeEdit;
import com.echothree.control.user.core.common.form.EditEntityGeoPointAttributeForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.spec.EntityGeoPointAttributeSpec;
import com.echothree.model.control.core.server.logic.EntityAttributeLogic;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.core.server.entity.EntityGeoPointAttribute;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.string.GeoPointUtils;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.PersistenceUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditEntityGeoPointAttributeCommand
        extends BaseEditCommand<EntityGeoPointAttributeSpec, EntityGeoPointAttributeEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), null)
        ));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("EntityAttributeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityAttributeUuid", FieldType.UUID, false, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Latitude", FieldType.LATITUDE, true, null, null),
                new FieldDefinition("Longitude", FieldType.LONGITUDE, true, null, null),
                new FieldDefinition("Elevation", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("ElevationUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("Altitude", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("AltitudeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null)

                ));
    }
    
    /** Creates a new instance of EditEntityGeoPointAttributeCommand */
    public EditEntityGeoPointAttributeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getEditEntityGeoPointAttributeResult();
        var parameterCount = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(spec);

        if(parameterCount == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, spec);

            if(!hasExecutionErrors()) {
                var entityAttributeName = spec.getEntityAttributeName();
                var entityAttributeUuid = spec.getEntityAttributeUuid();

                parameterCount = (entityAttributeName == null ? 0 : 1) + (entityAttributeUuid == null ? 0 : 1);

                if(parameterCount == 1) {
                    var entityAttribute = entityAttributeName == null ?
                            EntityAttributeLogic.getInstance().getEntityAttributeByUuid(this, entityAttributeUuid) :
                            EntityAttributeLogic.getInstance().getEntityAttributeByName(this, entityInstance.getEntityType(), entityAttributeName);

                    if(!hasExecutionErrors()) {
                        if(entityInstance.getEntityType().equals(entityAttribute.getLastDetail().getEntityType())) {
                            EntityGeoPointAttribute entityGeoPointAttribute = null;
                            var basePK = PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance);

                            if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                                entityGeoPointAttribute = coreControl.getEntityGeoPointAttribute(entityAttribute, entityInstance);

                                if(entityGeoPointAttribute != null) {
                                    if(editMode.equals(EditMode.LOCK)) {
                                        result.setEntityGeoPointAttribute(coreControl.getEntityGeoPointAttributeTransfer(getUserVisit(), entityGeoPointAttribute, entityInstance));

                                        if(lockEntity(basePK)) {
                                            var unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();
                                            var geoPointUtils = GeoPointUtils.getInstance();
                                            var edit = CoreEditFactory.getEntityGeoPointAttributeEdit();
                                            UnitOfMeasureTypeLogic.StringUnitOfMeasure stringUnitOfMeasure;

                                            result.setEdit(edit);
                                            edit.setLatitude(geoPointUtils.formatDegrees(entityGeoPointAttribute.getLatitude()));
                                            edit.setLongitude(geoPointUtils.formatDegrees(entityGeoPointAttribute.getLongitude()));
                                            stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_ELEVATION, entityGeoPointAttribute.getElevation());
                                            edit.setElevationUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
                                            edit.setElevation(stringUnitOfMeasure.getValue());
                                            stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_ALTITUDE, entityGeoPointAttribute.getAltitude());
                                            edit.setAltitudeUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
                                            edit.setAltitude(stringUnitOfMeasure.getValue());
                                        } else {
                                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                                        }
                                    } else { // EditMode.ABANDON
                                        unlockEntity(basePK);
                                        basePK = null;
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownEntityGeoPointAttribute.name(),
                                            EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                                            entityAttribute.getLastDetail().getEntityAttributeName());
                                }
                            } else if(editMode.equals(EditMode.UPDATE)) {
                                entityGeoPointAttribute = coreControl.getEntityGeoPointAttributeForUpdate(entityAttribute, entityInstance);

                                if(entityGeoPointAttribute != null) {
                                    var unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();

                                    var elevation = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_ELEVATION,
                                            edit.getElevation(), edit.getElevationUnitOfMeasureTypeName(),
                                            null, ExecutionErrors.MissingRequiredElevation.name(), null, ExecutionErrors.MissingRequiredElevationUnitOfMeasureTypeName.name(),
                                            null, ExecutionErrors.UnknownElevationUnitOfMeasureTypeName.name());

                                    if(!hasExecutionErrors()) {
                                        var altitude = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_ALTITUDE,
                                                edit.getAltitude(), edit.getAltitudeUnitOfMeasureTypeName(),
                                                null, ExecutionErrors.MissingRequiredAltitude.name(), null, ExecutionErrors.MissingRequiredAltitudeUnitOfMeasureTypeName.name(),
                                                null, ExecutionErrors.UnknownAltitudeUnitOfMeasureTypeName.name());

                                        if(!hasExecutionErrors()) {
                                            if(lockEntityForUpdate(basePK)) {
                                                try {
                                                    var entityGeoPointAttributeValue = coreControl.getEntityGeoPointAttributeValueForUpdate(entityGeoPointAttribute);

                                                    entityGeoPointAttributeValue.setLatitude(Integer.valueOf(edit.getLatitude()));
                                                    entityGeoPointAttributeValue.setLongitude(Integer.valueOf(edit.getLongitude()));
                                                    entityGeoPointAttributeValue.setElevation(elevation);
                                                    entityGeoPointAttributeValue.setAltitude(altitude);

                                                    coreControl.updateEntityGeoPointAttributeFromValue(entityGeoPointAttributeValue, getPartyPK());
                                                } finally {
                                                    unlockEntity(basePK);
                                                    basePK = null;
                                                }
                                            } else {
                                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                                            }
                                        }
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownEntityGeoPointAttribute.name(),
                                            EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                                            entityAttribute.getLastDetail().getEntityAttributeName());
                                }
                            }

                            if(basePK != null) {
                                result.setEntityLock(getEntityLockTransfer(basePK));
                            }

                            if(entityGeoPointAttribute != null) {
                                result.setEntityGeoPointAttribute(coreControl.getEntityGeoPointAttributeTransfer(getUserVisit(), entityGeoPointAttribute, entityInstance));
                            }
                        } else {
                            addExecutionError(ExecutionErrors.MismatchedEntityType.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.InvalidParameterCount.name());
                }
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }

        return result;
    }
    
}
