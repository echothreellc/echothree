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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.FontWeightTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.FontWeight;
import com.echothree.model.data.core.server.entity.FontWeightDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class FontWeightTransferCache
        extends BaseCoreTransferCache<FontWeight, FontWeightTransfer> {

    /** Creates a new instance of FontWeightTransferCache */
    public FontWeightTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        setIncludeEntityInstance(true);
    }

    public FontWeightTransfer getFontWeightTransfer(FontWeight fontWeight) {
        FontWeightTransfer fontWeightTransfer = get(fontWeight);

        if(fontWeightTransfer == null) {
            FontWeightDetail fontWeightDetail = fontWeight.getLastDetail();
            String fontWeightName = fontWeightDetail.getFontWeightName();
            Boolean isDefault = fontWeightDetail.getIsDefault();
            Integer sortOrder = fontWeightDetail.getSortOrder();
            String description = coreControl.getBestFontWeightDescription(fontWeight, getLanguage());

            fontWeightTransfer = new FontWeightTransfer(fontWeightName, isDefault, sortOrder, description);
            put(fontWeight, fontWeightTransfer);
        }

        return fontWeightTransfer;
    }

}
