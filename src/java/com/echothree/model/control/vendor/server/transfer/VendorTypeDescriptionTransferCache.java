// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.vendor.server.transfer;

import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.control.vendor.remote.transfer.VendorTypeDescriptionTransfer;
import com.echothree.model.control.vendor.remote.transfer.VendorTypeTransfer;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.VendorTypeDescription;

public class VendorTypeDescriptionTransferCache
        extends BaseVendorDescriptionTransferCache<VendorTypeDescription, VendorTypeDescriptionTransfer> {
    
    /** Creates a new instance of VendorTypeDescriptionTransferCache */
    public VendorTypeDescriptionTransferCache(UserVisit userVisit, VendorControl vendorControl) {
        super(userVisit, vendorControl);
    }
    
    public VendorTypeDescriptionTransfer getVendorTypeDescriptionTransfer(VendorTypeDescription vendorTypeDescription) {
        VendorTypeDescriptionTransfer vendorTypeDescriptionTransfer = get(vendorTypeDescription);
        
        if(vendorTypeDescriptionTransfer == null) {
            VendorTypeTransfer vendorTypeTransfer = vendorControl.getVendorTypeTransfer(userVisit, vendorTypeDescription.getVendorType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, vendorTypeDescription.getLanguage());
            
            vendorTypeDescriptionTransfer = new VendorTypeDescriptionTransfer(languageTransfer, vendorTypeTransfer, vendorTypeDescription.getDescription());
            put(vendorTypeDescription, vendorTypeDescriptionTransfer);
        }
        
        return vendorTypeDescriptionTransfer;
    }
    
}
