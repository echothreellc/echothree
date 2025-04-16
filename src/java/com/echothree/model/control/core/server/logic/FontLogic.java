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

import com.echothree.model.control.core.common.exception.UnknownFontStyleNameException;
import com.echothree.model.control.core.common.exception.UnknownFontWeightNameException;
import com.echothree.model.control.core.server.control.FontControl;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.core.server.entity.FontWeight;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class FontLogic
        extends BaseLogic {

    private FontLogic() {
        super();
    }
    
    private static class FontLogicHolder {
        static FontLogic instance = new FontLogic();
    }
    
    public static FontLogic getInstance() {
        return FontLogicHolder.instance;
    }
    
    public FontStyle getFontStyleByName(final ExecutionErrorAccumulator eea, final String fontStyleName) {
        var fontControl = Session.getModelController(FontControl.class);
        var fontStyle = fontControl.getFontStyleByName(fontStyleName);

        if(fontStyle == null) {
            handleExecutionError(UnknownFontStyleNameException.class, eea, ExecutionErrors.UnknownFontStyleName.name(), fontStyleName);
        }

        return fontStyle;
    }
    
    public FontWeight getFontWeightByName(final ExecutionErrorAccumulator eea, final String fontWeightName) {
        var fontControl = Session.getModelController(FontControl.class);
        var fontWeight = fontControl.getFontWeightByName(fontWeightName);

        if(fontWeight == null) {
            handleExecutionError(UnknownFontWeightNameException.class, eea, ExecutionErrors.UnknownFontWeightName.name(), fontWeightName);
        }

        return fontWeight;
    }
    
}
