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

import com.echothree.control.user.subscription.common.form.GetSubscriptionsForm;
import com.echothree.control.user.subscription.common.result.SubscriptionResultFactory;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.subscription.server.control.SubscriptionControl;
import com.echothree.model.control.subscription.server.logic.SubscriptionTypeLogic;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.subscription.server.entity.Subscription;
import com.echothree.model.data.subscription.server.entity.SubscriptionType;
import com.echothree.model.data.subscription.server.factory.SubscriptionFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetSubscriptionsCommand
        extends BasePaginatedMultipleEntitiesCommand<Subscription, GetSubscriptionsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SubscriptionKindName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("SubscriptionTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null)
        );
    }
    
    @Inject
    PartyControl partyControl;

    @Inject
    SubscriptionControl subscriptionControl;

    @Inject
    PartyLogic partyLogic;

    @Inject
    SubscriptionTypeLogic subscriptionTypeLogic;
    
    /** Creates a new instance of GetSubscriptionsCommand */
    public GetSubscriptionsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    private SubscriptionType subscriptionType;
    private Party party;

    @Override
    protected void handleForm() {
        var subscriptionKindName = form.getSubscriptionKindName();
        var subscriptionTypeName = form.getSubscriptionTypeName();
        var partyName = form.getPartyName();

        if(subscriptionKindName != null && subscriptionTypeName != null && partyName == null) {
                subscriptionType = subscriptionTypeLogic.getSubscriptionTypeByName(this, subscriptionKindName, subscriptionTypeName);
        } else if(subscriptionKindName == null && subscriptionTypeName == null && partyName != null) {
            party = partyLogic.getPartyByName(this, partyName);
        } else {
            addExecutionError(com.echothree.util.common.message.ExecutionErrors.InvalidParameterCount.name());
        }
    }

    @Override
    protected Long getTotalEntities() {
        Long total = null;

        if(!hasExecutionErrors()) {
            if(subscriptionType != null) {
                total = subscriptionControl.countSubscriptionsBySubscriptionType(subscriptionType);
            } else if(party != null) {
                total = subscriptionControl.countSubscriptionsByParty(party);
            }
        }

        return total;
    }

    @Override
    protected Collection<Subscription> getEntities() {
        Collection<Subscription> subscriptions = null;

        if(!hasExecutionErrors()) {
            if(subscriptionType != null) {
                subscriptions = subscriptionControl.getSubscriptionsBySubscriptionType(subscriptionType);
            } else if(party != null) {
                subscriptions = subscriptionControl.getSubscriptionsByParty(party);
            }
        }

        return subscriptions;
    }

    @Override
    protected BaseResult getResult(Collection<Subscription> entities) {
        var result = SubscriptionResultFactory.getGetSubscriptionsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            if(subscriptionType != null) {
                result.setSubscriptionType(subscriptionControl.getSubscriptionTypeTransfer(userVisit, subscriptionType));
            } else if(party != null) {
                result.setParty(partyControl.getPartyTransfer(userVisit, party));
            }

            if(session.hasLimit(SubscriptionFactory.class)) {
                result.setSubscriptionCount(getTotalEntities());
            }

            result.setSubscriptions(subscriptionControl.getSubscriptionTransfers(userVisit, entities));
        }

        return result;
    }

}
