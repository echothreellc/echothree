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

package com.echothree.model.control.index.server.logic;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.server.database.EntityInstancePKResult;
import com.echothree.model.control.core.server.database.EntityInstancePKsByEntityTypeQuery;
import com.echothree.model.control.core.server.database.EntityInstancePKsByPartyTypeQuery;
import com.echothree.model.control.index.common.exception.UnknownIndexNameException;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.party.server.logic.PartyLogic;
import com.echothree.model.control.queue.common.QueueTypes;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.control.queue.server.logic.QueueTypeLogic;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.index.server.entity.IndexType;
import com.echothree.model.data.queue.common.pk.QueueTypePK;
import com.echothree.model.data.queue.server.value.QueuedEntityValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class IndexLogic
        extends BaseLogic {

    protected IndexLogic() {
        super();
    }

    public static IndexLogic getInstance() {
        return CDI.current().select(IndexLogic.class).get();
    }
    
    public Index getIndexByName(final ExecutionErrorAccumulator eea, final String indexName) {
        var indexControl = Session.getModelController(IndexControl.class);
        var index = indexControl.getIndexByName(indexName);

        if(index == null) {
            handleExecutionError(UnknownIndexNameException.class, eea, ExecutionErrors.UnknownIndexName.name(), indexName);
        }

        return index;
    }
    
    private void queueEntityInstances(final Session session, final QueueControl queueControl, final QueueTypePK queueTypePK,
            final List<EntityInstancePKResult> entityInstanceResults) {
        List<QueuedEntityValue> queuedEntities = new ArrayList<>(entityInstanceResults.size());

        for(var entityInstanceResult : entityInstanceResults) {
            queuedEntities.add(new QueuedEntityValue(queueTypePK, entityInstanceResult.getEntityInstancePK(), session.START_TIME_LONG));
        }

        queueControl.createQueuedEntities(queuedEntities);
    }

    private void queueEntityInstancesByEntityType(final Session session, final ExecutionErrorAccumulator eea, final QueueControl queueControl,
            final QueueTypePK queueTypePK, final Set<EntityType> queuedEntityTypes, final IndexType indexType) {
        var indexTypeDetail = indexType.getLastDetail();
        var entityType = indexTypeDetail.getEntityType();
        var entityTypeDetail = entityType.getLastDetail();
        var componentVendor = entityTypeDetail.getComponentVendor();
        var entityTypeName = entityTypeDetail.getEntityTypeName();
        
        if(entityTypeName.equals(EntityTypes.Party.name())
                && componentVendor.getLastDetail().getComponentVendorName().equals(ComponentVendors.ECHO_THREE.name())) {
            // The Party indexes aren't by Language, so we're not worried about de-dupping them.
            var partyType = PartyLogic.getInstance().getPartyTypeByName(eea, indexTypeDetail.getIndexTypeName());

            if(eea == null || !eea.hasExecutionErrors()) {
                queueEntityInstances(session, queueControl, queueTypePK, new EntityInstancePKsByPartyTypeQuery().execute(entityType, partyType));
            }
        } else {
            if(!queuedEntityTypes.contains(entityType)) {
                queueEntityInstances(session, queueControl, queueTypePK, new EntityInstancePKsByEntityTypeQuery().execute(entityType));

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
        var queueType = QueueTypeLogic.getInstance().getQueueTypeByName(eea, QueueTypes.INDEXING.name());
        
        if(eea == null || !eea.hasExecutionErrors()) {
            var indexControl = Session.getModelController(IndexControl.class);
            var queueControl = Session.getModelController(QueueControl.class);
            var queueTypePK = queueType.getPrimaryKey();
            Set<EntityType> queuedEntityTypes = new HashSet<>();
            var indexTypes = entityType == null ? indexControl.getIndexTypes() : indexControl.getIndexTypesByEntityType(entityType);

            for(var indexType : indexTypes) {
                var indexes = indexControl.getIndexesByIndexType(indexType);
                
                for(var index : indexes) {
                    var indexStatus = indexControl.getIndexStatusForUpdate(index);
                    
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
