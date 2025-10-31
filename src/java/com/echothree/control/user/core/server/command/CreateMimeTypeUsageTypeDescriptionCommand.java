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

import com.echothree.control.user.core.common.form.CreateMimeTypeUsageTypeDescriptionForm;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateMimeTypeUsageTypeDescriptionCommand
        extends BaseSimpleCommand<CreateMimeTypeUsageTypeDescriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("MimeTypeUsageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateMimeTypeUsageTypeDescriptionCommand */
    public CreateMimeTypeUsageTypeDescriptionCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
        var mimeTypeUsageTypeName = form.getMimeTypeUsageTypeName();
        var mimeTypeUsageType = mimeTypeControl.getMimeTypeUsageTypeByName(mimeTypeUsageTypeName);
        
        if(mimeTypeUsageType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = form.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                var mimeTypeUsageTypeDescription = mimeTypeControl.getMimeTypeUsageTypeDescription(mimeTypeUsageType, language);

                if(mimeTypeUsageTypeDescription == null) {
                    var description = form.getDescription();

                    mimeTypeControl.createMimeTypeUsageTypeDescription(mimeTypeUsageType, language, description);
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
