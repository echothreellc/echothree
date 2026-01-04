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

package com.echothree.model.control.geo.server.logic;

import com.echothree.control.user.geo.common.spec.GeoCodeScopeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.geo.common.exception.DuplicateGeoCodeScopeNameException;
import com.echothree.model.control.geo.common.exception.UnknownDefaultGeoCodeScopeException;
import com.echothree.model.control.geo.common.exception.UnknownGeoCodeScopeNameException;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.model.data.geo.server.value.GeoCodeScopeDetailValue;
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
public class GeoCodeScopeLogic
        extends BaseLogic {

    protected GeoCodeScopeLogic() {
        super();
    }

    public static GeoCodeScopeLogic getInstance() {
        return CDI.current().select(GeoCodeScopeLogic.class).get();
    }

    public GeoCodeScope createGeoCodeScope(final ExecutionErrorAccumulator eea, final String geoCodeScopeName,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeScope = geoControl.getGeoCodeScopeByName(geoCodeScopeName);

        if(geoCodeScope == null) {
            geoCodeScope = geoControl.createGeoCodeScope(geoCodeScopeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                geoControl.createGeoCodeScopeDescription(geoCodeScope, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateGeoCodeScopeNameException.class, eea, ExecutionErrors.DuplicateGeoCodeScopeName.name(),
                    geoCodeScopeName);
        }

        return geoCodeScope;
    }

    public GeoCodeScope getGeoCodeScopeByName(final ExecutionErrorAccumulator eea, final String geoCodeScopeName,
            final EntityPermission entityPermission) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeScope = geoControl.getGeoCodeScopeByName(geoCodeScopeName, entityPermission);

        if(geoCodeScope == null) {
            handleExecutionError(UnknownGeoCodeScopeNameException.class, eea, ExecutionErrors.UnknownGeoCodeScopeName.name(), geoCodeScopeName);
        }

        return geoCodeScope;
    }

    public GeoCodeScope getGeoCodeScopeByName(final ExecutionErrorAccumulator eea, final String geoCodeScopeName) {
        return getGeoCodeScopeByName(eea, geoCodeScopeName, EntityPermission.READ_ONLY);
    }

    public GeoCodeScope getGeoCodeScopeByNameForUpdate(final ExecutionErrorAccumulator eea, final String geoCodeScopeName) {
        return getGeoCodeScopeByName(eea, geoCodeScopeName, EntityPermission.READ_WRITE);
    }

    public GeoCodeScope getGeoCodeScopeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final GeoCodeScopeUniversalSpec universalSpec, boolean allowDefault, final EntityPermission entityPermission) {
        GeoCodeScope geoCodeScope = null;
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeScopeName = universalSpec.getGeoCodeScopeName();
        var parameterCount = (geoCodeScopeName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    geoCodeScope = geoControl.getDefaultGeoCodeScope(entityPermission);

                    if(geoCodeScope == null) {
                        handleExecutionError(UnknownDefaultGeoCodeScopeException.class, eea, ExecutionErrors.UnknownDefaultGeoCodeScope.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(geoCodeScopeName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.GeoCodeScope.name());

                    if(!eea.hasExecutionErrors()) {
                        geoCodeScope = geoControl.getGeoCodeScopeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    geoCodeScope = getGeoCodeScopeByName(eea, geoCodeScopeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return geoCodeScope;
    }

    public GeoCodeScope getGeoCodeScopeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final GeoCodeScopeUniversalSpec universalSpec, boolean allowDefault) {
        return getGeoCodeScopeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public GeoCodeScope getGeoCodeScopeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final GeoCodeScopeUniversalSpec universalSpec, boolean allowDefault) {
        return getGeoCodeScopeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void updateGeoCodeScopeFromValue(GeoCodeScopeDetailValue geoCodeScopeDetailValue, BasePK updatedBy) {
        var geoControl = Session.getModelController(GeoControl.class);

        geoControl.updateGeoCodeScopeFromValue(geoCodeScopeDetailValue, updatedBy);
    }

    private void checkDeleteGeoCodeScope(final ExecutionErrorAccumulator eea, final GeoCodeScope geoCodeScope) {
        var geoControl = Session.getModelController(GeoControl.class);

        if(geoControl.countGeoCodesByGeoCodeScope(geoCodeScope) != 0) {
            eea.addExecutionError(ExecutionErrors.CannotDeleteGeoCodeScopeInUse.name(),
                    geoCodeScope.getLastDetail().getGeoCodeScopeName());
        }
    }

    public void deleteGeoCodeScope(final ExecutionErrorAccumulator eea, final GeoCodeScope geoCodeScope, final BasePK deletedBy) {
        checkDeleteGeoCodeScope(eea, geoCodeScope);

        if(!eea.hasExecutionErrors()) {
            var geoControl = Session.getModelController(GeoControl.class);

            geoControl.deleteGeoCodeScope(geoCodeScope, deletedBy);
        }
    }

}
