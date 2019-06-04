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

package com.echothree.control.user.scale.server.command;

import com.echothree.control.user.scale.common.form.CreateScaleUseTypeForm;
import com.echothree.model.control.scale.server.ScaleControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.scale.server.entity.ScaleUseType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateScaleUseTypeCommand
        extends BaseSimpleCommand<CreateScaleUseTypeForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ScaleUseTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of CreateScaleUseTypeCommand */
    public CreateScaleUseTypeCommand(UserVisitPK userVisitPK, CreateScaleUseTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
   @Override
    protected BaseResult execute() {
        var scaleControl = (ScaleControl)Session.getModelController(ScaleControl.class);
        String scaleUseTypeName = form.getScaleUseTypeName();
        ScaleUseType scaleUseType = scaleControl.getScaleUseTypeByName(scaleUseTypeName);
        
        if(scaleUseType == null) {
            Boolean isDefault = Boolean.valueOf(form.getIsDefault());
            Integer sortOrder = Integer.valueOf(form.getSortOrder());
            String description = form.getDescription();
            PartyPK createdBy = getPartyPK();

            scaleUseType = scaleControl.createScaleUseType(scaleUseTypeName, isDefault, sortOrder, createdBy);

            if(description != null) {
                Language language = getPreferredLanguage();

                scaleControl.createScaleUseTypeDescription(scaleUseType, language, description, createdBy);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateScaleUseTypeName.name(), scaleUseTypeName);
        }
        
        return null;
    }
    
}
