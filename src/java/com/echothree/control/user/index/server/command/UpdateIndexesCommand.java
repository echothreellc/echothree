// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.control.user.index.server.command;

import com.echothree.control.user.index.common.result.IndexResultFactory;
import com.echothree.control.user.index.common.result.UpdateIndexesResult;
import com.echothree.model.control.contact.server.indexer.ContactMechanismIndexer;
import com.echothree.model.control.content.server.indexer.ContentCategoryIndexer;
import com.echothree.model.control.core.server.indexer.EntityListItemIndexer;
import com.echothree.model.control.core.server.indexer.EntityTypeIndexer;
import com.echothree.model.control.customer.server.indexer.CustomerIndexer;
import com.echothree.model.control.employee.server.indexer.EmployeeIndexer;
import com.echothree.model.control.forum.server.indexer.ForumMessageIndexer;
import com.echothree.model.control.index.common.IndexTypes;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.index.server.indexer.BaseIndexer;
import com.echothree.model.control.item.server.indexer.HarmonizedTariffScheduleCodeIndexer;
import com.echothree.model.control.item.server.indexer.ItemIndexer;
import com.echothree.model.control.offer.server.indexer.OfferIndexer;
import com.echothree.model.control.offer.server.indexer.UseIndexer;
import com.echothree.model.control.offer.server.indexer.UseTypeIndexer;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.queue.common.QueueTypes;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.control.queue.server.logic.QueueTypeLogic;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.security.server.indexer.SecurityRoleGroupIndexer;
import com.echothree.model.control.security.server.indexer.SecurityRoleIndexer;
import com.echothree.model.control.vendor.server.indexer.VendorIndexer;
import com.echothree.model.control.warehouse.server.indexer.WarehouseIndexer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.index.server.entity.IndexType;
import com.echothree.model.data.queue.common.QueuedEntityConstants;
import com.echothree.model.data.queue.server.entity.QueueType;
import com.echothree.model.data.queue.server.entity.QueuedEntity;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.ThreadSession;
import static java.lang.Math.toIntExact;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UpdateIndexesCommand
        extends BaseSimpleCommand {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null))
        );
    }
    
    /** Creates a new instance of UpdateIndexesCommand */
    public UpdateIndexesCommand(UserVisitPK userVisitPK) {
        super(userVisitPK, COMMAND_SECURITY_DEFINITION, false);
    }
    
    private static final int QUEUED_ENTITY_COUNT = 10;
    private static final long MAXIMUM_MILLISECONDS = 90 * 1000; // 90 seconds
    
    private void setLimits() {
        Map<String, Limit> limits = new HashMap<>(1);
        
        limits.put(QueuedEntityConstants.ENTITY_TYPE_NAME, new Limit(Integer.toString(QUEUED_ENTITY_COUNT), null));
        session.setLimits(limits);
    }
    
    private Map<EntityInstance, List<QueuedEntity>> getQueuedEntities(final QueueType queueType) {
        var queueControl = Session.getModelController(QueueControl.class);
        Map<EntityInstance, List<QueuedEntity>> queuedEntityMap = new HashMap<>(QUEUED_ENTITY_COUNT);
        List<QueuedEntity> queuedEntities = queueControl.getQueuedEntitiesByQueueType(queueType);
        
        queuedEntities.stream().map(QueuedEntity::getEntityInstance).filter(
                (entityInstance) -> !queuedEntityMap.containsKey(entityInstance)).forEach((entityInstance) -> {
            List<QueuedEntity> duplicateQueuedEntities = queueControl.getQueuedEntities(queueType, entityInstance);
            
            queuedEntityMap.put(entityInstance, duplicateQueuedEntities);
        });
        
        return queuedEntityMap;
    }
    
    private void setupIndexers(final IndexControl indexControl, final Map<EntityType, List<BaseIndexer>> indexersMap, final EntityType entityType) {
        List<IndexType> indexTypes = indexControl.getIndexTypesByEntityType(entityType);
        long size = 0;

        size = indexTypes.stream().map(indexControl::countIndexesByIndexType).reduce(size, Long::sum);

        List<BaseIndexer> indexers = new ArrayList<>(toIntExact(size));

        indexTypes.forEach((indexType) -> {
            List<Index> indexes = indexControl.getIndexesByIndexType(indexType);
            String indexTypeName = indexType.getLastDetail().getIndexTypeName();
            indexes.stream().map((index) -> {
                BaseIndexer baseIndexer = null;
                if(indexTypeName.equals(IndexTypes.CUSTOMER.name())) {
                    baseIndexer = new CustomerIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.EMPLOYEE.name())) {
                    baseIndexer = new EmployeeIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.VENDOR.name())) {
                    baseIndexer = new VendorIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.ITEM.name())) {
                    baseIndexer = new ItemIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.FORUM_MESSAGE.name())) {
                    baseIndexer = new ForumMessageIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.ENTITY_LIST_ITEM.name())) {
                    baseIndexer = new EntityListItemIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.CONTENT_CATEGORY.name())) {
                    baseIndexer = new ContentCategoryIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.SECURITY_ROLE_GROUP.name())) {
                    baseIndexer = new SecurityRoleGroupIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.SECURITY_ROLE.name())) {
                    baseIndexer = new SecurityRoleIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.HARMONIZED_TARIFF_SCHEDULE_CODE.name())) {
                    baseIndexer = new HarmonizedTariffScheduleCodeIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.ENTITY_TYPE.name())) {
                    baseIndexer = new EntityTypeIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.CONTACT_MECHANISM.name())) {
                    baseIndexer = new ContactMechanismIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.OFFER.name())) {
                    baseIndexer = new OfferIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.USE.name())) {
                    baseIndexer = new UseIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.USE_TYPE.name())) {
                    baseIndexer = new UseTypeIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.WAREHOUSE.name())) {
                    baseIndexer = new WarehouseIndexer(this, index);
                }
                return baseIndexer;
            }).filter(Objects::nonNull).peek(BaseIndexer::open).forEach(indexers::add);
        });

        indexersMap.put(entityType, indexers);
    }
    
    private void indexQueuedEntity(final QueueControl queueControl, final Map<EntityType, List<BaseIndexer>> indexersMap,
            final Map.Entry<EntityInstance, List<QueuedEntity>> queuedEntityEntry) {
        EntityInstance entityInstance = queuedEntityEntry.getKey();
        EntityType entityType = entityInstance.getEntityType();
        List<BaseIndexer> baseIndexers = indexersMap.get(entityType);
        
        for(var baseIndexer : baseIndexers) {
            baseIndexer.updateIndex(entityInstance);

            if(hasExecutionErrors()) {
                break;
            }
        }

        if(!hasExecutionErrors()) {
            queuedEntityEntry.getValue().forEach(queueControl::removeQueuedEntity);
        }
    }
    
    private void closeIndexers(final QueueControl queueControl, final QueueType queueType, final Map<EntityType, List<BaseIndexer>> indexersMap) {
        indexersMap.forEach((key, value) -> value.stream().peek((baseIndexer) -> {
            if(queueControl.countQueuedEntitiesByEntityType(queueType, baseIndexer.getEntityType()) == 0) {
                SearchLogic.getInstance().invalidateCachedSearchesByIndex(baseIndexer.getIndex());
            }
        }).forEach(BaseIndexer::close));
    }
    
    private void verifyIndexersAreSetup(final IndexControl indexControl, final Map<EntityType, List<BaseIndexer>> indexersMap,
            final Map<EntityInstance, List<QueuedEntity>> queuedEntityMap) {
        for(Map.Entry<EntityInstance, List<QueuedEntity>> queuedEntityEntry : queuedEntityMap.entrySet()) {
            EntityType entityType = queuedEntityEntry.getKey().getEntityType();
            
            if(!indexersMap.containsKey(entityType)) {
                setupIndexers(indexControl, indexersMap, entityType);
            }
            
            if(hasExecutionErrors()) {
                break;
            }
        }
    }

    private void indexQueuedEntities(final QueueControl queueControl, final Map<EntityType, List<BaseIndexer>> indexersMap,
            final Map<EntityInstance, List<QueuedEntity>> queuedEntityMap) {
        try {
            ThreadSession.pushSessionEntityCache();

            for(Map.Entry<EntityInstance, List<QueuedEntity>> queuedEntityEntry : queuedEntityMap.entrySet()) {
                indexQueuedEntity(queueControl, indexersMap, queuedEntityEntry);

                if(hasExecutionErrors()) {
                    break;
                }
            }
        } finally {
            ThreadSession.popSessionEntityCache();
        }
    }
    
    @Override
    protected BaseResult execute() {
        UpdateIndexesResult result = IndexResultFactory.getUpdateIndexesResult();
        QueueType queueType = QueueTypeLogic.getInstance().getQueueTypeByName(this, QueueTypes.INDEXING.name());
        boolean indexingComplete = false; // Indexing is only complete when we can absolutely verify it as being complete.
        
        if(!hasExecutionErrors()) {
            var queueControl = Session.getModelController(QueueControl.class);
            
            indexingComplete = queueControl.countQueuedEntitiesByQueueType(queueType) == 0;
            
            // If there isn't anything in the queue, skip over all of this.
            if(!indexingComplete) {
                var indexControl = Session.getModelController(IndexControl.class);
                Map<EntityType, List<BaseIndexer>> indexersMap = new HashMap<>(toIntExact(indexControl.countIndexes()));

                try {
                    long exitTime = session.START_TIME + MAXIMUM_MILLISECONDS;

                    setLimits();

                    while(System.currentTimeMillis() < exitTime) {
                        Map<EntityInstance, List<QueuedEntity>> queuedEntityMap = getQueuedEntities(queueType);

                        // If there are no more to index, break out of here.
                        if(queuedEntityMap.isEmpty()) {
                            break;
                        }
                        
                        // Make sure we have the indexers available for each EntityType we've found.
                        verifyIndexersAreSetup(indexControl, indexersMap, queuedEntityMap);

                        if(!hasExecutionErrors()) {
                            indexQueuedEntities(queueControl, indexersMap, queuedEntityMap);
                        }

                        if(hasExecutionErrors()) {
                            break;
                        }
                    }
                } finally {
                    closeIndexers(queueControl, queueType, indexersMap);
                }

                // Either the QueuedEntities have run out, or the time expired. Check to see which it is, and
                // set indexingComplete to indicate if the QueuedEntities have run out.
                indexingComplete = queueControl.countQueuedEntitiesByQueueType(queueType) == 0;
            }
        }    
        
        result.setIndexingComplete(indexingComplete);
        
        return result;
    }

}
