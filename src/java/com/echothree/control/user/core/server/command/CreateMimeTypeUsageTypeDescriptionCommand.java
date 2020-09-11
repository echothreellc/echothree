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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.CreateMimeTypeUsageTypeDescriptionForm;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageTypeDescription;
import com.echothree.model.data.party.server.entity.Language;
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

public class CreateMimeTypeUsageTypeDescriptionCommand
        extends BaseSimpleCommand<CreateMimeTypeUsageTypeDescriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MimeTypeUsageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateMimeTypeUsageTypeDescriptionCommand */
    public CreateMimeTypeUsageTypeDescriptionCommand(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeDescriptionForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
      var coreControl = getCoreControl();
        String mimeTypeUsageTypeName = form.getMimeTypeUsageTypeName();
        MimeTypeUsageType mimeTypeUsageType = coreControl.getMimeTypeUsageTypeByName(mimeTypeUsageTypeName);
        
        if(mimeTypeUsageType != null) {
            var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = form.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                MimeTypeUsageTypeDescription mimeTypeUsageTypeDescription = coreControl.getMimeTypeUsageTypeDescription(mimeTypeUsageType, language);

                if(mimeTypeUsageTypeDescription == null) {
                    var description = form.getDescription();

                    coreControl.createMimeTypeUsageTypeDescription(mimeTypeUsageType, language, description);
                } else {
                    addExecutionError(ExecutionErrors.DuplicateMimeTypeUsageTypeDescription.name(), mimeTypeUsageTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownMimeTypeUsageTypeName.name(), mimeTypeUsageTypeName);
        }
        
        return null;
    }
    
}
