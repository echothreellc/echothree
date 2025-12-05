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

package com.echothree.model.control.batch.server.control;

import com.echothree.model.control.batch.common.choice.BatchAliasTypeChoicesBean;
import com.echothree.model.control.batch.common.choice.BatchTypeChoicesBean;
import com.echothree.model.control.batch.common.transfer.BatchAliasTransfer;
import com.echothree.model.control.batch.common.transfer.BatchAliasTypeDescriptionTransfer;
import com.echothree.model.control.batch.common.transfer.BatchAliasTypeTransfer;
import com.echothree.model.control.batch.common.transfer.BatchEntityTransfer;
import com.echothree.model.control.batch.common.transfer.BatchTransfer;
import com.echothree.model.control.batch.common.transfer.BatchTypeDescriptionTransfer;
import com.echothree.model.control.batch.common.transfer.BatchTypeEntityTypeTransfer;
import com.echothree.model.control.batch.common.transfer.BatchTypeTransfer;
import com.echothree.model.control.batch.server.transfer.BatchAliasTransferCache;
import com.echothree.model.control.batch.server.transfer.BatchAliasTypeDescriptionTransferCache;
import com.echothree.model.control.batch.server.transfer.BatchAliasTypeTransferCache;
import com.echothree.model.control.batch.server.transfer.BatchEntityTransferCache;
import com.echothree.model.control.batch.server.transfer.BatchTransferCache;
import com.echothree.model.control.batch.server.transfer.BatchTypeDescriptionTransferCache;
import com.echothree.model.control.batch.server.transfer.BatchTypeEntityTypeTransferCache;
import com.echothree.model.control.batch.server.transfer.BatchTypeTransferCache;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.data.batch.common.pk.BatchPK;
import com.echothree.model.data.batch.server.entity.Batch;
import com.echothree.model.data.batch.server.entity.BatchAlias;
import com.echothree.model.data.batch.server.entity.BatchAliasType;
import com.echothree.model.data.batch.server.entity.BatchAliasTypeDescription;
import com.echothree.model.data.batch.server.entity.BatchEntity;
import com.echothree.model.data.batch.server.entity.BatchType;
import com.echothree.model.data.batch.server.entity.BatchTypeDescription;
import com.echothree.model.data.batch.server.entity.BatchTypeEntityType;
import com.echothree.model.data.batch.server.factory.BatchAliasFactory;
import com.echothree.model.data.batch.server.factory.BatchAliasTypeDescriptionFactory;
import com.echothree.model.data.batch.server.factory.BatchAliasTypeDetailFactory;
import com.echothree.model.data.batch.server.factory.BatchAliasTypeFactory;
import com.echothree.model.data.batch.server.factory.BatchDetailFactory;
import com.echothree.model.data.batch.server.factory.BatchEntityFactory;
import com.echothree.model.data.batch.server.factory.BatchFactory;
import com.echothree.model.data.batch.server.factory.BatchTypeDescriptionFactory;
import com.echothree.model.data.batch.server.factory.BatchTypeDetailFactory;
import com.echothree.model.data.batch.server.factory.BatchTypeEntityTypeFactory;
import com.echothree.model.data.batch.server.factory.BatchTypeFactory;
import com.echothree.model.data.batch.server.value.BatchAliasTypeDescriptionValue;
import com.echothree.model.data.batch.server.value.BatchAliasTypeDetailValue;
import com.echothree.model.data.batch.server.value.BatchAliasValue;
import com.echothree.model.data.batch.server.value.BatchDetailValue;
import com.echothree.model.data.batch.server.value.BatchTypeDescriptionValue;
import com.echothree.model.data.batch.server.value.BatchTypeDetailValue;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import com.echothree.util.server.cdi.CommandScope;
import javax.inject.Inject;

@CommandScope
public class BatchControl
        extends BaseModelControl {
    
    /** Creates a new instance of BatchControl */
    protected BatchControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Batch Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    BatchTypeTransferCache batchTypeTransferCache;

    @Inject
    BatchTypeDescriptionTransferCache batchTypeDescriptionTransferCache;

    @Inject
    BatchTypeEntityTypeTransferCache batchTypeEntityTypeTransferCache;

    @Inject
    BatchAliasTypeTransferCache batchAliasTypeTransferCache;

    @Inject
    BatchAliasTypeDescriptionTransferCache batchAliasTypeDescriptionTransferCache;

    @Inject
    BatchTransferCache batchTransferCache;

    @Inject
    BatchAliasTransferCache batchAliasTransferCache;

    @Inject
    BatchEntityTransferCache batchEntityTransferCache;

    // --------------------------------------------------------------------------------
    //   Batch Types
    // --------------------------------------------------------------------------------
    
    public BatchType createBatchType(String batchTypeName, BatchType parentBatchType, SequenceType batchSequenceType, Workflow batchWorkflow,
            WorkflowEntrance batchWorkflowEntrance, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultBatchType = getDefaultBatchType();
        var defaultFound = defaultBatchType != null;
        
        if(defaultFound && isDefault) {
            var defaultBatchTypeDetailValue = getDefaultBatchTypeDetailValueForUpdate();
            
            defaultBatchTypeDetailValue.setIsDefault(false);
            updateBatchTypeFromValue(defaultBatchTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var batchType = BatchTypeFactory.getInstance().create();
        var batchTypeDetail = BatchTypeDetailFactory.getInstance().create(batchType, batchTypeName, parentBatchType, batchSequenceType,
                batchWorkflow, batchWorkflowEntrance, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        batchType = BatchTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                batchType.getPrimaryKey());
        batchType.setActiveDetail(batchTypeDetail);
        batchType.setLastDetail(batchTypeDetail);
        batchType.store();
        
        sendEvent(batchType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return batchType;
    }
    
    private static final Map<EntityPermission, String> getBatchTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchtypes, batchtypedetails " +
                "WHERE btchtyp_activedetailid = btchtypdt_batchtypedetailid " +
                "AND btchtypdt_batchtypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchtypes, batchtypedetails " +
                "WHERE btchtyp_activedetailid = btchtypdt_batchtypedetailid " +
                "AND btchtypdt_batchtypename = ? " +
                "FOR UPDATE");
        getBatchTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private BatchType getBatchTypeByName(String batchTypeName, EntityPermission entityPermission) {
        return BatchTypeFactory.getInstance().getEntityFromQuery(entityPermission, getBatchTypeByNameQueries, batchTypeName);
    }

    public BatchType getBatchTypeByName(String batchTypeName) {
        return getBatchTypeByName(batchTypeName, EntityPermission.READ_ONLY);
    }

    public BatchType getBatchTypeByNameForUpdate(String batchTypeName) {
        return getBatchTypeByName(batchTypeName, EntityPermission.READ_WRITE);
    }

    public BatchTypeDetailValue getBatchTypeDetailValueForUpdate(BatchType batchType) {
        return batchType == null? null: batchType.getLastDetailForUpdate().getBatchTypeDetailValue().clone();
    }

    public BatchTypeDetailValue getBatchTypeDetailValueByNameForUpdate(String batchTypeName) {
        return getBatchTypeDetailValueForUpdate(getBatchTypeByNameForUpdate(batchTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultBatchTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchtypes, batchtypedetails " +
                "WHERE btchtyp_activedetailid = btchtypdt_batchtypedetailid " +
                "AND btchtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchtypes, batchtypedetails " +
                "WHERE btchtyp_activedetailid = btchtypdt_batchtypedetailid " +
                "AND btchtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultBatchTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private BatchType getDefaultBatchType(EntityPermission entityPermission) {
        return BatchTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultBatchTypeQueries);
    }

    public BatchType getDefaultBatchType() {
        return getDefaultBatchType(EntityPermission.READ_ONLY);
    }

    public BatchType getDefaultBatchTypeForUpdate() {
        return getDefaultBatchType(EntityPermission.READ_WRITE);
    }

    public BatchTypeDetailValue getDefaultBatchTypeDetailValueForUpdate() {
        return getDefaultBatchTypeForUpdate().getLastDetailForUpdate().getBatchTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getBatchTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchtypes, batchtypedetails " +
                "WHERE btchtyp_activedetailid = btchtypdt_batchtypedetailid " +
                "ORDER BY btchtypdt_sortorder, btchtypdt_batchtypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchtypes, batchtypedetails " +
                "WHERE btchtyp_activedetailid = btchtypdt_batchtypedetailid " +
                "FOR UPDATE");
        getBatchTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<BatchType> getBatchTypes(EntityPermission entityPermission) {
        return BatchTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getBatchTypesQueries);
    }

    public List<BatchType> getBatchTypes() {
        return getBatchTypes(EntityPermission.READ_ONLY);
    }

    public List<BatchType> getBatchTypesForUpdate() {
        return getBatchTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getBatchTypesByParentBatchTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchtypes, batchtypedetails " +
                "WHERE btchtyp_activedetailid = btchtypdt_batchtypedetailid AND btchtypdt_parentbatchtypeid = ? " +
                "ORDER BY btchtypdt_sortorder, btchtypdt_batchtypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchtypes, batchtypedetails " +
                "WHERE btchtyp_activedetailid = btchtypdt_batchtypedetailid AND btchtypdt_parentbatchtypeid = ? " +
                "FOR UPDATE");
        getBatchTypesByParentBatchTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<BatchType> getBatchTypesByParentBatchType(BatchType parentBatchType,
            EntityPermission entityPermission) {
        return BatchTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getBatchTypesByParentBatchTypeQueries,
                parentBatchType);
    }

    public List<BatchType> getBatchTypesByParentBatchType(BatchType parentBatchType) {
        return getBatchTypesByParentBatchType(parentBatchType, EntityPermission.READ_ONLY);
    }

    public List<BatchType> getBatchTypesByParentBatchTypeForUpdate(BatchType parentBatchType) {
        return getBatchTypesByParentBatchType(parentBatchType, EntityPermission.READ_WRITE);
    }

    public BatchTypeTransfer getBatchTypeTransfer(UserVisit userVisit, BatchType batchType) {
        return batchTypeTransferCache.getTransfer(userVisit, batchType);
    }
    
    public List<BatchTypeTransfer> getBatchTypeTransfers(UserVisit userVisit) {
        var batchTypes = getBatchTypes();
        List<BatchTypeTransfer> batchTypeTransfers = new ArrayList<>(batchTypes.size());
        
        batchTypes.forEach((batchType) ->
                batchTypeTransfers.add(batchTypeTransferCache.getTransfer(userVisit, batchType))
        );
        
        return batchTypeTransfers;
    }
    
    public BatchTypeChoicesBean getBatchTypeChoices(String defaultBatchTypeChoice,
            Language language, boolean allowNullChoice) {
        var batchTypes = getBatchTypes();
        var size = batchTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultBatchTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var batchType : batchTypes) {
            var batchTypeDetail = batchType.getLastDetail();
            
            var label = getBestBatchTypeDescription(batchType, language);
            var value = batchTypeDetail.getBatchTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultBatchTypeChoice != null && defaultBatchTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && batchTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new BatchTypeChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentBatchTypeSafe(BatchType batchType,
            BatchType parentBatchType) {
        var safe = true;
        
        if(parentBatchType != null) {
            Set<BatchType> parentBatchTypes = new HashSet<>();
            
            parentBatchTypes.add(batchType);
            do {
                if(parentBatchTypes.contains(parentBatchType)) {
                    safe = false;
                    break;
                }
                
                parentBatchTypes.add(parentBatchType);
                parentBatchType = parentBatchType.getLastDetail().getParentBatchType();
            } while(parentBatchType != null);
        }
        
        return safe;
    }
    
    private void updateBatchTypeFromValue(BatchTypeDetailValue batchTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(batchTypeDetailValue.hasBeenModified()) {
            var batchType = BatchTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     batchTypeDetailValue.getBatchTypePK());
            var batchTypeDetail = batchType.getActiveDetailForUpdate();
            
            batchTypeDetail.setThruTime(session.getStartTime());
            batchTypeDetail.store();

            var batchTypePK = batchTypeDetail.getBatchTypePK(); // Not updated
            var batchTypeName = batchTypeDetailValue.getBatchTypeName();
            var parentBatchTypePK = batchTypeDetailValue.getParentBatchTypePK();
            var batchSequenceTypePK = batchTypeDetailValue.getBatchSequenceTypePK();
            var batchWorkflowPK = batchTypeDetailValue.getBatchWorkflowPK();
            var batchWorkflowEntrancePK = batchTypeDetailValue.getBatchWorkflowEntrancePK();
            var isDefault = batchTypeDetailValue.getIsDefault();
            var sortOrder = batchTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultBatchType = getDefaultBatchType();
                var defaultFound = defaultBatchType != null && !defaultBatchType.equals(batchType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultBatchTypeDetailValue = getDefaultBatchTypeDetailValueForUpdate();
                    
                    defaultBatchTypeDetailValue.setIsDefault(false);
                    updateBatchTypeFromValue(defaultBatchTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            batchTypeDetail = BatchTypeDetailFactory.getInstance().create(batchTypePK, batchTypeName, parentBatchTypePK, batchSequenceTypePK,
                    batchWorkflowPK, batchWorkflowEntrancePK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME_LONG);
            
            batchType.setActiveDetail(batchTypeDetail);
            batchType.setLastDetail(batchTypeDetail);
            
            sendEvent(batchTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateBatchTypeFromValue(BatchTypeDetailValue batchTypeDetailValue, BasePK updatedBy) {
        updateBatchTypeFromValue(batchTypeDetailValue, true, updatedBy);
    }
    
    private void deleteBatchType(BatchType batchType, boolean checkDefault, BasePK deletedBy) {
        var batchTypeDetail = batchType.getLastDetailForUpdate();

        deleteBatchTypesByParentBatchType(batchType, deletedBy);
        deleteBatchTypeDescriptionsByBatchType(batchType, deletedBy);
        deleteBatchTypeEntityTypesByBatchType(batchType, deletedBy);
        deleteBatchAliasTypesByBatchType(batchType, deletedBy);
        deleteBatchesByBatchType(batchType, deletedBy);
        
        batchTypeDetail.setThruTime(session.getStartTime());
        batchType.setActiveDetail(null);
        batchType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultBatchType = getDefaultBatchType();

            if(defaultBatchType == null) {
                var batchTypes = getBatchTypesForUpdate();

                if(!batchTypes.isEmpty()) {
                    var iter = batchTypes.iterator();
                    if(iter.hasNext()) {
                        defaultBatchType = iter.next();
                    }
                    var batchTypeDetailValue = Objects.requireNonNull(defaultBatchType).getLastDetailForUpdate().getBatchTypeDetailValue().clone();

                    batchTypeDetailValue.setIsDefault(true);
                    updateBatchTypeFromValue(batchTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(batchType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteBatchType(BatchType batchType, BasePK deletedBy) {
        deleteBatchType(batchType, true, deletedBy);
    }

    private void deleteBatchTypes(List<BatchType> batchTypes, boolean checkDefault, BasePK deletedBy) {
        batchTypes.forEach((batchType) -> deleteBatchType(batchType, checkDefault, deletedBy));
    }

    public void deleteBatchTypes(List<BatchType> batchTypes, BasePK deletedBy) {
        deleteBatchTypes(batchTypes, true, deletedBy);
    }

    private void deleteBatchTypesByParentBatchType(BatchType parentBatchType, BasePK deletedBy) {
        deleteBatchTypes(getBatchTypesByParentBatchTypeForUpdate(parentBatchType), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Batch Type Descriptions
    // --------------------------------------------------------------------------------
    
    public BatchTypeDescription createBatchTypeDescription(BatchType batchType, Language language, String description, BasePK createdBy) {
        var batchTypeDescription = BatchTypeDescriptionFactory.getInstance().create(batchType, language, description,
                session.getStartTime(), Session.MAX_TIME_LONG);
        
        sendEvent(batchType.getPrimaryKey(), EventTypes.MODIFY, batchTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return batchTypeDescription;
    }
    
    private static final Map<EntityPermission, String> getBatchTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchtypedescriptions " +
                "WHERE btchtypd_btchtyp_batchtypeid = ? AND btchtypd_lang_languageid = ? AND btchtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchtypedescriptions " +
                "WHERE btchtypd_btchtyp_batchtypeid = ? AND btchtypd_lang_languageid = ? AND btchtypd_thrutime = ? " +
                "FOR UPDATE");
        getBatchTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private BatchTypeDescription getBatchTypeDescription(BatchType batchType, Language language, EntityPermission entityPermission) {
        return BatchTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getBatchTypeDescriptionQueries,
                batchType, language, Session.MAX_TIME);
    }
    
    public BatchTypeDescription getBatchTypeDescription(BatchType batchType, Language language) {
        return getBatchTypeDescription(batchType, language, EntityPermission.READ_ONLY);
    }
    
    public BatchTypeDescription getBatchTypeDescriptionForUpdate(BatchType batchType, Language language) {
        return getBatchTypeDescription(batchType, language, EntityPermission.READ_WRITE);
    }
    
    public BatchTypeDescriptionValue getBatchTypeDescriptionValue(BatchTypeDescription batchTypeDescription) {
        return batchTypeDescription == null? null: batchTypeDescription.getBatchTypeDescriptionValue().clone();
    }
    
    public BatchTypeDescriptionValue getBatchTypeDescriptionValueForUpdate(BatchType batchType, Language language) {
        return getBatchTypeDescriptionValue(getBatchTypeDescriptionForUpdate(batchType, language));
    }
    
    private static final Map<EntityPermission, String> getBatchTypeDescriptionsByBatchTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchtypedescriptions, languages " +
                "WHERE btchtypd_btchtyp_batchtypeid = ? AND btchtypd_thrutime = ? AND btchtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchtypedescriptions " +
                "WHERE btchtypd_btchtyp_batchtypeid = ? AND btchtypd_thrutime = ? " +
                "FOR UPDATE");
        getBatchTypeDescriptionsByBatchTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<BatchTypeDescription> getBatchTypeDescriptionsByBatchType(BatchType batchType, EntityPermission entityPermission) {
        return BatchTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getBatchTypeDescriptionsByBatchTypeQueries,
                batchType, Session.MAX_TIME);
    }
    
    public List<BatchTypeDescription> getBatchTypeDescriptionsByBatchType(BatchType batchType) {
        return getBatchTypeDescriptionsByBatchType(batchType, EntityPermission.READ_ONLY);
    }
    
    public List<BatchTypeDescription> getBatchTypeDescriptionsByBatchTypeForUpdate(BatchType batchType) {
        return getBatchTypeDescriptionsByBatchType(batchType, EntityPermission.READ_WRITE);
    }
    
    public String getBestBatchTypeDescription(BatchType batchType, Language language) {
        String description;
        var batchTypeDescription = getBatchTypeDescription(batchType, language);
        
        if(batchTypeDescription == null && !language.getIsDefault()) {
            batchTypeDescription = getBatchTypeDescription(batchType, partyControl.getDefaultLanguage());
        }
        
        if(batchTypeDescription == null) {
            description = batchType.getLastDetail().getBatchTypeName();
        } else {
            description = batchTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public BatchTypeDescriptionTransfer getBatchTypeDescriptionTransfer(UserVisit userVisit, BatchTypeDescription batchTypeDescription) {
        return batchTypeDescriptionTransferCache.getTransfer(userVisit, batchTypeDescription);
    }
    
    public List<BatchTypeDescriptionTransfer> getBatchTypeDescriptionTransfersByBatchType(UserVisit userVisit, BatchType batchType) {
        var batchTypeDescriptions = getBatchTypeDescriptionsByBatchType(batchType);
        List<BatchTypeDescriptionTransfer> batchTypeDescriptionTransfers = new ArrayList<>(batchTypeDescriptions.size());
        
        batchTypeDescriptions.forEach((batchTypeDescription) ->
                batchTypeDescriptionTransfers.add(batchTypeDescriptionTransferCache.getTransfer(userVisit, batchTypeDescription))
        );
        
        return batchTypeDescriptionTransfers;
    }
    
    public void updateBatchTypeDescriptionFromValue(BatchTypeDescriptionValue batchTypeDescriptionValue, BasePK updatedBy) {
        if(batchTypeDescriptionValue.hasBeenModified()) {
            var batchTypeDescription = BatchTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    batchTypeDescriptionValue.getPrimaryKey());
            
            batchTypeDescription.setThruTime(session.getStartTime());
            batchTypeDescription.store();

            var batchType = batchTypeDescription.getBatchType();
            var language = batchTypeDescription.getLanguage();
            var description = batchTypeDescriptionValue.getDescription();
            
            batchTypeDescription = BatchTypeDescriptionFactory.getInstance().create(batchType, language, description,
                    session.getStartTime(), Session.MAX_TIME_LONG);
            
            sendEvent(batchType.getPrimaryKey(), EventTypes.MODIFY, batchTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteBatchTypeDescription(BatchTypeDescription batchTypeDescription, BasePK deletedBy) {
        batchTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(batchTypeDescription.getBatchTypePK(), EventTypes.MODIFY, batchTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteBatchTypeDescriptionsByBatchType(BatchType batchType, BasePK deletedBy) {
        var batchTypeDescriptions = getBatchTypeDescriptionsByBatchTypeForUpdate(batchType);
        
        batchTypeDescriptions.forEach((batchTypeDescription) -> 
                deleteBatchTypeDescription(batchTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Batch Type Entity Types
    // --------------------------------------------------------------------------------

    public BatchTypeEntityType createBatchTypeEntityType(BatchType batchType, EntityType entityType, BasePK createdBy) {
        var batchTypeEntityType = BatchTypeEntityTypeFactory.getInstance().create(batchType, entityType,
                session.getStartTime(), Session.MAX_TIME_LONG);

        sendEvent(batchType.getPrimaryKey(), EventTypes.MODIFY, batchTypeEntityType.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return batchTypeEntityType;
    }

    public long countBatchTypeEntityTypesByBatchType(BatchType batchType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM batchtypeentitytypes " +
                "WHERE btchtypent_btchtyp_batchtypeid = ? AND btchtypent_thrutime = ?",
                batchType, Session.MAX_TIME_LONG);
    }

    public boolean getBatchTypeEntityTypeExists(BatchType batchType, EntityType entityType) {
        return 1 == session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM batchtypeentitytypes " +
                "WHERE btchtypent_btchtyp_batchtypeid = ? AND btchtypent_ent_entitytypeid = ? AND btchtypent_thrutime = ?",
                batchType, entityType, Session.MAX_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getBatchTypeEntityTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchtypeentitytypes " +
                "WHERE btchtypent_btchtyp_batchtypeid = ? AND btchtypent_ent_entitytypeid = ? AND btchtypent_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchtypeentitytypes " +
                "WHERE btchtypent_btchtyp_batchtypeid = ? AND btchtypent_ent_entitytypeid = ? AND btchtypent_thrutime = ? " +
                "FOR UPDATE");
        getBatchTypeEntityTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private BatchTypeEntityType getBatchTypeEntityType(BatchType batchType, EntityType entityType, EntityPermission entityPermission) {
        return BatchTypeEntityTypeFactory.getInstance().getEntityFromQuery(entityPermission, getBatchTypeEntityTypeQueries,
                batchType, entityType, Session.MAX_TIME_LONG);
    }

    public BatchTypeEntityType getBatchTypeEntityType(BatchType batchType, EntityType entityType) {
        return getBatchTypeEntityType(batchType, entityType, EntityPermission.READ_ONLY);
    }

    public BatchTypeEntityType getBatchTypeEntityTypeForUpdate(BatchType batchType, EntityType entityType) {
        return getBatchTypeEntityType(batchType, entityType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getBatchTypeEntityTypesByBatchTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchtypeentitytypes, entitytypes, entitytypedetails " +
                "WHERE btchtypent_btchtyp_batchtypeid = ? AND btchtypent_thrutime = ? " +
                "AND btchtypent_ent_entitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid " +
                "ORDER BY entdt_sortorder, entdt_entitytypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchtypeentitytypes " +
                "WHERE btchtypent_btchtyp_batchtypeid = ? AND btchtypent_thrutime = ? " +
                "FOR UPDATE");
        getBatchTypeEntityTypesByBatchTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<BatchTypeEntityType> getBatchTypeEntityTypesByBatchType(BatchType batchType, EntityPermission entityPermission) {
        return BatchTypeEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getBatchTypeEntityTypesByBatchTypeQueries,
                batchType, Session.MAX_TIME_LONG);
    }

    public List<BatchTypeEntityType> getBatchTypeEntityTypesByBatchType(BatchType batchType) {
        return getBatchTypeEntityTypesByBatchType(batchType, EntityPermission.READ_ONLY);
    }

    public List<BatchTypeEntityType> getBatchTypeEntityTypesByBatchTypeForUpdate(BatchType batchType) {
        return getBatchTypeEntityTypesByBatchType(batchType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getBatchTypeEntityTypesByEntityTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchtypeentitytypes, batchtypes, batchtypedetails " +
                "WHERE btchtypent_ent_entitytypeid = ? AND btchtypent_thrutime = ? " +
                "AND btchtypent_btchtyp_batchtypeid = btchtyp_batchtypeid AND btchtyp_lastdetailid = btchtypdt_batchtypedetailid " +
                "ORDER BY btchtypdt_sortorder, btchtypdt_batchtypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchtypeentitytypes " +
                "WHERE btchtypent_btchtyp_batchtypeid = ? AND btchtypent_thrutime = ? " +
                "FOR UPDATE");
        getBatchTypeEntityTypesByEntityTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<BatchTypeEntityType> getBatchTypeEntityTypesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        return BatchTypeEntityTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getBatchTypeEntityTypesByEntityTypeQueries,
                entityType, Session.MAX_TIME_LONG);
    }

    public List<BatchTypeEntityType> getBatchTypeEntityTypesByEntityType(EntityType entityType) {
        return getBatchTypeEntityTypesByEntityType(entityType, EntityPermission.READ_ONLY);
    }

    public List<BatchTypeEntityType> getBatchTypeEntityTypesByEntityTypeForUpdate(EntityType entityType) {
        return getBatchTypeEntityTypesByEntityType(entityType, EntityPermission.READ_WRITE);
    }

    public BatchTypeEntityTypeTransfer getBatchTypeEntityTypeTransfer(UserVisit userVisit, BatchTypeEntityType batchTypeEntityType) {
        return batchTypeEntityTypeTransferCache.getTransfer(userVisit, batchTypeEntityType);
    }

    public List<BatchTypeEntityTypeTransfer> getBatchTypeEntityTypeTransfers(UserVisit userVisit, Collection<BatchTypeEntityType> batchTypeEntityTypes) {
        List<BatchTypeEntityTypeTransfer> batchTypeEntityTypeTransfers = new ArrayList<>(batchTypeEntityTypes.size());

        batchTypeEntityTypes.forEach((batchTypeEntityType) ->
                batchTypeEntityTypeTransfers.add(batchTypeEntityTypeTransferCache.getTransfer(userVisit, batchTypeEntityType))
        );

        return batchTypeEntityTypeTransfers;
    }

    public List<BatchTypeEntityTypeTransfer> getBatchTypeEntityTypeTransfersByBatchType(UserVisit userVisit, BatchType batchType) {
        return getBatchTypeEntityTypeTransfers(userVisit, getBatchTypeEntityTypesByBatchType(batchType));
    }

    public List<BatchTypeEntityTypeTransfer> getBatchTypeEntityTypeTransfersByEntityType(UserVisit userVisit, EntityType entityType) {
        return getBatchTypeEntityTypeTransfers(userVisit, getBatchTypeEntityTypesByEntityType(entityType));
    }

    public void deleteBatchTypeEntityType(BatchTypeEntityType batchTypeEntityType, BasePK deletedBy) {
        batchTypeEntityType.setThruTime(session.getStartTime());

        sendEvent(batchTypeEntityType.getBatchTypePK(), EventTypes.MODIFY, batchTypeEntityType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteBatchTypeEntityTypes(List<BatchTypeEntityType> batchTypeEntityTypes, BasePK deletedBy) {
        batchTypeEntityTypes.forEach((batchTypeEntityType) -> 
                deleteBatchTypeEntityType(batchTypeEntityType, deletedBy)
        );
    }

    public void deleteBatchTypeEntityTypesByBatchType(BatchType batchType, BasePK deletedBy) {
        deleteBatchTypeEntityTypes(getBatchTypeEntityTypesByBatchTypeForUpdate(batchType), deletedBy);
    }

    public void deleteBatchTypeEntityTypesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteBatchTypeEntityTypes(getBatchTypeEntityTypesByEntityTypeForUpdate(entityType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Batch Alias Types
    // --------------------------------------------------------------------------------
    
    public BatchAliasType createBatchAliasType(BatchType batchType, String batchAliasTypeName, String validationPattern, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        var defaultBatchAliasType = getDefaultBatchAliasType(batchType);
        var defaultFound = defaultBatchAliasType != null;
        
        if(defaultFound && isDefault) {
            var defaultBatchAliasTypeDetailValue = getDefaultBatchAliasTypeDetailValueForUpdate(batchType);
            
            defaultBatchAliasTypeDetailValue.setIsDefault(false);
            updateBatchAliasTypeFromValue(defaultBatchAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var batchAliasType = BatchAliasTypeFactory.getInstance().create();
        var batchAliasTypeDetail = BatchAliasTypeDetailFactory.getInstance().create(batchAliasType, batchType, batchAliasTypeName,
                validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME_LONG);
        
        // Convert to R/W
        batchAliasType = BatchAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, batchAliasType.getPrimaryKey());
        batchAliasType.setActiveDetail(batchAliasTypeDetail);
        batchAliasType.setLastDetail(batchAliasTypeDetail);
        batchAliasType.store();
        
        sendEvent(batchAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return batchAliasType;
    }
    
    private static final Map<EntityPermission, String> getBatchAliasTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchaliastypes, batchaliastypedetails " +
                "WHERE btchat_activedetailid = btchatdt_batchaliastypedetailid AND btchatdt_btchtyp_batchtypeid = ? " +
                "AND btchatdt_batchaliastypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchaliastypes, batchaliastypedetails " +
                "WHERE btchat_activedetailid = btchatdt_batchaliastypedetailid AND btchatdt_btchtyp_batchtypeid = ? " +
                "AND btchatdt_batchaliastypename = ? " +
                "FOR UPDATE");
        getBatchAliasTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private BatchAliasType getBatchAliasTypeByName(BatchType batchType, String batchAliasTypeName, EntityPermission entityPermission) {
        return BatchAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getBatchAliasTypeByNameQueries,
                batchType, batchAliasTypeName);
    }
    
    public BatchAliasType getBatchAliasTypeByName(BatchType batchType, String batchAliasTypeName) {
        return getBatchAliasTypeByName(batchType, batchAliasTypeName, EntityPermission.READ_ONLY);
    }
    
    public BatchAliasType getBatchAliasTypeByNameForUpdate(BatchType batchType, String batchAliasTypeName) {
        return getBatchAliasTypeByName(batchType, batchAliasTypeName, EntityPermission.READ_WRITE);
    }
    
    public BatchAliasTypeDetailValue getBatchAliasTypeDetailValueForUpdate(BatchAliasType batchAliasType) {
        return batchAliasType == null? null: batchAliasType.getLastDetailForUpdate().getBatchAliasTypeDetailValue().clone();
    }
    
    public BatchAliasTypeDetailValue getBatchAliasTypeDetailValueByNameForUpdate(BatchType batchType,
            String batchAliasTypeName) {
        return getBatchAliasTypeDetailValueForUpdate(getBatchAliasTypeByNameForUpdate(batchType, batchAliasTypeName));
    }
    
    private static final Map<EntityPermission, String> getDefaultBatchAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchaliastypes, batchaliastypedetails " +
                "WHERE btchat_activedetailid = btchatdt_batchaliastypedetailid AND btchatdt_btchtyp_batchtypeid = ? " +
                "AND btchatdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchaliastypes, batchaliastypedetails " +
                "WHERE btchat_activedetailid = btchatdt_batchaliastypedetailid AND btchatdt_btchtyp_batchtypeid = ? " +
                "AND btchatdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultBatchAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private BatchAliasType getDefaultBatchAliasType(BatchType batchType, EntityPermission entityPermission) {
        return BatchAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultBatchAliasTypeQueries, batchType);
    }
    
    public BatchAliasType getDefaultBatchAliasType(BatchType batchType) {
        return getDefaultBatchAliasType(batchType, EntityPermission.READ_ONLY);
    }
    
    public BatchAliasType getDefaultBatchAliasTypeForUpdate(BatchType batchType) {
        return getDefaultBatchAliasType(batchType, EntityPermission.READ_WRITE);
    }
    
    public BatchAliasTypeDetailValue getDefaultBatchAliasTypeDetailValueForUpdate(BatchType batchType) {
        return getDefaultBatchAliasTypeForUpdate(batchType).getLastDetailForUpdate().getBatchAliasTypeDetailValue().clone();
    }
    
    private static final Map<EntityPermission, String> getBatchAliasTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchaliastypes, batchaliastypedetails " +
                "WHERE btchat_activedetailid = btchatdt_batchaliastypedetailid AND btchatdt_btchtyp_batchtypeid = ? " +
                "ORDER BY btchatdt_sortorder, btchatdt_batchaliastypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchaliastypes, batchaliastypedetails " +
                "WHERE btchat_activedetailid = btchatdt_batchaliastypedetailid AND btchatdt_btchtyp_batchtypeid = ? " +
                "FOR UPDATE");
        getBatchAliasTypesQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<BatchAliasType> getBatchAliasTypes(BatchType batchType, EntityPermission entityPermission) {
        return BatchAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getBatchAliasTypesQueries, batchType);
    }
    
    public List<BatchAliasType> getBatchAliasTypes(BatchType batchType) {
        return getBatchAliasTypes(batchType, EntityPermission.READ_ONLY);
    }
    
    public List<BatchAliasType> getBatchAliasTypesForUpdate(BatchType batchType) {
        return getBatchAliasTypes(batchType, EntityPermission.READ_WRITE);
    }
    
    public BatchAliasTypeTransfer getBatchAliasTypeTransfer(UserVisit userVisit, BatchAliasType batchAliasType) {
        return batchAliasTypeTransferCache.getTransfer(userVisit, batchAliasType);
    }
    
    public List<BatchAliasTypeTransfer> getBatchAliasTypeTransfers(UserVisit userVisit, BatchType batchType) {
        var batchAliasTypes = getBatchAliasTypes(batchType);
        List<BatchAliasTypeTransfer> batchAliasTypeTransfers = new ArrayList<>(batchAliasTypes.size());
        
        batchAliasTypes.forEach((batchAliasType) ->
                batchAliasTypeTransfers.add(batchAliasTypeTransferCache.getTransfer(userVisit, batchAliasType))
        );
        
        return batchAliasTypeTransfers;
    }
    
    public BatchAliasTypeChoicesBean getBatchAliasTypeChoices(String defaultBatchAliasTypeChoice, Language language,
            boolean allowNullChoice, BatchType batchType) {
        var batchAliasTypes = getBatchAliasTypes(batchType);
        var size = batchAliasTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultBatchAliasTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var batchAliasType : batchAliasTypes) {
            var batchAliasTypeDetail = batchAliasType.getLastDetail();
            
            var label = getBestBatchAliasTypeDescription(batchAliasType, language);
            var value = batchAliasTypeDetail.getBatchAliasTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultBatchAliasTypeChoice != null && defaultBatchAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && batchAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new BatchAliasTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateBatchAliasTypeFromValue(BatchAliasTypeDetailValue batchAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(batchAliasTypeDetailValue.hasBeenModified()) {
            var batchAliasType = BatchAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    batchAliasTypeDetailValue.getBatchAliasTypePK());
            var batchAliasTypeDetail = batchAliasType.getActiveDetailForUpdate();
            
            batchAliasTypeDetail.setThruTime(session.getStartTime());
            batchAliasTypeDetail.store();

            var batchAliasTypePK = batchAliasTypeDetail.getBatchAliasTypePK();
            var batchType = batchAliasTypeDetail.getBatchType();
            var batchTypePK = batchType.getPrimaryKey();
            var batchAliasTypeName = batchAliasTypeDetailValue.getBatchAliasTypeName();
            var validationPattern = batchAliasTypeDetailValue.getValidationPattern();
            var isDefault = batchAliasTypeDetailValue.getIsDefault();
            var sortOrder = batchAliasTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultBatchAliasType = getDefaultBatchAliasType(batchType);
                var defaultFound = defaultBatchAliasType != null && !defaultBatchAliasType.equals(batchAliasType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultBatchAliasTypeDetailValue = getDefaultBatchAliasTypeDetailValueForUpdate(batchType);
                    
                    defaultBatchAliasTypeDetailValue.setIsDefault(false);
                    updateBatchAliasTypeFromValue(defaultBatchAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            batchAliasTypeDetail = BatchAliasTypeDetailFactory.getInstance().create(batchAliasTypePK, batchTypePK, batchAliasTypeName,
                    validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME_LONG);
            
            batchAliasType.setActiveDetail(batchAliasTypeDetail);
            batchAliasType.setLastDetail(batchAliasTypeDetail);
            
            sendEvent(batchAliasTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateBatchAliasTypeFromValue(BatchAliasTypeDetailValue batchAliasTypeDetailValue, BasePK updatedBy) {
        updateBatchAliasTypeFromValue(batchAliasTypeDetailValue, true, updatedBy);
    }
    
    public void deleteBatchAliasType(BatchAliasType batchAliasType, BasePK deletedBy) {
        deleteBatchAliasesByBatchAliasType(batchAliasType, deletedBy);
        deleteBatchAliasTypeDescriptionsByBatchAliasType(batchAliasType, deletedBy);

        var batchAliasTypeDetail = batchAliasType.getLastDetailForUpdate();
        batchAliasTypeDetail.setThruTime(session.getStartTime());
        batchAliasType.setActiveDetail(null);
        batchAliasType.store();
        
        // Check for default, and pick one if necessary
        var batchType = batchAliasTypeDetail.getBatchType();
        var defaultBatchAliasType = getDefaultBatchAliasType(batchType);
        if(defaultBatchAliasType == null) {
            var batchAliasTypes = getBatchAliasTypesForUpdate(batchType);
            
            if(!batchAliasTypes.isEmpty()) {
                var iter = batchAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultBatchAliasType = iter.next();
                }
                var batchAliasTypeDetailValue = Objects.requireNonNull(defaultBatchAliasType).getLastDetailForUpdate().getBatchAliasTypeDetailValue().clone();
                
                batchAliasTypeDetailValue.setIsDefault(true);
                updateBatchAliasTypeFromValue(batchAliasTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(batchAliasType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteBatchAliasTypes(List<BatchAliasType> batchAliasTypes, BasePK deletedBy) {
        batchAliasTypes.forEach((batchAliasType) -> 
                deleteBatchAliasType(batchAliasType, deletedBy)
        );
    }
    
    public void deleteBatchAliasTypesByBatchType(BatchType batchType, BasePK deletedBy) {
        deleteBatchAliasTypes(getBatchAliasTypesForUpdate(batchType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Batch Alias Type Descriptions
    // --------------------------------------------------------------------------------
    
    public BatchAliasTypeDescription createBatchAliasTypeDescription(BatchAliasType batchAliasType, Language language, String description, BasePK createdBy) {
        var batchAliasTypeDescription = BatchAliasTypeDescriptionFactory.getInstance().create(batchAliasType, language,
                description, session.getStartTime(), Session.MAX_TIME_LONG);
        
        sendEvent(batchAliasType.getPrimaryKey(), EventTypes.MODIFY, batchAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return batchAliasTypeDescription;
    }
    
    private static final Map<EntityPermission, String> getBatchAliasTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchaliastypedescriptions " +
                "WHERE btchatd_btchat_batchaliastypeid = ? AND btchatd_lang_languageid = ? AND btchatd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchaliastypedescriptions " +
                "WHERE btchatd_btchat_batchaliastypeid = ? AND btchatd_lang_languageid = ? AND btchatd_thrutime = ? " +
                "FOR UPDATE");
        getBatchAliasTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private BatchAliasTypeDescription getBatchAliasTypeDescription(BatchAliasType batchAliasType, Language language, EntityPermission entityPermission) {
        return BatchAliasTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getBatchAliasTypeDescriptionQueries,
                batchAliasType, language, Session.MAX_TIME);
    }
    
    public BatchAliasTypeDescription getBatchAliasTypeDescription(BatchAliasType batchAliasType, Language language) {
        return getBatchAliasTypeDescription(batchAliasType, language, EntityPermission.READ_ONLY);
    }
    
    public BatchAliasTypeDescription getBatchAliasTypeDescriptionForUpdate(BatchAliasType batchAliasType, Language language) {
        return getBatchAliasTypeDescription(batchAliasType, language, EntityPermission.READ_WRITE);
    }
    
    public BatchAliasTypeDescriptionValue getBatchAliasTypeDescriptionValue(BatchAliasTypeDescription batchAliasTypeDescription) {
        return batchAliasTypeDescription == null? null: batchAliasTypeDescription.getBatchAliasTypeDescriptionValue().clone();
    }
    
    public BatchAliasTypeDescriptionValue getBatchAliasTypeDescriptionValueForUpdate(BatchAliasType batchAliasType, Language language) {
        return getBatchAliasTypeDescriptionValue(getBatchAliasTypeDescriptionForUpdate(batchAliasType, language));
    }
    
    private static final Map<EntityPermission, String> getBatchAliasTypeDescriptionsByBatchAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchaliastypedescriptions, languages " +
                "WHERE btchatd_btchat_batchaliastypeid = ? AND btchatd_thrutime = ? AND btchatd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchaliastypedescriptions " +
                "WHERE btchatd_btchat_batchaliastypeid = ? AND btchatd_thrutime = ? " +
                "FOR UPDATE");
        getBatchAliasTypeDescriptionsByBatchAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<BatchAliasTypeDescription> getBatchAliasTypeDescriptionsByBatchAliasType(BatchAliasType batchAliasType, EntityPermission entityPermission) {
        return BatchAliasTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getBatchAliasTypeDescriptionsByBatchAliasTypeQueries,
                batchAliasType, Session.MAX_TIME);
    }
    
    public List<BatchAliasTypeDescription> getBatchAliasTypeDescriptionsByBatchAliasType(BatchAliasType batchAliasType) {
        return getBatchAliasTypeDescriptionsByBatchAliasType(batchAliasType, EntityPermission.READ_ONLY);
    }
    
    public List<BatchAliasTypeDescription> getBatchAliasTypeDescriptionsByBatchAliasTypeForUpdate(BatchAliasType batchAliasType) {
        return getBatchAliasTypeDescriptionsByBatchAliasType(batchAliasType, EntityPermission.READ_WRITE);
    }
    
    public String getBestBatchAliasTypeDescription(BatchAliasType batchAliasType, Language language) {
        String description;
        var batchAliasTypeDescription = getBatchAliasTypeDescription(batchAliasType, language);
        
        if(batchAliasTypeDescription == null && !language.getIsDefault()) {
            batchAliasTypeDescription = getBatchAliasTypeDescription(batchAliasType, partyControl.getDefaultLanguage());
        }
        
        if(batchAliasTypeDescription == null) {
            description = batchAliasType.getLastDetail().getBatchAliasTypeName();
        } else {
            description = batchAliasTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public BatchAliasTypeDescriptionTransfer getBatchAliasTypeDescriptionTransfer(UserVisit userVisit, BatchAliasTypeDescription batchAliasTypeDescription) {
        return batchAliasTypeDescriptionTransferCache.getTransfer(userVisit, batchAliasTypeDescription);
    }
    
    public List<BatchAliasTypeDescriptionTransfer> getBatchAliasTypeDescriptionTransfersByBatchAliasType(UserVisit userVisit, BatchAliasType batchAliasType) {
        var batchAliasTypeDescriptions = getBatchAliasTypeDescriptionsByBatchAliasType(batchAliasType);
        List<BatchAliasTypeDescriptionTransfer> batchAliasTypeDescriptionTransfers = new ArrayList<>(batchAliasTypeDescriptions.size());
        
        batchAliasTypeDescriptions.forEach((batchAliasTypeDescription) ->
                batchAliasTypeDescriptionTransfers.add(batchAliasTypeDescriptionTransferCache.getTransfer(userVisit, batchAliasTypeDescription))
        );
        
        return batchAliasTypeDescriptionTransfers;
    }
    
    public void updateBatchAliasTypeDescriptionFromValue(BatchAliasTypeDescriptionValue batchAliasTypeDescriptionValue, BasePK updatedBy) {
        if(batchAliasTypeDescriptionValue.hasBeenModified()) {
            var batchAliasTypeDescription = BatchAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     batchAliasTypeDescriptionValue.getPrimaryKey());
            
            batchAliasTypeDescription.setThruTime(session.getStartTime());
            batchAliasTypeDescription.store();

            var batchAliasType = batchAliasTypeDescription.getBatchAliasType();
            var language = batchAliasTypeDescription.getLanguage();
            var description = batchAliasTypeDescriptionValue.getDescription();
            
            batchAliasTypeDescription = BatchAliasTypeDescriptionFactory.getInstance().create(batchAliasType, language, description,
                    session.getStartTime(), Session.MAX_TIME_LONG);
            
            sendEvent(batchAliasType.getPrimaryKey(), EventTypes.MODIFY, batchAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteBatchAliasTypeDescription(BatchAliasTypeDescription batchAliasTypeDescription, BasePK deletedBy) {
        batchAliasTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(batchAliasTypeDescription.getBatchAliasTypePK(), EventTypes.MODIFY, batchAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteBatchAliasTypeDescriptionsByBatchAliasType(BatchAliasType batchAliasType, BasePK deletedBy) {
        var batchAliasTypeDescriptions = getBatchAliasTypeDescriptionsByBatchAliasTypeForUpdate(batchAliasType);
        
        batchAliasTypeDescriptions.forEach((batchAliasTypeDescription) -> 
                deleteBatchAliasTypeDescription(batchAliasTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Batches
    // --------------------------------------------------------------------------------
    
    public Batch createBatch(BatchType batchType, String batchName, BasePK createdBy) {
        var batch = BatchFactory.getInstance().create();
        var batchDetail = BatchDetailFactory.getInstance().create(batch, batchType, batchName, session.getStartTime(), Session.MAX_TIME_LONG);
        
        // Convert to R/W
        batch = BatchFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, batch.getPrimaryKey());
        batch.setActiveDetail(batchDetail);
        batch.setLastDetail(batchDetail);
        batch.store();
        
        sendEvent(batch.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return batch;
    }
    
    public Batch getBatchByPK(BatchPK batchPK) {
        return BatchFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, batchPK);
    }
    
    private static final Map<EntityPermission, String> getBatchByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batches, batchdetails " +
                "WHERE btch_activedetailid = btchdt_batchdetailid AND btchdt_btchtyp_batchtypeid = ? " +
                "AND btchdt_batchname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batches, batchdetails " +
                "WHERE btch_activedetailid = btchdt_batchdetailid AND btchdt_btchtyp_batchtypeid = ? " +
                "AND btchdt_batchname = ? " +
                "FOR UPDATE");
        getBatchByNameQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private Batch getBatchByName(BatchType batchType, String batchName, EntityPermission entityPermission) {
        return BatchFactory.getInstance().getEntityFromQuery(entityPermission, getBatchByNameQueries,
                batchType, batchName);
    }
    
    public Batch getBatchByName(BatchType batchType, String batchName) {
        return getBatchByName(batchType, batchName, EntityPermission.READ_ONLY);
    }
    
    public Batch getBatchByNameForUpdate(BatchType batchType, String batchName) {
        return getBatchByName(batchType, batchName, EntityPermission.READ_WRITE);
    }
    
    public BatchDetailValue getBatchDetailValueForUpdate(Batch batch) {
        return batch == null? null: batch.getLastDetailForUpdate().getBatchDetailValue().clone();
    }
    
    public BatchDetailValue getBatchDetailValueByNameForUpdate(BatchType batchType,
            String batchName) {
        return getBatchDetailValueForUpdate(getBatchByNameForUpdate(batchType, batchName));
    }
    
    private static final Map<EntityPermission, String> getBatchByAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchaliases, batch, batchdetails " +
                "WHERE btchal_btchat_batchaliastypeid = ? AND btchal_alias = ? AND btchal_thrutime = ? " +
                "AND btchal_btchar_batchid = btchar_batchid " +
                "AND btchar_activedetailid = btchardt_batchdetailid");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchaliases, batch, batchdetails " +
                "WHERE btchal_btchat_batchaliastypeid = ? AND btchal_alias = ? AND btchal_thrutime = ? " +
                "AND btchal_btchar_batchid = btchar_batchid " +
                "AND btchar_activedetailid = btchardt_batchdetailid " +
                "FOR UPDATE");
        getBatchByAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private Batch getBatchByAlias(BatchAliasType batchAliasType, String alias, EntityPermission entityPermission) {
        return BatchFactory.getInstance().getEntityFromQuery(entityPermission, getBatchByAliasQueries, batchAliasType, alias, Session.MAX_TIME);
    }

    public Batch getBatchByAlias(BatchAliasType batchAliasType, String alias) {
        return getBatchByAlias(batchAliasType, alias, EntityPermission.READ_ONLY);
    }

    public Batch getBatchByAliasForUpdate(BatchAliasType batchAliasType, String alias) {
        return getBatchByAlias(batchAliasType, alias, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getBatchesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batches, batchdetails " +
                "WHERE btch_activedetailid = btchdt_batchdetailid AND btchdt_btchtyp_batchtypeid = ? " +
                "ORDER BY btchdt_batchname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batches, batchdetails " +
                "WHERE btch_activedetailid = btchdt_batchdetailid AND btchdt_btchtyp_batchtypeid = ? " +
                "FOR UPDATE");
        getBatchesQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<Batch> getBatches(BatchType batchType, EntityPermission entityPermission) {
        return BatchFactory.getInstance().getEntitiesFromQuery(entityPermission, getBatchesQueries, batchType);
    }
    
    public List<Batch> getBatches(BatchType batchType) {
        return getBatches(batchType, EntityPermission.READ_ONLY);
    }

    public List<Batch> getBatchesForUpdate(BatchType batchType) {
        return getBatches(batchType, EntityPermission.READ_WRITE);
    }

    public List<Batch> getBatchesUsingNames(String batchTypeName) {
        return getBatches(getBatchTypeByName(batchTypeName), EntityPermission.READ_ONLY);
    }

    public List<Batch> getBatchesUsingNamesForUpdate(String batchTypeName) {
        return getBatches(getBatchTypeByName(batchTypeName), EntityPermission.READ_WRITE);
    }

    public BatchTransfer getBatchTransfer(UserVisit userVisit, Batch batch) {
        return batchTransferCache.getTransfer(userVisit, batch);
    }
    
    public List<BatchTransfer> getBatchTransfers(UserVisit userVisit, BatchType batchType) {
        var batches = getBatches(batchType);
        List<BatchTransfer> batchTransfers = new ArrayList<>(batches.size());
        
        batches.forEach((batch) ->
                batchTransfers.add(batchTransferCache.getTransfer(userVisit, batch))
        );
        
        return batchTransfers;
    }
    
    public void updateBatchFromValue(BatchDetailValue batchDetailValue, BasePK updatedBy) {
        if(batchDetailValue.hasBeenModified()) {
            var batch = BatchFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    batchDetailValue.getBatchPK());
            var batchDetail = batch.getActiveDetailForUpdate();
            
            batchDetail.setThruTime(session.getStartTime());
            batchDetail.store();

            var batchPK = batchDetail.getBatchPK();
            var batchType = batchDetail.getBatchType();
            var batchTypePK = batchType.getPrimaryKey();
            var batchName = batchDetailValue.getBatchName();
            
            batchDetail = BatchDetailFactory.getInstance().create(batchPK, batchTypePK, batchName, session.getStartTime(), Session.MAX_TIME_LONG);
            
            batch.setActiveDetail(batchDetail);
            batch.setLastDetail(batchDetail);
            
            sendEvent(batchPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteBatch(Batch batch, BasePK deletedBy) {
        deleteBatchAliasesByBatch(batch, deletedBy);

        var batchDetail = batch.getLastDetailForUpdate();
        batchDetail.setThruTime(session.getStartTime());
        batch.setActiveDetail(null);
        batch.store();
        
        sendEvent(batch.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteBatches(List<Batch> batches, BasePK deletedBy) {
        batches.forEach((batch) -> 
                deleteBatch(batch, deletedBy)
        );
    }
    
    public void deleteBatchesByBatchType(BatchType batchType, BasePK deletedBy) {
        deleteBatches(getBatchesForUpdate(batchType), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Batch Aliases
    // --------------------------------------------------------------------------------
    
    public BatchAlias createBatchAlias(Batch batch, BatchAliasType batchAliasType, String alias, BasePK createdBy) {
        var batchAlias = BatchAliasFactory.getInstance().create(batch, batchAliasType, alias, session.getStartTime(), Session.MAX_TIME_LONG);
        
        sendEvent(batch.getPrimaryKey(), EventTypes.MODIFY, batchAlias.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return batchAlias;
    }
    
    private static final Map<EntityPermission, String> getBatchAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchaliases " +
                "WHERE btchal_btch_batchid = ? AND btchal_btchat_batchaliastypeid = ? AND btchal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchaliases " +
                "WHERE btchal_btch_batchid = ? AND btchal_btchat_batchaliastypeid = ? AND btchal_thrutime = ? " +
                "FOR UPDATE");
        getBatchAliasQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private BatchAlias getBatchAlias(Batch batch, BatchAliasType batchAliasType, EntityPermission entityPermission) {
        return BatchAliasFactory.getInstance().getEntityFromQuery(entityPermission, getBatchAliasQueries,
                batch, batchAliasType, Session.MAX_TIME);
    }
    
    public BatchAlias getBatchAlias(Batch batch, BatchAliasType batchAliasType) {
        return getBatchAlias(batch, batchAliasType, EntityPermission.READ_ONLY);
    }
    
    public BatchAlias getBatchAliasForUpdate(Batch batch, BatchAliasType batchAliasType) {
        return getBatchAlias(batch, batchAliasType, EntityPermission.READ_WRITE);
    }
    
    public BatchAliasValue getBatchAliasValue(BatchAlias batchAlias) {
        return batchAlias == null? null: batchAlias.getBatchAliasValue().clone();
    }
    
    public BatchAliasValue getBatchAliasValueForUpdate(Batch batch, BatchAliasType batchAliasType) {
        return getBatchAliasValue(getBatchAliasForUpdate(batch, batchAliasType));
    }
    
    private static final Map<EntityPermission, String> getBatchAliasByAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchaliases " +
                "WHERE btchal_btchat_batchaliastypeid = ? AND btchal_alias = ? AND btchal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchaliases " +
                "WHERE btchal_btchat_batchaliastypeid = ? AND btchal_alias = ? AND btchal_thrutime = ? " +
                "FOR UPDATE");
        getBatchAliasByAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private BatchAlias getBatchAliasByAlias(BatchAliasType batchAliasType, String alias, EntityPermission entityPermission) {
        return BatchAliasFactory.getInstance().getEntityFromQuery(entityPermission, getBatchAliasByAliasQueries, batchAliasType, alias, Session.MAX_TIME);
    }

    public BatchAlias getBatchAliasByAlias(BatchAliasType batchAliasType, String alias) {
        return getBatchAliasByAlias(batchAliasType, alias, EntityPermission.READ_ONLY);
    }

    public BatchAlias getBatchAliasByAliasForUpdate(BatchAliasType batchAliasType, String alias) {
        return getBatchAliasByAlias(batchAliasType, alias, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getBatchAliasesByBatchQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchaliases, batchaliastypes, batchaliastypedetails " +
                "WHERE btchal_btch_batchid = ? AND btchal_thrutime = ? " +
                "AND btchal_btchat_batchaliastypeid = btchat_batchaliastypeid AND btchat_lastdetailid = btchatdt_batchaliastypedetailid" +
                "ORDER BY btchatdt_sortorder, btchatdt_batchaliastypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchaliases " +
                "WHERE btchal_btch_batchid = ? AND btchal_thrutime = ? " +
                "FOR UPDATE");
        getBatchAliasesByBatchQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<BatchAlias> getBatchAliasesByBatch(Batch batch, EntityPermission entityPermission) {
        return BatchAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getBatchAliasesByBatchQueries,
                batch, Session.MAX_TIME);
    }
    
    public List<BatchAlias> getBatchAliasesByBatch(Batch batch) {
        return getBatchAliasesByBatch(batch, EntityPermission.READ_ONLY);
    }
    
    public List<BatchAlias> getBatchAliasesByBatchForUpdate(Batch batch) {
        return getBatchAliasesByBatch(batch, EntityPermission.READ_WRITE);
    }
    
    private static final Map<EntityPermission, String> getBatchAliasesByBatchAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchaliases, batches, batchdetails " +
                "WHERE btchal_btchat_batchaliastypeid = ? AND btchal_thrutime = ? " +
                "AND btchal_btch_batchid = btch_batchid AND btch_lastdetailid = btchdt_batchdetailid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchaliases " +
                "WHERE btchal_btchat_batchaliastypeid = ? AND btchal_thrutime = ? " +
                "FOR UPDATE");
        getBatchAliasesByBatchAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<BatchAlias> getBatchAliasesByBatchAliasType(BatchAliasType batchAliasType, EntityPermission entityPermission) {
        return BatchAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getBatchAliasesByBatchAliasTypeQueries,
                batchAliasType, Session.MAX_TIME);
    }
    
    public List<BatchAlias> getBatchAliasesByBatchAliasType(BatchAliasType batchAliasType) {
        return getBatchAliasesByBatchAliasType(batchAliasType, EntityPermission.READ_ONLY);
    }
    
    public List<BatchAlias> getBatchAliasesByBatchAliasTypeForUpdate(BatchAliasType batchAliasType) {
        return getBatchAliasesByBatchAliasType(batchAliasType, EntityPermission.READ_WRITE);
    }
    
    public BatchAliasTransfer getBatchAliasTransfer(UserVisit userVisit, BatchAlias batchAlias) {
        return batchAliasTransferCache.getTransfer(userVisit, batchAlias);
    }
    
    public List<BatchAliasTransfer> getBatchAliasTransfersByBatch(UserVisit userVisit, Batch batch) {
        var batchaliases = getBatchAliasesByBatch(batch);
        List<BatchAliasTransfer> batchAliasTransfers = new ArrayList<>(batchaliases.size());
        
        batchaliases.forEach((batchAlias) ->
                batchAliasTransfers.add(batchAliasTransferCache.getTransfer(userVisit, batchAlias))
        );
        
        return batchAliasTransfers;
    }
    
    public void updateBatchAliasFromValue(BatchAliasValue batchAliasValue, BasePK updatedBy) {
        if(batchAliasValue.hasBeenModified()) {
            var batchAlias = BatchAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, batchAliasValue.getPrimaryKey());
            
            batchAlias.setThruTime(session.getStartTime());
            batchAlias.store();

            var batchPK = batchAlias.getBatchPK();
            var batchAliasTypePK = batchAlias.getBatchAliasTypePK();
            var alias  = batchAliasValue.getAlias();
            
            batchAlias = BatchAliasFactory.getInstance().create(batchPK, batchAliasTypePK, alias, session.getStartTime(), Session.MAX_TIME_LONG);
            
            sendEvent(batchPK, EventTypes.MODIFY, batchAlias.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteBatchAlias(BatchAlias batchAlias, BasePK deletedBy) {
        batchAlias.setThruTime(session.getStartTime());
        
        sendEvent(batchAlias.getBatchPK(), EventTypes.MODIFY, batchAlias.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteBatchAliasesByBatchAliasType(BatchAliasType batchAliasType, BasePK deletedBy) {
        var batchaliases = getBatchAliasesByBatchAliasTypeForUpdate(batchAliasType);
        
        batchaliases.forEach((batchAlias) -> 
                deleteBatchAlias(batchAlias, deletedBy)
        );
    }
    
    public void deleteBatchAliasesByBatch(Batch batch, BasePK deletedBy) {
        var batchaliases = getBatchAliasesByBatchForUpdate(batch);
        
        batchaliases.forEach((batchAlias) -> 
                deleteBatchAlias(batchAlias, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Batch Type Entity Types
    // --------------------------------------------------------------------------------

    public BatchEntity createBatchEntity(EntityInstance entityInstance, Batch batch, BasePK createdBy) {
        var batchEntity = BatchEntityFactory.getInstance().create(entityInstance, batch, session.getStartTime(), Session.MAX_TIME_LONG);

        sendEvent(entityInstance, EventTypes.MODIFY, batchEntity.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return batchEntity;
    }

    public long countBatchEntitiesByBatch(Batch batch) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM batchentities " +
                "WHERE btche_btch_batchid = ? AND btche_thrutime = ?",
                batch, Session.MAX_TIME_LONG);
    }

    public boolean batchEntityExists(EntityInstance entityInstance, Batch batch) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM batchentities " +
                "WHERE btche_eni_entityinstanceid = ? AND btche_btch_batchid = ? AND btche_thrutime = ?",
                entityInstance, batch, Session.MAX_TIME_LONG) == 1;
    }
    
    private static final Map<EntityPermission, String> getBatchEntityQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchentities " +
                "WHERE btche_eni_entityinstanceid = ? AND btche_btch_batchid = ? AND btche_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchentities " +
                "WHERE btche_eni_entityinstanceid = ? AND btche_btch_batchid = ? AND btche_thrutime = ? " +
                "FOR UPDATE");
        getBatchEntityQueries = Collections.unmodifiableMap(queryMap);
    }

    private BatchEntity getBatchEntity(EntityInstance entityInstance, Batch batch, EntityPermission entityPermission) {
        return BatchEntityFactory.getInstance().getEntityFromQuery(entityPermission, getBatchEntityQueries,
                entityInstance, batch, Session.MAX_TIME_LONG);
    }

    public BatchEntity getBatchEntity(EntityInstance entityInstance, Batch batch) {
        return getBatchEntity(entityInstance, batch, EntityPermission.READ_ONLY);
    }

    public BatchEntity getBatchEntityForUpdate(EntityInstance entityInstance, Batch batch) {
        return getBatchEntity(entityInstance, batch, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getBatchEntitiesByEntityInstanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchentities, batchs, batchdetails " +
                "WHERE btche_eni_entityinstanceid = ? AND btche_thrutime = ? " +
                "AND btche_btch_batchid = btch_batchid AND btch_lastdetailid = entdt_batchdetailid " +
                "ORDER BY entdt_sortorder, entdt_batchname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchentities " +
                "WHERE btche_eni_entityinstanceid = ? AND btche_thrutime = ? " +
                "FOR UPDATE");
        getBatchEntitiesByEntityInstanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<BatchEntity> getBatchEntitiesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        return BatchEntityFactory.getInstance().getEntitiesFromQuery(entityPermission, getBatchEntitiesByEntityInstanceQueries,
                entityInstance, Session.MAX_TIME_LONG);
    }

    public List<BatchEntity> getBatchEntitiesByEntityInstance(EntityInstance entityInstance) {
        return getBatchEntitiesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public List<BatchEntity> getBatchEntitiesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getBatchEntitiesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getBatchEntitiesByBatchQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM batchentities, entityinstances " +
                "WHERE btche_btch_batchid = ? AND btche_thrutime = ? " +
                "AND btche_eni_entityinstanceid = eni_entityinstanceid " +
                "ORDER BY eni_entityuniqueid");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM batchentities " +
                "WHERE btche_eni_entityinstanceid = ? AND btche_thrutime = ? " +
                "FOR UPDATE");
        getBatchEntitiesByBatchQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<BatchEntity> getBatchEntitiesByBatch(Batch batch, EntityPermission entityPermission) {
        return BatchEntityFactory.getInstance().getEntitiesFromQuery(entityPermission, getBatchEntitiesByBatchQueries,
                batch, Session.MAX_TIME_LONG);
    }

    public List<BatchEntity> getBatchEntitiesByBatch(Batch batch) {
        return getBatchEntitiesByBatch(batch, EntityPermission.READ_ONLY);
    }

    public List<BatchEntity> getBatchEntitiesByBatchForUpdate(Batch batch) {
        return getBatchEntitiesByBatch(batch, EntityPermission.READ_WRITE);
    }

    public BatchEntityTransfer getBatchEntityTransfer(UserVisit userVisit, BatchEntity batchEntity) {
        return batchEntityTransferCache.getTransfer(userVisit, batchEntity);
    }

    public List<BatchEntityTransfer> getBatchEntityTransfers(UserVisit userVisit, Collection<BatchEntity> batchEntities) {
        List<BatchEntityTransfer> batchEntityTransfers = new ArrayList<>(batchEntities.size());

        batchEntities.forEach((batchEntity) ->
                batchEntityTransfers.add(batchEntityTransferCache.getTransfer(userVisit, batchEntity))
        );

        return batchEntityTransfers;
    }

    public List<BatchEntityTransfer> getBatchEntityTransfersByEntityInstance(UserVisit userVisit, EntityInstance entityInstance) {
        return getBatchEntityTransfers(userVisit, getBatchEntitiesByEntityInstance(entityInstance));
    }

    public List<BatchEntityTransfer> getBatchEntityTransfersByBatch(UserVisit userVisit, Batch batch) {
        return getBatchEntityTransfers(userVisit, getBatchEntitiesByBatch(batch));
    }

    public void deleteBatchEntity(BatchEntity batchEntity, BasePK deletedBy) {
        batchEntity.setThruTime(session.getStartTime());

        sendEvent(batchEntity.getEntityInstance(), EventTypes.MODIFY, batchEntity.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteBatchEntities(List<BatchEntity> batchEntities, BasePK deletedBy) {
        batchEntities.forEach((batchEntity) -> 
                deleteBatchEntity(batchEntity, deletedBy)
        );
    }

    public void deleteBatchEntitiesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteBatchEntities(getBatchEntitiesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    public void deleteBatchEntitiesByBatch(Batch batch, BasePK deletedBy) {
        deleteBatchEntities(getBatchEntitiesByBatchForUpdate(batch), deletedBy);
    }

}
