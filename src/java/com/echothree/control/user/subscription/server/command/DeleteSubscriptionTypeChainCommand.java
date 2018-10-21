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

package com.echothree.control.user.subscription.server.command;

import com.echothree.control.user.subscription.remote.form.DeleteSubscriptionTypeChainForm;
import com.echothree.model.control.chain.common.ChainConstants;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.subscription.server.SubscriptionControl;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainKind;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.subscription.server.entity.SubscriptionKind;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.model.data.subscription.server.entity.SubscriptionTypeChain;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DeleteSubscriptionTypeChainCommand
        extends BaseSimpleCommand<DeleteSubscriptionTypeChainForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SubscriptionKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SubscriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ChainName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of DeleteSubscriptionTypeChainCommand */
    public DeleteSubscriptionTypeChainCommand(UserVisitPK userVisitPK, DeleteSubscriptionTypeChainForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        SubscriptionControl subscriptionControl = (SubscriptionControl)Session.getModelController(SubscriptionControl.class);
        String subscriptionKindName = form.getSubscriptionKindName();
        SubscriptionKind subscriptionKind = subscriptionControl.getSubscriptionKindByName(subscriptionKindName);
        
        if(subscriptionKind != null) {
            String subscriptionTypeName = form.getSubscriptionTypeName();
            SubscriptionType subscriptionType = subscriptionControl.getSubscriptionTypeByName(subscriptionKind, subscriptionTypeName);
            
            if(subscriptionType != null) {
                ChainControl chainControl = (ChainControl)Session.getModelController(ChainControl.class);
                ChainKind chainKind = chainControl.getChainKindByName(ChainConstants.ChainKind_SUBSCRIPTION);
                String chainTypeName = form.getChainTypeName();
                ChainType chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);
                
                if(chainType != null) {
                    String chainName = form.getChainName();
                    Chain chain = chainControl.getChainByName(chainType, chainName);
                    
                    if(chain != null) {
                        SubscriptionTypeChain subscriptionTypeChain = subscriptionControl.getSubscriptionTypeChainForUpdate(subscriptionType,
                                chain);
                        
                        if(subscriptionTypeChain != null) {
                            subscriptionControl.deleteSubscriptionTypeChain(subscriptionTypeChain, getPartyPK());
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSubscriptionTypeChain.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownChainName.name(), chainName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSubscriptionTypeName.name(), subscriptionTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSubscriptionKindName.name(), subscriptionKindName);
        }
        
        return null;
    }
    
}
