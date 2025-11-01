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

package com.echothree.model.control.geo.server.logic;

import com.echothree.control.user.geo.common.spec.GeoCodeTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.geo.common.exception.DuplicateGeoCodeTypeNameException;
import com.echothree.model.control.geo.common.exception.UnknownDefaultGeoCodeTypeException;
import com.echothree.model.control.geo.common.exception.UnknownGeoCodeTypeNameException;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.model.data.geo.server.value.GeoCodeTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class GeoCodeTypeLogic
        extends BaseLogic {

    protected GeoCodeTypeLogic() {
        super();
    }

    public static GeoCodeTypeLogic getInstance() {
        return CDI.current().select(GeoCodeTypeLogic.class).get();
    }

    public GeoCodeType createGeoCodeType(final ExecutionErrorAccumulator eea, final String geoCodeTypeName,
            final GeoCodeType parentGeoCodeType, final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeType = geoControl.getGeoCodeTypeByName(geoCodeTypeName);

        if(geoCodeType == null) {
            geoCodeType = geoControl.createGeoCodeType(geoCodeTypeName, parentGeoCodeType, isDefault, sortOrder, createdBy);

            if(description != null) {
                geoControl.createGeoCodeTypeDescription(geoCodeType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateGeoCodeTypeNameException.class, eea, ExecutionErrors.DuplicateGeoCodeTypeName.name(),
                    geoCodeTypeName);
        }

        return geoCodeType;
    }

    public GeoCodeType getGeoCodeTypeByName(final ExecutionErrorAccumulator eea, final String geoCodeTypeName,
            final EntityPermission entityPermission) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeType = geoControl.getGeoCodeTypeByName(geoCodeTypeName, entityPermission);

        if(geoCodeType == null) {
            handleExecutionError(UnknownGeoCodeTypeNameException.class, eea, ExecutionErrors.UnknownGeoCodeTypeName.name(), geoCodeTypeName);
        }

        return geoCodeType;
    }

    public GeoCodeType getGeoCodeTypeByName(final ExecutionErrorAccumulator eea, final String geoCodeTypeName) {
        return getGeoCodeTypeByName(eea, geoCodeTypeName, EntityPermission.READ_ONLY);
    }

    public GeoCodeType getGeoCodeTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String geoCodeTypeName) {
        return getGeoCodeTypeByName(eea, geoCodeTypeName, EntityPermission.READ_WRITE);
    }

    public GeoCodeType getGeoCodeTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final GeoCodeTypeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        GeoCodeType geoCodeType = null;
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeTypeName = universalSpec.getGeoCodeTypeName();
        var parameterCount = (geoCodeTypeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    geoCodeType = geoControl.getDefaultGeoCodeType(entityPermission);

                    if(geoCodeType == null) {
                        handleExecutionError(UnknownDefaultGeoCodeTypeException.class, eea, ExecutionErrors.UnknownDefaultGeoCodeType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(geoCodeTypeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.GeoCodeType.name());

                    if(!eea.hasExecutionErrors()) {
                        geoCodeType = geoControl.getGeoCodeTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    geoCodeType = getGeoCodeTypeByName(eea, geoCodeTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return geoCodeType;
    }

    public GeoCodeType getGeoCodeTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final GeoCodeTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getGeoCodeTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public GeoCodeType getGeoCodeTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final GeoCodeTypeUniversalSpec universalSpec, boolean allowDefault) {
        return getGeoCodeTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateGeoCodeTypeFromValue(GeoCodeTypeDetailValue geoCodeTypeDetailValue, BasePK updatedBy) {
        var geoControl = Session.getModelController(GeoControl.class);

        geoControl.updateGeoCodeTypeFromValue(geoCodeTypeDetailValue, updatedBy);
    }

    private void checkDeleteGeoCodeType(final ExecutionErrorAccumulator eea, final GeoCodeType geoCodeType) {
        var geoControl = Session.getModelController(GeoControl.class);

        if(geoControl.countGeoCodeTypesByParentGeoCodeType(geoCodeType) != 0
                || geoControl.countGeoCodesByGeoCodeType(geoCodeType) != 0) {
            eea.addExecutionError(ExecutionErrors.CannotDeleteGeoCodeTypeInUse.name(),
                    geoCodeType.getLastDetail().getGeoCodeTypeName());
        }
    }

    public void deleteGeoCodeType(final ExecutionErrorAccumulator eea, final GeoCodeType geoCodeType, final BasePK deletedBy) {
        checkDeleteGeoCodeType(eea, geoCodeType);

        if(!eea.hasExecutionErrors()) {
            var geoControl = Session.getModelController(GeoControl.class);

            geoControl.deleteGeoCodeType(geoCodeType, deletedBy);
        }
    }

}
