// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.search.server.logic;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.exception.InvalidEntityAttributeTypeException;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.search.common.transfer.UserVisitSearchFacetIntegerTransfer;
import com.echothree.model.control.search.common.transfer.UserVisitSearchFacetListItemTransfer;
import com.echothree.model.control.search.common.transfer.UserVisitSearchFacetLongTransfer;
import com.echothree.model.control.search.common.transfer.UserVisitSearchFacetTransfer;
import com.echothree.model.control.search.server.database.EntityIntegerAttributeFacetQuery;
import com.echothree.model.control.search.server.database.EntityListItemAttributeFacetQuery;
import com.echothree.model.control.search.server.database.EntityLongAttributeFacetQuery;
import com.echothree.model.control.search.server.database.EntityMultipleListItemAttributeFacetQuery;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityIntegerRangeDetail;
import com.echothree.model.data.core.server.entity.EntityLongRangeDetail;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserVisitSearchFacetLogic
        extends BaseLogic {

    private UserVisitSearchFacetLogic() {
        super();
    }

    private static class UserVisitSearchFacetLogicHolder {
        static UserVisitSearchFacetLogic instance = new UserVisitSearchFacetLogic();
    }

    public static UserVisitSearchFacetLogic getInstance() {
        return UserVisitSearchFacetLogicHolder.instance;
    }
    
    private List<EntityIntegerRangeDetail> getEntityIntegerRangeDetails(final EntityAttribute entityAttribute) {
        final var coreControl = Session.getModelController(CoreControl.class);
        final var entityIntegerRanges = coreControl.getEntityIntegerRanges(entityAttribute);
        final var entityIntegerRangeDetails = new ArrayList<EntityIntegerRangeDetail>(entityIntegerRanges.size());
        
        entityIntegerRanges.forEach((entityIntegerRange) -> {
            entityIntegerRangeDetails.add(entityIntegerRange.getLastDetail());
        });
        
        return entityIntegerRangeDetails;
    }
    
    private List<EntityLongRangeDetail> getEntityLongRangeDetails(final EntityAttribute entityAttribute) {
        final var coreControl = Session.getModelController(CoreControl.class);
        final var entityLongRanges = coreControl.getEntityLongRanges(entityAttribute);
        final var entityLongRangeDetails = new ArrayList<EntityLongRangeDetail>(entityLongRanges.size());
        
        entityLongRanges.forEach((entityLongRange) -> {
            entityLongRangeDetails.add(entityLongRange.getLastDetail());
        });
        
        return entityLongRangeDetails;
    }
    
    public UserVisitSearchFacetTransfer getUserVisitSearchFacetTransfer(final ExecutionErrorAccumulator eea, final UserVisitSearch userVisitSearch,
            final EntityAttribute entityAttribute) {
        var coreControl = Session.getModelController(CoreControl.class);
        List<UserVisitSearchFacetListItemTransfer> userVisitSearchFacetListItemTransfers = null;
        List<UserVisitSearchFacetIntegerTransfer> userVisitSearchFacetIntegerTransfers = null;
        List<UserVisitSearchFacetLongTransfer> userVisitSearchFacetLongTransfers = null;
        var userVisit = userVisitSearch.getUserVisit();
        var entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
                
        if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
            var entityListItemAttributeFacetResults = new EntityListItemAttributeFacetQuery(userVisitSearch).execute(entityAttribute);

            userVisitSearchFacetListItemTransfers = new ArrayList<>(entityListItemAttributeFacetResults.size());

            for(var entityListItemAttributeFacetResult : entityListItemAttributeFacetResults) {
                userVisitSearchFacetListItemTransfers.add(
                        new UserVisitSearchFacetListItemTransfer(coreControl.getEntityListItemTransfer(userVisit, entityListItemAttributeFacetResult.getEntityListItem(), null),
                        entityListItemAttributeFacetResult.getCount()));
            }
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
            var entityMultipleListItemAttributeFacetResults = new EntityMultipleListItemAttributeFacetQuery(userVisitSearch).execute(entityAttribute);

            userVisitSearchFacetListItemTransfers = new ArrayList<>(entityMultipleListItemAttributeFacetResults.size());

            for(var entityListItemAttributeFacetResult : entityMultipleListItemAttributeFacetResults) {
                userVisitSearchFacetListItemTransfers.add(
                        new UserVisitSearchFacetListItemTransfer(coreControl.getEntityListItemTransfer(userVisit, entityListItemAttributeFacetResult.getEntityListItem(), null),
                        entityListItemAttributeFacetResult.getCount()));
            }
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
            var entityIntegerRangeDetails = getEntityIntegerRangeDetails(entityAttribute);
            var currentSize = entityIntegerRangeDetails.size();
            
            userVisitSearchFacetIntegerTransfers = new ArrayList<>(currentSize); // maximum size, may be smaller.
            
            if(currentSize > 0) {
                var entityIntegerAttributeFacetResults = new EntityIntegerAttributeFacetQuery(userVisitSearch).execute(entityAttribute);
                
                for(var entityIntegerAttributeFacetResult : entityIntegerAttributeFacetResults) {
                    var addedUserVisitSearchFacetIntegerTransfers = new ArrayList<UserVisitSearchFacetIntegerTransfer>();
                    var integerAttribute = entityIntegerAttributeFacetResult.getIntegerAttribute();
                    
                    for(Iterator<EntityIntegerRangeDetail> iter = entityIntegerRangeDetails.iterator(); iter.hasNext(); ) {
                        var entityIntegerRangeDetail = iter.next();
                        var minimumIntegerValue = entityIntegerRangeDetail.getMinimumIntegerValue();
                        var maximumIntegerValue = entityIntegerRangeDetail.getMaximumIntegerValue();
                        
                        if((minimumIntegerValue == null && maximumIntegerValue != null && integerAttribute <= maximumIntegerValue)
                                || (minimumIntegerValue != null && maximumIntegerValue == null && integerAttribute >= minimumIntegerValue)
                                || (minimumIntegerValue != null && maximumIntegerValue != null && integerAttribute >= minimumIntegerValue && integerAttribute <= maximumIntegerValue)) {
                            addedUserVisitSearchFacetIntegerTransfers.add(
                                    new UserVisitSearchFacetIntegerTransfer(coreControl.getEntityIntegerRangeTransfer(userVisit, entityIntegerRangeDetail.getEntityIntegerRange(), null),
                                    entityIntegerAttributeFacetResult.getCount()));
                            iter.remove();
                        }
                    }
                    
                    for(var userVisitSearchFacetIntegerTransfer : userVisitSearchFacetIntegerTransfers) {
                        var entityIntegerRange = userVisitSearchFacetIntegerTransfer.getEntityIntegerRange();
                        var minimumIntegerValue = entityIntegerRange.getMinimumIntegerValue();
                        var maximumIntegerValue = entityIntegerRange.getMaximumIntegerValue();
                        
                        if((minimumIntegerValue == null && maximumIntegerValue != null && integerAttribute <= maximumIntegerValue)
                                || (minimumIntegerValue != null && maximumIntegerValue == null && integerAttribute >= minimumIntegerValue)
                                || (minimumIntegerValue != null && maximumIntegerValue != null && integerAttribute >= minimumIntegerValue && integerAttribute <= maximumIntegerValue)) {
                            userVisitSearchFacetIntegerTransfer.setCount(userVisitSearchFacetIntegerTransfer.getCount() + entityIntegerAttributeFacetResult.getCount());
                        }
                    }
                    
                    userVisitSearchFacetIntegerTransfers.addAll(addedUserVisitSearchFacetIntegerTransfers);
                }
            }

            userVisitSearchFacetIntegerTransfers.sort((UserVisitSearchFacetIntegerTransfer o1, UserVisitSearchFacetIntegerTransfer o2) -> {
                int o1Count = o1.getCount();
                int o2Count = o2.getCount();

                return Integer.compare(o2Count, o1Count);
            });
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
            var entityLongRangeDetails = getEntityLongRangeDetails(entityAttribute);
            var currentSize = entityLongRangeDetails.size();
            
            userVisitSearchFacetLongTransfers = new ArrayList<>(currentSize); // maximum size, may be smaller.
            
            if(currentSize > 0) {
                var entityLongAttributeFacetResults = new EntityLongAttributeFacetQuery(userVisitSearch).execute(entityAttribute);
                
                for(var entityLongAttributeFacetResult : entityLongAttributeFacetResults) {
                    var addedUserVisitSearchFacetLongTransfers = new ArrayList<UserVisitSearchFacetLongTransfer>();
                    var longAttribute = entityLongAttributeFacetResult.getLongAttribute();
                    
                    for(Iterator<EntityLongRangeDetail> iter = entityLongRangeDetails.iterator(); iter.hasNext(); ) {
                        var entityLongRangeDetail = iter.next();
                        var minimumLongValue = entityLongRangeDetail.getMinimumLongValue();
                        var maximumLongValue = entityLongRangeDetail.getMaximumLongValue();
                        
                        if((minimumLongValue == null && maximumLongValue != null && longAttribute <= maximumLongValue)
                                || (minimumLongValue != null && maximumLongValue == null && longAttribute >= minimumLongValue)
                                || (minimumLongValue != null && maximumLongValue != null && longAttribute >= minimumLongValue && longAttribute <= maximumLongValue)) {
                            addedUserVisitSearchFacetLongTransfers.add(
                                    new UserVisitSearchFacetLongTransfer(coreControl.getEntityLongRangeTransfer(userVisit, entityLongRangeDetail.getEntityLongRange(), null),
                                    entityLongAttributeFacetResult.getCount()));
                            iter.remove();
                        }
                    }
                    
                    for(var userVisitSearchFacetLongTransfer : userVisitSearchFacetLongTransfers) {
                        var entityLongRange = userVisitSearchFacetLongTransfer.getEntityLongRange();
                        var minimumLongValue = entityLongRange.getMinimumLongValue();
                        var maximumLongValue = entityLongRange.getMaximumLongValue();
                        
                        if((minimumLongValue == null && maximumLongValue != null && longAttribute <= maximumLongValue)
                                || (minimumLongValue != null && maximumLongValue == null && longAttribute >= minimumLongValue)
                                || (minimumLongValue != null && maximumLongValue != null && longAttribute >= minimumLongValue && longAttribute <= maximumLongValue)) {
                            userVisitSearchFacetLongTransfer.setCount(userVisitSearchFacetLongTransfer.getCount() + entityLongAttributeFacetResult.getCount());
                        }
                    }
                    
                    userVisitSearchFacetLongTransfers.addAll(addedUserVisitSearchFacetLongTransfers);
                }
            }
            
            userVisitSearchFacetLongTransfers.sort((UserVisitSearchFacetLongTransfer o1, UserVisitSearchFacetLongTransfer o2) -> {
                long o1Count = o1.getCount();
                long o2Count = o2.getCount();

                return Long.compare(o2Count, o1Count);
            });
        } else {
            handleExecutionError(InvalidEntityAttributeTypeException.class, eea, ExecutionErrors.InvalidEntityAttributeType.name(), entityAttributeTypeName);
        }
        
        return eea.hasExecutionErrors() ? null : new UserVisitSearchFacetTransfer(coreControl.getEntityAttributeTransfer(userVisit, entityAttribute, null),
                userVisitSearchFacetListItemTransfers == null ? null : new ListWrapper<>(userVisitSearchFacetListItemTransfers),
                userVisitSearchFacetIntegerTransfers == null ? null : new ListWrapper<>(userVisitSearchFacetIntegerTransfers),
                userVisitSearchFacetLongTransfers == null ? null : new ListWrapper<>(userVisitSearchFacetLongTransfers));
    }
    
}
