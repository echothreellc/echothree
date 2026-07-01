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

import com.echothree.control.user.subscription.common.form.GetSubscriptionTypesForm;
import com.echothree.control.user.subscription.common.result.SubscriptionResultFactory;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.control.subscription.server.logic.SubscriptionKindLogic;
import com.echothree.model.data.subscription.server.entity.SubscriptionKind;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.model.data.subscription.server.factory.SubscriptionTypeFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetSubscriptionTypesCommand
        extends BasePaginatedMultipleEntitiesCommand<SubscriptionType, GetSubscriptionTypesForm> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.SubscriptionType.name(), SecurityRoles.Review.name())
                ))
        ));

        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SubscriptionKindName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    SubscriptionControl subscriptionControl;

    @Inject
    SubscriptionKindLogic subscriptionKindLogic;

    /** Creates a new instance of GetSubscriptionTypesCommand */
    public GetSubscriptionTypesCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, true);
    }

    SubscriptionKind subscriptionKind;

    @Override
    protected void handleForm() {
        subscriptionKind = subscriptionKindLogic.getSubscriptionKindByName(this, form.getSubscriptionKindName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : subscriptionControl.countSubscriptionTypesBySubscriptionKind(subscriptionKind);
    }

    @Override
    protected Collection<SubscriptionType> getEntities() {
        return hasExecutionErrors() ? null : subscriptionControl.getSubscriptionTypesBySubscriptionKind(subscriptionKind);
    }

    @Override
    protected BaseResult getResult(Collection<SubscriptionType> entities) {
        var result = SubscriptionResultFactory.getGetSubscriptionTypesResult();

        if(entities != null) {
            result.setSubscriptionKind(subscriptionControl.getSubscriptionKindTransfer(getUserVisit(), subscriptionKind));

            if(session.hasLimit(SubscriptionTypeFactory.class)) {
                result.setSubscriptionTypeCount(getTotalEntities());
            }

            result.setSubscriptionTypes(subscriptionControl.getSubscriptionTypeTransfersBySubscriptionKind(getUserVisit(), subscriptionKind));
        }

        return result;
    }

}
