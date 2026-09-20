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

package com.echothree.model.control.sequence.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.choice.SequenceChecksumTypeChoicesBean;
import com.echothree.model.control.sequence.common.choice.SequenceChoicesBean;
import com.echothree.model.control.sequence.common.choice.SequenceEncoderTypeChoicesBean;
import com.echothree.model.control.sequence.common.choice.SequenceTypeChoicesBean;
import com.echothree.model.control.sequence.common.transfer.SequenceChecksumTypeTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceDescriptionTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceEncoderTypeTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTypeDescriptionTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTypeTransfer;
import com.echothree.model.control.sequence.server.transfer.SequenceChecksumTypeTransferCache;
import com.echothree.model.control.sequence.server.transfer.SequenceDescriptionTransferCache;
import com.echothree.model.control.sequence.server.transfer.SequenceEncoderTypeTransferCache;
import com.echothree.model.control.sequence.server.transfer.SequenceTransferCache;
import com.echothree.model.control.sequence.server.transfer.SequenceTypeDescriptionTransferCache;
import com.echothree.model.control.sequence.server.transfer.SequenceTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.common.pk.SequencePK;
import com.echothree.model.data.sequence.common.pk.SequenceTypePK;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceChecksumType;
import com.echothree.model.data.sequence.server.entity.SequenceChecksumTypeDescription;
import com.echothree.model.data.sequence.server.entity.SequenceDescription;
import com.echothree.model.data.sequence.server.entity.SequenceEncoderType;
import com.echothree.model.data.sequence.server.entity.SequenceEncoderTypeDescription;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.sequence.server.entity.SequenceTypeDescription;
import com.echothree.model.data.sequence.server.entity.SequenceValue;
import com.echothree.model.data.sequence.server.factory.SequenceChecksumTypeDescriptionFactory;
import com.echothree.model.data.sequence.server.factory.SequenceChecksumTypeFactory;
import com.echothree.model.data.sequence.server.factory.SequenceDescriptionFactory;
import com.echothree.model.data.sequence.server.factory.SequenceDetailFactory;
import com.echothree.model.data.sequence.server.factory.SequenceEncoderTypeDescriptionFactory;
import com.echothree.model.data.sequence.server.factory.SequenceEncoderTypeFactory;
import com.echothree.model.data.sequence.server.factory.SequenceFactory;
import com.echothree.model.data.sequence.server.factory.SequenceTypeDescriptionFactory;
import com.echothree.model.data.sequence.server.factory.SequenceTypeDetailFactory;
import com.echothree.model.data.sequence.server.factory.SequenceTypeFactory;
import com.echothree.model.data.sequence.server.factory.SequenceValueFactory;
import com.echothree.model.data.sequence.server.value.SequenceDescriptionValue;
import com.echothree.model.data.sequence.server.value.SequenceDetailValue;
import com.echothree.model.data.sequence.server.value.SequenceTypeDescriptionValue;
import com.echothree.model.data.sequence.server.value.SequenceTypeDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.sequence.SequenceForeignKeys.SEQUENCES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.sequence.SequenceForeignKeys.SEQUENCE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.sequence.SequenceForeignKeys.SEQUENCE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.sequence.SequenceForeignKeys.SEQUENCE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.sequence.SequenceChecksumTypeDescriptions.SequenceChecksumTypeDescriptions;
import static com.echothree.model.jooq.server.tables.sequence.SequenceChecksumTypes.SequenceChecksumTypes;
import static com.echothree.model.jooq.server.tables.sequence.SequenceDescriptions.SequenceDescriptions;
import static com.echothree.model.jooq.server.tables.sequence.SequenceDetails.SequenceDetails;
import static com.echothree.model.jooq.server.tables.sequence.SequenceEncoderTypeDescriptions.SequenceEncoderTypeDescriptions;
import static com.echothree.model.jooq.server.tables.sequence.SequenceEncoderTypes.SequenceEncoderTypes;
import static com.echothree.model.jooq.server.tables.sequence.SequenceTypeDescriptions.SequenceTypeDescriptions;
import static com.echothree.model.jooq.server.tables.sequence.SequenceTypeDetails.SequenceTypeDetails;
import static com.echothree.model.jooq.server.tables.sequence.SequenceTypes.SequenceTypes;
import static com.echothree.model.jooq.server.tables.sequence.SequenceValues.SequenceValues;
import static com.echothree.model.jooq.server.tables.sequence.Sequences.Sequences;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class SequenceControl
        extends BaseModelControl {
    
    @Inject
    protected OfferUseControl offerUseControl;

    /** Creates a new instance of SequenceControl */
    protected SequenceControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Types
    // --------------------------------------------------------------------------------

    @Inject
    protected SequenceTypeDetailFactory sequenceTypeDetailFactory;

    @Inject
    protected SequenceTypeFactory sequenceTypeFactory;

    @Inject
    private SequenceTypeTransferCache sequenceTypeTransferCache;

    public SequenceType createSequenceType(String sequenceTypeName, String prefix, String suffix,
            SequenceEncoderType sequenceEncoderType, SequenceChecksumType sequenceChecksumType, Integer chunkSize, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultSequenceType = getDefaultSequenceType();
        var defaultFound = defaultSequenceType != null;
        
        if(defaultFound && isDefault) {
            var defaultSequenceTypeDetailValue = getDefaultSequenceTypeDetailValueForUpdate();
            
            defaultSequenceTypeDetailValue.setIsDefault(false);
            updateSequenceTypeFromValue(defaultSequenceTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var sequenceType = sequenceTypeFactory.create();
        var sequenceTypeDetail = sequenceTypeDetailFactory.create(sequenceType,
                sequenceTypeName, prefix, suffix, sequenceEncoderType, sequenceChecksumType, chunkSize, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        sequenceType = sequenceTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, sequenceType.getPrimaryKey());
        sequenceType.setActiveDetail(sequenceTypeDetail);
        sequenceType.setLastDetail(sequenceTypeDetail);
        sequenceType.store();
        
        sendEvent(sequenceType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return sequenceType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.SequenceType */
    public SequenceType getSequenceTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SequenceTypePK(entityInstance.getEntityUniqueId());
        var sequenceType = sequenceTypeFactory.getEntityFromPK(entityPermission, pk);

        return sequenceType;
    }

    public SequenceType getSequenceTypeByEntityInstance(EntityInstance entityInstance) {
        return getSequenceTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public SequenceType getSequenceTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSequenceTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countSequenceTypes() {
        return session.getDslContext()
                .selectCount()
                .from(SequenceTypes)
                .join(SequenceTypeDetails).onKey(SEQUENCE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<SequenceType> getSequenceTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SequenceTypes.fields())
                .from(SequenceTypes)
                .join(SequenceTypeDetails).onKey(SEQUENCE_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SequenceTypeDetails.SORT_ORDER, SequenceTypeDetails.SEQUENCE_TYPE_NAME),
                    SequenceTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return sequenceTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SequenceType> getSequenceTypes() {
        return getSequenceTypes(EntityPermission.READ_ONLY);
    }
    
    public List<SequenceType> getSequenceTypesForUpdate() {
        return getSequenceTypes(EntityPermission.READ_WRITE);
    }
    
    public SequenceType getDefaultSequenceType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SequenceTypes.fields())
                .from(SequenceTypes)
                .join(SequenceTypeDetails).onKey(SEQUENCE_TYPES_ACTIVE_DETAIL_FK)
                .where(SequenceTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return sequenceTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SequenceType getDefaultSequenceType() {
        return getDefaultSequenceType(EntityPermission.READ_ONLY);
    }
    
    public SequenceType getDefaultSequenceTypeForUpdate() {
        return getDefaultSequenceType( EntityPermission.READ_WRITE);
    }
    
    public SequenceTypeDetailValue getDefaultSequenceTypeDetailValueForUpdate() {
        return getDefaultSequenceTypeForUpdate().getLastDetailForUpdate().getSequenceTypeDetailValue().clone();
    }

    public SequenceType getSequenceTypeByName(String sequenceTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SequenceTypes.fields())
                .from(SequenceTypes)
                .join(SequenceTypeDetails).onKey(SEQUENCE_TYPES_ACTIVE_DETAIL_FK)
                .where(SequenceTypeDetails.SEQUENCE_TYPE_NAME.eq(sequenceTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return sequenceTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SequenceType getSequenceTypeByName(String sequenceTypeName) {
        return getSequenceTypeByName(sequenceTypeName, EntityPermission.READ_ONLY);
    }
    
    public SequenceType getSequenceTypeByNameForUpdate(String sequenceTypeName) {
        return getSequenceTypeByName(sequenceTypeName, EntityPermission.READ_WRITE);
    }
    
    private SequenceType getSequenceTypeByPrefix(String sequenceTypePrefix, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SequenceTypes.fields())
                .from(SequenceTypes)
                .join(SequenceTypeDetails).onKey(SEQUENCE_TYPES_ACTIVE_DETAIL_FK)
                .where(SequenceTypeDetails.PREFIX.eq(sequenceTypePrefix));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return sequenceTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SequenceType getSequenceTypeByPrefix(String sequenceTypePrefix) {
        return getSequenceTypeByPrefix(sequenceTypePrefix, EntityPermission.READ_ONLY);
    }
    
    public SequenceType getSequenceTypeByPrefixForUpdate(String sequenceTypePrefix) {
        return getSequenceTypeByPrefix(sequenceTypePrefix, EntityPermission.READ_WRITE);
    }
    
    private SequenceType getSequenceTypeBySuffix(String sequenceTypeSuffix, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SequenceTypes.fields())
                .from(SequenceTypes)
                .join(SequenceTypeDetails).onKey(SEQUENCE_TYPES_ACTIVE_DETAIL_FK)
                .where(SequenceTypeDetails.SUFFIX.eq(sequenceTypeSuffix));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return sequenceTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SequenceType getSequenceTypeBySuffix(String sequenceTypeSuffix) {
        return getSequenceTypeBySuffix(sequenceTypeSuffix, EntityPermission.READ_ONLY);
    }
    
    public SequenceType getSequenceTypeBySuffixForUpdate(String sequenceTypeSuffix) {
        return getSequenceTypeBySuffix(sequenceTypeSuffix, EntityPermission.READ_WRITE);
    }
    
    public SequenceTypeDetailValue getSequenceTypeDetailValueForUpdate(SequenceType sequenceType) {
        return sequenceType == null? null: sequenceType.getLastDetailForUpdate().getSequenceTypeDetailValue().clone();
    }
    
    public SequenceTypeDetailValue getSequenceTypeDetailValueByNameForUpdate(String sequenceTypeName) {
        return getSequenceTypeDetailValueForUpdate(getSequenceTypeByNameForUpdate(sequenceTypeName));
    }
    
    public SequenceTypeChoicesBean getSequenceTypeChoices(String defaultSequenceTypeChoice, Language language, boolean allowNullChoice) {
        var sequenceTypes = getSequenceTypes();
        var size = sequenceTypes.size() + (allowNullChoice? 1: 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSequenceTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var sequenceType : sequenceTypes) {
            var sequenceTypeDetail = sequenceType.getLastDetail();
            var label = getBestSequenceTypeDescription(sequenceType, language);
            var value = sequenceTypeDetail.getSequenceTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSequenceTypeChoice != null && defaultSequenceTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && sequenceTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SequenceTypeChoicesBean(labels, values, defaultValue);
    }
    
    public SequenceTypeTransfer getSequenceTypeTransfer(UserVisit userVisit, SequenceType sequenceType) {
        return sequenceTypeTransferCache.getSequenceTypeTransfer(userVisit, sequenceType);
    }

    public List<SequenceTypeTransfer> getSequenceTypeTransfers(UserVisit userVisit, Collection<SequenceType> sequenceTypes) {
        List<SequenceTypeTransfer> sequenceTypeTransfers = new ArrayList<>(sequenceTypes.size());

        sequenceTypes.forEach((sequenceType) ->
                sequenceTypeTransfers.add(sequenceTypeTransferCache.getSequenceTypeTransfer(userVisit, sequenceType))
        );

        return sequenceTypeTransfers;
    }

    public List<SequenceTypeTransfer> getSequenceTypeTransfers(UserVisit userVisit) {
        return getSequenceTypeTransfers(userVisit, getSequenceTypes());
    }

    private void updateSequenceTypeFromValue(SequenceTypeDetailValue sequenceTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        var sequenceType = sequenceTypeFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, sequenceTypeDetailValue.getSequenceTypePK());
        var sequenceTypeDetail = sequenceType.getActiveDetailForUpdate();
        
        sequenceTypeDetail.setThruTime(session.getStartTime());
        sequenceTypeDetail.store();

        var sequenceTypePK = sequenceTypeDetail.getSequenceTypePK();
        var sequenceTypeName = sequenceTypeDetailValue.getSequenceTypeName();
        var prefix = sequenceTypeDetailValue.getPrefix();
        var suffix = sequenceTypeDetailValue.getSuffix();
        var sequenceEncoderTypePK = sequenceTypeDetailValue.getSequenceEncoderTypePK();
        var sequenceChecksumTypePK = sequenceTypeDetailValue.getSequenceChecksumTypePK();
        var chunkSize = sequenceTypeDetailValue.getChunkSize();
        var isDefault = sequenceTypeDetailValue.getIsDefault();
        var sortOrder = sequenceTypeDetailValue.getSortOrder();
        
        if(checkDefault) {
            var defaultSequenceType = getDefaultSequenceType();
            var defaultFound = defaultSequenceType != null && !defaultSequenceType.equals(sequenceType);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultSequenceTypeDetailValue = getDefaultSequenceTypeDetailValueForUpdate();
                
                defaultSequenceTypeDetailValue.setIsDefault(false);
                updateSequenceTypeFromValue(defaultSequenceTypeDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }
        
        sequenceTypeDetail = sequenceTypeDetailFactory.create(sequenceTypePK, sequenceTypeName, prefix,
                suffix, sequenceEncoderTypePK, sequenceChecksumTypePK, chunkSize, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        sequenceType.setActiveDetail(sequenceTypeDetail);
        sequenceType.setLastDetail(sequenceTypeDetail);
        sequenceType.store();
        
        sendEvent(sequenceTypePK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void updateSequenceTypeFromValue(SequenceTypeDetailValue sequenceTypeDetailValue, BasePK updatedBy) {
        updateSequenceTypeFromValue(sequenceTypeDetailValue, true, updatedBy);
    }
    
    public void deleteSequenceType(SequenceType sequenceType, BasePK deletedBy) {
        deleteSequencesBySequenceType(sequenceType, deletedBy);
        deleteSequenceTypeDescriptionsBySequenceType(sequenceType, deletedBy);

        var sequenceTypeDetail = sequenceType.getLastDetailForUpdate();
        sequenceTypeDetail.setThruTime(session.getStartTime());
        sequenceType.setActiveDetail(null);
        sequenceType.store();
        
        // Check for default, and pick one if necessary
        var defaultSequenceType = getDefaultSequenceType();
        if(defaultSequenceType == null) {
            var sequenceTypes = getSequenceTypesForUpdate();
            
            if(!sequenceTypes.isEmpty()) {
                var iter = sequenceTypes.iterator();
                if(iter.hasNext()) {
                    defaultSequenceType = iter.next();
                }
                var sequenceTypeDetailValue = Objects.requireNonNull(defaultSequenceType).getLastDetailForUpdate().getSequenceTypeDetailValue().clone();
                
                sequenceTypeDetailValue.setIsDefault(true);
                updateSequenceTypeFromValue(sequenceTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(sequenceType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected SequenceTypeDescriptionFactory sequenceTypeDescriptionFactory;

    @Inject
    private SequenceTypeDescriptionTransferCache sequenceTypeDescriptionTransferCache;

    public SequenceTypeDescription createSequenceTypeDescription(SequenceType sequenceType, Language language, String description, BasePK createdBy) {
        var sequenceTypeDescription = sequenceTypeDescriptionFactory.create(sequenceType, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(sequenceType.getPrimaryKey(), EventTypes.MODIFY, sequenceTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return sequenceTypeDescription;
    }
    
    private List<SequenceTypeDescription> getSequenceTypeDescriptionsBySequenceType(SequenceType sequenceType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SequenceTypeDescriptions.fields())
                    .from(SequenceTypeDescriptions)
                    .join(Languages).onKey(SEQUENCE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(SequenceTypeDescriptions.SEQUENCE_TYPE.eq(sequenceType.getPrimaryKey()),
                            SequenceTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    SequenceTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SequenceTypeDescriptions.fields())
                    .from(SequenceTypeDescriptions)
                    .where(SequenceTypeDescriptions.SEQUENCE_TYPE.eq(sequenceType.getPrimaryKey()),
                            SequenceTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return sequenceTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SequenceTypeDescription> getSequenceTypeDescriptionsBySequenceType(SequenceType sequenceType) {
        return getSequenceTypeDescriptionsBySequenceType(sequenceType, EntityPermission.READ_ONLY);
    }
    
    public List<SequenceTypeDescription> getSequenceTypeDescriptionsBySequenceTypeForUpdate(SequenceType sequenceType) {
        return getSequenceTypeDescriptionsBySequenceType(sequenceType, EntityPermission.READ_WRITE);
    }
    
    private SequenceTypeDescription getSequenceTypeDescription(SequenceType sequenceType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SequenceTypeDescriptions.fields())
                .from(SequenceTypeDescriptions)
                .where(SequenceTypeDescriptions.SEQUENCE_TYPE.eq(sequenceType.getPrimaryKey()),
                        SequenceTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        SequenceTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return sequenceTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SequenceTypeDescription getSequenceTypeDescription(SequenceType sequenceType, Language language) {
        return getSequenceTypeDescription(sequenceType, language, EntityPermission.READ_ONLY);
    }
    
    public SequenceTypeDescription getSequenceTypeDescriptionForUpdate(SequenceType sequenceType, Language language) {
        return getSequenceTypeDescription(sequenceType, language, EntityPermission.READ_WRITE);
    }
    
    public SequenceTypeDescriptionValue getSequenceTypeDescriptionValue(SequenceTypeDescription sequenceTypeDescription) {
        return sequenceTypeDescription == null? null: sequenceTypeDescription.getSequenceTypeDescriptionValue().clone();
    }
    
    public SequenceTypeDescriptionValue getSequenceTypeDescriptionValueForUpdate(SequenceType sequenceType, Language language) {
        return getSequenceTypeDescriptionValue(getSequenceTypeDescriptionForUpdate(sequenceType, language));
    }
    
    public String getBestSequenceTypeDescription(SequenceType sequenceType, Language language) {
        String description;
        var sequenceTypeDescription = getSequenceTypeDescription(sequenceType, language);
        
        if(sequenceTypeDescription == null && !language.getIsDefault()) {
            sequenceTypeDescription = getSequenceTypeDescription(sequenceType, partyControl.getDefaultLanguage());
        }
        
        if(sequenceTypeDescription == null) {
            description = sequenceType.getLastDetail().getSequenceTypeName();
        } else {
            description = sequenceTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public SequenceTypeDescriptionTransfer getSequenceTypeDescriptionTransfer(UserVisit userVisit, SequenceTypeDescription sequenceTypeDescription) {
        return sequenceTypeDescriptionTransferCache.getSequenceTypeDescriptionTransfer(userVisit, sequenceTypeDescription);
    }
    
    public List<SequenceTypeDescriptionTransfer> getSequenceTypeDescriptionTransfers(UserVisit userVisit, SequenceType sequenceType) {
        var sequenceTypeDescriptions = getSequenceTypeDescriptionsBySequenceType(sequenceType);
        List<SequenceTypeDescriptionTransfer> sequenceTypeDescriptionTransfers = new ArrayList<>(sequenceTypeDescriptions.size());
        
        sequenceTypeDescriptions.forEach((sequenceTypeDescription) ->
                sequenceTypeDescriptionTransfers.add(sequenceTypeDescriptionTransferCache.getSequenceTypeDescriptionTransfer(userVisit, sequenceTypeDescription))
        );
        
        return sequenceTypeDescriptionTransfers;
    }
    
    public void updateSequenceTypeDescriptionFromValue(SequenceTypeDescriptionValue sequenceTypeDescriptionValue, BasePK updatedBy) {
        if(sequenceTypeDescriptionValue.hasBeenModified()) {
            var sequenceTypeDescription = sequenceTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, sequenceTypeDescriptionValue.getPrimaryKey());
            
            sequenceTypeDescription.setThruTime(session.getStartTime());
            sequenceTypeDescription.store();

            var sequenceType = sequenceTypeDescription.getSequenceType();
            var language = sequenceTypeDescription.getLanguage();
            var description = sequenceTypeDescriptionValue.getDescription();
            
            sequenceTypeDescription = sequenceTypeDescriptionFactory.create(sequenceType, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(sequenceType.getPrimaryKey(), EventTypes.MODIFY, sequenceTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSequenceTypeDescription(SequenceTypeDescription sequenceTypeDescription, BasePK deletedBy) {
        sequenceTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(sequenceTypeDescription.getSequenceTypePK(), EventTypes.MODIFY, sequenceTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteSequenceTypeDescriptionsBySequenceType(SequenceType sequenceType, BasePK deletedBy) {
        var sequenceTypeDescriptions = getSequenceTypeDescriptionsBySequenceTypeForUpdate(sequenceType);
        
        sequenceTypeDescriptions.forEach((sequenceTypeDescription) -> 
                deleteSequenceTypeDescription(sequenceTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Checksum Types
    // --------------------------------------------------------------------------------

    @Inject
    protected SequenceChecksumTypeFactory sequenceChecksumTypeFactory;

    @Inject
    private SequenceChecksumTypeTransferCache sequenceChecksumTypeTransferCache;

    public SequenceChecksumType createSequenceChecksumType(String sequenceChecksumTypeName, Boolean isDefault, Integer sortOrder) {
        return sequenceChecksumTypeFactory.create(sequenceChecksumTypeName, isDefault, sortOrder);
    }

    public long countSequenceChecksumTypes() {
        return session.getDslContext()
                .selectCount()
                .from(SequenceChecksumTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<SequenceChecksumType> getSequenceChecksumTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(SequenceChecksumTypes.fields())
                .from(SequenceChecksumTypes)
                .orderBy(SequenceChecksumTypes.SORT_ORDER, SequenceChecksumTypes.SEQUENCE_CHECKSUM_TYPE_NAME),
                SequenceChecksumTypeFactory.class);

        return sequenceChecksumTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public SequenceChecksumType getSequenceChecksumTypeByName(String sequenceChecksumTypeName) {
        var query = session.getDslContext()
                .select(SequenceChecksumTypes.fields())
                .from(SequenceChecksumTypes)
                .where(SequenceChecksumTypes.SEQUENCE_CHECKSUM_TYPE_NAME.eq(sequenceChecksumTypeName));

        return sequenceChecksumTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

    public SequenceChecksumTypeTransfer getSequenceChecksumTypeTransfer(UserVisit userVisit, SequenceChecksumType sequenceChecksumType) {
        return sequenceChecksumTypeTransferCache.getSequenceChecksumTypeTransfer(userVisit, sequenceChecksumType);
    }

    public List<SequenceChecksumTypeTransfer> getSequenceChecksumTypeTransfers(UserVisit userVisit, Collection<SequenceChecksumType> sequenceChecksumTypes) {
        List<SequenceChecksumTypeTransfer> sequenceChecksumTypeTransfers = new ArrayList<>(sequenceChecksumTypes.size());

        sequenceChecksumTypes.forEach((sequenceChecksumType) ->
                sequenceChecksumTypeTransfers.add(sequenceChecksumTypeTransferCache.getSequenceChecksumTypeTransfer(userVisit, sequenceChecksumType))
        );

        return sequenceChecksumTypeTransfers;
    }

    public List<SequenceChecksumTypeTransfer> getSequenceChecksumTypeTransfers(UserVisit userVisit) {
        return getSequenceChecksumTypeTransfers(userVisit, getSequenceChecksumTypes());
    }

    public SequenceChecksumTypeChoicesBean getSequenceChecksumTypeChoices(String defaultSequenceChecksumTypeChoice,
            Language language, boolean allowNullChoice) {
        var sequenceChecksumTypes = getSequenceChecksumTypes();
        var size = sequenceChecksumTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSequenceChecksumTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var sequenceChecksumType : sequenceChecksumTypes) {
            var label = getBestSequenceChecksumTypeDescription(sequenceChecksumType, language);
            var value = sequenceChecksumType.getSequenceChecksumTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSequenceChecksumTypeChoice != null && defaultSequenceChecksumTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && sequenceChecksumType.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SequenceChecksumTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Checksum Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected SequenceChecksumTypeDescriptionFactory sequenceChecksumTypeDescriptionFactory;

    public SequenceChecksumTypeDescription createSequenceChecksumTypeDescription(SequenceChecksumType sequenceChecksumType, Language language, String description) {
        return sequenceChecksumTypeDescriptionFactory.create(sequenceChecksumType, language, description);
    }
    
    public SequenceChecksumTypeDescription getSequenceChecksumTypeDescription(SequenceChecksumType sequenceChecksumType, Language language) {
        var query = session.getDslContext()
                .select(SequenceChecksumTypeDescriptions.fields())
                .from(SequenceChecksumTypeDescriptions)
                .where(SequenceChecksumTypeDescriptions.SEQUENCE_CHECKSUM_TYPE.eq(sequenceChecksumType.getPrimaryKey()),
                        SequenceChecksumTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return sequenceChecksumTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestSequenceChecksumTypeDescription(SequenceChecksumType sequenceChecksumType, Language language) {
        String description;
        var sequenceChecksumTypeDescription = getSequenceChecksumTypeDescription(sequenceChecksumType, language);
        
        if(sequenceChecksumTypeDescription == null && !language.getIsDefault()) {
            sequenceChecksumTypeDescription = getSequenceChecksumTypeDescription(sequenceChecksumType, partyControl.getDefaultLanguage());
        }
        
        if(sequenceChecksumTypeDescription == null) {
            description = sequenceChecksumType.getSequenceChecksumTypeName();
        } else {
            description = sequenceChecksumTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Encoder Types
    // --------------------------------------------------------------------------------

    @Inject
    protected SequenceEncoderTypeFactory sequenceEncoderTypeFactory;

    @Inject
    private SequenceEncoderTypeTransferCache sequenceEncoderTypeTransferCache;

    public SequenceEncoderType createSequenceEncoderType(String sequenceEncoderTypeName, Boolean isDefault, Integer sortOrder) {
        return sequenceEncoderTypeFactory.create(sequenceEncoderTypeName, isDefault, sortOrder);
    }

    public long countSequenceEncoderTypes() {
        return session.getDslContext()
                .selectCount()
                .from(SequenceEncoderTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<SequenceEncoderType> getSequenceEncoderTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(SequenceEncoderTypes.fields())
                .from(SequenceEncoderTypes)
                .orderBy(SequenceEncoderTypes.SORT_ORDER, SequenceEncoderTypes.SEQUENCE_ENCODER_TYPE_NAME),
                SequenceEncoderTypeFactory.class);

        return sequenceEncoderTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public SequenceEncoderType getSequenceEncoderTypeByName(String sequenceEncoderTypeName) {
        var query = session.getDslContext()
                .select(SequenceEncoderTypes.fields())
                .from(SequenceEncoderTypes)
                .where(SequenceEncoderTypes.SEQUENCE_ENCODER_TYPE_NAME.eq(sequenceEncoderTypeName));

        return sequenceEncoderTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

    public SequenceEncoderTypeTransfer getSequenceEncoderTypeTransfer(UserVisit userVisit, SequenceEncoderType sequenceEncoderType) {
        return sequenceEncoderTypeTransferCache.getSequenceEncoderTypeTransfer(userVisit, sequenceEncoderType);
    }

    public List<SequenceEncoderTypeTransfer> getSequenceEncoderTypeTransfers(UserVisit userVisit, Collection<SequenceEncoderType> sequenceEncoderTypes) {
        List<SequenceEncoderTypeTransfer> sequenceEncoderTypeTransfers = new ArrayList<>(sequenceEncoderTypes.size());

        sequenceEncoderTypes.forEach((sequenceEncoderType) ->
                sequenceEncoderTypeTransfers.add(sequenceEncoderTypeTransferCache.getSequenceEncoderTypeTransfer(userVisit, sequenceEncoderType))
        );

        return sequenceEncoderTypeTransfers;
    }

    public List<SequenceEncoderTypeTransfer> getSequenceEncoderTypeTransfers(UserVisit userVisit) {
        return getSequenceEncoderTypeTransfers(userVisit, getSequenceEncoderTypes());
    }

    public SequenceEncoderTypeChoicesBean getSequenceEncoderTypeChoices(String defaultSequenceEncoderTypeChoice, Language language,
            boolean allowNullChoice) {
        var sequenceEncoderTypes = getSequenceEncoderTypes();
        var size = sequenceEncoderTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSequenceEncoderTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var sequenceEncoderType : sequenceEncoderTypes) {
            var label = getBestSequenceEncoderTypeDescription(sequenceEncoderType, language);
            var value = sequenceEncoderType.getSequenceEncoderTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSequenceEncoderTypeChoice != null && defaultSequenceEncoderTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && sequenceEncoderType.getIsDefault()))
                defaultValue = value;
        }
        
        return new SequenceEncoderTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Encoder Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected SequenceEncoderTypeDescriptionFactory sequenceEncoderTypeDescriptionFactory;

    public SequenceEncoderTypeDescription createSequenceEncoderTypeDescription(SequenceEncoderType sequenceEncoderType, Language language, String description) {
        return sequenceEncoderTypeDescriptionFactory.create(sequenceEncoderType, language, description);
    }
    
    public SequenceEncoderTypeDescription getSequenceEncoderTypeDescription(SequenceEncoderType sequenceEncoderType, Language language) {
        var query = session.getDslContext()
                .select(SequenceEncoderTypeDescriptions.fields())
                .from(SequenceEncoderTypeDescriptions)
                .where(SequenceEncoderTypeDescriptions.SEQUENCE_ENCODER_TYPE.eq(sequenceEncoderType.getPrimaryKey()),
                        SequenceEncoderTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return sequenceEncoderTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public String getBestSequenceEncoderTypeDescription(SequenceEncoderType sequenceEncoderType, Language language) {
        String description;
        var sequenceEncoderTypeDescription = getSequenceEncoderTypeDescription(sequenceEncoderType, language);
        
        if(sequenceEncoderTypeDescription == null && !language.getIsDefault()) {
            sequenceEncoderTypeDescription = getSequenceEncoderTypeDescription(sequenceEncoderType, partyControl.getDefaultLanguage());
        }
        
        if(sequenceEncoderTypeDescription == null) {
            description = sequenceEncoderType.getSequenceEncoderTypeName();
        } else {
            description = sequenceEncoderTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Sequences
    // --------------------------------------------------------------------------------

    @Inject
    protected SequenceDetailFactory sequenceDetailFactory;

    @Inject
    protected SequenceFactory sequenceFactory;

    @Inject
    private SequenceTransferCache sequenceTransferCache;

    public Sequence createSequence(SequenceType sequenceType, String sequenceName, String mask, Integer chunkSize, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultSequence = getDefaultSequence(sequenceType);
        var defaultFound = defaultSequence != null;
        
        if(defaultFound && isDefault) {
            var defaultSequenceDetailValue = getDefaultSequenceDetailValueForUpdate(sequenceType);
            
            defaultSequenceDetailValue.setIsDefault(false);
            updateSequenceFromValue(defaultSequenceDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var sequence = sequenceFactory.create();
        var sequenceDetail = sequenceDetailFactory.create(sequence, sequenceType, sequenceName,
                mask, chunkSize, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        sequence = sequenceFactory.getEntityFromPK(EntityPermission.READ_WRITE, sequence.getPrimaryKey());
        sequence.setActiveDetail(sequenceDetail);
        sequence.setLastDetail(sequenceDetail);
        sequence.store();
        
        sendEvent(sequence.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return sequence;
    }

    public long countSequences() {
        return session.getDslContext()
                .selectCount()
                .from(Sequences)
                .join(SequenceDetails).onKey(SEQUENCES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countSequencesBySequenceType(SequenceType sequenceType) {
        return session.getDslContext()
                .selectCount()
                .from(Sequences)
                .join(SequenceDetails).onKey(SEQUENCES_ACTIVE_DETAIL_FK)
                .where(SequenceDetails.SEQUENCE_TYPE.eq(sequenceType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Sequence */
    public Sequence getSequenceByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new SequencePK(entityInstance.getEntityUniqueId());
        var sequence = sequenceFactory.getEntityFromPK(entityPermission, pk);

        return sequence;
    }

    public Sequence getSequenceByEntityInstance(EntityInstance entityInstance) {
        return getSequenceByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Sequence getSequenceByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getSequenceByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<Sequence> getSequencesBySequenceType(SequenceType sequenceType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Sequences.fields())
                .from(Sequences)
                .join(SequenceDetails).onKey(SEQUENCES_ACTIVE_DETAIL_FK)
                .where(SequenceDetails.SEQUENCE_TYPE.eq(sequenceType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(SequenceDetails.SORT_ORDER, SequenceDetails.SEQUENCE_NAME),
                    SequenceFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return sequenceFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Sequence> getSequencesBySequenceType(SequenceType sequenceType) {
        return getSequencesBySequenceType(sequenceType, EntityPermission.READ_ONLY);
    }
    
    public List<Sequence> getSequencesBySequenceTypeForUpdate(SequenceType sequenceType) {
        return getSequencesBySequenceType(sequenceType, EntityPermission.READ_WRITE);
    }
    
    public Sequence getDefaultSequence(SequenceType sequenceType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Sequences.fields())
                .from(Sequences)
                .join(SequenceDetails).onKey(SEQUENCES_ACTIVE_DETAIL_FK)
                .where(SequenceDetails.SEQUENCE_TYPE.eq(sequenceType.getPrimaryKey()),
                        SequenceDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return sequenceFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Sequence getDefaultSequence(SequenceType sequenceType) {
        return getDefaultSequence(sequenceType, EntityPermission.READ_ONLY);
    }
    
    public Sequence getDefaultSequenceForUpdate(SequenceType sequenceType) {
        return getDefaultSequence(sequenceType, EntityPermission.READ_WRITE);
    }
    
    public SequenceDetailValue getDefaultSequenceDetailValueForUpdate(SequenceType sequenceType) {
        return getDefaultSequenceForUpdate(sequenceType).getLastDetailForUpdate().getSequenceDetailValue().clone();
    }
    
    public Sequence getDefaultSequenceUsingNames(String sequenceTypeName) {
        var sequenceType = getSequenceTypeByName(sequenceTypeName);
        Sequence sequence = null;
        
        if(sequenceType != null) {
            sequence = getDefaultSequence(sequenceType);
        }
        
        return sequence;
    }
    
    public Sequence getSequenceByName(SequenceType sequenceType, String sequenceName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Sequences.fields())
                .from(Sequences)
                .join(SequenceDetails).onKey(SEQUENCES_ACTIVE_DETAIL_FK)
                .where(SequenceDetails.SEQUENCE_TYPE.eq(sequenceType.getPrimaryKey()),
                        SequenceDetails.SEQUENCE_NAME.eq(sequenceName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return sequenceFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Sequence getSequenceByName(SequenceType sequenceType, String sequenceName) {
        return getSequenceByName(sequenceType, sequenceName, EntityPermission.READ_ONLY);
    }
    
    public Sequence getSequenceByNameForUpdate(SequenceType sequenceType, String sequenceName) {
        return getSequenceByName(sequenceType, sequenceName, EntityPermission.READ_WRITE);
    }
    
    public SequenceDetailValue getSequenceDetailValueForUpdate(Sequence sequence) {
        return sequence == null? null: sequence.getLastDetailForUpdate().getSequenceDetailValue().clone();
    }
    
    public SequenceDetailValue getSequenceDetailValueByNameForUpdate(SequenceType sequenceType, String sequenceName) {
        return getSequenceDetailValueForUpdate(getSequenceByNameForUpdate(sequenceType, sequenceName));
    }
    
    public SequenceChoicesBean getSequenceChoices(String defaultSequenceChoice, Language language, boolean allowNullChoice,
            SequenceType sequenceType) {
        var sequences = getSequencesBySequenceType(sequenceType);
        var size = sequences.size() + (allowNullChoice? 1: 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultSequenceChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var sequence : sequences) {
            var sequenceDetail = sequence.getLastDetail();
            var label = getBestSequenceDescription(sequence, language);
            var value = sequenceDetail.getSequenceName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultSequenceChoice != null && defaultSequenceChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && sequenceDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new SequenceChoicesBean(labels, values, defaultValue);
    }
    
    public SequenceTransfer getSequenceTransfer(UserVisit userVisit, Sequence sequence) {
        return sequenceTransferCache.getSequenceTransfer(userVisit, sequence);
    }

    public List<SequenceTransfer> getSequenceTransfers(UserVisit userVisit, Collection<Sequence> sequences) {
        var sequenceTransfers = new ArrayList<SequenceTransfer>(sequences.size());

        sequences.forEach((sequence) ->
            sequenceTransfers.add(sequenceTransferCache.getSequenceTransfer(userVisit, sequence))
        );

        return sequenceTransfers;
    }

    public List<SequenceTransfer> getSequenceTransfersBySequenceType(UserVisit userVisit, SequenceType sequenceType) {
        return getSequenceTransfers(userVisit, getSequencesBySequenceType(sequenceType));
    }

    private void updateSequenceFromValue(SequenceDetailValue sequenceDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        var sequence = sequenceFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, sequenceDetailValue.getSequencePK());
        var sequenceDetail = sequence.getActiveDetailForUpdate();
        
        sequenceDetail.setThruTime(session.getStartTime());
        sequenceDetail.store();

        var sequencePK = sequenceDetail.getSequencePK();
        var sequenceTypePK = sequenceDetail.getSequenceTypePK();
        var sequenceType = sequenceDetail.getSequenceType();
        var sequenceName = sequenceDetailValue.getSequenceName();
        var mask = sequenceDetailValue.getMask();
        var chunkSize = sequenceDetailValue.getChunkSize();
        var isDefault = sequenceDetailValue.getIsDefault();
        var sortOrder = sequenceDetailValue.getSortOrder();
        
        if(checkDefault) {
            var defaultSequence = getDefaultSequence(sequenceType);
            var defaultFound = defaultSequence != null && !defaultSequence.equals(sequence);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultSequenceDetailValue = getDefaultSequenceDetailValueForUpdate(sequenceType);
                
                defaultSequenceDetailValue.setIsDefault(false);
                updateSequenceFromValue(defaultSequenceDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }
        
        sequenceDetail = sequenceDetailFactory.create(sequencePK, sequenceTypePK, sequenceName, mask,
                chunkSize, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sequence.setActiveDetail(sequenceDetail);
        sequence.setLastDetail(sequenceDetail);
        sequence.store();
        
        sendEvent(sequencePK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void updateSequenceFromValue(SequenceDetailValue sequenceDetailValue, BasePK updatedBy) {
        updateSequenceFromValue(sequenceDetailValue, true, updatedBy);
    }
    
    public void deleteSequence(Sequence sequence, BasePK deletedBy) {
        var sequenceDetail = sequence.getLastDetailForUpdate();
        var sequenceTypeName = sequenceDetail.getSequenceType().getLastDetail().getSequenceTypeName();
        
        if(com.echothree.model.control.sequence.common.SequenceTypes.SALES_ORDER.name().equals(sequenceTypeName)) {

            offerUseControl.deleteOfferUsesBySalesOrderSequence(sequence, deletedBy);
        }
        
        deleteSequenceDescriptionsBySequence(sequence, deletedBy);
        
        sequenceDetail.setThruTime(session.getStartTime());
        sequence.setActiveDetail(null);
        sequence.store();
        
        // Check for default, and pick one if necessary
        var sequenceType = sequenceDetail.getSequenceType();
        var defaultSequence = getDefaultSequence(sequenceType);
        if(defaultSequence == null) {
            var sequences = getSequencesBySequenceTypeForUpdate(sequenceType);
            
            if(!sequences.isEmpty()) {
                var iter = sequences.iterator();
                if(iter.hasNext()) {
                    defaultSequence = iter.next();
                }
                var sequenceDetailValue = Objects.requireNonNull(defaultSequence).getLastDetailForUpdate().getSequenceDetailValue().clone();
                
                sequenceDetailValue.setIsDefault(true);
                updateSequenceFromValue(sequenceDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(sequence.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteSequencesBySequenceType(SequenceType sequenceType, BasePK deletedBy) {
        var sequences = getSequencesBySequenceTypeForUpdate(sequenceType);
        
        sequences.forEach((sequence) -> 
                deleteSequence(sequence, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected SequenceDescriptionFactory sequenceDescriptionFactory;

    @Inject
    private SequenceDescriptionTransferCache sequenceDescriptionTransferCache;

    public SequenceDescription createSequenceDescription(Sequence sequence, Language language, String description, BasePK createdBy) {
        var sequenceDescription = sequenceDescriptionFactory.create(sequence, language,
                description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(sequence.getPrimaryKey(), EventTypes.MODIFY, sequenceDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return sequenceDescription;
    }
    
    private List<SequenceDescription> getSequenceDescriptionsBySequence(Sequence sequence, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(SequenceDescriptions.fields())
                    .from(SequenceDescriptions)
                    .join(Languages).onKey(SEQUENCE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(SequenceDescriptions.SEQUENCE.eq(sequence.getPrimaryKey()),
                            SequenceDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    SequenceDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(SequenceDescriptions.fields())
                    .from(SequenceDescriptions)
                    .where(SequenceDescriptions.SEQUENCE.eq(sequence.getPrimaryKey()),
                            SequenceDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return sequenceDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<SequenceDescription> getSequenceDescriptionsBySequence(Sequence sequence) {
        return getSequenceDescriptionsBySequence(sequence, EntityPermission.READ_ONLY);
    }
    
    public List<SequenceDescription> getSequenceDescriptionsBySequenceForUpdate(Sequence sequence) {
        return getSequenceDescriptionsBySequence(sequence, EntityPermission.READ_WRITE);
    }
    
    private SequenceDescription getSequenceDescription(Sequence sequence, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(SequenceDescriptions.fields())
                .from(SequenceDescriptions)
                .where(SequenceDescriptions.SEQUENCE.eq(sequence.getPrimaryKey()),
                        SequenceDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        SequenceDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return sequenceDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public SequenceDescription getSequenceDescription(Sequence sequence, Language language) {
        return getSequenceDescription(sequence, language, EntityPermission.READ_ONLY);
    }
    
    public SequenceDescription getSequenceDescriptionForUpdate(Sequence sequence, Language language) {
        return getSequenceDescription(sequence, language, EntityPermission.READ_WRITE);
    }
    
    public SequenceDescriptionValue getSequenceDescriptionValue(SequenceDescription sequenceDescription) {
        return sequenceDescription == null? null: sequenceDescription.getSequenceDescriptionValue().clone();
    }
    
    public SequenceDescriptionValue getSequenceDescriptionValueForUpdate(Sequence sequence, Language language) {
        return getSequenceDescriptionValue(getSequenceDescriptionForUpdate(sequence, language));
    }
    
    public String getBestSequenceDescription(Sequence sequence, Language language) {
        String description;
        var sequenceDescription = getSequenceDescription(sequence, language);
        
        if(sequenceDescription == null && !language.getIsDefault()) {
            sequenceDescription = getSequenceDescription(sequence, partyControl.getDefaultLanguage());
        }
        
        if(sequenceDescription == null) {
            description = sequence.getLastDetail().getSequenceName();
        } else {
            description = sequenceDescription.getDescription();
        }
        
        return description;
    }
    
    public SequenceDescriptionTransfer getSequenceDescriptionTransfer(UserVisit userVisit, SequenceDescription sequenceDescription) {
        return sequenceDescriptionTransferCache.getSequenceDescriptionTransfer(userVisit, sequenceDescription);
    }
    
    public List<SequenceDescriptionTransfer> getSequenceDescriptionTransfers(UserVisit userVisit, Sequence sequence) {
        var sequenceDescriptions = getSequenceDescriptionsBySequence(sequence);
        List<SequenceDescriptionTransfer> sequenceDescriptionTransfers = new ArrayList<>(sequenceDescriptions.size());
        
        sequenceDescriptions.forEach((sequenceDescription) ->
                sequenceDescriptionTransfers.add(sequenceDescriptionTransferCache.getSequenceDescriptionTransfer(userVisit, sequenceDescription))
        );
        
        return sequenceDescriptionTransfers;
    }
    
    public void updateSequenceDescriptionFromValue(SequenceDescriptionValue sequenceDescriptionValue, BasePK updatedBy) {
        if(sequenceDescriptionValue.hasBeenModified()) {
            var sequenceDescription = sequenceDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, sequenceDescriptionValue.getPrimaryKey());
            
            sequenceDescription.setThruTime(session.getStartTime());
            sequenceDescription.store();

            var sequence = sequenceDescription.getSequence();
            var language = sequenceDescription.getLanguage();
            var description = sequenceDescriptionValue.getDescription();
            
            sequenceDescription = sequenceDescriptionFactory.create(sequence, language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(sequence.getPrimaryKey(), EventTypes.MODIFY, sequenceDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteSequenceDescription(SequenceDescription sequenceDescription, BasePK deletedBy) {
        sequenceDescription.setThruTime(session.getStartTime());
        
        sendEvent(sequenceDescription.getSequencePK(), EventTypes.MODIFY, sequenceDescription.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteSequenceDescriptionsBySequence(Sequence sequence, BasePK deletedBy) {
        var sequenceDescriptions = getSequenceDescriptionsBySequenceForUpdate(sequence);
        
        sequenceDescriptions.forEach((sequenceDescription) -> 
                deleteSequenceDescription(sequenceDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Sequence Values
    // --------------------------------------------------------------------------------

    @Inject
    protected SequenceValueFactory sequenceValueFactory;

    public SequenceValue createSequenceValue(Sequence sequence, String value) {
        return sequenceValueFactory.create(sequence, value);
    }
    
    public SequenceValue getSequenceValue(Sequence sequence) {
        var query = session.getDslContext()
                .select(SequenceValues.fields())
                .from(SequenceValues)
                .where(SequenceValues.SEQUENCE.eq(sequence.getPrimaryKey()));

        return sequenceValueFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public SequenceValue getSequenceValueForUpdate(Sequence sequence) {
        var query = session.getDslContext()
                .select(SequenceValues.fields())
                .from(SequenceValues)
                .where(SequenceValues.SEQUENCE.eq(sequence.getPrimaryKey()))
                .forUpdate();

        return sequenceValueFactory.getEntityFromQuery(EntityPermission.READ_WRITE, query);
    }

}
