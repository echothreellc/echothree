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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.FontStyleTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.core.server.entity.FontStyleDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class FontStyleTransferCache
        extends BaseCoreTransferCache<FontStyle, FontStyleTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of FontStyleTransferCache */
    public FontStyleTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }

    public FontStyleTransfer getFontStyleTransfer(FontStyle fontStyle) {
        FontStyleTransfer fontStyleTransfer = get(fontStyle);

        if(fontStyleTransfer == null) {
            FontStyleDetail fontStyleDetail = fontStyle.getLastDetail();
            String fontStyleName = fontStyleDetail.getFontStyleName();
            Boolean isDefault = fontStyleDetail.getIsDefault();
            Integer sortOrder = fontStyleDetail.getSortOrder();
            String description = coreControl.getBestFontStyleDescription(fontStyle, getLanguage());

            fontStyleTransfer = new FontStyleTransfer(fontStyleName, isDefault, sortOrder, description);
            put(fontStyle, fontStyleTransfer);
        }

        return fontStyleTransfer;
    }

}
