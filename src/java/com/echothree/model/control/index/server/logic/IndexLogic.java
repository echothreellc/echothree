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

package com.echothree.model.control.index.server.logic;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.database.EntityInstanceResult;
import com.echothree.model.control.core.server.database.EntityInstancesByEntityTypeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesByPartyTypeQuery;
import com.echothree.model.control.index.common.exception.UnknownIndexNameException;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.queue.common.QueueConstants;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.control.queue.server.logic.QueueTypeLogic;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.index.server.entity.IndexStatus;
import com.echothree.model.data.index.server.entity.IndexType;
import com.echothree.model.data.index.server.entity.IndexTypeDetail;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.queue.common.pk.QueueTypePK;
import com.echothree.model.data.queue.server.entity.QueueType;
import com.echothree.model.data.queue.server.value.QueuedEntityValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IndexLogic
        extends BaseLogic {

    private IndexLogic() {
        super();
    }

    private static class IndexLogicHolder {
        static IndexLogic instance = new IndexLogic();
    }

    public static IndexLogic getInstance() {
        return IndexLogicHolder.instance;
    }
    
    public Index getIndexByName(final ExecutionErrorAccumulator eea, final String indexName) {
        var indexControl = (IndexControl)Session.getModelController(IndexControl.class);
        Index index = indexControl.getIndexByName(indexName);

        if(index == null) {
            handleExecutionError(UnknownIndexNameException.class, eea, ExecutionErrors.UnknownIndexName.name(), indexName);
        }

        return index;
    }
    
    private void queueEntityInstances(final Session session, final QueueControl queueControl, final QueueTypePK queueTypePK,
            final List<EntityInstanceResult> entityInstanceResults) {
        List<QueuedEntityValue> queuedEntities = new ArrayList<>(entityInstanceResults.size());

        for(EntityInstanceResult entityInstanceResult : entityInstanceResults) {
            queuedEntities.add(new QueuedEntityValue(queueTypePK, entityInstanceResult.getEntityInstancePK(), session.START_TIME_LONG));
        }

        queueControl.createQueuedEntities(queuedEntities);
    }

    private void queueEntityInstancesByEntityType(final Session session, final ExecutionErrorAccumulator eea, final QueueControl queueControl,
            final QueueTypePK queueTypePK, final Set<EntityType> queuedEntityTypes, final IndexType indexType) {
        IndexTypeDetail indexTypeDetail = indexType.getLastDetail();
        EntityType entityType = indexTypeDetail.getEntityType();
        EntityTypeDetail entityTypeDetail = entityType.getLastDetail();
        ComponentVendor componentVendor = entityTypeDetail.getComponentVendor();
        String entityTypeName = entityTypeDetail.getEntityTypeName();
        
        if(entityTypeName.equals(EntityTypes.Party.name())
                && componentVendor.getLastDetail().getComponentVendorName().equals(ComponentVendors.ECHOTHREE.name())) {
            // The Party indexes aren't by Language, so we're not worried about de-dupping them.
            PartyType partyType = PartyLogic.getInstance().getPartyTypeByName(eea, indexTypeDetail.getIndexTypeName());

            if(eea == null || !eea.hasExecutionErrors()) {
                queueEntityInstances(session, queueControl, queueTypePK, new EntityInstancesByPartyTypeQuery().execute(entityType, partyType));
            }
        } else {
            if(!queuedEntityTypes.contains(entityType)) {
                queueEntityInstances(session, queueControl, queueTypePK, new EntityInstancesByEntityTypeQuery().execute(entityType));

                queuedEntityTypes.add(entityType);
            }
        }
    }
    
    /**
     * Request that a specific Index or that all Indexes should be reindexed.
     * @param eea the ExecutionErrorAccumulator that should gather any errors encountered during execution (Required)
     * @param entityType the EntityType that should be reindexed. If null, all indexes will be reindexed (Optional)
     */
    public void reindex(final Session session, final ExecutionErrorAccumulator eea, final EntityType entityType) {
        QueueType queueType = QueueTypeLogic.getInstance().getQueueTypeByName(eea, QueueConstants.QueueType_INDEXING);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            var indexControl = (IndexControl)Session.getModelController(IndexControl.class);
            var queueControl = (QueueControl)Session.getModelController(QueueControl.class);
            QueueTypePK queueTypePK = queueType.getPrimaryKey();
            Set<EntityType> queuedEntityTypes = new HashSet<>();
            List<IndexType> indexTypes = entityType == null ? indexControl.getIndexTypes() : indexControl.getIndexTypesByEntityType(entityType);

            for(IndexType indexType : indexTypes) {
                List<Index> indexes = indexControl.getIndexesByIndexType(indexType);
                
                for(Index index : indexes) {
                    IndexStatus indexStatus = indexControl.getIndexStatusForUpdate(index);
                    
                    queueEntityInstancesByEntityType(session, eea, queueControl, queueTypePK, queuedEntityTypes, indexType);
                    
                    if(eea.hasExecutionErrors()) {
                        break;
                    }
                    
                    indexStatus.setCreatedTime(null);
                }
                
                if(eea.hasExecutionErrors()) {
                    break;
                }
            }
        }
    }

}
