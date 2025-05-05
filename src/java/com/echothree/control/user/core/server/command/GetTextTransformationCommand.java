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

import com.echothree.control.user.core.common.form.GetTextTransformationForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.TextControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.core.server.logic.TextLogic;
import com.echothree.model.data.core.server.entity.TextTransformation;
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

public class GetTextTransformationCommand
        extends BaseSingleEntityCommand<TextTransformation, GetTextTransformationForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TextTransformationName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        ));
    }
    
    /** Creates a new instance of GetTextTransformationCommand */
    public GetTextTransformationCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected TextTransformation getEntity() {
        TextTransformation textTransformation = null;
        var textTransformationName = form.getTextTransformationName();
        var parameterCount = (textTransformationName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(form);

        if(parameterCount == 1) {
            if(textTransformationName == null) {
                var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(this, form,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.TextTransformation.name());

                if(!hasExecutionErrors()) {
                    var textControl = Session.getModelController(TextControl.class);

                    textTransformation = textControl.getTextTransformationByEntityInstance(entityInstance);
                }
            } else {
                textTransformation = TextLogic.getInstance().getTextTransformationByName(this, textTransformationName);
            }

            if(textTransformation != null) {
                sendEvent(textTransformation.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return textTransformation;
    }
    
    @Override
    protected BaseResult getResult(TextTransformation textTransformation) {
        var result = CoreResultFactory.getGetTextTransformationResult();

        if(textTransformation != null) {
            var textControl = Session.getModelController(TextControl.class);

            result.setTextTransformation(textControl.getTextTransformationTransfer(getUserVisit(), textTransformation));
        }

        return result;
    }
    
}
