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

import com.echothree.control.user.geo.common.spec.GeoCodeDateTimeFormatSpec;
import com.echothree.model.control.geo.common.exception.UnknownGeoCodeDateTimeFormatException;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.server.logic.DateTimeFormatLogic;
import com.echothree.model.data.geo.server.entity.GeoCodeDateTimeFormat;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GeoCodeDateTimeFormatLogic
        extends BaseLogic {

    @Inject
    GeoControl geoControl;

    @Inject
    DateTimeFormatLogic dateTimeFormatLogic;

    @Inject
    GeoCodeLogic geoCodeLogic;

    protected GeoCodeDateTimeFormatLogic() {
        super();
    }

    public GeoCodeDateTimeFormat getGeoCodeDateTimeFormatBySpec(final ExecutionErrorAccumulator eea,
            final GeoCodeDateTimeFormatSpec spec, final EntityPermission entityPermission) {
        GeoCodeDateTimeFormat geoCodeDateTimeFormat = null;
        var geoCodeName = spec.getGeoCodeName();
        var geoCode = geoCodeLogic.getGeoCodeByName(eea, geoCodeName);

        if(!hasExecutionErrors(eea)) {
            var dateTimeFormatName = spec.getDateTimeFormatName();
            var dateTimeFormat = dateTimeFormatLogic.getDateTimeFormatByName(eea, dateTimeFormatName);

            if(!hasExecutionErrors(eea)) {
                geoCodeDateTimeFormat = geoControl.getGeoCodeDateTimeFormat(geoCode, dateTimeFormat, entityPermission);

                if(geoCodeDateTimeFormat == null) {
                    handleExecutionError(UnknownGeoCodeDateTimeFormatException.class, eea, ExecutionErrors.UnknownGeoCodeDateTimeFormat.name(), geoCodeName, dateTimeFormatName);
                }
            }
        }

        return geoCodeDateTimeFormat;
    }

}
