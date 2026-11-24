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
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.core.server.control.LinkCardinalityControl;
import com.echothree.model.control.core.server.control.LinkDirectionalityControl;
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.model.data.core.common.EntityTypeConstants;
import com.echothree.model.data.core.common.LinkCardinalityConstants;
import com.echothree.model.data.core.common.LinkDirectionalityConstants;
import com.echothree.model.data.core.common.LinkTypeConstants;
import com.echothree.model.data.core.common.pk.LinkTypePK;
import com.echothree.model.data.core.server.entity.Event;
import com.echothree.model.data.core.server.entity.LinkType;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.google.common.eventbus.Subscribe;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

@SentEventSubscriber
public class LinkTypeModificationSubscriber
        extends BaseEventSubscriber {

    @Inject
    EntityTypeControl entityTypeControl;

    @Inject
    EventControl eventControl;

    @Inject
    LinkCardinalityControl linkCardinalityControl;

    @Inject
    LinkDirectionalityControl linkDirectionalityControl;

    @Inject
    LinkTypeControl linkTypeControl;

    private static class PropagationState {
        final Set<LinkTypePK> touchedLinkTypes = new HashSet<>();
        final Set<LinkTypePK> expandedLinkTypes = new HashSet<>();
    }

    private void touchLinkTypes(List<LinkType> linkTypes, BasePK dependencyPK, EventTypes eventType, Event event, SentEvent sentEvent) {
        var createdByEntityInstance = event.getCreatedBy();
        var createdBy = createdByEntityInstance == null ? null
                : PersistenceUtils.getInstance().getBasePKFromEntityInstance(createdByEntityInstance);
        var propagationState = sentEvent.getPropagationState(PropagationState.class, PropagationState::new);

        for(var linkType : linkTypes) {
            var linkTypePK = linkType.getPrimaryKey();

            if(propagationState.touchedLinkTypes.add(linkTypePK)) {
                eventControl.sendEvent(linkTypePK, EventTypes.TOUCH, dependencyPK, eventType, createdBy);
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
                var linkTypePK = linkType.getPrimaryKey();
                var propagationState = sentEvent.getPropagationState(PropagationState.class, PropagationState::new);

                if(propagationState.expandedLinkTypes.add(linkTypePK)) {
                    propagationState.touchedLinkTypes.add(linkTypePK);
                    var linkTypes = linkTypeControl.getLinkTypesByParentOrInverseLinkTypeForTouch(linkType);

                    touchLinkTypes(linkTypes, linkTypePK, eventType, event, sentEvent);
                }
            }
        });
    }

    @Subscribe
    public void receiveSentEventForEntityTypes(SentEvent sentEvent) {
        decodeEventAndApply(sentEvent, (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
            if(EntityTypeConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                    && EntityTypeConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                    && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
                var entityType = entityTypeControl.getEntityTypeByEntityInstance(entityInstance);
                var linkTypes = linkTypeControl.getLinkTypesByEntityTypeForTouch(entityType);

                touchLinkTypes(linkTypes, entityType.getPrimaryKey(), eventType, event, sentEvent);
            }
        });
    }

    @Subscribe
    public void receiveSentEventForLinkCardinalities(SentEvent sentEvent) {
        decodeEventAndApply(sentEvent, (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
            if(LinkCardinalityConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                    && LinkCardinalityConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                    && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
                var linkCardinality = linkCardinalityControl.getLinkCardinalityByEntityInstance(entityInstance);
                var linkTypes = linkTypeControl.getLinkTypesByLinkCardinalityForTouch(linkCardinality);

                touchLinkTypes(linkTypes, linkCardinality.getPrimaryKey(), eventType, event, sentEvent);
            }
        });
    }

    @Subscribe
    public void receiveSentEventForLinkDirectionalities(SentEvent sentEvent) {
        decodeEventAndApply(sentEvent, (event, entityInstance, eventType, componentVendorName, entityTypeName) -> {
            if(LinkDirectionalityConstants.COMPONENT_VENDOR_NAME.equals(componentVendorName)
                    && LinkDirectionalityConstants.ENTITY_TYPE_NAME.equals(entityTypeName)
                    && (eventType == EventTypes.MODIFY || eventType == EventTypes.TOUCH)) {
                var linkDirectionality = linkDirectionalityControl.getLinkDirectionalityByEntityInstance(entityInstance);
                var linkTypes = linkTypeControl.getLinkTypesByLinkDirectionalityForTouch(linkDirectionality);

                touchLinkTypes(linkTypes, linkDirectionality.getPrimaryKey(), eventType, event, sentEvent);
            }
        });
    }

}
