// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.core.common.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class CommandMessageTranslationTransfer
        extends BaseTransfer {
    
    private CommandMessageTransfer commandMessage;
    private LanguageTransfer language;
    private String translation;
    
    /** Creates a new instance of CommandMessageTranslationTransfer */
    public CommandMessageTranslationTransfer(CommandMessageTransfer commandMessage, LanguageTransfer language, String translation) {
        this.commandMessage = commandMessage;
        this.language = language;
        this.translation = translation;
    }

    /**
     * @return the commandMessage
     */
    public CommandMessageTransfer getCommandMessage() {
        return commandMessage;
    }

    /**
     * @param commandMessage the commandMessage to set
     */
    public void setCommandMessage(CommandMessageTransfer commandMessage) {
        this.commandMessage = commandMessage;
    }

    /**
     * @return the language
     */
    public LanguageTransfer getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }

    /**
     * @return the translation
     */
    public String getTranslation() {
        return translation;
    }

    /**
     * @param translation the translation to set
     */
    public void setTranslation(String translation) {
        this.translation = translation;
    }

}
