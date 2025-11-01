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

package com.echothree.model.control.warehouse.server.logic;

import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.control.user.warehouse.common.spec.LocationUseTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.warehouse.common.exception.DuplicateLocationUseTypeNameException;
import com.echothree.model.control.warehouse.common.exception.UnknownDefaultLocationUseTypeException;
import com.echothree.model.control.warehouse.common.exception.UnknownLocationUseTypeNameException;
import com.echothree.model.control.warehouse.server.control.LocationUseTypeControl;
import com.echothree.model.data.warehouse.server.entity.LocationUseType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class LocationUseTypeLogic
        extends BaseLogic {

    protected LocationUseTypeLogic() {
        super();
    }

    public static LocationUseTypeLogic getInstance() {
        return CDI.current().select(LocationUseTypeLogic.class).get();
    }

    public LocationUseType createLocationUseType(final ExecutionErrorAccumulator eea, final String locationUseTypeName,
            final Boolean allowMultiple,final Boolean isDefault, final Integer sortOrder, final BasePK createdBy) {
        var locationUseTypeControl = Session.getModelController(LocationUseTypeControl.class);
        var locationUseType = locationUseTypeControl.getLocationUseTypeByName(locationUseTypeName);

        if(locationUseType == null) {
            locationUseType = locationUseTypeControl.createLocationUseType(locationUseTypeName, allowMultiple, isDefault, sortOrder, createdBy);
        } else {
            handleExecutionError(DuplicateLocationUseTypeNameException.class, eea, ExecutionErrors.DuplicateLocationUseTypeName.name(), locationUseTypeName);
        }

        return locationUseType;
    }

    public LocationUseType getLocationUseTypeByName(final ExecutionErrorAccumulator eea, final String locationUseTypeName,
            final UniversalEntitySpec universalEntitySpec, final boolean allowDefault, final EntityPermission entityPermission) {
        LocationUseType locationUseType = null;
        var locationUseTypeControl = Session.getModelController(LocationUseTypeControl.class);
        var parameterCount = (locationUseTypeName == null ? 0 : 1) +
                EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalEntitySpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    locationUseType = locationUseTypeControl.getDefaultLocationUseType(entityPermission);

                    if(locationUseType == null) {
                        handleExecutionError(UnknownDefaultLocationUseTypeException.class, eea, ExecutionErrors.UnknownDefaultLocationUseType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                var partyControl = Session.getModelController(PartyControl.class);

                if(locationUseTypeName != null) {
                    locationUseType = locationUseTypeControl.getLocationUseTypeByName(locationUseTypeName, entityPermission);

                    if(locationUseType == null) {
                        handleExecutionError(UnknownLocationUseTypeNameException.class, eea, ExecutionErrors.UnknownLocationUseTypeName.name(), locationUseTypeName);
                    }
                } else if(universalEntitySpec != null){
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalEntitySpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Party.name());

                    if(!eea.hasExecutionErrors()) {
                        locationUseType = locationUseTypeControl.getLocationUseTypeByEntityInstance(entityInstance, entityPermission);
                    }
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return locationUseType;
    }

    public LocationUseType getLocationUseTypeByName(final ExecutionErrorAccumulator eea, final String locationUseTypeName,
            final UniversalEntitySpec universalEntitySpec, final boolean allowDefault) {
        return getLocationUseTypeByName(eea, locationUseTypeName, universalEntitySpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public LocationUseType getLocationUseTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String locationUseTypeName,
            final UniversalEntitySpec universalEntitySpec, final boolean allowDefault) {
        return getLocationUseTypeByName(eea, locationUseTypeName, universalEntitySpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public LocationUseType getLocationUseTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final LocationUseTypeUniversalSpec universalSpec,
            final boolean allowDefault, final EntityPermission entityPermission) {
        return getLocationUseTypeByName(eea, universalSpec.getLocationUseTypeName(), universalSpec, allowDefault, entityPermission);
    }

    public LocationUseType getLocationUseTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final LocationUseTypeUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getLocationUseTypeByName(eea, universalSpec.getLocationUseTypeName(), universalSpec, allowDefault);
    }

    public LocationUseType getLocationUseTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final LocationUseTypeUniversalSpec universalSpec,
            final boolean allowDefault) {
        return getLocationUseTypeByNameForUpdate(eea, universalSpec.getLocationUseTypeName(), universalSpec, allowDefault);
    }

}
