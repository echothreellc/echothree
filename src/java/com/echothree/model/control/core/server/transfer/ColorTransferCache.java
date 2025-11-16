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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.ColorTransfer;
import com.echothree.model.control.core.server.control.ColorControl;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ColorTransferCache
        extends BaseCoreTransferCache<Color, ColorTransfer> {

    ColorControl colorControl = Session.getModelController(ColorControl.class);

    /** Creates a new instance of ColorTransferCache */
    public ColorTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }

    public ColorTransfer getColorTransfer(UserVisit userVisit, Color color) {
        var colorTransfer = get(color);

        if(colorTransfer == null) {
            var colorDetail = color.getLastDetail();
            var colorName = colorDetail.getColorName();
            var red = colorDetail.getRed();
            var green = colorDetail.getGreen();
            var blue = colorDetail.getBlue();
            var isDefault = colorDetail.getIsDefault();
            var sortOrder = colorDetail.getSortOrder();
            var description = colorControl.getBestColorDescription(color, getLanguage(userVisit));

            colorTransfer = new ColorTransfer(colorName, red, green, blue, isDefault, sortOrder, description);
            put(userVisit, color, colorTransfer);
        }

        return colorTransfer;
    }

}
