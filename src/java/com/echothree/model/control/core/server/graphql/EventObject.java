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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.graphql.server.graphql.TimeObject;
import com.echothree.model.data.core.server.entity.Event;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("event object")
@GraphQLName("Event")
public class EventObject
        extends BaseObject {

    private final Event event; // Always Present

    public EventObject(Event event) {
        this.event = event;
    }

    @GraphQLField
    @GraphQLDescription("event group")
    public EventGroupObject getEventGroup() {
        var eventgroup = event.getEventGroup();

        return eventgroup == null ? null : new EventGroupObject(eventgroup);
    }

    @GraphQLField
    @GraphQLDescription("event time")
    @GraphQLNonNull
    public TimeObject getEventTime(final DataFetchingEnvironment env) {
        return new TimeObject(event.getEventTime());
    }

    @GraphQLField
    @GraphQLDescription("event time sequence")
    @GraphQLNonNull
    public int getSortOrder() {
        return event.getEventTimeSequence();
    }

    @GraphQLField
    @GraphQLDescription("entity instance")
    @GraphQLNonNull
    public EntityInstanceObject getEntityInstance() {
        return new EntityInstanceObject(event.getEntityInstance());
    }

    @GraphQLField
    @GraphQLDescription("event type")
    @GraphQLNonNull
    public EventTypeObject getEventType() {
        return new EventTypeObject(event.getEventType());
    }

    @GraphQLField
    @GraphQLDescription("related entity instance")
    public EntityInstanceObject getRelatedEntityInstance(final DataFetchingEnvironment env) {
        var relatedEntityInstance = event.getRelatedEntityInstance();

        return relatedEntityInstance != null && CoreSecurityUtils.getHasEntityInstanceAccess(env) ? new EntityInstanceObject(relatedEntityInstance) : null;
    }

    @GraphQLField
    @GraphQLDescription("related event type")
    public EventTypeObject getRelatedEventType() {
        var relatedEventType = event.getRelatedEventType();

        return relatedEventType == null ? null : new EventTypeObject(relatedEventType);
    }

    @GraphQLField
    @GraphQLDescription("created by")
    public EntityInstanceObject getCreatedBy(final DataFetchingEnvironment env) {
        var createdBy = event.getCreatedBy();

        return createdBy != null && CoreSecurityUtils.getHasEntityInstanceAccess(env) ? new EntityInstanceObject(createdBy) : null;
    }

}
