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

import com.echothree.model.control.license.common.LicenseOptions;
import com.echothree.model.control.license.common.transfer.LicenseTypeTransfer;
import com.echothree.model.control.license.server.control.LicenseControl;
import com.echothree.model.data.license.server.entity.LicenseType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class LicenseTypeTransferCache
        extends BaseLicenseTransferCache<LicenseType, LicenseTypeTransfer> {

    LicenseControl licenseControl = Session.getModelController(LicenseControl.class);

    /** Creates a new instance of LicenseTypeTransferCache */
    protected LicenseTypeTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(LicenseOptions.LicenseTypeIncludeUuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public LicenseTypeTransfer getLicenseTypeTransfer(UserVisit userVisit, LicenseType licenseType) {
        var licenseTypeTransfer = get(licenseType);

        if(licenseTypeTransfer == null) {
            var licenseTypeDetail = licenseType.getLastDetail();
            var licenseTypeName = licenseTypeDetail.getLicenseTypeName();
            var isDefault = licenseTypeDetail.getIsDefault();
            var sortOrder = licenseTypeDetail.getSortOrder();
            var description = licenseControl.getBestLicenseTypeDescription(licenseType, getLanguage(userVisit));

            licenseTypeTransfer = new LicenseTypeTransfer(licenseTypeName, isDefault, sortOrder, description);
            put(userVisit, licenseType, licenseTypeTransfer);
        }

        return licenseTypeTransfer;
    }

}
