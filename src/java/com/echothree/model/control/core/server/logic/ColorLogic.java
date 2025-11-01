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

import com.echothree.model.control.core.common.exception.UnknownColorNameException;
import com.echothree.model.control.core.server.control.ColorControl;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class ColorLogic
        extends BaseLogic {

    protected ColorLogic() {
        super();
    }

    public static ColorLogic getInstance() {
        return CDI.current().select(ColorLogic.class).get();
    }

    public Color getColorByName(final ExecutionErrorAccumulator eea, final String colorName) {
        var colorControl = Session.getModelController(ColorControl.class);
        var color = colorControl.getColorByName(colorName);

        if(color == null) {
            handleExecutionError(UnknownColorNameException.class, eea, ExecutionErrors.UnknownColorName.name(), colorName);
        }

        return color;
    }
    
}
