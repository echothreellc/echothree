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
import com.echothree.control.user.subscription.common.edit.SubscriptionKindEdit;
import com.echothree.control.user.subscription.common.form.EditSubscriptionKindForm;
import com.echothree.control.user.subscription.common.result.EditSubscriptionKindResult;
import com.echothree.control.user.subscription.common.result.SubscriptionResultFactory;
import com.echothree.control.user.subscription.common.spec.SubscriptionKindSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.data.subscription.server.entity.SubscriptionKind;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditSubscriptionKindCommand
        extends BaseAbstractEditCommand<SubscriptionKindSpec, SubscriptionKindEdit, EditSubscriptionKindResult, SubscriptionKind, SubscriptionKind> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.SubscriptionKind.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SubscriptionKindName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SubscriptionKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditSubscriptionKindCommand */
    public EditSubscriptionKindCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSubscriptionKindResult getResult() {
        return SubscriptionResultFactory.getEditSubscriptionKindResult();
    }

    @Override
    public SubscriptionKindEdit getEdit() {
        return SubscriptionEditFactory.getSubscriptionKindEdit();
    }

    @Override
    public SubscriptionKind getEntity(EditSubscriptionKindResult result) {
        var subscriptionControl = Session.getModelController(SubscriptionControl.class);
        SubscriptionKind subscriptionKind;
        var subscriptionKindName = spec.getSubscriptionKindName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            subscriptionKind = subscriptionControl.getSubscriptionKindByName(subscriptionKindName);
        } else { // EditMode.UPDATE
            subscriptionKind = subscriptionControl.getSubscriptionKindByNameForUpdate(subscriptionKindName);
        }

        if(subscriptionKind == null) {
            addExecutionError(ExecutionErrors.UnknownSubscriptionKindName.name(), subscriptionKindName);
        }

        return subscriptionKind;
    }

    @Override
    public SubscriptionKind getLockEntity(SubscriptionKind subscriptionKind) {
        return subscriptionKind;
    }

    @Override
    public void fillInResult(EditSubscriptionKindResult result, SubscriptionKind subscriptionKind) {
        var subscriptionControl = Session.getModelController(SubscriptionControl.class);

        result.setSubscriptionKind(subscriptionControl.getSubscriptionKindTransfer(getUserVisit(), subscriptionKind));
    }

    @Override
    public void doLock(SubscriptionKindEdit edit, SubscriptionKind subscriptionKind) {
        var subscriptionControl = Session.getModelController(SubscriptionControl.class);
        var subscriptionKindDescription = subscriptionControl.getSubscriptionKindDescription(subscriptionKind, getPreferredLanguage());
        var subscriptionKindDetail = subscriptionKind.getLastDetail();

        edit.setSubscriptionKindName(subscriptionKindDetail.getSubscriptionKindName());
        edit.setIsDefault(subscriptionKindDetail.getIsDefault().toString());
        edit.setSortOrder(subscriptionKindDetail.getSortOrder().toString());

        if(subscriptionKindDescription != null) {
            edit.setDescription(subscriptionKindDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(SubscriptionKind subscriptionKind) {
        var subscriptionControl = Session.getModelController(SubscriptionControl.class);
        var subscriptionKindName = edit.getSubscriptionKindName();
        var duplicateSubscriptionKind = subscriptionControl.getSubscriptionKindByName(subscriptionKindName);

        if(duplicateSubscriptionKind != null && !subscriptionKind.equals(duplicateSubscriptionKind)) {
            addExecutionError(ExecutionErrors.DuplicateSubscriptionKindName.name(), subscriptionKindName);
        }
    }

    @Override
    public void doUpdate(SubscriptionKind subscriptionKind) {
        var subscriptionControl = Session.getModelController(SubscriptionControl.class);
        var partyPK = getPartyPK();
        var subscriptionKindDetailValue = subscriptionControl.getSubscriptionKindDetailValueForUpdate(subscriptionKind);
        var subscriptionKindDescription = subscriptionControl.getSubscriptionKindDescriptionForUpdate(subscriptionKind, getPreferredLanguage());
        var description = edit.getDescription();

        subscriptionKindDetailValue.setSubscriptionKindName(edit.getSubscriptionKindName());
        subscriptionKindDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        subscriptionKindDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        subscriptionControl.updateSubscriptionKindFromValue(subscriptionKindDetailValue, partyPK);

        if(subscriptionKindDescription == null && description != null) {
            subscriptionControl.createSubscriptionKindDescription(subscriptionKind, getPreferredLanguage(), description, partyPK);
        } else if(subscriptionKindDescription != null && description == null) {
            subscriptionControl.deleteSubscriptionKindDescription(subscriptionKindDescription, partyPK);
        } else if(subscriptionKindDescription != null && description != null) {
            var subscriptionKindDescriptionValue = subscriptionControl.getSubscriptionKindDescriptionValue(subscriptionKindDescription);

            subscriptionKindDescriptionValue.setDescription(description);
            subscriptionControl.updateSubscriptionKindDescriptionFromValue(subscriptionKindDescriptionValue, partyPK);
        }
    }

}
