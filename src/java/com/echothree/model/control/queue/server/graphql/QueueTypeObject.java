// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.queue.server.graphql;

import com.echothree.model.control.queue.server.QueueControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.queue.server.entity.QueueType;
import com.echothree.model.data.queue.server.entity.QueueTypeDetail;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.DateUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("queue type object")
@GraphQLName("QueueType")
public class QueueTypeObject
        extends BaseEntityInstanceObject {
    
    private final QueueType queueType; // Always Present
    
    public QueueTypeObject(QueueType queueType) {
        super(queueType.getPrimaryKey());
        
        this.queueType = queueType;
    }

    private QueueTypeDetail queueTypeDetail; // Optional, use getQueueTypeDetail()
    
    private QueueTypeDetail getQueueTypeDetail() {
        if(queueTypeDetail == null) {
            queueTypeDetail = queueType.getLastDetail();
        }
        
        return queueTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("queue type name")
    @GraphQLNonNull
    public String getQueueTypeName() {
        return getQueueTypeDetail().getQueueTypeName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getQueueTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getQueueTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var queueControl = (QueueControl)Session.getModelController(QueueControl.class);
        var userControl = (UserControl)Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return queueControl.getBestQueueTypeDescription(queueType, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }

    @GraphQLField
    @GraphQLDescription("queued entity count")
    @GraphQLNonNull
    public long getQueuedEntityCount() {
        var queueControl = (QueueControl)Session.getModelController(QueueControl.class);

        return queueControl.countQueuedEntitiesByQueueType(queueType);
    }

    @GraphQLField
    @GraphQLDescription("unformatted oldest queued entity")
    public Long getUnformattedOldestQueuedEntityTime() {
        var queueControl = (QueueControl)Session.getModelController(QueueControl.class);

        return queueControl.oldestQueuedEntityTimeByQueueType(queueType);
    }

    @GraphQLField
    @GraphQLDescription("oldest queued entity")
    public String getOldestQueuedEntityTime(final DataFetchingEnvironment env) {
        GraphQlContext context = env.getContext();
        var queueControl = (QueueControl)Session.getModelController(QueueControl.class);
        Long oldestQueuedEntityTime = queueControl.oldestQueuedEntityTimeByQueueType(queueType);

        return oldestQueuedEntityTime == null ? null : DateUtils.getInstance().formatTypicalDateTime(context.getUserVisit(), oldestQueuedEntityTime);
    }

    @GraphQLField
    @GraphQLDescription("unformatted latest queued entity")
    public Long getUnformattedLatestQueuedEntityTime() {
        var queueControl = (QueueControl)Session.getModelController(QueueControl.class);

        return queueControl.latestQueuedEntityTimeByQueueType(queueType);
    }

    @GraphQLField
    @GraphQLDescription("latest queued entity")
    public String getLatestQueuedEntityTime(final DataFetchingEnvironment env) {
        GraphQlContext context = env.getContext();
        var queueControl = (QueueControl)Session.getModelController(QueueControl.class);
        Long latestQueuedEntityTime = queueControl.latestQueuedEntityTimeByQueueType(queueType);

        return latestQueuedEntityTime == null ? null : DateUtils.getInstance().formatTypicalDateTime(context.getUserVisit(), latestQueuedEntityTime);
    }

}
