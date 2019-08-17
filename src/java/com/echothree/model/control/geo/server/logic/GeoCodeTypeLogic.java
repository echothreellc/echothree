// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.geo.common.exception.UnknownGeoCodeTypeNameException;
import com.echothree.model.control.geo.server.GeoControl;
import com.echothree.model.data.geo.server.entity.GeoCodeType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class GeoCodeTypeLogic
        extends BaseLogic {

    private GeoCodeTypeLogic() {
        super();
    }

    private static class GeoLogicTypeHolder {
        static GeoCodeTypeLogic instance = new GeoCodeTypeLogic();
    }

    public static GeoCodeTypeLogic getInstance() {
        return GeoLogicTypeHolder.instance;
    }

    public GeoCodeType getGeoCodeTypeByName(final ExecutionErrorAccumulator eea, final String geoCodeTypeName) {
        var geoControl = (GeoControl)Session.getModelController(GeoControl.class);
        var geoCodeType = geoControl.getGeoCodeTypeByName(geoCodeTypeName);

        if(geoCodeType == null) {
            handleExecutionError(UnknownGeoCodeTypeNameException.class, eea, ExecutionErrors.UnknownGeoCodeTypeName.name(),
                    geoCodeTypeName);
        }

        return geoCodeType;
    }

}
