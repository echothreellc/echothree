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

import com.echothree.control.user.subscription.common.form.CreateSubscriptionTypeChainForm;
import com.echothree.model.control.chain.common.ChainConstants;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
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

public class CreateSubscriptionTypeChainCommand
        extends BaseSimpleCommand<CreateSubscriptionTypeChainForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SubscriptionKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SubscriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ChainName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("RemainingTime", FieldType.UNSIGNED_LONG, false, null, null)
        ));
    }
    
    /** Creates a new instance of CreateSubscriptionTypeChainCommand */
    public CreateSubscriptionTypeChainCommand() {
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
                        var subscriptionTypeChain = subscriptionControl.getSubscriptionTypeChain(subscriptionType, chain);
                        
                        if(subscriptionTypeChain == null) {
                            var uomControl = Session.getModelController(UomControl.class);
                            var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                            UnitOfMeasureType unitOfMeasureType = null;
                            
                            if(unitOfMeasureTypeName != null) {
                                var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
                                
                                unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                            }
                            
                            if(unitOfMeasureTypeName == null || unitOfMeasureType != null) {
                                var strRemainingTime = form.getRemainingTime();
                                var remainingTime = strRemainingTime == null? null: Long.valueOf(strRemainingTime);
                                
                                if(unitOfMeasureTypeName == null || remainingTime != null) {
                                    Conversion conversion = null;
                                    
                                    if(remainingTime != null) {
                                        conversion = new Conversion(uomControl, unitOfMeasureType, remainingTime).convertToLowestUnitOfMeasureType();
                                    }
                                    
                                    subscriptionControl.createSubscriptionTypeChain( subscriptionType, chain,
                                            conversion == null? null: conversion.getQuantity(), getPartyPK());
                                } else {
                                    addExecutionError(ExecutionErrors.MissingRemainingTime.name());
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.DuplicateSubscriptionTypeChain.name());
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
