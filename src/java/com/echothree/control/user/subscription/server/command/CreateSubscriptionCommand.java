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

import com.echothree.control.user.subscription.common.form.CreateSubscriptionForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.control.subscription.server.logic.SubscriptionLogic;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateSubscriptionCommand
        extends BaseSimpleCommand<CreateSubscriptionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SubscriptionKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SubscriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("UnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("SubscriptionTime", FieldType.UNSIGNED_LONG, false, null, null)
        ));
    }
    
    /** Creates a new instance of CreateSubscriptionCommand */
    public CreateSubscriptionCommand() {
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
                var partyControl = Session.getModelController(PartyControl.class);
                var partyName = form.getPartyName();
                var party = partyControl.getPartyByName(partyName);
                
                if(party != null) {
                    var subscription = subscriptionControl.getSubscription(subscriptionType, party);
                    
                    if(subscription == null) {
                        var uomControl = Session.getModelController(UomControl.class);
                        var unitOfMeasureTypeName = form.getUnitOfMeasureTypeName();
                        UnitOfMeasureType unitOfMeasureType = null;
                        
                        if(unitOfMeasureTypeName != null) {
                            var unitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
                            
                            unitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(unitOfMeasureKind, unitOfMeasureTypeName);
                        }
                        
                        if(unitOfMeasureTypeName == null || unitOfMeasureType != null) {
                            var strSubscriptionTime = form.getSubscriptionTime();
                            var subscriptionTime = strSubscriptionTime == null? null: Long.valueOf(strSubscriptionTime);
                            
                            if(unitOfMeasureTypeName == null || subscriptionTime != null) {
                                Long endTime = null;
                                var createdBy = getPartyPK();
                                
                                if(subscriptionTime != null) {
                                    var conversion = new Conversion(uomControl, unitOfMeasureType, subscriptionTime).convertToLowestUnitOfMeasureType();
                                    endTime = session.START_TIME + conversion.getQuantity();
                                }
                                
                                // ExecutionErrorAccumulator is passed in as null so that an Exception will be thrown if there is an error.
                                SubscriptionLogic.getInstance().createSubscription(null, session, subscriptionType, party, endTime, createdBy);
                            } else {
                                addExecutionError(ExecutionErrors.MissingSubscriptionTime.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownUnitOfMeasureTypeName.name(), unitOfMeasureTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateSubscription.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
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
