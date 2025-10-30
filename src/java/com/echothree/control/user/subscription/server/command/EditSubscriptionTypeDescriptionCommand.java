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

import com.echothree.control.user.subscription.common.edit.SubscriptionEditFactory;
import com.echothree.control.user.subscription.common.edit.SubscriptionTypeDescriptionEdit;
import com.echothree.control.user.subscription.common.form.EditSubscriptionTypeDescriptionForm;
import com.echothree.control.user.subscription.common.result.SubscriptionResultFactory;
import com.echothree.control.user.subscription.common.spec.SubscriptionTypeDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditSubscriptionTypeDescriptionCommand
        extends BaseEditCommand<SubscriptionTypeDescriptionSpec, SubscriptionTypeDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SubscriptionKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SubscriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of EditSubscriptionTypeDescriptionCommand */
    public EditSubscriptionTypeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var subscriptionControl = Session.getModelController(SubscriptionControl.class);
        var result = SubscriptionResultFactory.getEditSubscriptionTypeDescriptionResult();
        var subscriptionKindName = spec.getSubscriptionKindName();
        var subscriptionKind = subscriptionControl.getSubscriptionKindByName(subscriptionKindName);
        
        if(subscriptionKind != null) {
            var subscriptionTypeName = spec.getSubscriptionTypeName();
            var subscriptionType = subscriptionControl.getSubscriptionTypeByName(subscriptionKind, subscriptionTypeName);
            
            if(subscriptionType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        var subscriptionTypeDescription = subscriptionControl.getSubscriptionTypeDescription(subscriptionType, language);
                        
                        if(subscriptionTypeDescription != null) {
                            result.setSubscriptionTypeDescription(subscriptionControl.getSubscriptionTypeDescriptionTransfer(getUserVisit(), subscriptionTypeDescription));
                            
                            if(lockEntity(subscriptionType)) {
                                var edit = SubscriptionEditFactory.getSubscriptionTypeDescriptionEdit();
                                
                                result.setEdit(edit);
                                edit.setDescription(subscriptionTypeDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(subscriptionType));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSubscriptionTypeDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        var subscriptionTypeDescriptionValue = subscriptionControl.getSubscriptionTypeDescriptionValueForUpdate(subscriptionType, language);
                        
                        if(subscriptionTypeDescriptionValue != null) {
                            if(lockEntityForUpdate(subscriptionType)) {
                                try {
                                    var description = edit.getDescription();
                                    
                                    subscriptionTypeDescriptionValue.setDescription(description);
                                    
                                    subscriptionControl.updateSubscriptionTypeDescriptionFromValue(subscriptionTypeDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(subscriptionType);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSubscriptionTypeDescription.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownSubscriptionTypeName.name(), subscriptionTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSubscriptionKindName.name(), subscriptionKindName);
        }
        
        return result;
    }
    
}
