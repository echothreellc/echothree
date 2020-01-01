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

package com.echothree.model.control.search.server.logic;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.exception.InvalidEntityAttributeTypeException;
import com.echothree.model.control.core.common.transfer.EntityIntegerRangeTransfer;
import com.echothree.model.control.core.common.transfer.EntityLongRangeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.search.common.transfer.UserVisitSearchFacetIntegerTransfer;
import com.echothree.model.control.search.common.transfer.UserVisitSearchFacetListItemTransfer;
import com.echothree.model.control.search.common.transfer.UserVisitSearchFacetLongTransfer;
import com.echothree.model.control.search.common.transfer.UserVisitSearchFacetTransfer;
import com.echothree.model.control.search.server.database.EntityIntegerAttributeFacetQuery;
import com.echothree.model.control.search.server.database.EntityIntegerAttributeFacetResult;
import com.echothree.model.control.search.server.database.EntityListItemAttributeFacetQuery;
import com.echothree.model.control.search.server.database.EntityListItemAttributeFacetResult;
import com.echothree.model.control.search.server.database.EntityLongAttributeFacetQuery;
import com.echothree.model.control.search.server.database.EntityLongAttributeFacetResult;
import com.echothree.model.control.search.server.database.EntityMultipleListItemAttributeFacetQuery;
import com.echothree.model.control.search.server.database.EntityMultipleListItemAttributeFacetResult;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityIntegerRange;
import com.echothree.model.data.core.server.entity.EntityIntegerRangeDetail;
import com.echothree.model.data.core.server.entity.EntityLongRange;
import com.echothree.model.data.core.server.entity.EntityLongRangeDetail;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
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
        final CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        final List<EntityIntegerRange> entityIntegerRanges = coreControl.getEntityIntegerRanges(entityAttribute);
        final List<EntityIntegerRangeDetail> entityIntegerRangeDetails = new ArrayList<>(entityIntegerRanges.size());
        
        entityIntegerRanges.stream().forEach((entityIntegerRange) -> {
            entityIntegerRangeDetails.add(entityIntegerRange.getLastDetail());
        });
        
        return entityIntegerRangeDetails;
    }
    
    private List<EntityLongRangeDetail> getEntityLongRangeDetails(final EntityAttribute entityAttribute) {
        final CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        final List<EntityLongRange> entityLongRanges = coreControl.getEntityLongRanges(entityAttribute);
        final List<EntityLongRangeDetail> entityLongRangeDetails = new ArrayList<>(entityLongRanges.size());
        
        entityLongRanges.stream().forEach((entityLongRange) -> {
            entityLongRangeDetails.add(entityLongRange.getLastDetail());
        });
        
        return entityLongRangeDetails;
    }
    
    public UserVisitSearchFacetTransfer getUserVisitSearchFacetTransfer(final ExecutionErrorAccumulator eea, final UserVisitSearch userVisitSearch,
            final EntityAttribute entityAttribute) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        List<UserVisitSearchFacetListItemTransfer> userVisitSearchFacetListItemTransfers = null;
        List<UserVisitSearchFacetIntegerTransfer> userVisitSearchFacetIntegerTransfers = null;
        List<UserVisitSearchFacetLongTransfer> userVisitSearchFacetLongTransfers = null;
        UserVisit userVisit = userVisitSearch.getUserVisit();
        String entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
                
        if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
            List<EntityListItemAttributeFacetResult> entityListItemAttributeFacetResults = new EntityListItemAttributeFacetQuery(userVisitSearch).execute(entityAttribute);

            userVisitSearchFacetListItemTransfers = new ArrayList<>(entityListItemAttributeFacetResults.size());

            for(EntityListItemAttributeFacetResult entityListItemAttributeFacetResult : entityListItemAttributeFacetResults) {
                userVisitSearchFacetListItemTransfers.add(
                        new UserVisitSearchFacetListItemTransfer(coreControl.getEntityListItemTransfer(userVisit, entityListItemAttributeFacetResult.getEntityListItem(), null),
                        entityListItemAttributeFacetResult.getCount()));
            }
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
            List<EntityMultipleListItemAttributeFacetResult> entityMultipleListItemAttributeFacetResults = new EntityMultipleListItemAttributeFacetQuery(userVisitSearch).execute(entityAttribute);

            userVisitSearchFacetListItemTransfers = new ArrayList<>(entityMultipleListItemAttributeFacetResults.size());

            for(EntityListItemAttributeFacetResult entityListItemAttributeFacetResult : entityMultipleListItemAttributeFacetResults) {
                userVisitSearchFacetListItemTransfers.add(
                        new UserVisitSearchFacetListItemTransfer(coreControl.getEntityListItemTransfer(userVisit, entityListItemAttributeFacetResult.getEntityListItem(), null),
                        entityListItemAttributeFacetResult.getCount()));
            }
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
            List<EntityIntegerRangeDetail> entityIntegerRangeDetails = getEntityIntegerRangeDetails(entityAttribute);
            int currentSize = entityIntegerRangeDetails.size();
            
            userVisitSearchFacetIntegerTransfers = new ArrayList<>(currentSize); // maximum size, may be smaller.
            
            if(currentSize > 0) {
                List<EntityIntegerAttributeFacetResult> entityIntegerAttributeFacetResults = new EntityIntegerAttributeFacetQuery(userVisitSearch).execute(entityAttribute);
                
                for(EntityIntegerAttributeFacetResult entityIntegerAttributeFacetResult : entityIntegerAttributeFacetResults) {
                    List<UserVisitSearchFacetIntegerTransfer> addedUserVisitSearchFacetIntegerTransfers = new ArrayList<>();
                    int integerAttribute = entityIntegerAttributeFacetResult.getIntegerAttribute();
                    
                    for(Iterator<EntityIntegerRangeDetail> iter = entityIntegerRangeDetails.iterator(); iter.hasNext(); ) {
                        EntityIntegerRangeDetail entityIntegerRangeDetail = iter.next();
                        Integer minimumIntegerValue = entityIntegerRangeDetail.getMinimumIntegerValue();
                        Integer maximumIntegerValue = entityIntegerRangeDetail.getMaximumIntegerValue();
                        
                        if((minimumIntegerValue == null && maximumIntegerValue != null && integerAttribute <= maximumIntegerValue)
                                || (minimumIntegerValue != null && maximumIntegerValue == null && integerAttribute >= minimumIntegerValue)
                                || (minimumIntegerValue != null && maximumIntegerValue != null && integerAttribute >= minimumIntegerValue && integerAttribute <= maximumIntegerValue)) {
                            addedUserVisitSearchFacetIntegerTransfers.add(
                                    new UserVisitSearchFacetIntegerTransfer(coreControl.getEntityIntegerRangeTransfer(userVisit, entityIntegerRangeDetail.getEntityIntegerRange(), null),
                                    entityIntegerAttributeFacetResult.getCount()));
                            iter.remove();
                        }
                    }
                    
                    for(UserVisitSearchFacetIntegerTransfer userVisitSearchFacetIntegerTransfer : userVisitSearchFacetIntegerTransfers) {
                        EntityIntegerRangeTransfer entityIntegerRange = userVisitSearchFacetIntegerTransfer.getEntityIntegerRange();
                        Integer minimumIntegerValue = entityIntegerRange.getMinimumIntegerValue();
                        Integer maximumIntegerValue = entityIntegerRange.getMaximumIntegerValue();
                        
                        if((minimumIntegerValue == null && maximumIntegerValue != null && integerAttribute <= maximumIntegerValue)
                                || (minimumIntegerValue != null && maximumIntegerValue == null && integerAttribute >= minimumIntegerValue)
                                || (minimumIntegerValue != null && maximumIntegerValue != null && integerAttribute >= minimumIntegerValue && integerAttribute <= maximumIntegerValue)) {
                            userVisitSearchFacetIntegerTransfer.setCount(userVisitSearchFacetIntegerTransfer.getCount() + entityIntegerAttributeFacetResult.getCount());
                        }
                    }
                    
                    userVisitSearchFacetIntegerTransfers.addAll(addedUserVisitSearchFacetIntegerTransfers);
                }
            }

            Collections.sort(userVisitSearchFacetIntegerTransfers, (UserVisitSearchFacetIntegerTransfer o1, UserVisitSearchFacetIntegerTransfer o2) -> {
                int o1Count = o1.getCount(), o2Count = o2.getCount();
                int result;
                
                if(o1Count < o2Count) {
                    result = 1;
                } else if(o1Count > o2Count) {
                    result = -1;
                } else {
                    result = 0;
                }
                
                return result;
            });
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
            List<EntityLongRangeDetail> entityLongRangeDetails = getEntityLongRangeDetails(entityAttribute);
            int currentSize = entityLongRangeDetails.size();
            
            userVisitSearchFacetLongTransfers = new ArrayList<>(currentSize); // maximum size, may be smaller.
            
            if(currentSize > 0) {
                List<EntityLongAttributeFacetResult> entityLongAttributeFacetResults = new EntityLongAttributeFacetQuery(userVisitSearch).execute(entityAttribute);
                
                for(EntityLongAttributeFacetResult entityLongAttributeFacetResult : entityLongAttributeFacetResults) {
                    List<UserVisitSearchFacetLongTransfer> addedUserVisitSearchFacetLongTransfers = new ArrayList<>();
                    long longAttribute = entityLongAttributeFacetResult.getLongAttribute();
                    
                    for(Iterator<EntityLongRangeDetail> iter = entityLongRangeDetails.iterator(); iter.hasNext(); ) {
                        EntityLongRangeDetail entityLongRangeDetail = iter.next();
                        Long minimumLongValue = entityLongRangeDetail.getMinimumLongValue();
                        Long maximumLongValue = entityLongRangeDetail.getMaximumLongValue();
                        
                        if((minimumLongValue == null && maximumLongValue != null && longAttribute <= maximumLongValue)
                                || (minimumLongValue != null && maximumLongValue == null && longAttribute >= minimumLongValue)
                                || (minimumLongValue != null && maximumLongValue != null && longAttribute >= minimumLongValue && longAttribute <= maximumLongValue)) {
                            addedUserVisitSearchFacetLongTransfers.add(
                                    new UserVisitSearchFacetLongTransfer(coreControl.getEntityLongRangeTransfer(userVisit, entityLongRangeDetail.getEntityLongRange(), null),
                                    entityLongAttributeFacetResult.getCount()));
                            iter.remove();
                        }
                    }
                    
                    for(UserVisitSearchFacetLongTransfer userVisitSearchFacetLongTransfer : userVisitSearchFacetLongTransfers) {
                        EntityLongRangeTransfer entityLongRange = userVisitSearchFacetLongTransfer.getEntityLongRange();
                        Long minimumLongValue = entityLongRange.getMinimumLongValue();
                        Long maximumLongValue = entityLongRange.getMaximumLongValue();
                        
                        if((minimumLongValue == null && maximumLongValue != null && longAttribute <= maximumLongValue)
                                || (minimumLongValue != null && maximumLongValue == null && longAttribute >= minimumLongValue)
                                || (minimumLongValue != null && maximumLongValue != null && longAttribute >= minimumLongValue && longAttribute <= maximumLongValue)) {
                            userVisitSearchFacetLongTransfer.setCount(userVisitSearchFacetLongTransfer.getCount() + entityLongAttributeFacetResult.getCount());
                        }
                    }
                    
                    userVisitSearchFacetLongTransfers.addAll(addedUserVisitSearchFacetLongTransfers);
                }
            }
            
            Collections.sort(userVisitSearchFacetLongTransfers, (UserVisitSearchFacetLongTransfer o1, UserVisitSearchFacetLongTransfer o2) -> {
                long o1Count = o1.getCount(), o2Count = o2.getCount();
                int result;
                
                if(o1Count < o2Count) {
                    result = 1;
                } else if(o1Count > o2Count) {
                    result = -1;
                } else {
                    result = 0;
                }
                
                return result;
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
