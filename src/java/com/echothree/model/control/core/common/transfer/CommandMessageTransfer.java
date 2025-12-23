// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.MapWrapper;

public class CommandMessageTransfer
        extends BaseTransfer {
    
    private CommandMessageTypeTransfer commandMessageType;
    private String commandMessageKey;
    private String translation;
    
    private MapWrapper<CommandMessageTranslationTransfer> commandMessageTranslations;
    
    /** Creates a new instance of CommandMessageTransfer */
    public CommandMessageTransfer(CommandMessageTypeTransfer commandMessageType, String commandMessageKey, String translation) {
        this.commandMessageType = commandMessageType;
        this.commandMessageKey = commandMessageKey;
        this.translation = translation;
    }

    /**
     * Returns the commandMessageType.
     * @return the commandMessageType
     */
    public CommandMessageTypeTransfer getCommandMessageType() {
        return commandMessageType;
    }

    /**
     * Sets the commandMessageType.
     * @param commandMessageType the commandMessageType to set
     */
    public void setCommandMessageType(CommandMessageTypeTransfer commandMessageType) {
        this.commandMessageType = commandMessageType;
    }

    /**
     * Returns the commandMessageKey.
     * @return the commandMessageKey
     */
    public String getCommandMessageKey() {
        return commandMessageKey;
    }

    /**
     * Sets the commandMessageKey.
     * @param commandMessageKey the commandMessageKey to set
     */
    public void setCommandMessageKey(String commandMessageKey) {
        this.commandMessageKey = commandMessageKey;
    }

    /**
     * Returns the translation.
     * @return the translation
     */
    public String getTranslation() {
        return translation;
    }

    /**
     * Sets the translation.
     * @param translation the translation to set
     */
    public void setTranslation(String translation) {
        this.translation = translation;
    }

    /**
     * Returns the commandMessageTranslations.
     * @return the commandMessageTranslations
     */
    public MapWrapper<CommandMessageTranslationTransfer> getCommandMessageTranslations() {
        return commandMessageTranslations;
    }

    /**
     * Sets the commandMessageTranslations.
     * @param commandMessageTranslations the commandMessageTranslations to set
     */
    public void setCommandMessageTranslations(MapWrapper<CommandMessageTranslationTransfer> commandMessageTranslations) {
        this.commandMessageTranslations = commandMessageTranslations;
    }
    
}
