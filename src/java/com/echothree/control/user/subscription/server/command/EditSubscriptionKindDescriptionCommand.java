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
import com.echothree.control.user.subscription.common.edit.SubscriptionKindDescriptionEdit;
import com.echothree.control.user.subscription.common.form.EditSubscriptionKindDescriptionForm;
import com.echothree.control.user.subscription.common.result.EditSubscriptionKindDescriptionResult;
import com.echothree.control.user.subscription.common.result.SubscriptionResultFactory;
import com.echothree.control.user.subscription.common.spec.SubscriptionKindDescriptionSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.subscription.server.entity.SubscriptionKind;
import com.echothree.model.data.subscription.server.entity.SubscriptionKindDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditSubscriptionKindDescriptionCommand
        extends BaseAbstractEditCommand<SubscriptionKindDescriptionSpec, SubscriptionKindDescriptionEdit, EditSubscriptionKindDescriptionResult, SubscriptionKindDescription, SubscriptionKind> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SubscriptionKind.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SubscriptionKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditSubscriptionKindDescriptionCommand */
    public EditSubscriptionKindDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSubscriptionKindDescriptionResult getResult() {
        return SubscriptionResultFactory.getEditSubscriptionKindDescriptionResult();
    }

    @Override
    public SubscriptionKindDescriptionEdit getEdit() {
        return SubscriptionEditFactory.getSubscriptionKindDescriptionEdit();
    }

    @Override
    public SubscriptionKindDescription getEntity(EditSubscriptionKindDescriptionResult result) {
        var subscriptionControl = Session.getModelController(SubscriptionControl.class);
        SubscriptionKindDescription subscriptionKindDescription = null;
        var subscriptionKindName = spec.getSubscriptionKindName();
        var subscriptionKind = subscriptionControl.getSubscriptionKindByName(subscriptionKindName);

        if(subscriptionKind != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    subscriptionKindDescription = subscriptionControl.getSubscriptionKindDescription(subscriptionKind, language);
                } else { // EditMode.UPDATE
                    subscriptionKindDescription = subscriptionControl.getSubscriptionKindDescriptionForUpdate(subscriptionKind, language);
                }

                if(subscriptionKindDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownSubscriptionKindDescription.name(), subscriptionKindName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownSubscriptionKindName.name(), subscriptionKindName);
        }

        return subscriptionKindDescription;
    }

    @Override
    public SubscriptionKind getLockEntity(SubscriptionKindDescription subscriptionKindDescription) {
        return subscriptionKindDescription.getSubscriptionKind();
    }

    @Override
    public void fillInResult(EditSubscriptionKindDescriptionResult result, SubscriptionKindDescription subscriptionKindDescription) {
        var subscriptionControl = Session.getModelController(SubscriptionControl.class);

        result.setSubscriptionKindDescription(subscriptionControl.getSubscriptionKindDescriptionTransfer(getUserVisit(), subscriptionKindDescription));
    }

    @Override
    public void doLock(SubscriptionKindDescriptionEdit edit, SubscriptionKindDescription subscriptionKindDescription) {
        edit.setDescription(subscriptionKindDescription.getDescription());
    }

    @Override
    public void doUpdate(SubscriptionKindDescription subscriptionKindDescription) {
        var subscriptionControl = Session.getModelController(SubscriptionControl.class);
        var subscriptionKindDescriptionValue = subscriptionControl.getSubscriptionKindDescriptionValue(subscriptionKindDescription);

        subscriptionKindDescriptionValue.setDescription(edit.getDescription());

        subscriptionControl.updateSubscriptionKindDescriptionFromValue(subscriptionKindDescriptionValue, getPartyPK());
    }

}
