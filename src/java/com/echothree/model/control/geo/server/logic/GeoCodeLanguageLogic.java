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

import com.echothree.control.user.geo.common.spec.GeoCodeLanguageSpec;
import com.echothree.model.control.geo.common.exception.UnknownGeoCodeLanguageException;
import com.echothree.model.control.geo.server.control.GeoControl;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.geo.server.entity.GeoCodeLanguage;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GeoCodeLanguageLogic
        extends BaseLogic {

    @Inject
    GeoControl geoControl;

    @Inject
    GeoCodeLogic geoCodeLogic;

    @Inject
    LanguageLogic languageLogic;

    protected GeoCodeLanguageLogic() {
        super();
    }

    public GeoCodeLanguage getGeoCodeLanguageBySpec(final ExecutionErrorAccumulator eea,
            final GeoCodeLanguageSpec spec, final EntityPermission entityPermission) {
        GeoCodeLanguage geoCodeLanguage = null;
        var geoCodeName = spec.getGeoCodeName();
        var geoCode = geoCodeLogic.getGeoCodeByName(eea, geoCodeName);

        if(!hasExecutionErrors(eea)) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(eea, languageIsoName);

            if(!hasExecutionErrors(eea)) {
                geoCodeLanguage = geoControl.getGeoCodeLanguage(geoCode, language, entityPermission);

                if(geoCodeLanguage == null) {
                    handleExecutionError(UnknownGeoCodeLanguageException.class, eea, ExecutionErrors.UnknownGeoCodeLanguage.name(), geoCodeName, languageIsoName);
                }
            }
        }

        return geoCodeLanguage;
    }

}
