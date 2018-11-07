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

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.AppearanceTransfer;
import com.echothree.model.control.core.common.transfer.ColorTransfer;
import com.echothree.model.control.core.common.transfer.FontStyleTransfer;
import com.echothree.model.control.core.common.transfer.FontWeightTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.AppearanceDetail;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.core.server.entity.FontWeight;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.Set;

public class AppearanceTransferCache
        extends BaseCoreTransferCache<Appearance, AppearanceTransfer> {

    boolean includeTextDecorations;
    boolean includeTextTransformations;
    
    /** Creates a new instance of AppearanceTransferCache */
    public AppearanceTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeTextDecorations = options.contains(CoreOptions.AppearanceIncludeTextDecorations);
            includeTextTransformations = options.contains(CoreOptions.AppearanceIncludeTextTransformations);
        }
        
        setIncludeEntityInstance(true);
    }

    public AppearanceTransfer getAppearanceTransfer(Appearance appearance) {
        AppearanceTransfer appearanceTransfer = get(appearance);

        if(appearanceTransfer == null) {
            AppearanceDetail appearanceDetail = appearance.getLastDetail();
            String appearanceName = appearanceDetail.getAppearanceName();
            Color textColor = appearanceDetail.getTextColor();
            ColorTransfer textColorTransfer = textColor == null ? null : coreControl.getColorTransfer(userVisit, textColor);
            Color backgroundColor = appearanceDetail.getBackgroundColor();
            ColorTransfer backgroundColorTransfer = backgroundColor == null ? null : coreControl.getColorTransfer(userVisit, backgroundColor);
            FontStyle fontStyle = appearanceDetail.getFontStyle();
            FontStyleTransfer fontStyleTransfer = fontStyle == null ? null : coreControl.getFontStyleTransfer(userVisit, fontStyle);
            FontWeight fontWeight = appearanceDetail.getFontWeight();
            FontWeightTransfer fontWeightTransfer = fontWeight == null ? null : coreControl.getFontWeightTransfer(userVisit, fontWeight);
            Boolean isDefault = appearanceDetail.getIsDefault();
            Integer sortOrder = appearanceDetail.getSortOrder();
            String description = coreControl.getBestAppearanceDescription(appearance, getLanguage());

            appearanceTransfer = new AppearanceTransfer(appearanceName, textColorTransfer, backgroundColorTransfer, fontStyleTransfer, fontWeightTransfer,
                    isDefault, sortOrder, description);
            put(appearance, appearanceTransfer);
            
            if(includeTextDecorations) {
                appearanceTransfer.setAppearanceTextDecorations(new ListWrapper<>(coreControl.getAppearanceTextDecorationTransfersByAppearance(userVisit, appearance)));
            }
            
            if(includeTextTransformations) {
                appearanceTransfer.setAppearanceTextTransformations(new ListWrapper<>(coreControl.getAppearanceTextTransformationTransfersByAppearance(userVisit, appearance)));
            }
        }

        return appearanceTransfer;
    }

}
