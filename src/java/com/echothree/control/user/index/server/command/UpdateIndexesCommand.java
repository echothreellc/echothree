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

package com.echothree.control.user.index.server.command;

import com.echothree.control.user.index.common.form.UpdateIndexesForm;
import com.echothree.control.user.index.common.result.IndexResultFactory;
import com.echothree.model.control.contact.server.indexer.ContactMechanismIndexer;
import com.echothree.model.control.content.server.indexer.ContentCatalogIndexer;
import com.echothree.model.control.content.server.indexer.ContentCatalogItemIndexer;
import com.echothree.model.control.content.server.indexer.ContentCategoryIndexer;
import com.echothree.model.control.core.server.indexer.ComponentVendorIndexer;
import com.echothree.model.control.core.server.indexer.EntityAliasTypeIndexer;
import com.echothree.model.control.core.server.indexer.EntityAttributeGroupIndexer;
import com.echothree.model.control.core.server.indexer.EntityAttributeIndexer;
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
import com.echothree.model.control.shipping.server.indexer.ShippingMethodIndexer;
import com.echothree.model.control.vendor.server.indexer.VendorIndexer;
import com.echothree.model.control.warehouse.server.indexer.WarehouseIndexer;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.Dependent;

@Dependent
public class UpdateIndexesCommand
        extends BaseSimpleCommand<UpdateIndexesForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null))
        );
    }
    
    /** Creates a new instance of UpdateIndexesCommand */
    public UpdateIndexesCommand() {
        super(COMMAND_SECURITY_DEFINITION, null, false);
    }
    
    private static final int QUEUED_ENTITY_COUNT = 10;
    private static final long MAXIMUM_MILLISECONDS = 90 * 1000; // 90 seconds
    
    private void setLimits() {
        var limits = new HashMap<String, Limit>(1);
        
        limits.put(QueuedEntityConstants.ENTITY_TYPE_NAME, new Limit(Integer.toString(QUEUED_ENTITY_COUNT), null));
        session.setLimits(limits);
    }
    
    private Map<EntityInstance, List<QueuedEntity>> getQueuedEntities(final QueueType queueType) {
        var queueControl = Session.getModelController(QueueControl.class);
        var queuedEntityMap = new HashMap<EntityInstance, List<QueuedEntity>>(QUEUED_ENTITY_COUNT);
        var queuedEntities = queueControl.getQueuedEntitiesByQueueType(queueType);
        
        queuedEntities.stream().map(QueuedEntity::getEntityInstance).filter(
                (entityInstance) -> !queuedEntityMap.containsKey(entityInstance)).forEach((entityInstance) -> {
            var duplicateQueuedEntities = queueControl.getQueuedEntities(queueType, entityInstance);
            
            queuedEntityMap.put(entityInstance, duplicateQueuedEntities);
        });
        
        return queuedEntityMap;
    }
    
    private void setupIndexers(final IndexControl indexControl, final Map<EntityType, List<BaseIndexer<?>>> indexersMap, final EntityType entityType) {
        var indexTypes = indexControl.getIndexTypesByEntityType(entityType);
        var size = 0L;

        size = indexTypes.stream().map(indexControl::countIndexesByIndexType).reduce(size, Long::sum);

        var indexers = new ArrayList<BaseIndexer<?>>(toIntExact(size));

        indexTypes.forEach((indexType) -> {
            var indexes = indexControl.getIndexesByIndexType(indexType);
            var indexTypeName = indexType.getLastDetail().getIndexTypeName();

            indexes.stream().map((index) -> {
                BaseIndexer<?> baseIndexer = null;

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
                } else if(indexTypeName.equals(IndexTypes.COMPONENT_VENDOR.name())) {
                    baseIndexer = new ComponentVendorIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.ENTITY_TYPE.name())) {
                    baseIndexer = new EntityTypeIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.ENTITY_ALIAS_TYPE.name())) {
                    baseIndexer = new EntityAliasTypeIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.ENTITY_ATTRIBUTE_GROUP.name())) {
                    baseIndexer = new EntityAttributeGroupIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.ENTITY_ATTRIBUTE.name())) {
                    baseIndexer = new EntityAttributeIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.ENTITY_LIST_ITEM.name())) {
                    baseIndexer = new EntityListItemIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.CONTENT_CATALOG.name())) {
                    baseIndexer = new ContentCatalogIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.CONTENT_CATALOG_ITEM.name())) {
                    baseIndexer = new ContentCatalogItemIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.CONTENT_CATEGORY.name())) {
                    baseIndexer = new ContentCategoryIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.SECURITY_ROLE_GROUP.name())) {
                    baseIndexer = new SecurityRoleGroupIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.SECURITY_ROLE.name())) {
                    baseIndexer = new SecurityRoleIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.HARMONIZED_TARIFF_SCHEDULE_CODE.name())) {
                    baseIndexer = new HarmonizedTariffScheduleCodeIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.CONTACT_MECHANISM.name())) {
                    baseIndexer = new ContactMechanismIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.OFFER.name())) {
                    baseIndexer = new OfferIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.USE.name())) {
                    baseIndexer = new UseIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.USE_TYPE.name())) {
                    baseIndexer = new UseTypeIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.SHIPPING_METHOD.name())) {
                    baseIndexer = new ShippingMethodIndexer(this, index);
                } else if(indexTypeName.equals(IndexTypes.WAREHOUSE.name())) {
                    baseIndexer = new WarehouseIndexer(this, index);
                }

                return baseIndexer;
            }).filter(Objects::nonNull).peek(BaseIndexer::open).forEach(indexers::add);
        });

        indexersMap.put(entityType, indexers);
    }
    
    private void indexQueuedEntity(final QueueControl queueControl, final Map<EntityType, List<BaseIndexer<?>>> indexersMap,
            final Map.Entry<EntityInstance, List<QueuedEntity>> queuedEntityEntry) {
        var entityInstance = queuedEntityEntry.getKey();
        var entityType = entityInstance.getEntityType();
        var baseIndexers = indexersMap.get(entityType);
        
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
    
    private void closeIndexers(final QueueControl queueControl, final QueueType queueType, final Map<EntityType, List<BaseIndexer<?>>> indexersMap) {
        indexersMap.forEach((key, value) -> value.stream().peek((baseIndexer) -> {
            if(queueControl.countQueuedEntitiesByEntityType(queueType, baseIndexer.getEntityType()) == 0) {
                SearchLogic.getInstance().invalidateCachedSearchesByIndex(baseIndexer.getIndex());
            }
        }).forEach(BaseIndexer::close));
    }
    
    private void verifyIndexersAreSetup(final IndexControl indexControl, final Map<EntityType, List<BaseIndexer<?>>> indexersMap,
            final Map<EntityInstance, List<QueuedEntity>> queuedEntityMap) {
        for(var queuedEntityEntry : queuedEntityMap.entrySet()) {
            var entityType = queuedEntityEntry.getKey().getEntityType();
            
            if(!indexersMap.containsKey(entityType)) {
                setupIndexers(indexControl, indexersMap, entityType);
            }
            
            if(hasExecutionErrors()) {
                break;
            }
        }
    }

    private void indexQueuedEntities(final QueueControl queueControl, final Map<EntityType, List<BaseIndexer<?>>> indexersMap,
            final Map<EntityInstance, List<QueuedEntity>> queuedEntityMap) {
        try {
            ThreadSession.pushSessionEntityCache();

            for(var queuedEntityEntry : queuedEntityMap.entrySet()) {
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
        var result = IndexResultFactory.getUpdateIndexesResult();
        var queueType = QueueTypeLogic.getInstance().getQueueTypeByName(this, QueueTypes.INDEXING.name());
        var indexingComplete = false; // Indexing is only complete when we can absolutely verify it as being complete.
        
        if(!hasExecutionErrors()) {
            var queueControl = Session.getModelController(QueueControl.class);
            
            indexingComplete = queueControl.countQueuedEntitiesByQueueType(queueType) == 0;
            
            // If there isn't anything in the queue, skip over all of this.
            if(!indexingComplete) {
                var indexControl = Session.getModelController(IndexControl.class);
                var indexersMap = new HashMap<EntityType, List<BaseIndexer<?>>>(toIntExact(indexControl.countIndexes()));

                try {
                    var exitTime = session.getStartTime() + MAXIMUM_MILLISECONDS;

                    setLimits();

                    while(System.currentTimeMillis() < exitTime) {
                        var queuedEntityMap = getQueuedEntities(queueType);

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
