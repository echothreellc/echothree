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

import com.echothree.model.control.geo.common.exception.UnknownGeoCodeAliasTypeNameException;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCodeAliasType;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class GeoCodeAliasTypeLogic
        extends BaseLogic {

    protected GeoCodeAliasTypeLogic() {
        super();
    }

    public static GeoCodeAliasTypeLogic getInstance() {
        return CDI.current().select(GeoCodeAliasTypeLogic.class).get();
    }

    public GeoCodeAliasType getGeoCodeAliasTypeByName(final ExecutionErrorAccumulator eea, final GeoCodeType geoCodeType,
            final String geoCodeAliasTypeName) {
        var geoControl = Session.getModelController(GeoControl.class);
        var geoCodeAliasType = geoControl.getGeoCodeAliasTypeByName(geoCodeType, geoCodeAliasTypeName);

        if(geoCodeAliasType == null) {
            handleExecutionError(UnknownGeoCodeAliasTypeNameException.class, eea, ExecutionErrors.UnknownGeoCodeAliasTypeName.name(),
                    geoCodeType.getLastDetail().getGeoCodeTypeName(), geoCodeAliasTypeName);
        }

        return geoCodeAliasType;
    }

}
