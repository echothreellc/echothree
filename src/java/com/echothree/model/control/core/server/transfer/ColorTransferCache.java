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

import com.echothree.model.control.core.common.transfer.ColorTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.core.server.entity.ColorDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ColorTransferCache
        extends BaseCoreTransferCache<Color, ColorTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of ColorTransferCache */
    public ColorTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        setIncludeEntityInstance(true);
    }

    public ColorTransfer getColorTransfer(Color color) {
        ColorTransfer colorTransfer = get(color);

        if(colorTransfer == null) {
            ColorDetail colorDetail = color.getLastDetail();
            String colorName = colorDetail.getColorName();
            Integer red = colorDetail.getRed();
            Integer green = colorDetail.getGreen();
            Integer blue = colorDetail.getBlue();
            Boolean isDefault = colorDetail.getIsDefault();
            Integer sortOrder = colorDetail.getSortOrder();
            String description = coreControl.getBestColorDescription(color, getLanguage());

            colorTransfer = new ColorTransfer(colorName, red, green, blue, isDefault, sortOrder, description);
            put(color, colorTransfer);
        }

        return colorTransfer;
    }

}
