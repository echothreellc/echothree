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

import com.echothree.control.user.subscription.common.form.CreateSubscriptionTypeForm;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
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
public class CreateSubscriptionTypeCommand
        extends BaseSimpleCommand<CreateSubscriptionTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SubscriptionKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SubscriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SubscriptionSequenceName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of CreateSubscriptionTypeCommand */
    public CreateSubscriptionTypeCommand() {
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
            
            if(subscriptionType == null) {
                var subscriptionSequenceName = form.getSubscriptionSequenceName();
                Sequence subscriptionSequence = null;
                
                if(subscriptionSequenceName != null) {
                    var sequenceControl = Session.getModelController(SequenceControl.class);
                    var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.SUBSCRIPTION.name());
                    subscriptionSequence = sequenceControl.getSequenceByName(sequenceType, subscriptionSequenceName);
                }
                
                if(subscriptionSequenceName == null || subscriptionSequence != null) {
                    var partyPK = getPartyPK();
                    var isDefault = Boolean.valueOf(form.getIsDefault());
                    var sortOrder = Integer.valueOf(form.getSortOrder());
                    var description = form.getDescription();
                    
                    subscriptionType = subscriptionControl.createSubscriptionType(subscriptionKind, subscriptionTypeName,
                            subscriptionSequence, isDefault, sortOrder, partyPK);
                    
                    if(description != null) {
                        subscriptionControl.createSubscriptionTypeDescription(subscriptionType, getPreferredLanguage(), description,
                                partyPK);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSubscriptionSequenceName.name(), subscriptionSequenceName);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateSubscriptionTypeName.name(), subscriptionTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateSubscriptionKindName.name(), subscriptionKindName);
        }
        
        return null;
    }
    
}
