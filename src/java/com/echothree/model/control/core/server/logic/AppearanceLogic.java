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

package com.echothree.model.control.core.server.logic;

import com.echothree.control.user.core.common.spec.AppearanceUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.UnknownAppearanceNameException;
import com.echothree.model.control.core.common.exception.UnknownColorNameException;
import com.echothree.model.control.core.common.exception.UnknownFontStyleNameException;
import com.echothree.model.control.core.common.exception.UnknownFontWeightNameException;
import com.echothree.model.control.core.common.exception.UnknownTextDecorationNameException;
import com.echothree.model.control.core.common.exception.UnknownTextTransformationNameException;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.core.server.entity.FontWeight;
import com.echothree.model.data.core.server.entity.TextDecoration;
import com.echothree.model.data.core.server.entity.TextTransformation;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
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

    public Appearance getAppearanceByName(final ExecutionErrorAccumulator eea, final String appearanceName,
            final EntityPermission entityPermission) {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var appearance = appearanceControl.getAppearanceByName(appearanceName, entityPermission);

        if(appearance == null) {
            handleExecutionError(UnknownAppearanceNameException.class, eea, ExecutionErrors.UnknownAppearanceName.name(), appearanceName);
        }

        return appearance;
    }

    public Appearance getAppearanceByName(final ExecutionErrorAccumulator eea, final String appearanceName) {
        return getAppearanceByName(eea, appearanceName, EntityPermission.READ_ONLY);
    }

    public Appearance getAppearanceByNameForUpdate(final ExecutionErrorAccumulator eea, final String appearanceName) {
        return getAppearanceByName(eea, appearanceName, EntityPermission.READ_WRITE);
    }

    public Appearance getAppearanceByUniversalSpec(final ExecutionErrorAccumulator eea,
            final AppearanceUniversalSpec universalSpec, final EntityPermission entityPermission) {
        Appearance appearance = null;
        var appearanceName = universalSpec.getAppearanceName();
        var parameterCount = (appearanceName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1:
                if(appearanceName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Appearance.name());

                    if(!eea.hasExecutionErrors()) {
                        var appearanceControl = Session.getModelController(AppearanceControl.class);

                        appearance = appearanceControl.getAppearanceByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    appearance = getAppearanceByName(eea, appearanceName, entityPermission);
                }
                break;
            default:
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                break;
        }

        return appearance;
    }

    public Appearance getAppearanceByUniversalSpec(final ExecutionErrorAccumulator eea,
            final AppearanceUniversalSpec universalSpec) {
        return getAppearanceByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public Appearance getAppearanceByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final AppearanceUniversalSpec universalSpec) {
        return getAppearanceByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public Color getColorByName(final ExecutionErrorAccumulator eea, final String colorName) {
        var coreControl = Session.getModelController(CoreControl.class);
        var color = coreControl.getColorByName(colorName);

        if(color == null) {
            handleExecutionError(UnknownColorNameException.class, eea, ExecutionErrors.UnknownColorName.name(), colorName);
        }

        return color;
    }
    
    public FontStyle getFontStyleByName(final ExecutionErrorAccumulator eea, final String fontStyleName) {
        var coreControl = Session.getModelController(CoreControl.class);
        var fontStyle = coreControl.getFontStyleByName(fontStyleName);

        if(fontStyle == null) {
            handleExecutionError(UnknownFontStyleNameException.class, eea, ExecutionErrors.UnknownFontStyleName.name(), fontStyleName);
        }

        return fontStyle;
    }
    
    public FontWeight getFontWeightByName(final ExecutionErrorAccumulator eea, final String fontWeightName) {
        var coreControl = Session.getModelController(CoreControl.class);
        var fontWeight = coreControl.getFontWeightByName(fontWeightName);

        if(fontWeight == null) {
            handleExecutionError(UnknownFontWeightNameException.class, eea, ExecutionErrors.UnknownFontWeightName.name(), fontWeightName);
        }

        return fontWeight;
    }
    
    public TextDecoration getTextDecorationByName(final ExecutionErrorAccumulator eea, final String textDecorationName) {
        var coreControl = Session.getModelController(CoreControl.class);
        var textDecoration = coreControl.getTextDecorationByName(textDecorationName);

        if(textDecoration == null) {
            handleExecutionError(UnknownTextDecorationNameException.class, eea, ExecutionErrors.UnknownTextDecorationName.name(), textDecorationName);
        }

        return textDecoration;
    }
    
    public TextTransformation getTextTransformationByName(final ExecutionErrorAccumulator eea, final String textTransformationName) {
        var coreControl = Session.getModelController(CoreControl.class);
        var textTransformation = coreControl.getTextTransformationByName(textTransformationName);

        if(textTransformation == null) {
            handleExecutionError(UnknownTextTransformationNameException.class, eea, ExecutionErrors.UnknownTextTransformationName.name(), textTransformationName);
        }

        return textTransformation;
    }
    
}
