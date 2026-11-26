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
import com.echothree.control.user.subscription.common.result.EditSubscriptionTypeResult;
import com.echothree.control.user.subscription.common.result.SubscriptionResultFactory;
import com.echothree.control.user.subscription.common.spec.SubscriptionTypeUniversalSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceLogic;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.control.subscription.server.logic.SubscriptionTypeLogic;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditSubscriptionTypeCommand
        extends BaseEditCommand<SubscriptionTypeUniversalSpec, SubscriptionTypeEdit, EditSubscriptionTypeResult, SubscriptionType, SubscriptionType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.SubscriptionType.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SubscriptionKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SubscriptionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SubscriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SubscriptionSequenceName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }

    private Sequence subscriptionSequence;

    @Inject
    SubscriptionControl subscriptionControl;

    @Inject
    SequenceLogic sequenceLogic;

    @Inject
    SubscriptionTypeLogic subscriptionTypeLogic;

    /** Creates a new instance of EditSubscriptionTypeCommand */
    public EditSubscriptionTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSubscriptionTypeResult getResult() {
        return SubscriptionResultFactory.getEditSubscriptionTypeResult();
    }

    @Override
    public SubscriptionTypeEdit getEdit() {
        return SubscriptionEditFactory.getSubscriptionTypeEdit();
    }

    @Override
    public SubscriptionType getEntity(EditSubscriptionTypeResult result) {
        return subscriptionTypeLogic.getSubscriptionTypeByUniversalSpec(this, spec, false, editModeToEntityPermission(editMode));
    }

    @Override
    public SubscriptionType getLockEntity(SubscriptionType subscriptionType) {
        return subscriptionType;
    }

    @Override
    public void fillInResult(EditSubscriptionTypeResult result, SubscriptionType subscriptionType) {
        result.setSubscriptionType(subscriptionControl.getSubscriptionTypeTransfer(getUserVisit(), subscriptionType));
    }

    @Override
    public void doLock(SubscriptionTypeEdit edit, SubscriptionType subscriptionType) {
        var subscriptionTypeDescription = subscriptionControl.getSubscriptionTypeDescription(subscriptionType, getPreferredLanguage());
        var subscriptionTypeDetail = subscriptionType.getLastDetail();
        var subscriptionSequence = subscriptionTypeDetail.getSubscriptionSequence();

        edit.setSubscriptionTypeName(subscriptionTypeDetail.getSubscriptionTypeName());
        edit.setSubscriptionSequenceName(subscriptionSequence == null? null: subscriptionSequence.getLastDetail().getSequenceName());
        edit.setIsDefault(subscriptionTypeDetail.getIsDefault().toString());
        edit.setSortOrder(subscriptionTypeDetail.getSortOrder().toString());

        if(subscriptionTypeDescription != null) {
            edit.setDescription(subscriptionTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(SubscriptionType subscriptionType) {
        var subscriptionKind = subscriptionType.getLastDetail().getSubscriptionKind();
        var subscriptionTypeName = edit.getSubscriptionTypeName();
        var duplicateSubscriptionType = subscriptionControl.getSubscriptionTypeByName(subscriptionKind, subscriptionTypeName);
        var subscriptionSequenceName = edit.getSubscriptionSequenceName();

        if(duplicateSubscriptionType != null && !subscriptionType.equals(duplicateSubscriptionType)) {
            addExecutionError(ExecutionErrors.DuplicateSubscriptionTypeName.name(),
                    subscriptionKind.getLastDetail().getSubscriptionKindName(), subscriptionTypeName);
        }

        subscriptionSequence = subscriptionSequenceName == null ? null : sequenceLogic.getSequenceByName(this,
                SequenceTypes.SUBSCRIPTION.name(), subscriptionSequenceName);
    }

    @Override
    public void doUpdate(SubscriptionType subscriptionType) {
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
    }

}
