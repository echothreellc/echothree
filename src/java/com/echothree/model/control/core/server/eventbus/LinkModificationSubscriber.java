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

package com.echothree.model.control.core.server.eventbus;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.core.server.control.LinkControl;
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.model.data.core.common.LinkConstants;
import com.echothree.model.data.core.common.LinkTypeConstants;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.common.pk.LinkPK;
import com.echothree.model.data.core.common.pk.LinkTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.core.server.entity.Link;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.google.common.eventbus.Subscribe;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

@SentEventSubscriber
public class LinkModificationSubscriber
        extends BaseEventSubscriber {

    @Inject
    EventControl eventControl;

    @Inject
    LinkControl linkControl;

    @Inject
    LinkTypeControl linkTypeControl;

    private static class PropagationState {
        final Set<LinkPK> touchedLinks = new HashSet<>();
        final Set<EntityInstancePK> expandedEntityInstances = new HashSet<>();
        final Set<LinkTypePK> expandedLinkTypes = new HashSet<>();
    }

    private void touchLinks(List<Link> links, EntityInstance entityInstance, EventTypes eventType, Event event, SentEvent sentEvent) {
        var persistenceUtils = PersistenceUtils.getInstance();
        var dependencyPK = persistenceUtils.getBasePKFromEntityInstance(entityInstance);
        var createdByEntityInstance = event.getCreatedBy();
        var createdBy = createdByEntityInstance == null ? null
                : persistenceUtils.getBasePKFromEntityInstance(createdByEntityInstance);
        var propagationState = sentEvent.getPropagationState(PropagationState.class, PropagationState::new);

        for(var link : links) {
            var linkPK = link.getPrimaryKey();

            if(propagationState.touchedLinks.add(linkPK)) {
                eventControl.sendEvent(linkPK, EventTypes.TOUCH, dependencyPK, eventType, createdBy);
            }
        }
    }

    @Subscribe
    public void receiveSentEventForLinkTypes(SentEvent sentEvent) {
        decodeEventAndApply(sentEvent, (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
            if(LinkTypeConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                    && LinkTypeConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                    && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
                var linkType = linkTypeControl.getLinkTypeByEntityInstance(entityInstance);
                var propagationState = sentEvent.getPropagationState(PropagationState.class, PropagationState::new);

                if(propagationState.expandedLinkTypes.add(linkType.getPrimaryKey())) {
                    var links = linkControl.getLinksByLinkTypeForTouch(linkType);

                    touchLinks(links, entityInstance, eventType, event, sentEvent);
                }
            }
        });
    }

    @Subscribe
    public void receiveSentEventForEntityInstances(SentEvent sentEvent) {
        decodeEventAndApply(sentEvent, (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
            if(eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH) {
                var propagationState = sentEvent.getPropagationState(PropagationState.class, PropagationState::new);

                if(propagationState.expandedEntityInstances.add(entityInstance.getPrimaryKey())) {
                    if(LinkConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                            && LinkConstants.ENTITY_TYPE_NAME.equals(entityTypeName)) {
                        propagationState.touchedLinks.add(new LinkPK(entityInstance.getEntityUniqueId()));
                    }

                    var links = linkControl.getLinksByEntityInstanceForTouch(entityInstance);

                    touchLinks(links, entityInstance, eventType, event, sentEvent);
                }
            }
        });
    }

}
