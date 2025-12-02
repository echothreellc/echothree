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

package com.echothree.control.user.subscription.server.command;

import com.echothree.control.user.subscription.common.form.DeleteSubscriptionTypeChainForm;
import com.echothree.model.control.chain.common.ChainConstants;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
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
import javax.enterprise.context.Dependent;

@Dependent
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
    public DeleteSubscriptionTypeChainCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var subscriptionControl = Session.getModelController(SubscriptionControl.class);
        var subscriptionKindName = form.getSubscriptionKindName();
        var subscriptionKind = subscriptionControl.getSubscriptionKindByName(subscriptionKindName);
        
        if(subscriptionKind != null) {
            var subscriptionTypeName = form.getSubscriptionTypeName();
            var subscriptionType = subscriptionControl.getSubscriptionTypeByName(subscriptionKind, subscriptionTypeName);
            
            if(subscriptionType != null) {
                var chainControl = Session.getModelController(ChainControl.class);
                var chainKind = chainControl.getChainKindByName(ChainConstants.ChainKind_SUBSCRIPTION);
                var chainTypeName = form.getChainTypeName();
                var chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);
                
                if(chainType != null) {
                    var chainName = form.getChainName();
                    var chain = chainControl.getChainByName(chainType, chainName);
                    
                    if(chain != null) {
                        var subscriptionTypeChain = subscriptionControl.getSubscriptionTypeChainForUpdate(subscriptionType,
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
