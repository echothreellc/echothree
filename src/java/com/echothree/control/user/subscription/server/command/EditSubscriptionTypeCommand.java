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

package com.echothree.control.user.subscription.server.command;

import com.echothree.control.user.subscription.common.edit.SubscriptionEditFactory;
import com.echothree.control.user.subscription.common.edit.SubscriptionTypeEdit;
import com.echothree.control.user.subscription.common.form.EditSubscriptionTypeForm;
import com.echothree.control.user.subscription.common.result.SubscriptionResultFactory;
import com.echothree.control.user.subscription.common.spec.SubscriptionTypeSpec;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.sequence.server.entity.Sequence;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditSubscriptionTypeCommand
        extends BaseEditCommand<SubscriptionTypeSpec, SubscriptionTypeEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SubscriptionKindName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SubscriptionTypeName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("SubscriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SubscriptionSequenceName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of EditSubscriptionTypeCommand */
    public EditSubscriptionTypeCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var subscriptionControl = Session.getModelController(SubscriptionControl.class);
        var result = SubscriptionResultFactory.getEditSubscriptionTypeResult();
        var subscriptionKindName = spec.getSubscriptionKindName();
        var subscriptionKind = subscriptionControl.getSubscriptionKindByName(subscriptionKindName);
        
        if(subscriptionKind != null) {
            if(editMode.equals(EditMode.LOCK)) {
                var subscriptionTypeName = spec.getSubscriptionTypeName();
                var subscriptionType = subscriptionControl.getSubscriptionTypeByName(subscriptionKind, subscriptionTypeName);
                
                if(subscriptionType != null) {
                    result.setSubscriptionType(subscriptionControl.getSubscriptionTypeTransfer(getUserVisit(), subscriptionType));
                    
                    if(lockEntity(subscriptionType)) {
                        var subscriptionTypeDescription = subscriptionControl.getSubscriptionTypeDescription(subscriptionType, getPreferredLanguage());
                        var edit = SubscriptionEditFactory.getSubscriptionTypeEdit();
                        var subscriptionTypeDetail = subscriptionType.getLastDetail();
                        var subscriptionSequence = subscriptionTypeDetail.getSubscriptionSequence();
                        
                        result.setEdit(edit);
                        edit.setSubscriptionTypeName(subscriptionTypeDetail.getSubscriptionTypeName());
                        edit.setSubscriptionSequenceName(subscriptionSequence == null? null: subscriptionSequence.getLastDetail().getSequenceName());
                        edit.setIsDefault(subscriptionTypeDetail.getIsDefault().toString());
                        edit.setSortOrder(subscriptionTypeDetail.getSortOrder().toString());
                        
                        if(subscriptionTypeDescription != null) {
                            edit.setDescription(subscriptionTypeDescription.getDescription());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockFailed.name());
                    }
                    
                    result.setEntityLock(getEntityLockTransfer(subscriptionType));
                } else {
                    addExecutionError(ExecutionErrors.UnknownSubscriptionTypeName.name(), subscriptionTypeName);
                }
            } else if(editMode.equals(EditMode.UPDATE)) {
                var subscriptionTypeName = spec.getSubscriptionTypeName();
                var subscriptionType = subscriptionControl.getSubscriptionTypeByNameForUpdate(subscriptionKind, subscriptionTypeName);
                
                if(subscriptionType != null) {
                    subscriptionTypeName = edit.getSubscriptionTypeName();
                    var duplicateSubscriptionType = subscriptionControl.getSubscriptionTypeByName(subscriptionKind, subscriptionTypeName);
                    
                    if(duplicateSubscriptionType == null || subscriptionType.equals(duplicateSubscriptionType)) {
                        var subscriptionSequenceName = edit.getSubscriptionSequenceName();
                        Sequence subscriptionSequence = null;
                        
                        if(subscriptionSequenceName != null) {
                            var sequenceControl = Session.getModelController(SequenceControl.class);
                            var sequenceType = sequenceControl.getSequenceTypeByName(SequenceTypes.SUBSCRIPTION.name());
                            subscriptionSequence = sequenceControl.getSequenceByName(sequenceType, subscriptionSequenceName);
                        }
                        
                        if(subscriptionSequenceName == null || subscriptionSequence != null) {
                            if(lockEntityForUpdate(subscriptionType)) {
                                try {
                                    var partyPK = getPartyPK();
                                    var subscriptionTypeDetailValue = subscriptionControl.getSubscriptionTypeDetailValueForUpdate(subscriptionType);
                                    var subscriptionTypeDescription = subscriptionControl.getSubscriptionTypeDescriptionForUpdate(subscriptionType, getPreferredLanguage());
                                    var description = edit.getDescription();
                                    
                                    subscriptionTypeDetailValue.setSubscriptionTypeName(edit.getSubscriptionTypeName());
                                    subscriptionTypeDetailValue.setSubscriptionSequencePK(subscriptionSequence == null? null: subscriptionSequence.getPrimaryKey());
                                    subscriptionTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                                    subscriptionTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                                    
                                    subscriptionControl.updateSubscriptionTypeFromValue(subscriptionTypeDetailValue, partyPK);
                                    
                                    if(subscriptionTypeDescription == null && description != null) {
                                        subscriptionControl.createSubscriptionTypeDescription(subscriptionType, getPreferredLanguage(), description, partyPK);
                                    } else if(subscriptionTypeDescription != null && description == null) {
                                        subscriptionControl.deleteSubscriptionTypeDescription(subscriptionTypeDescription, partyPK);
                                    } else if(subscriptionTypeDescription != null && description != null) {
                                        var subscriptionTypeDescriptionValue = subscriptionControl.getSubscriptionTypeDescriptionValue(subscriptionTypeDescription);
                                        
                                        subscriptionTypeDescriptionValue.setDescription(description);
                                        subscriptionControl.updateSubscriptionTypeDescriptionFromValue(subscriptionTypeDescriptionValue, partyPK);
                                    }
                                } finally {
                                    unlockEntity(subscriptionType);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownSubscriptionSequenceName.name(), subscriptionSequenceName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.DuplicateSubscriptionTypeName.name(), subscriptionTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownSubscriptionTypeName.name(), subscriptionTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSubscriptionKindName.name(), subscriptionKindName);
        }
        
        return result;
    }
    
}
