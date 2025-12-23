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
import com.echothree.model.control.search.server.graphql.UserVisitSearchFacetIntegerObject;
import com.echothree.model.control.search.server.graphql.UserVisitSearchFacetListItemObject;
import com.echothree.model.control.search.server.graphql.UserVisitSearchFacetLongObject;
import com.echothree.model.control.search.server.graphql.UserVisitSearchFacetObject;
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
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class UserVisitSearchFacetLogic
        extends BaseLogic {

    protected UserVisitSearchFacetLogic() {
        super();
    }

    public static UserVisitSearchFacetLogic getInstance() {
        return CDI.current().select(UserVisitSearchFacetLogic.class).get();
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

    // Substantial portions of this are duplicated in getUserVisitSearchFacetObject(...).
    public UserVisitSearchFacetTransfer getUserVisitSearchFacetTransfer(final ExecutionErrorAccumulator eea, final UserVisitSearch userVisitSearch,
            final EntityAttribute entityAttribute) {
        final var coreControl = Session.getModelController(CoreControl.class);
        List<UserVisitSearchFacetListItemTransfer> userVisitSearchFacetListItemTransfers = null;
        List<UserVisitSearchFacetIntegerTransfer> userVisitSearchFacetIntegerTransfers = null;
        List<UserVisitSearchFacetLongTransfer> userVisitSearchFacetLongTransfers = null;
        final var userVisit = userVisitSearch.getUserVisit();
        final var entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

        if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
            final var entityListItemAttributeFacetResults = new EntityListItemAttributeFacetQuery(userVisitSearch).execute(entityAttribute);

            userVisitSearchFacetListItemTransfers = new ArrayList<>(entityListItemAttributeFacetResults.size());

            for(var entityListItemAttributeFacetResult : entityListItemAttributeFacetResults) {
                userVisitSearchFacetListItemTransfers.add(
                        new UserVisitSearchFacetListItemTransfer(coreControl.getEntityListItemTransfer(userVisit, entityListItemAttributeFacetResult.getEntityListItem(), null),
                                entityListItemAttributeFacetResult.getCount()));
            }
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
            final var entityMultipleListItemAttributeFacetResults = new EntityMultipleListItemAttributeFacetQuery(userVisitSearch).execute(entityAttribute);

            userVisitSearchFacetListItemTransfers = new ArrayList<>(entityMultipleListItemAttributeFacetResults.size());

            for(var entityListItemAttributeFacetResult : entityMultipleListItemAttributeFacetResults) {
                userVisitSearchFacetListItemTransfers.add(
                        new UserVisitSearchFacetListItemTransfer(coreControl.getEntityListItemTransfer(userVisit, entityListItemAttributeFacetResult.getEntityListItem(), null),
                                entityListItemAttributeFacetResult.getCount()));
            }
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
            final var entityIntegerRangeDetails = getEntityIntegerRangeDetails(entityAttribute);
            final var currentSize = entityIntegerRangeDetails.size();

            userVisitSearchFacetIntegerTransfers = new ArrayList<>(currentSize); // maximum size, may be smaller.

            if(currentSize > 0) {
                final var entityIntegerAttributeFacetResults = new EntityIntegerAttributeFacetQuery(userVisitSearch).execute(entityAttribute);

                for(var entityIntegerAttributeFacetResult : entityIntegerAttributeFacetResults) {
                    final var addedUserVisitSearchFacetIntegerTransfers = new ArrayList<UserVisitSearchFacetIntegerTransfer>();
                    final var integerAttribute = entityIntegerAttributeFacetResult.getIntegerAttribute();

                    for(var iter = entityIntegerRangeDetails.iterator(); iter.hasNext(); ) {
                        final var entityIntegerRangeDetail = iter.next();
                        final var minimumIntegerValue = entityIntegerRangeDetail.getMinimumIntegerValue();
                        final var maximumIntegerValue = entityIntegerRangeDetail.getMaximumIntegerValue();

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
                        final var entityIntegerRange = userVisitSearchFacetIntegerTransfer.getEntityIntegerRange();
                        final var minimumIntegerValue = entityIntegerRange.getMinimumIntegerValue();
                        final var maximumIntegerValue = entityIntegerRange.getMaximumIntegerValue();

                        if((minimumIntegerValue == null && maximumIntegerValue != null && integerAttribute <= maximumIntegerValue)
                                || (minimumIntegerValue != null && maximumIntegerValue == null && integerAttribute >= minimumIntegerValue)
                                || (minimumIntegerValue != null && maximumIntegerValue != null && integerAttribute >= minimumIntegerValue && integerAttribute <= maximumIntegerValue)) {
                            userVisitSearchFacetIntegerTransfer.setCount(userVisitSearchFacetIntegerTransfer.getCount() + entityIntegerAttributeFacetResult.getCount());
                        }
                    }

                    userVisitSearchFacetIntegerTransfers.addAll(addedUserVisitSearchFacetIntegerTransfers);
                }
            }

            userVisitSearchFacetIntegerTransfers.sort(
                    (UserVisitSearchFacetIntegerTransfer o1, UserVisitSearchFacetIntegerTransfer o2) -> Long.compare(o2.getCount(), o1.getCount())
            );
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
            final var entityLongRangeDetails = getEntityLongRangeDetails(entityAttribute);
            final var currentSize = entityLongRangeDetails.size();

            userVisitSearchFacetLongTransfers = new ArrayList<>(currentSize); // maximum size, may be smaller.

            if(currentSize > 0) {
                final var entityLongAttributeFacetResults = new EntityLongAttributeFacetQuery(userVisitSearch).execute(entityAttribute);

                for(var entityLongAttributeFacetResult : entityLongAttributeFacetResults) {
                    final var addedUserVisitSearchFacetLongTransfers = new ArrayList<UserVisitSearchFacetLongTransfer>();
                    final var longAttribute = entityLongAttributeFacetResult.getLongAttribute();

                    for(var iter = entityLongRangeDetails.iterator(); iter.hasNext(); ) {
                        final var entityLongRangeDetail = iter.next();
                        final var minimumLongValue = entityLongRangeDetail.getMinimumLongValue();
                        final var maximumLongValue = entityLongRangeDetail.getMaximumLongValue();

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
                        final var entityLongRange = userVisitSearchFacetLongTransfer.getEntityLongRange();
                        final var minimumLongValue = entityLongRange.getMinimumLongValue();
                        final var maximumLongValue = entityLongRange.getMaximumLongValue();

                        if((minimumLongValue == null && maximumLongValue != null && longAttribute <= maximumLongValue)
                                || (minimumLongValue != null && maximumLongValue == null && longAttribute >= minimumLongValue)
                                || (minimumLongValue != null && maximumLongValue != null && longAttribute >= minimumLongValue && longAttribute <= maximumLongValue)) {
                            userVisitSearchFacetLongTransfer.setCount(userVisitSearchFacetLongTransfer.getCount() + entityLongAttributeFacetResult.getCount());
                        }
                    }

                    userVisitSearchFacetLongTransfers.addAll(addedUserVisitSearchFacetLongTransfers);
                }
            }

            userVisitSearchFacetLongTransfers.sort(
                    (UserVisitSearchFacetLongTransfer o1, UserVisitSearchFacetLongTransfer o2) -> Long.compare(o2.getCount(), o1.getCount())
            );
        } else {
            handleExecutionError(InvalidEntityAttributeTypeException.class, eea, ExecutionErrors.InvalidEntityAttributeType.name(), entityAttributeTypeName);
        }

        return eea.hasExecutionErrors() ? null : new UserVisitSearchFacetTransfer(coreControl.getEntityAttributeTransfer(userVisit, entityAttribute, null),
                userVisitSearchFacetListItemTransfers == null ? null : new ListWrapper<>(userVisitSearchFacetListItemTransfers),
                userVisitSearchFacetIntegerTransfers == null ? null : new ListWrapper<>(userVisitSearchFacetIntegerTransfers),
                userVisitSearchFacetLongTransfers == null ? null : new ListWrapper<>(userVisitSearchFacetLongTransfers));
    }

    // Substantial portions of this are duplicated in getUserVisitSearchFacetTransfer(...).
    public UserVisitSearchFacetObject getUserVisitSearchFacetObject(final ExecutionErrorAccumulator eea, final UserVisitSearch userVisitSearch,
            final EntityAttribute entityAttribute) {
        List<UserVisitSearchFacetListItemObject> userVisitSearchFacetListItemObjects = null;
        List<UserVisitSearchFacetIntegerObject> userVisitSearchFacetIntegerObjects = null;
        List<UserVisitSearchFacetLongObject> userVisitSearchFacetLongObjects = null;
        final var entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

        if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
            final var entityListItemAttributeFacetResults = new EntityListItemAttributeFacetQuery(userVisitSearch).execute(entityAttribute);

            userVisitSearchFacetListItemObjects = new ArrayList<>(entityListItemAttributeFacetResults.size());

            for(var entityListItemAttributeFacetResult : entityListItemAttributeFacetResults) {
                userVisitSearchFacetListItemObjects.add(
                        new UserVisitSearchFacetListItemObject(entityListItemAttributeFacetResult.getEntityListItem(),
                                entityListItemAttributeFacetResult.getCount()));
            }
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
            final var entityMultipleListItemAttributeFacetResults = new EntityMultipleListItemAttributeFacetQuery(userVisitSearch).execute(entityAttribute);

            userVisitSearchFacetListItemObjects = new ArrayList<>(entityMultipleListItemAttributeFacetResults.size());

            for(var entityListItemAttributeFacetResult : entityMultipleListItemAttributeFacetResults) {
                userVisitSearchFacetListItemObjects.add(
                        new UserVisitSearchFacetListItemObject(entityListItemAttributeFacetResult.getEntityListItem(),
                                entityListItemAttributeFacetResult.getCount()));
            }
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
            final var entityIntegerRangeDetails = getEntityIntegerRangeDetails(entityAttribute);
            final var currentSize = entityIntegerRangeDetails.size();

            userVisitSearchFacetIntegerObjects = new ArrayList<>(currentSize); // maximum size, may be smaller.

            if(currentSize > 0) {
                final var entityIntegerAttributeFacetResults = new EntityIntegerAttributeFacetQuery(userVisitSearch).execute(entityAttribute);

                for(var entityIntegerAttributeFacetResult : entityIntegerAttributeFacetResults) {
                    final var addedUserVisitSearchFacetIntegerObjects = new ArrayList<UserVisitSearchFacetIntegerObject>();
                    final var integerAttribute = entityIntegerAttributeFacetResult.getIntegerAttribute();

                    for(var iter = entityIntegerRangeDetails.iterator(); iter.hasNext(); ) {
                        final var entityIntegerRangeDetail = iter.next();
                        final var minimumIntegerValue = entityIntegerRangeDetail.getMinimumIntegerValue();
                        final var maximumIntegerValue = entityIntegerRangeDetail.getMaximumIntegerValue();

                        if((minimumIntegerValue == null && maximumIntegerValue != null && integerAttribute <= maximumIntegerValue)
                                || (minimumIntegerValue != null && maximumIntegerValue == null && integerAttribute >= minimumIntegerValue)
                                || (minimumIntegerValue != null && maximumIntegerValue != null && integerAttribute >= minimumIntegerValue && integerAttribute <= maximumIntegerValue)) {
                            addedUserVisitSearchFacetIntegerObjects.add(
                                    new UserVisitSearchFacetIntegerObject(entityIntegerRangeDetail.getEntityIntegerRange(),
                                            entityIntegerAttributeFacetResult.getCount()));
                            iter.remove();
                        }
                    }

                    for(var userVisitSearchFacetIntegerObject : userVisitSearchFacetIntegerObjects) {
                        final var entityIntegerRange = userVisitSearchFacetIntegerObject.getEntityIntegerRange();
                        final var minimumIntegerValue = entityIntegerRange.getLastDetail().getMinimumIntegerValue();
                        final var maximumIntegerValue = entityIntegerRange.getLastDetail().getMaximumIntegerValue();

                        if((minimumIntegerValue == null && maximumIntegerValue != null && integerAttribute <= maximumIntegerValue)
                                || (minimumIntegerValue != null && maximumIntegerValue == null && integerAttribute >= minimumIntegerValue)
                                || (minimumIntegerValue != null && maximumIntegerValue != null && integerAttribute >= minimumIntegerValue && integerAttribute <= maximumIntegerValue)) {
                            userVisitSearchFacetIntegerObject.setCount(userVisitSearchFacetIntegerObject.getCount() + entityIntegerAttributeFacetResult.getCount());
                        }
                    }

                    userVisitSearchFacetIntegerObjects.addAll(addedUserVisitSearchFacetIntegerObjects);
                }
            }

            userVisitSearchFacetIntegerObjects.sort(
                    (UserVisitSearchFacetIntegerObject o1, UserVisitSearchFacetIntegerObject o2) -> Long.compare(o2.getCount(), o1.getCount())
            );
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
            final var entityLongRangeDetails = getEntityLongRangeDetails(entityAttribute);
            final var currentSize = entityLongRangeDetails.size();

            userVisitSearchFacetLongObjects = new ArrayList<>(currentSize); // maximum size, may be smaller.

            if(currentSize > 0) {
                final var entityLongAttributeFacetResults = new EntityLongAttributeFacetQuery(userVisitSearch).execute(entityAttribute);

                for(var entityLongAttributeFacetResult : entityLongAttributeFacetResults) {
                    final var addedUserVisitSearchFacetLongObjects = new ArrayList<UserVisitSearchFacetLongObject>();
                    final var longAttribute = entityLongAttributeFacetResult.getLongAttribute();

                    for(var iter = entityLongRangeDetails.iterator(); iter.hasNext(); ) {
                        final var entityLongRangeDetail = iter.next();
                        final var minimumLongValue = entityLongRangeDetail.getMinimumLongValue();
                        final var maximumLongValue = entityLongRangeDetail.getMaximumLongValue();

                        if((minimumLongValue == null && maximumLongValue != null && longAttribute <= maximumLongValue)
                                || (minimumLongValue != null && maximumLongValue == null && longAttribute >= minimumLongValue)
                                || (minimumLongValue != null && maximumLongValue != null && longAttribute >= minimumLongValue && longAttribute <= maximumLongValue)) {
                            addedUserVisitSearchFacetLongObjects.add(
                                    new UserVisitSearchFacetLongObject(entityLongRangeDetail.getEntityLongRange(),
                                            entityLongAttributeFacetResult.getCount()));
                            iter.remove();
                        }
                    }

                    for(var userVisitSearchFacetLongObject : userVisitSearchFacetLongObjects) {
                        final var entityLongRange = userVisitSearchFacetLongObject.getEntityLongRange();
                        final var minimumLongValue = entityLongRange.getLastDetail().getMinimumLongValue();
                        final var maximumLongValue = entityLongRange.getLastDetail().getMaximumLongValue();

                        if((minimumLongValue == null && maximumLongValue != null && longAttribute <= maximumLongValue)
                                || (minimumLongValue != null && maximumLongValue == null && longAttribute >= minimumLongValue)
                                || (minimumLongValue != null && maximumLongValue != null && longAttribute >= minimumLongValue && longAttribute <= maximumLongValue)) {
                            userVisitSearchFacetLongObject.setCount(userVisitSearchFacetLongObject.getCount() + entityLongAttributeFacetResult.getCount());
                        }
                    }

                    userVisitSearchFacetLongObjects.addAll(addedUserVisitSearchFacetLongObjects);
                }
            }

            userVisitSearchFacetLongObjects.sort(
                    (UserVisitSearchFacetLongObject o1, UserVisitSearchFacetLongObject o2) -> Long.compare(o2.getCount(), o1.getCount())
            );
        } else {
            handleExecutionError(InvalidEntityAttributeTypeException.class, eea, ExecutionErrors.InvalidEntityAttributeType.name(), entityAttributeTypeName);
        }

        return (eea != null && eea.hasExecutionErrors()) ? null : new UserVisitSearchFacetObject(entityAttribute,
                userVisitSearchFacetListItemObjects, userVisitSearchFacetIntegerObjects, userVisitSearchFacetLongObjects);
    }

}
