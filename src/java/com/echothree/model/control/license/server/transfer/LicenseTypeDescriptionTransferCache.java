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

package com.echothree.model.control.license.server.transfer;

import com.echothree.model.control.license.common.transfer.LicenseTypeDescriptionTransfer;
import com.echothree.model.control.license.server.control.LicenseControl;
import com.echothree.model.data.license.server.entity.LicenseTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class LicenseTypeDescriptionTransferCache
        extends BaseLicenseDescriptionTransferCache<LicenseTypeDescription, LicenseTypeDescriptionTransfer> {

    LicenseControl licenseControl = Session.getModelController(LicenseControl.class);

    /** Creates a new instance of LicenseTypeDescriptionTransferCache */
    public LicenseTypeDescriptionTransferCache() {
        super();
    }
    
    public LicenseTypeDescriptionTransfer getLicenseTypeDescriptionTransfer(UserVisit userVisit, LicenseTypeDescription licenseTypeDescription) {
        var licenseTypeDescriptionTransfer = get(licenseTypeDescription);
        
        if(licenseTypeDescriptionTransfer == null) {
            var licenseTypeTransfer = licenseControl.getLicenseTypeTransfer(userVisit, licenseTypeDescription.getLicenseType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, licenseTypeDescription.getLanguage());
            
            licenseTypeDescriptionTransfer = new LicenseTypeDescriptionTransfer(languageTransfer, licenseTypeTransfer, licenseTypeDescription.getDescription());
            put(userVisit, licenseTypeDescription, licenseTypeDescriptionTransfer);
        }
        return licenseTypeDescriptionTransfer;
    }
    
}
