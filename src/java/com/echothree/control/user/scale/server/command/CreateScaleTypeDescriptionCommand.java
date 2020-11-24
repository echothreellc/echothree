// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.control.user.scale.common.form.CreateScaleTypeDescriptionForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.scale.server.entity.ScaleType;
import com.echothree.model.data.scale.server.entity.ScaleTypeDescription;
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

public class CreateScaleTypeDescriptionCommand
        extends BaseSimpleCommand<CreateScaleTypeDescriptionForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ScaleTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }

    /** Creates a new instance of CreateScaleTypeDescriptionCommand */
    public CreateScaleTypeDescriptionCommand(UserVisitPK userVisitPK, CreateScaleTypeDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
   @Override
    protected BaseResult execute() {
        var scaleControl = Session.getModelController(ScaleControl.class);
        String scaleTypeName = form.getScaleTypeName();
        ScaleType scaleType = scaleControl.getScaleTypeByName(scaleTypeName);
        
        if(scaleType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                ScaleTypeDescription scaleDescription = scaleControl.getScaleTypeDescription(scaleType, language);
                
                if(scaleDescription == null) {
                    var description = form.getDescription();
                    
                    scaleControl.createScaleTypeDescription(scaleType, language, description, getPartyPK());
                } else {
                    addExecutionError(ExecutionErrors.DuplicateScaleTypeDescription.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownScaleTypeName.name(), scaleTypeName);
        }
        
        return null;
    }
    
}
