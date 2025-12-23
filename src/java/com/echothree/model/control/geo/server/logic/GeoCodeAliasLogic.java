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

import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCode;
import com.echothree.model.data.geo.server.entity.GeoCodeAlias;
import com.echothree.model.data.geo.server.value.GeoCodeAliasValue;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class GeoCodeAliasLogic
        extends BaseLogic {

    protected GeoCodeAliasLogic() {
        super();
    }

    public static GeoCodeAliasLogic getInstance() {
        return CDI.current().select(GeoCodeAliasLogic.class).get();
    }

    public GeoCodeAlias getGeoCodeAliasUsingNames(final ExecutionErrorAccumulator eea, final GeoCode geoCode,
            final String geoCodeAliasTypeName) {
        var geoControl = Session.getModelController(GeoControl.class);

        return geoControl.getGeoCodeAlias(geoCode, GeoCodeAliasTypeLogic.getInstance().getGeoCodeAliasTypeByName(eea,
                geoCode.getLastDetail().getGeoCodeType(), geoCodeAliasTypeName));
    }

    public GeoCodeAliasValue getGeoCodeAliasValueUsingNames(final ExecutionErrorAccumulator ema, final GeoCode geoCode,
            final String geoCodeAliasTypeName) {
        var geoControl = Session.getModelController(GeoControl.class);

        return geoControl.getGeoCodeAliasValueForUpdate(geoCode, GeoCodeAliasTypeLogic.getInstance().getGeoCodeAliasTypeByName(ema,
                geoCode.getLastDetail().getGeoCodeType(), geoCodeAliasTypeName));
    }

}
