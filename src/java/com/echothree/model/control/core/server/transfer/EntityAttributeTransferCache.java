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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.transfer.EntityAttributeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EntityAttributeTransferCache
        extends BaseCoreTransferCache<EntityAttribute, EntityAttributeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);

    boolean includeDefault;
    boolean includeValue;
    boolean includeEntityListItems;
    boolean includeEntityListItemsCount;
    boolean includeEntityAttributeEntityTypes;
    boolean includeEntityAttributeEntityTypesCount;

    TransferProperties transferProperties;
    boolean filterEntityType;
    boolean filterEntityAttributeName;
    boolean filterEntityAttributeType;
    boolean filterTrackRevisions;
    boolean filterCheckContentWebAddress;
    boolean filterValidationPattern;
    boolean filterEntityListItemSequence;
    boolean filterUnitOfMeasureType;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;

    /** Creates a new instance of EntityAttributeTransferCache */
    protected EntityAttributeTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeDefault = options.contains(CoreOptions.EntityAttributeIncludeDefault);
            includeValue = options.contains(CoreOptions.EntityAttributeIncludeValue);
            includeEntityListItems = options.contains(CoreOptions.EntityAttributeIncludeEntityListItems);
            includeEntityListItemsCount = options.contains(CoreOptions.EntityAttributeIncludeEntityListItemsCount);
            includeEntityAttributeEntityTypes = options.contains(CoreOptions.EntityAttributeIncludeEntityAttributeEntityTypes);
            includeEntityAttributeEntityTypesCount = options.contains(CoreOptions.EntityAttributeIncludeEntityAttributeEntityTypesCount);
        }
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(EntityAttributeTransfer.class);
            
            if(properties != null) {
                filterEntityType = !properties.contains(CoreProperties.ENTITY_TYPE);
                filterEntityAttributeName = !properties.contains(CoreProperties.ENTITY_ATTRIBUTE_NAME);
                filterEntityAttributeType = !properties.contains(CoreProperties.ENTITY_ATTRIBUTE_TYPE);
                filterTrackRevisions = !properties.contains(CoreProperties.TRACK_REVISIONS);
                filterCheckContentWebAddress = !properties.contains(CoreProperties.CHECK_CONTENT_WEB_ADDRESS);
                filterValidationPattern = !properties.contains(CoreProperties.VALIDATION_PATTERN);
                filterEntityListItemSequence = !properties.contains(CoreProperties.ENTITY_LIST_ITEM_SEQUENCE);
                filterUnitOfMeasureType = !properties.contains(CoreProperties.UNIT_OF_MEASURE_TYPE);
                filterSortOrder = !properties.contains(CoreProperties.SORT_ORDER);
                filterDescription = !properties.contains(CoreProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(CoreProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public EntityAttributeTransfer getEntityAttributeTransfer(final UserVisit userVisit, final EntityAttribute entityAttribute, final EntityInstance entityInstance) {
        var entityAttributeTransfer = get(entityAttribute);
        
        if(entityAttributeTransfer == null) {
            var entityAttributeDetail = entityAttribute.getLastDetail();
            var entityTypeTransfer = filterEntityType ? null : entityTypeControl.getEntityTypeTransfer(userVisit, entityAttributeDetail.getEntityType());
            var entityAttributeName = filterEntityAttributeName ? null : entityAttributeDetail.getEntityAttributeName();
            var entityAttributeType = entityAttributeDetail.getEntityAttributeType();
            var entityAttributeTypeTransfer = filterEntityAttributeType ? null : coreControl.getEntityAttributeTypeTransfer(userVisit, entityAttributeType);
            var trackRevisions = filterTrackRevisions ? null : entityAttributeDetail.getTrackRevisions();
            var sortOrder = filterSortOrder ? null : entityAttributeDetail.getSortOrder();
            var description = filterDescription ? null : coreControl.getBestEntityAttributeDescription(entityAttribute, getLanguage(userVisit));
            
            entityAttributeTransfer = new EntityAttributeTransfer(entityTypeTransfer, entityAttributeTypeTransfer, entityAttributeName,
                    trackRevisions, sortOrder, description);

            var entityAttributeTypeName = entityAttributeType.getEntityAttributeTypeName();
            switch(EntityAttributeTypes.valueOf(entityAttributeTypeName)) {
                case BLOB -> {
                    if(!filterCheckContentWebAddress) {
                        var entityAttributeBlob = coreControl.getEntityAttributeBlob(entityAttribute);

                        entityAttributeTransfer.setCheckContentWebAddress(entityAttributeBlob.getCheckContentWebAddress());
                    }
                }
                case STRING -> {
                    if(!filterValidationPattern) {
                        var entityAttributeString = coreControl.getEntityAttributeString(entityAttribute);

                        if(entityAttributeString != null) {
                            entityAttributeTransfer.setValidationPattern(filterValidationPattern ? null : entityAttributeString.getValidationPattern());
                        }
                    }
                }
                case INTEGER -> {
                    // TODO

                    if(!filterUnitOfMeasureType) {
                        var entityAttributeNumeric = coreControl.getEntityAttributeNumeric(entityAttribute);

                        if(entityAttributeNumeric != null) {
                            if(!filterUnitOfMeasureType) {
                                var unitOfMeasureType = entityAttributeNumeric.getUnitOfMeasureType();

                                entityAttributeTransfer.setUnitOfMeasureType(unitOfMeasureType == null ? null : uomControl.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureType));
                            }
                        }
                    }
                }
                case LONG -> {
                    // TODO
                    if(!filterUnitOfMeasureType) {
                        var entityAttributeNumeric = coreControl.getEntityAttributeNumeric(entityAttribute);

                        if(entityAttributeNumeric != null) {
                            if(!filterUnitOfMeasureType) {
                                var unitOfMeasureType = entityAttributeNumeric.getUnitOfMeasureType();

                                entityAttributeTransfer.setUnitOfMeasureType(unitOfMeasureType == null ? null : uomControl.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureType));
                            }
                        }
                    }
                }
                case LISTITEM, MULTIPLELISTITEM -> {
                    if(!filterEntityListItemSequence) {
                        var entityAttributeListItem = coreControl.getEntityAttributeListItem(entityAttribute);

                        if(entityAttributeListItem != null) {
                            if(!filterEntityListItemSequence) {
                                var entityListItemSequence = entityAttributeListItem.getEntityListItemSequence();

                                entityAttributeTransfer.setEntityListItemSequence(entityListItemSequence == null ? null : sequenceControl.getSequenceTransfer(userVisit, entityListItemSequence));
                            }
                        }
                    }
                }
                default -> {}
            }
            
            if(entityInstance == null) {
                put(userVisit, entityAttribute, entityAttributeTransfer);
            } else {
                setupEntityInstance(userVisit, entityAttribute, null, entityAttributeTransfer);
            }

            if(includeDefault || (includeValue && entityInstance != null)) {
                var entityAttributeTypeEnum = EntityAttributeTypes.valueOf(entityAttributeTypeName);

                switch(entityAttributeTypeEnum) {
                    case BOOLEAN -> {
                        if(includeDefault) {
                            var entityBooleanDefault = coreControl.getEntityBooleanDefault(entityAttribute);

                            entityAttributeTransfer.setEntityBooleanDefault(entityBooleanDefault == null ? null : coreControl.getEntityBooleanDefaultTransfer(userVisit, entityBooleanDefault));
                        }

                        if(includeValue && entityInstance != null) {
                            var entityBooleanAttribute = coreControl.getEntityBooleanAttribute(entityAttribute, entityInstance);

                            entityAttributeTransfer.setEntityBooleanAttribute(entityBooleanAttribute == null ? null : coreControl.getEntityBooleanAttributeTransfer(userVisit, entityBooleanAttribute, entityInstance));
                        }
                    }
                    case NAME -> {
                        if(includeValue && entityInstance != null) {
                            var entityNameAttribute = coreControl.getEntityNameAttribute(entityAttribute, entityInstance);

                            entityAttributeTransfer.setEntityNameAttribute(entityNameAttribute == null ? null : coreControl.getEntityNameAttributeTransfer(userVisit, entityNameAttribute, entityInstance));
                        }
                    }
                    case INTEGER -> {
                        if(includeDefault) {
                            var entityIntegerDefault = coreControl.getEntityIntegerDefault(entityAttribute);

                            entityAttributeTransfer.setEntityIntegerDefault(entityIntegerDefault == null ? null : coreControl.getEntityIntegerDefaultTransfer(userVisit, entityIntegerDefault));
                        }

                        if(includeValue && entityInstance != null) {
                            var entityIntegerAttribute = coreControl.getEntityIntegerAttribute(entityAttribute, entityInstance);

                            entityAttributeTransfer.setEntityIntegerAttribute(entityIntegerAttribute == null ? null : coreControl.getEntityIntegerAttributeTransfer(userVisit, entityIntegerAttribute, entityInstance));
                        }
                    }
                    case LONG -> {
                        if(includeDefault) {
                            var entityLongDefault = coreControl.getEntityLongDefault(entityAttribute);

                            entityAttributeTransfer.setEntityLongDefault(entityLongDefault == null ? null : coreControl.getEntityLongDefaultTransfer(userVisit, entityLongDefault));
                        }

                        if(includeValue && entityInstance != null) {
                            var entityLongAttribute = coreControl.getEntityLongAttribute(entityAttribute, entityInstance);

                            entityAttributeTransfer.setEntityLongAttribute(entityLongAttribute == null ? null : coreControl.getEntityLongAttributeTransfer(userVisit, entityLongAttribute, entityInstance));
                        }
                    }
                    case STRING -> {
                        if(includeDefault) {
                            var entityStringDefault = coreControl.getEntityStringDefault(entityAttribute, getLanguage(userVisit));

                            entityAttributeTransfer.setEntityStringDefault(entityStringDefault == null ? null : coreControl.getEntityStringDefaultTransfer(userVisit, entityStringDefault));
                        }

                        if(includeValue && entityInstance != null) {
                            var entityStringAttribute = coreControl.getBestEntityStringAttribute(entityAttribute, entityInstance, getLanguage(userVisit));

                            entityAttributeTransfer.setEntityStringAttribute(entityStringAttribute == null ? null : coreControl.getEntityStringAttributeTransfer(userVisit, entityStringAttribute, entityInstance));
                        }
                    }
                    case GEOPOINT -> {
                        if(includeValue && entityInstance != null) {
                            var entityGeoPointAttribute = coreControl.getEntityGeoPointAttribute(entityAttribute, entityInstance);

                            entityAttributeTransfer.setEntityGeoPointAttribute(entityGeoPointAttribute == null ? null : coreControl.getEntityGeoPointAttributeTransfer(userVisit, entityGeoPointAttribute, entityInstance));
                        }
                    }
                    case BLOB -> {
                        if(includeValue && entityInstance != null) {
                            var entityBlobAttribute = coreControl.getBestEntityBlobAttribute(entityAttribute, entityInstance, getLanguage(userVisit));

                            entityAttributeTransfer.setEntityBlobAttribute(entityBlobAttribute == null ? null : coreControl.getEntityBlobAttributeTransfer(userVisit, entityBlobAttribute, entityInstance));
                        }
                    }
                    case CLOB -> {
                        if(includeValue && entityInstance != null) {
                            var entityClobAttribute = coreControl.getBestEntityClobAttribute(entityAttribute, entityInstance, getLanguage(userVisit));

                            entityAttributeTransfer.setEntityClobAttribute(entityClobAttribute == null ? null : coreControl.getEntityClobAttributeTransfer(userVisit, entityClobAttribute, entityInstance));
                        }
                    }
                    case ENTITY -> {
                        if(includeValue && entityInstance != null) {
                            var entityEntityAttribute = coreControl.getEntityEntityAttribute(entityAttribute, entityInstance);

                            entityAttributeTransfer.setEntityEntityAttribute(entityEntityAttribute == null ? null : coreControl.getEntityEntityAttributeTransfer(userVisit, entityEntityAttribute, entityInstance));
                        }
                    }
                    case COLLECTION -> {
                        if(includeValue && entityInstance != null) {
                            entityAttributeTransfer.setEntityCollectionAttributes(new ListWrapper<>(coreControl.getEntityCollectionAttributeTransfers(userVisit, entityAttribute, entityInstance)));
                        }
                    }
                    case DATE -> {
                        if(includeDefault) {
                            var entityDateDefault = coreControl.getEntityDateDefault(entityAttribute);

                            entityAttributeTransfer.setEntityDateDefault(entityDateDefault == null ? null : coreControl.getEntityDateDefaultTransfer(userVisit, entityDateDefault));
                        }

                        if(includeValue && entityInstance != null) {
                            var entityDateAttribute = coreControl.getEntityDateAttribute(entityAttribute, entityInstance);

                            entityAttributeTransfer.setEntityDateAttribute(entityDateAttribute == null ? null : coreControl.getEntityDateAttributeTransfer(userVisit, entityDateAttribute, entityInstance));
                        }
                    }
                    case TIME -> {
                        if(includeValue && entityInstance != null) {
                            var entityTimeAttribute = coreControl.getEntityTimeAttribute(entityAttribute, entityInstance);

                            entityAttributeTransfer.setEntityTimeAttribute(entityTimeAttribute == null ? null : coreControl.getEntityTimeAttributeTransfer(userVisit, entityTimeAttribute, entityInstance));
                        }
                    }
                    case LISTITEM -> {
                        if(includeDefault) {
                            var entityListItemDefault = coreControl.getEntityListItemDefault(entityAttribute);

                            entityAttributeTransfer.setEntityListItemDefault(entityListItemDefault == null ? null : coreControl.getEntityListItemDefaultTransfer(userVisit, entityListItemDefault));
                        }

                        if(includeValue && entityInstance != null) {
                            var entityListItemAttribute = coreControl.getEntityListItemAttribute(entityAttribute, entityInstance);

                            entityAttributeTransfer.setEntityListItemAttribute(entityListItemAttribute == null ? null : coreControl.getEntityListItemAttributeTransfer(userVisit, entityListItemAttribute, entityInstance));
                        }
                    }
                    case MULTIPLELISTITEM -> {
                        if(includeDefault) {
                            var entityMultipleListItemDefaults = coreControl.getEntityMultipleListItemDefaults(entityAttribute);

                            entityAttributeTransfer.setEntityMultipleListItemDefaults(new ListWrapper<>(coreControl.getEntityMultipleListItemDefaultTransfers(userVisit, entityMultipleListItemDefaults)));
                        }

                        if(includeValue && entityInstance != null) {
                            entityAttributeTransfer.setEntityMultipleListItemAttributes(new ListWrapper<>(coreControl.getEntityMultipleListItemAttributeTransfers(userVisit, entityAttribute, entityInstance)));
                        }
                    }
                    case WORKFLOW -> {} // Nothing
                }
            }

            if(includeEntityListItemsCount) {
                entityAttributeTransfer.setEntityListItemsCount(coreControl.countEntityListItems(entityAttribute));
            }

            if(includeEntityListItems) {
                entityAttributeTransfer.setEntityListItems(new ListWrapper<>(coreControl.getEntityListItemTransfersByEntityAttribute(userVisit, entityAttribute, entityInstance)));
            }

            if(includeEntityAttributeEntityTypesCount) {
                entityAttributeTransfer.setEntityAttributeEntityTypesCount(coreControl.countEntityAttributeEntityTypesByEntityAttribute(entityAttribute));
            }

            if(includeEntityAttributeEntityTypes) {
                entityAttributeTransfer.setEntityAttributeEntityTypes(new ListWrapper<>(coreControl.getEntityAttributeEntityTypeTransfersByEntityAttribute(userVisit, entityAttribute, entityInstance)));
            }
        }
        return entityAttributeTransfer;
    }
    
}
