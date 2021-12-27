// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.item.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.sequence.server.graphql.SequenceObject;
import com.echothree.model.control.sequence.server.graphql.SequenceSecurityUtils;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.item.common.ItemConstants;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.item.server.entity.ItemCategoryDetail;
import com.echothree.util.common.transfer.Limit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.ThreadSession;
import com.echothree.util.server.validation.Validator;
import com.google.common.primitives.Longs;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.connection.AbstractPaginatedData;
import graphql.annotations.connection.GraphQLConnection;
import graphql.annotations.connection.PaginatedData;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@GraphQLDescription("item category object")
@GraphQLName("ItemCategory")
public class ItemCategoryObject
        extends BaseEntityInstanceObject {
    
    private final ItemCategory itemCategory; // Always Present
    
    public ItemCategoryObject(ItemCategory itemCategory) {
        super(itemCategory.getPrimaryKey());
        
        this.itemCategory = itemCategory;
    }

    private ItemCategoryDetail itemCategoryDetail; // Optional, use getItemCategoryDetail()
    
    private ItemCategoryDetail getItemCategoryDetail() {
        if(itemCategoryDetail == null) {
            itemCategoryDetail = itemCategory.getLastDetail();
        }
        
        return itemCategoryDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("item category name")
    @GraphQLNonNull
    public String getItemCategoryName() {
        return getItemCategoryDetail().getItemCategoryName();
    }

    @GraphQLField
    @GraphQLDescription("parent item category")
    public ItemCategoryObject getParentItemCategory() {
        ItemCategory parentItemCategory = getItemCategoryDetail().getParentItemCategory();
        
        return parentItemCategory == null ? null : new ItemCategoryObject(parentItemCategory);
    }
    
    @GraphQLField
    @GraphQLDescription("item sequence")
    public SequenceObject getItemSequence(final DataFetchingEnvironment env) {
        if(SequenceSecurityUtils.getInstance().getHasSequenceAccess(env)) {
            var itemSequence = getItemCategoryDetail().getItemSequence();

            return itemSequence == null ? null : new SequenceObject(itemSequence);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getItemCategoryDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getItemCategoryDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var itemControl = Session.getModelController(ItemControl.class);
        var userControl = Session.getModelController(UserControl.class);
        GraphQlContext context = env.getContext();
        
        return itemControl.getBestItemCategoryDescription(itemCategory, userControl.getPreferredLanguageFromUserVisit(context.getUserVisit()));
    }


    public class ObjectLimiter implements AutoCloseable {

        private static final String PARAMETER_FIRST = "first";
        private static final String PARAMETER_AFTER = "after";
        private static final String PARAMETER_LAST = "last";
        private static final String PARAMETER_BEFORE = "before";

        DataFetchingEnvironment env;
        String entityName;

        Map<String, Limit> limits;
        Limit savedLimit;
        long count;
        long limitOffset;
        long limitCount;

        public long getCount() {
            return count;
        }

        public long getLimitOffset() {
            return limitOffset;
        }

        public long getLimitCount() {
            return limitCount;
        }

        public ObjectLimiter(final DataFetchingEnvironment env, final String entityName, final long count) {
            this.env = env;
            this.entityName = entityName;
            this.count = count;

            var session = ThreadSession.currentSession();
            var after = Validator.validateUnsignedLong(env.getArgument(PARAMETER_AFTER));
            var before = Validator.validateUnsignedLong(env.getArgument(PARAMETER_BEFORE));
            var first = env.<Integer>getArgument(PARAMETER_FIRST);
            var afterEdge = after == null ? null : Long.valueOf(after);
            var last = env.<Integer>getArgument(PARAMETER_LAST);
            var beforeEdge = before == null ? null : Long.valueOf(before);

            // Initialize edges to be allEdges.
            limitOffset = 0;
            limitCount = count;

            // Source: https://relay.dev/graphql/connections.htm
            // 4.4 Pagination algorithm
            if(first != null || afterEdge != null || last != null || beforeEdge != null) {
                limits = session.getLimits();
                if(limits == null) {
                    limits = new HashMap<>();
                    session.setLimits(limits);
                }

                savedLimit = limits.get(entityName);

                // If after is set: && If afterEdge exists:
                if(afterEdge != null && afterEdge < count) {
                    // Remove all elements of edges before and including afterEdge.
                    limitOffset = afterEdge;
                    limitCount -= afterEdge;
                }

                // If before is set: && If beforeEdge exists:
                if(beforeEdge != null && beforeEdge <= count) {
                    // Remove all elements of edges after and including beforeEdge.
                    limitCount = beforeEdge - limitOffset - 1;
                }

                // TODO: If first is less than 0: Throw an error.

                // If first is set:
                if(first != null) {
                    // If edges has length greater than first:
                    if(limitCount > first) {
                        // Slice edges to be of length first by removing edges from the end of edges.
                        limitCount = first;
                    }
                }

                // TODO: If last is less than 0: Throw an error.

                // If last is set:
                if(last != null) {
                    // If edges has length greater than last:
                    if(limitCount > last) {
                        // Slice edges to be of length last by removing edges from the start of edges.
                        limitOffset = limitOffset + limitCount - last;
                        limitCount = last;
                    }
                }

                limits.put(entityName, new Limit(Long.toString(limitCount), Long.toString(limitOffset)));
            }
        }

        @Override
        public void close() {
            if(savedLimit != null) {
                // Restore previous Limit;
                limits.put(entityName, savedLimit);
            }
        }

    }

    public class LimitedObjects<BEIO extends BaseEntityInstanceObject>
            extends AbstractPaginatedData<BEIO> {

        long cursor;

        public LimitedObjects(ObjectLimiter objectLimiter, boolean hasPreviousPage, boolean hasNextPage, Iterable<BEIO> iterable) {
            super(objectLimiter.getLimitOffset() > 0,
                  objectLimiter.getLimitCount() < (objectLimiter.getCount() - objectLimiter.getLimitOffset()),
                  iterable);

            this.cursor = objectLimiter.getLimitOffset();
        }

        @Override
        public String getCursor(BEIO beio) {
            return Long.toString(cursor += 1);
        }

    }

    @GraphQLField
    @GraphQLDescription("items")
    @GraphQLConnection
    public PaginatedData<ItemObject> getItems(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getInstance().getHasItemsAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);

            try(var objectLimiter = new ObjectLimiter(env, ItemConstants.ENTITY_TYPE_NAME, itemControl.countItemsByItemCategory(itemCategory))) {
                var entities = itemControl.getItemsByItemCategory(itemCategory);
                var items = entities.stream().map(ItemObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new LimitedObjects<>(objectLimiter, false, false, items) {
                };
            }
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("item count")
    public Long getItemCount(final DataFetchingEnvironment env) {
        if(ItemSecurityUtils.getInstance().getHasItemsAccess(env)) {
            var itemControl = Session.getModelController(ItemControl.class);

            return itemControl.countItemsByItemCategory(itemCategory);
        } else {
            return null;
        }
    }

}
