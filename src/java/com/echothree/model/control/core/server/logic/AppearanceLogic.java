// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.core.server.logic;

import com.echothree.model.control.core.common.exception.UnknownAppearanceNameException;
import com.echothree.model.control.core.common.exception.UnknownColorNameException;
import com.echothree.model.control.core.common.exception.UnknownFontStyleNameException;
import com.echothree.model.control.core.common.exception.UnknownFontWeightNameException;
import com.echothree.model.control.core.common.exception.UnknownTextDecorationNameException;
import com.echothree.model.control.core.common.exception.UnknownTextTransformationNameException;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.core.server.entity.FontWeight;
import com.echothree.model.data.core.server.entity.TextDecoration;
import com.echothree.model.data.core.server.entity.TextTransformation;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class AppearanceLogic
        extends BaseLogic {
    
    private AppearanceLogic() {
        super();
    }
    
    private static class AppearanceLogicHolder {
        static AppearanceLogic instance = new AppearanceLogic();
    }
    
    public static AppearanceLogic getInstance() {
        return AppearanceLogicHolder.instance;
    }
    
    public Appearance getAppearanceByName(final ExecutionErrorAccumulator eea, final String appearanceName) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        Appearance appearance = coreControl.getAppearanceByName(appearanceName);

        if(appearance == null) {
            handleExecutionError(UnknownAppearanceNameException.class, eea, ExecutionErrors.UnknownAppearanceName.name(), appearanceName);
        }

        return appearance;
    }
    
    public Color getColorByName(final ExecutionErrorAccumulator eea, final String colorName) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        Color color = coreControl.getColorByName(colorName);

        if(color == null) {
            handleExecutionError(UnknownColorNameException.class, eea, ExecutionErrors.UnknownColorName.name(), colorName);
        }

        return color;
    }
    
    public FontStyle getFontStyleByName(final ExecutionErrorAccumulator eea, final String fontStyleName) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        FontStyle fontStyle = coreControl.getFontStyleByName(fontStyleName);

        if(fontStyle == null) {
            handleExecutionError(UnknownFontStyleNameException.class, eea, ExecutionErrors.UnknownFontStyleName.name(), fontStyleName);
        }

        return fontStyle;
    }
    
    public FontWeight getFontWeightByName(final ExecutionErrorAccumulator eea, final String fontWeightName) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        FontWeight fontWeight = coreControl.getFontWeightByName(fontWeightName);

        if(fontWeight == null) {
            handleExecutionError(UnknownFontWeightNameException.class, eea, ExecutionErrors.UnknownFontWeightName.name(), fontWeightName);
        }

        return fontWeight;
    }
    
    public TextDecoration getTextDecorationByName(final ExecutionErrorAccumulator eea, final String textDecorationName) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        TextDecoration textDecoration = coreControl.getTextDecorationByName(textDecorationName);

        if(textDecoration == null) {
            handleExecutionError(UnknownTextDecorationNameException.class, eea, ExecutionErrors.UnknownTextDecorationName.name(), textDecorationName);
        }

        return textDecoration;
    }
    
    public TextTransformation getTextTransformationByName(final ExecutionErrorAccumulator eea, final String textTransformationName) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        TextTransformation textTransformation = coreControl.getTextTransformationByName(textTransformationName);

        if(textTransformation == null) {
            handleExecutionError(UnknownTextTransformationNameException.class, eea, ExecutionErrors.UnknownTextTransformationName.name(), textTransformationName);
        }

        return textTransformation;
    }
    
}
