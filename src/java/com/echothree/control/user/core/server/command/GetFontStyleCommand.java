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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.GetFontStyleForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.FontControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.FontLogic;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetFontStyleCommand
        extends BaseSingleEntityCommand<FontStyle, GetFontStyleForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("FontStyleName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetFontStyleCommand */
    public GetFontStyleCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected FontStyle getEntity() {
        FontStyle fontStyle = null;
        var fontStyleName = form.getFontStyleName();
        var parameterCount = (fontStyleName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            if(fontStyleName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.FontStyle.name());

                if(!hasExecutionErrors()) {
                    var fontControl = Session.getModelController(FontControl.class);

                    fontStyle = fontControl.getFontStyleByEntityInstance(entityInstance);
                }
            } else {
                fontStyle = FontLogic.getInstance().getFontStyleByName(this, fontStyleName);
            }

            if(fontStyle != null) {
                sendEvent(fontStyle.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return fontStyle;
    }
    
    @Override
    protected BaseResult getResult(FontStyle fontStyle) {
        var result = CoreResultFactory.getGetFontStyleResult();

        if(fontStyle != null) {
            var fontControl = Session.getModelController(FontControl.class);

            result.setFontStyle(fontControl.getFontStyleTransfer(getUserVisit(), fontStyle));
        }

        return result;
    }
    
}
