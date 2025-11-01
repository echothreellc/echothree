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

import com.echothree.model.control.core.common.exception.UnknownTextDecorationNameException;
import com.echothree.model.control.core.common.exception.UnknownTextTransformationNameException;
import com.echothree.model.control.core.server.control.TextControl;
import com.echothree.model.data.core.server.entity.TextDecoration;
import com.echothree.model.data.core.server.entity.TextTransformation;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class TextLogic
        extends BaseLogic {

    protected TextLogic() {
        super();
    }

    public static TextLogic getInstance() {
        return CDI.current().select(TextLogic.class).get();
    }

    public TextDecoration getTextDecorationByName(final ExecutionErrorAccumulator eea, final String textDecorationName) {
        var textControl = Session.getModelController(TextControl.class);
        var textDecoration = textControl.getTextDecorationByName(textDecorationName);

        if(textDecoration == null) {
            handleExecutionError(UnknownTextDecorationNameException.class, eea, ExecutionErrors.UnknownTextDecorationName.name(), textDecorationName);
        }

        return textDecoration;
    }
    
    public TextTransformation getTextTransformationByName(final ExecutionErrorAccumulator eea, final String textTransformationName) {
        var textControl = Session.getModelController(TextControl.class);
        var textTransformation = textControl.getTextTransformationByName(textTransformationName);

        if(textTransformation == null) {
            handleExecutionError(UnknownTextTransformationNameException.class, eea, ExecutionErrors.UnknownTextTransformationName.name(), textTransformationName);
        }

        return textTransformation;
    }
    
}
