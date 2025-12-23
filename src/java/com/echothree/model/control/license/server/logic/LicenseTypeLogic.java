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

package com.echothree.model.control.license.server.logic;

import com.echothree.model.control.license.common.exception.UnknownLicenseTypeNameException;
import com.echothree.model.control.license.server.control.LicenseControl;
import com.echothree.model.data.license.server.entity.LicenseType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class LicenseTypeLogic
        extends BaseLogic {

    protected LicenseTypeLogic() {
        super();
    }

    public static LicenseTypeLogic getInstance() {
        return CDI.current().select(LicenseTypeLogic.class).get();
    }
    
    public LicenseType getLicenseTypeByName(final ExecutionErrorAccumulator eea, final String licenseTypeName) {
        var licenseControl = Session.getModelController(LicenseControl.class);
        var licenseType = licenseControl.getLicenseTypeByName(licenseTypeName);

        if(licenseType == null) {
            handleExecutionError(UnknownLicenseTypeNameException.class, eea, ExecutionErrors.UnknownLicenseTypeName.name(), licenseTypeName);
        }

        return licenseType;
    }
    
}
