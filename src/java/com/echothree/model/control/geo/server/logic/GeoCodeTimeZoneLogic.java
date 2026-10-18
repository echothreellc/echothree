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

import com.echothree.control.user.geo.common.spec.GeoCodeTimeZoneSpec;
import com.echothree.model.control.geo.common.exception.UnknownGeoCodeTimeZoneException;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.server.logic.TimeZoneLogic;
import com.echothree.model.data.geo.server.entity.GeoCodeTimeZone;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GeoCodeTimeZoneLogic
        extends BaseLogic {

    @Inject
    GeoControl geoControl;

    @Inject
    GeoCodeLogic geoCodeLogic;

    @Inject
    TimeZoneLogic timeZoneLogic;

    protected GeoCodeTimeZoneLogic() {
        super();
    }

    public GeoCodeTimeZone getGeoCodeTimeZoneBySpec(final ExecutionErrorAccumulator eea,
            final GeoCodeTimeZoneSpec spec, final EntityPermission entityPermission) {
        GeoCodeTimeZone geoCodeTimeZone = null;
        var geoCodeName = spec.getGeoCodeName();
        var geoCode = geoCodeLogic.getGeoCodeByName(eea, geoCodeName);

        if(!hasExecutionErrors(eea)) {
            var javaTimeZoneName = spec.getJavaTimeZoneName();
            var timeZone = timeZoneLogic.getTimeZoneByName(eea, javaTimeZoneName);

            if(!hasExecutionErrors(eea)) {
                geoCodeTimeZone = geoControl.getGeoCodeTimeZone(geoCode, timeZone, entityPermission);

                if(geoCodeTimeZone == null) {
                    handleExecutionError(UnknownGeoCodeTimeZoneException.class, eea, ExecutionErrors.UnknownGeoCodeTimeZone.name(), geoCodeName, javaTimeZoneName);
                }
            }
        }

        return geoCodeTimeZone;
    }

}
