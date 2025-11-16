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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.server.control.ComponentControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.UnitOfMeasureUtils;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EntityTypeTransferCache
        extends BaseCoreTransferCache<EntityType, EntityTypeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    ComponentControl componentControl = Session.getModelController(ComponentControl.class);
    CommentControl commentControl; // As-needed
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);
    IndexControl indexControl; // As-needed
    MessageControl messageControl; // As-needed
    RatingControl ratingControl; // As-needed
    UomControl uomControl = Session.getModelController(UomControl.class);
    UnitOfMeasureKind timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
    UnitOfMeasureUtils unitOfMeasureUtils = UnitOfMeasureUtils.getInstance();

    TransferProperties transferProperties;
    boolean filterComponentVendor;
    boolean filterEntityTypeName;
    boolean filterKeepAllHistory;
    boolean filterUnformattedLockTimeout;
    boolean filterLockTimeout;
    boolean filterIsExtensible;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;

    boolean includeIndexTypesCount;
    boolean includeIndexTypes;
    boolean includeEntityAttributes;
    boolean includeCommentTypes;
    boolean includeRatingTypes;
    boolean includeMessageTypes;
    boolean includeEntityInstancesCount;
    boolean includeEntityInstances;

    /** Creates a new instance of EntityTypeTransferCache */
    protected EntityTypeTransferCache() {
        super();
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(EntityTypeTransfer.class);
            
            if(properties != null) {
                filterComponentVendor = !properties.contains(CoreProperties.COMPONENT_VENDOR);
                filterEntityTypeName = !properties.contains(CoreProperties.ENTITY_TYPE_NAME);
                filterKeepAllHistory = !properties.contains(CoreProperties.KEEP_ALL_HISTORY);
                filterUnformattedLockTimeout = !properties.contains(CoreProperties.UNFORMATTED_LOCK_TIMEOUT);
                filterLockTimeout = !properties.contains(CoreProperties.LOCK_TIMEOUT);
                filterIsExtensible = !properties.contains(CoreProperties.IS_EXTENSIBLE);
                filterSortOrder = !properties.contains(CoreProperties.SORT_ORDER);
                filterDescription = !properties.contains(CoreProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(CoreProperties.ENTITY_INSTANCE);
            }
        }

        var options = session.getOptions();
        if(options != null) {
            includeIndexTypesCount = options.contains(CoreOptions.EntityTypeIncludeIndexTypesCount);
            includeIndexTypes = options.contains(CoreOptions.EntityTypeIncludeIndexTypes);
            includeEntityAttributes = options.contains(CoreOptions.EntityTypeIncludeEntityAttributes);
            includeCommentTypes = options.contains(CoreOptions.EntityTypeIncludeCommentTypes);
            includeRatingTypes = options.contains(CoreOptions.EntityTypeIncludeRatingTypes);
            includeMessageTypes = options.contains(CoreOptions.EntityTypeIncludeMessageTypes);
            includeEntityInstancesCount = options.contains(CoreOptions.EntityTypeIncludeEntityInstancesCount);
            includeEntityInstances = options.contains(CoreOptions.EntityTypeIncludeEntityInstances);

            if(includeCommentTypes) {
                commentControl = Session.getModelController(CommentControl.class);
            }

            if(includeIndexTypesCount || includeIndexTypes) {
                indexControl = Session.getModelController(IndexControl.class);
            }

            if(includeMessageTypes) {
                messageControl = Session.getModelController(MessageControl.class);
            }

            if(includeRatingTypes) {
                ratingControl = Session.getModelController(RatingControl.class);
            }
        }
    }
    
    public EntityTypeTransfer getEntityTypeTransfer(UserVisit userVisit, EntityType entityType) {
        var entityTypeTransfer = get(entityType);
        
        if(entityTypeTransfer == null) {
            var entityTypeDetail = entityType.getLastDetail();
            var unformattedLockTimeout = entityTypeDetail.getLockTimeout();
            
            entityTypeTransfer = new EntityTypeTransfer();
            put(userVisit, entityType, entityTypeTransfer);
            
            if(!filterComponentVendor) {
                entityTypeTransfer.setComponentVendor(componentControl.getComponentVendorTransfer(userVisit, entityTypeDetail.getComponentVendor()));
            }
            
            if(!filterEntityTypeName) {
                entityTypeTransfer.setEntityTypeName(entityTypeDetail.getEntityTypeName());
            }
            
            if(!filterKeepAllHistory) {
                entityTypeTransfer.setKeepAllHistory(entityTypeDetail.getKeepAllHistory());
            }
            
            if(!filterUnformattedLockTimeout) {
                entityTypeTransfer.setUnformattedLockTimeout(unformattedLockTimeout);
            }
            
            if(!filterLockTimeout) {
                entityTypeTransfer.setLockTimeout(unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedLockTimeout));
            }

            if(!filterSortOrder) {
                entityTypeTransfer.setSortOrder(entityTypeDetail.getSortOrder());
            }

            if(!filterIsExtensible) {
                entityTypeTransfer.setIsExtensible(entityTypeDetail.getIsExtensible());
            }

            if(!filterDescription) {
                entityTypeTransfer.setDescription(entityTypeControl.getBestEntityTypeDescription(entityType, getLanguage(userVisit)));
            }
            
            if(!filterEntityInstance) {
                entityTypeTransfer.setEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit, entityType, false, false, false, false));
            }
            
            setupEntityInstance(userVisit, entityType, null, entityTypeTransfer);
            
            if(includeIndexTypesCount) {
                entityTypeTransfer.setIndexTypesCount(indexControl.countIndexTypesByEntityType(entityType));
            }

            if(includeIndexTypes) {
                entityTypeTransfer.setIndexTypes(new ListWrapper<>(indexControl.getIndexTypeTransfersByEntityType(userVisit, entityType)));
            }

            if(includeEntityAttributes) {
                entityTypeTransfer.setEntityAttributes(new ListWrapper<>(coreControl.getEntityAttributeTransfersByEntityType(userVisit, entityType, null)));
            }

            if(includeCommentTypes) {
                entityTypeTransfer.setCommentTypes(new ListWrapper<>(commentControl.getCommentTypeTransfers(userVisit, entityType)));
            }

            if(includeRatingTypes) {
                entityTypeTransfer.setRatingTypes(new ListWrapper<>(ratingControl.getRatingTypeTransfers(userVisit, entityType)));
            }

            if(includeMessageTypes) {
                entityTypeTransfer.setMessageTypes(new ListWrapper<>(messageControl.getMessageTypeTransfers(userVisit, entityType)));
            }

            if(includeEntityInstancesCount) {
                entityTypeTransfer.setEntityInstancesCount(entityInstanceControl.countEntityInstancesByEntityType(entityType));
            }

            if(includeEntityInstances) {
                entityTypeTransfer.setEntityInstances(new ListWrapper<>(entityInstanceControl.getEntityInstanceTransfersByEntityType(userVisit, entityType, false, false, false, false)));
            }
        }
        
        return entityTypeTransfer;
    }
    
}
