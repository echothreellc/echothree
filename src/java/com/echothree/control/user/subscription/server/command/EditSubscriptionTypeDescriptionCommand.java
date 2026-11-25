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
import com.echothree.control.user.subscription.common.edit.SubscriptionTypeDescriptionEdit;
import com.echothree.control.user.subscription.common.result.EditSubscriptionTypeDescriptionResult;
import com.echothree.control.user.subscription.common.result.SubscriptionResultFactory;
import com.echothree.control.user.subscription.common.spec.SubscriptionTypeDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.control.subscription.server.logic.SubscriptionTypeLogic;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.model.data.subscription.server.entity.SubscriptionTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditSubscriptionTypeDescriptionCommand
        extends BaseAbstractEditCommand<SubscriptionTypeDescriptionSpec, SubscriptionTypeDescriptionEdit, EditSubscriptionTypeDescriptionResult, SubscriptionTypeDescription, SubscriptionType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.SubscriptionType.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SubscriptionKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SubscriptionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    SubscriptionControl subscriptionControl;

    @Inject
    LanguageLogic languageLogic;

    @Inject
    SubscriptionTypeLogic subscriptionTypeLogic;

    /** Creates a new instance of EditSubscriptionTypeDescriptionCommand */
    public EditSubscriptionTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSubscriptionTypeDescriptionResult getResult() {
        return SubscriptionResultFactory.getEditSubscriptionTypeDescriptionResult();
    }

    @Override
    public SubscriptionTypeDescriptionEdit getEdit() {
        return SubscriptionEditFactory.getSubscriptionTypeDescriptionEdit();
    }

    @Override
    public SubscriptionTypeDescription getEntity(EditSubscriptionTypeDescriptionResult result) {
        SubscriptionTypeDescription subscriptionTypeDescription = null;
        var subscriptionKindName = spec.getSubscriptionKindName();
        var subscriptionTypeName = spec.getSubscriptionTypeName();
        var languageIsoName = spec.getLanguageIsoName();
        var subscriptionType = subscriptionTypeLogic.getSubscriptionTypeByName(this, subscriptionKindName, subscriptionTypeName);
        var language = languageLogic.getLanguageByName(this, languageIsoName);

        if(!hasExecutionErrors()) {
            subscriptionTypeDescription = subscriptionControl.getSubscriptionTypeDescription(subscriptionType, language,
                    editModeToEntityPermission(editMode));

            if(subscriptionTypeDescription == null) {
                addExecutionError(ExecutionErrors.UnknownSubscriptionTypeDescription.name(), subscriptionKindName, subscriptionTypeName, languageIsoName);
            }
        }

        return subscriptionTypeDescription;
    }

    @Override
    public SubscriptionType getLockEntity(SubscriptionTypeDescription subscriptionTypeDescription) {
        return subscriptionTypeDescription.getSubscriptionType();
    }

    @Override
    public void fillInResult(EditSubscriptionTypeDescriptionResult result, SubscriptionTypeDescription subscriptionTypeDescription) {
        result.setSubscriptionTypeDescription(subscriptionControl.getSubscriptionTypeDescriptionTransfer(getUserVisit(), subscriptionTypeDescription));
    }

    @Override
    public void doLock(SubscriptionTypeDescriptionEdit edit, SubscriptionTypeDescription subscriptionTypeDescription) {
        edit.setDescription(subscriptionTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(SubscriptionTypeDescription subscriptionTypeDescription) {
        var subscriptionTypeDescriptionValue = subscriptionControl.getSubscriptionTypeDescriptionValue(subscriptionTypeDescription);

        subscriptionTypeDescriptionValue.setDescription(edit.getDescription());

        subscriptionControl.updateSubscriptionTypeDescriptionFromValue(subscriptionTypeDescriptionValue, getPartyPK());
    }

}
