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

package com.echothree.control.user.content.server.command;

import com.echothree.control.user.content.common.form.GetContentPageAreaTypeForm;
import com.echothree.control.user.content.common.result.ContentResultFactory;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.content.server.logic.ContentPageAreaTypeLogic;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.content.server.entity.ContentPageAreaType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetContentPageAreaTypeCommand
        extends BaseSingleEntityCommand<ContentPageAreaType, GetContentPageAreaTypeForm> {
    
    // No COMMAND_SECURITY_DEFINITION, anyone may execute this command.
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ContentPageAreaTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
                ));
    }
    
    /** Creates a new instance of GetContentPageAreaTypeCommand */
    public GetContentPageAreaTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected ContentPageAreaType getEntity() {
        var contentPageAreaType = ContentPageAreaTypeLogic.getInstance().getContentPageAreaTypeByUniversalSpec(this, form);

        if(contentPageAreaType != null) {
            sendEvent(contentPageAreaType.getPrimaryKey(), EventTypes.READ, null, null, getPartyPK());
        }

        return contentPageAreaType;
    }
    
    @Override
    protected BaseResult getResult(ContentPageAreaType contentPageAreaType) {
        var contentControl = Session.getModelController(ContentControl.class);
        var result = ContentResultFactory.getGetContentPageAreaTypeResult();

        if(contentPageAreaType != null) {
            result.setContentPageAreaType(contentControl.getContentPageAreaTypeTransfer(getUserVisit(), contentPageAreaType));
        }

        return result;
    }
    
}
