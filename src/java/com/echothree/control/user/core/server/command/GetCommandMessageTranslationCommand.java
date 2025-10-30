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

import com.echothree.control.user.core.common.form.GetCommandMessageTranslationForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.model.control.core.server.control.CommandControl;
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
public class GetCommandMessageTranslationCommand
        extends BaseSimpleCommand<GetCommandMessageTranslationForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CommandMessageTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CommandMessageKey", FieldType.COMMAND_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetCommandMessageTranslationCommand */
    public GetCommandMessageTranslationCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var commandControl = Session.getModelController(CommandControl.class);
        var result = CoreResultFactory.getGetCommandMessageTranslationResult();
        var commandMessageTypeName = form.getCommandMessageTypeName();
        var commandMessageType = commandControl.getCommandMessageTypeByName(commandMessageTypeName);

        if(commandMessageType != null) {
            var commandMessageKey = form.getCommandMessageKey();
            var commandMessage = commandControl.getCommandMessageByKey(commandMessageType, commandMessageKey);

            if(commandMessage != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = form.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);

                if(language != null) {
                    var commandMessageTranslation = commandControl.getCommandMessageTranslation(commandMessage, language);

                    if(commandMessageTranslation != null) {
                        result.setCommandMessageTranslation(commandControl.getCommandMessageTranslationTransfer(getUserVisit(), commandMessageTranslation));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownCommandMessageTranslation.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCommandMessageKey.name(), commandMessageKey);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCommandMessageTypeName.name(), commandMessageTypeName);
        }

        return result;
    }
    
}
