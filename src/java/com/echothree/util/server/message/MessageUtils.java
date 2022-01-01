// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
// Copyright 1999-2005 The Apache Software Foundation.
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

package com.echothree.util.server.message;

import com.echothree.model.control.core.common.exception.UnknownCommandMessageTypeNameException;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.CommandMessage;
import com.echothree.model.data.core.server.entity.CommandMessageTranslation;
import com.echothree.model.data.core.server.entity.CommandMessageType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.persistence.Session;
import java.text.MessageFormat;
import java.util.Iterator;

public class MessageUtils {
    
    private static final MessageUtils instance = new MessageUtils();
    
    protected MessageUtils() {
        super();
    }
    
    public static MessageUtils getInstance() {
        return instance;
    }
    
    /**
     * Escape any single quote characters that are included in the specified
     * message string.
     *
     * @param string The string to be escaped
     */
    protected String escape(String string) {
        if((string == null) || (string.indexOf('\'') < 0)) {
            return string;
        }
        
        int n = string.length();
        StringBuilder sb = new StringBuilder(n);
        
        for (int i = 0; i < n; i++) {
            char ch = string.charAt(i);
            
            if (ch == '\'') {
                sb.append('\'');
            }
            
            sb.append(ch);
        }
        
        return sb.toString();
    }
    
    /**
     * Returns a text message after parametric replacement of the specified
     * parameter placeholders.  A null string result will be returned by this
     * method if no resource bundle has been configured.
     */
    protected void fillInMessage(CoreControl coreControl, Language language, CommandMessageType commandMessageType, Message message) {
        String key = message.getKey();
        CommandMessage commandMessage = coreControl.getCommandMessageByKey(commandMessageType, key);
        String translation = null;
        
        if(commandMessage != null) {
            CommandMessageTranslation commandMessageTranslation = coreControl.getBestCommandMessageTranslation(commandMessage, language);

            if(commandMessageTranslation != null) {
                translation = commandMessageTranslation.getTranslation();

                if(translation != null) {
                    translation = new MessageFormat(escape(translation)).format(message.getValues());
                }
            }
        }
        
        message.setMessage(translation == null ? new StringBuilder("??").append(key).append("??").toString() : translation);
    }
    
    public void fillInMessages(Language language, String commandMessageTypeName, Messages messages) {
        if(messages != null) {
            var coreControl = Session.getModelController(CoreControl.class);
            CommandMessageType commandMessageType = coreControl.getCommandMessageTypeByName(commandMessageTypeName);
            
            if(commandMessageType != null) {
                Iterator<Message> iter = messages.get();
                
                while(iter.hasNext()) {
                    fillInMessage(coreControl, language, commandMessageType, iter.next());
                }
            }
        }
    }

    // The language used in the Exceptions will always be the default Language (typically English).
    public String getExceptionMessage(String commandMessageTypeName, Message message) {
        var coreControl = Session.getModelController(CoreControl.class);
        CommandMessageType commandMessageType = coreControl.getCommandMessageTypeByName(commandMessageTypeName);
        
        if(commandMessageType == null) {
            throw new UnknownCommandMessageTypeNameException(new Message(ExecutionErrors.UnknownCommandMessageTypeName.name(), commandMessageTypeName));
        } else {
            var partyControl = Session.getModelController(PartyControl.class);
            Language language = partyControl.getDefaultLanguage();
            
            fillInMessage(coreControl, language, commandMessageType, message);
        }

        return message.getMessage();
    }
    
}
