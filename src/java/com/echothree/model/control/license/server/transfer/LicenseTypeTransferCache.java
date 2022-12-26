// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.data.license.server.entity.LicenseTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import java.util.Set;

public class LicenseTypeTransferCache
        extends BaseLicenseTransferCache<LicenseType, LicenseTypeTransfer> {

    /** Creates a new instance of LicenseTypeTransferCache */
    public LicenseTypeTransferCache(UserVisit userVisit, LicenseControl licenseControl) {
        super(userVisit, licenseControl);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(LicenseOptions.LicenseTypeIncludeKey));
            setIncludeGuid(options.contains(LicenseOptions.LicenseTypeIncludeGuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public LicenseTypeTransfer getLicenseTypeTransfer(LicenseType licenseType) {
        LicenseTypeTransfer licenseTypeTransfer = get(licenseType);

        if(licenseTypeTransfer == null) {
            LicenseTypeDetail licenseTypeDetail = licenseType.getLastDetail();
            String licenseTypeName = licenseTypeDetail.getLicenseTypeName();
            Boolean isDefault = licenseTypeDetail.getIsDefault();
            Integer sortOrder = licenseTypeDetail.getSortOrder();
            String description = licenseControl.getBestLicenseTypeDescription(licenseType, getLanguage());

            licenseTypeTransfer = new LicenseTypeTransfer(licenseTypeName, isDefault, sortOrder, description);
            put(licenseType, licenseTypeTransfer);
        }

        return licenseTypeTransfer;
    }

}
