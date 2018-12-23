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

package com.echothree.control.user.chain.server.command;


import com.echothree.control.user.chain.common.form.CreateChainActionTypeUseForm;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.data.chain.server.entity.ChainActionType;
import com.echothree.model.data.chain.server.entity.ChainActionTypeUse;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateChainActionTypeUseCommand
        extends BaseSimpleCommand<CreateChainActionTypeUseForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null)
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ChainActionTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null)
        ));
    }
    
    /** Creates a new instance of CreateChainActionTypeUseCommand */
    public CreateChainActionTypeUseCommand(UserVisitPK userVisitPK, CreateChainActionTypeUseForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);
        String chainKindName = form.getChainKindName();
        ChainKind chainKind = chainControl.getChainKindByName(chainKindName);
        
        if(chainKind != null) {
            String chainActionTypeName = form.getChainActionTypeName();
            ChainActionType chainActionType = chainControl.getChainActionTypeByName(chainActionTypeName);
            
            if(chainActionType != null) {
                ChainActionTypeUse chainActionTypeUse = chainControl.getChainActionTypeUse(chainKind, chainActionType);
                
                if(chainActionTypeUse == null) {
                    Boolean isDefault = Boolean.valueOf(form.getIsDefault());
                    
                    chainControl.createChainActionTypeUse(chainKind, chainActionType, isDefault);
                } else {
                    addExecutionError(ExecutionErrors.DuplicateChainActionTypeUse.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownChainActionTypeName.name(), chainActionTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }
        
        return null;
    }
    
}
