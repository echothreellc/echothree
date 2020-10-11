// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.geo.common.exception.UnknownGeoCodeScopeNameException;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCodeScope;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class GeoCodeScopeLogic
        extends BaseLogic {

    private GeoCodeScopeLogic() {
        super();
    }

    private static class GeoLogicScopeHolder {
        static GeoCodeScopeLogic instance = new GeoCodeScopeLogic();
    }

    public static GeoCodeScopeLogic getInstance() {
        return GeoLogicScopeHolder.instance;
    }

    public GeoCodeScope getGeoCodeScopeByName(final ExecutionErrorAccumulator eea, final String geoCodeScopeName) {
        var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        var geoCodeScope = geoControl.getGeoCodeScopeByName(geoCodeScopeName);

        if(geoCodeScope == null) {
            handleExecutionError(UnknownGeoCodeScopeNameException.class, eea, ExecutionErrors.UnknownGeoCodeScopeName.name(),
                    geoCodeScopeName);
        }

        return geoCodeScope;
    }

}
